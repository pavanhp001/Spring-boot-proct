package com.A.vm.util.converter.unmarshall;

import org.springframework.stereotype.Component;

import com.A.util.XmlUtil;
import com.A.V.beans.LineItemPriceInfo;
import com.A.V.beans.PriceInfo;
import com.A.xml.v4.PriceInfoType;

/**
 * @author ebthomas
 * 
 */
@Component
public class UnmarshallPriceInfo {

	/**
	 * @param priceInfoType
	 *            Source
	 * @return indicator if valid
	 */
	public Boolean validate(final PriceInfoType priceInfoType) {

		return Boolean.TRUE;
	}

	/**
	 * @param priceInfoType
	 *            Source
	 * @return Domain Object representing Price Info
	 */
	public PriceInfo build(final PriceInfoType priceInfoType) {
		if (validate(priceInfoType)) {
			PriceInfo priceInfo = new PriceInfo();
			priceInfo.setBaseNonRecurringPrice(priceInfoType.getBaseNonRecurringPrice());
			priceInfo.setBaseRecurringPrice(priceInfoType.getBaseRecurringPrice());
			
			priceInfo.setBaseNonRecurringPriceUnits(priceInfoType.getBaseNonRecurringPriceUnits());
			priceInfo.setBaseRecurringPriceUnits(priceInfoType.getBaseRecurringPriceUnits());
			 

			return priceInfo;
		}

		throw new IllegalArgumentException("unable to copy price info");
	}
	
	
	public PriceInfo build(final PriceInfoType priceInfoType,PriceInfo priceInfo) {
		if (validate(priceInfoType) && (priceInfoType != null)) {
			
			if (!XmlUtil.isElementNull(priceInfoType.newCursor(), "baseNonRecurringPrice")) {
				priceInfo.setBaseNonRecurringPrice(priceInfoType.getBaseNonRecurringPrice());
			}
			
			if (!XmlUtil.isElementNull(priceInfoType.newCursor(), "baseRecurringPrice")) {
				priceInfo.setBaseRecurringPrice(priceInfoType.getBaseRecurringPrice());
			}
			
			if (!XmlUtil.isElementNull(priceInfoType.newCursor(), "baseNonRecurringPriceUnits")) {
				priceInfo.setBaseNonRecurringPriceUnits(priceInfoType.getBaseNonRecurringPriceUnits());
			}
			
			if (!XmlUtil.isElementNull(priceInfoType.newCursor(), "baseRecurringPriceUnits")) {
				priceInfo.setBaseRecurringPriceUnits(priceInfoType.getBaseRecurringPriceUnits());
			}
			
		  
			
		}
		return priceInfo;

		 
	}

	
	public LineItemPriceInfo build(final PriceInfoType priceInfoType,LineItemPriceInfo priceInfo) {
		
		if (priceInfo == null) {
			priceInfo = new LineItemPriceInfo();
		}
		if (validate(priceInfoType) && (priceInfoType != null)) {
			
			if (!XmlUtil.isElementNull(priceInfoType.newCursor(), "baseNonRecurringPrice")) {
				priceInfo.setBaseNonRecurringPrice(priceInfoType.getBaseNonRecurringPrice());
			}
			
			if (!XmlUtil.isElementNull(priceInfoType.newCursor(), "baseRecurringPrice")) {
				priceInfo.setBaseRecurringPrice(priceInfoType.getBaseRecurringPrice());
			}
			
			if (!XmlUtil.isElementNull(priceInfoType.newCursor(), "baseNonRecurringPriceUnits")) {
				priceInfo.setBaseNonRecurringPriceUnits(priceInfoType.getBaseNonRecurringPriceUnits());
			}
			
			if (!XmlUtil.isElementNull(priceInfoType.newCursor(), "baseRecurringPriceUnits")) {
				priceInfo.setBaseRecurringPriceUnits(priceInfoType.getBaseRecurringPriceUnits());
			}
			
			boolean includeInTotalPrice = (XmlUtil.isElementPresent(priceInfoType
					.toString(), "includeInTotalPrice"));
			
			 priceInfo.setIncludePriceInTotal(includeInTotalPrice);
		 
			
			
		}
		return priceInfo;

		 
	}
}
