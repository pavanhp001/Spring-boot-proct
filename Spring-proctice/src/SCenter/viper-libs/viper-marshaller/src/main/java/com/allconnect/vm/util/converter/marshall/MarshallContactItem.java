package com.A.vm.util.converter.marshall;

import com.A.V.beans.entity.EMailContactChannel;
import com.A.V.beans.entity.PhoneContactChannel;
import com.A.xml.v4.EMailAddressType;
import com.A.xml.v4.PhoneNumberType;

/**
 * @author ebthomas
 * 
 */
public final class MarshallContactItem {
	/**
	 * private constructor. static access only.
	 */
	private MarshallContactItem() {

	}

	
	
	
	
	public static PhoneNumberType getPhoneValue(
			final PhoneContactChannel number,
			final PhoneNumberType phoneNumberType) {
		if ((number != null)) {
			phoneNumberType.setDesc(number.getDescription());
			phoneNumberType.setValue(number.getValue());
			phoneNumberType.setOrder(number.getPreferenceOrder());
		}

		return phoneNumberType;
	}

	/**
	 * @param number
	 *            source
	 * @return destination
	 */
	public static PhoneNumberType getPhoneValue(
			final PhoneContactChannel number) {
		PhoneNumberType phoneNumberType = PhoneNumberType.Factory.newInstance();

		return getPhoneValue(number, phoneNumberType);
	}

	
	
	 
	
	
	/**
	 * @param addr
	 *            source
	 * @return destination
	 */
	public static EMailAddressType getEmailValue(
			final PhoneContactChannel phone,
			final EMailAddressType eMailAddressType) {
		if ((phone != null)) {

			eMailAddressType.setValue(phone.getValue());
			eMailAddressType.setDesc(phone.getDescription());
			eMailAddressType.setOrder(phone.getPreferenceOrder());

			return eMailAddressType;
		}

		return eMailAddressType;
	}

	
	
	/**
	 * @param addr
	 *            source
	 * @return destination
	 */
	public static EMailAddressType getEmailValue(
			final EMailContactChannel  addr,
			final EMailAddressType eMailAddressType) {
		if ((addr != null)) {

			eMailAddressType.setValue(addr.getValue());
			eMailAddressType.setDesc(addr.getDescription());
			eMailAddressType.setOrder(addr.getPreferenceOrder());

			return eMailAddressType;
		}

		return eMailAddressType;
	}

	/**
	 * @param addr
	 *            source
	 * @return destination
	 */
	public static EMailAddressType getEmailValue(
			final EMailContactChannel  addr) {
		EMailAddressType eMailAddressType = EMailAddressType.Factory
				.newInstance();

		return getEmailValue(addr, eMailAddressType);
	}
}
