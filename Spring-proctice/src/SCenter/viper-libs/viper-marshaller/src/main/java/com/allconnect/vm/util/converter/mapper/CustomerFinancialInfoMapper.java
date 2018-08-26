package com.A.vm.util.converter.mapper;

import java.util.HashMap;
import java.util.Map;

public class CustomerFinancialInfoMapper {
    public static final Map<String, String> financial = new HashMap<String, String>();
    public static final Map<String, String> employed = new HashMap<String, String>();

    static
    {
        financial.put( "BankOrMortgageInstitution", "BankOrMortgageInstitution" );
        financial.put( "OtherIncomeSource", "OtherIncomeSource" ); 
        financial.put( "Student", "Student" );
        financial.put( "Retired", "Retired" );
         
         
        //Employed
        employed.put( "BusinessName", "EmployerBusinessName" );
        employed.put( "BusinessPhoneNum", "EmployerPhoneNumber" );
        employed.put( "Occupation", "Occupation" );
        
         
         
    }
}
