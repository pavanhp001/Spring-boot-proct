package com.A.V.beans;


public enum OfferTypeEnum
{
    NEW(1), TRANSFER(2), UPGRADE(3);
    
    private int value;
    
    OfferTypeEnum(int value)
    {
    	this.value = value; 
    }
    
    public int getValue() {
        return value;
    }
    
    public static OfferTypeEnum getOfferType(int id) {
       for (OfferTypeEnum offerTypeEnum : OfferTypeEnum.values()) {
            if (offerTypeEnum.getValue() == id) {
                return offerTypeEnum;
            }
        }
        throw new IllegalArgumentException("No matching offerType for id " + id);
    }
    
    public static OfferTypeEnum getOfferType( final String offerTypeString ) 
    {
    	if( offerTypeString == null || "".equals(offerTypeString.trim()))
    	{
    		return OfferTypeEnum.NEW;
    	}
    	return OfferTypeEnum.valueOf(offerTypeString.toUpperCase());
     }
    
} 