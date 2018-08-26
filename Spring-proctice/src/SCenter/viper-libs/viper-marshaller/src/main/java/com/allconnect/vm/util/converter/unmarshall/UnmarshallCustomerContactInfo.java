package com.A.vm.util.converter.unmarshall;

//import com.A.xml.v4.CustomerType;

public class UnmarshallCustomerContactInfo {

    /*private static final Logger logger = Logger.getLogger(UnmarshallCustomerContactInfo.class);
    private static final String HOME_PHONE = "ac:homePhoneNumber";
    private static final String CELL_PHONE = "ac:cellPhoneNumber";
    private static final String WORK_PHONE = "ac:workPhoneNumber";
    private static final String HOME_EMAIL = "ac:homeEMail";
    private static final String WORK_EMAIL = "ac:workEMail";
    
    *//**
     * Method to copy contact details from XML source to Consumer Bean
     * @param src
     * @param dest
     * @param level
     * @param isUpdateRequest
     *//*
    public static void copyCustomerContactInfo( final CustomerType src, final ConsumerBean dest,
            final UnmarshallValidationEnum level, boolean isUpdateRequest )
    {
	logger.info("copyCustomerContactInfo()...");
        if ( ( dest == null ) || ( src == null ) )
        {
            return;
        }

        //Home Phone
        if(isUpdateRequest){
            if(!XmlUtil.isElementNull(src.newCursor(), HOME_PHONE)){
        	dest.setHomePhone( UnmarshallContactItem.getPhoneValue(dest.getHomePhone(), src.getHomePhoneNumber(), level,isUpdateRequest ) );
            }else{
        	logger.info("HOME PHONE NUMBER ELEMENT SKIPPED BECAUSE IT IS NOT IN XML............");
            }
        }else{
            dest.setHomePhone( UnmarshallContactItem.getPhoneValue( src.getHomePhoneNumber(), level,isUpdateRequest ) );
        }
        
        //Cell Phone
        if(isUpdateRequest){
            if(!XmlUtil.isElementNull(src.newCursor(), CELL_PHONE)){
        	dest.setCellPhone( UnmarshallContactItem.getPhoneValue( dest.getCellPhone(),src.getCellPhoneNumber(), level,isUpdateRequest ) );
            }else{
        	logger.info("CELL PHONE NUMBER ELEMENT SKIPPED BECAUSE IT IS NOT IN XML............");
            }
        }else{
            dest.setCellPhone( UnmarshallContactItem.getPhoneValue( src.getCellPhoneNumber(), level,isUpdateRequest ) );
        }
        
        //Work Phone
        if(isUpdateRequest){
            if(!XmlUtil.isElementNull(src.newCursor(), WORK_PHONE)){
        	dest.setWorkPhone( UnmarshallContactItem.getPhoneValue( dest.getWorkPhone(),src.getWorkPhoneNumber(), level,isUpdateRequest ) );
            }else{
        	logger.info("WORK PHONE NUMBER ELEMENT SKIPPED BECAUSE IT IS NOT IN XML............");
            }
        }else{
            dest.setWorkPhone( UnmarshallContactItem.getPhoneValue( src.getWorkPhoneNumber(), level,isUpdateRequest ) );
        }

        //Home Email
        if(isUpdateRequest){
            if(!XmlUtil.isElementNull(src.newCursor(), HOME_EMAIL)){
        	dest.setHomeEMail( UnmarshallContactItem.getEmailValue(dest.getHomeEMail(), src.getHomeEMail(), level ) );
            }else{
        	logger.info("HOME EMAIL ELEMENT SKIPPED BECAUSE IT IS NOT IN XML............");
            }
        }else{
            dest.setHomeEMail( UnmarshallContactItem.getEmailValue( src.getHomeEMail(), level ) );
        }
        
        //Work Email
        if(isUpdateRequest){
            if(!XmlUtil.isElementNull(src.newCursor(), WORK_EMAIL)){
        	dest.setWorkEMail( UnmarshallContactItem.getEmailValue( dest.getWorkEMail(),src.getWorkEMail(), level ) );
            }else{
        	logger.info("WORK EMAIL ELEMENT SKIPPED BECAUSE IT IS NOT IN XML............");
            }
        }else{
            dest.setWorkEMail( UnmarshallContactItem.getEmailValue( src.getWorkEMail(), level ) );
        }

    }
*/
}
