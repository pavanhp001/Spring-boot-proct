/**
 *
 */
package com.A.vm.util.converter.mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ebthomas
 * 
 */
public final class ContactMapper
{
	public static final Map<String, String> contactFields = new HashMap<String, String>();

	static
	{
		contactFields.put( "value", "value" );
		contactFields.put( "desc", "description" );
		contactFields.put( "order", "preferenceOrder" );
	}
}
