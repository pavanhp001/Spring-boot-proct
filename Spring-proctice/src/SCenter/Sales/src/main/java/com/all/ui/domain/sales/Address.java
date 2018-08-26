package com.AL.ui.domain.sales;

//import org.apache.commons.lang3.StringUtils;

public class Address {

    private String street;

    private String street2;

    private String city;

    private String state;

    private String zip;

    private String country;
    
    public String getCityState() {
    	return city + ", " + state; 
    }
   
    public String getStreet() {
        return this.street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getStreet2() {
        return this.street2;
    }
    
    public void setStreet2(String street2) {
        this.street2 = street2;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZip() {
        return this.zip;
    }
    
    public void setZip(String zip) {
        this.zip = zip;
    }
    
    public String getCountry() {
        return this.country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getStreetAddress() {
       String retval = getStreet();
   /*    if (StringUtils.isNotBlank(getStreet2())) {
          retval = retval + " " + getStreet2();
       }*/
       return retval;
    }
}
