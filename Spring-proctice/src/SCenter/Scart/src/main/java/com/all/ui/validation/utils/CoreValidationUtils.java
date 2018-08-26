package com.AL.ui.validation.utils;

import org.apache.cxf.common.util.StringUtils;

public enum CoreValidationUtils {
	
	INSTANCE;
	
	public   boolean isAlphabetic(String target){
		return target.matches("[a-zA-Z]*");
	}
	
	public   boolean isNumeric(String target) {  
        return target.matches("[-+]?\\d*\\.?\\d+");
    }
	
	public   boolean isValidZipCode(String target) {  
        return target.matches("^\\d{5}\\p{Punct}?\\s?(?:\\d{4})?$");
    }
	
	public   boolean isValidPhoneNumber(String target) {  
        return target.matches("(\\d-)?(\\d{3}-)?\\d{3}-\\d{4}");
    }
	
	public   boolean isValidEmail(String target) {  
	      return target.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
	}

	
	public boolean validateIfPresent(String target) {
		return StringUtils.isEmpty(target);
	}
}
