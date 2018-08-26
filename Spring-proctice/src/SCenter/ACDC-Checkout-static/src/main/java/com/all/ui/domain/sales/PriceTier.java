package com.AL.ui.domain.sales;


public class PriceTier {
    private PriceInformation price;
    private int rangeStart;
    private Integer rangeEnd;
	public PriceInformation getPrice() {
		return price;
	}
	public void setPrice(PriceInformation price) {
		this.price = price;
	}
	public int getRangeStart() {
		return rangeStart;
	}
	public void setRangeStart(int rangeStart) {
		this.rangeStart = rangeStart;
	}
	public Integer getRangeEnd() {
		return rangeEnd;
	}
	public void setRangeEnd(Integer rangeEnd) {
		this.rangeEnd = rangeEnd;
	}
}
