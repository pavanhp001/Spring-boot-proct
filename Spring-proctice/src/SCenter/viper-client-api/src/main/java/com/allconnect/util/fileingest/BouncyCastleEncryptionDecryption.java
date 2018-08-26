
package com.A.util.fileingest;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;

import java.io.*;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;
import java.util.Iterator;
import com.A.util.fileingest.PGPConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BouncyCastleEncryptionDecryption implements EncryptionDecryption
{
	private static final Logger log = LoggerFactory.getLogger(BouncyCastleEncryptionDecryption.class);
	private PGPConfig pgpConfig;
	private final static String PROVIDER_NAME = "BC";

	private BouncyCastleEncryptionDecryption(PGPConfig pgpConfig)
	{
		this.pgpConfig = pgpConfig;
	}

	public final static BouncyCastleEncryptionDecryption getInstance(PGPConfig pgpConfig)
	{
		Security.addProvider(new BouncyCastleProvider());
		BouncyCastleEncryptionDecryption bced = new BouncyCastleEncryptionDecryption(pgpConfig);
		return bced;
	}

	public static PGPPublicKeyRingCollection readPublicKeyRingCollection(InputStream in) throws IOException,
		PGPException
	{
		in = PGPUtil.getDecoderStream(in);

		return new PGPPublicKeyRingCollection(in);
	}

	private static PGPPrivateKey findSecretKey(final PGPSecretKeyRingCollection pgpSec, final long keyID,
		final char[] pass) throws PGPException, NoSuchProviderException
	{
		final PGPSecretKey pgpSecKey = pgpSec.getSecretKey(keyID);

		if (pgpSecKey == null)
		{
			return null;
		}

		return pgpSecKey.extractPrivateKey(pass, PROVIDER_NAME);
	}

	/**
	 * decrypt the passed in message stream
	 *
	 * @param encrypted The message to be decrypted.
	 * @return Clear text as a byte array. I18N considerations are not handled
	 *         by this routine
	 */
	public byte[] decrypt(byte[] encrypted) throws IllegalArgumentException
	{
		if (null == encrypted || encrypted.length < 1)
		{
			throw new IllegalArgumentException("Bytes to dencrypt must not be null or less than 1 byte.");
		}
		byte[] returnBytes = null;
		try
		{
			InputStream in = new ByteArrayInputStream(encrypted);
			char[] password = (null == pgpConfig.getPassphrase())
				? "".toCharArray()
				: pgpConfig.getPassphrase().toCharArray();
			InputStream keyIn = null;
				String privateKeyFileName = pgpConfig.getPrivateKeyFilePath() + pgpConfig.getPrivateKeyFileName();
                if (log.isDebugEnabled())
                {
                    log.debug("privateKeyFileName => "+privateKeyFileName);
                }
				keyIn = new FileInputStream(privateKeyFileName);

			in = PGPUtil.getDecoderStream(in);

			PGPObjectFactory pgpF = new PGPObjectFactory(in);
			PGPEncryptedDataList enc = null;
			Object o = pgpF.nextObject();

			//
			// the first object might be a PGP marker packet.
			//
			if (o instanceof PGPEncryptedDataList)
			{
				enc = (PGPEncryptedDataList) o;
			}
			else
			{
				enc = (PGPEncryptedDataList) pgpF.nextObject();
			}

			//
			// find the secret key
			//
			Iterator it = enc.getEncryptedDataObjects();
			PGPPrivateKey sKey = null;
			PGPPublicKeyEncryptedData pbe = null;
			PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(PGPUtil.getDecoderStream(keyIn));

			while (sKey == null && it.hasNext())
			{
				pbe = (PGPPublicKeyEncryptedData) it.next();

				sKey = findSecretKey(pgpSec, pbe.getKeyID(), password);
			}

			if (sKey == null)
			{
				throw new IllegalArgumentException("secret key for message not found.");
			}

			InputStream clear = pbe.getDataStream(sKey, PROVIDER_NAME);

			PGPObjectFactory pgpFact = new PGPObjectFactory(clear);

            Object object = pgpFact.nextObject();
            InputStream unc = null;
            if (object instanceof PGPCompressedData)
            {
                 PGPCompressedData cData = (PGPCompressedData) object;
                pgpFact = new PGPObjectFactory(cData.getDataStream());
			    PGPLiteralData ld = (PGPLiteralData) pgpFact.nextObject();
			    unc = ld.getInputStream();
            }
            else
            {
               PGPLiteralData ld = (PGPLiteralData) object;

			    unc = ld.getInputStream();
            }
            /*
			PGPCompressedData cData = (PGPCompressedData) pgpFact.nextObject();

			pgpFact = new PGPObjectFactory(cData.getDataStream());

			PGPLiteralData ld = (PGPLiteralData) pgpFact.nextObject();

			InputStream unc = ld.getInputStream();
            */
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int ch;

			while ((ch = unc.read()) >= 0)
			{
				out.write(ch);

			}

			returnBytes = out.toByteArray();
			out.close();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return returnBytes;
	}

	/**
	 * Simple PGP encryptor between byte[].
	 *
	 * @param clearData The test to be encrypted
	 * @return encrypted data.
	 *
	 */
	@Override
	public byte[] encrypt(byte[] clearData) throws IllegalArgumentException
	{
		if (null == clearData || clearData.length < 1)
		{
			throw new IllegalArgumentException("Bytes to encrypt must not be null or less than 1 byte.");
		}
		ByteArrayOutputStream encOut = new ByteArrayOutputStream();
		try
		{
			String fileName = null;
			boolean withIntegrityCheck = true;
			boolean armor = true;
			//	String publicKeyFileName = "/Users/tap/.gnupg/local.public.key";
			InputStream pubKeyStream = null;

				String publicKeyFileName = pgpConfig.getPublicKeyFilePath() + pgpConfig.getPublicKeyFileName();
                if (log.isDebugEnabled())
                {
                    log.debug("publicKeyFileName => "+publicKeyFileName);
                }
				pubKeyStream = new FileInputStream(publicKeyFileName);

			PGPPublicKey encKey = readPublicKey(pubKeyStream);

			if (fileName == null)
			{
				fileName = PGPLiteralData.CONSOLE;
			}

			OutputStream out = encOut;
			if (armor)
			{
				out = new ArmoredOutputStream(out);
			}

			ByteArrayOutputStream bOut = new ByteArrayOutputStream();

			PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(PGPCompressedDataGenerator.ZIP);
			OutputStream cos = comData.open(bOut); // open it with the final
			// destination
			PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();

			// we want to generate compressed data. This might be a user option
			// later,
			// in which case we would pass in bOut.
			OutputStream pOut = lData.open(cos, // the compressed output stream
				PGPLiteralData.BINARY, fileName, // "filename" to store
				clearData.length, // length of clear data
				new Date() // current time
			);
			pOut.write(clearData);

			lData.close();
			comData.close();

			PGPEncryptedDataGenerator cPk = new PGPEncryptedDataGenerator(PGPEncryptedData.CAST5, withIntegrityCheck,
				new SecureRandom(), PROVIDER_NAME);

			cPk.addMethod(encKey);

			byte[] bytes = bOut.toByteArray();

			OutputStream cOut = cPk.open(out, bytes.length);

			cOut.write(bytes); // obtain the actual bytes from the compressed stream

			cOut.close();

			out.close();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}

		return encOut.toByteArray();
	}

	private static PGPPublicKey readPublicKey(InputStream in) throws IOException, PGPException
	{
		in = PGPUtil.getDecoderStream(in);

		PGPPublicKeyRingCollection pgpPub = new PGPPublicKeyRingCollection(in);

		//
		// we just loop through the collection till we find a key suitable for
		// encryption, in the real
		// world you would probably want to be a bit smarter about this.
		//

		//
		// iterate through the key rings.
		//
		Iterator rIt = pgpPub.getKeyRings();

		while (rIt.hasNext())
		{
			PGPPublicKeyRing kRing = (PGPPublicKeyRing) rIt.next();
			Iterator kIt = kRing.getPublicKeys();

			while (kIt.hasNext())
			{
				PGPPublicKey k = (PGPPublicKey) kIt.next();

				if (k.isEncryptionKey())
				{
					return k;
				}
			}
		}

		throw new IllegalArgumentException("Can't find encryption key in key ring.");
	}

}
