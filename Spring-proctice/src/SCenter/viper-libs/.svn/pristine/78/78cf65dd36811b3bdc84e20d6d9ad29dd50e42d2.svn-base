package com.A.vm.util.converter.unmarshall;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.A.util.XmlUtil;
import com.A.V.beans.LineItemPriceInfo;
import com.A.V.beans.LineitemScheduleInfo;
import com.A.V.beans.entity.CoachingBean;
import com.A.V.beans.entity.CustomSelection;
import com.A.V.beans.entity.CustomerPaymentEvent;
import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.LineItemAttribute;
import com.A.V.beans.entity.LineItemDetail;
import com.A.V.beans.entity.ReasonBean;
import com.A.V.beans.entity.SelectedDialogue;
import com.A.V.beans.entity.SelectedFeatureValue;
import com.A.V.beans.entity.StatusRecordBean;
import com.A.Vdao.dao.SelectedFeatureValueDao;
import com.A.vm.util.converter.DynamicBuilder;
import com.A.vm.util.converter.mapper.DatesMapper;
import com.A.vm.util.converter.mapper.LineItemMapper;
import com.A.xml.v4.AttributeDetailType;
import com.A.xml.v4.AttributeEntityType;
import com.A.xml.v4.CoachingList;
import com.A.xml.v4.Coachings;
import com.A.xml.v4.LineItemAttributeType;
import com.A.xml.v4.LineItemCollectionType;
import com.A.xml.v4.LineItemDetailType;
import com.A.xml.v4.LineItemPriceInfoType;
import com.A.xml.v4.LineItemStatusHistoryType;
import com.A.xml.v4.LineItemStatusType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.ReasonList;
import com.A.xml.v4.SchedulingInfoType;

/**
 * @author ebthomas
 * 
 */
@Component("unmarshallLineItem")
public final class UnmarshallLineItem extends UnmarshallBase<LineItemType> {

	// @Autowired
	// private PartyService partyService;

	// @Autowired
	// private CatalogProductService service;

	@Autowired
	private UnmarshallLineItemDetail unmarshallLineItemDetail;

	@Autowired
	private SelectedFeatureValueDao selectedFeatureValueDao;

	Logger logger = Logger.getLogger(UnmarshallLineItem.class);

	/**
	 * Unmarshall Line Item.
	 */
	public UnmarshallLineItem() {
		super();
	}

	/**
	 * @author ebthomas
	 * 
	 */

	public static final List<LineItem> DEFAULT_EMPTY_LIST = new ArrayList<LineItem>();;

	/**
	 * @param lineItemBeansArg
	 *            input argument
	 * @return builder
	 */
	public Object lineItemBeans(final List<LineItem> lineItemBeansArg) {
		// lineItemBeans = lineItemBeansArg;

		return null;
	}

	// This method will only copy basic info
	public LineItem customCopy(final LineItem lineItemDestination,
			final LineItemType srcLineItem,
			final UnmarshallValidationEnum level, boolean isUpdateRequest) {

		DynamicBuilder<LineItemType, LineItem> builder = new DynamicBuilder<LineItemType, LineItem>(
				level);

		try {

			// TODO Check if it is LineItem update request then do we modify
			// these fields
			// if(!isUpdateRequest)
			builder.copyInstanceAttributes(srcLineItem, lineItemDestination,
					LineItemMapper.lineItemFields, isUpdateRequest);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(
					"unable.to.unmarshall.addressType");
		}

		return lineItemDestination;
	}

	/**
	 * @param isUpdateRequest
	 *            TODO
	 * @param src
	 *            source
	 * @return destination
	 */
	public LineItem copy(final LineItem lineItemDestination,
			final LineItemType srcLineItem,
			final UnmarshallValidationEnum level, boolean isUpdateRequest) {

		DynamicBuilder<LineItemType, LineItem> builder = new DynamicBuilder<LineItemType, LineItem>(
				level);

		try {

			// Update or add date fields
			builder.copyInstanceAttributes(srcLineItem, lineItemDestination,
					DatesMapper.datesFields, isUpdateRequest);

			builder.copyInstanceAttributes(srcLineItem, lineItemDestination,
					LineItemMapper.lineItemFields, isUpdateRequest);

			copyAttributesBillingAddress(lineItemDestination, srcLineItem,
					level);
			copyAttributesServiceAddress(lineItemDestination, srcLineItem,
					false);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(
					"unable.to.unmarshall.addressType");
		}

		return lineItemDestination;
	}

	public void copyAttributesLineItem(final LineItem lineItemDestination,
			final LineItemType srcLineItem,
			final UnmarshallValidationEnum level, boolean isUpdateRequest) {

		lineItemDestination.setBillingInfoExtId(srcLineItem
				.getBillingInfoExtId());
	}

	public void copyAttributesBillingAddress(
			final LineItem lineItemDestination, final LineItemType srcLineItem,
			final UnmarshallValidationEnum level) {
		// TODO pass the boolean value based on request type(if
		// updateRequest then true else false)
		/*
		 * if (srcLineItem.getBillingInfo() != null) { AddressBean
		 * billingAddressBean = UnmarshallAddressBean.copy(
		 * srcLineItem.getBillingInfo().getBillingAddress(), level, false);
		 * lineItemDestination.getBilling().setBillingAddress(
		 * billingAddressBean); }
		 */
	}

	public void copyAttributesServiceAddress(
			final LineItem lineItemDestination, final LineItemType srcLineItem,
			boolean isUpdateRequest) {

		lineItemDestination.setServiceAddressExtId(srcLineItem
				.getSvcAddressExtId());
	}

	/**
	 * @param destLineItem
	 *            destination
	 * @param src
	 *            Source
	 * @param isUpdateRequest
	 *            TODO
	 */
	public void copyBasicLineItemInfo(final LineItem destLineItem,
			final LineItemType src, boolean isUpdateRequest) {
		UnmarshallValidationEnum level = UnmarshallValidationEnum.unconstrained;

		// First copy basic info like all date fields and howLongAtAddress
		copyScheduleInfo(destLineItem, src, isUpdateRequest);
		customCopy(destLineItem, src, level, isUpdateRequest);

		if (src.getProductDatasource() != null) {
			destLineItem.setProductDatasource(src.getProductDatasource()
					.getStringValue());

		} else {
			logger.debug("lineitem:" + src.getExternalId()
					+ " product datasource is null");
		}

		if (src.getAccountHolderExternalId() != 0) {
			destLineItem.setAccountHolderExternalId(src
					.getAccountHolderExternalId());
		}

		if (src.getSvcAddressExtId() != null) {
			destLineItem.setServiceAddressExtId(src.getSvcAddressExtId());
		}
		if (src.getBillingInfoExtId() != null) {
			destLineItem.setBillingInfoExtId(src.getBillingInfoExtId());
		}

		if (src.getNewPhone() != null) {
			destLineItem.setNewPhone(src.getNewPhone());
		}

		if (src.getService() != null) {
			destLineItem.setServiceType(src.getService().toString());
		}

		if (src.getCreditStatus() != null) {
			destLineItem.setCreditStatus(src.getCreditStatus().toString());
		}

		if (!XmlUtil.isElementNil(src.newCursor(), "customer")) {

			if (src.getCustomer() != null) {
				String lineItemOwnerExtId = String.valueOf(src.getCustomer()
						.getExternalId());
				destLineItem.setLineItemOwnerExtId(lineItemOwnerExtId);
			}
		}

		if (!isUpdateRequest) {
			destLineItem.setLineItemCreationDate(Calendar.getInstance());
		}

		destLineItem.setState(src.getState());

		if (src.getLeadId() > 0) {
			logger.debug("Setting lead id : " + src.getLeadId());
			destLineItem.setLeadId(src.getLeadId());
		}

		if (src.getProviderConfirmationNumber() != null
				&& src.getProviderConfirmationNumber().trim().length() > 0) {
			logger.debug("Setting provider conf number = "
					+ src.getProviderConfirmationNumber());
			destLineItem.setProviderConfirmationNumber(src
					.getProviderConfirmationNumber());
		}

		if (!XmlUtil.isElementNull(src.newCursor(), "isEventSelected")) {
			if (src.getIsEventSelected()) {
				destLineItem.setIsEventSelected(Boolean.TRUE);
			} else {
				destLineItem.setIsEventSelected(Boolean.FALSE);
			}
		}

		if (!XmlUtil.isElementNull(src.newCursor(), "isEventCompleted")) {
			if (src.getIsEventCompleted()) {
				destLineItem.setIsEventCompleted(Boolean.TRUE);
			} else {
				destLineItem.setIsEventCompleted(Boolean.FALSE);
			}
		}

		if (!XmlUtil.isElementNull(src.newCursor(), "eventType")) {
			destLineItem.setEventType(src.getEventType());
		}

		if (!XmlUtil.isElementNull(src.newCursor(), "isTransfer")) {
			destLineItem.setIsTransfer(src.getIsTransfer());
		}
	}

	/**
	 * Method to copy scheduling information
	 * 
	 * @param destLineItem
	 * @param src
	 * @param isUpdateRequest
	 */
	private void copyScheduleInfo(LineItem destLineItem, LineItemType src,
			boolean isUpdateRequest) {

		if (src.getSchedulingInfo() != null) {
			SchedulingInfoType srcSchInfo = src.getSchedulingInfo();
			LineitemScheduleInfo destSchInfo = null;

			if (destLineItem.getLineitemScheduleInfo() == null) {
				destSchInfo = new LineitemScheduleInfo();
			} else {
				destSchInfo = destLineItem.getLineitemScheduleInfo();
			}

			// ***********************************************************

			if (srcSchInfo.getActualStartDate() != null
					&& !XmlUtil.isElementNil(srcSchInfo.getActualStartDate()
							.newCursor(), "date")) {
				destSchInfo.setActualStartDate(UnmarshallDate
						.getCalendarDateTimeStart(srcSchInfo
								.getActualStartDate()));
			}

			if (srcSchInfo.getDesiredStartDate() != null
					&& !XmlUtil.isElementNil(srcSchInfo.getDesiredStartDate()
							.newCursor(), "date")) {
				destSchInfo.setDesiredStartDate(UnmarshallDate
						.getCalendarDateTimeStart(srcSchInfo
								.getDesiredStartDate()));
			}
			if (srcSchInfo.getScheduledStartDate() != null
					&& !XmlUtil.isElementNil(srcSchInfo.getScheduledStartDate()
							.newCursor(), "date")) {
				destSchInfo.setScheduledStartDate(UnmarshallDate
						.getCalendarDateTimeStart(srcSchInfo
								.getScheduledStartDate()));
			}
			if (srcSchInfo.getDisconnectDate() != null
					&& !XmlUtil.isElementNil(srcSchInfo.getDisconnectDate()
							.newCursor(), "date")) {
				destSchInfo.setDisconnectDate(UnmarshallDate
						.getCalendarDateTimeStart(srcSchInfo
								.getDisconnectDate()));
			}

			// ***********************************************************

			if (srcSchInfo.getActualStartDate() != null
					&& !XmlUtil.isElementNil(srcSchInfo.getActualStartDate()
							.newCursor(), "date")) {
				destSchInfo
						.setActualStartEEDate(UnmarshallDate
								.getCalendarDateTimeEnd(srcSchInfo
										.getActualStartDate()));
			}

			if (srcSchInfo.getDesiredStartDate() != null
					&& !XmlUtil.isElementNil(srcSchInfo.getDesiredStartDate()
							.newCursor(), "date")) {
				destSchInfo.setDesiredStartEEDate(UnmarshallDate
						.getCalendarDateTimeEnd(srcSchInfo
								.getDesiredStartDate()));
			}
			if (srcSchInfo.getScheduledStartDate() != null
					&& !XmlUtil.isElementNil(srcSchInfo.getScheduledStartDate()
							.newCursor(), "date")) {
				destSchInfo.setScheduledStartEEDate(UnmarshallDate
						.getCalendarDateTimeEnd(srcSchInfo
								.getScheduledStartDate()));
			}
			if (srcSchInfo.getDisconnectDate() != null
					&& !XmlUtil.isElementNil(srcSchInfo.getDisconnectDate()
							.newCursor(), "date")) {
				destSchInfo
						.setDisconnectEEDate(UnmarshallDate
								.getCalendarDateTimeEnd(srcSchInfo
										.getDisconnectDate()));
			}

			// ***********************************************************

			if (srcSchInfo.getOrderDate() != null
					&& !XmlUtil.isElementNil(srcSchInfo.getOrderDate()
							.newCursor(), "date")) {
				destSchInfo.setOrderDate(UnmarshallDate
						.getCalendarDateTimeStart(srcSchInfo.getOrderDate()));
			}

			if ((srcSchInfo != null)
					&& (srcSchInfo.getWishScheduleCollection() != null)) {
				destSchInfo.setScheduleAsSoonAsPossible(srcSchInfo
						.getWishScheduleCollection()
						.getScheduleAsSoonAsPossible());
				destSchInfo.setDesiredStartReq(srcSchInfo
						.getWishScheduleCollection().xmlText());
			}

			destSchInfo.setAppointmentComment(srcSchInfo
					.getAppointmentComment());
			destSchInfo.setBillingInstallments(srcSchInfo
					.getBillingInstallments());
			destSchInfo.setEarlierAppointmentDate(srcSchInfo
					.getEarlierAppointmentDate());

			destSchInfo.setInstallationFee(srcSchInfo.getInstallationFee());

			destSchInfo.setResidenceType(srcSchInfo.getResidenceType());

			// TODO:Backwards Compatibility remove
			destSchInfo.setScheduleAsSoonAsPossible(srcSchInfo
					.getScheduleAsSoonAsPossible()
					|| destSchInfo.isScheduleAsSoonAsPossible());

			destLineItem.setLineitemScheduleInfo(destSchInfo);
		}

	}

	/**
	 * @param destLineItem
	 *            destination
	 * @param srcLineItem
	 *            Source
	 * @param isUpdateRequest
	 * 
	 *            This method will copy Billing Information and Billing Address
	 */
	public void copyBillingLineItemInfo(final LineItem destLineItem,
			final LineItemType srcLineItem, boolean isUpdateRequest) {
		UnmarshallValidationEnum level = UnmarshallValidationEnum.unconstrained;

		if (destLineItem == null)
			return;

		if ((srcLineItem == null)
				|| (srcLineItem.getBillingInfoExtId() == null))
			return;
		copyAttributesLineItem(destLineItem, srcLineItem, level,
				isUpdateRequest);
	}

	public void copyLineItemPriceInfo(final LineItem destLineItem,
			final LineItemType srcLineItem, boolean isUpdateRequest) {

		if (destLineItem.getPrice() == null)
			destLineItem.setPrice(new LineItemPriceInfo());
		copyPriceInfoAttributes(destLineItem, srcLineItem, isUpdateRequest);
	}

	private void copyPriceInfoAttributes(LineItem destLineItem,
			LineItemType srcLineItem, boolean isUpdateRequest) {
		if (srcLineItem != null && srcLineItem.getLineItemPriceInfo() != null) {
			LineItemPriceInfoType priceInfoType = srcLineItem
					.getLineItemPriceInfo();
			destLineItem.getPrice().setBaseNonRecurringPrice(
					priceInfoType.getBaseNonRecurringPrice());
			destLineItem.getPrice().setBaseRecurringPrice(
					priceInfoType.getBaseRecurringPrice());
			destLineItem.getPrice().setPricingDate(
					priceInfoType.getPricingDate());

			destLineItem.getPrice().setBaseNonRecurringPriceUnits(
					priceInfoType.getBaseNonRecurringPriceUnits());
			destLineItem.getPrice().setBaseRecurringPriceUnits(
					priceInfoType.getBaseRecurringPriceUnits());

			// TODO need to rework to save this field
			// boolean includeInTotalPrice
			// =(!XmlUtil.isElementNull(priceInfoType.newCursor(),
			// "includeInTotalPrice"));
			// destLineItem.getPrice().setIncludePriceInTotal(includeInTotalPrice);

			if ((priceInfoType != null)
					&& (priceInfoType.getPriceInfoStatus() != null)) {
				destLineItem.getPrice().setPricingStatus(
						Long.valueOf(priceInfoType.getPriceInfoStatus()
								.getStatusCode()));
			}

			if (priceInfoType != null) {

				String codPrice = priceInfoType.getOnDeliveryPrice();

				try {
					if ((codPrice != null) && (codPrice.length() > 0)) {
						destLineItem.getPrice().setOnDeliveryPrice(
								Double.valueOf(codPrice));
					} else {
						destLineItem.getPrice().setOnDeliveryPrice(
								Double.parseDouble("0"));
					}
				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
			}

		}
	}

	/**
	 * @param srcLineItemType
	 *            source
	 * @param em
	 *            entity manager
	 * @param isUpdateRequest
	 *            TODO
	 * @param dest
	 *            destination
	 */
	public void copyLineItemDetail(final LineItem destDomainLineItem,
			final LineItemType srcLineItemType, boolean isUpdateRequest) {

		if ((destDomainLineItem == null) || (srcLineItemType == null)) {
			return;
		}

		LineItemDetailType lineItemDetailTypeSrc = srcLineItemType
				.getLineItemDetail();

		// Allow only when creating the line item. Do not allow update detail
		// info
		if ((lineItemDetailTypeSrc != null) & (!isUpdateRequest)) {
			LineItemDetail detailBean = unmarshallLineItemDetail
					.copyLineItemDetailBean(destDomainLineItem,
							lineItemDetailTypeSrc);
			destDomainLineItem.setLineItemDetailBean(detailBean);

			copyLineItemDetailProviderId(lineItemDetailTypeSrc,
					destDomainLineItem);
		}
	}

	public void copyLineItemDetailProviderId(
			final LineItemDetailType lineItemDetailTypeSrc,
			final LineItem destDomainLineItem) {

		if ((lineItemDetailTypeSrc != null)
				&& (lineItemDetailTypeSrc.getDetail() != null)
				&& (lineItemDetailTypeSrc.getDetail().getProductLineItem() != null)
				&& (lineItemDetailTypeSrc.getDetail().getProductLineItem()
						.getProvider() != null)) {
			String providerExternalId = lineItemDetailTypeSrc.getDetail()
					.getProductLineItem().getProvider().getExternalId();
			destDomainLineItem.setProviderExternalId(providerExternalId);
		}

	}

	public void copySelectedFeatureValues(LineItem destLineItem,
			LineItemType src, boolean isUpdateReq) {

		if ((destLineItem == null) || (src == null)) {
			return;
		}
		Set<SelectedFeatureValue> oldFeatureSet = destLineItem
				.getSelectedFeatureValues();
		List<SelectedFeatureValue> featuresBeanList = UnmarshallSelectedFeatureValue
				.copySelectedFeatureValues(destLineItem, src);
		if (featuresBeanList != null) {

			Set<SelectedFeatureValue> featureSet = new HashSet<SelectedFeatureValue>(
					featuresBeanList);

			if (oldFeatureSet != null && !oldFeatureSet.isEmpty()) {
				logger.info("Inactivating existing features : "
						+ oldFeatureSet.toString());

				Iterator<SelectedFeatureValue> oldFVIter = oldFeatureSet
						.iterator();
				boolean isMergeNeeded = false;
				while (oldFVIter.hasNext()) {

					SelectedFeatureValue oldFV = oldFVIter.next();
					if (oldFV.getId() > 0) {
						oldFV.setActive(false);
						oldFV.setFeatureDate(Calendar.getInstance());
						isMergeNeeded = true;
					}
				}
				if (isMergeNeeded) {
					selectedFeatureValueDao.merge(oldFeatureSet);
				}
			}
			destLineItem.setSelectedFeatureValues(featureSet);
		}
	}

	/**
	 * A method to prepare StatusRecordBean from xml and set it to LineItem
	 * bean.
	 * 
	 * @param destinationLineItem
	 * @param lineItemTypeSource
	 */
	public void copyStatusRecordBean(LineItem destinationLineItem,
			final LineItemType lineItemTypeSource, boolean isUpdateRequest,
			String agentId) {
		StatusRecordBean dest = null;
		if (isUpdateRequest) {
			dest = destinationLineItem.getCurrentStatus();
		}
		if (lineItemTypeSource.getLineItemStatus() != null) {
			dest = UnmarshallStatus.copyStatusRecordBean(
					lineItemTypeSource.getLineItemStatus(), agentId);
		} else {
			dest = new StatusRecordBean();
			dest.setAgentExternalId(agentId);
			dest.setDateTimeStamp(Calendar.getInstance());
			dest.setStatus("sales_new_order");
			List<String> reasonList = new ArrayList<String>();
			reasonList.add("0");
			dest.setReasons(reasonList);
		}
		destinationLineItem.setCurrentStatus(dest);
	}

	/**
	 * @param src
	 *            Source
	 * @param entityManagerReference
	 *            Reference to Entity Manager
	 * @return Order Status Domain Object
	 */
	/*
	 * public OrderChangeValueObject buildUpdate(final LineItemType src, final
	 * EntityManager entityManagerReference) {
	 * 
	 * //TODO fix the hard coded agentId LineItem bean = build(src, false);
	 * 
	 * OrderChangeValueObject status = new OrderChangeValueObject(bean);
	 * 
	 * return status; }
	 */

	/**
	 * This method will be used to update lineitem information and will be
	 * called from TaskUpdateLineitem
	 * 
	 * @param src
	 * @param destLineItem
	 * @param isUpdateRequest
	 * @return
	 */
	public LineItem build(final LineItemType src, final LineItem destLineItem,
			boolean isUpdateRequest, String agentId) {

		if (src != null) {
			copyBasicLineItemInfo(destLineItem, src, isUpdateRequest);
			copyBillingLineItemInfo(destLineItem, src, isUpdateRequest);
			copyServiceAddrLineItemInfo(destLineItem, src, isUpdateRequest);
			copyLineItemPriceInfo(destLineItem, src, false);
			if (!isUpdateRequest)
				copyLineItemDetail(destLineItem, src, false);
			// UpdateLineitem will not update the status of the lineitem, so
			// keep this code commented
			if (!isUpdateRequest) {
				copyStatusRecordBean(destLineItem, src, isUpdateRequest,
						agentId);
			}
			copySelectedFeatureValues(destLineItem, src, isUpdateRequest);
			copyScheduleInfo(destLineItem, src, isUpdateRequest);
			copyActiveDialogues(destLineItem, src, isUpdateRequest);
			copyLineItemAttributes(destLineItem, src, isUpdateRequest);
			copyCustomSelection(destLineItem, src, isUpdateRequest);
			copyPaymentEvents(destLineItem, src, isUpdateRequest);
			copyACESData(destLineItem, src);
			return destLineItem;
		}

		return null;

	}

	private void copyActiveDialogues(LineItem destLineItem, LineItemType src,
			boolean isUpdateRequest) {

		// Boolean shouldAppend = src.getActiveDialogs() == null ? Boolean.FALSE
		// : Boolean.TRUE;
		Set<SelectedDialogue> activeDialogues = UnmarshallSelectedDialogue
				.buildSelectedDialogue(src, destLineItem.getDialogues());
		logger.debug("All dialgoues : " + activeDialogues.toString());
		destLineItem.setDialogues(activeDialogues);

	}

	public void copyServiceAddrLineItemInfo(LineItem destLineItem,
			LineItemType src, boolean isUpdateRequest) {
		if (destLineItem == null)
			return;

		if ((src == null) || (src.getSvcAddressExtId() == null))
			return;
		copyAttributesServiceAddress(destLineItem, src, isUpdateRequest);
	}

	/**
	 * @param src
	 *            Source
	 * @param entityManagerReference
	 *            Entity Manager Factory
	 * @param isUpdateRequest
	 *            TODO
	 * @return Domain Line Items
	 */
	public LineItem build(final LineItemType src, boolean isUpdateRequest,
			String agentId) {

		LineItem destLineItem = null;

		// We will unmarshall only those lineitems which are validated by
		// Product Service
		// if (src != null && src.getIsPersistable()) {
		if (isUpdateRequest) {
			// Retrieve existing lineItem from db based on // LineItemNumber
		} else {
			destLineItem = new LineItem();
		}

		copyBasicLineItemInfo(destLineItem, src, isUpdateRequest);
		copyLineItemAttributes(destLineItem, src, isUpdateRequest);
		copyBillingLineItemInfo(destLineItem, src, isUpdateRequest);
		copyAttributesServiceAddress(destLineItem, src, isUpdateRequest);
		copyLineItemPriceInfo(destLineItem, src, false);
		if (!isUpdateRequest)
			copyLineItemDetail(destLineItem, src, false);
		copySelectedFeatureValues(destLineItem, src, isUpdateRequest);
		copyActiveDialogues(destLineItem, src);
		copyStatusRecordBean(destLineItem, src, isUpdateRequest, agentId);
		copyStatusHistory(destLineItem, src, isUpdateRequest, agentId);
		copyCustomSelection(destLineItem, src, isUpdateRequest);
		copyPaymentEvents(destLineItem, src, isUpdateRequest);
		// Assign actual Market Item external id from details to transient
		// variable "marketItemExtId" at lineitem level

		return destLineItem;
		// }

		// return destLineItem;

	}

	/**
	 * Method to unmarshall extra lineitem information provided from client
	 * 
	 * @param destLineItem
	 * @param src
	 * @param isUpdateRequest
	 */
	public void copyLineItemAttributes(LineItem destLineItem, LineItemType src,
			boolean isUpdateRequest) {
		logger.debug("Unmarshalling lineitem attributes");
		Set<LineItemAttribute> destInfoList = new HashSet<LineItemAttribute>();
		if (src.getLineItemAttributes() != null) {
			LineItemAttributeType liAttribType = src.getLineItemAttributes();
			List<AttributeEntityType> attribListType = liAttribType
					.getEntityList();
			for (AttributeEntityType attribType : attribListType) {
				List<AttributeDetailType> valueList = attribType
						.getAttributeList();
				String source = attribType.getSource();
				for (AttributeDetailType detailType : valueList) {
					LineItemAttribute entity = new LineItemAttribute();
					entity.setSource(source);
					entity.setName(detailType.getName());
					entity.setValue(detailType.getValue());
					entity.setDescription(detailType.getDescription());
					destInfoList.add(entity);
				}

			}

			// If request is of type update then only update existing attributes
			// other wise just add the client provided attributes
			if (isUpdateRequest) {
				updateExistingLIAttributes(destLineItem, destInfoList);
			} else {
				destLineItem.setLineItemAttribute(destInfoList);
			}
		}
	}

	/**
	 * Helper method to update existing lineitem attributes
	 * 
	 * @param destLineItem
	 * @param destInfoList
	 */
	private void updateExistingLIAttributes(LineItem destLineItem,
			Set<LineItemAttribute> destInfoSet) {

		logger.debug("New or Updated Attributes : " + destInfoSet);
		Set<LineItemAttribute> finalAttrbList = new HashSet<LineItemAttribute>();

		Set<LineItemAttribute> existingAttributeList = destLineItem
				.getLineItemAttribute();

		for (LineItemAttribute existingAttribute : existingAttributeList) {

			Iterator<LineItemAttribute> newAttribIterator = destInfoSet
					.iterator();
			while (newAttribIterator.hasNext()) {
				LineItemAttribute newAttribute = newAttribIterator.next();
				logger.trace("New Attrib Name [" + newAttribute.getName()
						+ "] source [" + newAttribute.getSource() + "]");
				String extName = existingAttribute.getName();
				String extSource = existingAttribute.getSource();
				if (extName != null && extSource != null) {
					if (extName.equalsIgnoreCase(newAttribute.getName().trim())
							&& extSource.equalsIgnoreCase(newAttribute
									.getSource().trim())) {
						existingAttribute.setValue(newAttribute.getValue());
						existingAttribute.setDescription(newAttribute
								.getDescription() != null ? newAttribute
								.getDescription() : existingAttribute
								.getDescription());
						newAttribIterator.remove();
					}
				} else {
					logger.warn("Update request contains null attribute name or source");
				}
			}
		}

		finalAttrbList.addAll(destInfoSet);
		finalAttrbList.addAll(existingAttributeList);

		logger.debug("Final LineItem Attribute set : " + finalAttrbList);
		destLineItem.setLineItemAttribute(finalAttrbList);
	}

	private void copyStatusHistory(LineItem destLineItem, LineItemType src,
			boolean isUpdateRequest, String agentId) {

		if (src.getLineItemStatusHistory() != null) {
			LineItemStatusHistoryType srcStatHistory = src
					.getLineItemStatusHistory();
			List<LineItemStatusType> srcStatusList = srcStatHistory
					.getPreviousStatusList();
			List<StatusRecordBean> prevStatusList = new ArrayList<StatusRecordBean>();
			if (srcStatusList != null) {
				for (LineItemStatusType srcStatus : srcStatusList) {
					StatusRecordBean prevStatus = UnmarshallStatus
							.copyStatusRecordBean(srcStatus, agentId);
					prevStatusList.add(prevStatus);
				}
			}
			destLineItem.setHistoricStatus(prevStatusList);
		}

	}

	private void copyCustomSelection(LineItem destLineItem, LineItemType src,
			boolean isUpdateReq) {
		Set<CustomSelection> selections = UnmarshallCustomSelection
				.buildCustomSelection(src, destLineItem.getSelections(),
						Boolean.FALSE);

		// if(selections != null && !selections.isEmpty()) {
		// destLineItem.setSelections(selections);
		// }

		if (isUpdateReq) {
			if (selections != null && !selections.isEmpty()) {
				destLineItem.setSelections(selections);
			}
		} else {
			if (selections == null) {
				destLineItem.setSelections(Collections.EMPTY_SET);
			} else {
				destLineItem.setSelections(selections);
			}
		}

	}

	private void copyPaymentEvents(LineItem destLineItem,
			LineItemType lineItemType, boolean isUpdateRequest) {
		Set<CustomerPaymentEvent> paymentEvents = new HashSet<CustomerPaymentEvent>();
		if ((lineItemType.getPayments() != null)
				&& (lineItemType.getPayments().getPaymentEventList() != null)
				&& (lineItemType.getPayments().getPaymentEventList().size() > 0)) {
			paymentEvents = UnmarshallPaymentEvent
					.buildPaymentEvents(lineItemType.getPayments()
							.getPaymentEventList());
		}
		if(isUpdateRequest) {
			if(!paymentEvents.isEmpty()) {
				destLineItem.setPaymentEvents(paymentEvents);
			}
		} else {
			if(paymentEvents.isEmpty()) {
				destLineItem.setPaymentEvents(Collections.EMPTY_SET);
			} else {
				destLineItem.setPaymentEvents(paymentEvents);
			}
		}
		
	}

	private void copyActiveDialogues(LineItem destLineItem, LineItemType src) {
		Set<SelectedDialogue> activeDialogues = UnmarshallSelectedDialogue
				.buildSelectedDialogue(src, destLineItem.getDialogues());
		destLineItem.setDialogues(activeDialogues);
	}

	/**
	 * @param lineitemCollection
	 *            Input Source
	 * @param entityManagerReference
	 *            Entity Manager Reference
	 * @return List of Domain Line Item Beans
	 */
	public List<LineItem> buildLineItem(
			final LineItemCollectionType lineitemCollection, String agentId) {

		List<LineItem> list = new ArrayList<LineItem>();
		List<LineItemType> lineItemArray = lineitemCollection.getLineItemList();

		if (lineItemArray != null) {

			for (LineItemType lineItemType : lineItemArray) {
				LineItem item = build(lineItemType, false, agentId);

				if (item != null) {
					list.add(item);
				}
			}
		}

		return list;
	}

	public LineItem buildLineItem(final LineItemType lineItemType,
			String agentId) {

		// TODO fix the hardcoded agent id
		return build(lineItemType, false, agentId);
	}

	/**
	 * @param lineitemCollection
	 *            Input Source
	 * @param em
	 *            Entity Manager Reference
	 * @return Domain Line Item Beans
	 */
	public List<LineItem> build(
			final LineItemCollectionType lineitemCollection,
			final EntityManager em, String agentId) {
		return buildLineItem(lineitemCollection, agentId);
	}

	/*
	 * public PartyService getPartyService() { return partyService; }
	 * 
	 * public void setPartyService(PartyService partyService) {
	 * this.partyService = partyService; }
	 */

	/*
	 * public CatalogProductService getService() { return service; }
	 * 
	 * public void setService(CatalogProductService service) { this.service =
	 * service; }
	 */

	public UnmarshallLineItemDetail getUnmarshallLineItemDetail() {
		return unmarshallLineItemDetail;
	}

	public void setUnmarshallLineItemDetail(
			UnmarshallLineItemDetail unmarshallLineItemDetail) {
		this.unmarshallLineItemDetail = unmarshallLineItemDetail;
	}
	
	private void copyACESData(LineItem destLineItem,
			LineItemType lineItemType) {
		Set<ReasonBean> reasons = new HashSet<ReasonBean>();
		Set<CoachingBean> coachings = new HashSet<CoachingBean>();
		long lineItemId = destLineItem.getId();
		if (lineItemType.getReasons() != null) {
			List<ReasonList> list = lineItemType.getReasons().getReasonList(); 
			for(ReasonList reason: list){
				ReasonBean reasonBean = new ReasonBean();
				reasonBean.setReasonCategoryId(reason.getReasonCategory().getId());
				reasonBean.setReasonTypeId(reason.getReasonType().getId());
				reasonBean.setReasonId(reason.getReasonDesc().getId());
				reasonBean.setLineItemId(lineItemId);
				reasonBean.setReasonCategory(reason.getReasonCategory().getValue());
				reasonBean.setReasonDesc(reason.getReasonDesc().getValue());
				reasonBean.setPriorityId(reason.getPriority());
				reasons.add(reasonBean);
			}
			destLineItem.setReasons(reasons);
		}
		if (lineItemType.getCoachings() != null) {
			List<CoachingList> list = lineItemType.getCoachings().getCoachingList(); 
			for(CoachingList coaching: list){
				CoachingBean coachingBean = new CoachingBean();
				coachingBean.setAgentId(coaching.getAgentId());
				coachingBean.setCoachingGroupId(coaching.getCoachingType().getId());
				coachingBean.setCoachingId(coaching.getCoachingDesc().getId());
				coachingBean.setLineItemId(lineItemId);
				coachingBean.setCoachingType(coaching.getCoachingType().getValue());
				coachingBean.setCoachingDesc(coaching.getCoachingDesc().getValue());
				coachings.add(coachingBean);
			}
			destLineItem.setCoachings(coachings);
		}
	}

}
