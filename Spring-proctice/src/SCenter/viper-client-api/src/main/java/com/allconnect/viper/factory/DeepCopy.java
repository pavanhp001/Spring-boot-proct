package com.A.V.factory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DeepCopy {

	@SuppressWarnings("unchecked")
	public <T extends Object> T deepCopy(final Serializable obj)
	{
	        try
	        {
	                ObjectOutputStream oos = null;
	                ObjectInputStream ois = null;
	                try
	                {
	                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                        oos = new ObjectOutputStream(bos);
	                        oos.writeObject(obj);
	                        oos.flush();
	                        ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
	                        return (T) ois.readObject();
	                }
	                finally
	                {
	                        oos.close();
	                        ois.close();
	                }
	        }
	        catch ( ClassNotFoundException cnfe )
	        {
	                // Impossible, since both sides deal in the same loaded classes.
	                return null;
	        }
	        catch ( IOException ioe )
	        {
	                // This has to be "impossible", given that oos and ois wrap a *byte array*.
	                return null;
	        }
	}

}
