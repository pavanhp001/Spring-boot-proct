package com.A.vm.util.converter.unmarshall;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.GDuration;
import org.apache.xmlbeans.GDurationBuilder;

import com.A.enums.AddressOwnershipEnum;
import com.A.enums.DwellingEnum;
import com.A.util.XmlUtil;
import com.A.V.beans.entity.AddressBean;
import com.A.vm.util.converter.DynamicBuilder;
import com.A.vm.util.converter.mapper.AddressMapper;
import com.A.xml.v4.AddressType;
import com.A.xml.v4.MailingAddressType;

/**
 * @author ebthomas
 *
 */
public final class UnmarshallAddressBean {
	private static final Logger logger = Logger
			.getLogger(UnmarshallAddressBean.class);

	/**
	 * convert Address Bean.
	 */
	private UnmarshallAddressBean() {
		super();
	}

	/**
	 * @param src
	 *            source
	 * @param isUpdateRequest
	 *            TODO
	 * @return destination
	 */
	public static AddressBean copy(final AddressType src,
			final AddressBean address, final UnmarshallValidationEnum level,
			boolean isUpdateRequest) {

		if (src == null) {
			return new AddressBean();
		}

		return copy(address, src, isUpdateRequest);
	}

	/**
	 * @param src
	 *            source
	 * @return destination
	 */
	public static AddressBean copy(final AddressType src,
			boolean isUpdateRequest) {
		final AddressBean address = new AddressBean();

		return copy(address, src, isUpdateRequest);
	}

	/**
	 * @param src
	 *            source
	 * @return destination
	 */
	public static AddressBean copy(final MailingAddressType src,
			boolean isUpdateRequest) {
		final AddressBean address = new AddressBean();

		return copy(address, src, isUpdateRequest);
	}

	/**
	 * @param src
	 *            source
	 * @return destination
	 */
	public static AddressBean copy(final AddressBean address,
			final MailingAddressType src, boolean isUpdateRequest) {
		if (src == null) {
			return null;
		}

		DynamicBuilder<MailingAddressType, AddressBean> builder = new DynamicBuilder<MailingAddressType, AddressBean>(
				null);

		try {
			builder.copyInstanceAttributes(src, address,
					AddressMapper.addressFields, isUpdateRequest);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(
					"unable.to.unmarshall.mailing.address");
		}

		return address;
	}



	/**
	 * @param src
	 *            source
	 * @return destination
	 */
	public static AddressBean copy(final AddressBean address,
			final AddressType src, boolean isUpdateRequest) {

		DynamicBuilder<AddressType, AddressBean> builder = new DynamicBuilder<AddressType, AddressBean>(
				null);

		try {
			builder.copyInstanceAttributes(src, address,
					AddressMapper.addressFields, isUpdateRequest);
			customCopy(builder, src, address, null, isUpdateRequest);
			changeCase(address);
			// Calendar cal = Calendar.getInstance();
			// address.setInEffect( cal );
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(
					"unable.to.unmarshall.addressType");
		}

		return address;
	}

	private static void changeCase(AddressBean address) {
		if(address.getStreetName() != null){
			address.setStreetName(address.getStreetName().toUpperCase());
		}
		if(address.getLine2() != null){
			address.setLine2(address.getLine2().toUpperCase());
		}

		if(address.getCity() != null){
			address.setCity(address.getCity().toUpperCase());
		}
		if(address.getStateOrProvince() != null){
			address.setStateOrProvince(address.getStateOrProvince().toUpperCase());
		}
	}

	private static void customCopy(
			DynamicBuilder<AddressType, AddressBean> builder,
			AddressType src, AddressBean address, Object level,
			boolean isUpdateRequest) {

		if (src == null)
			return;

		if (src.getDwellingType() != null) {
			address.setDwellingType(src.getDwellingType().toString());
		} else {
			address.setDwellingType("apartment");
		}

		if(src.getAddressOwnership() != null){
			address.setAddressOwnership( AddressOwnershipEnum.get( src.getAddressOwnership().toString() ) );
		}else{
			address.setAddressOwnership("rent");
		}
	}

	public static AddressBean copyAddressInfo(AddressType addressSrc,
			AddressBean dest, boolean isUpdateRequest) {

		if (addressSrc == null) {
			return dest;
		}
		return copyAddressDetails(dest, addressSrc, isUpdateRequest);
	}

	public static AddressBean copyAddressDetails(final AddressBean address,
			final AddressType src, boolean isUpdateRequest) {

		DynamicBuilder<AddressType, AddressBean> builder = new DynamicBuilder<AddressType, AddressBean>(
				null);

		try {
			builder.copyInstanceAttributes(src, address,
					AddressMapper.addressFields, isUpdateRequest);
			copyDwellingType(src, address);
			copyAddressOwnershipType( src, address );
			customCopy(address, src);
			changeCase(address);
		} catch (Exception e) {
			logger.error(
					"Exception thrown while copying address details from XML to AddressBean",
					e);
			e.printStackTrace();
			throw new IllegalArgumentException(
					"unable to unmarshall addressType");
		}

		return address;
	}

	private static void customCopy(final AddressBean address,
			final AddressType src) {
		/*if(!XmlUtil.isElementNull( src.newCursor(), "gasStartAt" ) && src.getGasStartAt() != null){
			address.setGasStartAt(src.getGasStartAt().getTime() );
		}else{
			address.setGasStartAt(null);
		}
		if(!XmlUtil.isElementNull( src.newCursor(), "electricityStartAt" ) && src.getElectricityStartAt() != null){
			address.setElectricityStartAt( src.getElectricityStartAt().getTime() );
		}else{
			address.setElectricityStartAt(null);
		}*/

//		String gasStartAt = UnmarshallUtil.INSTANCE.getValue(src.newCursor(), "gasStartAt");
//		if (gasStartAt != null && !gasStartAt.trim().equals("")) {
//			Calendar gasStartAtCal = UnmarshallUtil.INSTANCE.getDate(src, gasStartAt);
//			address.setGasStartAt(gasStartAtCal.getTime());
//		} else {
//			address.setGasStartAt(null);
//		}

		if(src.getGasStartAt() != null){
			Calendar cal = src.getGasStartAt();
			address.setGasStartAt(cal.getTime());
		}

//		String electricityStartAt = UnmarshallUtil.INSTANCE.getValue(src.newCursor(), "electricityStartAt");
//		if (electricityStartAt != null && !electricityStartAt.trim().equals("")) {
//			Calendar electricityStartAtCal = UnmarshallUtil.INSTANCE.getDate(src, electricityStartAt);
//			address.setElectricityStartAt(electricityStartAtCal.getTime());
//		} else {
//			address.setElectricityStartAt(null);
//		}

		if(src.getElectricityStartAt() != null){
			Calendar cal = src.getElectricityStartAt();
			address.setElectricityStartAt(cal.getTime());
		}

		if(src.getHasHadServicePreviously() ){
			address.setHasHadServicePreviously(src.getHasHadServicePreviously());
		}

		if(src.getHowLongAtAddress() != null){
			GDuration gd =  src.getHowLongAtAddress() ;
			address.setHowLongAtAddress(gd.toString());
		} else {
			address.setHowLongAtAddress("PT0S");
		}


		if(src.getInEffect() != null){
			address.setInEffect(src.getInEffect());
		}else {
		    address.setInEffect(Calendar.getInstance());
		}

		if(src.getExpiration() != null){
			address.setExpiration(src.getExpiration());
		}

		if(src.getProviderExternalId() != null) {
             address.setProviderExternalId(src.getProviderExternalId());
		}

		if(src.getCityAlias() != null){
			address.setCityAlias(src.getCityAlias());
		}
	}

	private static String copyDuration(GDuration howLongAtAddress) {
		String inputDuration = howLongAtAddress.toString().toUpperCase();
		GDurationBuilder gdBldr = new GDurationBuilder(inputDuration);
		if(gdBldr.isValid()){
			return inputDuration;
		}else{
			throw new IllegalArgumentException("Invalid value for field 'HowLongAtAddress'!! Sample values P5Y, P5Y2M10D");
		}

	}

	private static void copyDwellingType(AddressType src, AddressBean address) {
		logger.debug("Copying Dwelling Type");
		if (src == null)
			return;

		if (src.getDwellingType() != null ) {
			address.setDwellingType(src.getDwellingType().toString().toLowerCase());
		}
	}

	private static void copyAddressOwnershipType(AddressType src, AddressBean address) {
		logger.debug("Copying Address Ownership Type");
		if (src == null)
			return;

		if (src.getAddressOwnership() != null) {
			address.setAddressOwnership(AddressOwnershipEnum.get(src.getAddressOwnership()
					.toString().toLowerCase()));
		}
	}

}
