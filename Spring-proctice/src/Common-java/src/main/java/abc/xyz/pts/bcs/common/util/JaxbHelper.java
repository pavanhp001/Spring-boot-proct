/*
 * This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2001-2009
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.impl.StackKeyedObjectPool;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

public class JaxbHelper {

    private static final Logger logger = Logger.getLogger( JaxbHelper.class );

    private static final ConcurrentMap< Class<?>, Future< JAXBContext > > classNameContextMap = new ConcurrentHashMap< Class<?>, Future< JAXBContext > >();
    private static final ConcurrentMap< String, Future< JAXBContext > > pathSpecContextMap = new ConcurrentHashMap< String, Future< JAXBContext > >();
        private static final int MAX_POOLED_OBJECTS = 100;

        /* Caches for marshallers, unmarshallers and introspectors.
         * Note that if an exception is thrown while using one of the objects,
         * the object should NOT be put back in the pool!
         */
        private static final KeyedObjectPool unmarshallerPool = new StackKeyedObjectPool(new BaseKeyedPoolableObjectFactory() {
            @Override
            public Unmarshaller makeObject(final Object key) throws Exception {
                logger.debug("Creating new unmarshaller for pool");
                return ((JAXBContext)key).createUnmarshaller();
            }

            @Override
            public void passivateObject(final Object key, final Object object) throws Exception {
                logger.debug("Passivating unmarshaller");
                final Unmarshaller unmarshaller = (Unmarshaller)object;
                unmarshaller.setEventHandler(null);
                unmarshaller.setSchema(null);
            }
        }, MAX_POOLED_OBJECTS);

        private static final KeyedObjectPool marshallerPool = new StackKeyedObjectPool(new BaseKeyedPoolableObjectFactory() {
             @Override
             public Marshaller makeObject(final Object key) throws Exception {
                 logger.debug("Creatng new marshaller for pool");
                 final Marshaller marshaller = ((JAXBContext)key).createMarshaller();
                 marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
                 return marshaller;
             }

             @Override
             public void passivateObject(final Object key, final Object object) throws Exception {
                 logger.debug("Passivating marshaller");
                 final Marshaller marshaller = (Marshaller)object;
                 marshaller.setProperty( Marshaller.JAXB_FRAGMENT, Boolean.TRUE );
                 marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
                 marshaller.setEventHandler(null);
                 marshaller.setSchema(null);
             }
        }, MAX_POOLED_OBJECTS);

        private static final KeyedObjectPool introspectorPool = new StackKeyedObjectPool(new BaseKeyedPoolableObjectFactory() {
              @Override
              public JAXBIntrospector makeObject(final Object key) throws Exception {
                  logger.debug("Creating new introspector for pool");
                  return ((JAXBContext)key).createJAXBIntrospector();
              }
        }, MAX_POOLED_OBJECTS);

    private JaxbHelper() {
    }

    public static JAXBContext getContext( final Object object) {
        return getContext( object.getClass() );
    }

    public static JAXBContext getContext( final Class< ? > clazz ) {

        Future< JAXBContext > jaxbTask = classNameContextMap.get( clazz );

        if( jaxbTask == null ) {
            final Callable< JAXBContext > callable = new Callable< JAXBContext >() {
                @Override
                public JAXBContext call() throws JAXBException {
                    return JAXBContext.newInstance( clazz );
                    // this throws JAXBException
                }
            };

            final FutureTask< JAXBContext > newTask = new FutureTask< JAXBContext >( callable );
            jaxbTask = classNameContextMap.putIfAbsent( clazz, newTask );
            if( jaxbTask == null ) {
                jaxbTask = newTask;
                newTask.run();
            }
        }

        try {
            return jaxbTask.get();
        }
        catch( final ExecutionException ex ) {
            logger.error( "Unable to create JAXB context for: " + clazz.getName(), ex );
            return null;
        }
        catch( final InterruptedException ex ) {
            logger.error( "Unable to create JAXB context for: " + clazz.getName(), ex );
            return null;
        }
    }


    // Uses Memoization pattern from JCIP
    public static JAXBContext getContext( final String pathSpec ) {
        Future< JAXBContext > jaxbTask = pathSpecContextMap.get( pathSpec );

        if( jaxbTask == null ) {
            final Callable< JAXBContext > callable = new Callable< JAXBContext >() {
                @Override
                public JAXBContext call() throws JAXBException {
                    return JAXBContext.newInstance( pathSpec, this.getClass().getClassLoader() );
                    // this throws JAXBException
                }
            };

            final FutureTask< JAXBContext > newTask = new FutureTask< JAXBContext >( callable );
            jaxbTask = pathSpecContextMap.putIfAbsent( pathSpec, newTask );
            if( jaxbTask == null ) {
                jaxbTask = newTask;
                newTask.run();
            }
        }

        try {
            return jaxbTask.get();
        }
        catch( final ExecutionException ex ) {
            logger.error( "Unable to create JAXB context for: " + pathSpec, ex );
            return null;
        }
        catch( final InterruptedException ex ) {
            logger.error( "Unable to create JAXB context for: " + pathSpec, ex );
            return null;
        }
    }


    public static < T > String serialise( final T thing ) {
        return serialise( JaxbHelper.getContext( thing ), thing, logger.isDebugEnabled() );
    }


    public static < T > String serialise( final JAXBContext jaxbContext, final T thing ) {
        return serialise( jaxbContext, thing, logger.isDebugEnabled() );
    }


    public static < T > String serialise( final T thing, final boolean format ) {
        return serialise( JaxbHelper.getContext( thing ), thing, format );
    }


    public static < T > String serialise( final JAXBContext jaxbContext, final T thing, final boolean format ) {
        final StringWriter stringWriter = new StringWriter();
        serialise( stringWriter, jaxbContext, thing, format );
        final String xmlString = stringWriter.toString();
        return xmlString;
    }


    public static < T > boolean serialise( final File file, final T thing ) {
        return serialise( file, JaxbHelper.getContext( thing ), thing, logger.isDebugEnabled() );
    }


    public static < T > boolean serialise( final File file, final JAXBContext jaxbContext, final T thing ) {
        return serialise( file, jaxbContext, thing, logger.isDebugEnabled() );
    }


    public static < T > boolean serialise( final File file, final T thing, final boolean format ) {
        return serialise( file, JaxbHelper.getContext( thing ), thing, format );
    }


    public static < T > boolean serialise( final File file, final JAXBContext jaxbContext, final T thing, final boolean format ) {
        OutputStream fileWriter = null;
        try {
            fileWriter = new FileOutputStream( file ); // Jaxb is optimised to use OutputStreams
        }
        catch( final IOException e ) {
            logger.error( "JAXBException! ", e );
            return false;
        }
        serialise( fileWriter, jaxbContext, thing, format );
        try {
            fileWriter.close();
        }
        catch( final IOException e ) {
            logger.error( "Error closing fileWriter! ", e );
            return false;
        }
        return true;
    }

        public static Unmarshaller getUnmarshaller(final Class<?> clazz) throws JAXBException {
            return getUnmarshaller(getContext(clazz));
        }

        public static Marshaller getMarshaller(final Class<?> clazz) throws JAXBException {
            return getMarshaller(getContext(clazz));
        }

        private static JAXBIntrospector getIntrospector(final JAXBContext context) {
            JAXBIntrospector jaxbIntrospector;

            logger.debug("Retrieving cached introspector");

            try {
                if(logger.isTraceEnabled()) {
                    logger.trace("Current introspector pool status: " + introspectorPool);
                }
                jaxbIntrospector = (JAXBIntrospector)introspectorPool.borrowObject(context);
            } catch (final Exception e) {
                logger.warn("Failed to obtain cached intropector, creating new one", e);
                jaxbIntrospector = context.createJAXBIntrospector();
            }

            return jaxbIntrospector;
        }

        private static Unmarshaller getUnmarshaller(final JAXBContext context) throws JAXBException {
            Unmarshaller unmarshaller;

            logger.debug("Retrieving cached unmarshaller");

            try {
                if(logger.isTraceEnabled()) {
                    logger.trace("Current unmarshaller pool status:" + unmarshallerPool);
                }
                unmarshaller = (Unmarshaller)unmarshallerPool.borrowObject(context);
            } catch (final Exception e) {
                logger.warn("Failed to obtain cached unmarshaller, creating new one", e);
                unmarshaller = context.createUnmarshaller();
            }

            return unmarshaller;
        }

        private static Marshaller getMarshaller(final JAXBContext context) throws JAXBException {
            Marshaller marshaller;

            logger.debug("Retrieving cached marshaller");

            try {
                if(logger.isTraceEnabled()) {
                    logger.trace("Current marshaller pool status:" + marshallerPool);
                }
                marshaller = (Marshaller)marshallerPool.borrowObject(context);
            } catch (final Exception e) {
                logger.warn("Failed to obtain cached marshaller, creating new one",e );
                marshaller = context.createMarshaller();
            }

            marshaller.setProperty( Marshaller.JAXB_FRAGMENT, Boolean.TRUE );
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
            return marshaller;
        }

        private static void returnMarshaller(final JAXBContext context, final Marshaller marshaller) {
            try {
                logger.debug("Returning marshaller to pool");
                marshallerPool.returnObject(context, marshaller);
            } catch (final Exception e) {
                logger.warn("Failed to return cached marshaller, discarding", e);
            }
        }

        private static void returnUnmarshaller(final JAXBContext context, final Unmarshaller unmarshaller) {
            try {
               logger.debug("Returning unmarshaller to pool");
               unmarshallerPool.returnObject(context, unmarshaller);
            } catch (final Exception e) {
                logger.warn("Failed to return cached unmarshaller, discarding", e);
            }
        }

        private static void returnIntrospector(final JAXBContext context, final JAXBIntrospector introspector) {
            try {
               logger.debug("Returning introspector to pool");
               introspectorPool.returnObject(context, introspector);
            } catch (final Exception e) {
                logger.warn("Failed to return cached unmarshaller, discarding", e);
            }
        }

        public static void returnUnmarshaller(final Class<?> clazz, final Unmarshaller unmarshaller) {
            returnUnmarshaller(getContext(clazz), unmarshaller);
        }

        public static void returnMarshaller(final Class<?> clazz, final Marshaller marshaller) {
            returnMarshaller(getContext(clazz), marshaller);
        }

        private static void invalidateMarshaller(final JAXBContext jaxbContext, final Marshaller m) {
            try{
               if( m != null) {
                    logger.debug("Invalidating marshaller object");
                    marshallerPool.invalidateObject(jaxbContext, m);
               }
            } catch(final Exception e) { // I know, I know, but that's the contract of invalidateObject!
                logger.error("Unable to invalidate marshaller from cache, just discarding", e);
            }
        }

        private static void invalidateUnmarshaller(final JAXBContext jaxbContext, final Unmarshaller um) {
            try{
               if( um != null) {
                    logger.debug("Invalidating unmarshaller object");
                    unmarshallerPool.invalidateObject(jaxbContext, um);
               }
            } catch(final Exception e) {
                logger.error("Unable to invalidate unmarshaller from cache, just discarding", e);
            }
        }

        public static void invalidateUnmarshaller(final Class<?> clazz, final Unmarshaller um) {
            invalidateUnmarshaller(getContext(clazz), um);
        }

        public static void invalidateMarshaller(final Class<?> clazz, final Marshaller m) {
            invalidateMarshaller(getContext(clazz), m);
        }

    public static < T > void serialise( final Writer writer, final JAXBContext jaxbContext, final T thing, final boolean format) {

            final JAXBIntrospector jaxbIntrospector = getIntrospector(jaxbContext);

        QName thingQName = null;
                final Class<T> clazz = (Class<T>)thing.getClass();

        if( jaxbIntrospector.isElement( thing ) ) {
            thingQName = jaxbIntrospector.getElementName( thing );
        }
        else {
            thingQName = new QName( clazz.getSimpleName() );
        }

        returnIntrospector(jaxbContext, jaxbIntrospector);

        Marshaller m = null;

        try {
            m = getMarshaller(jaxbContext);

            if( format ) {
                m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.valueOf(format) );
            }

            @SuppressWarnings( "unchecked" )
            final
            JAXBElement< T > jxe = new JAXBElement< T >( thingQName, clazz, thing );
            m.marshal( jxe, writer );
            returnMarshaller(jaxbContext, m);
        }
        catch( final JAXBException ex ) {
            logger.error( "JAXBException! ", ex );
            invalidateMarshaller(jaxbContext, m);
        }

    }

    public static < T > void serialise( final OutputStream output, final JAXBContext jaxbContext, final T thing, final boolean format) {
        final JAXBIntrospector jaxbIntrospector = getIntrospector(jaxbContext);

        QName thingQName = null;
        final Class<T> clazz = (Class<T>)thing.getClass();

        if( jaxbIntrospector.isElement( thing ) ) {
            thingQName = jaxbIntrospector.getElementName( thing );
        }
        else {
            thingQName = new QName( clazz.getSimpleName() );
        }
        returnIntrospector(jaxbContext, jaxbIntrospector);

        Marshaller m = null;

        try {
            m = getMarshaller(jaxbContext);
            if( format ) {
                m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.valueOf(format) );
            }

            @SuppressWarnings( "unchecked" )
            final
            JAXBElement< T > jxe = new JAXBElement< T >( thingQName, clazz, thing );
            m.marshal( jxe, output );

            returnMarshaller(jaxbContext, m);
        }
        catch( final JAXBException ex ) {
            logger.error( "JAXBException! ", ex );
                        invalidateMarshaller(jaxbContext, m);
        }
    }

    public static Object deserialise( final JAXBContext jaxbContext, final String input ) {

        final StringReader reader = new StringReader( input );
        Object o = null;
        Unmarshaller um = null;

        try {
            um = getUnmarshaller(jaxbContext);
            o = um.unmarshal( reader );
            returnUnmarshaller(jaxbContext, um);
        }
        catch( final JAXBException ex ) {
            logger.error( "JAXBException! ", ex );
            invalidateUnmarshaller(jaxbContext, um);
        }
        return o;
    }

    public static <T> T deserialise( final JAXBContext jaxbContext, final InputStream input, final Class<T> clazz ) {
        T t = null;
        Unmarshaller um = null;

        try {
            um = getUnmarshaller(jaxbContext);
            t = um.unmarshal( new StreamSource(input), clazz ).getValue();
            returnUnmarshaller(jaxbContext, um);
        }
        catch( final JAXBException ex ) {
            logger.error( "JAXBException! ", ex );
            invalidateUnmarshaller(jaxbContext, um);
        }

        return t;
    }

    public static Object deserialise( final JAXBContext jaxbContext, final File inputFile ) {
        Object o = null;
        Unmarshaller um = null;

        try {
            um = getUnmarshaller(jaxbContext);
            o = um.unmarshal( inputFile );
                        returnUnmarshaller(jaxbContext, um);
        }
        catch( final JAXBException ex ) {
            logger.error( "JAXBException! ", ex );
            invalidateUnmarshaller(jaxbContext, um);
        }

        return o;
    }

    public static < T > T deserialise( final String input, final Class< T > clazz ) {
        final StringReader reader = new StringReader( input );
                Unmarshaller um = null;

        try {
            um = getUnmarshaller(clazz);
            @SuppressWarnings( "unchecked" )
            final
            T unmarshal = ( T )um.unmarshal( reader );
            returnUnmarshaller(clazz, um);
            return unmarshal;
        }
        catch( final JAXBException ex ) {
            logger.error( "JAXBException! ", ex );
            invalidateUnmarshaller(clazz, um);
        }

        return null;
    }

    public static < T > T deserialise( final File inputFile, final Class< T > clazz ) {

        Unmarshaller um = null;

        try {
            um = getUnmarshaller(clazz);

            @SuppressWarnings( "unchecked" )
            final
            T unmarshal = ( T )um.unmarshal( inputFile );
            returnUnmarshaller(clazz, um);

            return unmarshal;
        }
        catch( final JAXBException ex ) {
            logger.error( "JAXBException! ", ex );
            invalidateUnmarshaller(clazz, um);
        }
        return null;
    }


    public static < T > T deserialise( final InputSource source, final Class< T > clazz ) {

                Unmarshaller um = null;

        try {
            um = getUnmarshaller(clazz);
            @SuppressWarnings( "unchecked" )
            final
            T unmarshal = ( T )um.unmarshal( source );
            returnUnmarshaller(clazz, um);
            return unmarshal;
        }
        catch( final JAXBException ex ) {
            logger.error( "JAXBException! ", ex );
            invalidateUnmarshaller(clazz, um);
        }
        return null;
    }


    public static < T > T deserialise( final InputStream source, final Class< T > clazz ) {

        Unmarshaller um = null;

        try {
            um = getUnmarshaller(clazz);
            @SuppressWarnings( "unchecked" )
            final
            T unmarshal = ( T )um.unmarshal( source );
            returnUnmarshaller(clazz, um);
            return unmarshal;
        }
        catch( final JAXBException ex ) {
            logger.error( "JAXBException! ", ex );
            invalidateUnmarshaller(clazz, um);
        }

        return null;
    }

}
