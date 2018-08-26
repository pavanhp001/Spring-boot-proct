package com.AL.comm.manager.jms.util;

import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

 

public class JNDIUtil {
	
	private static Logger logger = Logger.getLogger(JNDIUtil.class);
	
	public static void displayContext(final String namespace)
	{
		InitialContext ctx;
		try {
			ctx = new InitialContext();
		
		
	      NamingEnumeration<NameClassPair> names = ctx.list(namespace);
	      int count = 0;
	      while( names.hasMore() )
	      {
	         NameClassPair ncp = (NameClassPair) names.next();
	         logger.debug(ncp+":"+ncp.getName()+":"+ncp.getClassName());
	         
	         
	         count ++;
	      }
	     
	      ctx.close();

		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
		}
	}

}
