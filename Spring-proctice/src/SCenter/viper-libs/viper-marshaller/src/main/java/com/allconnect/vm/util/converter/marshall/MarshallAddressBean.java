package com.A.vm.util.converter.marshall;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.GDate;
import org.apache.xmlbeans.GDuration;
import org.apache.xmlbeans.GDurationBuilder;
import com.A.V.beans.entity.AddressBean;
import com.A.xml.v4.AddressType;
import com.A.xml.v4.DwellingType;
import com.A.xml.v4.OwnershipType;
import com.A.xml.v4.CustAddress.AddressRoles;
import com.A.xml.v4.RoleType;

/**
 * @author ebthomas
 * 
 */
public final class MarshallAddressBean {

	private static final Logger logger = Logger
			.getLogger(MarshallAddressBean.class);

	/**
	 * Marshall Address.
	 */
	private MarshallAddressBean() {

	}

	/**
	 * @param src
	 *            A address bean
	 * @return xml bean address bean
	 */

	public static AddressType copyAddress(final AddressBean src,
			final AddressType address, final AddressRoles roles) {
		if (src != null) {
			address.setPrefixDirectional(src.getPrefixDirectional());
			address.setStreetNumber(src.getStreetNumber());
			address.setStreetName(src.getStreetName());

			address.setStreetType(src.getStreetType());
			address.setLine2(src.getLine2());
			address.setPostfixDirectional(src.getPostfixDirectional());
			address.setCity(src.getCity());
			address.setPostalCode(src.getPostalCode());
			address.setCountry(src.getCountry());
			address.setCountyParishBorough(src.getCountyParishBorough());
			address.setAddressBlock(src.getInputAddress());
			address.setStateOrProvince(src.getStateOrProvince());
			address.setExternalId(src.getExternalId().longValue());
			address.setInEffect(src.getInEffect());
			address.setChangeType(src.getChangeType());
			address.setCityAlias(src.getCityAlias());
			address.setProviderExternalId(src.getProviderExternalId());
			address.setExpiration(src.getExpiration());
			address.setHasHadServicePreviously(src.getHasHadServicePreviously() == null ? false
					: src.getHasHadServicePreviously());
			
			GDuration  gDuration = buildGDurationHowLongAtAddress(src);
			
			address.setHowLongAtAddress(gDuration);
			
			if (src.getDwellingType() != null) {
				address.setDwellingType(DwellingType.Enum
						.forString(
						src.getDwellingType().toLowerCase()));
			}
			if (src.getAddressOwnership() != null) {
				address.setAddressOwnership(OwnershipType.Enum
						.forString(src.getAddressOwnership().toLowerCase()));
			}
			Calendar gasCal = Calendar.getInstance();
			if (src.getGasStartAt() != null) {
				gasCal.setTime(src.getGasStartAt());
				address.setGasStartAt(gasCal);
			} else {
				address.setGasStartAt(null);
			}

			Calendar eleCal = Calendar.getInstance();
			if (src.getElectricityStartAt() != null) {
				eleCal.setTime(src.getElectricityStartAt());
				address.setElectricityStartAt(eleCal);
			} else {
				address.setElectricityStartAt(null);
			}
			if (src.getAddressOwnership() != null)
				address.setAddressOwnership(OwnershipType.Enum
						.forString(src.getAddressOwnership().toLowerCase()));

			if (roles != null) {
				List<String> addressRoles = src.getAddressRoles();

				for (String role : addressRoles) {

					if ((role != null) && (role.length() > 1)) {
						String firstLetter = role.substring(0, 1).toUpperCase();
						String rest = role.substring(1);
						RoleType.Enum rt = RoleType.Enum.forString(firstLetter
								+ rest);

						roles.addRole(rt);
					}

				}

			}
		}

		return address;
	}
	
	private static GDuration buildGDurationHowLongAtAddress(final AddressBean src) {
		 
		GDuration gdur = null;
		
		if (src.getHowLongAtAddress() != null
				&& src.getHowLongAtAddress().trim().length() > 0) {
				
			try {
				  gdur = new GDuration(src.getHowLongAtAddress().trim());
			} catch (Exception e) {
				 
				logger.warn("invalid long at address value");
			}
			 
		} else {
			  gdur = new GDuration("PT0S");
		}

		return gdur;
	}

	public static AddressType copyAddress(final AddressBean src) {
		AddressType address = AddressType.Factory.newInstance();
		AddressRoles roles = AddressRoles.Factory.newInstance();

		return copyAddress(src, address, roles);
	}
 
}
