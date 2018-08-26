package com.AL.ui.validation;

import java.util.Arrays;

import com.AL.ui.exception.InvalidDataException;
import com.AL.ui.exception.InvalidFormatException;

public class PhoneNumberValidator {
	/**
	 * Array of invalid area codes
	 */
	private static String[] invalidAreaCodes =
	{
		"000",
		"111",
		"999"
	};

	/**
	 * Array of invalid last seven digits
	 */
	private static String[] invalidLastDigits =
	{
		"0000000",
		"1111111",
		"9999999"
	};

	/**
	 * Constructs an instance of <code>PhoneNumberValidator</code>.
	 */
	public PhoneNumberValidator()
	{

	}

	/**
	 * Validates the specified phone number.
	 * 
	 * Additional checks prevents the area code from being 000, 111, or 999
	 * and prevents last seven digits from being 0000000, 1111111, 9999999.
	 * 
	 * @param phoneNumber the phone number to validate
	 * @return <code>true</code> if the phone number is valid
	 * @throws InvalidDataException if the phone number is not valid
	 */
	private boolean validate(String phoneNumber) throws InvalidDataException
	{
		if(phoneNumber == null)
		{
			throw new InvalidDataException("phoneNumber cannot be null");
		}
		if(phoneNumber.length() < 10){
			String areaCode = phoneNumber.substring(0,3);
			String lastDigits =  phoneNumber.substring(3);
//			System.out.println("areaCode ::::::::::::: "+areaCode);
//			System.out.println("lastDigits ::::::::::::: "+lastDigits);
			if(Arrays.asList(invalidAreaCodes).indexOf(areaCode) >= 0)
			{
				String codes = "";
				for(int i = 0; i < invalidAreaCodes.length - 1; i++)
				{
					codes += invalidAreaCodes[i] + ", ";
				}
				if(invalidAreaCodes.length > 1)
				{
					codes += "or ";
				}
				codes += invalidAreaCodes[invalidAreaCodes.length - 1];
				throw new InvalidDataException("Invalid area code (cannot be " + codes + ")");
			}

			if(Arrays.asList(invalidLastDigits).indexOf(lastDigits) >= 0)
			{
				String codes = "";
				for(int i = 0; i < invalidLastDigits.length - 1; i++)
				{
					codes += invalidLastDigits[i] + ", ";
				}
				codes += invalidLastDigits[invalidLastDigits.length - 1];
				throw new InvalidDataException("Invalid phone number (last seven digits cannot be "
						+ codes + ")");
			}
		}
		return true;
	}

	public void isValiedPhoneNumber(String number) throws InvalidFormatException, InvalidDataException{
		String reg = "^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$";
		if(! number.matches(reg)){
			throw new InvalidFormatException("Expected Phone number string, but found: " + number);
		}
		else{
			validate(number);
		}
	}
}
