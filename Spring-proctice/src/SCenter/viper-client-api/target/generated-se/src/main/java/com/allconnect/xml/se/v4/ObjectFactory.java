
package com.A.xml.se.v4;

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
 * generated in the com.A.xml.se.v4 package. 
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

    private final static QName _FirstName_QNAME = new QName("http://xml.A.com/v4", "firstName");
    private final static QName _MessageOrigin_QNAME = new QName("http://xml.A.com/v4", "messageOrigin");
    private final static QName _Gender_QNAME = new QName("http://xml.A.com/v4", "gender");
    private final static QName _AgentUserName_QNAME = new QName("http://xml.A.com/v4", "agentUserName");
    private final static QName _EntryType_QNAME = new QName("http://xml.A.com/v4", "entryType");
    private final static QName _Dob_QNAME = new QName("http://xml.A.com/v4", "dob");
    private final static QName _Request_QNAME = new QName("http://xml.A.com/v4", "request");
    private final static QName _ServiceableAddress_QNAME = new QName("http://xml.A.com/v4", "serviceableAddress");
    private final static QName _Extension_QNAME = new QName("http://xml.A.com/v4", "extension");
    private final static QName _Response_QNAME = new QName("http://xml.A.com/v4", "response");
    private final static QName _Id_QNAME = new QName("http://xml.A.com/v4", "id");
    private final static QName _EncryptedPassword_QNAME = new QName("http://xml.A.com/v4", "encryptedPassword");
    private final static QName _Title_QNAME = new QName("http://xml.A.com/v4", "title");
    private final static QName _ReferredAddress_QNAME = new QName("http://xml.A.com/v4", "referredAddress");
    private final static QName _InputAddressString_QNAME = new QName("http://xml.A.com/v4", "inputAddressString");
    private final static QName _Ssn_QNAME = new QName("http://xml.A.com/v4", "ssn");
    private final static QName _ServiceabilityTransactionType_QNAME = new QName("http://xml.A.com/v4", "serviceabilityTransactionType");
    private final static QName _MiddleName_QNAME = new QName("http://xml.A.com/v4", "middleName");
    private final static QName _LastName_QNAME = new QName("http://xml.A.com/v4", "lastName");
    private final static QName _NameSuffix_QNAME = new QName("http://xml.A.com/v4", "nameSuffix");
    private final static QName _CorrectedAddress_QNAME = new QName("http://xml.A.com/v4", "correctedAddress");
    private final static QName _CorrelationId_QNAME = new QName("http://xml.A.com/v4", "correlationId");
    private final static QName _AddressTypeGasStartAt_QNAME = new QName("http://xml.A.com/v4", "gasStartAt");
    private final static QName _AddressTypeExpiration_QNAME = new QName("http://xml.A.com/v4", "expiration");
    private final static QName _AddressTypeElectricityStartAt_QNAME = new QName("http://xml.A.com/v4", "electricityStartAt");
    private final static QName _AddressTypeInEffect_QNAME = new QName("http://xml.A.com/v4", "inEffect");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.A.xml.se.v4
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RepsonseContext }
     * 
     */
    public RepsonseContext createRepsonseContext() {
        return new RepsonseContext();
    }

    /**
     * Create an instance of {@link Dialog.Dialogs }
     * 
     */
    public Dialog.Dialogs createDialogDialogs() {
        return new Dialog.Dialogs();
    }

    /**
     * Create an instance of {@link DataConstraintType }
     * 
     */
    public DataConstraintType createDataConstraintType() {
        return new DataConstraintType();
    }

    /**
     * Create an instance of {@link CatalogedProductList }
     * 
     */
    public CatalogedProductList createCatalogedProductList() {
        return new CatalogedProductList();
    }

    /**
     * Create an instance of {@link SelectionChoiceType }
     * 
     */
    public SelectionChoiceType createSelectionChoiceType() {
        return new SelectionChoiceType();
    }

    /**
     * Create an instance of {@link ProviderCriteriaListEntityType }
     * 
     */
    public ProviderCriteriaListEntityType createProviderCriteriaListEntityType() {
        return new ProviderCriteriaListEntityType();
    }

    /**
     * Create an instance of {@link ProductResponse.ProductBundles }
     * 
     */
    public ProductResponse.ProductBundles createProductResponseProductBundles() {
        return new ProductResponse.ProductBundles();
    }

    /**
     * Create an instance of {@link ChannelType }
     * 
     */
    public ChannelType createChannelType() {
        return new ChannelType();
    }

    /**
     * Create an instance of {@link ProductResponse }
     * 
     */
    public ProductResponse createProductResponse() {
        return new ProductResponse();
    }

    /**
     * Create an instance of {@link Price }
     * 
     */
    public Price createPrice() {
        return new Price();
    }

    /**
     * Create an instance of {@link BundlePromoType }
     * 
     */
    public BundlePromoType createBundlePromoType() {
        return new BundlePromoType();
    }

    /**
     * Create an instance of {@link PaymentStatusWithTypeType }
     * 
     */
    public PaymentStatusWithTypeType createPaymentStatusWithTypeType() {
        return new PaymentStatusWithTypeType();
    }

    /**
     * Create an instance of {@link LineItemStatusType }
     * 
     */
    public LineItemStatusType createLineItemStatusType() {
        return new LineItemStatusType();
    }

    /**
     * Create an instance of {@link BusinessPartyDetailType }
     * 
     */
    public BusinessPartyDetailType createBusinessPartyDetailType() {
        return new BusinessPartyDetailType();
    }

    /**
     * Create an instance of {@link ClientInfo }
     * 
     */
    public ClientInfo createClientInfo() {
        return new ClientInfo();
    }

    /**
     * Create an instance of {@link IdListType }
     * 
     */
    public IdListType createIdListType() {
        return new IdListType();
    }

    /**
     * Create an instance of {@link SelectedFeaturesType.Features }
     * 
     */
    public SelectedFeaturesType.Features createSelectedFeaturesTypeFeatures() {
        return new SelectedFeaturesType.Features();
    }

    /**
     * Create an instance of {@link ImpactAreaListType }
     * 
     */
    public ImpactAreaListType createImpactAreaListType() {
        return new ImpactAreaListType();
    }

    /**
     * Create an instance of {@link MarketItemWithCapabilitiesType }
     * 
     */
    public MarketItemWithCapabilitiesType createMarketItemWithCapabilitiesType() {
        return new MarketItemWithCapabilitiesType();
    }

    /**
     * Create an instance of {@link ExternalItemType }
     * 
     */
    public ExternalItemType createExternalItemType() {
        return new ExternalItemType();
    }

    /**
     * Create an instance of {@link EmptyElementType }
     * 
     */
    public EmptyElementType createEmptyElementType() {
        return new EmptyElementType();
    }

    /**
     * Create an instance of {@link ProductRequest.ProductList.ProductId }
     * 
     */
    public ProductRequest.ProductList.ProductId createProductRequestProductListProductId() {
        return new ProductRequest.ProductList.ProductId();
    }

    /**
     * Create an instance of {@link ProviderNameValuePairType2 }
     * 
     */
    public ProviderNameValuePairType2 createProviderNameValuePairType2() {
        return new ProviderNameValuePairType2();
    }

    /**
     * Create an instance of {@link OptionsType }
     * 
     */
    public OptionsType createOptionsType() {
        return new OptionsType();
    }

    /**
     * Create an instance of {@link CcpEventNotificationType }
     * 
     */
    public CcpEventNotificationType createCcpEventNotificationType() {
        return new CcpEventNotificationType();
    }

    /**
     * Create an instance of {@link ProductResponse.RtimRequestResponseCriteria }
     * 
     */
    public ProductResponse.RtimRequestResponseCriteria createProductResponseRtimRequestResponseCriteria() {
        return new ProductResponse.RtimRequestResponseCriteria();
    }

    /**
     * Create an instance of {@link ProductCatalogType }
     * 
     */
    public ProductCatalogType createProductCatalogType() {
        return new ProductCatalogType();
    }

    /**
     * Create an instance of {@link ProviderSourceType }
     * 
     */
    public ProviderSourceType createProviderSourceType() {
        return new ProviderSourceType();
    }

    /**
     * Create an instance of {@link ProviderCriteriaListType }
     * 
     */
    public ProviderCriteriaListType createProviderCriteriaListType() {
        return new ProviderCriteriaListType();
    }

    /**
     * Create an instance of {@link ServiceabilityResponse2 .ProviderList }
     * 
     */
    public ServiceabilityResponse2 .ProviderList createServiceabilityResponse2ProviderList() {
        return new ServiceabilityResponse2 .ProviderList();
    }

    /**
     * Create an instance of {@link ProductRequest.ProviderList }
     * 
     */
    public ProductRequest.ProviderList createProductRequestProviderList() {
        return new ProductRequest.ProviderList();
    }

    /**
     * Create an instance of {@link DateTimeOrTimeRangeType }
     * 
     */
    public DateTimeOrTimeRangeType createDateTimeOrTimeRangeType() {
        return new DateTimeOrTimeRangeType();
    }

    /**
     * Create an instance of {@link OrderStatusHistoryType }
     * 
     */
    public OrderStatusHistoryType createOrderStatusHistoryType() {
        return new OrderStatusHistoryType();
    }

    /**
     * Create an instance of {@link ServiceabilityEnterpriseResponse }
     * 
     */
    public ServiceabilityEnterpriseResponse createServiceabilityEnterpriseResponse() {
        return new ServiceabilityEnterpriseResponse();
    }

    /**
     * Create an instance of {@link ProductDetailType }
     * 
     */
    public ProductDetailType createProductDetailType() {
        return new ProductDetailType();
    }

    /**
     * Create an instance of {@link Referrer }
     * 
     */
    public Referrer createReferrer() {
        return new Referrer();
    }

    /**
     * Create an instance of {@link FeatureType }
     * 
     */
    public FeatureType createFeatureType() {
        return new FeatureType();
    }

    /**
     * Create an instance of {@link ProviderType.CapabilityList }
     * 
     */
    public ProviderType.CapabilityList createProviderTypeCapabilityList() {
        return new ProviderType.CapabilityList();
    }

    /**
     * Create an instance of {@link CandidateAddressList.CandidateAddress }
     * 
     */
    public CandidateAddressList.CandidateAddress createCandidateAddressListCandidateAddress() {
        return new CandidateAddressList.CandidateAddress();
    }

    /**
     * Create an instance of {@link ReferenceResultType.Referrers }
     * 
     */
    public ReferenceResultType.Referrers createReferenceResultTypeReferrers() {
        return new ReferenceResultType.Referrers();
    }

    /**
     * Create an instance of {@link ServiceabilityEnterpriseRequest }
     * 
     */
    public ServiceabilityEnterpriseRequest createServiceabilityEnterpriseRequest() {
        return new ServiceabilityEnterpriseRequest();
    }

    /**
     * Create an instance of {@link DetailElementType }
     * 
     */
    public DetailElementType createDetailElementType() {
        return new DetailElementType();
    }

    /**
     * Create an instance of {@link PromotionResultType }
     * 
     */
    public PromotionResultType createPromotionResultType() {
        return new PromotionResultType();
    }

    /**
     * Create an instance of {@link LineItemSelectionsType }
     * 
     */
    public LineItemSelectionsType createLineItemSelectionsType() {
        return new LineItemSelectionsType();
    }

    /**
     * Create an instance of {@link MarketItemDetail }
     * 
     */
    public MarketItemDetail createMarketItemDetail() {
        return new MarketItemDetail();
    }

    /**
     * Create an instance of {@link PriceField }
     * 
     */
    public PriceField createPriceField() {
        return new PriceField();
    }

    /**
     * Create an instance of {@link PriceTierType }
     * 
     */
    public PriceTierType createPriceTierType() {
        return new PriceTierType();
    }

    /**
     * Create an instance of {@link FeatureCapabilityListType }
     * 
     */
    public FeatureCapabilityListType createFeatureCapabilityListType() {
        return new FeatureCapabilityListType();
    }

    /**
     * Create an instance of {@link CandidateAddressList }
     * 
     */
    public CandidateAddressList createCandidateAddressList() {
        return new CandidateAddressList();
    }

    /**
     * Create an instance of {@link ItemDetailType }
     * 
     */
    public ItemDetailType createItemDetailType() {
        return new ItemDetailType();
    }

    /**
     * Create an instance of {@link DescriptiveInfoType }
     * 
     */
    public DescriptiveInfoType createDescriptiveInfoType() {
        return new DescriptiveInfoType();
    }

    /**
     * Create an instance of {@link ServiceabilityResponse2 .RtimRequestResponseCriteria }
     * 
     */
    public ServiceabilityResponse2 .RtimRequestResponseCriteria createServiceabilityResponse2RtimRequestResponseCriteria() {
        return new ServiceabilityResponse2 .RtimRequestResponseCriteria();
    }

    /**
     * Create an instance of {@link OrderSource }
     * 
     */
    public OrderSource createOrderSource() {
        return new OrderSource();
    }

    /**
     * Create an instance of {@link TimeSlots }
     * 
     */
    public TimeSlots createTimeSlots() {
        return new TimeSlots();
    }

    /**
     * Create an instance of {@link CatalogEntryType }
     * 
     */
    public CatalogEntryType createCatalogEntryType() {
        return new CatalogEntryType();
    }

    /**
     * Create an instance of {@link AcMessage.Payload }
     * 
     */
    public AcMessage.Payload createAcMessagePayload() {
        return new AcMessage.Payload();
    }

    /**
     * Create an instance of {@link ProviderType }
     * 
     */
    public ProviderType createProviderType() {
        return new ProviderType();
    }

    /**
     * Create an instance of {@link FeatureGroupType }
     * 
     */
    public FeatureGroupType createFeatureGroupType() {
        return new FeatureGroupType();
    }

    /**
     * Create an instance of {@link MarketItemPromoType }
     * 
     */
    public MarketItemPromoType createMarketItemPromoType() {
        return new MarketItemPromoType();
    }

    /**
     * Create an instance of {@link EnergyPriceInfoType.Rate }
     * 
     */
    public EnergyPriceInfoType.Rate createEnergyPriceInfoTypeRate() {
        return new EnergyPriceInfoType.Rate();
    }

    /**
     * Create an instance of {@link PromotionElementType }
     * 
     */
    public PromotionElementType createPromotionElementType() {
        return new PromotionElementType();
    }

    /**
     * Create an instance of {@link ProviderResults }
     * 
     */
    public ProviderResults createProviderResults() {
        return new ProviderResults();
    }

    /**
     * Create an instance of {@link NetworkPackageType }
     * 
     */
    public NetworkPackageType createNetworkPackageType() {
        return new NetworkPackageType();
    }

    /**
     * Create an instance of {@link ValidateAddressResult }
     * 
     */
    public ValidateAddressResult createValidateAddressResult() {
        return new ValidateAddressResult();
    }

    /**
     * Create an instance of {@link OpMessage }
     * 
     */
    public OpMessage createOpMessage() {
        return new OpMessage();
    }

    /**
     * Create an instance of {@link ProductRequest.ProductCatalogList }
     * 
     */
    public ProductRequest.ProductCatalogList createProductRequestProductCatalogList() {
        return new ProductRequest.ProductCatalogList();
    }

    /**
     * Create an instance of {@link OrderStatusWithTypeType }
     * 
     */
    public OrderStatusWithTypeType createOrderStatusWithTypeType() {
        return new OrderStatusWithTypeType();
    }

    /**
     * Create an instance of {@link OrderSourceRequestElementType }
     * 
     */
    public OrderSourceRequestElementType createOrderSourceRequestElementType() {
        return new OrderSourceRequestElementType();
    }

    /**
     * Create an instance of {@link ChoiceType }
     * 
     */
    public ChoiceType createChoiceType() {
        return new ChoiceType();
    }

    /**
     * Create an instance of {@link MetaData }
     * 
     */
    public MetaData createMetaData() {
        return new MetaData();
    }

    /**
     * Create an instance of {@link CapabilityType }
     * 
     */
    public CapabilityType createCapabilityType() {
        return new CapabilityType();
    }

    /**
     * Create an instance of {@link MarketItemInfoType2 }
     * 
     */
    public MarketItemInfoType2 createMarketItemInfoType2() {
        return new MarketItemInfoType2();
    }

    /**
     * Create an instance of {@link ServiceabilityRequest2 .RtimRequestResponseCriteria }
     * 
     */
    public ServiceabilityRequest2 .RtimRequestResponseCriteria createServiceabilityRequest2RtimRequestResponseCriteria() {
        return new ServiceabilityRequest2 .RtimRequestResponseCriteria();
    }

    /**
     * Create an instance of {@link BundleInfoType.BundleDetails }
     * 
     */
    public BundleInfoType.BundleDetails createBundleInfoTypeBundleDetails() {
        return new BundleInfoType.BundleDetails();
    }

    /**
     * Create an instance of {@link TelephonyListType }
     * 
     */
    public TelephonyListType createTelephonyListType() {
        return new TelephonyListType();
    }

    /**
     * Create an instance of {@link TermsAndConditionsType }
     * 
     */
    public TermsAndConditionsType createTermsAndConditionsType() {
        return new TermsAndConditionsType();
    }

    /**
     * Create an instance of {@link PriceTierList }
     * 
     */
    public PriceTierList createPriceTierList() {
        return new PriceTierList();
    }

    /**
     * Create an instance of {@link NetworkProductType }
     * 
     */
    public NetworkProductType createNetworkProductType() {
        return new NetworkProductType();
    }

    /**
     * Create an instance of {@link Customization }
     * 
     */
    public Customization createCustomization() {
        return new Customization();
    }

    /**
     * Create an instance of {@link PaymentEventType }
     * 
     */
    public PaymentEventType createPaymentEventType() {
        return new PaymentEventType();
    }

    /**
     * Create an instance of {@link ProviderCriteriaEntityType2 }
     * 
     */
    public ProviderCriteriaEntityType2 createProviderCriteriaEntityType2() {
        return new ProviderCriteriaEntityType2();
    }

    /**
     * Create an instance of {@link ProductCatalogResults }
     * 
     */
    public ProductCatalogResults createProductCatalogResults() {
        return new ProductCatalogResults();
    }

    /**
     * Create an instance of {@link LineItemSelectionType }
     * 
     */
    public LineItemSelectionType createLineItemSelectionType() {
        return new LineItemSelectionType();
    }

    /**
     * Create an instance of {@link ItemPromoType }
     * 
     */
    public ItemPromoType createItemPromoType() {
        return new ItemPromoType();
    }

    /**
     * Create an instance of {@link ProductPromotionType }
     * 
     */
    public ProductPromotionType createProductPromotionType() {
        return new ProductPromotionType();
    }

    /**
     * Create an instance of {@link ProgramType }
     * 
     */
    public ProgramType createProgramType() {
        return new ProgramType();
    }

    /**
     * Create an instance of {@link ProcessingMessage }
     * 
     */
    public ProcessingMessage createProcessingMessage() {
        return new ProcessingMessage();
    }

    /**
     * Create an instance of {@link ProductDetailType.Detail }
     * 
     */
    public ProductDetailType.Detail createProductDetailTypeDetail() {
        return new ProductDetailType.Detail();
    }

    /**
     * Create an instance of {@link ApplicationType }
     * 
     */
    public ApplicationType createApplicationType() {
        return new ApplicationType();
    }

    /**
     * Create an instance of {@link ProductBundleType.BundledProducts }
     * 
     */
    public ProductBundleType.BundledProducts createProductBundleTypeBundledProducts() {
        return new ProductBundleType.BundledProducts();
    }

    /**
     * Create an instance of {@link ArbitraryXML }
     * 
     */
    public ArbitraryXML createArbitraryXML() {
        return new ArbitraryXML();
    }

    /**
     * Create an instance of {@link RequestStatus }
     * 
     */
    public RequestStatus createRequestStatus() {
        return new RequestStatus();
    }

    /**
     * Create an instance of {@link DataConstraintType.BooleanConstraint }
     * 
     */
    public DataConstraintType.BooleanConstraint createDataConstraintTypeBooleanConstraint() {
        return new DataConstraintType.BooleanConstraint();
    }

    /**
     * Create an instance of {@link EnergyPriceInfoType }
     * 
     */
    public EnergyPriceInfoType createEnergyPriceInfoType() {
        return new EnergyPriceInfoType();
    }

    /**
     * Create an instance of {@link ProductType }
     * 
     */
    public ProductType createProductType() {
        return new ProductType();
    }

    /**
     * Create an instance of {@link DataConstraintType.StringConstraint.ListOfValues }
     * 
     */
    public DataConstraintType.StringConstraint.ListOfValues createDataConstraintTypeStringConstraintListOfValues() {
        return new DataConstraintType.StringConstraint.ListOfValues();
    }

    /**
     * Create an instance of {@link StatusType.ProcessingMessages }
     * 
     */
    public StatusType.ProcessingMessages createStatusTypeProcessingMessages() {
        return new StatusType.ProcessingMessages();
    }

    /**
     * Create an instance of {@link ProductRequest }
     * 
     */
    public ProductRequest createProductRequest() {
        return new ProductRequest();
    }

    /**
     * Create an instance of {@link PaymentsType }
     * 
     */
    public PaymentsType createPaymentsType() {
        return new PaymentsType();
    }

    /**
     * Create an instance of {@link Phone }
     * 
     */
    public Phone createPhone() {
        return new Phone();
    }

    /**
     * Create an instance of {@link BusinessPartyDetailResultType }
     * 
     */
    public BusinessPartyDetailResultType createBusinessPartyDetailResultType() {
        return new BusinessPartyDetailResultType();
    }

    /**
     * Create an instance of {@link ProductCategoryListType }
     * 
     */
    public ProductCategoryListType createProductCategoryListType() {
        return new ProductCategoryListType();
    }

    /**
     * Create an instance of {@link ProviderCriteriaEntryType }
     * 
     */
    public ProviderCriteriaEntryType createProviderCriteriaEntryType() {
        return new ProviderCriteriaEntryType();
    }

    /**
     * Create an instance of {@link BundleInfoType }
     * 
     */
    public BundleInfoType createBundleInfoType() {
        return new BundleInfoType();
    }

    /**
     * Create an instance of {@link ServiceabilityRequest2 }
     * 
     */
    public ServiceabilityRequest2 createServiceabilityRequest2() {
        return new ServiceabilityRequest2();
    }

    /**
     * Create an instance of {@link OrderSourceResultType }
     * 
     */
    public OrderSourceResultType createOrderSourceResultType() {
        return new OrderSourceResultType();
    }

    /**
     * Create an instance of {@link NameValuePairType }
     * 
     */
    public NameValuePairType createNameValuePairType() {
        return new NameValuePairType();
    }

    /**
     * Create an instance of {@link PreconditionsType }
     * 
     */
    public PreconditionsType createPreconditionsType() {
        return new PreconditionsType();
    }

    /**
     * Create an instance of {@link EnergyProduct }
     * 
     */
    public EnergyProduct createEnergyProduct() {
        return new EnergyProduct();
    }

    /**
     * Create an instance of {@link ProductRequest.ProductList }
     * 
     */
    public ProductRequest.ProductList createProductRequestProductList() {
        return new ProductRequest.ProductList();
    }

    /**
     * Create an instance of {@link MailingAddressType }
     * 
     */
    public MailingAddressType createMailingAddressType() {
        return new MailingAddressType();
    }

    /**
     * Create an instance of {@link CustomSelectionsType }
     * 
     */
    public CustomSelectionsType createCustomSelectionsType() {
        return new CustomSelectionsType();
    }

    /**
     * Create an instance of {@link CcpCommunicationEventsType }
     * 
     */
    public CcpCommunicationEventsType createCcpCommunicationEventsType() {
        return new CcpCommunicationEventsType();
    }

    /**
     * Create an instance of {@link PromotionConflictsType }
     * 
     */
    public PromotionConflictsType createPromotionConflictsType() {
        return new PromotionConflictsType();
    }

    /**
     * Create an instance of {@link EMailAddress }
     * 
     */
    public EMailAddress createEMailAddress() {
        return new EMailAddress();
    }

    /**
     * Create an instance of {@link CoreMarketItemType }
     * 
     */
    public CoreMarketItemType createCoreMarketItemType() {
        return new CoreMarketItemType();
    }

    /**
     * Create an instance of {@link NotificationEventCollectionType }
     * 
     */
    public NotificationEventCollectionType createNotificationEventCollectionType() {
        return new NotificationEventCollectionType();
    }

    /**
     * Create an instance of {@link CatalogEntryType.Status }
     * 
     */
    public CatalogEntryType.Status createCatalogEntryTypeStatus() {
        return new CatalogEntryType.Status();
    }

    /**
     * Create an instance of {@link MarketingHighlightType }
     * 
     */
    public MarketingHighlightType createMarketingHighlightType() {
        return new MarketingHighlightType();
    }

    /**
     * Create an instance of {@link RequiredFeatureType }
     * 
     */
    public RequiredFeatureType createRequiredFeatureType() {
        return new RequiredFeatureType();
    }

    /**
     * Create an instance of {@link SalesContextEntityType }
     * 
     */
    public SalesContextEntityType createSalesContextEntityType() {
        return new SalesContextEntityType();
    }

    /**
     * Create an instance of {@link DetailElementType.DetailType }
     * 
     */
    public DetailElementType.DetailType createDetailElementTypeDetailType() {
        return new DetailElementType.DetailType();
    }

    /**
     * Create an instance of {@link CustomerContextType }
     * 
     */
    public CustomerContextType createCustomerContextType() {
        return new CustomerContextType();
    }

    /**
     * Create an instance of {@link MarketItemType }
     * 
     */
    public MarketItemType createMarketItemType() {
        return new MarketItemType();
    }

    /**
     * Create an instance of {@link LineItemStatusHistoryType }
     * 
     */
    public LineItemStatusHistoryType createLineItemStatusHistoryType() {
        return new LineItemStatusHistoryType();
    }

    /**
     * Create an instance of {@link FeatureDependencyType }
     * 
     */
    public FeatureDependencyType createFeatureDependencyType() {
        return new FeatureDependencyType();
    }

    /**
     * Create an instance of {@link OpResponseStatus }
     * 
     */
    public OpResponseStatus createOpResponseStatus() {
        return new OpResponseStatus();
    }

    /**
     * Create an instance of {@link MetaDataType }
     * 
     */
    public MetaDataType createMetaDataType() {
        return new MetaDataType();
    }

    /**
     * Create an instance of {@link ProductType.CapabilityList }
     * 
     */
    public ProductType.CapabilityList createProductTypeCapabilityList() {
        return new ProductType.CapabilityList();
    }

    /**
     * Create an instance of {@link FeatureGroupType.SelectionType }
     * 
     */
    public FeatureGroupType.SelectionType createFeatureGroupTypeSelectionType() {
        return new FeatureGroupType.SelectionType();
    }

    /**
     * Create an instance of {@link PromotionElementType.PromotionType }
     * 
     */
    public PromotionElementType.PromotionType createPromotionElementTypePromotionType() {
        return new PromotionElementType.PromotionType();
    }

    /**
     * Create an instance of {@link FeatureCapabilityType }
     * 
     */
    public FeatureCapabilityType createFeatureCapabilityType() {
        return new FeatureCapabilityType();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link CustomerContextEntityType }
     * 
     */
    public CustomerContextEntityType createCustomerContextEntityType() {
        return new CustomerContextEntityType();
    }

    /**
     * Create an instance of {@link ItemType }
     * 
     */
    public ItemType createItemType() {
        return new ItemType();
    }

    /**
     * Create an instance of {@link RateType }
     * 
     */
    public RateType createRateType() {
        return new RateType();
    }

    /**
     * Create an instance of {@link CustomerPhoneEntry }
     * 
     */
    public CustomerPhoneEntry createCustomerPhoneEntry() {
        return new CustomerPhoneEntry();
    }

    /**
     * Create an instance of {@link DataConstraintType.StringConstraint }
     * 
     */
    public DataConstraintType.StringConstraint createDataConstraintTypeStringConstraint() {
        return new DataConstraintType.StringConstraint();
    }

    /**
     * Create an instance of {@link CcpCommunicationEventType }
     * 
     */
    public CcpCommunicationEventType createCcpCommunicationEventType() {
        return new CcpCommunicationEventType();
    }

    /**
     * Create an instance of {@link RtimErrors }
     * 
     */
    public RtimErrors createRtimErrors() {
        return new RtimErrors();
    }

    /**
     * Create an instance of {@link FeatureValueType }
     * 
     */
    public FeatureValueType createFeatureValueType() {
        return new FeatureValueType();
    }

    /**
     * Create an instance of {@link ProductBundleType }
     * 
     */
    public ProductBundleType createProductBundleType() {
        return new ProductBundleType();
    }

    /**
     * Create an instance of {@link UserGroupType }
     * 
     */
    public UserGroupType createUserGroupType() {
        return new UserGroupType();
    }

    /**
     * Create an instance of {@link DialogValueType.Value }
     * 
     */
    public DialogValueType.Value createDialogValueTypeValue() {
        return new DialogValueType.Value();
    }

    /**
     * Create an instance of {@link DataConstraintType.IntegerConstraint.ListOfValues }
     * 
     */
    public DataConstraintType.IntegerConstraint.ListOfValues createDataConstraintTypeIntegerConstraintListOfValues() {
        return new DataConstraintType.IntegerConstraint.ListOfValues();
    }

    /**
     * Create an instance of {@link FeatureDependencyListType }
     * 
     */
    public FeatureDependencyListType createFeatureDependencyListType() {
        return new FeatureDependencyListType();
    }

    /**
     * Create an instance of {@link MarketItemWithCapabilitiesType.CapabilityList }
     * 
     */
    public MarketItemWithCapabilitiesType.CapabilityList createMarketItemWithCapabilitiesTypeCapabilityList() {
        return new MarketItemWithCapabilitiesType.CapabilityList();
    }

    /**
     * Create an instance of {@link ReferenceResultType }
     * 
     */
    public ReferenceResultType createReferenceResultType() {
        return new ReferenceResultType();
    }

    /**
     * Create an instance of {@link RtimError }
     * 
     */
    public RtimError createRtimError() {
        return new RtimError();
    }

    /**
     * Create an instance of {@link AcMessage }
     * 
     */
    public AcMessage createAcMessage() {
        return new AcMessage();
    }

    /**
     * Create an instance of {@link UserGroupListType }
     * 
     */
    public UserGroupListType createUserGroupListType() {
        return new UserGroupListType();
    }

    /**
     * Create an instance of {@link SelectedFeaturesType.FeatureGroup }
     * 
     */
    public SelectedFeaturesType.FeatureGroup createSelectedFeaturesTypeFeatureGroup() {
        return new SelectedFeaturesType.FeatureGroup();
    }

    /**
     * Create an instance of {@link ProductInfoType.ProductDetails }
     * 
     */
    public ProductInfoType.ProductDetails createProductInfoTypeProductDetails() {
        return new ProductInfoType.ProductDetails();
    }

    /**
     * Create an instance of {@link DetailResultType }
     * 
     */
    public DetailResultType createDetailResultType() {
        return new DetailResultType();
    }

    /**
     * Create an instance of {@link ItemCategory }
     * 
     */
    public ItemCategory createItemCategory() {
        return new ItemCategory();
    }

    /**
     * Create an instance of {@link ProductInfoType }
     * 
     */
    public ProductInfoType createProductInfoType() {
        return new ProductInfoType();
    }

    /**
     * Create an instance of {@link PhoneNumber }
     * 
     */
    public PhoneNumber createPhoneNumber() {
        return new PhoneNumber();
    }

    /**
     * Create an instance of {@link Dialog }
     * 
     */
    public Dialog createDialog() {
        return new Dialog();
    }

    /**
     * Create an instance of {@link CustomerPhone2 }
     * 
     */
    public CustomerPhone2 createCustomerPhone2() {
        return new CustomerPhone2();
    }

    /**
     * Create an instance of {@link MetaDataListType }
     * 
     */
    public MetaDataListType createMetaDataListType() {
        return new MetaDataListType();
    }

    /**
     * Create an instance of {@link RequestContext }
     * 
     */
    public RequestContext createRequestContext() {
        return new RequestContext();
    }

    /**
     * Create an instance of {@link SelectedFeaturesType }
     * 
     */
    public SelectedFeaturesType createSelectedFeaturesType() {
        return new SelectedFeaturesType();
    }

    /**
     * Create an instance of {@link TelephonyType }
     * 
     */
    public TelephonyType createTelephonyType() {
        return new TelephonyType();
    }

    /**
     * Create an instance of {@link DataConstraintType.IntegerConstraint }
     * 
     */
    public DataConstraintType.IntegerConstraint createDataConstraintTypeIntegerConstraint() {
        return new DataConstraintType.IntegerConstraint();
    }

    /**
     * Create an instance of {@link ProviderCriteriaType2 }
     * 
     */
    public ProviderCriteriaType2 createProviderCriteriaType2() {
        return new ProviderCriteriaType2();
    }

    /**
     * Create an instance of {@link ServiceabilityResponse2 }
     * 
     */
    public ServiceabilityResponse2 createServiceabilityResponse2() {
        return new ServiceabilityResponse2();
    }

    /**
     * Create an instance of {@link PriceFields }
     * 
     */
    public PriceFields createPriceFields() {
        return new PriceFields();
    }

    /**
     * Create an instance of {@link StatusType }
     * 
     */
    public StatusType createStatusType() {
        return new StatusType();
    }

    /**
     * Create an instance of {@link Name }
     * 
     */
    public Name createName() {
        return new Name();
    }

    /**
     * Create an instance of {@link NotificationEventType }
     * 
     */
    public NotificationEventType createNotificationEventType() {
        return new NotificationEventType();
    }

    /**
     * Create an instance of {@link SalesContextType }
     * 
     */
    public SalesContextType createSalesContextType() {
        return new SalesContextType();
    }

    /**
     * Create an instance of {@link DialogValueType }
     * 
     */
    public DialogValueType createDialogValueType() {
        return new DialogValueType();
    }

    /**
     * Create an instance of {@link OrderSourceBusinessPartyType }
     * 
     */
    public OrderSourceBusinessPartyType createOrderSourceBusinessPartyType() {
        return new OrderSourceBusinessPartyType();
    }

    /**
     * Create an instance of {@link com.A.xml.se.v4.PromotionType }
     * 
     */
    public com.A.xml.se.v4.PromotionType createPromotionType() {
        return new com.A.xml.se.v4.PromotionType();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "messageOrigin")
    public JAXBElement<String> createMessageOrigin(String value) {
        return new JAXBElement<String>(_MessageOrigin_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "agentUserName")
    public JAXBElement<String> createAgentUserName(String value) {
        return new JAXBElement<String>(_AgentUserName_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "request")
    public JAXBElement<AbstractRequestType> createRequest(AbstractRequestType value) {
        return new JAXBElement<AbstractRequestType>(_Request_QNAME, AbstractRequestType.class, null, value);
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
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "encryptedPassword")
    public JAXBElement<String> createEncryptedPassword(String value) {
        return new JAXBElement<String>(_EncryptedPassword_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "referredAddress")
    public JAXBElement<AddressType> createReferredAddress(AddressType value) {
        return new JAXBElement<AddressType>(_ReferredAddress_QNAME, AddressType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "inputAddressString")
    public JAXBElement<String> createInputAddressString(String value) {
        return new JAXBElement<String>(_InputAddressString_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "serviceabilityTransactionType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createServiceabilityTransactionType(String value) {
        return new JAXBElement<String>(_ServiceabilityTransactionType_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "nameSuffix")
    public JAXBElement<String> createNameSuffix(String value) {
        return new JAXBElement<String>(_NameSuffix_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "correctedAddress")
    public JAXBElement<AddressType> createCorrectedAddress(AddressType value) {
        return new JAXBElement<AddressType>(_CorrectedAddress_QNAME, AddressType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "correlationId")
    public JAXBElement<String> createCorrelationId(String value) {
        return new JAXBElement<String>(_CorrelationId_QNAME, String.class, null, value);
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
