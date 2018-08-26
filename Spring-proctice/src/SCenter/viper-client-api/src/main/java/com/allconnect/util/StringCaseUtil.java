package com.A.util;

public class StringCaseUtil {

	/**
	 * Converts a String to Camel Case
	 * 
	 * @param s
	 * @return String
	 */
	public static String toCamelCase(String str,boolean isForCustomerNames){
		StringBuilder camelCaseString = new StringBuilder();
		if(str != null && str.trim().length()>0){
			String[] parts = str.split(" ");

			for (String part : parts){
				if(part!=null && part.trim().length()>0){
					camelCaseString.append(toProperCase(part));
					camelCaseString.append(" ");
				}
			}
			
			
			/*
			 * This is for only customer names related code.
			 * if Names having Apostrophe(') or Hyphen(-) then next letter must be capital letter.
			 */
			if(isForCustomerNames){
				str = camelCaseString.substring(0, camelCaseString.length()-1);
				parts = str.split("'");
				if(parts.length>1){
					camelCaseString.delete(0, camelCaseString.length());
					for (String part : parts){
						if(part!=null && part.trim().length()>0){
							camelCaseString.append(afteToProperCase(part));
							camelCaseString.append("'");
						}
					}
				}
				
				str = camelCaseString.substring(0, camelCaseString.length()-1);
				parts = str.split("-");
				if(parts.length>1){
					camelCaseString.delete(0, camelCaseString.length());
					for (String part : parts){
						if(part!=null && part.trim().length()>0){
							camelCaseString.append(afteToProperCase(part));
							camelCaseString.append("-");
						}
					}
				}
			}
		}
		
		if(camelCaseString.length()>0)
			str = camelCaseString.substring(0, camelCaseString.length()-1);
		
		return str;
	}

	
	/**
	 * @param s
	 * @return String
	 */
	private static String toProperCase(String s) {
		String temp = null;
		if(s.length()>1){
			temp=s.substring(0, 1).toUpperCase()+s.substring(1).toLowerCase();
		}else{
			temp=s.toUpperCase();
		}
		return temp;
	}
	
	
	/**
	 * @param s
	 * @return String
	 */
	private static String afteToProperCase(String s) {
		String temp = null;
		if(s.length()>1){
			temp = s.substring(0,1).toUpperCase()+s.substring(1);
		}else{
			temp = s.toUpperCase();
		}
		return temp;
	}
	
	
	
}
