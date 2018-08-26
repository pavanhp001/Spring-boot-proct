/**
 *
 */
package com.A.vm.util.converter;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCalendar;
import org.apache.xmlbeans.XmlObject;

import com.A.util.DateUtil;
import com.A.util.XmlUtil;
import com.A.vm.util.converter.unmarshall.UnmarshallBase;
import com.A.vm.util.converter.unmarshall.UnmarshallDate;
import com.A.vm.util.converter.unmarshall.UnmarshallValidationEnum;
import com.A.xml.v4.DateTimeOrTimeRangeType;
import com.A.xml.v4.EmptyElementType;
import com.A.xml.v4.impl.DateTimeOrTimeRangeTypeImpl;
/**
 * @author ebthomas
 * 
 */
/**
 * @author ebthomas
 * 
 * @param <T>
 *            getter class
 * @param <U>
 *            setter class
 */
public class DynamicBuilder<T, U> extends UnmarshallBase<T> {
	private UnmarshallValidationEnum level = UnmarshallValidationEnum.constrained;
	private static final String SET_VALUE = "set";
	private static final String GET_VALUE = "get";
	private static final String REG_EX_SEPERATOR_DELIMIT = "[.]";
	private static final Logger logger = Logger.getLogger(DynamicBuilder.class);

	public DynamicBuilder() {
		level = UnmarshallValidationEnum.unconstrained;
	}

	public DynamicBuilder(final     UnmarshallValidationEnum level2) {
		if (level2 == null) {
			this.level = UnmarshallValidationEnum.constrained;
		} else {
			this.level = level2;
		}

	}

	@SuppressWarnings("unchecked")
	public Map<String, Method> getClassGetterMethods(T getInstance) {
		// Class<T> setClazz = (Class<T>) getInstance.getClass();

		Map<String, Method> methodMap = new HashMap<String, Method>();
		Method[] methods = ((Class<T>) getInstance.getClass()).getMethods();

		for (Method methodx : methods) {
			if (methodx.getName().startsWith(GET_VALUE)) {
				methodMap.put(methodx.getName().trim(), methodx);

			}
		}

		return methodMap;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Method> getClassSetterMethods(final U setInstance) {
		// Class<U> setClazz = (Class<U>) setInstance.getClass();

		Map<String, Method> methodMap = new HashMap<String, Method>();
		Method[] methods = ((Class<U>) setInstance.getClass()).getMethods();

		for (Method methodx : methods) {
			if (methodx.getName().startsWith(SET_VALUE)) {
				methodMap.put(methodx.getName().trim(), methodx);

			}
		}

		return methodMap;
	}

	public Boolean doPerformUpdate(final Object value) {

		boolean performCopy = Boolean.TRUE;
		 

		return performCopy;

	}

	public void invoke(final Object setInstance, final Method setterMethod,
			final Object value, Class<?> getterReturnTypeClazz) {
		Object[] methodArgs = new Object[] { value };

		if (doPerformUpdate(methodArgs[0])) {
			try {
				String simpleName = getterReturnTypeClazz.getSimpleName();

				customInvoke(simpleName, setInstance, setterMethod, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void customInvoke(String simpleName, final Object setInstance,
			final Method setterMethod, final Object value) throws Exception {
		if ((simpleName.equals("EmptyElementType"))) {
			if (value != null) {
				boolean updateValueForOptionalBooleanParam = ((EmptyElementType) value)
						.isNil();
				setterMethod.invoke(setInstance,
						updateValueForOptionalBooleanParam);
			}
		} else if ((simpleName.equals("DateTimeOrTimeRangeType"))) {
			if (value != null) {
				Calendar cal = UnmarshallDate
						.getCalendar((DateTimeOrTimeRangeType) value);
				setterMethod.invoke(setInstance, cal);
			}
		} else {
			setterMethod.invoke(setInstance, value);
		}
	}

	public void copyInstanceAttributes(T getInstance, U setInstance,
			final Map<String, String> map, boolean isUpdateRequest)
			throws Exception {
		Map<String, Method> methodMapGet = getClassGetterMethods(getInstance);

		for (String getterPath : map.keySet()) {
			Object value = nestedGet(getInstance, getterPath, methodMapGet,
					isUpdateRequest);
			String setterPath = map.get(getterPath);
			if (setterPath != null) {
				Object setterTargetInstance = nestedSet(setInstance, setterPath);

				if (setterTargetInstance != null) {

					String[] setterMethodNames = setterPath
							.split(REG_EX_SEPERATOR_DELIMIT);

					if (setterMethodNames != null) {
						String setterMethodName = setterMethodNames[setterMethodNames.length - 1];

						String setCamelCaseMethodName = SET_VALUE
								+ setterMethodName.substring(0, 1)
										.toUpperCase()
								+ setterMethodName.substring(1);

						if ((setterTargetInstance != null) && (value != null)) {
							try {
 
								setValue(setterTargetInstance,
										setCamelCaseMethodName, value);
							} catch (NoSuchMethodException nsme) {
								if (value instanceof XmlCalendar) {
									value = DateUtil
											.getCalendar((XmlCalendar) value);
									setValue(setterTargetInstance,
											setCamelCaseMethodName, value);
								}
							}
						}
					}
				}
			}
		}
	}

	public void setValue(final Object setterTargetInstance,
			final String setCamelCaseMethodName, final Object value)
			throws SecurityException, NoSuchMethodException {
		Method setterMethod = null;

 
		try {
			setterMethod = setterTargetInstance.getClass().getMethod(
					setCamelCaseMethodName, value.getClass());
			Class<?> getterReturnTypeClazz = setterMethod.getReturnType();

			invoke(setterTargetInstance, setterMethod, value,
					getterReturnTypeClazz);
		} catch (NoSuchMethodException nsme) {
			if (value.getClass().isAssignableFrom(Calendar.class)) {
				Calendar valueAsCalendar = (Calendar) value;
				//logger.debug(setCamelCaseMethodName + "----" + valueAsCalendar);
				setterMethod = setterTargetInstance.getClass().getMethod(
						setCamelCaseMethodName, valueAsCalendar.getClass());
				Class<?> getterReturnTypeClazz = setterMethod.getReturnType();
				invoke(setterTargetInstance, setterMethod, value,
						getterReturnTypeClazz);
			} else if (value.getClass().isAssignableFrom(XmlCalendar.class)) {
				if (value instanceof XmlCalendar) {
					Calendar cal = DateUtil.getCalendar((XmlCalendar) value);
					//logger.debug(setCamelCaseMethodName + "----" + cal);
					setterMethod = setterTargetInstance.getClass().getMethod(
							setCamelCaseMethodName, cal.getClass());
					Class<?> getterReturnTypeClazz = setterMethod
							.getReturnType();
					invoke(setterTargetInstance, setterMethod, cal,
							getterReturnTypeClazz);
				}
			} else if (value.getClass().isAssignableFrom(
					DateTimeOrTimeRangeTypeImpl.class)) {
				Calendar cal = UnmarshallDate
						.getCalendarDateTimeStart((DateTimeOrTimeRangeType) value);

				if (cal instanceof GregorianCalendar) {
					Calendar gCal = cal;
					//logger.debug(setCamelCaseMethodName + "----" + cal);
					setterMethod = setterTargetInstance.getClass().getMethod(
							setCamelCaseMethodName, Calendar.class);
					Class<?> getterReturnTypeClazz = setterMethod
							.getReturnType();
					invoke(setterTargetInstance, setterMethod, gCal,
							getterReturnTypeClazz);
				}

			}
		}

	}

	public Object simpleGet(Object getInstance, String getMethodName,
			Map<String, Method> methodMapGet, boolean isUpdateRequest)
			throws Exception {

		XmlObject srcItem = getXmlObject(getInstance);

		Object value = null;
	 
		String getCamelCaseMethodName = GET_VALUE
				+ getMethodName.substring(0, 1).toUpperCase()
				+ getMethodName.substring(1);
		Method getterMethod = methodMapGet.get(getCamelCaseMethodName);
		if (!isUpdateRequest && getterMethod != null) {
			value = getterMethod.invoke(getInstance, new Object[0]);
		} else {

			if (XmlUtil.isElementNil(srcItem.newCursor(), getMethodName)) {
 
				if (getterMethod.getReturnType().isPrimitive())
					value = Integer.valueOf(0);
				else
					value = "";
			} else if (!XmlUtil.isElementNull(srcItem.newCursor(),
					getMethodName)) {// If element is not present in the xml
										// then just ignore it
				value = getterMethod.invoke(getInstance, new Object[0]);
			}
		}
		return value;
	}

	private XmlObject getXmlObject(Object getInstance) {
		XmlObject srcItem = null;
		if (getInstance instanceof XmlObject) {
			srcItem = (XmlObject) getInstance;
		}
		return srcItem;
	}

	public Object nestedGet(Object toBean, String path,
			Map<String, Method> methodMapGet, boolean isUpdateRequest)
			throws Exception {

		if (path.indexOf(".") == -1) {
			return simpleGet(toBean, path, methodMapGet, isUpdateRequest);
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
					return target;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return target;
	}

	public Object nestedSet(Object toBean, String path) throws Exception {
		String[] data = path.split(REG_EX_SEPERATOR_DELIMIT);
		Object target = toBean;

		for (int i = 0; i < data.length - 1; i++) {
			String getCamelCaseMethodName = GET_VALUE
					+ data[i].substring(0, 1).toUpperCase()
					+ data[i].substring(1);
			Class<?>[] clazz = null;

			Method getterMethod = target.getClass().getMethod(
					getCamelCaseMethodName, clazz);

			if (getterMethod == null) {
				throw new IllegalArgumentException("illegal.set.method");
			}

			target = getterMethod.invoke(target, new Object[0]);

			if (target == null) {
				return target;
			}
		}

		return target;
		// setterMethod.invoke( target, value );
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

	public UnmarshallValidationEnum getLevel() {
		return level;
	}

	public void setLevel(UnmarshallValidationEnum level) {
		this.level = level;
	}
	
	
	
	
}
