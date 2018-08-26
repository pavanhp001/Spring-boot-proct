package com.AL.ui.domain.sales;


public class PriceInformation {
    private Double baseNonRecurringPrice;
    private String baseNonRecurringPriceUnits;
    private Double baseRecurringPrice;
    private String baseRecurringPriceUnits;
    private Object includeInTotalPrice;
	public Double getBaseNonRecurringPrice() {
		return baseNonRecurringPrice;
	}
	public void setBaseNonRecurringPrice(Double baseNonRecurringPrice) {
		this.baseNonRecurringPrice = baseNonRecurringPrice;
	}
	public String getBaseNonRecurringPriceUnits() {
		return baseNonRecurringPriceUnits;
	}
	public void setBaseNonRecurringPriceUnits(String baseNonRecurringPriceUnits) {
		this.baseNonRecurringPriceUnits = baseNonRecurringPriceUnits;
	}
	public Double getBaseRecurringPrice() {
		return baseRecurringPrice;
	}
	public void setBaseRecurringPrice(Double baseRecurringPrice) {
		this.baseRecurringPrice = baseRecurringPrice;
	}
	public String getBaseRecurringPriceUnits() {
		return baseRecurringPriceUnits;
	}
	public void setBaseRecurringPriceUnits(String baseRecurringPriceUnits) {
		this.baseRecurringPriceUnits = baseRecurringPriceUnits;
	}
	public Object getIncludeInTotalPrice() {
		return includeInTotalPrice;
	}
	public void setIncludeInTotalPrice(Object includeInTotalPrice) {
		this.includeInTotalPrice = includeInTotalPrice;
	}
}
