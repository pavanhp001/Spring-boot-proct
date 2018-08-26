package com.A.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

import com.A.xml.v4.LineItemPriceInfoType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.ObjectFactory;

/**
 * @author ebthomas
 * 
 */

public class DynamicMetaGet<T> {

	private static final String GET_VALUE = "get";
	private static final String REG_EX_SEPERATOR_DELIMIT = "[.]";
	private static final Logger logger = Logger
			.getLogger(DynamicMetaGet.class);

	@SuppressWarnings("unchecked")
	public Map<String, Method> getClassGetterMethods(T getInstance) {
		 

		Map<String, Method> methodMap = new HashMap<String, Method>();
		Method[] methods = ((Class<T>) getInstance.getClass()).getMethods();

		for (Method methodx : methods) {
			if (methodx.getName().startsWith(GET_VALUE)) {
				methodMap.put(methodx.getName().trim(), methodx);

			}
		}

		return methodMap;
	}

	public Object simpleGet(Object getInstance, String getMethodName ) throws Exception {
		 
		String getCamelCaseMethodName = GET_VALUE
				+ getMethodName.substring(0, 1).toUpperCase()
				+ getMethodName.substring(1);
		
		
		Class<?>[] clazz = null;
		
		Method getterMethod = getInstance.getClass().getMethod(
				getCamelCaseMethodName, clazz);

		if (getterMethod == null) {
			throw new IllegalArgumentException("illegal.get.method");
		}

		Object result = getterMethod.invoke(getInstance, clazz);
		
		
		
		 
		return result;
	}

	public Object nestedGet(Object toBean, String path ) throws Exception {

		if (path.indexOf(".") == -1) {
			return simpleGet(toBean, path );
		}

		String[] data = path.split(REG_EX_SEPERATOR_DELIMIT);
		Object target = toBean;

		for (int i = 0; i < data.length; i++) {
			String getCamelCaseMethodName = GET_VALUE
					+ data[i].substring(0, 1).toUpperCase()
					+ data[i].substring(1);
			Class<?>[] clazz = null;

			try {
				Method getterMethod = target.getClass().getMethod(
						getCamelCaseMethodName, clazz);

				if (getterMethod == null) {
					throw new IllegalArgumentException("illegal.get.method");
				}

				target = getterMethod.invoke(target, clazz);

				if (target == null) {
					return "";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return target;
	}
	
	public static String convertToString(Object value) {

		if (value instanceof String && value.getClass() == String.class) {
			return (String)value;
		} else if (value instanceof Integer && value.getClass() == Integer.class) {
			return String.valueOf((String) value);
		} else if (value instanceof Long && value.getClass() == Long.class) {
			return String.valueOf((String) value);
		} else if (value instanceof Short && value.getClass() == Short.class) {
			return String.valueOf((String) value);
		} else if (value instanceof Byte && value.getClass() == Byte.class) {
			return String.valueOf((String) value);
		} else if (value instanceof Double && value.getClass() == Double.class) {
			return String.valueOf(((Double) value).doubleValue());
		} else if (value instanceof Float && value.getClass() == Float.class) {
			return String.valueOf((String) value);
		} else if (value instanceof BigDecimal
				&& value.getClass() == BigDecimal.class) {
			return new BigDecimal((String) value).toPlainString();
		} else if (value instanceof BigInteger
				&& value.getClass() == BigInteger.class) {
			return new BigInteger((String) value).toString();
		} else {
			// TODO look up converter in Spring context. Spring uses property
			// editors
			return null;
		}

	}

	public Object convertObject(Object value, Class<?> clazz) {

		if (clazz == String.class && value.getClass() == String.class) {
			return value;
		} else if (clazz == String.class && value.getClass() == Integer.class) {
			return Integer.valueOf((String) value);
		} else if (clazz == String.class && value.getClass() == Long.class) {
			return Long.valueOf((String) value);
		} else if (clazz == String.class && value.getClass() == Short.class) {
			return Short.valueOf((String) value);
		} else if (clazz == String.class && value.getClass() == Byte.class) {
			return Byte.valueOf((String) value);
		} else if (clazz == String.class && value.getClass() == Double.class) {
			return Double.valueOf((String) value);
		} else if (clazz == String.class && value.getClass() == Float.class) {
			return Float.valueOf((String) value);
		} else if (clazz == String.class
				&& value.getClass() == BigDecimal.class) {
			return new BigDecimal((String) value);
		} else if (clazz == String.class
				&& value.getClass() == BigInteger.class) {
			return new BigInteger((String) value);
		} else {
			// TODO look up converter in Spring context. Spring uses property
			// editors
			return null;
		}

	}

	public static void main(String[] arg) throws Exception {

		ObjectFactory oFactory = new ObjectFactory();
		LineItemPriceInfoType lipi = oFactory.createLineItemPriceInfoType();
		lipi.setOnDeliveryPrice("45.89");
		
		DynamicMetaGet<LineItemType> loader = new DynamicMetaGet<LineItemType>();
		LineItemType lit = new LineItemType();
		lit.setLineItemPriceInfo(lipi) ;

		Object obj = loader.nestedGet(lit, "lineItemPriceInfo.onDeliveryPrice" );
		 
		logger.info("value="+obj);
	}

}
