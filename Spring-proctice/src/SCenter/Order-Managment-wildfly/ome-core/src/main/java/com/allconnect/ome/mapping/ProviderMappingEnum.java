package com.AL.ome.mapping;

public enum ProviderMappingEnum {

	ATTSTI("24699452"), VERIZON("4353598"), DISH_SYP("27010360"),DISH_HARMONY("18063259"), G2BCOMCAST("26069940"), COMCAST("26069942"),COMCASTSWIVEL("30698347"),CENTURYLINK("32416075"),
	FRONTIER("32937483"),MONITORONICS("32946482"),ADT("15498481"),VERIZON_COFEE("15498701");

	private String provider;

	ProviderMappingEnum(String id){
		this.provider = id;
	}

	public String getValue(){
		return this.provider;
	}
}


