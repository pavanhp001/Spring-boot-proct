
package com.A.xml.v4;

import java.util.List;
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
 * generated in the com.A.xml.v4 package. 
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

    private final static QName _RequestContext_QNAME = new QName("http://xml.A.com/common", "requestContext");
    private final static QName _OrderQualificationRequest_QNAME = new QName("http://xml.A.com/v4/OrderProvisioning/", "orderQualificationRequest");
    private final static QName _EntryType_QNAME = new QName("http://xml.A.com/v4", "entryType");
    private final static QName _Dob_QNAME = new QName("http://xml.A.com/v4", "dob");
    private final static QName _Extension_QNAME = new QName("http://xml.A.com/v4", "extension");
    private final static QName _ServiceableAddress_QNAME = new QName("http://xml.A.com/v4", "serviceableAddress");
    private final static QName _TransactionType_QNAME = new QName("http://xml.A.com/v4", "transactionType");
    private final static QName _CreditQualificationRequest_QNAME = new QName("http://xml.A.com/v4/OrderProvisioning/", "creditQualificationRequest");
    private final static QName _EncryptedPassword_QNAME = new QName("http://xml.A.com/v4", "encryptedPassword");
    private final static QName _ValidateAddressResult_QNAME = new QName("http://xml.A.com/common", "validateAddressResult");
    private final static QName _RtimProvisioningRequest_QNAME = new QName("http://xml.A.com/v4/OrderProvisioning/", "rtimProvisioningRequest");
    private final static QName _OrderSource_QNAME = new QName("http://xml.A.com/v4", "orderSource");
    private final static QName _FirstName_QNAME = new QName("http://xml.A.com/v4", "firstName");
    private final static QName _Dialog_QNAME = new QName("http://xml.A.com/v4", "dialog");
    private final static QName _RtimResponse_QNAME = new QName("http://xml.A.com/v4/VendorRequestResponse/", "rtimResponse");
    private final static QName _Customization_QNAME = new QName("http://xml.A.com/v4", "customization");
    private final static QName _LineItemStatus_QNAME = new QName("http://xml.A.com/v4", "lineItemStatus");
    private final static QName _OpRequest_QNAME = new QName("http://xml.A.com/v4/OrderProvisioning/", "opRequest");
    private final static QName _Response_QNAME = new QName("http://xml.A.com/v4", "response");
    private final static QName _Id_QNAME = new QName("http://xml.A.com/v4", "id");
    private final static QName _Title_QNAME = new QName("http://xml.A.com/v4", "title");
    private final static QName _ReferredAddress_QNAME = new QName("http://xml.A.com/v4", "referredAddress");
    private final static QName _ProductRequest_QNAME = new QName("http://xml.A.com/v4", "productRequest");
    private final static QName _RequestStatus_QNAME = new QName("http://xml.A.com/common", "requestStatus");
    private final static QName _ClientInfo_QNAME = new QName("http://xml.A.com/common", "clientInfo");
    private final static QName _ProductResponse_QNAME = new QName("http://xml.A.com/v4", "productResponse");
    private final static QName _RtimRequest_QNAME = new QName("http://xml.A.com/v4/VendorRequestResponse/", "rtimRequest");
    private final static QName _RepsonseContext_QNAME = new QName("http://xml.A.com/common", "repsonseContext");
    private final static QName _EMailAddress_QNAME = new QName("http://xml.A.com/v4", "eMailAddress");
    private final static QName _Referrer_QNAME = new QName("http://xml.A.com/v4", "referrer");
    private final static QName _Price_QNAME = new QName("http://xml.A.com/v4", "price");
    private final static QName _PriceTierList_QNAME = new QName("http://xml.A.com/v4", "priceTierList");
    private final static QName _OrderStatus_QNAME = new QName("http://xml.A.com/v4", "orderStatus");
    private final static QName _ValidatePaymentRequest_QNAME = new QName("http://xml.A.com/v4/OrderProvisioning/", "validatePaymentRequest");
    private final static QName _MarketItemDetail_QNAME = new QName("http://xml.A.com/v4", "marketItemDetail");
    private final static QName _MiddleName_QNAME = new QName("http://xml.A.com/v4", "middleName");
    private final static QName _ApplicationType_QNAME = new QName("http://xml.A.com/common", "applicationType");
    private final static QName _WishScheduleCollection_QNAME = new QName("http://xml.A.com/v4", "wishScheduleCollection");
    private final static QName _LastName_QNAME = new QName("http://xml.A.com/v4", "lastName");
    private final static QName _JobCollection_QNAME = new QName("http://xml.A.com/v4", "jobCollection");
    private final static QName _Gender_QNAME = new QName("http://xml.A.com/v4", "gender");
    private final static QName _AgentUserName_QNAME = new QName("http://xml.A.com/v4", "agentUserName");
    private final static QName _PhoneNumber_QNAME = new QName("http://xml.A.com/v4", "phoneNumber");
    private final static QName _Request_QNAME = new QName("http://xml.A.com/v4", "request");
    private final static QName _OpResponse_QNAME = new QName("http://xml.A.com/v4/OrderProvisioning/", "opResponse");
    private final static QName _LineItemCollection_QNAME = new QName("http://xml.A.com/v4", "lineItemCollection");
    private final static QName _Ssn_QNAME = new QName("http://xml.A.com/v4", "ssn");
    private final static QName _RtimProvisioningResponse_QNAME = new QName("http://xml.A.com/v4/OrderProvisioning/", "rtimProvisioningResponse");
    private final static QName _NameSuffix_QNAME = new QName("http://xml.A.com/v4", "nameSuffix");
    private final static QName _Detail_QNAME = new QName("http://xml.A.com/v4", "detail");
    private final static QName _TransientResponseTypeSuggestedPhone_QNAME = new QName("http://xml.A.com/v4", "suggestedPhone");
    private final static QName _TransientResponseTypeConfirmedPhone_QNAME = new QName("http://xml.A.com/v4", "confirmedPhone");
    private final static QName _AddressTypeGasStartAt_QNAME = new QName("http://xml.A.com/v4", "gasStartAt");
    private final static QName _AddressTypeExpiration_QNAME = new QName("http://xml.A.com/v4", "expiration");
    private final static QName _AddressTypeElectricityStartAt_QNAME = new QName("http://xml.A.com/v4", "electricityStartAt");
    private final static QName _AddressTypeInEffect_QNAME = new QName("http://xml.A.com/v4", "inEffect");
    private final static QName _CPOSOrderQualRequestContainerTimeSlotRefId_QNAME = new QName("http://xml.A.com/v4", "timeSlotRefId");
    private final static QName _TransientRequestTypeRequestedPhone_QNAME = new QName("http://xml.A.com/v4", "requestedPhone");
    private final static QName _TransientRequestTypeNewsletterSelected_QNAME = new QName("http://xml.A.com/v4", "NewsletterSelected");
    private final static QName _CustomerTypeDtCreated_QNAME = new QName("http://xml.A.com/v4", "dtCreated");
    private final static QName _CustomerTypeBestTimeToCall2_QNAME = new QName("http://xml.A.com/v4", "bestTimeToCall2");
    private final static QName _CustomerTypeOffersPresented_QNAME = new QName("http://xml.A.com/v4", "offersPresented");
    private final static QName _CustomerTypeBestTimeToCall1_QNAME = new QName("http://xml.A.com/v4", "bestTimeToCall1");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.A.xml.v4
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Phone }
     * 
     */
    public Phone createPhone() {
        return new Phone();
    }

    /**
     * Create an instance of {@link ProviderSourceType }
     * 
     */
    public ProviderSourceType createProviderSourceType() {
        return new ProviderSourceType();
    }

    /**
     * Create an instance of {@link RtimError }
     * 
     */
    public RtimError createRtimError() {
        return new RtimError();
    }

    /**
     * Create an instance of {@link ItemType }
     * 
     */
    public ItemType createItemType() {
        return new ItemType();
    }

    /**
     * Create an instance of {@link RequiredFeatureType }
     * 
     */
    public RequiredFeatureType createRequiredFeatureType() {
        return new RequiredFeatureType();
    }

    /**
     * Create an instance of {@link SelectedFeaturesType.Features }
     * 
     */
    public SelectedFeaturesType.Features createSelectedFeaturesTypeFeatures() {
        return new SelectedFeaturesType.Features();
    }

    /**
     * Create an instance of {@link ScheduleTimeSlots }
     * 
     */
    public ScheduleTimeSlots createScheduleTimeSlots() {
        return new ScheduleTimeSlots();
    }

    /**
     * Create an instance of {@link OrderType }
     * 
     */
    public OrderType createOrderType() {
        return new OrderType();
    }

    /**
     * Create an instance of {@link CreditFees }
     * 
     */
    public CreditFees createCreditFees() {
        return new CreditFees();
    }

    /**
     * Create an instance of {@link RtimProvisioningResponse }
     * 
     */
    public RtimProvisioningResponse createRtimProvisioningResponse() {
        return new RtimProvisioningResponse();
    }

    /**
     * Create an instance of {@link CustomerAttributeEntityType }
     * 
     */
    public CustomerAttributeEntityType createCustomerAttributeEntityType() {
        return new CustomerAttributeEntityType();
    }

    /**
     * Create an instance of {@link AccountHolderSearch }
     * 
     */
    public AccountHolderSearch createAccountHolderSearch() {
        return new AccountHolderSearch();
    }

    /**
     * Create an instance of {@link OpProductLineItem }
     * 
     */
    public OpProductLineItem createOpProductLineItem() {
        return new OpProductLineItem();
    }

    /**
     * Create an instance of {@link NetworkProductType }
     * 
     */
    public NetworkProductType createNetworkProductType() {
        return new NetworkProductType();
    }

    /**
     * Create an instance of {@link TermsAndConditionsType }
     * 
     */
    public TermsAndConditionsType createTermsAndConditionsType() {
        return new TermsAndConditionsType();
    }

    /**
     * Create an instance of {@link MarketItemWithCapabilitiesType }
     * 
     */
    public MarketItemWithCapabilitiesType createMarketItemWithCapabilitiesType() {
        return new MarketItemWithCapabilitiesType();
    }

    /**
     * Create an instance of {@link ProviderType }
     * 
     */
    public ProviderType createProviderType() {
        return new ProviderType();
    }

    /**
     * Create an instance of {@link BillingInfoList }
     * 
     */
    public BillingInfoList createBillingInfoList() {
        return new BillingInfoList();
    }

    /**
     * Create an instance of {@link ProductInfoType.ProductDetails }
     * 
     */
    public ProductInfoType.ProductDetails createProductInfoTypeProductDetails() {
        return new ProductInfoType.ProductDetails();
    }

    /**
     * Create an instance of {@link CustAddress.AddressRoles }
     * 
     */
    public CustAddress.AddressRoles createCustAddressAddressRoles() {
        return new CustAddress.AddressRoles();
    }

    /**
     * Create an instance of {@link VerificationQuestion }
     * 
     */
    public VerificationQuestion createVerificationQuestion() {
        return new VerificationQuestion();
    }

    /**
     * Create an instance of {@link OrderProvisioningRequest }
     * 
     */
    public OrderProvisioningRequest createOrderProvisioningRequest() {
        return new OrderProvisioningRequest();
    }

    /**
     * Create an instance of {@link CPOSCreditQualRequestContainer }
     * 
     */
    public CPOSCreditQualRequestContainer createCPOSCreditQualRequestContainer() {
        return new CPOSCreditQualRequestContainer();
    }

    /**
     * Create an instance of {@link RequestStatus }
     * 
     */
    public RequestStatus createRequestStatus() {
        return new RequestStatus();
    }

    /**
     * Create an instance of {@link Order }
     * 
     */
    public Order createOrder() {
        return new Order();
    }

    /**
     * Create an instance of {@link CPOSCreditQualResponseContainer }
     * 
     */
    public CPOSCreditQualResponseContainer createCPOSCreditQualResponseContainer() {
        return new CPOSCreditQualResponseContainer();
    }

    /**
     * Create an instance of {@link OrderManagementRequestResponse.Request }
     * 
     */
    public OrderManagementRequestResponse.Request createOrderManagementRequestResponseRequest() {
        return new OrderManagementRequestResponse.Request();
    }

    /**
     * Create an instance of {@link OrderSourceRequestElementType }
     * 
     */
    public OrderSourceRequestElementType createOrderSourceRequestElementType() {
        return new OrderSourceRequestElementType();
    }

    /**
     * Create an instance of {@link AvailableConnections.ConnectionDetails }
     * 
     */
    public AvailableConnections.ConnectionDetails createAvailableConnectionsConnectionDetails() {
        return new AvailableConnections.ConnectionDetails();
    }

    /**
     * Create an instance of {@link MarketingHighlightType }
     * 
     */
    public MarketingHighlightType createMarketingHighlightType() {
        return new MarketingHighlightType();
    }

    /**
     * Create an instance of {@link StatusType }
     * 
     */
    public StatusType createStatusType() {
        return new StatusType();
    }

    /**
     * Create an instance of {@link OrderQualificationRequest }
     * 
     */
    public OrderQualificationRequest createOrderQualificationRequest() {
        return new OrderQualificationRequest();
    }

    /**
     * Create an instance of {@link OrderStatusHistoryType }
     * 
     */
    public OrderStatusHistoryType createOrderStatusHistoryType() {
        return new OrderStatusHistoryType();
    }

    /**
     * Create an instance of {@link DetailElementType.DetailType }
     * 
     */
    public DetailElementType.DetailType createDetailElementTypeDetailType() {
        return new DetailElementType.DetailType();
    }

    /**
     * Create an instance of {@link ProductCategoryListType }
     * 
     */
    public ProductCategoryListType createProductCategoryListType() {
        return new ProductCategoryListType();
    }

    /**
     * Create an instance of {@link CustomerInformation }
     * 
     */
    public CustomerInformation createCustomerInformation() {
        return new CustomerInformation();
    }

    /**
     * Create an instance of {@link CPOSOrderQualResponseContainer }
     * 
     */
    public CPOSOrderQualResponseContainer createCPOSOrderQualResponseContainer() {
        return new CPOSOrderQualResponseContainer();
    }

    /**
     * Create an instance of {@link ReasonType }
     * 
     */
    public ReasonType createReasonType() {
        return new ReasonType();
    }

    /**
     * Create an instance of {@link TransientRequestType }
     * 
     */
    public TransientRequestType createTransientRequestType() {
        return new TransientRequestType();
    }

    /**
     * Create an instance of {@link TelephonyType }
     * 
     */
    public TelephonyType createTelephonyType() {
        return new TelephonyType();
    }

    /**
     * Create an instance of {@link TvConstraints }
     * 
     */
    public TvConstraints createTvConstraints() {
        return new TvConstraints();
    }

    /**
     * Create an instance of {@link TransientResponseType }
     * 
     */
    public TransientResponseType createTransientResponseType() {
        return new TransientResponseType();
    }

    /**
     * Create an instance of {@link FeatureValueType }
     * 
     */
    public FeatureValueType createFeatureValueType() {
        return new FeatureValueType();
    }

    /**
     * Create an instance of {@link FeatureCapabilityListType }
     * 
     */
    public FeatureCapabilityListType createFeatureCapabilityListType() {
        return new FeatureCapabilityListType();
    }

    /**
     * Create an instance of {@link MailingAddressType }
     * 
     */
    public MailingAddressType createMailingAddressType() {
        return new MailingAddressType();
    }

    /**
     * Create an instance of {@link ProductRequestType.ProductList.ProductId }
     * 
     */
    public ProductRequestType.ProductList.ProductId createProductRequestTypeProductListProductId() {
        return new ProductRequestType.ProductList.ProductId();
    }

    /**
     * Create an instance of {@link ReceiversSolution }
     * 
     */
    public ReceiversSolution createReceiversSolution() {
        return new ReceiversSolution();
    }

    /**
     * Create an instance of {@link CustomerContextType }
     * 
     */
    public CustomerContextType createCustomerContextType() {
        return new CustomerContextType();
    }

    /**
     * Create an instance of {@link ReasonDesc }
     * 
     */
    public ReasonDesc createReasonDesc() {
        return new ReasonDesc();
    }

    /**
     * Create an instance of {@link CustomerAttributeType }
     * 
     */
    public CustomerAttributeType createCustomerAttributeType() {
        return new CustomerAttributeType();
    }

    /**
     * Create an instance of {@link EnterpriseResponseDocumentType }
     * 
     */
    public EnterpriseResponseDocumentType createEnterpriseResponseDocumentType() {
        return new EnterpriseResponseDocumentType();
    }

    /**
     * Create an instance of {@link CustomerInteractionType }
     * 
     */
    public CustomerInteractionType createCustomerInteractionType() {
        return new CustomerInteractionType();
    }

    /**
     * Create an instance of {@link CommentCollectionType }
     * 
     */
    public CommentCollectionType createCommentCollectionType() {
        return new CommentCollectionType();
    }

    /**
     * Create an instance of {@link TvSelect }
     * 
     */
    public TvSelect createTvSelect() {
        return new TvSelect();
    }

    /**
     * Create an instance of {@link PromotionResultType }
     * 
     */
    public PromotionResultType createPromotionResultType() {
        return new PromotionResultType();
    }

    /**
     * Create an instance of {@link ProductRule.Inclusion }
     * 
     */
    public ProductRule.Inclusion createProductRuleInclusion() {
        return new ProductRule.Inclusion();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link MetaDataListType }
     * 
     */
    public MetaDataListType createMetaDataListType() {
        return new MetaDataListType();
    }

    /**
     * Create an instance of {@link OrderSearch }
     * 
     */
    public OrderSearch createOrderSearch() {
        return new OrderSearch();
    }

    /**
     * Create an instance of {@link WishScheduleType }
     * 
     */
    public WishScheduleType createWishScheduleType() {
        return new WishScheduleType();
    }

    /**
     * Create an instance of {@link CreditDetail }
     * 
     */
    public CreditDetail createCreditDetail() {
        return new CreditDetail();
    }

    /**
     * Create an instance of {@link ContactType }
     * 
     */
    public ContactType createContactType() {
        return new ContactType();
    }

    /**
     * Create an instance of {@link LineItemDetailType }
     * 
     */
    public LineItemDetailType createLineItemDetailType() {
        return new LineItemDetailType();
    }

    /**
     * Create an instance of {@link OpMessage }
     * 
     */
    public OpMessage createOpMessage() {
        return new OpMessage();
    }

    /**
     * Create an instance of {@link ApplicableType }
     * 
     */
    public ApplicableType createApplicableType() {
        return new ApplicableType();
    }

    /**
     * Create an instance of {@link LineItemType }
     * 
     */
    public LineItemType createLineItemType() {
        return new LineItemType();
    }

    /**
     * Create an instance of {@link VerificationQuestion.AnswerOption }
     * 
     */
    public VerificationQuestion.AnswerOption createVerificationQuestionAnswerOption() {
        return new VerificationQuestion.AnswerOption();
    }

    /**
     * Create an instance of {@link ProductType }
     * 
     */
    public ProductType createProductType() {
        return new ProductType();
    }

    /**
     * Create an instance of {@link ProductEstimate }
     * 
     */
    public ProductEstimate createProductEstimate() {
        return new ProductEstimate();
    }

    /**
     * Create an instance of {@link Accounts }
     * 
     */
    public Accounts createAccounts() {
        return new Accounts();
    }

    /**
     * Create an instance of {@link ProviderAttributes }
     * 
     */
    public ProviderAttributes createProviderAttributes() {
        return new ProviderAttributes();
    }

    /**
     * Create an instance of {@link CsatSurveysType }
     * 
     */
    public CsatSurveysType createCsatSurveysType() {
        return new CsatSurveysType();
    }

    /**
     * Create an instance of {@link AccountType }
     * 
     */
    public AccountType createAccountType() {
        return new AccountType();
    }

    /**
     * Create an instance of {@link ProductBundleType.BundledProducts }
     * 
     */
    public ProductBundleType.BundledProducts createProductBundleTypeBundledProducts() {
        return new ProductBundleType.BundledProducts();
    }

    /**
     * Create an instance of {@link BillingAttributeType }
     * 
     */
    public BillingAttributeType createBillingAttributeType() {
        return new BillingAttributeType();
    }

    /**
     * Create an instance of {@link CustAddress }
     * 
     */
    public CustAddress createCustAddress() {
        return new CustAddress();
    }

    /**
     * Create an instance of {@link com.A.xml.v4.Inclusion }
     * 
     */
    public com.A.xml.v4.Inclusion createInclusion() {
        return new com.A.xml.v4.Inclusion();
    }

    /**
     * Create an instance of {@link PriceTierListType }
     * 
     */
    public PriceTierListType createPriceTierListType() {
        return new PriceTierListType();
    }

    /**
     * Create an instance of {@link ProductInfoType }
     * 
     */
    public ProductInfoType createProductInfoType() {
        return new ProductInfoType();
    }

    /**
     * Create an instance of {@link com.A.xml.v4.Exclusion }
     * 
     */
    public com.A.xml.v4.Exclusion createExclusion() {
        return new com.A.xml.v4.Exclusion();
    }

    /**
     * Create an instance of {@link SecurityCreditCardType }
     * 
     */
    public SecurityCreditCardType createSecurityCreditCardType() {
        return new SecurityCreditCardType();
    }

    /**
     * Create an instance of {@link Customer }
     * 
     */
    public Customer createCustomer() {
        return new Customer();
    }

    /**
     * Create an instance of {@link MON }
     * 
     */
    public MON createMON() {
        return new MON();
    }

    /**
     * Create an instance of {@link DetailResultType }
     * 
     */
    public DetailResultType createDetailResultType() {
        return new DetailResultType();
    }

    /**
     * Create an instance of {@link MetaDataType }
     * 
     */
    public MetaDataType createMetaDataType() {
        return new MetaDataType();
    }

    /**
     * Create an instance of {@link STIOrderQualRequestContainer }
     * 
     */
    public STIOrderQualRequestContainer createSTIOrderQualRequestContainer() {
        return new STIOrderQualRequestContainer();
    }

    /**
     * Create an instance of {@link CustomerType }
     * 
     */
    public CustomerType createCustomerType() {
        return new CustomerType();
    }

    /**
     * Create an instance of {@link ProductResponseType }
     * 
     */
    public ProductResponseType createProductResponseType() {
        return new ProductResponseType();
    }

    /**
     * Create an instance of {@link CustomerFinancialInfoType.Employed }
     * 
     */
    public CustomerFinancialInfoType.Employed createCustomerFinancialInfoTypeEmployed() {
        return new CustomerFinancialInfoType.Employed();
    }

    /**
     * Create an instance of {@link OrderQualificationResponse }
     * 
     */
    public OrderQualificationResponse createOrderQualificationResponse() {
        return new OrderQualificationResponse();
    }

    /**
     * Create an instance of {@link SecurityInfo.AuthorizedPersons }
     * 
     */
    public SecurityInfo.AuthorizedPersons createSecurityInfoAuthorizedPersons() {
        return new SecurityInfo.AuthorizedPersons();
    }

    /**
     * Create an instance of {@link ProductSchedule }
     * 
     */
    public ProductSchedule createProductSchedule() {
        return new ProductSchedule();
    }

    /**
     * Create an instance of {@link SelectedFeaturesType.FeatureGroup }
     * 
     */
    public SelectedFeaturesType.FeatureGroup createSelectedFeaturesTypeFeatureGroup() {
        return new SelectedFeaturesType.FeatureGroup();
    }

    /**
     * Create an instance of {@link DueDateResponseInfo }
     * 
     */
    public DueDateResponseInfo createDueDateResponseInfo() {
        return new DueDateResponseInfo();
    }

    /**
     * Create an instance of {@link ProviderCriteriaEntryType }
     * 
     */
    public ProviderCriteriaEntryType createProviderCriteriaEntryType() {
        return new ProviderCriteriaEntryType();
    }

    /**
     * Create an instance of {@link OrderManagementRequestResponse.Response }
     * 
     */
    public OrderManagementRequestResponse.Response createOrderManagementRequestResponseResponse() {
        return new OrderManagementRequestResponse.Response();
    }

    /**
     * Create an instance of {@link EquipmentCountInfo }
     * 
     */
    public EquipmentCountInfo createEquipmentCountInfo() {
        return new EquipmentCountInfo();
    }

    /**
     * Create an instance of {@link DayAndTimeType }
     * 
     */
    public DayAndTimeType createDayAndTimeType() {
        return new DayAndTimeType();
    }

    /**
     * Create an instance of {@link ItemPromoType }
     * 
     */
    public ItemPromoType createItemPromoType() {
        return new ItemPromoType();
    }

    /**
     * Create an instance of {@link RequestContext }
     * 
     */
    public RequestContext createRequestContext() {
        return new RequestContext();
    }

    /**
     * Create an instance of {@link OpCustomerInfo }
     * 
     */
    public OpCustomerInfo createOpCustomerInfo() {
        return new OpCustomerInfo();
    }

    /**
     * Create an instance of {@link ProviderResults }
     * 
     */
    public ProviderResults createProviderResults() {
        return new ProviderResults();
    }

    /**
     * Create an instance of {@link OrderStatusWithTypeType }
     * 
     */
    public OrderStatusWithTypeType createOrderStatusWithTypeType() {
        return new OrderStatusWithTypeType();
    }

    /**
     * Create an instance of {@link EmptyElementType }
     * 
     */
    public EmptyElementType createEmptyElementType() {
        return new EmptyElementType();
    }

    /**
     * Create an instance of {@link AuthenticateCustomerRequest }
     * 
     */
    public AuthenticateCustomerRequest createAuthenticateCustomerRequest() {
        return new AuthenticateCustomerRequest();
    }

    /**
     * Create an instance of {@link SchedulingInfoType.ActualSchedule }
     * 
     */
    public SchedulingInfoType.ActualSchedule createSchedulingInfoTypeActualSchedule() {
        return new SchedulingInfoType.ActualSchedule();
    }

    /**
     * Create an instance of {@link CustomerSearch }
     * 
     */
    public CustomerSearch createCustomerSearch() {
        return new CustomerSearch();
    }

    /**
     * Create an instance of {@link BusinessPartyType }
     * 
     */
    public BusinessPartyType createBusinessPartyType() {
        return new BusinessPartyType();
    }

    /**
     * Create an instance of {@link LineItemEventsType.LineItemEvent }
     * 
     */
    public LineItemEventsType.LineItemEvent createLineItemEventsTypeLineItemEvent() {
        return new LineItemEventsType.LineItemEvent();
    }

    /**
     * Create an instance of {@link TvConfiguration }
     * 
     */
    public TvConfiguration createTvConfiguration() {
        return new TvConfiguration();
    }

    /**
     * Create an instance of {@link CatalogEntryType.Status }
     * 
     */
    public CatalogEntryType.Status createCatalogEntryTypeStatus() {
        return new CatalogEntryType.Status();
    }

    /**
     * Create an instance of {@link RtimProvisioningRequest }
     * 
     */
    public RtimProvisioningRequest createRtimProvisioningRequest() {
        return new RtimProvisioningRequest();
    }

    /**
     * Create an instance of {@link EMailAddressType }
     * 
     */
    public EMailAddressType createEMailAddressType() {
        return new EMailAddressType();
    }

    /**
     * Create an instance of {@link BusinessPartyDetailType }
     * 
     */
    public BusinessPartyDetailType createBusinessPartyDetailType() {
        return new BusinessPartyDetailType();
    }

    /**
     * Create an instance of {@link EnergyProduct }
     * 
     */
    public EnergyProduct createEnergyProduct() {
        return new EnergyProduct();
    }

    /**
     * Create an instance of {@link PriceInfo.PriceCategory }
     * 
     */
    public PriceInfo.PriceCategory createPriceInfoPriceCategory() {
        return new PriceInfo.PriceCategory();
    }

    /**
     * Create an instance of {@link ArbitraryXML }
     * 
     */
    public ArbitraryXML createArbitraryXML() {
        return new ArbitraryXML();
    }

    /**
     * Create an instance of {@link PreconditionsType }
     * 
     */
    public PreconditionsType createPreconditionsType() {
        return new PreconditionsType();
    }

    /**
     * Create an instance of {@link RequestTnResponseInfo }
     * 
     */
    public RequestTnResponseInfo createRequestTnResponseInfo() {
        return new RequestTnResponseInfo();
    }

    /**
     * Create an instance of {@link LineItemAttributeType }
     * 
     */
    public LineItemAttributeType createLineItemAttributeType() {
        return new LineItemAttributeType();
    }

    /**
     * Create an instance of {@link DialogValueType.Value }
     * 
     */
    public DialogValueType.Value createDialogValueTypeValue() {
        return new DialogValueType.Value();
    }

    /**
     * Create an instance of {@link AvailableAppointmentType }
     * 
     */
    public AvailableAppointmentType createAvailableAppointmentType() {
        return new AvailableAppointmentType();
    }

    /**
     * Create an instance of {@link AvailableConnections }
     * 
     */
    public AvailableConnections createAvailableConnections() {
        return new AvailableConnections();
    }

    /**
     * Create an instance of {@link ProviderCriteriaListType }
     * 
     */
    public ProviderCriteriaListType createProviderCriteriaListType() {
        return new ProviderCriteriaListType();
    }

    /**
     * Create an instance of {@link CreditDetails }
     * 
     */
    public CreditDetails createCreditDetails() {
        return new CreditDetails();
    }

    /**
     * Create an instance of {@link JobCollectionType }
     * 
     */
    public JobCollectionType createJobCollectionType() {
        return new JobCollectionType();
    }

    /**
     * Create an instance of {@link SelectedFeaturesType }
     * 
     */
    public SelectedFeaturesType createSelectedFeaturesType() {
        return new SelectedFeaturesType();
    }

    /**
     * Create an instance of {@link CPOSOrderQualRequestContainer }
     * 
     */
    public CPOSOrderQualRequestContainer createCPOSOrderQualRequestContainer() {
        return new CPOSOrderQualRequestContainer();
    }

    /**
     * Create an instance of {@link ProductRequestType.ProviderList }
     * 
     */
    public ProductRequestType.ProviderList createProductRequestTypeProviderList() {
        return new ProductRequestType.ProviderList();
    }

    /**
     * Create an instance of {@link OpResponse }
     * 
     */
    public OpResponse createOpResponse() {
        return new OpResponse();
    }

    /**
     * Create an instance of {@link ScheduleDateType }
     * 
     */
    public ScheduleDateType createScheduleDateType() {
        return new ScheduleDateType();
    }

    /**
     * Create an instance of {@link MetaData }
     * 
     */
    public MetaData createMetaData() {
        return new MetaData();
    }

    /**
     * Create an instance of {@link Package }
     * 
     */
    public Package createPackage() {
        return new Package();
    }

    /**
     * Create an instance of {@link OrderLineItemDetailTypeType }
     * 
     */
    public OrderLineItemDetailTypeType createOrderLineItemDetailTypeType() {
        return new OrderLineItemDetailTypeType();
    }

    /**
     * Create an instance of {@link SelectedDialogsType }
     * 
     */
    public SelectedDialogsType createSelectedDialogsType() {
        return new SelectedDialogsType();
    }

    /**
     * Create an instance of {@link IdListType }
     * 
     */
    public IdListType createIdListType() {
        return new IdListType();
    }

    /**
     * Create an instance of {@link ProductRule }
     * 
     */
    public ProductRule createProductRule() {
        return new ProductRule();
    }

    /**
     * Create an instance of {@link ApplicablePromotion }
     * 
     */
    public ApplicablePromotion createApplicablePromotion() {
        return new ApplicablePromotion();
    }

    /**
     * Create an instance of {@link BundleInfoType }
     * 
     */
    public BundleInfoType createBundleInfoType() {
        return new BundleInfoType();
    }

    /**
     * Create an instance of {@link ValidateOrderResponseInfo }
     * 
     */
    public ValidateOrderResponseInfo createValidateOrderResponseInfo() {
        return new ValidateOrderResponseInfo();
    }

    /**
     * Create an instance of {@link LineItemEventsType }
     * 
     */
    public LineItemEventsType createLineItemEventsType() {
        return new LineItemEventsType();
    }

    /**
     * Create an instance of {@link AccountHolderData }
     * 
     */
    public AccountHolderData createAccountHolderData() {
        return new AccountHolderData();
    }

    /**
     * Create an instance of {@link AlternatePromotion }
     * 
     */
    public AlternatePromotion createAlternatePromotion() {
        return new AlternatePromotion();
    }

    /**
     * Create an instance of {@link SubmitOrderResponseInfo }
     * 
     */
    public SubmitOrderResponseInfo createSubmitOrderResponseInfo() {
        return new SubmitOrderResponseInfo();
    }

    /**
     * Create an instance of {@link ContractAcceptance }
     * 
     */
    public ContractAcceptance createContractAcceptance() {
        return new ContractAcceptance();
    }

    /**
     * Create an instance of {@link FeatureType }
     * 
     */
    public FeatureType createFeatureType() {
        return new FeatureType();
    }

    /**
     * Create an instance of {@link STIOrderQualResponseContainer }
     * 
     */
    public STIOrderQualResponseContainer createSTIOrderQualResponseContainer() {
        return new STIOrderQualResponseContainer();
    }

    /**
     * Create an instance of {@link UserGroupListType }
     * 
     */
    public UserGroupListType createUserGroupListType() {
        return new UserGroupListType();
    }

    /**
     * Create an instance of {@link Name }
     * 
     */
    public Name createName() {
        return new Name();
    }

    /**
     * Create an instance of {@link StateIdType }
     * 
     */
    public StateIdType createStateIdType() {
        return new StateIdType();
    }

    /**
     * Create an instance of {@link SchedulingInfoType }
     * 
     */
    public SchedulingInfoType createSchedulingInfoType() {
        return new SchedulingInfoType();
    }

    /**
     * Create an instance of {@link PaymentEventType }
     * 
     */
    public PaymentEventType createPaymentEventType() {
        return new PaymentEventType();
    }

    /**
     * Create an instance of {@link DescriptiveInfoType }
     * 
     */
    public DescriptiveInfoType createDescriptiveInfoType() {
        return new DescriptiveInfoType();
    }

    /**
     * Create an instance of {@link Rule }
     * 
     */
    public Rule createRule() {
        return new Rule();
    }

    /**
     * Create an instance of {@link OrderSourceType }
     * 
     */
    public OrderSourceType createOrderSourceType() {
        return new OrderSourceType();
    }

    /**
     * Create an instance of {@link SalesContextEntityType }
     * 
     */
    public SalesContextEntityType createSalesContextEntityType() {
        return new SalesContextEntityType();
    }

    /**
     * Create an instance of {@link MarketItemType }
     * 
     */
    public MarketItemType createMarketItemType() {
        return new MarketItemType();
    }

    /**
     * Create an instance of {@link UserGroupType }
     * 
     */
    public UserGroupType createUserGroupType() {
        return new UserGroupType();
    }

    /**
     * Create an instance of {@link LineItemStatusType }
     * 
     */
    public LineItemStatusType createLineItemStatusType() {
        return new LineItemStatusType();
    }

    /**
     * Create an instance of {@link CreditQualResponseInfo }
     * 
     */
    public CreditQualResponseInfo createCreditQualResponseInfo() {
        return new CreditQualResponseInfo();
    }

    /**
     * Create an instance of {@link ReasonList }
     * 
     */
    public ReasonList createReasonList() {
        return new ReasonList();
    }

    /**
     * Create an instance of {@link AddressListType }
     * 
     */
    public AddressListType createAddressListType() {
        return new AddressListType();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link ValidateAddressResult }
     * 
     */
    public ValidateAddressResult createValidateAddressResult() {
        return new ValidateAddressResult();
    }

    /**
     * Create an instance of {@link TelephonyListType }
     * 
     */
    public TelephonyListType createTelephonyListType() {
        return new TelephonyListType();
    }

    /**
     * Create an instance of {@link ProductRequestType }
     * 
     */
    public ProductRequestType createProductRequestType() {
        return new ProductRequestType();
    }

    /**
     * Create an instance of {@link Receivers }
     * 
     */
    public Receivers createReceivers() {
        return new Receivers();
    }

    /**
     * Create an instance of {@link CustomSelectionsType }
     * 
     */
    public CustomSelectionsType createCustomSelectionsType() {
        return new CustomSelectionsType();
    }

    /**
     * Create an instance of {@link ReasonCategory }
     * 
     */
    public ReasonCategory createReasonCategory() {
        return new ReasonCategory();
    }

    /**
     * Create an instance of {@link OpResponseStatus }
     * 
     */
    public OpResponseStatus createOpResponseStatus() {
        return new OpResponseStatus();
    }

    /**
     * Create an instance of {@link SolutionLineItem }
     * 
     */
    public SolutionLineItem createSolutionLineItem() {
        return new SolutionLineItem();
    }

    /**
     * Create an instance of {@link LandlordInfoType }
     * 
     */
    public LandlordInfoType createLandlordInfoType() {
        return new LandlordInfoType();
    }

    /**
     * Create an instance of {@link Attributes }
     * 
     */
    public Attributes createAttributes() {
        return new Attributes();
    }

    /**
     * Create an instance of {@link ProductPromotionType }
     * 
     */
    public ProductPromotionType createProductPromotionType() {
        return new ProductPromotionType();
    }

    /**
     * Create an instance of {@link CatalogEntryType }
     * 
     */
    public CatalogEntryType createCatalogEntryType() {
        return new CatalogEntryType();
    }

    /**
     * Create an instance of {@link BundlePromoType }
     * 
     */
    public BundlePromoType createBundlePromoType() {
        return new BundlePromoType();
    }

    /**
     * Create an instance of {@link CustomerAttributeDetailType }
     * 
     */
    public CustomerAttributeDetailType createCustomerAttributeDetailType() {
        return new CustomerAttributeDetailType();
    }

    /**
     * Create an instance of {@link AccountsType }
     * 
     */
    public AccountsType createAccountsType() {
        return new AccountsType();
    }

    /**
     * Create an instance of {@link LineItemCollectionType }
     * 
     */
    public LineItemCollectionType createLineItemCollectionType() {
        return new LineItemCollectionType();
    }

    /**
     * Create an instance of {@link ValidatePaymentResponse }
     * 
     */
    public ValidatePaymentResponse createValidatePaymentResponse() {
        return new ValidatePaymentResponse();
    }

    /**
     * Create an instance of {@link CCProcessingResponseInfo }
     * 
     */
    public CCProcessingResponseInfo createCCProcessingResponseInfo() {
        return new CCProcessingResponseInfo();
    }

    /**
     * Create an instance of {@link ProductBundleInfo }
     * 
     */
    public ProductBundleInfo createProductBundleInfo() {
        return new ProductBundleInfo();
    }

    /**
     * Create an instance of {@link CreditQualificationRequest }
     * 
     */
    public CreditQualificationRequest createCreditQualificationRequest() {
        return new CreditQualificationRequest();
    }

    /**
     * Create an instance of {@link LineItemSelectionType }
     * 
     */
    public LineItemSelectionType createLineItemSelectionType() {
        return new LineItemSelectionType();
    }

    /**
     * Create an instance of {@link SelectionChoiceType }
     * 
     */
    public SelectionChoiceType createSelectionChoiceType() {
        return new SelectionChoiceType();
    }

    /**
     * Create an instance of {@link CustomerInteractionList }
     * 
     */
    public CustomerInteractionList createCustomerInteractionList() {
        return new CustomerInteractionList();
    }

    /**
     * Create an instance of {@link PhoneNumberType }
     * 
     */
    public PhoneNumberType createPhoneNumberType() {
        return new PhoneNumberType();
    }

    /**
     * Create an instance of {@link WishScheduleCollectionType }
     * 
     */
    public WishScheduleCollectionType createWishScheduleCollectionType() {
        return new WishScheduleCollectionType();
    }

    /**
     * Create an instance of {@link EstimateFirstBillResponseInfo }
     * 
     */
    public EstimateFirstBillResponseInfo createEstimateFirstBillResponseInfo() {
        return new EstimateFirstBillResponseInfo();
    }

    /**
     * Create an instance of {@link ProgramType }
     * 
     */
    public ProgramType createProgramType() {
        return new ProgramType();
    }

    /**
     * Create an instance of {@link NetworkPackageType }
     * 
     */
    public NetworkPackageType createNetworkPackageType() {
        return new NetworkPackageType();
    }

    /**
     * Create an instance of {@link NotificationEventCollectionType }
     * 
     */
    public NotificationEventCollectionType createNotificationEventCollectionType() {
        return new NotificationEventCollectionType();
    }

    /**
     * Create an instance of {@link WishScheduleType.Schedule }
     * 
     */
    public WishScheduleType.Schedule createWishScheduleTypeSchedule() {
        return new WishScheduleType.Schedule();
    }

    /**
     * Create an instance of {@link FeatureCapabilityType }
     * 
     */
    public FeatureCapabilityType createFeatureCapabilityType() {
        return new FeatureCapabilityType();
    }

    /**
     * Create an instance of {@link NameValuePairType }
     * 
     */
    public NameValuePairType createNameValuePairType() {
        return new NameValuePairType();
    }

    /**
     * Create an instance of {@link LineItemStatusHistoryType }
     * 
     */
    public LineItemStatusHistoryType createLineItemStatusHistoryType() {
        return new LineItemStatusHistoryType();
    }

    /**
     * Create an instance of {@link OpSchedulingInfo }
     * 
     */
    public OpSchedulingInfo createOpSchedulingInfo() {
        return new OpSchedulingInfo();
    }

    /**
     * Create an instance of {@link RateType }
     * 
     */
    public RateType createRateType() {
        return new RateType();
    }

    /**
     * Create an instance of {@link EnergyPriceInfoType }
     * 
     */
    public EnergyPriceInfoType createEnergyPriceInfoType() {
        return new EnergyPriceInfoType();
    }

    /**
     * Create an instance of {@link ProductRequestType.ProductList }
     * 
     */
    public ProductRequestType.ProductList createProductRequestTypeProductList() {
        return new ProductRequestType.ProductList();
    }

    /**
     * Create an instance of {@link com.A.xml.v4.ProductInfo }
     * 
     */
    public com.A.xml.v4.ProductInfo createProductInfo() {
        return new com.A.xml.v4.ProductInfo();
    }

    /**
     * Create an instance of {@link ProductBundleType }
     * 
     */
    public ProductBundleType createProductBundleType() {
        return new ProductBundleType();
    }

    /**
     * Create an instance of {@link PriceInfo.PriceCategory.ProductInfo }
     * 
     */
    public PriceInfo.PriceCategory.ProductInfo createPriceInfoPriceCategoryProductInfo() {
        return new PriceInfo.PriceCategory.ProductInfo();
    }

    /**
     * Create an instance of {@link AttributeEntityType }
     * 
     */
    public AttributeEntityType createAttributeEntityType() {
        return new AttributeEntityType();
    }

    /**
     * Create an instance of {@link ProductResponseType.ProductBundles }
     * 
     */
    public ProductResponseType.ProductBundles createProductResponseTypeProductBundles() {
        return new ProductResponseType.ProductBundles();
    }

    /**
     * Create an instance of {@link OptionsType }
     * 
     */
    public OptionsType createOptionsType() {
        return new OptionsType();
    }

    /**
     * Create an instance of {@link MarketItemPromoType }
     * 
     */
    public MarketItemPromoType createMarketItemPromoType() {
        return new MarketItemPromoType();
    }

    /**
     * Create an instance of {@link EquipmentTvItem }
     * 
     */
    public EquipmentTvItem createEquipmentTvItem() {
        return new EquipmentTvItem();
    }

    /**
     * Create an instance of {@link AuthenticateCustomerResponse }
     * 
     */
    public AuthenticateCustomerResponse createAuthenticateCustomerResponse() {
        return new AuthenticateCustomerResponse();
    }

    /**
     * Create an instance of {@link PaymentStatusWithTypeType }
     * 
     */
    public PaymentStatusWithTypeType createPaymentStatusWithTypeType() {
        return new PaymentStatusWithTypeType();
    }

    /**
     * Create an instance of {@link FeatureGroupType }
     * 
     */
    public FeatureGroupType createFeatureGroupType() {
        return new FeatureGroupType();
    }

    /**
     * Create an instance of {@link ProviderType.CapabilityList }
     * 
     */
    public ProviderType.CapabilityList createProviderTypeCapabilityList() {
        return new ProviderType.CapabilityList();
    }

    /**
     * Create an instance of {@link AccountHolderType }
     * 
     */
    public AccountHolderType createAccountHolderType() {
        return new AccountHolderType();
    }

    /**
     * Create an instance of {@link DialogValueType }
     * 
     */
    public DialogValueType createDialogValueType() {
        return new DialogValueType();
    }

    /**
     * Create an instance of {@link DataConstraintType.IntegerConstraint.ListOfValues }
     * 
     */
    public DataConstraintType.IntegerConstraint.ListOfValues createDataConstraintTypeIntegerConstraintListOfValues() {
        return new DataConstraintType.IntegerConstraint.ListOfValues();
    }

    /**
     * Create an instance of {@link DishCreditQualRequestContainer }
     * 
     */
    public DishCreditQualRequestContainer createDishCreditQualRequestContainer() {
        return new DishCreditQualRequestContainer();
    }

    /**
     * Create an instance of {@link TransientRequestContainerType }
     * 
     */
    public TransientRequestContainerType createTransientRequestContainerType() {
        return new TransientRequestContainerType();
    }

    /**
     * Create an instance of {@link FeatureDependencyListType }
     * 
     */
    public FeatureDependencyListType createFeatureDependencyListType() {
        return new FeatureDependencyListType();
    }

    /**
     * Create an instance of {@link CoachingList }
     * 
     */
    public CoachingList createCoachingList() {
        return new CoachingList();
    }

    /**
     * Create an instance of {@link OrderProvisioningResponse }
     * 
     */
    public OrderProvisioningResponse createOrderProvisioningResponse() {
        return new OrderProvisioningResponse();
    }

    /**
     * Create an instance of {@link RtimErrors }
     * 
     */
    public RtimErrors createRtimErrors() {
        return new RtimErrors();
    }

    /**
     * Create an instance of {@link PriceFields }
     * 
     */
    public PriceFields createPriceFields() {
        return new PriceFields();
    }

    /**
     * Create an instance of {@link VerificationAnswer }
     * 
     */
    public VerificationAnswer createVerificationAnswer() {
        return new VerificationAnswer();
    }

    /**
     * Create an instance of {@link com.A.xml.v4.PromotionType }
     * 
     */
    public com.A.xml.v4.PromotionType createPromotionType() {
        return new com.A.xml.v4.PromotionType();
    }

    /**
     * Create an instance of {@link TermsAndConditionsType2 }
     * 
     */
    public TermsAndConditionsType2 createTermsAndConditionsType2() {
        return new TermsAndConditionsType2();
    }

    /**
     * Create an instance of {@link DetailElementType }
     * 
     */
    public DetailElementType createDetailElementType() {
        return new DetailElementType();
    }

    /**
     * Create an instance of {@link ProductCatalogResults }
     * 
     */
    public ProductCatalogResults createProductCatalogResults() {
        return new ProductCatalogResults();
    }

    /**
     * Create an instance of {@link ValidatePaymentRequest }
     * 
     */
    public ValidatePaymentRequest createValidatePaymentRequest() {
        return new ValidatePaymentRequest();
    }

    /**
     * Create an instance of {@link CustomReceivers }
     * 
     */
    public CustomReceivers createCustomReceivers() {
        return new CustomReceivers();
    }

    /**
     * Create an instance of {@link DataConstraintType.IntegerConstraint }
     * 
     */
    public DataConstraintType.IntegerConstraint createDataConstraintTypeIntegerConstraint() {
        return new DataConstraintType.IntegerConstraint();
    }

    /**
     * Create an instance of {@link LocalProgramming }
     * 
     */
    public LocalProgramming createLocalProgramming() {
        return new LocalProgramming();
    }

    /**
     * Create an instance of {@link DataConstraintType.StringConstraint.ListOfValues }
     * 
     */
    public DataConstraintType.StringConstraint.ListOfValues createDataConstraintTypeStringConstraintListOfValues() {
        return new DataConstraintType.StringConstraint.ListOfValues();
    }

    /**
     * Create an instance of {@link PromoDescription }
     * 
     */
    public PromoDescription createPromoDescription() {
        return new PromoDescription();
    }

    /**
     * Create an instance of {@link AvailableAppointmentType.Appointment }
     * 
     */
    public AvailableAppointmentType.Appointment createAvailableAppointmentTypeAppointment() {
        return new AvailableAppointmentType.Appointment();
    }

    /**
     * Create an instance of {@link OrderData }
     * 
     */
    public OrderData createOrderData() {
        return new OrderData();
    }

    /**
     * Create an instance of {@link MasterOrderNumberResponseInfo }
     * 
     */
    public MasterOrderNumberResponseInfo createMasterOrderNumberResponseInfo() {
        return new MasterOrderNumberResponseInfo();
    }

    /**
     * Create an instance of {@link Reasons }
     * 
     */
    public Reasons createReasons() {
        return new Reasons();
    }

    /**
     * Create an instance of {@link CustomerContextEntityType }
     * 
     */
    public CustomerContextEntityType createCustomerContextEntityType() {
        return new CustomerContextEntityType();
    }

    /**
     * Create an instance of {@link MapFeatures }
     * 
     */
    public MapFeatures createMapFeatures() {
        return new MapFeatures();
    }

    /**
     * Create an instance of {@link ExternalItemType }
     * 
     */
    public ExternalItemType createExternalItemType() {
        return new ExternalItemType();
    }

    /**
     * Create an instance of {@link PriceField }
     * 
     */
    public PriceField createPriceField() {
        return new PriceField();
    }

    /**
     * Create an instance of {@link DishCreditQualResponseContainer }
     * 
     */
    public DishCreditQualResponseContainer createDishCreditQualResponseContainer() {
        return new DishCreditQualResponseContainer();
    }

    /**
     * Create an instance of {@link AuthenticateCustomerResponseInfo }
     * 
     */
    public AuthenticateCustomerResponseInfo createAuthenticateCustomerResponseInfo() {
        return new AuthenticateCustomerResponseInfo();
    }

    /**
     * Create an instance of {@link ProductRule.Exclusion }
     * 
     */
    public ProductRule.Exclusion createProductRuleExclusion() {
        return new ProductRule.Exclusion();
    }

    /**
     * Create an instance of {@link EquipmentSolutionInfo }
     * 
     */
    public EquipmentSolutionInfo createEquipmentSolutionInfo() {
        return new EquipmentSolutionInfo();
    }

    /**
     * Create an instance of {@link OpRequest }
     * 
     */
    public OpRequest createOpRequest() {
        return new OpRequest();
    }

    /**
     * Create an instance of {@link ItemCategory }
     * 
     */
    public ItemCategory createItemCategory() {
        return new ItemCategory();
    }

    /**
     * Create an instance of {@link DateTimeOrTimeRangeType }
     * 
     */
    public DateTimeOrTimeRangeType createDateTimeOrTimeRangeType() {
        return new DateTimeOrTimeRangeType();
    }

    /**
     * Create an instance of {@link PromotionConflictsType }
     * 
     */
    public PromotionConflictsType createPromotionConflictsType() {
        return new PromotionConflictsType();
    }

    /**
     * Create an instance of {@link MarketItemDetailType }
     * 
     */
    public MarketItemDetailType createMarketItemDetailType() {
        return new MarketItemDetailType();
    }

    /**
     * Create an instance of {@link ProductResponseType.RtimRequestResponseCriteria }
     * 
     */
    public ProductResponseType.RtimRequestResponseCriteria createProductResponseTypeRtimRequestResponseCriteria() {
        return new ProductResponseType.RtimRequestResponseCriteria();
    }

    /**
     * Create an instance of {@link CcpCommunicationEventsType }
     * 
     */
    public CcpCommunicationEventsType createCcpCommunicationEventsType() {
        return new CcpCommunicationEventsType();
    }

    /**
     * Create an instance of {@link AttributeDetailType }
     * 
     */
    public AttributeDetailType createAttributeDetailType() {
        return new AttributeDetailType();
    }

    /**
     * Create an instance of {@link PartialPaymentDueOption }
     * 
     */
    public PartialPaymentDueOption createPartialPaymentDueOption() {
        return new PartialPaymentDueOption();
    }

    /**
     * Create an instance of {@link LineItemSelectionsType }
     * 
     */
    public LineItemSelectionsType createLineItemSelectionsType() {
        return new LineItemSelectionsType();
    }

    /**
     * Create an instance of {@link CustBillingInfoType }
     * 
     */
    public CustBillingInfoType createCustBillingInfoType() {
        return new CustBillingInfoType();
    }

    /**
     * Create an instance of {@link PriceInfo }
     * 
     */
    public PriceInfo createPriceInfo() {
        return new PriceInfo();
    }

    /**
     * Create an instance of {@link EnergyPriceInfoType.Rate }
     * 
     */
    public EnergyPriceInfoType.Rate createEnergyPriceInfoTypeRate() {
        return new EnergyPriceInfoType.Rate();
    }

    /**
     * Create an instance of {@link ChannelType }
     * 
     */
    public ChannelType createChannelType() {
        return new ChannelType();
    }

    /**
     * Create an instance of {@link ApplicationType }
     * 
     */
    public ApplicationType createApplicationType() {
        return new ApplicationType();
    }

    /**
     * Create an instance of {@link ReferenceResultType.Referrers }
     * 
     */
    public ReferenceResultType.Referrers createReferenceResultTypeReferrers() {
        return new ReferenceResultType.Referrers();
    }

    /**
     * Create an instance of {@link OrderSourceBusinessPartyType }
     * 
     */
    public OrderSourceBusinessPartyType createOrderSourceBusinessPartyType() {
        return new OrderSourceBusinessPartyType();
    }

    /**
     * Create an instance of {@link CoreMarketItemType }
     * 
     */
    public CoreMarketItemType createCoreMarketItemType() {
        return new CoreMarketItemType();
    }

    /**
     * Create an instance of {@link ReferenceResultType }
     * 
     */
    public ReferenceResultType createReferenceResultType() {
        return new ReferenceResultType();
    }

    /**
     * Create an instance of {@link JobType }
     * 
     */
    public JobType createJobType() {
        return new JobType();
    }

    /**
     * Create an instance of {@link ProcessingMessage }
     * 
     */
    public ProcessingMessage createProcessingMessage() {
        return new ProcessingMessage();
    }

    /**
     * Create an instance of {@link CustomerData }
     * 
     */
    public CustomerData createCustomerData() {
        return new CustomerData();
    }

    /**
     * Create an instance of {@link CustomerOrderInfo }
     * 
     */
    public CustomerOrderInfo createCustomerOrderInfo() {
        return new CustomerOrderInfo();
    }

    /**
     * Create an instance of {@link ProductCatalogType }
     * 
     */
    public ProductCatalogType createProductCatalogType() {
        return new ProductCatalogType();
    }

    /**
     * Create an instance of {@link NotificationEventType }
     * 
     */
    public NotificationEventType createNotificationEventType() {
        return new NotificationEventType();
    }

    /**
     * Create an instance of {@link SecurityInfo }
     * 
     */
    public SecurityInfo createSecurityInfo() {
        return new SecurityInfo();
    }

    /**
     * Create an instance of {@link BundleInfoType.BundleDetails }
     * 
     */
    public BundleInfoType.BundleDetails createBundleInfoTypeBundleDetails() {
        return new BundleInfoType.BundleDetails();
    }

    /**
     * Create an instance of {@link SalesContextType }
     * 
     */
    public SalesContextType createSalesContextType() {
        return new SalesContextType();
    }

    /**
     * Create an instance of {@link ProductDetailType.Detail }
     * 
     */
    public ProductDetailType.Detail createProductDetailTypeDetail() {
        return new ProductDetailType.Detail();
    }

    /**
     * Create an instance of {@link PromotionElementType.PromotionType }
     * 
     */
    public PromotionElementType.PromotionType createPromotionElementTypePromotionType() {
        return new PromotionElementType.PromotionType();
    }

    /**
     * Create an instance of {@link CcpCommunicationEventType }
     * 
     */
    public CcpCommunicationEventType createCcpCommunicationEventType() {
        return new CcpCommunicationEventType();
    }

    /**
     * Create an instance of {@link ArrayOfMON }
     * 
     */
    public ArrayOfMON createArrayOfMON() {
        return new ArrayOfMON();
    }

    /**
     * Create an instance of {@link GetDueDatesResponseInfo }
     * 
     */
    public GetDueDatesResponseInfo createGetDueDatesResponseInfo() {
        return new GetDueDatesResponseInfo();
    }

    /**
     * Create an instance of {@link ImpactAreaListType }
     * 
     */
    public ImpactAreaListType createImpactAreaListType() {
        return new ImpactAreaListType();
    }

    /**
     * Create an instance of {@link BillingAddress }
     * 
     */
    public BillingAddress createBillingAddress() {
        return new BillingAddress();
    }

    /**
     * Create an instance of {@link LinkableType }
     * 
     */
    public LinkableType createLinkableType() {
        return new LinkableType();
    }

    /**
     * Create an instance of {@link OrderManagementRequestResponse.PagingDetail }
     * 
     */
    public OrderManagementRequestResponse.PagingDetail createOrderManagementRequestResponsePagingDetail() {
        return new OrderManagementRequestResponse.PagingDetail();
    }

    /**
     * Create an instance of {@link TimeSlots }
     * 
     */
    public TimeSlots createTimeSlots() {
        return new TimeSlots();
    }

    /**
     * Create an instance of {@link PotentialAccessory }
     * 
     */
    public PotentialAccessory createPotentialAccessory() {
        return new PotentialAccessory();
    }

    /**
     * Create an instance of {@link OrderSourceResultType }
     * 
     */
    public OrderSourceResultType createOrderSourceResultType() {
        return new OrderSourceResultType();
    }

    /**
     * Create an instance of {@link DataConstraintType.BooleanConstraint }
     * 
     */
    public DataConstraintType.BooleanConstraint createDataConstraintTypeBooleanConstraint() {
        return new DataConstraintType.BooleanConstraint();
    }

    /**
     * Create an instance of {@link MarketItemWithCapabilitiesType.CapabilityList }
     * 
     */
    public MarketItemWithCapabilitiesType.CapabilityList createMarketItemWithCapabilitiesTypeCapabilityList() {
        return new MarketItemWithCapabilitiesType.CapabilityList();
    }

    /**
     * Create an instance of {@link FeatureGroupType.SelectionType }
     * 
     */
    public FeatureGroupType.SelectionType createFeatureGroupTypeSelectionType() {
        return new FeatureGroupType.SelectionType();
    }

    /**
     * Create an instance of {@link RegionalProgramming }
     * 
     */
    public RegionalProgramming createRegionalProgramming() {
        return new RegionalProgramming();
    }

    /**
     * Create an instance of {@link CreditQualificationResponse }
     * 
     */
    public CreditQualificationResponse createCreditQualificationResponse() {
        return new CreditQualificationResponse();
    }

    /**
     * Create an instance of {@link ResponseContext }
     * 
     */
    public ResponseContext createResponseContext() {
        return new ResponseContext();
    }

    /**
     * Create an instance of {@link CreditOptions }
     * 
     */
    public CreditOptions createCreditOptions() {
        return new CreditOptions();
    }

    /**
     * Create an instance of {@link EnterpriseRequestDocumentType }
     * 
     */
    public EnterpriseRequestDocumentType createEnterpriseRequestDocumentType() {
        return new EnterpriseRequestDocumentType();
    }

    /**
     * Create an instance of {@link PaymentsType }
     * 
     */
    public PaymentsType createPaymentsType() {
        return new PaymentsType();
    }

    /**
     * Create an instance of {@link CustomerPhoneEntry }
     * 
     */
    public CustomerPhoneEntry createCustomerPhoneEntry() {
        return new CustomerPhoneEntry();
    }

    /**
     * Create an instance of {@link ProviderLineItemStatusType }
     * 
     */
    public ProviderLineItemStatusType createProviderLineItemStatusType() {
        return new ProviderLineItemStatusType();
    }

    /**
     * Create an instance of {@link OrderManagementRequestResponse }
     * 
     */
    public OrderManagementRequestResponse createOrderManagementRequestResponse() {
        return new OrderManagementRequestResponse();
    }

    /**
     * Create an instance of {@link ReceiverOptions }
     * 
     */
    public ReceiverOptions createReceiverOptions() {
        return new ReceiverOptions();
    }

    /**
     * Create an instance of {@link ReceiverOption }
     * 
     */
    public ReceiverOption createReceiverOption() {
        return new ReceiverOption();
    }

    /**
     * Create an instance of {@link ItemDetailType }
     * 
     */
    public ItemDetailType createItemDetailType() {
        return new ItemDetailType();
    }

    /**
     * Create an instance of {@link SetDueDateResponseInfo }
     * 
     */
    public SetDueDateResponseInfo createSetDueDateResponseInfo() {
        return new SetDueDateResponseInfo();
    }

    /**
     * Create an instance of {@link TransientResponseContainerType }
     * 
     */
    public TransientResponseContainerType createTransientResponseContainerType() {
        return new TransientResponseContainerType();
    }

    /**
     * Create an instance of {@link InstallTimeSlot }
     * 
     */
    public InstallTimeSlot createInstallTimeSlot() {
        return new InstallTimeSlot();
    }

    /**
     * Create an instance of {@link TvConstraints.TvLabels }
     * 
     */
    public TvConstraints.TvLabels createTvConstraintsTvLabels() {
        return new TvConstraints.TvLabels();
    }

    /**
     * Create an instance of {@link PromotionElementType }
     * 
     */
    public PromotionElementType createPromotionElementType() {
        return new PromotionElementType();
    }

    /**
     * Create an instance of {@link SecurityVerificationType }
     * 
     */
    public SecurityVerificationType createSecurityVerificationType() {
        return new SecurityVerificationType();
    }

    /**
     * Create an instance of {@link CoachingDesc }
     * 
     */
    public CoachingDesc createCoachingDesc() {
        return new CoachingDesc();
    }

    /**
     * Create an instance of {@link ProviderCriteriaListEntityType }
     * 
     */
    public ProviderCriteriaListEntityType createProviderCriteriaListEntityType() {
        return new ProviderCriteriaListEntityType();
    }

    /**
     * Create an instance of {@link PriceTierType }
     * 
     */
    public PriceTierType createPriceTierType() {
        return new PriceTierType();
    }

    /**
     * Create an instance of {@link ProductDetailType }
     * 
     */
    public ProductDetailType createProductDetailType() {
        return new ProductDetailType();
    }

    /**
     * Create an instance of {@link CcpEventNotificationType }
     * 
     */
    public CcpEventNotificationType createCcpEventNotificationType() {
        return new CcpEventNotificationType();
    }

    /**
     * Create an instance of {@link OrderQualResponseInfo }
     * 
     */
    public OrderQualResponseInfo createOrderQualResponseInfo() {
        return new OrderQualResponseInfo();
    }

    /**
     * Create an instance of {@link RequestTnInfo }
     * 
     */
    public RequestTnInfo createRequestTnInfo() {
        return new RequestTnInfo();
    }

    /**
     * Create an instance of {@link SelectedDialogsType.Dialogs }
     * 
     */
    public SelectedDialogsType.Dialogs createSelectedDialogsTypeDialogs() {
        return new SelectedDialogsType.Dialogs();
    }

    /**
     * Create an instance of {@link EquipmentBundleInfo }
     * 
     */
    public EquipmentBundleInfo createEquipmentBundleInfo() {
        return new EquipmentBundleInfo();
    }

    /**
     * Create an instance of {@link DataConstraintType }
     * 
     */
    public DataConstraintType createDataConstraintType() {
        return new DataConstraintType();
    }

    /**
     * Create an instance of {@link ProductRequestType.ProductCatalogList }
     * 
     */
    public ProductRequestType.ProductCatalogList createProductRequestTypeProductCatalogList() {
        return new ProductRequestType.ProductCatalogList();
    }

    /**
     * Create an instance of {@link CapabilityType }
     * 
     */
    public CapabilityType createCapabilityType() {
        return new CapabilityType();
    }

    /**
     * Create an instance of {@link FeatureDependencyType }
     * 
     */
    public FeatureDependencyType createFeatureDependencyType() {
        return new FeatureDependencyType();
    }

    /**
     * Create an instance of {@link CustomerFinancialInfoType }
     * 
     */
    public CustomerFinancialInfoType createCustomerFinancialInfoType() {
        return new CustomerFinancialInfoType();
    }

    /**
     * Create an instance of {@link CsatSurveyType }
     * 
     */
    public CsatSurveyType createCsatSurveyType() {
        return new CsatSurveyType();
    }

    /**
     * Create an instance of {@link StatusType.ProcessingMessages }
     * 
     */
    public StatusType.ProcessingMessages createStatusTypeProcessingMessages() {
        return new StatusType.ProcessingMessages();
    }

    /**
     * Create an instance of {@link DataConstraintType.StringConstraint }
     * 
     */
    public DataConstraintType.StringConstraint createDataConstraintTypeStringConstraint() {
        return new DataConstraintType.StringConstraint();
    }

    /**
     * Create an instance of {@link LineItemPriceInfoType }
     * 
     */
    public LineItemPriceInfoType createLineItemPriceInfoType() {
        return new LineItemPriceInfoType();
    }

    /**
     * Create an instance of {@link Disposition }
     * 
     */
    public Disposition createDisposition() {
        return new Disposition();
    }

    /**
     * Create an instance of {@link ChoiceType }
     * 
     */
    public ChoiceType createChoiceType() {
        return new ChoiceType();
    }

    /**
     * Create an instance of {@link Coachings }
     * 
     */
    public Coachings createCoachings() {
        return new Coachings();
    }

    /**
     * Create an instance of {@link CatalogedProductList }
     * 
     */
    public CatalogedProductList createCatalogedProductList() {
        return new CatalogedProductList();
    }

    /**
     * Create an instance of {@link Receiver }
     * 
     */
    public Receiver createReceiver() {
        return new Receiver();
    }

    /**
     * Create an instance of {@link BusinessPartyDetailResultType }
     * 
     */
    public BusinessPartyDetailResultType createBusinessPartyDetailResultType() {
        return new BusinessPartyDetailResultType();
    }

    /**
     * Create an instance of {@link CustomizationType }
     * 
     */
    public CustomizationType createCustomizationType() {
        return new CustomizationType();
    }

    /**
     * Create an instance of {@link ClientInfo }
     * 
     */
    public ClientInfo createClientInfo() {
        return new ClientInfo();
    }

    /**
     * Create an instance of {@link PriceInfoType }
     * 
     */
    public PriceInfoType createPriceInfoType() {
        return new PriceInfoType();
    }

    /**
     * Create an instance of {@link ProductType.CapabilityList }
     * 
     */
    public ProductType.CapabilityList createProductTypeCapabilityList() {
        return new ProductType.CapabilityList();
    }

    /**
     * Create an instance of {@link DriverLicenseType }
     * 
     */
    public DriverLicenseType createDriverLicenseType() {
        return new DriverLicenseType();
    }

    /**
     * Create an instance of {@link OrderState }
     * 
     */
    public OrderState createOrderState() {
        return new OrderState();
    }

    /**
     * Create an instance of {@link CoachingType }
     * 
     */
    public CoachingType createCoachingType() {
        return new CoachingType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestContext }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/common", name = "requestContext")
    public JAXBElement<RequestContext> createRequestContext(RequestContext value) {
        return new JAXBElement<RequestContext>(_RequestContext_QNAME, RequestContext.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderQualificationRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4/OrderProvisioning/", name = "orderQualificationRequest")
    public JAXBElement<OrderQualificationRequest> createOrderQualificationRequest(OrderQualificationRequest value) {
        return new JAXBElement<OrderQualificationRequest>(_OrderQualificationRequest_QNAME, OrderQualificationRequest.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "extension")
    public JAXBElement<String> createExtension(String value) {
        return new JAXBElement<String>(_Extension_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractTransactionTypeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "transactionType")
    public JAXBElement<AbstractTransactionTypeType> createTransactionType(AbstractTransactionTypeType value) {
        return new JAXBElement<AbstractTransactionTypeType>(_TransactionType_QNAME, AbstractTransactionTypeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreditQualificationRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4/OrderProvisioning/", name = "creditQualificationRequest")
    public JAXBElement<CreditQualificationRequest> createCreditQualificationRequest(CreditQualificationRequest value) {
        return new JAXBElement<CreditQualificationRequest>(_CreditQualificationRequest_QNAME, CreditQualificationRequest.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateAddressResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/common", name = "validateAddressResult")
    public JAXBElement<ValidateAddressResult> createValidateAddressResult(ValidateAddressResult value) {
        return new JAXBElement<ValidateAddressResult>(_ValidateAddressResult_QNAME, ValidateAddressResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RtimProvisioningRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4/OrderProvisioning/", name = "rtimProvisioningRequest")
    public JAXBElement<RtimProvisioningRequest> createRtimProvisioningRequest(RtimProvisioningRequest value) {
        return new JAXBElement<RtimProvisioningRequest>(_RtimProvisioningRequest_QNAME, RtimProvisioningRequest.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link RtimResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4/VendorRequestResponse/", name = "rtimResponse")
    public JAXBElement<RtimResponse> createRtimResponse(RtimResponse value) {
        return new JAXBElement<RtimResponse>(_RtimResponse_QNAME, RtimResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderStatusWithTypeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "lineItemStatus")
    public JAXBElement<OrderStatusWithTypeType> createLineItemStatus(OrderStatusWithTypeType value) {
        return new JAXBElement<OrderStatusWithTypeType>(_LineItemStatus_QNAME, OrderStatusWithTypeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OpRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4/OrderProvisioning/", name = "opRequest")
    public JAXBElement<OpRequest> createOpRequest(OpRequest value) {
        return new JAXBElement<OpRequest>(_OpRequest_QNAME, OpRequest.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "referredAddress")
    public JAXBElement<AddressType> createReferredAddress(AddressType value) {
        return new JAXBElement<AddressType>(_ReferredAddress_QNAME, AddressType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/common", name = "requestStatus")
    public JAXBElement<RequestStatus> createRequestStatus(RequestStatus value) {
        return new JAXBElement<RequestStatus>(_RequestStatus_QNAME, RequestStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClientInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/common", name = "clientInfo")
    public JAXBElement<ClientInfo> createClientInfo(ClientInfo value) {
        return new JAXBElement<ClientInfo>(_ClientInfo_QNAME, ClientInfo.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link RtimRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4/VendorRequestResponse/", name = "rtimRequest")
    public JAXBElement<RtimRequest> createRtimRequest(RtimRequest value) {
        return new JAXBElement<RtimRequest>(_RtimRequest_QNAME, RtimRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseContext }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/common", name = "repsonseContext")
    public JAXBElement<ResponseContext> createRepsonseContext(ResponseContext value) {
        return new JAXBElement<ResponseContext>(_RepsonseContext_QNAME, ResponseContext.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessPartyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "referrer")
    public JAXBElement<BusinessPartyType> createReferrer(BusinessPartyType value) {
        return new JAXBElement<BusinessPartyType>(_Referrer_QNAME, BusinessPartyType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link PriceTierListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "priceTierList")
    public JAXBElement<PriceTierListType> createPriceTierList(PriceTierListType value) {
        return new JAXBElement<PriceTierListType>(_PriceTierList_QNAME, PriceTierListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderStatusWithTypeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "orderStatus")
    public JAXBElement<OrderStatusWithTypeType> createOrderStatus(OrderStatusWithTypeType value) {
        return new JAXBElement<OrderStatusWithTypeType>(_OrderStatus_QNAME, OrderStatusWithTypeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidatePaymentRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4/OrderProvisioning/", name = "validatePaymentRequest")
    public JAXBElement<ValidatePaymentRequest> createValidatePaymentRequest(ValidatePaymentRequest value) {
        return new JAXBElement<ValidatePaymentRequest>(_ValidatePaymentRequest_QNAME, ValidatePaymentRequest.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "middleName")
    public JAXBElement<String> createMiddleName(String value) {
        return new JAXBElement<String>(_MiddleName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/common", name = "applicationType")
    public JAXBElement<ApplicationType> createApplicationType(ApplicationType value) {
        return new JAXBElement<ApplicationType>(_ApplicationType_QNAME, ApplicationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WishScheduleCollectionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "wishScheduleCollection")
    public JAXBElement<WishScheduleCollectionType> createWishScheduleCollection(WishScheduleCollectionType value) {
        return new JAXBElement<WishScheduleCollectionType>(_WishScheduleCollection_QNAME, WishScheduleCollectionType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link JobCollectionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "jobCollection")
    public JAXBElement<JobCollectionType> createJobCollection(JobCollectionType value) {
        return new JAXBElement<JobCollectionType>(_JobCollection_QNAME, JobCollectionType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link PhoneNumberType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "phoneNumber")
    public JAXBElement<PhoneNumberType> createPhoneNumber(PhoneNumberType value) {
        return new JAXBElement<PhoneNumberType>(_PhoneNumber_QNAME, PhoneNumberType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link OpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4/OrderProvisioning/", name = "opResponse")
    public JAXBElement<OpResponse> createOpResponse(OpResponse value) {
        return new JAXBElement<OpResponse>(_OpResponse_QNAME, OpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LineItemCollectionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "lineItemCollection")
    public JAXBElement<LineItemCollectionType> createLineItemCollection(LineItemCollectionType value) {
        return new JAXBElement<LineItemCollectionType>(_LineItemCollection_QNAME, LineItemCollectionType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link RtimProvisioningResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4/OrderProvisioning/", name = "rtimProvisioningResponse")
    public JAXBElement<RtimProvisioningResponse> createRtimProvisioningResponse(RtimProvisioningResponse value) {
        return new JAXBElement<RtimProvisioningResponse>(_RtimProvisioningResponse_QNAME, RtimProvisioningResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link LineItemDetailType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "detail")
    public JAXBElement<LineItemDetailType> createDetail(LineItemDetailType value) {
        return new JAXBElement<LineItemDetailType>(_Detail_QNAME, LineItemDetailType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "suggestedPhone", scope = TransientResponseType.class)
    public JAXBElement<List<String>> createTransientResponseTypeSuggestedPhone(List<String> value) {
        return new JAXBElement<List<String>>(_TransientResponseTypeSuggestedPhone_QNAME, ((Class) List.class), TransientResponseType.class, ((List<String> ) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "confirmedPhone", scope = TransientResponseType.class)
    public JAXBElement<List<String>> createTransientResponseTypeConfirmedPhone(List<String> value) {
        return new JAXBElement<List<String>>(_TransientResponseTypeConfirmedPhone_QNAME, ((Class) List.class), TransientResponseType.class, ((List<String> ) value));
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

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "timeSlotRefId", scope = CPOSOrderQualRequestContainer.class)
    public JAXBElement<String> createCPOSOrderQualRequestContainerTimeSlotRefId(String value) {
        return new JAXBElement<String>(_CPOSOrderQualRequestContainerTimeSlotRefId_QNAME, String.class, CPOSOrderQualRequestContainer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "requestedPhone", scope = TransientRequestType.class)
    public JAXBElement<List<String>> createTransientRequestTypeRequestedPhone(List<String> value) {
        return new JAXBElement<List<String>>(_TransientRequestTypeRequestedPhone_QNAME, ((Class) List.class), TransientRequestType.class, ((List<String> ) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "NewsletterSelected", scope = TransientRequestType.class)
    public JAXBElement<Boolean> createTransientRequestTypeNewsletterSelected(Boolean value) {
        return new JAXBElement<Boolean>(_TransientRequestTypeNewsletterSelected_QNAME, Boolean.class, TransientRequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "dtCreated", scope = CustomerType.class)
    public JAXBElement<XMLGregorianCalendar> createCustomerTypeDtCreated(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_CustomerTypeDtCreated_QNAME, XMLGregorianCalendar.class, CustomerType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "bestTimeToCall2", scope = CustomerType.class)
    public JAXBElement<XMLGregorianCalendar> createCustomerTypeBestTimeToCall2(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_CustomerTypeBestTimeToCall2_QNAME, XMLGregorianCalendar.class, CustomerType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "offersPresented", scope = CustomerType.class)
    public JAXBElement<String> createCustomerTypeOffersPresented(String value) {
        return new JAXBElement<String>(_CustomerTypeOffersPresented_QNAME, String.class, CustomerType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.A.com/v4", name = "bestTimeToCall1", scope = CustomerType.class)
    public JAXBElement<XMLGregorianCalendar> createCustomerTypeBestTimeToCall1(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_CustomerTypeBestTimeToCall1_QNAME, XMLGregorianCalendar.class, CustomerType.class, value);
    }

}
