/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.admin.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ModelMap;

import abc.xyz.pts.bcs.admin.business.AdminService;
import abc.xyz.pts.bcs.admin.business.UserRoleService;
import abc.xyz.pts.bcs.admin.dto.Airport;
import abc.xyz.pts.bcs.common.ldap.dto.UserRoleData;


public class AdminUserCommonControllerTest extends TestCase {    
	 
    private final Mockery context = new JUnit4Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private AdminUserCommonController controller;
    private AdminService adminService;
    private UserRoleService userRoleService;

    List<UserRoleData> userRoleList = new ArrayList<UserRoleData>();
    List<Airport> validAirports = new ArrayList<Airport>();
    
    private static final String TEST_CODE = "TEST";
    private static final String ENGLISH_DESC = "ENGLISH";
    private static final String NOT_ENGLISH_DESC = "NOT_ENGLISH";
    
    private static final String IATA_CODE = "IATA_CODE";
    private static final String ICAO_CODE = "ICAO_CODE";
    private static final String AIRPORT_DESC = "AIRPORT";
    
    private static final String NON_ENGLISH_LANGUAGE = "ar";
    
   @Override
    public void setUp() throws Exception {

        this.controller = new AdminUserProfileController();

        // Create mock objects
        adminService = context.mock(AdminService.class);
        userRoleService = context.mock(UserRoleService.class);

        ReflectionTestUtils.setField(controller, "adminService", adminService);
        ReflectionTestUtils.setField(controller, "userRoleService", userRoleService);

        UserRoleData userRole = new UserRoleData();
        userRole.setCode(TEST_CODE);
        userRole.setDescriptionInEnglish(ENGLISH_DESC);
        userRole.setDescriptionInLang(NOT_ENGLISH_DESC);
        
        userRoleList.add(userRole);
        
        Airport airport = new Airport();
        airport.setIataCode(IATA_CODE);
        airport.setIcaoCode(ICAO_CODE);
        airport.setDescription(AIRPORT_DESC);
        
        validAirports.add(airport);
    }
   
   public void testReferenceEnglishRoleDescription() throws Exception{
	   ModelMap model = new ModelMap();

	   LocaleContextHolder.setLocale(Locale.ENGLISH);
       context.checking(new Expectations() {
		   {
			   one(userRoleService).findAllRoles();
			   will(returnValue(userRoleList));
			   one(adminService).getValidAirports() ;
               will(returnValue(validAirports));
		   }
	   });
	   controller.referenceData(model);
	   assertNotNull(model.get(AdminUserCommonController.ROLE_DESC_MAP));
	   assertEquals(userRoleList.get(0).getDescriptionInEnglish(), ((Map<String, String>)model.get(AdminUserCommonController.ROLE_DESC_MAP)).get(TEST_CODE));
   }

   public void testReferenceEnglishRoleDescriptionWithCountry() throws Exception{
       ModelMap model = new ModelMap();

       LocaleContextHolder.setLocale(Locale.UK);
       context.checking(new Expectations() {
           {
               one(userRoleService).findAllRoles();
               will(returnValue(userRoleList));
               one(adminService).getValidAirports() ;
               will(returnValue(validAirports));
           }
       });
       controller.referenceData(model);
       assertNotNull(model.get(AdminUserCommonController.ROLE_DESC_MAP));
       assertEquals(userRoleList.get(0).getDescriptionInEnglish(), ((Map<String, String>)model.get(AdminUserCommonController.ROLE_DESC_MAP)).get(TEST_CODE));
   }

   public void testReferenceEmptyRoleDescription() throws Exception{
       ModelMap model = new ModelMap();

       LocaleContextHolder.setLocale(Locale.ENGLISH);
       context.checking(new Expectations() {
           {
               one(userRoleService).findAllRoles();
               will(returnValue(new ArrayList<UserRoleData>()));
               one(adminService).getValidAirports() ;
               will(returnValue(validAirports));
           }
       });
       controller.referenceData(model);
       assertNotNull(model.get(AdminUserCommonController.ROLE_DESC_MAP));
       assertTrue(((Map<String, String>)model.get(AdminUserCommonController.ROLE_DESC_MAP)).isEmpty());
   }

   public void testReferenceNullRoleDescription() throws Exception{
       ModelMap model = new ModelMap();

       LocaleContextHolder.setLocale(Locale.ENGLISH);
       context.checking(new Expectations() {
           {
               one(userRoleService).findAllRoles();
               will(returnValue(null));
               one(adminService).getValidAirports() ;
               will(returnValue(validAirports));
           }
       });
       controller.referenceData(model);
       assertNotNull(model.get(AdminUserCommonController.ROLE_DESC_MAP));
       assertTrue(((Map<String, String>)model.get(AdminUserCommonController.ROLE_DESC_MAP)).isEmpty());
   }

   public void testReferenceNonEnglishRoleDescription() throws Exception{
       ModelMap model = new ModelMap();

       LocaleContextHolder.setLocale(new Locale(NON_ENGLISH_LANGUAGE));
       context.checking(new Expectations() {
           {
               one(userRoleService).findAllRoles();
               will(returnValue(userRoleList));
               one(adminService).getValidAirports() ;
               will(returnValue(validAirports));
           }
       });
       controller.referenceData(model);
       assertNotNull(model.get(AdminUserCommonController.ROLE_DESC_MAP));
       assertEquals(userRoleList.get(0).getDescriptionInLang(), ((Map<String, String>)model.get(AdminUserCommonController.ROLE_DESC_MAP)).get(TEST_CODE));
   }

   public void testReferenceValidAirports() throws Exception{
       ModelMap model = new ModelMap();

       LocaleContextHolder.setLocale(Locale.ENGLISH);
       context.checking(new Expectations() {
           {
               one(userRoleService).findAllRoles();
               will(returnValue(userRoleList));
               one(adminService).getValidAirports() ;
               will(returnValue(validAirports));
           }
       });
       controller.referenceData(model);
       assertNotNull(model.get(AdminUserCommonController.VALID_AIRPORTS));
       assertEquals(validAirports.get(0).getIataCode(), ((List<Airport>)model.get(AdminUserCommonController.VALID_AIRPORTS)).get(0).getIataCode());
   }

}
