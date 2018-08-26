package com.AL.comm.manager.jms.receiver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;


public abstract class MDBReceiverAbstract implements MessageListener {
	
	private CommunicationManager<Message, MessageListener> communicationManager = null;
	

	Logger logger = Logger.getLogger(MDBReceiverAbstract.class);

	protected Class<?> paramClazz = null;

	@Resource(name = "CLAZZ_NAME")
	protected String clazzname;

	@Resource(name = "PARAM_CLAZZ_NAME")
	protected String paramClazzName;

	@Resource(name = "METHOD_NAME")
	protected String methodName;

	@Resource(name = "JNDI_NAME")
	protected String jndiName;

	@Resource(name = "NAMESPACE")
	protected String namespace;

	protected static InitialContext ic;

	private static final String DEFAULT_MESSAGE = "executable.not.defined";

	/**
	 * @throws Exception
	 */
	public MDBReceiverAbstract() {
		super();

	}

	public MDBReceiverAbstract(final String key,
			final Executable<Message, String, String> executable) {
		super();
		JMSConfigManager.INSTANCE.put(key, executable);
		this.jndiName = key;
	}

	public MDBReceiverAbstract(final String jndiName) {
		super();
		this.jndiName = jndiName;
	}

	public MDBReceiverAbstract(final Class<?> clazz, final String method) {
		super();
		this.clazzname = clazz.getName();
		this.methodName = method;
	}

	public MDBReceiverAbstract(final Class<?> clazz, final String method,
			final String paramClazzName) {
		super();
		this.clazzname = clazz.getName();
		this.methodName = method;
		this.paramClazzName = paramClazzName;
	}

	public MDBReceiverAbstract(final String clazzname, final String method) {
		super();
		this.clazzname = clazzname;
		this.methodName = method;
	}

	public abstract void onMessage(final Message message);
	
	public void assertCommunicationManagerInitialized() {
		if (communicationManager == null) {
			communicationManager = JMSConfigManager.INSTANCE
					.createCommunicationManager(this.getNamespace());
			logger.debug("initializing.mdb.receiver.sender.with.namepace:"+this.getNamespace());
			logger.debug("initializing.mdb.receiver.sender.with.jndi:"+this.getNamespace()); 
		}
	}

	@PostConstruct
	public void initialize() {
		if (ic == null) {
			ic = JMSConfigManager.INSTANCE.getInitialContext();
		}
		
		assertCommunicationManagerInitialized();
	}

	@PreDestroy()
	public void cleanup() {
		ic = null;
	}
	
    @SuppressWarnings( "unchecked" )
    protected String processInstanceInvocation(final String message, 
                                                final Map<String, String> msgProps ) 
    {        
        logger.debug("processing.container.instance.invocation");

        Executable<String, String, String> executable;
        
        try {
            logger.debug("attempting.jndi.lookup:" + jndiName);

            executable = (Executable<String, String, String>) 
                            JMSConfigManager.INSTANCE.lookupFromJndi(jndiName);

            if (executable != null) {
                logger.debug("jndi.lookup.found:" + jndiName);
            } else {
                logger.debug("unable.to.find.jndi.lookup:" + jndiName);
            }

        } catch (NamingException e) {
            logger.debug("jndi.lookup.not.found:" + jndiName);
            logger.error(e);

            executable = null;
        }

        if (executable != null) {
            logger.debug("execute.method.execute.on.class."
                    + executable.getClass().getSimpleName());
            
            // TODO Try Catch needs to be around this block: 
            // whenever errors occur in the implemented receiver, 
            // it causes a strange ejbexception.
            try 
            {
                return executable.execute( message, msgProps );    
            }
            catch(Exception e)
            {
                logger.error("Error calling execute() method of MDBReceiverAbstract implementation.");
            }
        }

        throw new IllegalArgumentException("jndi.name.invalid." + jndiName);
    }	
	
    public void populateJMSHeader( final Map<String, String> map,
                                    final MapMessage message) 
    {
        if ((map == null) || (map.size() == 0)) {
            return;
        }

        Collection<String> keySet = map.keySet();

        for (String key : keySet) {

            String value = map.get(key);

            try {

                message.setStringProperty(key, value);
                logger.debug("added key:" + key + " value:" + value
                        + " to the message header ");

            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append(message.toString())
                        .append(" unable to set property key:").append(key)
                        .append(" value:").append(value).append(" ")
                        .append(e.getMessage());
                logger.warn(sb.toString());
            }
        }

    }

    @SuppressWarnings( "unchecked" )
    protected Map<String, String> extractHeaderInfo( final Message jmsMessage ) 
    {
        Map<String, String> mapHeaderProps = new HashMap<String, String>();
        try
        {
            java.util.Enumeration<String> msgProps = jmsMessage.getPropertyNames();
            
            while(msgProps.hasMoreElements())
            {
                String name = (String) msgProps.nextElement();
                String value = (String) jmsMessage.getObjectProperty( name );
                
                mapHeaderProps.put( name, value );
            }
        }
        catch ( JMSException e )
        {
            logger.error( e );
        } 
        
        if ( mapHeaderProps.size() <= 0 ) 
        {
            return null;
        }
        
        return mapHeaderProps;
    }
    
//	@SuppressWarnings( "unchecked" )
//    protected String processInstanceInvocation(final Message message) {
//		Executable<Message, String, String> executable = 
//		        (Executable<Message, String, String>) JMSConfigManager.INSTANCE.lookup(jndiName);
//
//		if (executable != null) {
//			return executable.execute(message);
//		}
//
//		return DEFAULT_MESSAGE;
//	}
	    
	protected String processDynamicInvocation(final Message message) {
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			String payload = null;

			try {
				payload = textMessage.getText();
			} catch (JMSException jmse) {
				logger.error(jmse);
			}
			return processDynamicInvocation(payload);

		}

		return DEFAULT_MESSAGE;

	}

	protected String processDynamicInvocation(final String message) {

		JMSConfigManager.INSTANCE.get(clazzname);

		Class<?> clazz = null;
		try {
			clazz = Class.forName(clazzname);
		} catch (ClassNotFoundException cnfe) {
			logger.fatal(cnfe);
		}
		Object objs = null;
		try {
			objs = clazz.newInstance();
		} catch (InstantiationException ie) {
			logger.fatal(ie);
		} catch (IllegalAccessException iae) {
			logger.fatal(iae);
		}

		if (objs == null) {
			throw new IllegalArgumentException("target instance is NULL:"
					+ objs);
		}

		if (paramClazzName != null) {
			try {

				paramClazz = Class.forName(paramClazzName);
			} catch (ClassNotFoundException cnfe) {
				logger.fatal("error finding class", cnfe);
			}
		} else {
			paramClazz = String.class;
		}

		Class<?>[] paramType = { paramClazz };

		Method method = null;
		try {
			method = clazz.getMethod(methodName, paramType);
		} catch (SecurityException se) {
			logger.fatal("error ", se);
		} catch (NoSuchMethodException nsme) {
			logger.fatal("error", nsme);
		}

		Object[] params = { message };
		try {
			Object result = method.invoke(objs, params);

			if (result instanceof String)
				return (String) result;
		} catch (IllegalArgumentException e) {
			logger.fatal("error ", e);
		} catch (IllegalAccessException e) {
			logger.fatal("error ", e);
		} catch (InvocationTargetException e) {
			logger.fatal("error ", e);
		}

		return DEFAULT_MESSAGE;
	}

	public Logger getLogger() {
		return logger;
	}

	public Class<?> getParamClazz() {
		return paramClazz;
	}

	public String getClazzname() {
		return clazzname;
	}

	public String getParamClazzName() {
		return paramClazzName;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getJndiName() {
		return jndiName;
	}

	public static InitialContext getIc() {
		return ic;
	}

	public static String getDEFAULT_MESSAGE() {
		return DEFAULT_MESSAGE;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void setParamClazz(Class<?> paramClazz) {
		this.paramClazz = paramClazz;
	}

	public void setClazzname(String clazzname) {
		this.clazzname = clazzname;
	}

	public void setParamClazzName(String paramClazzName) {
		this.paramClazzName = paramClazzName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public static void setIc(InitialContext ic) {
		MDBReceiverAbstract.ic = ic;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public CommunicationManager<Message, MessageListener> getCommunicationManager() {
		return communicationManager;
	}

	public void setCommunicationManager(
			CommunicationManager<Message, MessageListener> communicationManager) {
		this.communicationManager = communicationManager;
	}

}
