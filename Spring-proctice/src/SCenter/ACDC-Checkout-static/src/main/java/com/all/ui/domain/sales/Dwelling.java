package com.AL.ui.domain.sales;

public class Dwelling {
	private String line1;
	private String line2;
	private String city;
	private String state;
	private String zip;
	private String zipExtension;
	private String dwellingType;
	
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getZipExtension() {
		return zipExtension;
	}

   public String getZipExtensionHyphenated() {
      if ((zipExtension != null) && (zipExtension.length() > 0)) {
         return "-" + getZipExtension();
      }
      return "";
   }
   
	public void setZipExtension(String zipExtension) {
		this.zipExtension = zipExtension;
	}
	public String getDwellingType() {
		return dwellingType;
	}
	public void setDwellingType(String dwellingType) {
		this.dwellingType = dwellingType;
	}

   public String getAddressBlock() {
      return getLine1() + "\n" + getLine2() + "\n" + getCity() + " " + getState() + " " + getZip() + getZipExtensionHyphenated();
   }
}
