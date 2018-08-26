package com.A.productResults.vo;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.A.html.Br;
import com.A.html.Del;
import com.A.html.Fieldset;
import com.A.html.H1;
import com.A.html.Li;
import com.A.html.ObjectFactory;
import com.A.html.Span;
import com.A.productResults.util.HtmlBuilder;
import com.A.productResults.util.HtmlFactory;
import com.A.xml.pr.v4.DescriptiveInfoType;
import com.A.xml.pr.v4.FeatureGroupType;
import com.A.xml.pr.v4.FeatureType;
import com.A.xml.pr.v4.ProductInfoType;
import com.A.xml.pr.v4.ProductPromotionType;
import com.A.xml.pr.v4.ProductType;

/**
 * Default formatter extracted from Sales Center Code base
 * Responsible for formatting Price/MarketingInfo/Description
 * 
 * @author Padma
 *
 */
public class ProductDetailsFormatter implements ProductFormatter {

	private static final ObjectFactory oFactory = new ObjectFactory();
	private static ProductDetailsFormatter formatter = new ProductDetailsFormatter();
	
	RTProductHelper rtHelper = null;

	public static ProductDetailsFormatter getInstance() {
		return formatter;
	}

	public ProductDetailsVO format(ProductInfoType prodInfo) {

		final ObjectFactory oFactory = new ObjectFactory();

		List<FeatureType> features = getFeatures(prodInfo);
		List<FeatureGroupType> featureGroup = getFeatureGroups(prodInfo);
		List<ProductPromotionType> promotions = prodInfo.getProductDetails()
				.getPromotion();
		Fieldset fieldset = oFactory.createFieldset();

		if (features != null) {
			fieldset = HtmlFactory.INSTANCE.getFeatureFieldSet(features,
					featureGroup);
		}

		Fieldset promotionsFieldsetList = HtmlFactory.INSTANCE
				.getPromotionsFieldSet(promotions);

		StringBuilder events = new StringBuilder();

		String element = HtmlBuilder.INSTANCE.toString(fieldset);
		events.append(element);

		StringBuilder events1 = new StringBuilder();
		String element1 = HtmlBuilder.INSTANCE.toString(promotionsFieldsetList);
		events1.append(element1);

		Fieldset priceSet = getPriceDescription(prodInfo);
		String priceElement = HtmlBuilder.INSTANCE.toString(priceSet);

		Fieldset productName = getProductName(prodInfo);
		String nameElement = HtmlBuilder.INSTANCE.toString(productName);

		Fieldset marketingHighlights = getMarketingInfo(prodInfo);
		String marketing = HtmlBuilder.INSTANCE.toString(marketingHighlights);

		String longDescription = getLongDescription(prodInfo);

		ProductDetailsVO productVo = new ProductDetailsVO();
		productVo.setExternalId(prodInfo.getExternalId());
		ProductType product = prodInfo.getProduct();
		productVo.populateProductSummary(product);
		productVo.populateCapabilities(prodInfo);
		if (prodInfo.getProductDetails() != null) {
			productVo.populateMarketingHighlights(prodInfo);
			productVo.populateDescriptiveInfo(prodInfo);
			productVo.populatePromotions(prodInfo);
		}
		productVo.setFeatureBlk(events.toString());
		productVo.setPromotionBlk(events1.toString());
		productVo.setImage(product.getProvider().getExternalId());
		productVo.setName(nameElement);
		productVo.setLongDescription(longDescription);
		productVo.setPriceBlk(priceElement);
		productVo.setMarketingHighlightsBlk(marketing);

		return productVo;
	}

	protected List<FeatureGroupType> getFeatureGroups(ProductInfoType prodInfo) {
		List<FeatureGroupType> featureGroup = prodInfo.getProductDetails()
				.getFeatureGroup();
		return featureGroup;
	}

	protected List<FeatureType> getFeatures(ProductInfoType prodInfo) {
		
		List<FeatureType> features = prodInfo.getProductDetails().getFeature();
		if(rtHelper != null){
			features = rtHelper.getFeatures(prodInfo);
		}
		return features;
	}

	private String getLongDescription(ProductInfoType prodInfo) {
		String longDescription = "";
		if (prodInfo.getProductDetails() != null
				&& !CollectionUtils.isEmpty(prodInfo.getProductDetails()
						.getDescriptiveInfo())) {
			for (DescriptiveInfoType infoType : prodInfo.getProductDetails()
					.getDescriptiveInfo()) {
				if (infoType.getType() != null
						&& infoType.getType().equals("longDescription")) {
					longDescription = infoType.getValue();
				}
			}
		}
		return longDescription;
	}

	// Product Name
	private Fieldset getProductName(ProductInfoType product) {
		String name = product.getProduct().getName();
		Fieldset set = oFactory.createFieldset();
		H1 cellPName = oFactory.createH1();
		cellPName.getClazz().add("productName");
		cellPName.getContent().add(name);
		set.getContent().add(cellPName);
		return set;
	}

	// Price Description
	private Fieldset getPriceDescription(ProductInfoType product) {
		Double baseRecurringPrice = product.getProduct().getPriceInfo()
				.getBaseRecurringPrice();
		Double baseNonRecurringPrice = product.getProduct().getPriceInfo()
				.getBaseNonRecurringPrice();
		String promotionCode = "";
		Float promotionPrice = 0.0f;
		DecimalFormat format = new DecimalFormat("#0.00");

		if (product.getProductDetails().getPromotion() != null
				&& product.getProductDetails().getPromotion().size() > 0) {
			int i = 0;
			for (ProductPromotionType promotion : product.getProductDetails()
					.getPromotion()) {
				if (promotion != null) {
					if (i == 0) {
						promotionCode = promotion.getPromoCode();
						promotionPrice = promotion.getPriceValue();
					}
				}
				if (promotion.getMetaData() != null) {
					if (!CollectionUtils.isEmpty(promotion.getMetaData()
							.getMetaData())) {
						String strMetaData = promotion.getMetaData()
								.getMetaData().get(0);
						if (strMetaData != null
								&& strMetaData.equals("ADVERTISE")) {
							promotionCode = promotion.getPromoCode();
							promotionPrice = promotion.getPriceValue();
						}
					}
				}
				i++;
			}

		}

		Fieldset set = oFactory.createFieldset();
		Span cellPBPrice = oFactory.createSpan();
		cellPBPrice.getClazz().add("productBaseInfo");

		Br br = oFactory.createBr();
		Del del = oFactory.createDel();
		cellPBPrice.getContent().add("Base Price");
		cellPBPrice.getContent().add(br);

		if (promotionCode != null
				&& promotionCode != ""
				&& (promotionPrice != null && baseRecurringPrice != null && promotionPrice < baseRecurringPrice)) {
			cellPBPrice.getContent().add("$" + format.format(promotionPrice));// Add
																				// Promotion
																				// Price
																				// real
																				// data
			cellPBPrice.getContent().add(br);
			del.getContent().add("$" + format.format(baseRecurringPrice));// Add
																			// Recurring
																			// Price
																			// real
																			// data
			cellPBPrice.getContent().add(del);
		} else {
			cellPBPrice.getContent().add(
					"$" + format.format(baseRecurringPrice));// Add base
																// recurring
																// Price
		}
		/*
		 * cellPBPrice.getContent().add(br);
		 * cellPBPrice.getContent().add("$"+format
		 * .format(baseNonRecurringPrice));//Add Non Recurring Price real data
		 */
		set.getContent().add(cellPBPrice);
		return set;

	}

	// Marketing HighLights
	private Fieldset getMarketingInfo(ProductInfoType product) {
		Fieldset set = oFactory.createFieldset();
		if (product.getProductDetails() != null) {
			if (product.getProductDetails().getMarketingHighlights() != null) {
				for (String m_list : product.getProductDetails()
						.getMarketingHighlights().getMarketingHighlight()) {
					String result = m_list.replaceAll("[&amp;#8482;]", "");
					// String marketingList=m_list.replaceAll("&amp;#8482;",
					// "");
					System.out.println("MarketingHighlights list::::::::::::"
							+ result);
					Li list = oFactory.createLi();
					Span span = oFactory.createSpan();
					span.getContent().add(result);
					span.getClazz().add("productListSpan");
					list.getContent().add(span);
					set.getContent().add(list);
					set.setClazz("productList");
				}
			}
			if (product.getProductDetails().getPromotion() != null
					&& product.getProductDetails().getPromotion().size() > 0) {
				int i = 0;
				for (ProductPromotionType promotion : product
						.getProductDetails().getPromotion()) {
					if (promotion != null) {
						if (i == 0) {
							Li list = oFactory.createLi();
							Span span = oFactory.createSpan();
							span.getContent().add(
									promotion.getShortDescription());
							span.getClazz().add("productListSpan");
							span.setStyle("color:#F00");
							list.getContent().add(span);
							set.getContent().add(list);
							set.setClazz("productList");
						}
					}
					if (promotion.getMetaData() != null) {
						if (promotion.getMetaData().getMetaData() != null
								&& promotion.getMetaData().getMetaData().size() > 0) {
							String strMetaData = promotion.getMetaData()
									.getMetaData().get(0);
							if (strMetaData != null
									&& strMetaData.equals("ADVERTISE")) {
								Li list = oFactory.createLi();
								Span span = oFactory.createSpan();
								span.getContent().add(
										promotion.getShortDescription());
								span.getClazz().add("productListSpan");
								span.setStyle("color:#F00");
								list.getContent().add(span);
								set.getContent().add(list);
								set.setClazz("productList");
							}
						}
					}
					i++;
				}
			}
		}
		return set;
	}

	public void setRTHelper(RTProductHelper rtHelper) {
		this.rtHelper = rtHelper;		
	}
}
