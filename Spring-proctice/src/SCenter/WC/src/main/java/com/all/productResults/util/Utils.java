/**
 * 
 */
package com.A.productResults.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.A.xml.pr.v4.FeatureType;

public class Utils {
	
	public static boolean isAlphabetic(String target){
		return target.matches("[a-zA-Z]*");
	}
	
	public static boolean isNumeric(String target) {  
        return target.matches("[-+]?\\d*\\.?\\d+");
    }
	
	public static boolean isValidZipCode(String target) {  
        return target.matches("^\\d{5}\\p{Punct}?\\s?(?:\\d{4})?$");
    }
	
	public static boolean isValidPhoneNumber(String target) {  
        return target.matches("(\\d-)?(\\d{3}-)?\\d{3}-\\d{4}");
    }
	
	public static boolean isValidEmail(String target) {  
	      return target.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
	}
	/**
     * Checks if string is <code>null</code> or empty.
     *
     * @param str String to check.
     * @return if string is <code>null</code> or empty, <code>false</code> otherwise.
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

/*	public static List<FeatureType> sort(List<FeatureType> list){
		Collections.sort(list, new Comparator<FeatureType>() {
			public int compare(FeatureType o1, FeatureType o2) {
				int vo1 = getFeatureTypeOrder(o1);
				int vo2 = getFeatureTypeOrder(o2);
				if (vo1 == vo2) {
					
				} else {
					return vo1 - vo2;
				}
				String type1 = null;
				if (o1.getType() != null) {
					type1 = o1.getType();
				}
				String type2 = null;
				if (o2.getType() != null) {
					type2 = o2.getType();
				}
				if (type1 == null && type2 == null) {
					return 0;
				} else if (type1 == null) {
					return -1;
				} else if (type2 == null) {
					return 1;
				} else {
					return type1.compareTo(type2);
				}
			}
		});
		return list;
	}
*/
	
	public static List<FeatureType> sort(List<FeatureType> list){
		Collections.sort(list, new Comparator<FeatureType>() {
			public int compare(FeatureType o1, FeatureType o2) {
/*				int vo1 = getFeatureTypeOrder(o1);
				int vo2 = getFeatureTypeOrder(o2);
				if (vo1 == vo2) {
					
				} else {
					return vo1 - vo2;
				}*/
				String tag1 = null;
				if (o1.getTags() != null) {
					tag1 = o1.getTags();
				}
				String tag2 = null;
				if (o2.getTags() != null) {
					tag2 = o2.getTags();
				}
				if (tag1 == null && tag2 == null) {
					return 0;
				} else if (tag1 == null) {
					return -1;
				} else if (tag2 == null) {
					return 1;
				} else {
					return tag1.compareTo(tag2);
				}
			}
		});
		return list;
	}
	
}
