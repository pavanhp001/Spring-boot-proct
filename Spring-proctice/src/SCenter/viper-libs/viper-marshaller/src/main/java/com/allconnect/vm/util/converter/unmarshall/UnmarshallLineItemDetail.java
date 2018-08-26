package com.A.vm.util.converter.unmarshall;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.GDuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.A.V.beans.ProductBase;
import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.LineItemDetail;
import com.A.V.beans.entity.Product;
import com.A.V.beans.entity.ProductPromotion;
import com.A.vm.service.CatalogProductService;
import com.A.xml.v4.ApplicableType;
import com.A.xml.v4.ChannelType;
import com.A.xml.v4.ItemCategory;
import com.A.xml.v4.LineItemDetailType;
import com.A.xml.v4.OrderLineItemDetailTypeType;
import com.A.xml.v4.PriceInfoType;
import com.A.xml.v4.ProductBundleType;
import com.A.xml.v4.ProductCategoryListType;
import com.A.xml.v4.ProductPromotionType;
import com.A.xml.v4.ProductType;
import com.A.xml.v4.ProductType.CapabilityList;
import com.A.xml.v4.ProviderType;

@Component("unmarshallLineItemDetail")
public final class UnmarshallLineItemDetail {

	private static final Logger logger = Logger
			.getLogger(UnmarshallLineItemDetail.class);
	private static final String PRODUCT = "product";
	private static final String PRODUCT_PROMOTION = "productPromotion";
	private static final String PRODUCT_BUNDLE = "productBundle";

	@Autowired(required = false)
	private CatalogProductService catalogProductService;

	public UnmarshallLineItemDetail() {
		super();
	}

	public LineItemDetail copyLineItemDetailBean(final LineItem destLineItem,
			final LineItemDetailType srcLineItemType) {

		LineItemDetail destLIDetailBean = new LineItemDetail();

		Long productUniqueId = srcLineItemType.getProductUniqueId();
		destLIDetailBean.setProductUniqueId(productUniqueId);

		if (srcLineItemType.getDetailType() != null) {
			String itemDetailType = srcLineItemType.getDetailType().toString();
			destLIDetailBean
					.setType(srcLineItemType.getDetailType().toString());

			if (itemDetailType.equalsIgnoreCase(PRODUCT)) {

				copyProduct(productUniqueId, srcLineItemType, destLineItem,
						destLIDetailBean);

			} else if (itemDetailType.equalsIgnoreCase(PRODUCT_PROMOTION)) {
				copyProductPromotion(productUniqueId, srcLineItemType,
						destLineItem, destLIDetailBean);

			} else if (itemDetailType.equalsIgnoreCase(PRODUCT_BUNDLE)) {

				copyProductBundle(productUniqueId, srcLineItemType,
						destLineItem, destLIDetailBean);
			}
		}
		return destLIDetailBean;

	}

	public void copyProductBundle(Long productUniqueId,
			final LineItemDetailType srcLIType, final LineItem destLineItem,
			LineItemDetail destLIDetailBean) {
		logger.debug("Unmarshalling Product Bundle");
		if (srcLIType.getDetail() != null
				&& srcLIType.getDetail().getProductBundleLineItem() != null
				&& productUniqueId > 0) {
			ProductBundleType bundleType = srcLIType.getDetail()
					.getProductBundleLineItem();
			destLIDetailBean.setLineItemDetailExternalId(bundleType
					.getExternalId());
			Product bundleProduct = catalogProductService
					.findCatalogProductById(productUniqueId);
			bundleType.setProviderId(bundleProduct.getProductBase()
					.getProviderExternalId());
			destLIDetailBean.setType("ProductBundle");
		}

	}

	public void copyProductPromotion(Long productUniqueId,
			final LineItemDetailType srcLIType, final LineItem destLineItem,
			LineItemDetail destLIDetailBean) {
		logger.debug("Unmarshalling Product Promotion");
		if (srcLIType.getDetail() != null
				&& srcLIType.getDetail().getPromotionLineItem() != null) {
			ApplicableType applicableType = srcLIType.getDetail()
					.getPromotionLineItem();
			ProductPromotionType promotionType = applicableType.getPromotion();
			destLIDetailBean.setLineItemDetailExternalId(promotionType
					.getExternalId());
			ProductPromotion promotionBean = catalogProductService
					.findProductPromotionById(productUniqueId,
							promotionType.getExternalId());
			//ProductPromotion promotionBean = catalogPromotionService.findCatalogPromotionById(promotionType.getExternalId());
			if (promotionBean != null) {
				promotionType.setConditions(promotionBean.getConditions());
				promotionType.setDescription(promotionBean.getDescription());
				promotionType.setShortDescription(promotionBean.getShortDescription());
				promotionType.setPriceValue(promotionBean.getPriceValue());
				promotionType.setPromoCode(promotionBean.getPromotionCode());
				promotionType.setPromotionDuration(new GDuration(promotionBean.getPromotionDuration()));
				
				if (promotionBean.getPriceValueType() != null) {

					try {
						com.A.xml.v4.ProductPromotionType.PriceValueType.Enum productPromotionEnum = ProductPromotionType.PriceValueType.Enum
								.forString(promotionBean.getPriceValueType());
						promotionType.setPriceValueType(productPromotionEnum);
					} catch (Exception e) {
						logger.warn(String
								.format("unable to marshall product promotion price value type %s",
										e.getMessage()));
					}
				}
			} else {
				logger.warn("Promotion not found"
						+ promotionType.getExternalId());
			}

			if ((applicableType != null)
					&& (applicableType.getAppliesToList() != null)
					&& (applicableType.getAppliesToList().size() > 0)) {
				List<Integer> appliesToList = applicableType.getAppliesToList();
				destLIDetailBean.setAppliesToList(appliesToList);
			}

			destLIDetailBean.setType("ProductPromotion");
			destLIDetailBean.setProductUniqueId(productUniqueId);
		}

	}

	public void copyProduct(Long productUniqueId,
			final LineItemDetailType srcLineItemType,
			final LineItem destDomainLineItem,
			LineItemDetail destDomainLineItemDetail) {

		logger.debug("Unmarshalling product.");
		if (srcLineItemType.getDetail() != null
				&& srcLineItemType.getDetail().getProductLineItem() != null) {

			ProductType productType = srcLineItemType.getDetail()
					.getProductLineItem();
			String prodLIExtId = srcLineItemType.getDetail()
					.getProductLineItem().getExternalId();
			destDomainLineItemDetail.setLineItemDetailExternalId(prodLIExtId);

			if (productUniqueId == 0) {
				String patternProductUniqueId = "99"
						+ (System.nanoTime() % 10000) + "99";
				destDomainLineItemDetail.setProductUniqueId(Long
						.valueOf(patternProductUniqueId));
			}

			copyIncomingProductInformation(productUniqueId, srcLineItemType,
					destDomainLineItem, destDomainLineItemDetail);

			Product product = catalogProductService
					.findCatalogProductById(productUniqueId);

			if (product != null) {

				if (product.getProductBase() == null) {
					product.setProductBase(new ProductBase());
				}

				if ((product != null) && (product.getProductBase() != null)) {
					productType.setName(product.getProductBase()
							.getDisplayName());
				}
				destDomainLineItemDetail.setType("Product");

				if (product.getProductBase() != null) {
					ProductBase productBase = product.getProductBase();
					destDomainLineItemDetail.setName(productBase
							.getDisplayName());

					//For harmony orders we will not have product detail, so save what is being sent from harmony adapter
					if(productBase.getItemCategoryName() == null || productBase.getItemCategoryName().trim().length() == 0){
						destDomainLineItemDetail.setCategory(getCategory(productType));
					}else{
						destDomainLineItemDetail.setCategory(productBase.getItemCategoryName());
					}
				}

				if ((productType != null)
						&& (productType.getProvider() != null)
						&& (product != null)
						&& (product.getProductBase() != null)) {
					destDomainLineItem.setProviderExternalId(product
							.getProductBase().getProviderExternalId());
				}

			}else{
				destDomainLineItemDetail.setCategory(getCategory(productType));
			}

		}
	}

	private String getCategory(ProductType productType) {
		String category = "";
		if(productType != null && productType.getProductCategoryList() != null && productType.getProductCategoryList().getProductCategoryList() != null){
			List<ItemCategory> catList = productType.getProductCategoryList().getProductCategoryList();
			if(catList != null && !catList.isEmpty()){
				category = catList.get(0).getDisplayName();
				logger.debug("Product Category : " + category);
			}
		}
		return category;
	}

	public void copyIncomingProductInformation(Long productUniqueId,
			final LineItemDetailType srcLineItemType,
			final LineItem destDomainLineItem,
			final LineItemDetail destDomainLineItemDetail) {

		OrderLineItemDetailTypeType srcLineItemDetailType = srcLineItemType
				.getDetail();

		if (srcLineItemDetailType != null) {
			CapabilityList capabilityList = srcLineItemDetailType
					.getProductLineItem().getCapabilityList();
			ChannelType channelType = srcLineItemDetailType
					.getProductLineItem().getChannels();
			String externalId = srcLineItemDetailType.getProductLineItem()
					.getExternalId();
			String productExternalId = srcLineItemDetailType
					.getProductLineItem().getItemExternalId();
			String name = srcLineItemDetailType.getProductLineItem().getName();
			PriceInfoType priceInfo = srcLineItemDetailType
					.getProductLineItem().getPriceInfo();
			ProductCategoryListType categoryList = srcLineItemDetailType
					.getProductLineItem().getProductCategoryList();
			ProviderType provider = srcLineItemDetailType.getProductLineItem()
					.getProvider();

		}

	}

	public void copyCapability(final LineItem destDomainLineItem,
			CapabilityList capabilityList) {

	}
}
