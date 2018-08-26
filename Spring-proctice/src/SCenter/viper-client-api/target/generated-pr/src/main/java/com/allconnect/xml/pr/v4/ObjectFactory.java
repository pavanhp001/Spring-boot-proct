
package com.A.xml.pr.v4;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.A.xml.pr.v4 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EntryType_QNAME = new QName("http://xml.A.com/v4", "entryType");
    private final static QName _Dob_QNAME = new QName("http://xml.A.com/v4", "dob");
    private final static QName _ServiceableAddress_QNAME = new QName("http://xml.A.com/v4", "serviceableAddress");
    private final static QName _Extension_QNAME = new QName("http://xml.A.com/v4", "extension");
    private final static QName _EMailAddress_QNAME = new QName("http://xml.A.com/v4", "eMailAddress");
    private final static QName _TransactionType_QNAME = new QName("http://xml.A.com/v4", "transactionType");
    private final static QName _Referrer_QNAME = new QName("http://xml.A.com/v4", "referrer");
    private final static QName _EncryptedPassword_QNAME = new QName("http://xml.A.com/v4", "encryptedPassword");
    private final static QName _PriceTierList_QNAME = new QName("http://xml.A.com/v4", "priceTierList");
    private final static QName _Price_QNAME = new QName("http://xml.A.com/v4", "price");
    private final static QName _MarketItemDetail_QNAME = new QName("http://xml.A.com/v4", "marketItemDetail");
    private final static QName _OrderSource_QNAME = new QName("http://xml.A.com/v4", "orderSource");
    private final static QName _MiddleName_QNAME = new QName("http://xml.A.com/v4", "middleName");
    private final static QName _LastName_QNAME = new QName("http://xml.A.com/v4", "lastName");
    private final static QName _ProductEnterpriseRequest_QNAME = new QName("http://xml.A.com/v4", "productEnterpriseRequest");
    private final static QName _FirstName_QNAME = new QName("http://xml.A.com/v4", "firstName");
    private final static QName _Dialog_QNAME = new QName("http://xml.A.com/v4", "dialog");
    private final static QName _Gender_QNAME = new QName("http://xml.A.com/v4", "gender");
    private final static QName _Customization_QNAME = new QName("http://xml.A.com/v4", "customization");
    private final static QName _AgentUserName_QNAME = new QName("http://xml.A.com/v4", "agentUserName");
    private final static QName _PhoneNumber_QNAME = new QName("http://xml.A.com/v4", "phoneNumber");
    private final static QName _AcMessage_QNAME = new QName("http://xml.A.com/v4", "acMessage");
    private final static QName _Request_QNAME = new QName("http://xml.A.com/v4", "request");
    private final static QName _Response_QNAME = new QName("http://xml.A.com/v4", "response");
    private final static QName _Id_QNAME = new QName("http://xml.A.com/v4", "id");
    private final static QName _Title_QNAME = new QName("http://xml.A.com/v4", "title");
    private final static QName _ProcessRequestResponse_QNAME = new QName("http://xml.A.com/v4", "processRequestResponse");
    private final static QName _ReferredAddress_QNAME = new QName("http://xml.A.com/v4", "referredAddress");
    private final static QName _ProductEnterpriseResponse_QNAME = new QName("http://xml.A.com/v4", "productEnterpriseResponse");
    private final static QName _Ssn_QNAME = new QName("http://xml.A.com/v4", "ssn");
    private final static QName _ProductRequest_QNAME = new QName("http://xml.A.com/v4", "productRequest");
    private final static QName _NameSuffix_QNAME = new QName("http://xml.A.com/v4", "nameSuffix");
    private final static QName _ProductResponse_QNAME = new QName("http://xml.A.com/v4", "productResponse");
    private final static QName _ProcessRequest_QNAME = new QName("http://xml.A.com/v4", "processRequest");
    private final static QName _AddressTypeGasStartAt_QNAME = new QName("http://xml.A.com/v4", "gasStartAt");
    private final static QName _AddressTypeExpiration_QNAME = new QName("http://xml.A.com/v4", "expiration");
    private final static QName _AddressTypeElectricityStartAt_QNAME = new QName("http://xml.A.com/v4", "electricityStartAt");
    private final static QName _AddressTypeInEffect_QNAME = new QName("http://xml.A.com/v4", "inEffect");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.A.xml.pr.v4
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FeatureValueType }
     * 
     */
    public FeatureValueType createFeatureValueType() {
        return new FeatureValueType();
    }

    /**
     * Create an instance of {@link PriceFields }
     * 
     */
    public PriceFields createPriceFields() {
        return new PriceFields();
    }

    /**
     * Create an instance of {@link FeatureDependencyListType }
     * 
     */
    public FeatureDependencyListType createFeatureDependencyListType() {
        return new FeatureDependencyListType();
    }

    /**
     * Create an instance of {@link SelectedFeaturesType.Features }
     * 
     */
    public SelectedFeaturesType.Features createSelectedFeaturesTypeFeatures() {
        return new SelectedFeaturesType.Features();
    }

    /**
     * Create an instance of {@link ProductTransactionTypeType }
     * 
     */
    public ProductTransactionTypeType createProductTransactionTypeType() {
        return new ProductTransactionTypeType();
    }

    /**
     * Create an instance of {@link SelectedDialogsType.Dialogs }
     * 
     */
    public SelectedDialogsType.Dialogs createSelectedDialogsTypeDialogs() {
        return new SelectedDialogsType.Dialogs();
    }

    /**
     * Create an instance of {@link PriceTierType }
     * 
     */
    public PriceTierType createPriceTierType() {
        return new PriceTierType();
    }

    /**
     * Create an instance of {@link BundleInfoType }
     * 
     */
    public BundleInfoType createBundleInfoType() {
        return new BundleInfoType();
    }

    /**
     * Create an instance of {@link FeatureDependencyType }
     * 
     */
    public FeatureDependencyType createFeatureDependencyType() {
        return new FeatureDependencyType();
    }

    /**
     * Create an instance of {@link ArbitraryXML }
     * 
     */
    public ArbitraryXML createArbitraryXML() {
        return new ArbitraryXML();
    }

    /**
     * Create an instance of {@link FeatureGroupType }
     * 
     */
    public FeatureGroupType createFeatureGroupType() {
        return new FeatureGroupType();
    }

    /**
     * Create an instance of {@link ProductResponseType }
     * 
     */
    public ProductResponseType createProductResponseType() {
        return new ProductResponseType();
    }

    /**
     * Create an instance of {@link SelectionChoiceType }
     * 
     */
    public SelectionChoiceType createSelectionChoiceType() {
        return new SelectionChoiceType();
    }

    /**
     * Create an instance of {@link CustomerPhoneEntry }
     * 
     */
    public CustomerPhoneEntry createCustomerPhoneEntry() {
        return new CustomerPhoneEntry();
    }

    /**
     * Create an instance of {@link OrderStatusWithTypeType }
     * 
     */
    public OrderStatusWithTypeType createOrderStatusWithTypeType() {
        return new OrderStatusWithTypeType();
    }

    /**
     * Create an instance of {@link PromotionResultType }
     * 
     */
    public PromotionResultType createPromotionResultType() {
        return new PromotionResultType();
    }

    /**
     * Create an instance of {@link CatalogedProductList }
     * 
     */
    public CatalogedProductList createCatalogedProductList() {
        return new CatalogedProductList();
    }

    /**
     * Create an instance of {@link CustomerContextType }
     * 
     */
    public CustomerContextType createCustomerContextType() {
        return new CustomerContextType();
    }

    /**
     * Create an instance of {@link LineItemStatusType }
     * 
     */
    public LineItemStatusType createLineItemStatusType() {
        return new LineItemStatusType();
    }

    /**
     * Create an instance of {@link CoreMarketItemType }
     * 
     */
    public CoreMarketItemType createCoreMarketItemType() {
        return new CoreMarketItemType();
    }

    /**
     * Create an instance of {@link ProductRequestType }
     * 
     */
    public ProductRequestType createProductRequestType() {
        return new ProductRequestType();
    }

    /**
     * Create an instance of {@link BusinessPartyDetailType }
     * 
     */
    public BusinessPartyDetailType createBusinessPartyDetailType() {
        return new BusinessPartyDetailType();
    }

    /**
     * Create an instance of {@link LineItemSelectionType }
     * 
     */
    public LineItemSelectionType createLineItemSelectionType() {
        return new LineItemSelectionType();
    }

    /**
     * Create an instance of {@link StatusType }
     * 
     */
    public StatusType createStatusType() {
        return new StatusType();
    }

    /**
     * Create an instance of {@link DataConstraintType.StringConstraint.ListOfValues }
     * 
     */
    public DataConstraintType.StringConstraint.ListOfValues createDataConstraintTypeStringConstraintListOfValues() {
        return new DataConstraintType.StringConstraint.ListOfValues();
    }

    /**
     * Create an instance of {@link ChoiceType }
     * 
     */
    public ChoiceType createChoiceType() {
        return new ChoiceType();
    }

    /**
     * Create an instance of {@link CcpCommunicationEventsType }
     * 
     */
    public CcpCommunicationEventsType createCcpCommunicationEventsType() {
        return new CcpCommunicationEventsType();
    }

    /**
     * Create an instance of {@link PromotionElementType }
     * 
     */
    public PromotionElementType createPromotionElementType() {
        return new PromotionElementType();
    }

    /**
     * Create an instance of {@link PaymentStatusWithTypeType }
     * 
     */
    public PaymentStatusWithTypeType createPaymentStatusWithTypeType() {
        return new PaymentStatusWithTypeType();
    }

    /**
     * Create an instance of {@link ProviderType.CapabilityList }
     * 
     */
    public ProviderType.CapabilityList createProviderTypeCapabilityList() {
        return new ProviderType.CapabilityList();
    }

    /**
     * Create an instance of {@link DetailElementType.DetailType }
     * 
     */
    public DetailElementType.DetailType createDetailElementTypeDetailType() {
        return new DetailElementType.DetailType();
    }

    /**
     * Create an instance of {@link PhoneNumberType }
     * 
     */
    public PhoneNumberType createPhoneNumberType() {
        return new PhoneNumberType();
    }

    /**
     * Create an instance of {@link CustomerContextEntityType }
     * 
     */
    public CustomerContextEntityType createCustomerContextEntityType() {
        return new CustomerContextEntityType();
    }

    /**
     * Create an instance of {@link ProductRequestType.ProviderList }
     * 
     */
    public ProductRequestType.ProviderList createProductRequestTypeProviderList() {
        return new ProductRequestType.ProviderList();
    }

    /**
     * Create an instance of {@link EnterpriseRequestDocumentType }
     * 
     */
    public EnterpriseRequestDocumentType createEnterpriseRequestDocumentType() {
        return new EnterpriseRequestDocumentType();
    }

    /**
     * Create an instance of {@link DataConstraintType.StringConstraint }
     * 
     */
    public DataConstraintType.StringConstraint createDataConstraintTypeStringConstraint() {
        return new DataConstraintType.StringConstraint();
    }

    /**
     * Create an instance of {@link ProductDetailType.Detail }
     * 
     */
    public ProductDetailType.Detail createProductDetailTypeDetail() {
        return new ProductDetailType.Detail();
    }

    /**
     * Create an instance of {@link SelectedDialogsType }
     * 
     */
    public SelectedDialogsType createSelectedDialogsType() {
        return new SelectedDialogsType();
    }

    /**
     * Create an instance of {@link CcpCommunicationEventType }
     * 
     */
    public CcpCommunicationEventType createCcpCommunicationEventType() {
        return new CcpCommunicationEventType();
    }

    /**
     * Create an instance of {@link ItemPromoType }
     * 
     */
    public ItemPromoType createItemPromoType() {
        return new ItemPromoType();
    }

    /**
     * Create an instance of {@link RequiredFeatureType }
     * 
     */
    public RequiredFeatureType createRequiredFeatureType() {
        return new RequiredFeatureType();
    }

    /**
     * Create an instance of {@link FeatureCapabilityType }
     * 
     */
    public FeatureCapabilityType createFeatureCapabilityType() {
        return new FeatureCapabilityType();
    }

    /**
     * Create an instance of {@link MailingAddressType }
     * 
     */
    public MailingAddressType createMailingAddressType() {
        return new MailingAddressType();
    }

    /**
     * Create an instance of {@link ImpactAreaListType }
     * 
     */
    public ImpactAreaListType createImpactAreaListType() {
        return new ImpactAreaListType();
    }

    /**
     * Create an instance of {@link ReferenceResultType.Referrers }
     * 
     */
    public ReferenceResultType.Referrers createReferenceResultTypeReferrers() {
        return new ReferenceResultType.Referrers();
    }

    /**
     * Create an instance of {@link MarketItemDetailType }
     * 
     */
    public MarketItemDetailType createMarketItemDetailType() {
        return new MarketItemDetailType();
    }

    /**
     * Create an instance of {@link ProviderType }
     * 
     */
    public ProviderType createProviderType() {
        return new ProviderType();
    }

    /**
     * Create an instance of {@link EnterpriseResponseDocumentType }
     * 
     */
    public EnterpriseResponseDocumentType createEnterpriseResponseDocumentType() {
        return new EnterpriseResponseDocumentType();
    }

    /**
     * Create an instance of {@link NameValuePairType }
     * 
     */
    public NameValuePairType createNameValuePairType() {
        return new NameValuePairType();
    }

    /**
     * Create an instance of {@link MarketItemPromoType }
     * 
     */
    public MarketItemPromoType createMarketItemPromoType() {
        return new MarketItemPromoType();
    }

    /**
     * Create an instance of {@link ProviderSourceType }
     * 
     */
    public ProviderSourceType createProviderSourceType() {
        return new ProviderSourceType();
    }

    /**
     * Create an instance of {@link TelephonyType }
     * 
     */
    public TelephonyType createTelephonyType() {
        return new TelephonyType();
    }

    /**
     * Create an instance of {@link MarketItemType }
     * 
     */
    public MarketItemType createMarketItemType() {
        return new MarketItemType();
    }

    /**
     * Create an instance of {@link ChannelType }
     * 
     */
    public ChannelType createChannelType() {
        return new ChannelType();
    }

    /**
     * Create an instance of {@link ItemType }
     * 
     */
    public ItemType createItemType() {
        return new ItemType();
    }

    /**
     * Create an instance of {@link NotificationEventType }
     * 
     */
    public NotificationEventType createNotificationEventType() {
        return new NotificationEventType();
    }

    /**
     * Create an instance of {@link SelectedFeaturesType }
     * 
     */
    public SelectedFeaturesType createSelectedFeaturesType() {
        return new SelectedFeaturesType();
    }

    /**
     * Create an instance of {@link MarketItemWithCapabilitiesType }
     * 
     */
    public MarketItemWithCapabilitiesType createMarketItemWithCapabilitiesType() {
        return new MarketItemWithCapabilitiesType();
    }

    /**
     * Create an instance of {@link ItemCategory }
     * 
     */
    public ItemCategory createItemCategory() {
        return new ItemCategory();
    }

    /**
     * Create an instance of {@link PriceField }
     * 
     */
    public PriceField createPriceField() {
        return new PriceField();
    }

    /**
     * Create an instance of {@link PaymentsType }
     * 
     */
    public PaymentsType createPaymentsType() {
        return new PaymentsType();
    }

    /**
     * Create an instance of {@link DialogValueType.Value }
     * 
     */
    public DialogValueType.Value createDialogValueTypeValue() {
        return new DialogValueType.Value();
    }

    /**
     * Create an instance of {@link TelephonyListType }
     * 
     */
    public TelephonyListType createTelephonyListType() {
        return new TelephonyListType();
    }

    /**
     * Create an instance of {@link EnergyProduct }
     * 
     */
    public EnergyProduct createEnergyProduct() {
        return new EnergyProduct();
    }

    /**
     * Create an instance of {@link DescriptiveInfoType }
     * 
     */
    public DescriptiveInfoType createDescriptiveInfoType() {
        return new DescriptiveInfoType();
    }

    /**
     * Create an instance of {@link com.A.xml.pr.v4.PromotionType }
     * 
     */
    public com.A.xml.pr.v4.PromotionType createPromotionType() {
        return new com.A.xml.pr.v4.PromotionType();
    }

    /**
     * Create an instance of {@link ProductResponseType.ProductBundles }
     * 
     */
    public ProductResponseType.ProductBundles createProductResponseTypeProductBundles() {
        return new ProductResponseType.ProductBundles();
    }

    /**
     * Create an instance of {@link MarketingHighlightType }
     * 
     */
    public MarketingHighlightType createMarketingHighlightType() {
        return new MarketingHighlightType();
    }

    /**
     * Create an instance of {@link SalesContextEntityType }
     * 
     */
    public SalesContextEntityType createSalesContextEntityType() {
        return new SalesContextEntityType();
    }

    /**
     * Create an instance of {@link ProductCategoryListType }
     * 
     */
    public ProductCategoryListType createProductCategoryListType() {
        return new ProductCategoryListType();
    }

    /**
     * Create an instance of {@link ProductType }
     * 
     */
    public ProductType createProductType() {
        return new ProductType();
    }

    /**
     * Create an instance of {@link PromotionConflictsType }
     * 
     */
    public PromotionConflictsType createPromotionConflictsType() {
        return new PromotionConflictsType();
    }

    /**
     * Create an instance of {@link CustomSelectionsType }
     * 
     */
    public CustomSelectionsType createCustomSelectionsType() {
        return new CustomSelectionsType();
    }

    /**
     * Create an instance of {@link ProductBundleType.BundledProducts }
     * 
     */
    public ProductBundleType.BundledProducts createProductBundleTypeBundledProducts() {
        return new ProductBundleType.BundledProducts();
    }

    /**
     * Create an instance of {@link SalesContextType }
     * 
     */
    public SalesContextType createSalesContextType() {
        return new SalesContextType();
    }

    /**
     * Create an instance of {@link LineItemSelectionsType }
     * 
     */
    public LineItemSelectionsType createLineItemSelectionsType() {
        return new LineItemSelectionsType();
    }

    /**
     * Create an instance of {@link BusinessPartyDetailResultType }
     * 
     */
    public BusinessPartyDetailResultType createBusinessPartyDetailResultType() {
        return new BusinessPartyDetailResultType();
    }

    /**
     * Create an instance of {@link ItemDetailType }
     * 
     */
    public ItemDetailType createItemDetailType() {
        return new ItemDetailType();
    }

    /**
     * Create an instance of {@link OrderStatusHistoryType }
     * 
     */
    public OrderStatusHistoryType createOrderStatusHistoryType() {
        return new OrderStatusHistoryType();
    }

    /**
     * Create an instance of {@link DialogValueType }
     * 
     */
    public DialogValueType createDialogValueType() {
        return new DialogValueType();
    }

    /**
     * Create an instance of {@link ProductCatalogResults }
     * 
     */
    public ProductCatalogResults createProductCatalogResults() {
        return new ProductCatalogResults();
    }

    /**
     * Create an instance of {@link StatusType.ProcessingMessages }
     * 
     */
    public StatusType.ProcessingMessages createStatusTypeProcessingMessages() {
        return new StatusType.ProcessingMessages();
    }

    /**
     * Create an instance of {@link CcpEventNotificationType }
     * 
     */
    public CcpEventNotificationType createCcpEventNotificationType() {
        return new CcpEventNotificationType();
    }

    /**
     * Create an instance of {@link ProductInfoType }
     * 
     */
    public ProductInfoType createProductInfoType() {
        return new ProductInfoType();
    }

    /**
     * Create an instance of {@link PromotionElementType.PromotionType }
     * 
     */
    public PromotionElementType.PromotionType createPromotionElementTypePromotionType() {
        return new PromotionElementType.PromotionType();
    }

    /**
     * Create an instance of {@link OptionsType }
     * 
     */
    public OptionsType createOptionsType() {
        return new OptionsType();
    }

    /**
     * Create an instance of {@link ProductDetailType }
     * 
     */
    public ProductDetailType createProductDetailType() {
        return new ProductDetailType();
    }

    /**
     * Create an instance of {@link MetaDataType }
     * 
     */
    public MetaDataType createMetaDataType() {
        return new MetaDataType();
    }

    /**
     * Create an instance of {@link ProductCatalogType }
     * 
     */
    public ProductCatalogType createProductCatalogType() {
        return new ProductCatalogType();
    }

    /**
     * Create an instance of {@link ProductResponseType.RtimRequestResponseCriteria }
     * 
     */
    public ProductResponseType.RtimRequestResponseCriteria createProductResponseTypeRtimRequestResponseCriteria() {
        return new ProductResponseType.RtimRequestResponseCriteria();
    }

    /**
     * Create an instance of {@link BusinessPartyType }
     * 
     */
    public BusinessPartyType createBusinessPartyType() {
        return new BusinessPartyType();
    }

    /**
     * Create an instance of {@link EMailAddressType }
     * 
     */
    public EMailAddressType createEMailAddressType() {
        return new EMailAddressType();
    }

    /**
     * Create an instance of {@link UserGroupType }
     * 
     */
    public UserGroupType createUserGroupType() {
        return new UserGroupType();
    }

    /**
     * Create an instance of {@link SelectedFeaturesType.FeatureGroup }
     * 
     */
    public SelectedFeaturesType.FeatureGroup createSelectedFeaturesTypeFeatureGroup() {
        return new SelectedFeaturesType.FeatureGroup();
    }

    /**
     * Create an instance of {@link FeatureGroupType.SelectionType }
     * 
     */
    public FeatureGroupType.SelectionType createFeatureGroupTypeSelectionType() {
        return new FeatureGroupType.SelectionType();
    }

    /**
     * Create an instance of {@link PaymentEventType }
     * 
     */
    public PaymentEventType createPaymentEventType() {
        return new PaymentEventType();
    }

    /**
     * Create an instance of {@link OrderSourceBusinessPartyType }
     * 
     */
    public OrderSourceBusinessPartyType createOrderSourceBusinessPartyType() {
        return new OrderSourceBusinessPartyType();
    }

    /**
     * Create an instance of {@link ProductType.CapabilityList }
     * 
     */
    public ProductType.CapabilityList createProductTypeCapabilityList() {
        return new ProductType.CapabilityList();
    }

    /**
     * Create an instance of {@link ReferenceResultType }
     * 
     */
    public ReferenceResultType createReferenceResultType() {
        return new ReferenceResultType();
    }

    /**
     * Create an instance of {@link LineItemStatusHistoryType }
     * 
     */
    public LineItemStatusHistoryType createLineItemStatusHistoryType() {
        return new LineItemStatusHistoryType();
    }

    /**
     * Create an instance of {@link EnergyPriceInfoType.Rate }
     * 
     */
    public EnergyPriceInfoType.Rate createEnergyPriceInfoTypeRate() {
        return new EnergyPriceInfoType.Rate();
    }

    /**
     * Create an instance of {@link BundleInfoType.BundleDetails }
     * 
     */
    public BundleInfoType.BundleDetails createBundleInfoTypeBundleDetails() {
        return new BundleInfoType.BundleDetails();
    }

    /**
     * Create an instance of {@link DetailResultType }
     * 
     */
    public DetailResultType createDetailResultType() {
        return new DetailResultType();
    }

    /**
     * Create an instance of {@link ProductRequestType.ProductList }
     * 
     */
    public ProductRequestType.ProductList createProductRequestTypeProductList() {
        return new ProductRequestType.ProductList();
    }

    /**
     * Create an instance of {@link ProviderCriteriaEntryType }
     * 
     */
    public ProviderCriteriaEntryType createProviderCriteriaEntryType() {
        return new ProviderCriteriaEntryType();
    }

    /**
     * Create an instance of {@link ProviderResults }
     * 
     */
    public ProviderResults createProviderResults() {
        return new ProviderResults();
    }

    /**
     * Create an instance of {@link ProviderCriteriaListType }
     * 
     */
    public ProviderCriteriaListType createProviderCriteriaListType() {
        return new ProviderCriteriaListType();
    }

    /**
     * Create an instance of {@link EnergyPriceInfoType }
     * 
     */
    public EnergyPriceInfoType createEnergyPriceInfoType() {
        return new EnergyPriceInfoType();
    }

    /**
     * Create an instance of {@link FeatureType }
     * 
     */
    public FeatureType createFeatureType() {
        return new FeatureType();
    }

    /**
     * Create an instance of {@link CustomizationType }
     * 
     */
    public CustomizationType createCustomizationType() {
        return new CustomizationType();
    }

    /**
     * Create an instance of {@link ProcessRequestResponse }
     * 
     */
    public ProcessRequestResponse createProcessRequestResponse() {
        return new ProcessRequestResponse();
    }

    /**
     * Create an instance of {@link BundlePromoType }
     * 
     */
    public BundlePromoType createBundlePromoType() {
        return new BundlePromoType();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link CatalogEntryType }
     * 
     */
    public CatalogEntryType createCatalogEntryType() {
        return new CatalogEntryType();
    }

    /**
     * Create an instance of {@link DataConstraintType.IntegerConstraint }
     * 
     */
    public DataConstraintType.IntegerConstraint createDataConstraintTypeIntegerConstraint() {
        return new DataConstraintType.IntegerConstraint();
    }

    /**
     * Create an instance of {@link OrderSourceRequestElementType }
     * 
     */
    public OrderSourceRequestElementType createOrderSourceRequestElementType() {
        return new OrderSourceRequestElementType();
    }

    /**
     * Create an instance of {@link MetaDataListType }
     * 
     */
    public MetaDataListType createMetaDataListType() {
        return new MetaDataListType();
    }

    /**
     * Create an instance of {@link DataConstraintType }
     * 
     */
    public DataConstraintType createDataConstraintType() {
        return new DataConstraintType();
    }

    /**
     * Create an instance of {@link ProductPromotionType }
     * 
     */
    public ProductPromotionType createProductPromotionType() {
        return new ProductPromotionType();
    }

    /**
     * Create an instance of {@link PriceInfoType }
     * 
     */
    public PriceInfoType createPriceInfoType() {
        return new PriceInfoType();
    }

    /**
     * Create an instance of {@link ExternalItemType }
     * 
     */
    public ExternalItemType createExternalItemType() {
        return new ExternalItemType();
    }

    /**
     * Create an instance of {@link NotificationEventCollectionType }
     * 
     */
    public NotificationEventCollectionType createNotificationEventCollectionType() {
        return new NotificationEventCollectionType();
    }

    /**
     * Create an instance of {@link CapabilityType }
     * 
     */
    public CapabilityType createCapabilityType() {
        return new CapabilityType();
    }

    /**
     * Create an instance of {@link OrderSourceResultType }
     * 
     */
    public OrderSourceResultType createOrderSourceResultType() {
        return new OrderSourceResultType();
    }

    /**
     * Create an instance of {@link MetaData }
     * 
     */
    public MetaData createMetaData() {
        return new MetaData();
    }

    /**
     * Create an instance of {@link ProductRequestType.ProductList.ProductId }
     * 
     */
    public ProductRequestType.ProductList.ProductId createProductRequestTypeProductListProductId() {
        return new ProductRequestType.ProductList.ProductId();
    }

    /**
     * Create an instance of {@link AcMessageType.Payload }
     * 
     */
    public AcMessageType.Payload createAcMessageTypePayload() {
        return new AcMessageType.Payload();
    }

    /**
     * Create an instance of {@link DetailElementType }
     * 
     */
    public DetailElementType createDetailElementType() {
        return new DetailElementType();
    }

    /**
     * Create an instance of {@link PreconditionsType }
     * 
     */
    public PreconditionsType createPreconditionsType() {
        return new PreconditionsType();
    }

    /**
     * Create an instance of {@link DateTimeOrTimeRangeType }
     * 
     */
    public DateTimeOrTimeRangeType createDateTimeOrTimeRangeType() {
        return new DateTimeOrTimeRangeType();
    }

    /**
     * Create an instance of {@link ProductInfoType.ProductDetails }
     * 
     */
    public ProductInfoType.ProductDetails createProductInfoTypeProductDetails() {
        return new ProductInfoType.ProductDetails();
    }

    /**
     * Create an instance of {@link ProductBundleType }
     * 
     */
    public ProductBundleType createProductBundleType() {
        return new ProductBundleType();
    }

    /**
     * Create an instance of {@link CatalogEntryType.Status }
     * 
     */
    public CatalogEntryType.Status createCatalogEntryTypeStatus() {
        return new CatalogEntryType.Status();
    }

    /**
     * Create an instance of {@link DataConstraintType.IntegerConstraint.ListOfValues }
     * 
     */
    public DataConstraintType.IntegerConstraint.ListOfValues createDataConstraintTypeIntegerConstraintListOfValues() {
        return new DataConstraintType.IntegerConstraint.ListOfValues();
    }

    /**
     * Create an instance of {@link EmptyElementType }
     * 
     */
    public EmptyElementType createEmptyElementType() {
        return new EmptyElementType();
    }

    /**
     * Create an instance of {@link RateType }
     * 
     */
    public RateType createRateType() {
        return new RateType();
    }

    /**
     * Create an instance of {@link FeatureCapabilityListType }
     * 
     */
    public FeatureCapabilityListType createFeatureCapabilityListType() {
        return new FeatureCapabilityListType();
    }

    /**
     * Create an instance of {@link AcMessageType }
     * 
     */
    public AcMessageType createAcMessageType() {
        return new AcMessageType();
    }

    /**
     * Create an instance of {@link ProductRequestType.ProductCatalogList }
     * 
     */
    public ProductRequestType.ProductCatalogList createProductRequestTypeProductCatalogList() {
        return new ProductRequestType.ProductCatalogList();
    }

    /**
     * Create an instance of {@link ProcessRequest }
     * 
     */
    public ProcessRequest createProcessRequest() {
        return new ProcessRequest();
    }

    /**
     * Create an instance of {@link IdListType }
     * 
     */
    public IdListType createIdListType() {
        return new IdListType();
    }

    /**
     * Create an instance of {@link ProgramType }
     * 
     */
    public ProgramType createProgramType() {
        return new ProgramType();
    }

    /**
     * Create an instance of {@link PriceTierListType }
     * 
     */
    public PriceTierListType createPriceTierListType() {
        return new PriceTierListType();
    }

    /**
     * Create an instance of {@link UserGroupListType }
     * 
     */
    public UserGroupListType createUserGroupListType() {
        return new UserGroupListType();
    }

    /**
     * Create an instance of {@link MarketItemWithCapabilitiesType.CapabilityList }
     * 
     */
    public MarketItemWithCapabilitiesType.CapabilityList createMarketItemWithCapabilitiesTypeCapabilityList() {
        return new MarketItemWithCapabilitiesType.CapabilityList();
    }

    /**
     * Create an instance of {@link ProcessingMessage }
     * 
     */
    public ProcessingMessage createProcessingMessage() {
        return new ProcessingMessage();
    }

    /**
     * Create an instance of {@link DataConstraintType.BooleanConstraint }
     * 
     */
    public DataConstraintType.BooleanConstraint createDataConstraintTypeBooleanConstraint() {
        return new DataConstraintType.BooleanConstraint();
    }

    /**
     * Create an instance of {@link OrderSourceType }
     * 
     */
    public OrderSourceType createOrderSourceType() {
        return new OrderSourceType();
    }

    /**
     * Create an instance of {@link ProviderCriteriaListEntityType }
     * 
     */
    public ProviderCriteriaListEntityType createProviderCriteriaListEntityType() {
        return new ProviderCriteriaListEntityType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "entryType")
    public JAXBElement<String> createEntryType(String value) {
        return new JAXBElement<String>(_EntryType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "dob")
    public JAXBElement<XMLGregorianCalendar> createDob(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_Dob_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "serviceableAddress")
    public JAXBElement<AddressType> createServiceableAddress(AddressType value) {
        return new JAXBElement<AddressType>(_ServiceableAddress_QNAME, AddressType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "extension")
    public JAXBElement<String> createExtension(String value) {
        return new JAXBElement<String>(_Extension_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EMailAddressType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "eMailAddress")
    public JAXBElement<EMailAddressType> createEMailAddress(EMailAddressType value) {
        return new JAXBElement<EMailAddressType>(_EMailAddress_QNAME, EMailAddressType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractTransactionTypeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "transactionType")
    public JAXBElement<AbstractTransactionTypeType> createTransactionType(AbstractTransactionTypeType value) {
        return new JAXBElement<AbstractTransactionTypeType>(_TransactionType_QNAME, AbstractTransactionTypeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessPartyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "referrer")
    public JAXBElement<BusinessPartyType> createReferrer(BusinessPartyType value) {
        return new JAXBElement<BusinessPartyType>(_Referrer_QNAME, BusinessPartyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "encryptedPassword")
    public JAXBElement<String> createEncryptedPassword(String value) {
        return new JAXBElement<String>(_EncryptedPassword_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PriceTierListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "priceTierList")
    public JAXBElement<PriceTierListType> createPriceTierList(PriceTierListType value) {
        return new JAXBElement<PriceTierListType>(_PriceTierList_QNAME, PriceTierListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PriceInfoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "price")
    public JAXBElement<PriceInfoType> createPrice(PriceInfoType value) {
        return new JAXBElement<PriceInfoType>(_Price_QNAME, PriceInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MarketItemDetailType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "marketItemDetail")
    public JAXBElement<MarketItemDetailType> createMarketItemDetail(MarketItemDetailType value) {
        return new JAXBElement<MarketItemDetailType>(_MarketItemDetail_QNAME, MarketItemDetailType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderSourceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "orderSource")
    public JAXBElement<OrderSourceType> createOrderSource(OrderSourceType value) {
        return new JAXBElement<OrderSourceType>(_OrderSource_QNAME, OrderSourceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "middleName")
    public JAXBElement<String> createMiddleName(String value) {
        return new JAXBElement<String>(_MiddleName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "lastName")
    public JAXBElement<String> createLastName(String value) {
        return new JAXBElement<String>(_LastName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnterpriseRequestDocumentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "productEnterpriseRequest")
    public JAXBElement<EnterpriseRequestDocumentType> createProductEnterpriseRequest(EnterpriseRequestDocumentType value) {
        return new JAXBElement<EnterpriseRequestDocumentType>(_ProductEnterpriseRequest_QNAME, EnterpriseRequestDocumentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "firstName")
    public JAXBElement<String> createFirstName(String value) {
        return new JAXBElement<String>(_FirstName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectedDialogsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "dialog")
    public JAXBElement<SelectedDialogsType> createDialog(SelectedDialogsType value) {
        return new JAXBElement<SelectedDialogsType>(_Dialog_QNAME, SelectedDialogsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "gender")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createGender(String value) {
        return new JAXBElement<String>(_Gender_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomizationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "customization")
    public JAXBElement<CustomizationType> createCustomization(CustomizationType value) {
        return new JAXBElement<CustomizationType>(_Customization_QNAME, CustomizationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "agentUserName")
    public JAXBElement<String> createAgentUserName(String value) {
        return new JAXBElement<String>(_AgentUserName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PhoneNumberType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "phoneNumber")
    public JAXBElement<PhoneNumberType> createPhoneNumber(PhoneNumberType value) {
        return new JAXBElement<PhoneNumberType>(_PhoneNumber_QNAME, PhoneNumberType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "acMessage")
    public JAXBElement<AcMessageType> createAcMessage(AcMessageType value) {
        return new JAXBElement<AcMessageType>(_AcMessage_QNAME, AcMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "request")
    public JAXBElement<AbstractRequestType> createRequest(AbstractRequestType value) {
        return new JAXBElement<AbstractRequestType>(_Request_QNAME, AbstractRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "response")
    public JAXBElement<AbstractResponseType> createResponse(AbstractResponseType value) {
        return new JAXBElement<AbstractResponseType>(_Response_QNAME, AbstractResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "id")
    public JAXBElement<String> createId(String value) {
        return new JAXBElement<String>(_Id_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "title")
    public JAXBElement<String> createTitle(String value) {
        return new JAXBElement<String>(_Title_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessRequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "processRequestResponse")
    public JAXBElement<ProcessRequestResponse> createProcessRequestResponse(ProcessRequestResponse value) {
        return new JAXBElement<ProcessRequestResponse>(_ProcessRequestResponse_QNAME, ProcessRequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "referredAddress")
    public JAXBElement<AddressType> createReferredAddress(AddressType value) {
        return new JAXBElement<AddressType>(_ReferredAddress_QNAME, AddressType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnterpriseResponseDocumentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "productEnterpriseResponse")
    public JAXBElement<EnterpriseResponseDocumentType> createProductEnterpriseResponse(EnterpriseResponseDocumentType value) {
        return new JAXBElement<EnterpriseResponseDocumentType>(_ProductEnterpriseResponse_QNAME, EnterpriseResponseDocumentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "ssn")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createSsn(String value) {
        return new JAXBElement<String>(_Ssn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProductRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "productRequest")
    public JAXBElement<ProductRequestType> createProductRequest(ProductRequestType value) {
        return new JAXBElement<ProductRequestType>(_ProductRequest_QNAME, ProductRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "nameSuffix")
    public JAXBElement<String> createNameSuffix(String value) {
        return new JAXBElement<String>(_NameSuffix_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProductResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "productResponse")
    public JAXBElement<ProductResponseType> createProductResponse(ProductResponseType value) {
        return new JAXBElement<ProductResponseType>(_ProductResponse_QNAME, ProductResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "processRequest")
    public JAXBElement<ProcessRequest> createProcessRequest(ProcessRequest value) {
        return new JAXBElement<ProcessRequest>(_ProcessRequest_QNAME, ProcessRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "gasStartAt", scope = AddressType.class)
    public JAXBElement<XMLGregorianCalendar> createAddressTypeGasStartAt(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_AddressTypeGasStartAt_QNAME, XMLGregorianCalendar.class, AddressType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "expiration", scope = AddressType.class)
    public JAXBElement<XMLGregorianCalendar> createAddressTypeExpiration(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_AddressTypeExpiration_QNAME, XMLGregorianCalendar.class, AddressType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "electricityStartAt", scope = AddressType.class)
    public JAXBElement<XMLGregorianCalendar> createAddressTypeElectricityStartAt(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_AddressTypeElectricityStartAt_QNAME, XMLGregorianCalendar.class, AddressType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "inEffect", scope = AddressType.class)
    public JAXBElement<XMLGregorianCalendar> createAddressTypeInEffect(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_AddressTypeInEffect_QNAME, XMLGregorianCalendar.class, AddressType.class, value);
    }

}
