package com.AL.ui.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.AL.xml.v4.ApplicableType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.DialogValueType;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemSelectionType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.SelectedFeaturesType;
import com.AL.xml.v4.SelectedFeaturesType.FeatureGroup;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

@XStreamAlias("lineitem")
public class LineItemVo {
	
	private static final Logger logger = Logger.getLogger(LineItemVo.class);

	protected String productExternalId;
	protected String partnerExternalId;
	protected boolean isPromotion;
	protected int lineItemNumber;
	protected String appliesTo;

	protected List<FeatureValueType> featureValue = null;
	protected List<SelectedFeaturesType.FeatureGroup> featureGroup = null;
	protected List<DialogValueType> dialog = null;

	protected List<LineItemSelectionType> selection = null;

	protected List<AttributeEntityType> entity = null;
	protected String combinePromoDesc = null;
	
	
	protected Map<String,List<ApplicableType>> promotionMap = new HashMap<String, List<ApplicableType>>();

	public Map<String, List<ApplicableType>> getPromotionMap() {
		return promotionMap;
	}

	public void setPromotionMap(Map<String, List<ApplicableType>> promotionMap) {
		this.promotionMap = promotionMap;
	}

	public String getProductExternalId() {
		return productExternalId;
	}

	public void setProductExternalId(String productExternalId) {
		this.productExternalId = productExternalId;
	}

	public String getPartnerExternalId() {
		return partnerExternalId;
	}

	public void setPartnerExternalId(String partnerExternalId) {
		this.partnerExternalId = partnerExternalId;
	}

	public boolean isPromotion() {
		return isPromotion;
	}

	public void setPromotion(boolean isPromotion) {
		this.isPromotion = isPromotion;
	}

	public int getLineItemNumber() {
		return lineItemNumber;
	}

	public void setLineItemNumber(int lineItemNumber) {
		this.lineItemNumber = lineItemNumber;
	}

	public List<FeatureValueType> getFeatureValue() {
		
		if (featureValue == null) {
			featureValue = new ArrayList<FeatureValueType>();
		}
		return featureValue;
	}

	public void setFeatureValue(List<FeatureValueType> featureValue) {
		this.featureValue = featureValue;
	}

	public List<SelectedFeaturesType.FeatureGroup> getFeatureGroup() {
		
		if (featureGroup == null) {
			featureGroup = new ArrayList<SelectedFeaturesType.FeatureGroup>();
		}
		return featureGroup;
	}

	public void setFeatureGroup(
			List<SelectedFeaturesType.FeatureGroup> featureGroup) {
		this.featureGroup = featureGroup;
	}

	public List<DialogValueType> getDialog() {
		if (dialog == null) {
			dialog = new ArrayList<DialogValueType>();
		}
		return dialog;
	}

	public void setDialog(List<DialogValueType> dialog) {
		this.dialog = dialog;
	}

	public List<LineItemSelectionType> getSelection() {
		
		if (selection == null) {
			selection = new ArrayList<LineItemSelectionType>();
		}
		return selection;
	}

	public void setSelection(List<LineItemSelectionType> selection) {
		this.selection = selection;
	}

	public List<AttributeEntityType> getEntity() {
		
		if (entity == null) {
			entity = new ArrayList<AttributeEntityType>();
		}
		return entity;
	}

	public void setEntity(List<AttributeEntityType> entity) {
		this.entity = entity;
	}

	public String getAppliesTo() {
		return appliesTo;
	}

	public void setAppliesTo(String appliesTo) {
		this.appliesTo = appliesTo;
	}

	public static void main(String[] args) {

		ObjectFactory oFactory = new ObjectFactory();

		LineItemVo li = createLineItem("provider1","product1");
		LineItemVo liP = createPromotion("provider1","promo1","0");

		li.getFeatureValue().add(createFVT("fvtid0", "value0"));
		li.getFeatureValue().add(createFVT("fvtid99", "value99"));
		li.getFeatureValue().add(createFVT("fvtid98", "value98"));

		FeatureGroup fg = oFactory.createSelectedFeaturesTypeFeatureGroup();
		fg.setExternalId("OPTIONS");
		fg.setGroupType(1);
		li.getFeatureGroup().add(fg);
		fg.getFeatureValue().add(createFVT("fvtid1", "value1"));
		fg.getFeatureValue().add(createFVT("fvtid2", "value2"));

		OrderVo order = new OrderVo();
		List<LineItemVo> liList = new ArrayList<LineItemVo>();
		liList.add(li);
		liList.add(liP);
		order.setLineItems(liList);
		order.setExternalId("456");

		String json = toJSONString(order);

		logger.debug(json);

	}

	public FeatureGroup createFeatureGroup(final String extId,
			final Integer groupType) {
		ObjectFactory oFactory = new ObjectFactory();
		FeatureGroup fg = oFactory.createSelectedFeaturesTypeFeatureGroup();
		fg.setExternalId(extId);
		fg.setGroupType(groupType);

		return fg;
	}

	public static LineItemVo createLineItem(final String partnerExternalId, final String productId) {
		LineItemVo p = new LineItemVo();
		p.setProductExternalId(productId);
		p.setPartnerExternalId(partnerExternalId);

		return p;
	}

	public static LineItemVo createPromotion(final String partnerExternalId, final String productId,final String appliesTo) {
		LineItemVo p = new LineItemVo();
		p.setPromotion(Boolean.TRUE);
		p.setAppliesTo(appliesTo);
		p.setProductExternalId(productId);
		p.setPartnerExternalId(partnerExternalId);

		return p;
	}

	public static String toJSONString(final OrderVo order) {

		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.alias("order", OrderVo.class);
		xstream.alias("lineitem", LineItemVo.class);
		xstream.alias("featureType", FeatureValueType.class);
		xstream.alias("featureGroup",
				com.AL.xml.v4.SelectedFeaturesType.FeatureGroup.class);

		return xstream.toXML(order);
	}

	public static FeatureValueType createFVT(String id, String value) {
		FeatureValueType fvt2 = new FeatureValueType();
		fvt2.setExternalId(id);
		fvt2.setValue(value);

		return fvt2;
	}

	public String getCombinePromoDesc() {
		return combinePromoDesc;
	}

	public void setCombinePromoDesc(String combinePromoDesc) {
		this.combinePromoDesc = combinePromoDesc;
	}

}
