package com.AL.ui.domain;


public class MDUProperty{

		private String zip;

	    private String apt;

	    private String name;

	    private String state;

	    private String address1;

	    private String address2;

	    private String ordersourceExternalId;

	    private String externalId;

	    private String city;

	    private String mduPropertyId;

	    public String getZip ()
	    {
	        return zip;
	    }

	    public void setZip (String zip)
	    {
	        this.zip = zip;
	    }

	    public String getApt ()
	    {
	        return apt;
	    }

	    public void setApt (String apt)
	    {
	        this.apt = apt;
	    }

	    public String getName ()
	    {
	        return name;
	    }

	    public void setName (String name)
	    {
	        this.name = name;
	    }

	    public String getState ()
	    {
	        return state;
	    }

	    public void setState (String state)
	    {
	        this.state = state;
	    }

	    public String getAddress1 ()
	    {
	        return address1;
	    }

	    public void setAddress1 (String address1)
	    {
	        this.address1 = address1;
	    }

	    public String getAddress2 ()
	    {
	        return address2;
	    }

	    public void setAddress2 (String address2)
	    {
	        this.address2 = address2;
	    }

	    public String getOrdersourceExternalId ()
	    {
	        return ordersourceExternalId;
	    }

	    public void setOrdersourceExternalId (String ordersourceExternalId)
	    {
	        this.ordersourceExternalId = ordersourceExternalId;
	    }

	    public String getExternalId ()
	    {
	        return externalId;
	    }

	    public void setExternalId (String externalId)
	    {
	        this.externalId = externalId;
	    }

	    public String getCity ()
	    {
	        return city;
	    }

	    public void setCity (String city)
	    {
	        this.city = city;
	    }

	    public String getMduPropertyId ()
	    {
	        return mduPropertyId;
	    }

	    public void setMduPropertyId (String mduPropertyId)
	    {
	        this.mduPropertyId = mduPropertyId;
	    }

	    @Override
	    public String toString()
	    {
	        return "MDUProperty [zip = "+zip+", apt = "+apt+", name = "+name+", state = "+state+", address1 = "+address1+", address2 = "+address2+", ordersourceExternalId = "+ordersourceExternalId+", externalId = "+externalId+", city = "+city+", mduPropertyId = "+mduPropertyId+"]";
	    }

}
