package com.AL.validation;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;
import org.hibernate.validator.HibernateValidator;
import org.springframework.stereotype.Component;

import com.AL.V.beans.entity.SalesOrder;

@Component
public class OMEValidator
{
	private Validator validator = null;
	private static final Logger logger = Logger.getLogger( OMEValidator.class );

	public Validator getValidator() throws FileNotFoundException
	{
		logger.debug( "Configuring Validator" );

		// Building default validator factory
		//ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

		// Getting the configuration so we can change attributes
		//Configuration<?> config = Validation.byDefaultProvider().configure();

		//System.out.println(Validation.class.getName());

		Configuration<?> config  = Validation.byProvider( HibernateValidator.class ).configure();

		InputStream in = null;
		logger.info( "Reading validation configuration file" );
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		in = classLoader.getResourceAsStream( "order-validation.xml" );
		config.addMapping( in );

		// Building the customized factory (along with the changed configuration)
		ValidatorFactory factory = config.buildValidatorFactory();

		Validator validator = factory.getValidator();
		return null;
	}

	public Set<ConstraintViolation<SalesOrder>> validate(SalesOrder salesOrder){
		Set<ConstraintViolation<SalesOrder>> violations = Collections.emptySet();
		//Disabling validation as no longer used
//		try
//		{
//			validator = getValidator();
//			if(validator != null){
//				violations = validator.validate( salesOrder );
//			}
//		}
//		catch ( FileNotFoundException e )
//		{
//			logger.error( "Exception thrown" , e);
//		}
		return violations;
	}
}
