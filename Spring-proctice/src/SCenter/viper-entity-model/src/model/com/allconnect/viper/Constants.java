/**
 *
 */
package com.A.V;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ebaugh
 *
 */
public final class Constants
{
    // Database / Field Max and Min Sizes.
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 10;

    public static final int USERNAME_MIN_LENGTH = 4;
    public static final int USERNAME_MAX_LENGTH = 15;

    public static final int PHONE_EXTENSION_MIN_LENGTH = 4;
    public static final int PHONE_EXTENSION_MAX_LENGTH = 4;

    public static final int NAME_MIN_LENGTH = 0;
    public static final int NAME_MAX_LENGTH = 100;


    // Allocation sizes.
    public static final int DEFAULT_LOG_BUFFER_SIZE = 2000;

    public static final int DEFAULT_MAX_RESULT_SIZE = 25;

    
    // AgentBean Statuses
    public static final int AGENT_WAITING = 1;
    public static final int AGENT_ON_CALL = 2;
    public static final int AGENT_ON_BREAK = 3;

    
    // Channels
    public static final int CHANNEL_CALL_CENTER                 = 0x000001;
    public static final int CHANNEL_WEB                         = 0x000002;
    public static final int CHANNEL_AGENT                       = 0x000004;
    public static final int CHANNEL_DIRECT_MARKETING            = 0x000008;
    
    public static final String CHANNEL_CALL_CENTER_STRING       = "callCenter";
    public static final String CHANNEL_WEB_STRING               = "web";
    public static final String CHANNEL_AGENT_STRING             = "agent";
    public static final String CHANNEL_DIRECT_MARKETING_STRING  = "directMarketing";

    /**
     *  Constants used by the various widgets.
     *  This class lists the label text for all the widgets.
     */
    public static final class Widgets
    {
        // Name Widget.
        public static final String NAME_PREFIX_LABEL        = "Prefix:";
        public static final String FIRST_NAME_LABEL         = "First Name:";
        public static final String LAST_NAME_LABEL          = "Last Name:";
        public static final String MIDDLE_NAME_LABEL        = "MI:";
        public static final String NAME_SUFFIX_LABEL        = "Suffix:";
        
        
        // Address Widget.
        public static final String SERVICE_LABEL = "Service Address";
        public static final String CITY_LABEL = "City:";
        public static final String STREET_LABEL = "Street Address:";
        public static final String APT_LABEL = "Apartment or Suite:";
        public static final String STATE_LABEL = "State:";
        public static final String ZIP_LABEL = "Zip Code:";
        
        // ZipCode Widget.
        public static final String ZIP_CODE_LABEL = "Zip Code:";
        public static final int ZIPCODE_MIN_LENGTH = 5;
       
        
        //Email Widget
        public static final String EMAIL_LABEL = "Email:";
        
        //Phone Number Widget
        public static final String PHONE_LABEL = "Phone Number:";
        public static final int PHONE_SIZE = 25;
        
        //SSN Widget
        public static final String SSN_LABEL = "SSN:";
        public static final int SSN_SIZE = 12;
        
        //Text Area Widget
        public static final String TEXT_AREA_LABEL = "Enter Text :";
        public static final int TEXT_AREA_ROW_SIZE = 5;
        public static final int TEXT_AREA_COL_SIZE = 30;
 
        //Text Line Widget
        public static final String TEXT_LINE_LABEL = "Enter Text :";
        public static final int TEXT_LINE_SIZE = 50;
        
        //Date Widget
        public static final int DATE_WIDTH = 570;
        
        
        //MoneyControl Widget
        
        public static final int MONEY_MAX_LENGTH = 25;
        public static final String MONEY_LABEL = "Money:";
        
        //LettersOnly Widget
        
        public static final int LETTERS_MAX_LENGTH = 25;
        public static final String LETTERS_LABEL = "LettersOnly:";
        
        //Move-In Date Widget
        
        public static final int DATE_MAX_LENGTH = 7;
        public static final String MOVEIN_DATE_LABEL = "Move-In Date:";
        
        //NumbersOnly Widget
        
        public static final int NUMBER_MAX_LENGTH = 25;
        public static final String NUMBERS_LABEL = "NumbersOnly:";
        
        /**
         * Default Constructor.
         */
        private Widgets()
        {
            // We do nothing special for the widget constructor currently.
        }
    }
    /**
     *  Default Constructor.
     */
    private Constants()
    {
        // We do nothing special for the constants constructor currently.
    }
    
    /**
     * Helper function to decode the Serviceability Channels bitmap integer. 
     * @param channels an bitmapped integer encoding the serviceable channels
     * @return the list of strings 
     */
    public static List<String> getChannelStrings( final int channels )
    {
        List<String> result = new ArrayList<String>();
        
        if ( ( channels & Constants.CHANNEL_WEB ) > 0 )
        { 
            result.add( Constants.CHANNEL_WEB_STRING );
        }
        
        if ( ( channels & Constants.CHANNEL_AGENT ) > 0 )
        { 
            result.add( Constants.CHANNEL_AGENT_STRING );
        }
        
        if ( ( channels & Constants.CHANNEL_DIRECT_MARKETING ) > 0 )
        { 
            result.add( Constants.CHANNEL_DIRECT_MARKETING_STRING );
        }
        
        if ( ( channels & Constants.CHANNEL_CALL_CENTER ) > 0 )
        { 
            result.add( Constants.CHANNEL_CALL_CENTER_STRING );
        }
        
        return result;
    }
    
}
