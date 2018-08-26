/**
 * 
 */
package com.AL.ui.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import com.AL.ui.constants.Constants;
import com.AL.ui.domain.SessionKeys;
import com.AL.ui.service.V.impl.DialogCacheService;
import com.AL.ui.service.V.impl.OrderCacheService;
import com.AL.ui.service.V.impl.ProductCacheService;
import com.AL.ui.vo.CKOInitialVo;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.SessionVO;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.pr.v4.ProductInfoType;

/**
 * @author rkiran
 *
 */
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
	public static boolean isValidNumber(String number) {

		try {

			Long.parseLong(number);

		} catch (NumberFormatException ex) {
			return false;
		}

		return true;
	}

	public static String translateDialogue(String dataFieldText,String businessPartyName) {
		if(dataFieldText != null && dataFieldText.contains("{businessParty.name}") == true){
			dataFieldText = dataFieldText.replace("{businessParty.name}", businessPartyName);
		}
		return dataFieldText;
	}

	public static String convertToFormat(String date){

		final String OLD_FORMAT = "MM/dd/yyyy";
		final String NEW_FORMAT = "yyyy-MM-dd";
		String newDateString = null;
		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);

		try{

			Date d = sdf.parse(date);
			sdf.applyPattern(NEW_FORMAT);
			newDateString = sdf.format(d);

		}catch (ParseException e) {
		}
		return newDateString;

	}

	public static int sortArray(String[] array){
		int max = 0;
		for(String str : array){
			int integerValue = Integer.valueOf(str);
			if(max < integerValue){
				max = integerValue;
			}
		}
		return max;
	}

	public static int sortArray(Set<String> array){
		int max = 0;
		for(String str : array){
			int integerValue = Integer.valueOf(str);
			if(max < integerValue){
				max = integerValue;
			}
		}
		return max;
	}

	public static boolean isValidSession(SessionVO sessionVO, String CKOVOName) {
		if(sessionVO != null && sessionVO.get(CKOVOName) != null){
			return true;
		}
		else{
			return false;	
		}
	}

	public static String changeDateFormat(String oldFormat, String newFormat, String formatedOldDate){
		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
		Date d;
		String formatedNewDate = "";
		try {
			d = sdf.parse(formatedOldDate);
			sdf.applyPattern(newFormat);
			formatedNewDate = sdf.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatedNewDate;
	}	

	public static String buildCalender()
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -18);
		String hiddenDate = cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR);
		return hiddenDate;
	}

	public static void removeNull(List<String> dialogueFeatureExternalIDList){
		dialogueFeatureExternalIDList.removeAll(Collections.singleton(null));
	}

	public static boolean isEmpty(List<?> checkList){
		return checkList == null || checkList.isEmpty();
	}

	public static boolean listContainsString(List<String> searchList, String findString){
		boolean containsString = false;
		for(String obj : searchList){
			if(obj.equalsIgnoreCase(findString)){
				containsString = true;
				return containsString;
			}
		}
		return false;
	}

	public static void clearCachedData(OrderQualVO orderQualVO){
		if(OrderCacheService.INSTANCE.get(orderQualVO.getOrderId()) != null){
			OrderCacheService.INSTANCE.clear(orderQualVO.getOrderId());
		}
		if(ProductCacheService.INSTANCE.get(orderQualVO.getProviderExternalId(), orderQualVO.getProductExternalId()) != null){
			ProductCacheService.INSTANCE.clear(orderQualVO.getProviderExternalId(), orderQualVO.getProductExternalId());
		}

		if(DialogCacheService.INSTANCE.getFromCache(orderQualVO.getProductExternalId()) != null){
			DialogCacheService.INSTANCE.clearCached(orderQualVO.getProductExternalId());
		}
	}


	public static void clearSessionData(HttpSession session) 
	{
		MDC.clear();
		session.setAttribute("lineItemNumber", null);
		session.setAttribute("lineItemExternalID", null);
		session.setAttribute("orderID", null);
		session.setAttribute("CKOInput", null);
		session.setAttribute("product_base_recurring_price", null);
		session.setAttribute("product_base_nonrecurring_price", null);
		session.setAttribute("guid", null);
		session.setAttribute("providerExternalID", null);
		session.setAttribute("product_name", null);
		session.setAttribute("previouslyGivenDataId", null);
		session.setAttribute("baseRecurringPrice", null);
		session.setAttribute("baseNonRecurringPrice", null);
		session.setAttribute("intent", null);
		session.setAttribute("productFeatureMap",null);
		session.setAttribute("dynamicFlowContextMap", null);
		session.setAttribute("providersImageLocation", null);
		session.setAttribute("isReceiverMatch", null);
		session.setAttribute("receiversList", null);
		session.setAttribute(SessionKeys.usageRate.name(), null);
		session.setAttribute(SessionKeys.energyRateUnit.name(), null);

	}

	public static String getCKOParamValue(CKOInitialVo CKOVo,String paramName){
		for(String paramString:CKOVo.getParams()){
			if(paramString.contains(paramName)
					&& paramString.split("=").length > 1 
					&& paramString.split("=")[0].equalsIgnoreCase(paramName)){
				return  paramString.split("=")[1];
			}
		}
		return "";
	}

	/** Check whether meta data has "RECEIVER_MATCH=TRUE" Or Not,if it has return true otherwise false.
	 * @param productInfo
	 * @return
	 */
	public static boolean isMetaDataHasReceiverMatchTrueOrNot(ProductInfoType productInfo){
		if(productInfo != null 
				&& productInfo.getProductDetails() != null 
				&& productInfo.getProductDetails().getMetaData()!= null
				&& productInfo.getProductDetails().getMetaData().getMetaData() != null
				&& !productInfo.getProductDetails().getMetaData().getMetaData().isEmpty()){
			for(String metaData:productInfo.getProductDetails().getMetaData().getMetaData()){
				if((!Utils.isBlank(metaData)) 
						&& (Constants.RECEIVER_MATCH.equalsIgnoreCase(metaData))){
					return true;
				}
			}
		}
		return false;
	}
}
