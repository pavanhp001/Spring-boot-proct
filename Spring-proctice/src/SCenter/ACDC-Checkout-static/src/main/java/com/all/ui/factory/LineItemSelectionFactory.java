package com.AL.ui.factory;

import java.util.Map;

import org.json.JSONObject;

import com.AL.ui.constants.Constants;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.DataFeildFeatureVO;
import com.AL.ui.vo.PriceTierVO;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.DialogValueType;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.PriceInfoType;
import com.AL.xml.v4.SelectedFeaturesType;

public enum LineItemSelectionFactory {
	INSTANCE;


	/** This method is used to build ActiveDialogueType for update lineItem
	 * @param dataFeildFeatureVO
	 * @param oFactory
	 * @param objectInArray
	 * @return
	 * @throws Exception
	 */
	public DialogValueType buildActiveDialogueType(DataFeildFeatureVO dataFeildFeatureVO,ObjectFactory oFactory,JSONObject objectInArray) throws Exception {
		DialogValueType dialogueValueType = oFactory.createDialogValueType();
		DialogValueType.Value dialogueValueTypeValue = oFactory.createDialogValueTypeValue();
		dialogueValueType = oFactory.createDialogValueType();
		dialogueValueType.setExternalId(dataFeildFeatureVO.getDataFieldExID());
		dialogueValueTypeValue.setSelected(true);
		if(!Utils.isBlank(dataFeildFeatureVO.getDataConstraint())){
			dialogueValueTypeValue.setType(dataFeildFeatureVO.getDataConstraint().toLowerCase());
		}
		if (objectInArray.has(Constants.VALUE) && !objectInArray.isNull(Constants.VALUE)) {
			dialogueValueTypeValue.setValue(objectInArray.getString(Constants.VALUE));
			dataFeildFeatureVO.setEnteredValue(objectInArray.getString(Constants.VALUE));
		} else {
			dialogueValueTypeValue.setValue("");
			dataFeildFeatureVO.setEnteredValue("");
		}
		dialogueValueType.getValue().add(dialogueValueTypeValue);
		return dialogueValueType;

	}

	/**  This method is used to build selected Feature for update lineItem 
	 * @param dataFeildFeatureVO
	 * @param inputValue
	 * @param oFactory
	 * @return
	 */
	public FeatureValueType buildFeatureValueType(DataFeildFeatureVO dataFeildFeatureVO,String inputValue,ObjectFactory oFactory){
		FeatureValueType fVal = oFactory.createFeatureValueType();
		fVal.setExternalId(dataFeildFeatureVO.getFeatureExID());
		fVal.setType(dataFeildFeatureVO.getDataConstraint().toLowerCase());
		PriceInfoType priceValue = oFactory.createPriceInfoType();
		dataFeildFeatureVO.setEnteredValue(inputValue);
		if(dataFeildFeatureVO.getDataConstraint().equalsIgnoreCase(Constants.INTEGER_DATA_CONSTRAIN)){
			Map<Integer, PriceTierVO>  priceTierMap = dataFeildFeatureVO.getPriceTierMap();
			if(Utils.isInteger(inputValue) && priceTierMap != null && priceTierMap.get(Integer.valueOf(inputValue)) != null){
				PriceTierVO priceTierVO = priceTierMap.get(Integer.valueOf(inputValue));
				priceValue.setBaseNonRecurringPrice(priceTierVO.getBaseNonRecurringPrice());
				priceValue.setBaseRecurringPrice(priceTierVO.getBaseRecurringPrice());
			}
			fVal.setValue(inputValue);
		}else if(dataFeildFeatureVO.getDataConstraint().equalsIgnoreCase(Constants.BOOLEAN_DATA_CONSTRAIN)){
			addPriceValue(dataFeildFeatureVO,priceValue);
			fVal.setValue("true");
		}else{
			addPriceValue(dataFeildFeatureVO,priceValue);
			fVal.setValue(inputValue);
		}

		fVal.setPrice(priceValue);
		return fVal;
	}

	/**  This method is used to build selected FeatureGroup for update lineItem 
	 * @param dataFeildFeatureGroupVO
	 * @param inputValue
	 * @param oFactory
	 * @return
	 */
	public SelectedFeaturesType.FeatureGroup selctedFeatureGroup(DataFeildFeatureVO dataFeildFeatureGroupVO,String inputValue,ObjectFactory oFactory){
		FeatureValueType fVal = oFactory.createFeatureValueType();
		SelectedFeaturesType.FeatureGroup selFeatureGroup = oFactory.createSelectedFeaturesTypeFeatureGroup();
		selFeatureGroup = oFactory.createSelectedFeaturesTypeFeatureGroup();
		selFeatureGroup.setExternalId(dataFeildFeatureGroupVO.getFeatureExID());
		selFeatureGroup.setGroupType(1);
		for(DataFeildFeatureVO dataFeildFeatureVO:dataFeildFeatureGroupVO.getFeatureVOList()){
			if(dataFeildFeatureVO.getFeatureExID().equalsIgnoreCase(inputValue)){
				PriceInfoType priceValue = oFactory.createPriceInfoType();
				fVal.setExternalId(dataFeildFeatureVO.getFeatureExID());
				fVal.setDescription(dataFeildFeatureVO.getFeatureDescription());
				fVal.setType(dataFeildFeatureVO.getDataConstraint().toLowerCase());
				fVal.setValue(dataFeildFeatureVO.getDataConstraintValueList().get(0));
				addPriceValue(dataFeildFeatureVO,priceValue);
				if(dataFeildFeatureVO.getDataConstraintValueList() != null && dataFeildFeatureVO.getDataConstraintValueList().size() > 0){
					dataFeildFeatureVO.setEnteredValue(dataFeildFeatureVO.getDataConstraintValueList().get(0));
				}
				fVal.setPrice(priceValue);
				selFeatureGroup.getFeatureValue().add(fVal);
			}else{
				dataFeildFeatureVO.setEnteredValue("");
			}
		}
		return selFeatureGroup;
	}

	/**  This method is used set priceValue value to PriceInfoType
	 * @param dataFeildFeatureVO
	 * @param priceValue
	 */
	private void addPriceValue(DataFeildFeatureVO dataFeildFeatureVO,PriceInfoType priceValue){
		if(dataFeildFeatureVO.getBaseRecurringPrice() != null){
			priceValue.setBaseRecurringPrice(Double.valueOf(dataFeildFeatureVO.getBaseRecurringPrice()));
		}else{
			priceValue.setBaseNonRecurringPrice(0.00);
		}
		if(dataFeildFeatureVO.getBaseNonRecurringPrice() != null){
			priceValue.setBaseNonRecurringPrice(Double.valueOf(dataFeildFeatureVO.getBaseNonRecurringPrice()));
		}else{
			priceValue.setBaseRecurringPrice(0.00);
		}
	}
	
	
	/**  This method is used to build AttributeDetailType
	 * @param name
	 * @param desc
	 * @param value
	 * @param oFactory
	 * @return
	 */
	public AttributeDetailType createLineitemAttribute(String name,String desc,String value,ObjectFactory oFactory){
		AttributeDetailType attr = oFactory.createAttributeDetailType();
		attr.setName(name);
		attr.setDescription(desc);
		attr.setValue(value);
		return attr;
	}
}
