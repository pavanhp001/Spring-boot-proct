package com.A.vm.util.converter.unmarshall;

import org.apache.log4j.Logger;

import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.PhoneContactChannel;
import com.A.xml.v4.CustomerType;

/**
 * @author ebthomas
 *
 */
public final class UnmarshallConsumerContactInfo
{

	private static final String BEST_EMAIL_CONTACT = "bestEmailContact";
	private static final Logger logger = Logger.getLogger( UnmarshallConsumerContactInfo.class );
	private static final String HOME_PHONE = "homePhoneNumber";
	private static final String CELL_PHONE = "cellPhoneNumber";
	private static final String WORK_PHONE = "workPhoneNumber";
	private static final String HOME_EMAIL = "homeEMail";
	private static final String WORK_EMAIL = "workEMail";

	/**
	 * @param src
	 *            source data
	 * @param dest
	 *            destination data
	 * @param level
	 *            validation level
	 * @param isUpdateRequest
	 *            TODO
	 */
	public static void copyConsumerContactInfo( final CustomerType src, final Consumer dest, boolean isUpdateRequest )
	{
		if ( ( dest == null ) || ( src == null ) )
		{
			return;
		}

		// Home Phone
		if ( isUpdateRequest )
		{
			if ( src.getHomePhoneNumber() != null ){
				dest.setHomePhone( UnmarshallContactItem.getPhoneValue( dest.getHomePhone(), src.getHomePhoneNumber(),
						isUpdateRequest ) );
			}
		}
		else
			dest.setHomePhone( UnmarshallContactItem.getPhoneValue( src.getHomePhoneNumber(), isUpdateRequest ) );

		// Cell Phone
		if ( isUpdateRequest )
		{
			if ( src.getCellPhoneNumber() != null ){
				dest.setCellPhone( UnmarshallContactItem.getPhoneValue( dest.getCellPhone(), src.getCellPhoneNumber(),
						isUpdateRequest ) );
			}
		}
		else
			dest.setCellPhone( UnmarshallContactItem.getPhoneValue( src.getCellPhoneNumber(), isUpdateRequest ) );

		// Work Phone
		if ( isUpdateRequest )
		{
			if ( src.getWorkPhoneNumber() != null ){
				dest.setWorkPhone( UnmarshallContactItem.getPhoneValue( dest.getWorkPhone(), src.getWorkPhoneNumber(),
						isUpdateRequest ) );
			}
			
			dest.setWorkPhoneExtn( src.getWorkPhoneNumberExtn() );
		}
		else
			dest.setWorkPhone( UnmarshallContactItem.getPhoneValue( src.getWorkPhoneNumber(), isUpdateRequest ) );

		// Home Email
		if ( isUpdateRequest )
		{
			if ( src.getHomeEMail() != null ){
				dest.setHomeEMail( UnmarshallContactItem.getEmailValue( dest.getHomeEMail(), src.getHomeEMail() ) );
			}
		}
		else
			dest.setHomeEMail( UnmarshallContactItem.getEmailValue( src.getHomeEMail() ) );

		// Work Email
		if ( isUpdateRequest )
		{
			if ( src.getWorkEMail() != null ){
				dest.setWorkEMail( UnmarshallContactItem.getEmailValue( dest.getWorkEMail(), src.getWorkEMail() ) );
			}
		}
		else
			dest.setWorkEMail( UnmarshallContactItem.getEmailValue( src.getWorkEMail() ) );


		if(src.getBestEmailContact() != null){
			dest.setBestEmailContact(src.getBestEmailContact());
		}

		if(src.getBestPhoneContact() != null && src.getBestPhoneContact().trim().length() > 0){
			dest.setBestPhoneContact(replacePhoneChars(src.getBestPhoneContact()));
		}

		if(src.getSecondPhone() != null && src.getSecondPhone().trim().length() > 0){
			dest.setSecondPhone(replacePhoneChars(src.getSecondPhone()));
		}

		processPhoneNumebrs(dest);

	}


	private static void processPhoneNumebrs(Consumer dest){
		if (dest.getHomePhone() != null && dest.getHomePhone().getValue() != null) {
			//Replace extra charactes for phone
			PhoneContactChannel hpc = dest.getHomePhone();
			hpc.setValue(replacePhoneChars(hpc.getValue()));
			dest.setHomePhone(hpc);
		}

		if (dest.getCellPhone() != null && dest.getCellPhone().getValue() != null) {
			PhoneContactChannel cpc = dest.getCellPhone();
			cpc.setValue(replacePhoneChars(cpc.getValue()));
			dest.setCellPhone(cpc);
		}

		if (dest.getWorkPhone() != null && dest.getWorkPhone().getValue() != null) {
			PhoneContactChannel wpc = dest.getWorkPhone();
			wpc.setValue(replacePhoneChars(wpc.getValue()));
			dest.setWorkPhone(wpc);
		}
	}

	/**
	 * Replaces any characters other than numeric values
	 * @param phone
	 * @return
	 */
	private static String replacePhoneChars(String phone) {
	    String p = phone;

	    p = phone.replaceAll("[\\D]", "");
	    return p;
	}

}
