package com.AL.ui.util;

import java.text.DecimalFormat;

import com.AL.xml.pr.v4.ChoiceType;

public class PriceBuilder {

	private static final DecimalFormat twoDForm = new DecimalFormat("#.##");
	private static final String DEFAULT_CURRENCY_UNIT = "$";
	private static final String DEFAULT_NO_PRICE = "Free";
	private static final String DEFAULT_RECURRING_LABEL = "monthly:";
	private static final String DEFAULT_NON_RECURRING_LABEL = "install:";
	private static final double DEFAULT_PRICE = 0d;

	public static String getPriceLabel(ChoiceType choice) {
		// TODO: Check for null

		String finalPriceLabel = "";

		double iPrice = choice.getPriceInfo().getBaseNonRecurringPrice();
		double mPrice = choice.getPriceInfo().getBaseRecurringPrice();

		String iPriceU = choice.getPriceInfo().getBaseNonRecurringPriceUnits() != null ? choice
				.getPriceInfo().getBaseNonRecurringPriceUnits() : DEFAULT_CURRENCY_UNIT;
		String mPriceU = choice.getPriceInfo().getBaseRecurringPriceUnits() != null ? choice
				.getPriceInfo().getBaseRecurringPriceUnits() : DEFAULT_CURRENCY_UNIT;

		if ((iPrice == 0) && (mPrice == 0)) {
			return DEFAULT_NO_PRICE;
		}

		if (iPrice > 0) {
			finalPriceLabel = DEFAULT_NON_RECURRING_LABEL + twoDForm.format(iPrice) + iPriceU;
		}

		if (mPrice > 0) {
			finalPriceLabel = finalPriceLabel + DEFAULT_RECURRING_LABEL+ twoDForm.format(mPrice) + mPriceU;
		}

		return finalPriceLabel;

	}

	public static String getPriceLabel(com.AL.xml.v4.ChoiceType choice) {
		// TODO: Check for null

		String finalPriceLabel = "";
		
		double iPrice = choice.getPriceInfo().getBaseNonRecurringPrice() != null ? choice
				.getPriceInfo().getBaseNonRecurringPrice() : DEFAULT_PRICE;
		double mPrice = choice.getPriceInfo().getBaseRecurringPrice() != null ? choice
				.getPriceInfo().getBaseRecurringPrice() : DEFAULT_PRICE;

		String iPriceU = choice.getPriceInfo().getBaseNonRecurringPriceUnits() != null ? choice
				.getPriceInfo().getBaseNonRecurringPriceUnits() : DEFAULT_CURRENCY_UNIT;
		String mPriceU = choice.getPriceInfo().getBaseRecurringPriceUnits() != null ? choice
				.getPriceInfo().getBaseRecurringPriceUnits() : DEFAULT_CURRENCY_UNIT;

		if ((iPrice == 0) && (mPrice == 0)) {
			return DEFAULT_NO_PRICE;
		}

		if (iPrice > 0) {
			finalPriceLabel = DEFAULT_NON_RECURRING_LABEL + twoDForm.format(iPrice) + iPriceU;
		}

		if (mPrice > 0) {
			finalPriceLabel = finalPriceLabel + DEFAULT_RECURRING_LABEL+ twoDForm.format(mPrice) + mPriceU;
		}

		return finalPriceLabel;

	}
}
