package com.A.vm.util.converter.mapper;

import java.util.HashMap;
import java.util.Map;

public class CustomerDemographicInfoMapper {
	 public static final Map<String, String> contact = new HashMap<String, String>();

	    static
	    {
	        contact.put( "Title", "Title" );
	        contact.put( "FirstName", "FirstName" );
	        contact.put( "LastName", "LastName" );
	        contact.put( "MiddleName", "MiddleName" );
	        contact.put( "NameSuffix", "NameSuffix" );
	        contact.put( "Dob", "Dob" ); 
	        contact.put( "Ssn", "Ssn" );
	        contact.put( "ACustomerNumber", "ACustomerNumber" );
	        
	    }
}
