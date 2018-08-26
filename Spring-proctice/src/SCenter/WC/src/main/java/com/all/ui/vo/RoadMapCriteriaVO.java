package com.A.ui.vo;


public class RoadMapCriteriaVO {
 
	/**
	 * 
	 */
	private String internetProviderName;
	private String videoProviderName;
	private float withPhonePoints;
	private float withoutPhonePoints;
	private double connectionSpeedVal;
	
	
	public double getConnectionSpeedVal() {
		return connectionSpeedVal;
	}

	public void setConnectionSpeedVal(double connectionSpeedVal) {
		this.connectionSpeedVal = connectionSpeedVal;
	}
	
	@Override
	public String toString() {
		return "RoadMapCriteriaVO [internetProviderName="
				+ internetProviderName + ", videoProviderName="
				+ connectionSpeedVal + ", connectionSpeedVal="
				+ videoProviderName + ", withPhonePoints=" + withPhonePoints + ", withoutPhonePoints=" + withoutPhonePoints+ "]";
	}

	public String getInternetProviderName() {
		return internetProviderName;
	}

	public void setInternetProviderName(String internetProviderName) {
		this.internetProviderName = internetProviderName;
	}

	public String getVideoProviderName() {
		return videoProviderName;
	}

	public void setVideoProviderName(String videoProviderName) {
		this.videoProviderName = videoProviderName;
	}

	public float getWithPhonePoints() {
		return withPhonePoints;
	}

	public void setWithPhonePoints(float withPhonePoints) {
		this.withPhonePoints = withPhonePoints;
	}

	public float getWithoutPhonePoints() {
		return withoutPhonePoints;
	}

	public void setWithoutPhonePoints(float withoutPhonePoints) {
		this.withoutPhonePoints = withoutPhonePoints;
	}

	public RoadMapCriteriaVO() {
	}
	
	
}
