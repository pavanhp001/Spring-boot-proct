package com.AL.ui.factory;

import static com.AL.ui.constants.Constants.CREDIT_CARD_EXP_FORMAT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.AL.ui.builder.CustomerInfoVOBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.exception.InvalidDataException;
import com.AL.ui.exception.InvalidFormatException;
import com.AL.ui.service.V.AddressService;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.CustomerServiceUI;
import com.AL.ui.service.V.ESEService;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.VOperation;
import com.AL.ui.util.OrderUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.AddressCollectionVo;
import com.AL.ui.vo.AddressFields;
import com.AL.ui.vo.CustomerInfoVO;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.vo.OrderQualVO;
import com.AL.util.DateUtil;
import com.AL.V.factory.CustomerFactory;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.CustomerContextType;
import com.AL.xml.se.v4.ProcessingMessage;
import com.AL.xml.se.v4.ServiceabilityEnterpriseResponse;
import com.AL.xml.se.v4.ServiceabilityResponse2;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.BillingInfoList;
import com.AL.xml.v4.CreditCardTypeType;
import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.CustBillingInfoType;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.CustomerType;
import com.AL.xml.v4.DateTimeOrTimeRangeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.RoleType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SchedulingInfoType;

/**
 * @author prabhu sekhar tripuraneni
 *
 */
public enum PrepopulateDialogue {

	INSTANCE;
	private static final Logger logger = Logger.getLogger(PrepopulateDialogue.class);
	public static final ObjectFactory oFactory = new ObjectFactory();
	public static final String CREDIT_CARD = "credit_card";


	/**
	 * checks the order whether the customer information is saved or not, if the customer information is saved,
	 * a map with valuetarget as key and the corresponding saved value as value is built
	 * @param order
	 * @return Map<String, String>
	 */
	public Map<String, String> buildPreSelectedValues(OrderType order){

		logger.info("inside prepopulate dialogue method");

		/*
		 * preselectedMap contains the valueTarget as key and corresponding saved value as value
		 */
		Map<String, String> preSelectedMap = new HashMap<String, String>();

		if(order != null){
			logger.info("Customer External ID ::: "+order.getCustomerInformation().getCustomer().getExternalId());
			/**
			 * retrieving the customer information from the order
			 */
			com.AL.xml.cm.v4.CustomerType customer = CustomerServiceUI.INSTANCE.getCustomer(String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()));
			logger.info("Getting customer information");

			/**
			 * retrieving the address details that are present for the customer
			 */
			List<com.AL.xml.cm.v4.CustAddress> custAddressList = customer.getAddressList().getCustomerAddress(); 
			logger.info("Getting customer address List");

			/**
			 * retrieving the billing info details of the customer
			 */
			List<com.AL.xml.cm.v4.CustBillingInfoType> custBillingInfoTypeList = customer.getBillingInfoList().getBillingInfo();
			logger.info("Getting billing info List");

			/*
			 * iterate over the customer billing info and set ccNumber, ccType, ccDate to preselectedMap
			 */
			for(com.AL.xml.cm.v4.CustBillingInfoType billingInfo : custBillingInfoTypeList){
				if(!(billingInfo.getBillingUniqueId().equals("Customer-ccPayinfo"))){
					if(billingInfo.getCreditCardNumber() != null){
						preSelectedMap.put("consumerFinancialInfo.creditCard.number",	billingInfo.getCreditCardNumber());
					}
					if(billingInfo.getCreditCardType() != null){

						com.AL.xml.cm.v4.CreditCardTypeType cardType = billingInfo.getCreditCardType();

						String creditCardType = ""; 

						if(cardType.value().equalsIgnoreCase("AMERICAN EXPRESS")){
							creditCardType = "AMERICAN EXPRESS";
						}
						else if(cardType.value().equalsIgnoreCase("DINER'S CLUB")){
							creditCardType = "DINER'S CLUB";
						}
						else if(cardType.value().equalsIgnoreCase("DISCOVER")){
							creditCardType = "DISCOVER";
						}
						else if(cardType.value().equalsIgnoreCase("MASTER CARD")){
							creditCardType = "MASTERCARD";
						}
						else if(cardType.value().equalsIgnoreCase("OPTIMA")){
							creditCardType = "OPTIMA";
						}
						else if(cardType.value().equalsIgnoreCase("VISA")){
							creditCardType = "VISA";
						}

						preSelectedMap.put("consumerFinancialInfo.cardType", creditCardType);
					}
					//logger.info("Credit Card Exp Date ::::: "+billingInfo.getExpirationYearMonth());
					if(billingInfo.getExpirationYearMonth() != null){
						preSelectedMap.put("consumerFinancialInfo.creditCard.expirationDate", String.valueOf(billingInfo.getExpirationYearMonth()));
					}
				}
			}

			/*
			 * customer movedate
			 */
			if(order.getMoveDate() != null){
				String movinDate = DateUtil.toDateString(order.getMoveDate());
				preSelectedMap.put("consumer.serviceAddress.moveInDate", movinDate);
			}
			/*
			 * customer dateOfBirth
			 */
			if(order.getCustomerInformation().getCustomer().getDob() != null){
				String movinDate = DateUtil.toDateString(customer.getDob());
				preSelectedMap.put("consumer.dateOfBirth",	movinDate);
			}

			/*
			 * customer best email contact
			 */
			if(order.getCustomerInformation().getCustomer().getHomeEMail() != null){
				preSelectedMap.put("consumer.homeEmail.emailAddressValueType",	customer.getBestEmailContact());
			}
			
			/*
			 * customer First Name
			 */
			if(order.getCustomerInformation().getCustomer().getFirstName() != null){
				preSelectedMap.put("consumer.firstName",customer.getFirstName());
			}
			
			/*
			 * customer Last Name
			 */
			if(order.getCustomerInformation().getCustomer().getLastName() != null){
				preSelectedMap.put("consumer.lastName",	customer.getLastName());
			}

			/*
			 * customer bestContactNumber
			 * in static CKO, best contact number will be one of the following
			 * work or home or cell.
			 * the customer need to select one of these and need to give a number of corresponding type
			 * eg: customer can select a best phone number as home is select box, 
			 * give the best contact number in the corresponding text box
			 */

			String bestContactNumber = customer.getBestPhoneContact();
			if(bestContactNumber != null && bestContactNumber.trim().length()>0){

				String cellPhoneNumber = customer.getCellPhoneNumber().getValue();
				String workPhoneNumber = customer.getWorkPhoneNumber().getValue();
				String homePhoneNumber = customer.getHomePhoneNumber().getValue();

				if(cellPhoneNumber != null && bestContactNumber.equalsIgnoreCase(cellPhoneNumber) && bestContactNumber.trim().length()>0){

					preSelectedMap.put("consumer.bestContactNumberType", "Cell");
					preSelectedMap.put("consumer.cellPhoneNumber",	customer.getBestPhoneContact());
					preSelectedMap.put("consumer.bestContactNumber", customer.getBestPhoneContact());

				}else if(workPhoneNumber != null && bestContactNumber.equalsIgnoreCase(workPhoneNumber) && bestContactNumber.trim().length()>0){

					preSelectedMap.put("consumer.bestContactNumberType", "Work");
					preSelectedMap.put("consumer.workPhoneNumber",	customer.getBestPhoneContact());
					preSelectedMap.put("consumer.bestContactNumber", customer.getBestPhoneContact());

				}else if(homePhoneNumber != null && bestContactNumber.equalsIgnoreCase(homePhoneNumber) && bestContactNumber.trim().length()>0){
					preSelectedMap.put("consumer.bestContactNumberType", "Home");
					preSelectedMap.put("consumer.homePhoneNumber",	customer.getBestPhoneContact());
					preSelectedMap.put("consumer.bestContactNumber", customer.getBestPhoneContact());
				}
				else if(bestContactNumber!= null && bestContactNumber.trim().length()>0){
					preSelectedMap.put("consumer.homePhoneNumber",	customer.getBestPhoneContact());
					preSelectedMap.put("consumer.bestContactNumber", customer.getBestPhoneContact());
				}
			}

			/*
			 * customer SSN
			 */
			if(order.getCustomerInformation().getCustomer().getSsn() != null)
				preSelectedMap.put("consumer.socialSecurityNumber",	customer.getSsn());

			/*
			 * customer driver license
			 */
			if(order.getCustomerInformation().getCustomer().getDriverLicense().getState() != null){
				preSelectedMap.put("consumer.identification.driverLicense.state",	customer.getDriverLicense().getState());
			}
			/*
			 * customer driver license state
			 */
			if(order.getCustomerInformation().getCustomer().getDriverLicense().getLicenseNumber() != null){
				preSelectedMap.put("consumer.identification.driverLicense.number",	customer.getDriverLicense().getLicenseNumber());
			}

			/*
			 * iterating over customer address list and adding the value targets and corresponding values to map
			 */
			if(custAddressList != null){

				for(com.AL.xml.cm.v4.CustAddress custAddr : custAddressList){
					if(custAddr != null){

						for(com.AL.xml.cm.v4.RoleType roleType : custAddr.getAddressRoles().getRole()){

							if(roleType.name().equals("PREVIOUS_ADDRESS")){

								if(custAddr.getAddress().getStreetNumber() != null && custAddr.getAddress().getStreetName()!= null){ 

									String prevAddrKey = "consumer.previousAddress.dwelling.prefixDirectional ||'sp'|| consumer.previousAddress.dwelling.streetNumber ||'sp'|| consumer.previousAddress.dwelling.streetName ||'sp'|| consumer.previousAddress.dwelling.streetType";	

									String prevAddrValue = "";

									if(custAddr.getAddress().getPrefixDirectional() != null){
										prevAddrValue = custAddr.getAddress().getPrefixDirectional();
										prevAddrValue += " ";
									}

									prevAddrValue = custAddr.getAddress().getStreetNumber() + " "+ custAddr.getAddress().getStreetName();

									if(custAddr.getAddress().getStreetType() != null){
										prevAddrValue += " ";
										prevAddrValue += custAddr.getAddress().getStreetType();				
									}
									preSelectedMap.put(prevAddrKey, prevAddrValue);	
								}

								if(custAddr.getAddress().getLine2() != null){
									preSelectedMap.put("consumer.previousAddress.dwelling.line2", custAddr.getAddress().getLine2());
								}

								if(custAddr.getAddress().getCity() != null){

									preSelectedMap.put("consumer.previousAddress.dwelling.city", custAddr.getAddress().getCity());
								}

								if(custAddr.getAddress().getCity() != null){

									preSelectedMap.put("consumer.previousAddress.dwelling.stateOrProvince", custAddr.getAddress().getStateOrProvince());
								}

								if(custAddr.getAddress().getCity() != null){

									preSelectedMap.put("consumer.previousAddress.dwelling.postalCode", custAddr.getAddress().getPostalCode());
								}	
							}
							else if(roleType.name().equals("BILLING_ADDRESS")){
								if(custAddr.getAddress().getStreetNumber() != null && custAddr.getAddress().getStreetName()!= null ){
									String prevAddrKey = "consumer.billingAddress.dwelling.prefixDirectional ||'sp'|| consumer.billingAddress.dwelling.streetNumber ||'sp'|| consumer.billingAddress.dwelling.streetName ||'sp'|| consumer.billingAddress.dwelling.streetType";	

									String prevAddrValue = "";

									if(custAddr.getAddress().getPrefixDirectional() != null){
										prevAddrValue = custAddr.getAddress().getPrefixDirectional();
										prevAddrValue += " ";
									}

									prevAddrValue = custAddr.getAddress().getStreetNumber() + " "+ custAddr.getAddress().getStreetName();

									if(custAddr.getAddress().getStreetType() != null){
										prevAddrValue += " ";
										prevAddrValue += custAddr.getAddress().getStreetType();				
									}
									preSelectedMap.put(prevAddrKey, prevAddrValue);
								}

								if(custAddr.getAddress().getLine2() != null && custAddr.getAddress().getLine2().indexOf(" ") > 0){
									String line2Info = custAddr.getAddress().getLine2();
									String[] line2Arr = line2Info.split(" ");
									if(line2Arr.length >= 2){
										preSelectedMap.put("consumer.billingAddress.dwelling.line2", line2Arr[0]);
										String lineInfoStr = "";
										for(int i=1;i<line2Arr.length;i++){
											lineInfoStr += line2Arr[i];
										}
										preSelectedMap.put("consumer.billingAddress.dwelling.line2info", lineInfoStr);
									}
								}

								if(custAddr.getAddress().getCity() != null){

									preSelectedMap.put("consumer.billingAddress.dwelling.city", custAddr.getAddress().getCity());
								}

								if(custAddr.getAddress().getCity() != null){

									preSelectedMap.put("consumer.billingAddress.dwelling.stateOrProvince", custAddr.getAddress().getStateOrProvince());
								}

								if(custAddr.getAddress().getCity() != null){

									preSelectedMap.put("consumer.billingAddress.dwelling.postalCode", custAddr.getAddress().getPostalCode());
								}
							}
							else if(roleType.name().equals("SHIPPING_ADDRESS")){
								String prevAddrKey = "consumer.shippingAddress.dwelling.prefixDirectional ||'sp'|| consumer.shippingAddress.dwelling.streetNumber ||'sp'|| consumer.shippingAddress.dwelling.streetName ||'sp'|| consumer.shippingAddress.dwelling.streetType";

								String prevAddrValue = "";

								if(custAddr.getAddress().getPrefixDirectional() != null){
									prevAddrValue = custAddr.getAddress().getPrefixDirectional();
									prevAddrValue += " ";
								}

								prevAddrValue = custAddr.getAddress().getStreetNumber() + " "+ custAddr.getAddress().getStreetName();

								if(custAddr.getAddress().getStreetType() != null){
									prevAddrValue += " ";
									prevAddrValue += custAddr.getAddress().getStreetType();				
								}
								preSelectedMap.put(prevAddrKey, prevAddrValue);

								if(custAddr.getAddress().getLine2() != null){
									preSelectedMap.put("consumer.shippingAddress.dwelling.line2", custAddr.getAddress().getLine2());
								}
								if(custAddr.getAddress().getCity() != null){
									preSelectedMap.put("consumer.shippingAddress.dwelling.city", custAddr.getAddress().getLine2());
								}
								if(custAddr.getAddress().getCity() != null){
									preSelectedMap.put("consumer.shippingAddress.dwelling.stateOrProvince", custAddr.getAddress().getStateOrProvince());
								}
								if(custAddr.getAddress().getCity() != null){
									preSelectedMap.put("consumer.shippingAddress.dwelling.postalCode", custAddr.getAddress().getPostalCode());
								}
							}
						}
					}
				}
			}
		}
		//logger.info("Pre selected map --> "+preSelectedMap);

		return preSelectedMap;
	}

	public OrderType updateValuesToCustomer(OrderType order, List<DataField> dataFieldList, 
			Map<String, String> resultParamMap, 
			OrderQualVO orderQualVO, long lineItemExternalID, AttributeEntityType entity, 
			AttributeEntityType firstTimeEntity, SalesContextType context, Map<String, String> requestParamMap, String extSelectedValues, SchedulingInfoType schedulingInfo){

		String billingAddressUniqueId = String.valueOf(System.currentTimeMillis());

		String serviceAddrUniqueId = "";

		DateTimeOrTimeRangeType desiredStartDateTimeRange = oFactory.createDateTimeOrTimeRangeType();

		CustomerInfoVO customerInfoVO = CustomerInfoVOBuilder.buildCustomerInfoVO(resultParamMap, null);

		Map<String, String> billingInfoMap = new HashMap<String, String>();

		AddressFields addrFields = new AddressFields();

		String[] constants = new String[1];

		Customer customer = null;

		com.AL.xml.cm.v4.CustBillingInfoType billingInfoType = null;

		String customerExternalID = orderQualVO.getCustomerExternalId();
		boolean isBillingAddressPresent = false;

		com.AL.xml.cm.v4.CustomerType customerService = CustomerServiceUI.INSTANCE.getCustomer(customerExternalID);

		if(order.getCustomerInformation() != null && order.getCustomerInformation().getCustomer() != null){
			customer = order.getCustomerInformation().getCustomer();
		}
		for(DataField df : dataFieldList){	

			String valueTarget = df.getValueTarget();
			if(valueTarget != null && !(valueTarget.length() <= 0)){
				if(valueTarget.equals("consumer.billingAddress.dwelling.prefixDirectional ||'sp'|| consumer.billingAddress.dwelling.streetNumber ||'sp'|| consumer.billingAddress.dwelling.streetName ||'sp'|| consumer.billingAddress.dwelling.streetType")){
					isBillingAddressPresent = true;
				}

				if(resultParamMap.get(df.getExternalId()) != null || requestParamMap.get(df.getExternalId()+"_CCExpDate") != null ){

					if(valueTarget.equals("consumerFinancialInfo.creditCard.number") && extSelectedValues != null && extSelectedValues.contains(df.getExternalId())){
						String ccNumber = resultParamMap.get(df.getExternalId());
						if(!Utils.isBlank(ccNumber)){
							if(ccNumber.indexOf("*") >= 0){
								String jsonVal = requestParamMap.get(df.getExternalId()+"_JSONVAL");
								try {
									JSONObject selectedValueJSONObject = new JSONObject(jsonVal);
									ccNumber = selectedValueJSONObject.getString("enteredValue");
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							billingInfoMap.put("CCNumber", ccNumber);
						}
					}
					if(valueTarget.equals("consumerFinancialInfo.cardType") && extSelectedValues != null && extSelectedValues.contains(df.getExternalId())){
						String cardType = resultParamMap.get(df.getExternalId());
						if(!Utils.isBlank(cardType)){
							billingInfoMap.put("CCType", cardType);
						}
					}
					if(valueTarget.equals("consumerFinancialInfo.creditCard.expirationDate")){
						if(extSelectedValues != null && (extSelectedValues.indexOf("MonthDD") >= 0) && (extSelectedValues.indexOf("YearDD") >= 0)){
							try {
								String month = requestParamMap.get("MonthDD_EXPIRATIONDATE_JSONVAL");
								String year = requestParamMap.get("YearDD_EXPIRATIONDATE_JSONVAL");
								if(month != null && year != null){
									JSONObject monthJSON = new JSONObject(month);
									JSONObject yearJSON = new JSONObject(year);
									if(!monthJSON.isNull("enteredValue") && !yearJSON.isNull("enteredValue")){
										//logger.info("Month / Year "+(monthJSON.getString("enteredValue")+"/"+yearJSON.getString("enteredValue")));
										billingInfoMap.put("CCExpDate", (monthJSON.getString("enteredValue")+"/"+yearJSON.getString("enteredValue")));
									}
								}

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}


						}
					}
					if(valueTarget.equals("consumerFinancialInfo.creditCard.name") && extSelectedValues != null && extSelectedValues.contains(df.getExternalId())){
						String ccName = resultParamMap.get(df.getExternalId());
						if(!Utils.isBlank(ccName)){
								String jsonVal = requestParamMap.get(df.getExternalId()+"_JSONVAL");
								try {
									JSONObject selectedValueJSONObject = new JSONObject(jsonVal);
									ccName = selectedValueJSONObject.getString("enteredValue");
								} catch (JSONException e) {
									e.printStackTrace();
								}
							billingInfoMap.put("CCName", ccName);
						}
					}

					if(valueTarget.equals("consumer.serviceAddress.moveInDate")){

						String result = resultParamMap.get(df.getExternalId());
						if(result.endsWith(":00")){
							result = result.substring(0, result.indexOf(":00"));
						}

						XMLGregorianCalendar gregInstallDate = DateUtil.asXMLGregorianCalendar(result, Constants.CREDIT_CARD_EXP_FORMAT);

						order.setMoveDate(gregInstallDate);
					}

					if(valueTarget.equals("consumer.dateOfBirth")) {
						String result = resultParamMap.get(df.getExternalId());
						if(result.indexOf("*") >= 0){
							String jsonVal = requestParamMap.get(df.getExternalId()+"_JSONVAL");
							try {
								JSONObject selectedValueJSONObject = new JSONObject(jsonVal);
								result = selectedValueJSONObject.getString("enteredValue");
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						if(result.endsWith(":00")){
							result = result.substring(0, result.indexOf(":00"));
						}
						customerInfoVO.setDob(result);
					}

					if(valueTarget.equals("consumer.homeEmail.emailAddressValueType")){
						customerInfoVO.setEmailAddress(resultParamMap.get(df.getExternalId()));
					}

					if(valueTarget.equals("consumer.bestContactNumber")){
						String valueTar = ""; 
						if(resultParamMap.get(df.getExternalId()).equalsIgnoreCase("home")){
							valueTar = "consumer.homePhoneNumber";
						}
						else if(resultParamMap.get(df.getExternalId()).equalsIgnoreCase("cell")){
							valueTar = "consumer.cellPhoneNumber";
						}
						else if(resultParamMap.get(df.getExternalId()).equalsIgnoreCase("work")){
							valueTar = "consumer.workPhoneNumber";
						}
						for(DataField dataField : dataFieldList){
							if(dataField.getValueTarget().equals(valueTar)){
								if(resultParamMap.get(dataField.getExternalId()) != null){
									customerInfoVO.setBtn(resultParamMap.get(dataField.getExternalId()));
								}
							}
						}
					}

					logger.info(df.getExternalId()+" ::::::: "+extSelectedValues.contains(df.getExternalId()));

					if(valueTarget.equals("consumer.homePhoneNumber") && extSelectedValues.contains(df.getExternalId())){
						customerInfoVO.setHomePhoneNumber(resultParamMap.get(df.getExternalId()));
					}

					if(valueTarget.equals("consumer.cellPhoneNumber") && extSelectedValues.contains(df.getExternalId())){
						customerInfoVO.setCellPhoneNumber(resultParamMap.get(df.getExternalId()));
					}
					if(valueTarget.equals("consumer.workPhoneNumber") && extSelectedValues.contains(df.getExternalId())){
						customerInfoVO.setWorkPhoneNumber(resultParamMap.get(df.getExternalId()));
					}

					if(valueTarget.equals("consumer.socialSecurityNumber")){
						String socialSecurity = resultParamMap.get(df.getExternalId());
						if(socialSecurity.contains("*")){
							String jsonVal = requestParamMap.get(df.getExternalId()+"_JSONVAL");
							JSONObject jsonObject;
							try {
								jsonObject = new JSONObject(jsonVal);
								socialSecurity = jsonObject.getString("enteredValue");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						customerInfoVO.setSsn(socialSecurity);						
					}
					if(valueTarget.equals("consumer.identification.driverLicense.state")){
						customerInfoVO.setState(resultParamMap.get(df.getExternalId()));
					}

					if(valueTarget.equals("consumer.identification.driverLicense.number")){
						customerInfoVO.setDriverLicense(resultParamMap.get(df.getExternalId()));
					}

					if(valueTarget.equals("lineItem.schedulingInfo.desiredStartDate.date")){
						String desiredStartDate = resultParamMap.get(df.getExternalId());
						desiredStartDateTimeRange.setDate(DateUtil.asXMLGregorianCalendar(desiredStartDate, "MM/dd/yyyy"));
					}

					if(valueTarget.equals("lineItem.schedulingInfo.desiredStartDate2.date") || valueTarget.equals("lineItem.schedulingInfo.desiredStartDate2.time")){
						entity.setSource("SECOND_DESIRED_DATE");
						if(valueTarget.equals("lineItem.schedulingInfo.desiredStartDate2.date")){
							AttributeDetailType attr = oFactory.createAttributeDetailType();
							attr.setName("DATE");
							String dateString = resultParamMap.get(df.getExternalId());
							String formattedDate = Utils.changeDateFormat("MM/dd/yyyy", "yyyy-MM-dd", dateString);
							attr.setValue(formattedDate);
							attr.setDescription("Customer second installation date");
							entity.getAttribute().add(attr);

						}
						else if(valueTarget.equals("lineItem.schedulingInfo.desiredStartDate2.time")){
							AttributeDetailType attr = oFactory.createAttributeDetailType();
							attr.setName("TIME");
							attr.setDescription("Customer second installation time");
							attr.setValue(resultParamMap.get(df.getExternalId()));
							entity.getAttribute().add(attr);
						}
					}

					if(valueTarget.equals("lineItem.schedulingInfo.desiredStartDate.time")){
						firstTimeEntity.setSource("FIRST_INSTALLATION_TIME");
						AttributeDetailType attr = oFactory.createAttributeDetailType();
						attr.setName("TIME");
						attr.setDescription("Customer first installation time");
						attr.setValue(resultParamMap.get(df.getExternalId()));
						firstTimeEntity.getAttribute().add(attr);
					}
					logger.info("VALUE TARGET :::::: "+valueTarget);

					/*
					 * adding the address values from the request to AddressFields
					 */
					buildAddressFields(valueTarget, resultParamMap, addrFields, df, extSelectedValues, requestParamMap);
				}
			}
		}

		//setting the desired start date
		schedulingInfo.setDesiredStartDate(desiredStartDateTimeRange);

		//logger.info("Billing INFO MAP :::::::::::::: "+billingInfoMap);

		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		Map<String, String> sourceEntity = new HashMap<String, String>();
		sourceEntity.put("source", "CKO_static");
		sourceEntity.put("GUID", orderQualVO.getGUID());
		data.put("source", sourceEntity);
		CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext(data);

		BillingInfoList newBillingInfoList = customer.getBillingInfoList();
		List<AddressType> addressTypeList = new ArrayList<AddressType>();

		boolean billingAddressSameAsSvcAddr = false;
		if(addrFields != null){
			/*
			 * building List<AddressType> from AddressFields
			 */
			billingAddressSameAsSvcAddr = buildAddress(addrFields, addressTypeList);

			/*
			 * adding a billing address role to service address when the customer says billing address is same as service address
			 */
			if(isBillingAddressPresent) {
				if(billingAddressSameAsSvcAddr){
					List<CustAddress> customerAddrList = customer.getAddressList().getCustomerAddress();
					for(CustAddress custAddr: customerAddrList){
						for(RoleType roles : custAddr.getAddressRoles().getRole()){
							if(roles.name().equals("SERVICE_ADDRESS")){
								billingAddressUniqueId =  custAddr.getAddressUniqueId();
								serviceAddrUniqueId = custAddr.getAddressUniqueId(); 
								for(CustBillingInfoType custBillingInfoType : customer.getBillingInfoList().getBillingInfo()){
									if(custBillingInfoType.getAddressRef().equals(billingAddressUniqueId)){
										billingInfoType = createCustBillingInfoType(custBillingInfoType); 
									}
								}
								// adding billing address role to service address
								buildCmV4AddressObject(customer.getExternalId(), orderQualVO, customerContext);
							}
						}
					}
				}
			}

			/*
			 *	checks whether the credit card details are changed,
			 */
			boolean isCreditCardDetailsChanged = isCreditCardDetailsChanged(newBillingInfoList, billingInfoMap);
			logger.info("isCreditCardDetailsChanged ::::::::: "+isCreditCardDetailsChanged);

			/*
			 *	checks whether the billing address details are changed,
			 */
			boolean isBillingAddressChanged = isBillingAddressChanged(addressTypeList, customer.getAddressList().getCustomerAddress());
			logger.info("isBillingAddressChanged :::::::::::: "+isBillingAddressChanged);

			/*
			 * If credit Card Details are changed:
			 * add new BillingInfoType with the changed CreditCardDetails 
			 * create new BillingAddressEntity whether the billing address is changed or not
			 */
			if(isCreditCardDetailsChanged || isBillingAddressChanged){
				addNewBillingAddressForChangedCCDetails(newBillingInfoList, billingInfoMap, addressTypeList, 
						billingAddressUniqueId, customerContext, customerService, billingInfoType, orderQualVO, context);
			}
			else{

				/*
				 * billing info details are not available and Billing address is same as 
				 * Service address when billing address is available on customer address list...
				 */

				if(!isCreditCardDetailsChanged && !isBillingAddressChanged && billingAddressSameAsSvcAddr){

					String billingInfoExternalID = null;

					if(newBillingInfoList!=null && !newBillingInfoList.getBillingInfo().isEmpty()){
						for(CustBillingInfoType custBillingInfoType : newBillingInfoList.getBillingInfo()){
							if(custBillingInfoType.getAddressRef().equals(billingAddressUniqueId)){
								billingInfoExternalID = String.valueOf(custBillingInfoType.getExternalId());
							}
						}
					}
					// updating the line item with the billingInfo unique Id if the billing address is same as service address
					if(billingInfoExternalID != null && billingInfoExternalID.trim().length() > 0){
						LineItemType liType = orderQualVO.getLineItemType();
						LineItemType newLineItemType = oFactory.createLineItemType();

						newLineItemType.setExternalId(liType.getExternalId());
						newLineItemType.setLineItemNumber(liType.getLineItemNumber());
						newLineItemType.setBillingInfoExtId(billingInfoExternalID);

						LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
						logger.info("Adding line item to lone item collection type");
						liCollection.getLineItem().add(newLineItemType);
						logger.info("After setting item to line item collection type");
						ErrorList errorList = new ErrorList();

						LineItemService.INSTANCE.updateLineItem(orderQualVO.getAgentId(), orderQualVO.getOrderId(), 
								VOperation.updateLineItem.toString(), liCollection, context, errorList);
					}
				}

				/*
				 * When billing info details are same as previous billing info details...
				 */
				if(!isCreditCardDetailsChanged && !isBillingAddressChanged && billingInfoMap != null && !(billingInfoMap.isEmpty())){
					String billingInfoExternalID = null;
					String cardNumber = billingInfoMap.get("CCNumber");
					boolean isSameAsPreviousBillingInfo = false;
					for(CustBillingInfoType custBilling : newBillingInfoList.getBillingInfo()){
						//logger.info("CUSTOMER CC NUMBER ::::: "+custBilling.getCreditCardNumber());
						if(!Utils.isBlank(custBilling.getCreditCardNumber()) && custBilling.getCreditCardNumber().equals(cardNumber)){
							billingInfoExternalID = String.valueOf(custBilling.getExternalId()); 
							isSameAsPreviousBillingInfo = true;
						}
					}

					/*
					 * updating the line item billing info ref with the previous billing info external Id.
					 */
					if(isSameAsPreviousBillingInfo){
						LineItemType liType = orderQualVO.getLineItemType();
						LineItemType newLineItemType = oFactory.createLineItemType();

						if(billingInfoExternalID != null && billingInfoExternalID.trim().length() > 0){
							newLineItemType.setExternalId(liType.getExternalId());
							newLineItemType.setLineItemNumber(liType.getLineItemNumber());
							newLineItemType.setBillingInfoExtId(billingInfoExternalID);

							LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
							logger.info("Adding line item to lone item collection type");
							liCollection.getLineItem().add(newLineItemType);
							logger.info("After setting item to line item collection type");
							ErrorList errorList = new ErrorList();

							LineItemService.INSTANCE.updateLineItem(orderQualVO.getAgentId(), orderQualVO.getOrderId(), 
									VOperation.updateLineItem.toString(), liCollection, context, errorList);
						}
					}
				}

				for(AddressType addressType : addressTypeList){
					if(addressType.getAddressBlock().equals("BILLING_ADDR_ROLE_LIST")){

						addressType.setAddressBlock("");
						constants = Constants.BILLING_ADDR_ROLE_LIST;
						String addressUniqueId = String.valueOf(System.currentTimeMillis());
						AddressService.INSTANCE.saveNewAddress(orderQualVO.getAgentId(), orderQualVO.getCustomerExternalId(), 
								constants, null, addressUniqueId, addressType, "active", customerContext);

						/*
						 * Creating empty billing info details when we get billing address details without billing info details...
						 */

						com.AL.xml.cm.v4.CustomerType newCustomer = new com.AL.xml.cm.v4.CustomerType();
						com.AL.xml.cm.v4.CustBillingInfoType newBillingInfoType = createBillingInfo(billingInfoMap, addressUniqueId, billingInfoType, newBillingInfoList);
						com.AL.xml.cm.v4.BillingInfoList billingInfoList = new com.AL.xml.cm.v4.BillingInfoList();
						billingInfoList.getBillingInfo().add(newBillingInfoType);
						newCustomer.setBillingInfoList(billingInfoList);

						newCustomer.setDirectMailOptIn(customerService.isDirectMailOptIn());
						newCustomer.setPhoneContactOptIn(customerService.isPhoneContactOptIn());
						newCustomer.setEMailOptIn(customerService.isEMailOptIn());
						newCustomer.setEMailProductUpdatesOptIn(customerService.isEMailProductUpdatesOptIn());
						newCustomer.setMarketingOptIn(customerService.isMarketingOptIn());
						newCustomer.setNonBuyerWebOptIn(customerService.isNonBuyerWebOptIn());
						newCustomer = CustomerService.INSTANCE.submitCustomerType(orderQualVO.getAgentId(), String.valueOf(order.getCustomerInformation().getCustomer().getExternalId())
								, "updateCustomer", newCustomer, null, customerContext, new ErrorList());

						/*
						 * updating the line item billing info ref with the newly created billing info external Id.
						 */
						LineItemType liType = orderQualVO.getLineItemType();
						LineItemType newLineItemType = oFactory.createLineItemType();
						if(newCustomer != null){
							com.AL.xml.cm.v4.BillingInfoList updatedBillingInfoList = newCustomer.getBillingInfoList();
							String billingInfoExternalID = null;

							List<com.AL.xml.cm.v4.CustBillingInfoType> custBillingInfoList = updatedBillingInfoList.getBillingInfo();

							com.AL.xml.cm.v4.CustBillingInfoType custBillingInfo = getNewlyCreatedBillingInfoType(custBillingInfoList,newBillingInfoList.getBillingInfo());
							if(custBillingInfo!=null){
								billingInfoExternalID = String.valueOf(custBillingInfo.getExternalId()); 

								if(billingInfoExternalID != null && billingInfoExternalID.trim().length() > 0){
									newLineItemType.setExternalId(liType.getExternalId());
									newLineItemType.setLineItemNumber(liType.getLineItemNumber());
									newLineItemType.setBillingInfoExtId(billingInfoExternalID);

									/*
									 * explicitly making the reference to null to allow this object for GC
									 */
									liType = null;
									LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
									logger.info("Adding line item to lone item collection type");
									liCollection.getLineItem().add(newLineItemType);
									logger.info("After setting item to line item collection type");
									ErrorList errorList = new ErrorList();

									order = LineItemService.INSTANCE.updateLineItem(orderQualVO.getAgentId(), orderQualVO.getOrderId(), 
											VOperation.updateLineItem.toString(), liCollection, context, errorList);
								}
							}
						}
					}
				}
			}

			for(AddressType addressType : addressTypeList){
				if(addressType.getAddressBlock().equals("PREVIOUS_ADDR_ROLE_LIST") && 
						isPreviousAddressChanged(addressTypeList, customer.getAddressList().getCustomerAddress())){
					boolean isAddressExactMatch = false;
					isAddressExactMatch = setPreviousAddressBlock(addressType);
					constants = Constants.PREVIOUS_ADDR_ROLE_LIST;
					String addressUniqueId = String.valueOf(System.currentTimeMillis());
					if(addressType != null && addressType.getAddressBlock() != null && isAddressExactMatch){
						AddressService.INSTANCE.saveNewAddress(orderQualVO.getAgentId(), String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()), 
								constants, null, addressUniqueId, addressType, "active", customerContext);
						
					}
				}
				if(addressType.getAddressBlock().equals("SHIPPING_ADDR_ROLE_LIST") &&
						isShippingAddressChanged(addressTypeList, customer.getAddressList().getCustomerAddress())){
					addressType.setAddressBlock("");
					constants = Constants.SHIPPING_ADDR_ROLE_LIST;
					String addressUniqueId = String.valueOf(System.currentTimeMillis());
					AddressService.INSTANCE.saveNewAddress(orderQualVO.getAgentId(), String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()), 
							constants, null, addressUniqueId, addressType, "active", customerContext);
				}
			}
		}
		String agentId = order.getAgentId();

		if(customerInfoVO != null && isCustomerInfoEmpty(customerInfoVO) && order.getCustomerInformation() != null){

			com.AL.xml.cm.v4.CustomerType cust = CustomerInfoVOBuilder.buildCustomerType(customerInfoVO, 
					String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()), customer);

			cust.setDirectMailOptIn(customerService.isDirectMailOptIn());
			cust.setPhoneContactOptIn(customerService.isPhoneContactOptIn());
			cust.setEMailOptIn(customerService.isEMailOptIn());
			cust.setEMailProductUpdatesOptIn(customerService.isEMailProductUpdatesOptIn());
			cust.setMarketingOptIn(customerService.isMarketingOptIn());
			cust.setNonBuyerWebOptIn(customerService.isNonBuyerWebOptIn());
			CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(order.getCustomerInformation().getCustomer().getExternalId())
					, "updateCustomer", cust, null, customerContext, new ErrorList());
			order = OrderUtil.INSTANCE.returnOrderType(orderQualVO.getOrderId());
		}


		boolean containsMoreThanOneCC = containsMoreThanOneCard(order.getCustomerInformation().getCustomer().getBillingInfoList());

		if(!containsMoreThanOneCC && billingInfoMap != null && !(billingInfoMap.isEmpty())){

			if(!billingAddressUniqueId.equals(serviceAddrUniqueId)){
				boolean isBillingAddrAvailable = false;
				for(AddressType addressType : addressTypeList){
					if(addressType.getAddressBlock().equals("BILLING_ADDR_ROLE_LIST")){
						addressType.setAddressBlock("");
						constants = Constants.BILLING_ADDR_ROLE_LIST;
						isBillingAddrAvailable = true;
						AddressService.INSTANCE.saveNewAddress(agentId,  orderQualVO.getCustomerExternalId(), 
								constants, null, billingAddressUniqueId, addressType, "active", customerContext);
					}
				}
				if(!isBillingAddrAvailable){
					for(CustAddress custAddr: customer.getAddressList().getCustomerAddress()){
						for(RoleType roles : custAddr.getAddressRoles().getRole()){
							if(roles.name().equals("SERVICE_ADDRESS")){
								billingAddressUniqueId = custAddr.getAddressUniqueId();
								break;
							}
						}
					}
				}
			}

			com.AL.xml.cm.v4.CustomerType newCustomer = new com.AL.xml.cm.v4.CustomerType();
			com.AL.xml.cm.v4.CustBillingInfoType newBillingInfoType = createBillingInfo(billingInfoMap, billingAddressUniqueId, billingInfoType, newBillingInfoList);
			com.AL.xml.cm.v4.BillingInfoList billingInfoList = new com.AL.xml.cm.v4.BillingInfoList();
			billingInfoList.getBillingInfo().add(newBillingInfoType);
			newCustomer.setBillingInfoList(billingInfoList);

			newCustomer.setDirectMailOptIn(customerService.isDirectMailOptIn());
			newCustomer.setPhoneContactOptIn(customerService.isPhoneContactOptIn());
			newCustomer.setEMailOptIn(customerService.isEMailOptIn());
			newCustomer.setEMailProductUpdatesOptIn(customerService.isEMailProductUpdatesOptIn());
			newCustomer.setMarketingOptIn(customerService.isMarketingOptIn());
			newCustomer.setNonBuyerWebOptIn(customerService.isNonBuyerWebOptIn());
			newCustomer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(order.getCustomerInformation().getCustomer().getExternalId())
					, "updateCustomer", newCustomer, null, customerContext, new ErrorList());

			/*
			 * updating the line item billing info ref with the new billing info external Id.
			 */
			LineItemType liType = orderQualVO.getLineItemType();
			LineItemType newLineItemType = oFactory.createLineItemType();
			if(newCustomer != null){
				com.AL.xml.cm.v4.BillingInfoList updatedBillingInfoList = newCustomer.getBillingInfoList();
				String billingInfoExternalID = null;

				List<com.AL.xml.cm.v4.CustBillingInfoType> custBillingInfoList = updatedBillingInfoList.getBillingInfo();

				com.AL.xml.cm.v4.CustBillingInfoType custBillingInfo = getNewlyCreatedBillingInfoType(custBillingInfoList,newBillingInfoList.getBillingInfo());
				if(custBillingInfo!=null){
					billingInfoExternalID = String.valueOf(custBillingInfo.getExternalId()); 

					if(billingInfoExternalID != null && billingInfoExternalID.trim().length() > 0){
						newLineItemType.setExternalId(liType.getExternalId());
						newLineItemType.setLineItemNumber(liType.getLineItemNumber());
						newLineItemType.setBillingInfoExtId(billingInfoExternalID);

						LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
						logger.info("Adding line item to lone item collection type");
						liCollection.getLineItem().add(newLineItemType);
						logger.info("After setting item to line item collection type");
						ErrorList errorList = new ErrorList();

						order = LineItemService.INSTANCE.updateLineItem(orderQualVO.getAgentId(), orderQualVO.getOrderId(), 
								VOperation.updateLineItem.toString(), liCollection, context, errorList);
					}
				}
			}
		}
		return order;
	}


	private com.AL.xml.cm.v4.CustBillingInfoType getNewlyCreatedBillingInfoType(
			List<com.AL.xml.cm.v4.CustBillingInfoType> custBillingInfoList,
			List<CustBillingInfoType> billingInfo) {

		com.AL.xml.cm.v4.CustBillingInfoType infoType = null;

		for(com.AL.xml.cm.v4.CustBillingInfoType custBillingInfoType : custBillingInfoList){
			boolean isNewlyCreatedBillingInfo = true;
			for(CustBillingInfoType  billingInfoType : billingInfo){
				if(billingInfoType.getExternalId()==custBillingInfoType.getExternalId()){
					isNewlyCreatedBillingInfo = false;
					break;
				}
			}
			if(isNewlyCreatedBillingInfo){
				infoType = custBillingInfoType;
				break;
			}
		}

		return infoType;
	}

	/**
	 * method to save changed CreditCardDetails and billing address to the customer
	 * 
	 * @param newBillingInfoList
	 * @param billingInfoMap
	 * @param addressTypeList
	 * @param customerExternalId
	 * @param addressUniqueId
	 * @param customerContext
	 * @param agentId
	 * @param customerService
	 * @param billingInfoType
	 * @param orderQualVO 
	 * @param context 
	 */
	private void addNewBillingAddressForChangedCCDetails(BillingInfoList newBillingInfoList, Map<String, String> billingInfoMap, 
			List<AddressType> addressTypeList, String addressUniqueId, CustomerContextType customerContext, 
			com.AL.xml.cm.v4.CustomerType customerService, com.AL.xml.cm.v4.CustBillingInfoType billingInfoType, 
			OrderQualVO orderQualVO, SalesContextType context) {
		boolean isBiilingAddressAvailable = false;
		for(AddressType addressType : addressTypeList){
			if(addressType.getAddressBlock().equals("BILLING_ADDR_ROLE_LIST")){
				addressType.setAddressBlock("");
				String[] constants = Constants.BILLING_ADDR_ROLE_LIST;

				AddressService.INSTANCE.saveNewAddress(orderQualVO.getAgentId(), orderQualVO.getCustomerExternalId(), 
						constants, null, addressUniqueId, addressType, "active", customerContext);

				isBiilingAddressAvailable = true;

			}
		}
		com.AL.xml.cm.v4.CustBillingInfoType newBillingInfoType = null;

		if(!isBiilingAddressAvailable){
			for(com.AL.xml.cm.v4.CustAddress custAddr: customerService.getAddressList().getCustomerAddress()){
				for(com.AL.xml.cm.v4.RoleType roles : custAddr.getAddressRoles().getRole()){
					if(roles.name().equals("SERVICE_ADDRESS")){
						addressUniqueId = custAddr.getAddressUniqueId();
						break;
					}
				}
			}
		}

		com.AL.xml.cm.v4.CustomerType newCustomer = null;

		/*
		 * if billing info is present and is given, add the billing info with these details
		 * if the billing info is not present or given, add a new empty billing info add save the external ID to lineItem billingInfoExternalID
		 */
		if(billingInfoMap!= null && !(billingInfoMap.isEmpty())){
			newCustomer = new com.AL.xml.cm.v4.CustomerType();
			newBillingInfoType = createBillingInfo(billingInfoMap, addressUniqueId, billingInfoType, 
					newBillingInfoList);
			com.AL.xml.cm.v4.BillingInfoList billingInfoList = new com.AL.xml.cm.v4.BillingInfoList();
			billingInfoList.getBillingInfo().add(newBillingInfoType);
			newCustomer.setBillingInfoList(billingInfoList);

			newCustomer.setDirectMailOptIn(customerService.isDirectMailOptIn());
			newCustomer.setPhoneContactOptIn(customerService.isPhoneContactOptIn());
			newCustomer.setEMailOptIn(customerService.isEMailOptIn());
			newCustomer.setEMailProductUpdatesOptIn(customerService.isEMailProductUpdatesOptIn());
			newCustomer.setMarketingOptIn(customerService.isMarketingOptIn());
			newCustomer.setNonBuyerWebOptIn(customerService.isNonBuyerWebOptIn());
			newCustomer = CustomerService.INSTANCE.submitCustomerType(orderQualVO.getAgentId(), orderQualVO.getCustomerExternalId()
					, "updateCustomer", newCustomer, null, customerContext, new ErrorList());
		}
		else{
			newCustomer = new com.AL.xml.cm.v4.CustomerType();
			newBillingInfoType = createBillingInfo(billingInfoMap, addressUniqueId, billingInfoType, 
					newBillingInfoList);
			com.AL.xml.cm.v4.BillingInfoList billingInfoList = new com.AL.xml.cm.v4.BillingInfoList();
			billingInfoList.getBillingInfo().add(newBillingInfoType);
			newCustomer.setBillingInfoList(billingInfoList);

			newCustomer.setDirectMailOptIn(customerService.isDirectMailOptIn());
			newCustomer.setPhoneContactOptIn(customerService.isPhoneContactOptIn());
			newCustomer.setEMailOptIn(customerService.isEMailOptIn());
			newCustomer.setEMailProductUpdatesOptIn(customerService.isEMailProductUpdatesOptIn());
			newCustomer.setMarketingOptIn(customerService.isMarketingOptIn());
			newCustomer.setNonBuyerWebOptIn(customerService.isNonBuyerWebOptIn());
			newCustomer = CustomerService.INSTANCE.submitCustomerType(orderQualVO.getAgentId(), orderQualVO.getCustomerExternalId()
					, "updateCustomer", newCustomer, null, customerContext, new ErrorList());
		}


		/*
		 * updating the line item billing info ref with the new billing info external Id.
		 */
		LineItemType liType = orderQualVO.getLineItemType();
		LineItemType newLineItemType = oFactory.createLineItemType();
		if(newCustomer != null){
			com.AL.xml.cm.v4.BillingInfoList billingInfoList = newCustomer.getBillingInfoList();
			String billingInfoExternalID = null;

			List<com.AL.xml.cm.v4.CustBillingInfoType> custBillingInfoList = billingInfoList.getBillingInfo();

			com.AL.xml.cm.v4.CustBillingInfoType custBillingInfo = getNewlyCreatedBillingInfoType(custBillingInfoList,newBillingInfoList.getBillingInfo());

			if(custBillingInfo!=null){
				billingInfoExternalID = String.valueOf(custBillingInfo.getExternalId()); 

				if(billingInfoExternalID != null && billingInfoExternalID.trim().length() > 0){
					newLineItemType.setExternalId(liType.getExternalId());
					newLineItemType.setLineItemNumber(liType.getLineItemNumber());
					newLineItemType.setBillingInfoExtId(billingInfoExternalID);

					/*
					 * explicitly making the reference to null to allow this object for GC
					 */
					liType = null;
					LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
					logger.info("Adding line item to lone item collection type");
					liCollection.getLineItem().add(newLineItemType);
					logger.info("After setting item to line item collection type");
					ErrorList errorList = new ErrorList();

					LineItemService.INSTANCE.updateLineItem(orderQualVO.getAgentId(), orderQualVO.getOrderId(), 
							VOperation.updateLineItem.toString(), liCollection, context, errorList);	
				}
			}
		}
	}

	private boolean isBillingAddressChanged(List<AddressType> addressTypeList, List<CustAddress> customerAddressList) {
		boolean isBillingAddressChanged = false;
		int count = 0;
		for(CustAddress custAddr : customerAddressList){
			for(RoleType roles : custAddr.getAddressRoles().getRole()){
				if(roles.value().equals("BillingAddress")){
					for(AddressType addrType : addressTypeList){
						if(addrType.getAddressBlock().equals("BILLING_ADDR_ROLE_LIST")){
							if(Utils.isBlank(addrType.getStreetName()) && !Utils.isBlank(custAddr.getAddress().getStreetName()) ||
									!Utils.isBlank(addrType.getStreetName()) && Utils.isBlank(custAddr.getAddress().getStreetName()) ||
									(!Utils.isBlank(addrType.getStreetName()) && !Utils.isBlank(custAddr.getAddress().getStreetName()) && 
											!addrType.getStreetName().trim().equals(custAddr.getAddress().getStreetName().trim()))) {
								count++;
							}
							if(Utils.isBlank(addrType.getStreetNumber()) && !Utils.isBlank(custAddr.getAddress().getStreetNumber()) ||
									!Utils.isBlank(addrType.getStreetNumber()) && Utils.isBlank(custAddr.getAddress().getStreetNumber()) ||
									(!Utils.isBlank(addrType.getStreetNumber()) && !Utils.isBlank(custAddr.getAddress().getStreetNumber()) && 
											!addrType.getStreetNumber().trim().equals(custAddr.getAddress().getStreetNumber().trim()))) {
								count++;
							}
							if(Utils.isBlank(addrType.getStreetType()) && !Utils.isBlank(custAddr.getAddress().getStreetType()) ||
									!Utils.isBlank(addrType.getStreetType()) && Utils.isBlank(custAddr.getAddress().getStreetType()) ||
									(!Utils.isBlank(addrType.getStreetType()) && !Utils.isBlank(custAddr.getAddress().getStreetType()) && 
											!addrType.getStreetType().trim().equals(custAddr.getAddress().getStreetType().trim()))) {
								count++;
							}
							if(Utils.isBlank(addrType.getPostalCode()) && !Utils.isBlank(custAddr.getAddress().getPostalCode()) ||
									!Utils.isBlank(addrType.getPostalCode()) && Utils.isBlank(custAddr.getAddress().getPostalCode()) ||
									(!Utils.isBlank(addrType.getPostalCode()) && !Utils.isBlank(custAddr.getAddress().getPostalCode()) && 
											!addrType.getPostalCode().trim().equals(custAddr.getAddress().getPostalCode().trim()))) {
								count++;
							}
							if(Utils.isBlank(addrType.getLine2()) && !Utils.isBlank(custAddr.getAddress().getLine2()) ||
									!Utils.isBlank(addrType.getLine2()) && Utils.isBlank(custAddr.getAddress().getLine2()) ||
									(!Utils.isBlank(addrType.getLine2()) && !Utils.isBlank(custAddr.getAddress().getLine2()) && 
											!addrType.getLine2().trim().equals(custAddr.getAddress().getLine2().trim()))) {
								count++;
							}
						}
					}
				}
			}
		}
		if(count > 0){
			isBillingAddressChanged = true;
		}
		return isBillingAddressChanged;
	}

	/**
	 * checks whether the credit card details are changed or not
	 * @param newBillingInfoList
	 * @param billingInfoMap
	 * @return boolean
	 */
	private boolean isCreditCardDetailsChanged(BillingInfoList newBillingInfoList, Map<String, String> billingInfoMap) {
		boolean isCardDetailsChanged = false;
		String cardNumber = billingInfoMap.get("CCNumber");
		String cardType = billingInfoMap.get("CCType");
		String cardExpDate = billingInfoMap.get("CCExpDate");
		int same_card_type_count = 0;
		int different_card_type_count = 0;
		if(!Utils.isBlank(cardNumber) || !Utils.isBlank(cardType) || !Utils.isBlank(cardExpDate)){
			for(CustBillingInfoType custBilling : newBillingInfoList.getBillingInfo()){
				if(!Utils.isBlank(custBilling.getCreditCardNumber())) {
					if(!custBilling.getCreditCardNumber().equals(cardNumber)) {
						different_card_type_count++;
					}
					else{
						same_card_type_count++;
					}
				}
				if(custBilling.getCreditCardType() != null) {
					logger.info("IS Card Type Same ::: "+custBilling.getCreditCardType().equals(cardType));
					if(!custBilling.getCreditCardType().equals(cardType)) {
						different_card_type_count++;
					}
					else{
						same_card_type_count++;
					}
				}
				if(custBilling.getExpirationYearMonth() != null) {
					logger.info("Is Expiration Date Same :::::: "+custBilling.getExpirationYearMonth().equals(cardExpDate));
					if(!custBilling.getExpirationYearMonth().equals(cardExpDate)) {
						different_card_type_count++;
					}
					else{
						same_card_type_count++;
					}
				}
			}
		}

		if(different_card_type_count > 0 && (same_card_type_count == 0 || same_card_type_count < 3)) {
			isCardDetailsChanged = true;
		}
		return isCardDetailsChanged;
	}

	/**
	 * checks whether customer contains more than one credit card
	 * @param newBillingInfoList
	 * @return boolean
	 */
	private boolean containsMoreThanOneCard(BillingInfoList newBillingInfoList){
		int numberOfCreditCards = 0;
		boolean containsMoreThanOneCC = false;
		for(CustBillingInfoType custBilling : newBillingInfoList.getBillingInfo()){
			//logger.info("CUSTOMER CC NUMBER ::::: "+custBilling.getCreditCardNumber());
			logger.info(custBilling.getCreditCardNumber().trim().length()>0);
			if((custBilling.getCreditCardNumber() != null) && (custBilling.getCreditCardNumber().trim().length() > 0)){
				numberOfCreditCards++;	
			}
		}
		if(numberOfCreditCards > 0){
			containsMoreThanOneCC = true;
		}
		return containsMoreThanOneCC;
	}

	private com.AL.xml.cm.v4.CustBillingInfoType createCustBillingInfoType(CustBillingInfoType custBillingInfoType) {

		com.AL.xml.cm.v4.ObjectFactory cmObjectFactory = new com.AL.xml.cm.v4.ObjectFactory();
		com.AL.xml.cm.v4.CustBillingInfoType billingInfoType = cmObjectFactory.createCustBillingInfoType();
		billingInfoType.setAddressRef(custBillingInfoType.getAddressRef());
		/*if(Utils.isValidNumber(custBillingInfoType.getExternalId()+"")){
			billingInfoType.setExternalId(custBillingInfoType.getExternalId());
		}
		billingInfoType.setBillingUniqueId(custBillingInfoType.getBillingUniqueId());
		 */
		return billingInfoType;
	}

	/**
	 * Converts v4 custAddress object to cm v4 custAddress Object
	 * @param custAddr
	 * @return 
	 */
	private void buildCmV4AddressObject(Long custExternalID, OrderQualVO orderQualVO, CustomerContextType customerContext) {

		com.AL.xml.cm.v4.CustomerType cust = CustomerServiceUI.INSTANCE.getCustomer(String.valueOf(custExternalID));

		List<com.AL.xml.cm.v4.RoleType> custAddrRole = new ArrayList<com.AL.xml.cm.v4.RoleType>();
		custAddrRole.add(com.AL.xml.cm.v4.RoleType.SERVICE_ADDRESS);
		com.AL.xml.cm.v4.CustAddress cmCustAddr = AddressCollectionVo.getAddressByRole(cust.getAddressList().getCustomerAddress(), RoleType.SERVICE_ADDRESS.toString());

		cmCustAddr.getAddressRoles().getRole().add(com.AL.xml.cm.v4.RoleType.BILLING_ADDRESS);
		AddressService.INSTANCE.saveAddressUpdate(orderQualVO.getAgentId(), String.valueOf(custExternalID), cmCustAddr, customerContext);

	}

	/**
	 * converts String credit card type to com.AL.xml.cm.v4.CreditCardTypeType
	 * @param cardType
	 * @return
	 */
	private static com.AL.xml.cm.v4.CreditCardTypeType returnCardType(String cardType, CreditCardTypeType v4CCType){
		com.AL.xml.cm.v4.CreditCardTypeType cardReturnType = null;

		if(v4CCType != null && v4CCType.value() != null){
			cardType = v4CCType.value();
		}

		if(cardType != null){
			if(cardType.equalsIgnoreCase("AMERICANEXPRESS")){
				cardReturnType = com.AL.xml.cm.v4.CreditCardTypeType.AMERICAN_EXPRESS;
			}
			else if(cardType.equalsIgnoreCase("DINERSCLUB")){
				cardReturnType = com.AL.xml.cm.v4.CreditCardTypeType.DINER_S_CLUB;
			}
			else if(cardType.equalsIgnoreCase("DISCOVER")){
				cardReturnType = com.AL.xml.cm.v4.CreditCardTypeType.DISCOVER;
			}
			else if(cardType.equalsIgnoreCase("MasterCard")){
				cardReturnType = com.AL.xml.cm.v4.CreditCardTypeType.MASTER_CARD;
			}
			else if(cardType.equalsIgnoreCase("OPTIMA")){
				cardReturnType = com.AL.xml.cm.v4.CreditCardTypeType.OPTIMA;
			}
			else if(cardType.equalsIgnoreCase("VISA")){
				cardReturnType = com.AL.xml.cm.v4.CreditCardTypeType.VISA;
			}
		}
		return cardReturnType;
	}

	/**
	 * Creates billing info type object from billing info map
	 * @param billingInfoMap
	 * @param addressUniqueId
	 * @param newBillingInfoList 
	 * @return CustBillingInfoType
	 */
	public com.AL.xml.cm.v4.CustBillingInfoType createBillingInfo(
			Map<String, String> billingInfoMap, String addressUniqueId, com.AL.xml.cm.v4.CustBillingInfoType billingInfo, 
			BillingInfoList newBillingInfoList) {


		if(billingInfo == null){
			billingInfo = new com.AL.xml.cm.v4.CustBillingInfoType();	
		}
		if(billingInfoMap != null && billingInfoMap.get("CCNumber") != null) {
			billingInfo.setCardHolderName(billingInfoMap.get("CCName"));
			billingInfo.setCreditCardNumber(billingInfoMap.get("CCNumber"));
			billingInfo.setBillingMethod(CREDIT_CARD);
			String expirationDate = billingInfoMap.get("CCExpDate");

			if(expirationDate != null && expirationDate.indexOf("/") >= 0){
				String[] expMonthAndYear = expirationDate.split("/");
				String finalDate = expMonthAndYear[1]+"-"+expMonthAndYear[0];

				billingInfo.setExpirationYearMonth(DateUtil.asXMLGregorianCalendar(finalDate, CREDIT_CARD_EXP_FORMAT));	
			}

			String cardType = billingInfoMap.get("CCType");
			com.AL.xml.cm.v4.CreditCardTypeType ccType = returnCardType(cardType, null);
			billingInfo.setCreditCardType(ccType);
		}
		if(billingInfo != null && billingInfo.getBillingUniqueId() == null){
			billingInfo.setBillingUniqueId(String.valueOf(System.currentTimeMillis()));	
		}
		if(billingInfo != null && billingInfo.getAddressRef() == null){
			billingInfo.setAddressRef(addressUniqueId);
		}
		boolean containsExternalId = Utils.isValidNumber(billingInfo.getExternalId()+"");

		if(containsExternalId){
			billingInfo.setExternalId(billingInfo.getExternalId());
		}
		return billingInfo;
	}

	/**
	 * builds address fields object based on value targets
	 * @param valueTarget
	 * @param resultParamMap
	 * @param addrFields
	 * @param df
	 * @param requestParamMap 
	 * @param isShippingAddressPresent 
	 * @param isPreviousAddressPresent 
	 * @return
	 */
	private static boolean buildAddressFields(String valueTarget, Map<String, String> resultParamMap, AddressFields addrFields, DataField df, String extSelectedValues, 
			Map<String, String> requestParamMap){
		boolean isBillingAddrPresent = false;
		String[] selectedExternalIDValues = extSelectedValues.split(",");
		if(valueTarget.contains("previousAddress")){
			if(valueTarget.equals("consumer.previousAddress.dwelling.prefixDirectional ||'sp'|| consumer.previousAddress.dwelling.streetNumber ||'sp'|| consumer.previousAddress.dwelling.streetName ||'sp'|| consumer.previousAddress.dwelling.streetType")){

				String prevAddrValue = resultParamMap.get(df.getExternalId());
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setPrevAddrStreetName(prevAddrValue);
					}
				}
			}
			else if(valueTarget.equals("consumer.previousAddress.dwelling.line2")){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setPrevAddrLine2(resultParamMap.get(df.getExternalId()));
					}
				}
			}

			else if(valueTarget.equals("consumer.previousAddress.dwelling.line2info")){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						logger.info("consumer.previousAddress.dwelling.line2info :::::::: "+resultParamMap.get(df.getExternalId()));
						addrFields.setPrevAddrLine2Info(resultParamMap.get(df.getExternalId()));
					}
				}
			}

			else if(valueTarget.equals("consumer.previousAddress.dwelling.city")){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setPrevAddrCity(resultParamMap.get(df.getExternalId()));
					}
				}
			}
			else if(valueTarget.equals("consumer.previousAddress.dwelling.stateOrProvince")){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setPrevAddrStateOrPrivince(resultParamMap.get(df.getExternalId()));
					}
				}
			}
			else if(valueTarget.equals("consumer.previousAddress.dwelling.postalCode")){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setPrevAddrPostalCode(resultParamMap.get(df.getExternalId()));
					}
				}
			}
		}

		else if(valueTarget.contains("billingAddress")){

			if(valueTarget.equals("consumer.billingAddress.dwelling.prefixDirectional ||'sp'|| consumer.billingAddress.dwelling.streetNumber ||'sp'|| consumer.billingAddress.dwelling.streetName ||'sp'|| consumer.billingAddress.dwelling.streetType")){

				String billingAddrValue = resultParamMap.get(df.getExternalId());
				if(billingAddrValue != null){
					for(String extID : selectedExternalIDValues){
						if(extID.indexOf(df.getExternalId()) > 0){
							if(billingAddrValue.indexOf(" ") > 0){
								String [] sp = billingAddrValue.split(" ");
								addrFields.setBillingAddrStreetNumber(sp[0]);
								addrFields.setBillingAddrStreetName(sp[1]);
								if(sp.length >= 3){
									String str = "";
									for(int i = 2; i < sp.length; i++){
										str += sp[i]+" "; 
									}
									addrFields.setBillingAddrStreetType(str);	
								}
							}
							else{
								addrFields.setBillingAddrStreetName(billingAddrValue);
							}
						}
					}
				}
			}
			else if(valueTarget.equals("consumer.billingAddress.dwelling.line2") && resultParamMap.get(df.getExternalId()) != null){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setBillingAddrLine2(requestParamMap.get("addressLineAndLineInfo"));
					}
				}
			}

			else if(valueTarget.equals("consumer.billingAddress.dwelling.line2info") && resultParamMap.get(df.getExternalId()) != null){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setBillingAddrLine2Info(resultParamMap.get(df.getExternalId()));
					}
				}
			}

			else if(valueTarget.equals("consumer.billingAddress.dwelling.city") && resultParamMap.get(df.getExternalId()) != null){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setBillingAddrCity(resultParamMap.get(df.getExternalId()));
					}
				}
			}

			else if(valueTarget.equals("consumer.billingAddress.dwelling.stateOrProvince") && resultParamMap.get(df.getExternalId()) != null){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setBillingAddrStateOrPrivince(resultParamMap.get(df.getExternalId()));
					}
				}
			}
			else if(valueTarget.equals("consumer.billingAddress.dwelling.postalCode") && resultParamMap.get(df.getExternalId()) != null){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setBillingAddrPostalCode(resultParamMap.get(df.getExternalId()));
					}
				}
			}
		}

		else if(valueTarget.contains("shippingAddress")){

			if(valueTarget.equals("consumer.shippingAddress.dwelling.prefixDirectional ||'sp'|| consumer.shippingAddress.dwelling.streetNumber ||'sp'|| consumer.shippingAddress.dwelling.streetName ||'sp'|| consumer.shippingAddress.dwelling.streetType")){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						String shippingAddrValue = resultParamMap.get(df.getExternalId());
						if(shippingAddrValue != null){
							if(shippingAddrValue.indexOf(" ") > 0){
								String [] sp = shippingAddrValue.split(" ");
								addrFields.setBillingAddrStreetNumber(sp[0]);
								addrFields.setBillingAddrStreetName(sp[1]);
								if(sp.length >= 3){
									String str = "";
									for(int i = 2; i < sp.length; i++){
										str += sp[i]+" "; 
									}
									addrFields.setBillingAddrStreetType(str);	
								}
							}
							else{
								addrFields.setBillingAddrStreetName(shippingAddrValue);
							}
						}
					}
				}
			}
			else if(valueTarget.equals("consumer.shippingAddress.dwelling.line2")){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setShippingAddrLine2(resultParamMap.get(df.getExternalId()));
					}
				}
			}
			else if(valueTarget.equals("consumer.shippingAddress.dwelling.line2info")){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setShippingAddrLine2Info(resultParamMap.get(df.getExternalId()));
					}
				}
			}

			else if(valueTarget.equals("consumer.shippingAddress.dwelling.city")){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setShippingAddrCity(resultParamMap.get(df.getExternalId()));
					}
				}
			}

			else if(valueTarget.equals("consumer.shippingAddress.dwelling.stateOrProvince")){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setShippingAddrStateOrPrivince(resultParamMap.get(df.getExternalId()));
					}
				}
			}
			else if(valueTarget.equals("consumer.shippingAddress.dwelling.postalCode")){
				for(String extID : selectedExternalIDValues){
					if(extID.indexOf(df.getExternalId()) > 0){
						addrFields.setShippingAddrPostalCode(resultParamMap.get(df.getExternalId()));
					}
				}
			}
		}
		return isBillingAddrPresent;
	}

	/**
	 * Creates a List of addressType Objects from Address Field Object
	 * @param addrFields
	 * @param addressTypeMap 
	 * @param billingAddressSameAsSvcAddr
	 * @param addressTypeList2 
	 * @return
	 */
	private boolean buildAddress(AddressFields addrFields, List<AddressType> addressTypeList) {
		boolean billingAddressSameAsSvcAddr = true;
		com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();
		AddressType address = null;

		if(addrFields.getPrevAddrStreetName() != null && addrFields.getPrevAddrPostalCode() != null){

			address = oFactory.createAddressType();

			address.setStreetName(addrFields.getPrevAddrStreetName());
			
			if(!Utils.isBlank(addrFields.getPrevAddrStreetNumber())){
				address.setStreetNumber(addrFields.getPrevAddrStreetNumber());
			}
			if(!Utils.isBlank(addrFields.getPrevAddrStreetType())){
				address.setStreetType(addrFields.getPrevAddrStreetType());
			}
			if(!Utils.isBlank(addrFields.getPrevAddrLine2()) && !Utils.isBlank(addrFields.getPrevAddrLine2Info())){
				address.setLine2(addrFields.getPrevAddrLine2()+" "+addrFields.getPrevAddrLine2Info());
			}
			address.setCity(addrFields.getPrevAddrCity());
			address.setStateOrProvince(addrFields.getPrevAddrStateOrPrivince());
			address.setPostalCode(addrFields.getPrevAddrPostalCode());
			address.setAddressBlock("PREVIOUS_ADDR_ROLE_LIST");
			addressTypeList.add(address);
		}
		if(addrFields.getBillingAddrStreetName() != null && addrFields.getBillingAddrStreetNumber() != null && addrFields.getBillingAddrCity() != null && addrFields.getBillingAddrStateOrPrivince() != null && addrFields.getBillingAddrPostalCode() != null){

			address = oFactory.createAddressType();

			address.setStreetName(addrFields.getBillingAddrStreetName());
			address.setStreetNumber(addrFields.getBillingAddrStreetNumber());
			address.setStreetType(addrFields.getBillingAddrStreetType());
			address.setLine2(addrFields.getBillingAddrLine2());
			address.setCity(addrFields.getBillingAddrCity());
			address.setStateOrProvince(addrFields.getBillingAddrStateOrPrivince());
			address.setPostalCode(addrFields.getBillingAddrPostalCode());
			address.setAddressBlock("BILLING_ADDR_ROLE_LIST");
			addressTypeList.add(address);

			billingAddressSameAsSvcAddr = false;
		}
		if(addrFields.getShippingAddrStreetName() != null && addrFields.getShippingAddrStreetNumber() != null && addrFields.getShippingAddrCity() != null && addrFields.getShippingAddrStateOrPrivince() != null && addrFields.getShippingAddrPostalCode() != null){

			address = oFactory.createAddressType();

			address.setStreetName(addrFields.getShippingAddrStreetName());
			address.setStreetNumber(addrFields.getShippingAddrStreetNumber());
			address.setStreetType(addrFields.getShippingAddrStreetType());
			address.setLine2(addrFields.getShippingAddrLine2());
			address.setCity(addrFields.getShippingAddrCity());
			address.setStateOrProvince(addrFields.getShippingAddrStateOrPrivince());
			address.setPostalCode(addrFields.getShippingAddrPostalCode());
			address.setAddressBlock("SHIPPING_ADDR_ROLE_LIST");
			addressTypeList.add(address);
		}
		return billingAddressSameAsSvcAddr;
	}
	
	private static String getFormattedUnitType(String unitType) {
		String type = "";
		if (unitType.equalsIgnoreCase("apartment")) {
			type = "Apt";
		} else if (unitType.equalsIgnoreCase("building")) {
			type = "Bldg";
		} else if (unitType.equalsIgnoreCase("department")) {
			type = "Dept";
		} else if (unitType.equalsIgnoreCase("floor")) {
			type = "Fl";
		} else if (unitType.equalsIgnoreCase("hanger")) {
			type = "Hngr";
		} else if (unitType.equalsIgnoreCase("lot")) {
			type = "Lot";
		} else if (unitType.equalsIgnoreCase("office")) {
			type = "Ofc";
		} else if (unitType.equalsIgnoreCase("space")) {
			type = "Spc";
		} else if (unitType.equalsIgnoreCase("suite")) {
			type = "Ste";
		} else if (unitType.equalsIgnoreCase("trailer")) {
			type = "Trlr";
		} else if (unitType.equalsIgnoreCase("unit")) {
			type = "Unt";
		}
		return type;
	}



	private boolean isCustomerInfoEmpty(CustomerInfoVO customerInfoVO){
		boolean isCustomerInfoPresent = false;
		if(!Utils.isBlank(customerInfoVO.getDob()) || !Utils.isBlank(customerInfoVO.getEmailAddress()) || !Utils.isBlank(customerInfoVO.getBtn()) || !Utils.isBlank(customerInfoVO.getHomePhoneNumber())
				|| !Utils.isBlank(customerInfoVO.getCellPhoneNumber()) || !Utils.isBlank(customerInfoVO.getWorkPhoneNumber()) 
				|| !Utils.isBlank(customerInfoVO.getSsn()) || !Utils.isBlank(customerInfoVO.getState()) || !Utils.isBlank(customerInfoVO.getDriverLicense())){
			isCustomerInfoPresent = true;
		}
		return isCustomerInfoPresent;
	}

	private boolean isPreviousAddressChanged(List<AddressType> addressTypeList, List<CustAddress> customerAddressList) {
		boolean isPreviousAddressChanged = false;
		int isSameAddressCount = 0;
		List<String> isPreviousAddressList = new ArrayList<String>();
		for(CustAddress custAddr : customerAddressList){
			for(RoleType roles : custAddr.getAddressRoles().getRole()){
				if(roles.value().equals("PreviousAddress")){
					isPreviousAddressList.add(roles.value());
					for(AddressType addrType : addressTypeList){

						if(addrType.getAddressBlock().equals("PREVIOUS_ADDR_ROLE_LIST")){
							if((!Utils.isBlank(addrType.getStreetName()) && !Utils.isBlank(custAddr.getAddress().getStreetName()) && 
									!addrType.getStreetName().trim().equalsIgnoreCase(custAddr.getAddress().getStreetName().trim()))) {
								isSameAddressCount++;
							}
							if((!Utils.isBlank(addrType.getStreetNumber()) && !Utils.isBlank(custAddr.getAddress().getStreetNumber())) && 
									!addrType.getStreetNumber().trim().equalsIgnoreCase(custAddr.getAddress().getStreetNumber().trim())) {
								isSameAddressCount++;
							}
							if((!Utils.isBlank(addrType.getStreetType()) && !Utils.isBlank(custAddr.getAddress().getStreetType())) && 
									!addrType.getStreetType().trim().equalsIgnoreCase(custAddr.getAddress().getStreetType().trim())) {
								isSameAddressCount++;
							}
							if((!Utils.isBlank(addrType.getPostalCode()) && !Utils.isBlank(custAddr.getAddress().getPostalCode())) && 
									!addrType.getPostalCode().trim().equalsIgnoreCase(custAddr.getAddress().getPostalCode().trim())) {
								isSameAddressCount++;
							}
							if((!Utils.isBlank(addrType.getLine2()) && !Utils.isBlank(custAddr.getAddress().getLine2())) && 
									!addrType.getLine2().trim().equalsIgnoreCase(custAddr.getAddress().getLine2().trim())) {
								isSameAddressCount++;
							}					
						}
					}
				}
			}
		}
		logger.info("Previous Address COUNT :::: "+isSameAddressCount);
		logger.info("Previous Address ROLE LIST :::: "+isPreviousAddressList);
		if(isSameAddressCount > 0 || !(isPreviousAddressList.size() > 0)){
			isPreviousAddressChanged = true;
		}
		return isPreviousAddressChanged;
	}
	
	private boolean setPreviousAddressBlock(AddressType addrType) {
		boolean isAddressExactMatch = false;
		StringBuilder addressBlock = new StringBuilder();
		if(!Utils.isBlank(addrType.getStreetName())){
			addressBlock.append(" ");
			addressBlock.append(addrType.getStreetName());
		}
		if(!Utils.isBlank(addrType.getStreetType())){
			addressBlock.append(" ");
			addressBlock.append(addrType.getStreetType());
		}
		if(!Utils.isBlank(addrType.getLine2())){
			addressBlock.append(" ");
			String arr[] = addrType.getLine2().split(" ");
			addressBlock.append(getFormattedUnitType(arr[0])+" "+arr[1]);
		}
		if(!Utils.isBlank(addrType.getCity())){
			addressBlock.append(", ");
			addressBlock.append(addrType.getCity());
		}
		if(!Utils.isBlank(addrType.getStateOrProvince())){
			addressBlock.append(" ");
			addressBlock.append(addrType.getStateOrProvince());
		}
		if(!Utils.isBlank(addrType.getPostalCode())){
			addressBlock.append(" ");
			addressBlock.append(addrType.getPostalCode());
		}
		logger.info("Given_previous_AddressBlock="+addressBlock.toString());
		
		try {
			isAddressExactMatch = populateCorrectedAddressUsingSE2(addressBlock.toString(), addrType);
		}catch (Exception e) {
			logger.warn("Exception_Occured_while_populateCorrectedAddressUsingSE2",e);
		}
		return isAddressExactMatch;
	}
	
	protected boolean populateCorrectedAddressUsingSE2(String addressBlock, AddressType addrType) throws InvalidDataException, InvalidFormatException, JSONException{
		logger.info("Inside populateCorrectedAddressUsingSE2 ");

		logger.info("Entered_AddressBlock="+addressBlock);

		String dwellingType = "House";
		ServiceabilityEnterpriseResponse sarRes;
		boolean isAddressExactMatch = false;

		sarRes = ESEService.INSTANCE.getServiceabilityResponse(addressBlock, dwellingType);

		ServiceabilityResponse2 sre = (ServiceabilityResponse2)sarRes.getResponse();
		com.AL.xml.se.v4.AddressType sreAddress = sre.getCorrectedAddress();
		logger.info("CorrectedAddress="+sreAddress.getAddressBlock());
		if (sarRes.getStatus() != null){
			sre = (ServiceabilityResponse2)sarRes.getResponse();
			logger.info("valid_address=" +sarRes.getStatus());
			if (sarRes.getStatus().getProcessingMessages() != null 
					&& sarRes.getStatus().getProcessingMessages().getMessages() != null 
					&& sarRes.getStatus().getProcessingMessages().getMessages().size() > 0){
				for (ProcessingMessage status : sarRes.getStatus().getProcessingMessages().getMessages()){
					if (status != null 
							&& (status.getCode()  == 1.0 || status.getCode() == 1.1)){
						isAddressExactMatch = true;
						break;
					}
				}
			}
		}
		if(sreAddress != null && isAddressExactMatch){
			if(!Utils.isBlank(sreAddress.getPrefixDirectional())){
				addrType.setPrefixDirectional(getCapitalizationTextFromNormalText(sreAddress.getPrefixDirectional()));
			}
			if(!Utils.isBlank(sreAddress.getStreetNumber())){
				addrType.setStreetNumber(sreAddress.getStreetNumber());
			}
			if(!Utils.isBlank(sreAddress.getStreetName())){
				addrType.setStreetName(getCapitalizationTextFromNormalText(sreAddress.getStreetName()));
			}
			if(!Utils.isBlank(sreAddress.getStreetType())){
				addrType.setStreetType(getCapitalizationTextFromNormalText(sreAddress.getStreetType()));
			}
			if(!Utils.isBlank(sreAddress.getCity())){
				addrType.setCity(getCapitalizationTextFromNormalText(sreAddress.getCity()));
			}
			if(!Utils.isBlank(sreAddress.getCountry())){
				 addrType.setCountry(getCapitalizationTextFromNormalText(sreAddress.getCountry()));
			}
			if(!Utils.isBlank(sreAddress.getAddressBlock())){
				addrType.setAddressBlock(getCapitalizationTextFromNormalText(sreAddress.getAddressBlock()));
			}
			if(!Utils.isBlank(sreAddress.getLine2())){
				 addrType.setLine2(getCapitalizationTextFromNormalText(sreAddress.getLine2()));
			}
			if(!Utils.isBlank(sreAddress.getPostfixDirectional())){
				 addrType.setPostfixDirectional(getCapitalizationTextFromNormalText(sreAddress.getPostfixDirectional()));
			}
			if(!Utils.isBlank(sreAddress.getAddressBlock())){
				addrType.setAddressBlock(getCapitalizationTextFromNormalText(sreAddress.getAddressBlock()));
			}
		}
		return isAddressExactMatch;
	}
	
	private String getCapitalizationTextFromNormalText(String normalText){
		normalText = normalText.trim();
		StringBuilder capitalizedAddress = new StringBuilder();
		String[] stringArray = normalText.split(" ");

		for (String value : stringArray) {
			if(value.length()>1){
				capitalizedAddress.append(value.substring(0, 1).toUpperCase());
				capitalizedAddress.append(value.substring(1, value.length()).toLowerCase());
			}else if(value.length()==1){
				capitalizedAddress.append(value.toUpperCase());
			}else
				continue;
			if(!stringArray[stringArray.length-1].equals(value)){
				capitalizedAddress.append(" ");
			}
		}
		return capitalizedAddress.toString();

	}
	private String formatString(String toBeFormatted){
		String formattedString = "";
		if(!Utils.isBlank(toBeFormatted)){
			String [] sp = toBeFormatted.split(" ");
			for(int i=0;i < sp.length;i++){
				formattedString = formattedString + sp[i].substring(0,1).toUpperCase()+sp[i].substring(1).toLowerCase()+" ";
			}	
		}
		return formattedString;
	}
	

	private boolean isShippingAddressChanged(List<AddressType> addressTypeList, List<CustAddress> customerAddressList) {
		boolean isShippingAddressChanged = false;
		int count = 0;
		List<String> isPreviousAddressList = new ArrayList<String>();
		for(CustAddress custAddr : customerAddressList){
			for(RoleType roles : custAddr.getAddressRoles().getRole()){
				if(roles.value().equals("ShippingAddress")){
					isPreviousAddressList.add(roles.value());
					for(AddressType addrType : addressTypeList){
						if(addrType.getAddressBlock().equals("SHIPPING_ADDR_ROLE_LIST")){
							if((!Utils.isBlank(addrType.getStreetName()) && !Utils.isBlank(custAddr.getAddress().getStreetName()) && 
									!addrType.getStreetName().trim().equalsIgnoreCase(custAddr.getAddress().getStreetName().trim()))) {
								count++;
							}
							if((!Utils.isBlank(addrType.getStreetNumber()) && !Utils.isBlank(custAddr.getAddress().getStreetNumber())) && 
									!addrType.getStreetNumber().trim().equalsIgnoreCase(custAddr.getAddress().getStreetNumber().trim())) {
								count++;
							}
							if((!Utils.isBlank(addrType.getStreetType()) && !Utils.isBlank(custAddr.getAddress().getStreetType())) && 
									!addrType.getStreetType().trim().equalsIgnoreCase(custAddr.getAddress().getStreetType().trim())) {
								count++;
							}
							if((!Utils.isBlank(addrType.getPostalCode()) && !Utils.isBlank(custAddr.getAddress().getPostalCode())) && 
									!addrType.getPostalCode().trim().equalsIgnoreCase(custAddr.getAddress().getPostalCode().trim())) {
								count++;
							}
							if((!Utils.isBlank(addrType.getLine2()) && !Utils.isBlank(custAddr.getAddress().getLine2())) && 
									!addrType.getLine2().trim().equalsIgnoreCase(custAddr.getAddress().getLine2().trim())) {
								count++;
							}					
						}
					}
				}
			}
		}
		logger.info("SHIPPING Address COUNT :::: "+count);
		logger.info("SHIPPING Address ROLE LIST :::: "+isPreviousAddressList);
		if(count > 0 || !(isPreviousAddressList.size() > 0)){
			isShippingAddressChanged = true;
		}
		return isShippingAddressChanged;
	}
}

