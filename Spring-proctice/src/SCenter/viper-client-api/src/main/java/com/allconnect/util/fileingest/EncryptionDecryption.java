
package com.A.util.fileingest;

 

public interface EncryptionDecryption
{
	/**
	 * This method takes in a byte array and returns an encrypted
	 * byte array
	 * @param message
	 * @return
	 * @throws IllegalArgumentException
	 */
	public byte[] encrypt(final byte[] message) throws IllegalArgumentException;

	/**
	 * This method takes in a encrypted byte array and returns an decrypted
	 * byte array
	 * @param encryptedMessage
	 * @return
	 * @throws IllegalArgumentException
	 */
	public byte[] decrypt(final byte[] encryptedMessage) throws IllegalArgumentException;
}