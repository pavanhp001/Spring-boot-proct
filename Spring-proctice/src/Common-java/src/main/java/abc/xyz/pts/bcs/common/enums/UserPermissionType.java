/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.enums;

/**
 * Keep all permission constants in one place
 *
 * @author mmotlib-siddiqui
 *
 */
public enum UserPermissionType 
{
    // *************  DO NOT RE-ORDER THESE *************
    // They are presented to the screen in this order.
    // **************************************************
    
    APP_REPORT("APP_RPT_R"),                        			// Pre-Clearance - APP Reports
    APP_WRITE("APP_W"),                             			// Pre-Clearance - Government Override
	APP_READ("APP_R"),											// Pre-Clearance - View APP Screens
	REFERRAL_INTERVENE("REF_INT"),                  			// Risks - Own Referral
	REFERRAL_READ("RFL_R"),                         			// Risks - View Referrals (Qualified)
	REFERRAL_UNQUALIFIED_READ("REF_UNQ_R"),         			// Risks - View Unqualified Referrals
	REFERRAL_UNQUALIFIED_WRITE("REF_UNQ_W"),        			// Risks - Qualify Referrals
    REFERRAL_WRITE("RFL_W"),                        			// Risks - Edit Referrals (Qualified)
    TARGET_IMPORT("TRG_W"),                         			// Risks - Edit Watch List Targets
    TARGET_READ("TRG_R"),                           			// Risks - View Watch Lists Targets
    CLEARED_DOCUMENT_WRITE("CLRD_DOC_W"),           			// Risks - Cleared Documents Update
    TRAVELLER_DATA_READ("TRV_R"),                   			// Travel Data - Flights & Traveller Search
    DCS_READ("VDC_D"),                              			// Travel Data - View DCS Data
    FUZZY_NAME_SEARCH("FZ_SEARCH"),                 			// Fuzzy name search
    PNR_READ("PNR_READ"),                           			// Travel Data - View PNR Data
	CONFIG_READ("ADM_CFG_R"),									// Admin - View Configuration
	CONFIG_WRITE("ADM_CFG_W"),									// Admin - Edit Configuration
	USER_READ("ADM_USR_R"),                         			// Admin - View Users
	USER_WRITE("ADM_USR_W"),									// Admin - Edit Users
	USER_AUDIT_READ("AUDIT_USER_READ"),             			// Audit - View User Events
	SYSTEM_AUDIT_READ("AUDIT_SYSTEM_READ"),        				// Audit - View System Events
	CRYSTAL_REPORTS_VIEW("CR_V"),                   			// Reports - View Reports
    UPDATE_OWN_PROFILE("USR_OWN_PRF_W"),						// User - Update own profile/change pwd (also used for UI elements)
    WILDCARD_TRAVELLERS_SEARCH("WILDCARD_SEARCH")	// Wildcard Search Permission on Travellers Search Screen 
	;

    private String permission;

    private UserPermissionType(final String permission)
    {
        this.permission = permission;
    }

    public String getPermission()
    {
        return permission;
    }
}
