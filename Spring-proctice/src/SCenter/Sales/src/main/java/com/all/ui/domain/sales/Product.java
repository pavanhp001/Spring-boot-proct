package com.AL.ui.domain.sales;

import java.util.ArrayList;
import java.util.List;

public class Product {
	private List<String> capabilities;
	private List<String> channels;
	private String externalId;
	private List<ProductFeature>includedFeatures = new ArrayList<ProductFeature>();
	private List<ProductFeature>optionalFeatures = new ArrayList<ProductFeature>();
	private List<NameValuePair> merchandisingAttributes;
	private double merchandisingPriority;

	private String name;
	private PriceInformation priceInformation;

	private List<ProductCategory> productCategories;
	private List<ProductFeatureGroup> featureGroup;

	private ProductDetails productDetails;

	private List<ProductPromotion>promotions =new ArrayList<ProductPromotion>();

	private String providerExternalId;
	private String parentProviderExternalId;
	private Provider provider;
	private boolean sellable;

	public Product() {}

	public void addCapability(String c) {
		getCapabilities().add(c);
	}

	public void addChannel(String c) {
		getChannels().add(c);
	}

	public void addMerchandisingAttribute(NameValuePair merchandisingAttribute) {
		getMerchandisingAttributes().add(merchandisingAttribute);
	}

	public void addProductCategory(String name, String value) {
		ProductCategory pc = new ProductCategory();
		pc.setDisplayName(name);
		pc.setValue(value);
		addProductCategory(pc);
	}

	public void addProductCategory(ProductCategory category) {
		getProductCategories().add(category);
	}

	public List<String> getCapabilities() {
		if (capabilities == null) {
			capabilities = new ArrayList<String>();
		}
		return capabilities;
	}

	public List<String> getChannels() {
		if (channels == null) {
			channels = new ArrayList<String>();
		}
		return channels;
	}

	public String getExternalId() {
		return externalId;
	}

	public List<NameValuePair> getMerchandisingAttributes() {
		if (merchandisingAttributes == null) {
			merchandisingAttributes = new ArrayList<NameValuePair>();
		}
		return merchandisingAttributes;
	}
	
	public double getMerchandisingPriority() {
		return merchandisingPriority;
	}
	
	public String getName() {
		return name;
	}

	public PriceInformation getPriceInformation() {
		return priceInformation;
	}

	public List<ProductCategory> getProductCategories() {
		if (productCategories == null) {
			productCategories = new ArrayList<ProductCategory>();
		}
		return productCategories;
	}
	
	public ProductDetails getProductDetails() {
		return productDetails;
	}

	public Provider getProvider() {
		return provider;
	}

	public boolean isSellable() {
		return sellable;
	}

	public void setCapabilities(List<String> c) {
		getCapabilities().addAll(c);
	}

	public void setChannels(List<String> c) {
		getChannels().addAll(c);
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public void setMerchandisingPriority(double priority) {
		this.merchandisingPriority = priority;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPriceInformation(PriceInformation priceInfoType) {
		priceInformation = priceInfoType;
	}

	public void setProductCategories(List<ProductCategory> categories) {
		getProductCategories().addAll(categories);
	}

	public void setProductDetails(ProductDetails productDetails) {
		this.productDetails = productDetails;
	}

	public void setProvider(Provider provider) {
		if (provider != null) {
			providerExternalId = provider.getExternalId();

			if (provider.getParent() != null) {
				setParentProviderExternalId(provider.getParent().getExternalId());
			}
		}
	}

	public void setSellable(boolean sellable) {
		this.sellable = sellable;
	}

	public List<ProductPromotion> getPromotions() {
		if (promotions == null) {
			promotions = new ArrayList<ProductPromotion>();
		}
		return promotions;
	}

	public void setPromotions(List<ProductPromotion> promotions) {
		this.promotions = promotions;
	}

	public void setMerchandisingAttributes(List<NameValuePair> merchandisingAttributes) {
		getMerchandisingAttributes().addAll(merchandisingAttributes);
	}

	public List<ProductFeature> getIncludedFeatures() {
		if (includedFeatures == null) {
			includedFeatures = new ArrayList<ProductFeature>();
		}
		return includedFeatures;
	}

	public void setIncludedFeatures(List<ProductFeature> includedFeatures) {
		this.includedFeatures = includedFeatures;
	}

	public List<ProductFeature> getOptionalFeatures() {
		if (optionalFeatures == null) {
			optionalFeatures = new ArrayList<ProductFeature>();
		}
		return optionalFeatures;
	}

	public void setOptionalFeatures(List<ProductFeature> optionalFeatures) {
		this.optionalFeatures = optionalFeatures;
	}

   public void addFeature(ProductFeature feature) {
      if (feature == null) {
         return;
      }
      if (feature.getIncluded() != null) {
         getIncludedFeatures().add(feature);
      } else {
         getOptionalFeatures().add(feature);
      }
   }
   
	public void setProviderExternalId(String arg) {
		providerExternalId = arg;
	}

	public String getProviderExternalId() {
		if (providerExternalId != null) {
			return providerExternalId;
		}
		if (getProvider() != null) {
			return getProvider().getExternalId();
		}

		return null;
	}

	public String getParentProviderExternalId() {
		return parentProviderExternalId;
	}

	public void setParentProviderExternalId(String parentProviderExternalId) {
		this.parentProviderExternalId = parentProviderExternalId;
	}

	public List<ProductFeatureGroup> getFeatureGroup() {
		if (featureGroup == null) {
			featureGroup = new ArrayList<ProductFeatureGroup>();
		}

		return featureGroup;
	}

	public void setFeatureGroup(List<ProductFeatureGroup> productFeatureGroups) {
		this.featureGroup = productFeatureGroups;
	}

	public void addFeatureGroup(ProductFeatureGroup pfg) {
		getFeatureGroup().add(pfg);
	}
}
