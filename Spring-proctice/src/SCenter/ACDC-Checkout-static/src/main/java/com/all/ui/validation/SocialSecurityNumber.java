package com.AL.ui.validation;

import java.util.ArrayList;
import java.util.List;

import com.AL.ui.exception.InvalidDataException;
import com.AL.ui.util.Utils;

public enum SocialSecurityNumber {

	INSTANCE;

	private char mObfuscatedChar = '*';

	private String firstNum = "";
	private String midNum = "";
	private String lastNum = "";

	public String getSSN()
	{
		StringBuffer buff = new StringBuffer();
		buff.append(firstNum);
		buff.append('-');
		buff.append(midNum);
		buff.append('-');
		buff.append(lastNum);
		return buff.toString();
	}

	public String getFirstNum() { return this.firstNum; }
	public void setFirstNum( String p1 ) { this.firstNum = p1; }
	public String getMidNum() { return this.midNum; }
	public void setMidNum( String p1 ) { this.midNum = p1; }
	public String getLastNum() { return this.lastNum; }
	public void setLastNum( String p1 ) { this.lastNum = p1; }

	public List<String> isValid(String ssn) throws InvalidDataException
	{
		List<String> errorList = new ArrayList<String>();

		parseString(ssn, errorList);
		if(errorList.size() > 0){
//			System.out.println("errorList ::::::::: "+errorList);
			return errorList;	
		}

		StringBuffer buff = new StringBuffer( );
		String singleDigit = "";
		if(ssn.length() == 9 || ssn.length() == 11){
			if (firstNum.length() > 0)
			{
				singleDigit = firstNum.substring(0, 1);
				buff.append(singleDigit + singleDigit + singleDigit);
				buff.append("-");
				buff.append(singleDigit + singleDigit);
				buff.append("-");
				buff.append(singleDigit + singleDigit + singleDigit + singleDigit);
			}

			if (firstNum.length() != 3){
				errorList.add("SSN must contain exactly 9 digits.");
				return errorList;
			}else if (midNum.length() != 2){
				errorList.add("SSN must contain exactly 9 digits.");
				return errorList;
			}
			else if (lastNum.length() != 4){
				errorList.add("SSN must contain exactly 9 digits.");
				return errorList;
			}
			else if (firstNum.equals("000")){
				errorList.add("None of the three portions of SSN can consist entirely of repeating zeros.");
				return errorList;
			}
			else if (midNum.equals("000")){
				errorList.add("None of the three portions of SSN can consist entirely of repeating zeros.");
				return errorList;
			}
			else if (lastNum.equals("0000")){
				errorList.add("None of the three portions of SSN can consist entirely of repeating zeros.");
				return errorList;
			}
			else if (getSSN().equals(buff.toString())){
				errorList.add("SSN cannot consist entirely of the same digit repeated.");
				return errorList;
			}
			if(!Utils.isValidNumber(firstNum))
			{
				errorList.add("Invalid SSN Number");
				return errorList;
			}
		}
		else{
			errorList.add("SSN must contain exactly 9 digits.");
			return errorList;
		}
		return errorList;
	}

	public void parseString(String str, List<String> errorList)
	{
		if (str.length() == 9)
		{
			for (int idx = 0; idx < str.length(); idx++)
			{	
				if(!Utils.isNumeric(str)){
					errorList.add("SSN should not contain alphabets or special characters");
					break;
				}
			}
			firstNum = str.substring( 0, 3 );
			midNum = str.substring( 3, 5 );
			lastNum = str.substring( 5, 9 );
		}
		else if(str.length() == 11){
			String[] strArr = str.split("-");
			int i = 0;
			if(!(strArr.length == 3)){
				errorList.add("Invalid SSN. Please enter a valid SSN");
				return;
			}
			else{
				while(i < strArr.length){
					try{
//						System.out.println("strArr["+i+"] :::::::::: "+strArr[i]);
						Integer.valueOf(strArr[i]);
					}catch(Exception e){
						errorList.add("SSN should not contain alphabets or special characters");
					}
					i++;
				}
				firstNum = strArr[0];
				midNum = strArr[1];
				lastNum = strArr[2];
			}
		}
	}
}
