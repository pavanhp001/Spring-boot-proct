package com.A.vm.util.converter.unmarshall;

import org.apache.log4j.Logger;
import com.A.V.beans.entity.Consumer;
import com.A.vm.util.converter.DynamicBuilder;
import com.A.vm.util.converter.mapper.ConsumerFinancialInfoMapper;
import com.A.xml.v4.CustomerFinancialInfoType;

/**
 * @author ebthomas
 * 
 */
public final class UnmarshallConsumerFinancialInfo {

	private static final Logger logger = Logger
			.getLogger(UnmarshallConsumerFinancialInfo.class);

	public static Consumer copy(final CustomerFinancialInfoType src,
			boolean isUpdateRequest) {
		final Consumer consumerBean = new Consumer();

		copy(src, consumerBean, isUpdateRequest);
		return consumerBean;
	}

	/**
	 * @param isUpdateRequest
	 *            TODO
	 * @param financialInfo
	 *            Source Financial Information
	 * @param dest
	 *            Domain Financial Information
	 */
	public static void copy(final CustomerFinancialInfoType src,
			final Consumer consumerBean, boolean isUpdateRequest) {
		if (src == null) {
			return;
		}

		consumerBean.setStudent(src.isSetStudent());
		consumerBean.setRetired(src.isSetRetired());
		consumerBean.setEmployed(src.isSetEmployed());

		copy(consumerBean, src, isUpdateRequest);

	}

	public static void copy(final Consumer consumerBean,
			final CustomerFinancialInfoType.Employed src,
			boolean isUpdateRequest) {
		if (consumerBean == null) {
			return;
		}

		if (src != null && src.getOccupation() != null) {

			consumerBean.setOccupation(src.getOccupation());

		}

		if (src != null && src.getBusinessName() != null) {

			consumerBean.setEmployerBusinessName(src.getBusinessName());

		}

		if (src != null && src.getBusinessPhoneNum() != null) {

			consumerBean.setEmployerPhoneNumber(src.getBusinessPhoneNum());
		}

		 
	}

	/**
	 * @param src
	 *            source
	 * @return destination
	 */
	public static void copy(final Consumer consumerBean,
			final CustomerFinancialInfoType src, boolean isUpdateRequest) {
		if (consumerBean == null) {
			return;
		}

		DynamicBuilder<CustomerFinancialInfoType, Consumer> builder = new DynamicBuilder<CustomerFinancialInfoType, Consumer>(
				null);

		try {
			builder.copyInstanceAttributes(src, consumerBean,
					ConsumerFinancialInfoMapper.financial, false);
			copy(consumerBean, src.getEmployed(), isUpdateRequest);

		} catch (Exception e) {
			logger.error(
					"Exception thrown while unmarshalling Consumer Financial Information...",
					e);
			e.printStackTrace();
			throw new IllegalArgumentException(
					"unable.to.unmarshall.consumer.financial.info.type");
		}
	}

}
