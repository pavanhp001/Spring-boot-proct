package com.A.Vdao.transactional.dao.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.log4j.Logger;

public enum DataTypeConverter {

	INSTANCE;

	private Logger logger = Logger.getLogger(DataTypeConverter.class);
	
	public Long objToLong(Object obj) {

		if (obj != null) {

			if (obj instanceof BigDecimal) {
				return toLong((BigDecimal) obj);
			}

			if (obj instanceof BigInteger) {
				return toLong((BigInteger) obj);
			}
		}

		return null;

	}

	public Long toLong(BigDecimal obj) {
		logger.debug("inside toLong BigDecimal instance");
		BigDecimal nextVal = (BigDecimal) obj;
		logger.debug("nextVal.longValue() -> "+nextVal.longValue());
		return nextVal.longValue();
	}

	public Long toLong(BigInteger obj) {
		logger.debug("inside toLong BigInteger instance");
		BigInteger nextVal = (BigInteger) obj;
		logger.debug("nextVal.longValue() -> "+nextVal.longValue());
		return nextVal.longValue();
	}
	
	

}
