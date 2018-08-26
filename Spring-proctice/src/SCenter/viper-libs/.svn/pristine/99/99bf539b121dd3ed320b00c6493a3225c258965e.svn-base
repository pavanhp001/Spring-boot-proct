package com.A.vm.util.converter.marshall;

import com.A.util.XmlUtil;
import com.A.V.beans.LineItemPriceInfo;
import com.A.V.beans.PriceInfo;
import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.SelectedFeatureValue;
import com.A.xml.v4.FeatureValueType;
import com.A.xml.v4.LineItemPriceInfoType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.PriceInfoType;

/**
 * @author ebthomas
 * 
 */
public final class MarshallPriceInfo {
	/**
	 * @author ebthomas
	 * 
	 */
	public static final class Builder {
		/**
		 * Builder for Marshall Price Info.
		 */
		private Builder() {
			super();
		}

		/**
		 * @param priceInfo
		 *            price information validation
		 * @return boolean value indicating valid price info value
		 */
		private static Boolean validate(final PriceInfo priceInfo) {
			return (priceInfo != null);
		}

		/**
		 * @param priceInfo
		 *            source to be converted
		 * @return XMLBean convertion destination
		 */
		public static PriceInfoType build(final PriceInfo priceInfo) {
			PriceInfoType priceInfoItem = PriceInfoType.Factory.newInstance();

			if (validate(priceInfo)) {
				priceInfoItem.setBaseNonRecurringPrice(priceInfo
						.getBaseNonRecurringPrice());
				priceInfoItem.setBaseRecurringPrice(priceInfo
						.getBaseRecurringPrice());
				priceInfoItem.setBaseNonRecurringPriceUnits(priceInfo
						.getBaseNonRecurringPriceUnits());
				priceInfoItem.setBaseRecurringPriceUnits(priceInfo
						.getBaseRecurringPriceUnits());
			}

			return priceInfoItem;
		}

		public static void copy(SelectedFeatureValue src,
				PriceInfoType priceInfoType) {

			if ((src != null) && (src.getPrice() != null)
					&& (priceInfoType != null)) {

				priceInfoType.setBaseNonRecurringPrice(src.getPrice()
						.getBaseNonRecurringPrice());
				priceInfoType.setBaseRecurringPrice(src.getPrice()
						.getBaseRecurringPrice());

				priceInfoType.setBaseNonRecurringPriceUnits(src.getPrice()
						.getBaseNonRecurringPriceUnits());
				priceInfoType.setBaseRecurringPriceUnits(src.getPrice()
						.getBaseRecurringPriceUnits());

				if ((src.getPrice().getIncludePriceInTotal() != null)
						&& (src.getPrice().getIncludePriceInTotal())) {
					priceInfoType.addNewIncludeInTotalPrice();
				}

			}

		}
	}

}
