package com.A.Vdao.transactional.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.Account;
import com.A.V.beans.entity.AddressBean;
import com.A.V.beans.entity.BillingInformation;
import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerAddressAssociation;
import com.A.V.beans.entity.CustomerContext;
import com.A.V.beans.entity.CustomerPaymentEvent;
import com.A.Vdao.crypt.EncryptStrategy;
import com.A.Vdao.dao.AccountDao;
import com.A.Vdao.dao.AddressDao;
import com.A.Vdao.dao.BillingDao;
import com.A.Vdao.dao.CustomerAttributeDao;
import com.A.Vdao.dao.CustomerContextDao;
import com.A.Vdao.dao.CustomerDao;
import com.A.Vdao.dao.CustomerSurveyDao;
import com.A.Vdao.dao.EmailDao;
import com.A.Vdao.dao.PaymentEventDao;
import com.A.Vdao.dao.PhoneDao;
import com.A.Vdao.dao.impl.SearchCriteria;
import com.A.Vdao.query.SearchQueryBuilder;

@Component
public class CustomerDaoImpl extends BaseTransactionalJpaDao implements CustomerDao {

    private static final Logger logger = Logger.getLogger(CustomerDaoImpl.class);

    private String FIND_BY_CUSTOMER_NUMBER = " SELECT c FROM Consumer c " + " LEFT JOIN FETCH c.paymentEvents pe" + " LEFT JOIN FETCH c.addresses assoc"
	    + " LEFT JOIN FETCH c.accounts accounts" + " LEFT JOIN FETCH c.billingInfoList bill" + " WHERE c.ACustomerNumber = :ACustomerNumber";

    private String FIND_BY_EXTERNAL_ID = " SELECT c FROM Consumer c " + " LEFT JOIN FETCH c.paymentEvents pe" + " LEFT JOIN FETCH c.addresses assoc"

	    + " LEFT JOIN FETCH c.accounts accounts" + " LEFT JOIN FETCH c.billingInfoList bill" + " WHERE c.externalId = :externalID ";

	   // + " LEFT JOIN FETCH c.accounts accounts" + " LEFT JOIN FETCH c.billingInfoList bill" + " WHERE c.externalId = :externalId ";


    // Used by harmony adapter to avoid duplicate records in V and dw
    private String FIND_BY_TASKLOG_ID = " SELECT c FROM Consumer c " + " LEFT JOIN FETCH c.paymentEvents pe" + " LEFT JOIN FETCH c.customerAttributes ca"
	    + " LEFT JOIN FETCH c.addresses assoc" + " LEFT JOIN FETCH c.accounts accounts" + " LEFT JOIN FETCH c.billingInfoList bill"
	    + " WHERE ca.name = 'TASK_LOG_ID' AND ca.value =:tid";

    private static final String NEXT_VAL = "SELECT nextval('CM_CONSUMER_BEAN_SEQ')";

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private EmailDao emailDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private BillingDao billingDao;

    @Autowired
    private PaymentEventDao paymentEventDao;

    @Autowired
    private CustomerContextDao customerContextDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private CustomerAttributeDao customerAttributeDao;

    @Autowired
    private CustomerSurveyDao customerSurveyDao;

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public void saveConsumerAndAddress(Consumer consumerBean) {

	logger.info("Executing saveCustomerAndAddress");
	logger.info("saveConsumerAndAddress method consumer data is "+consumerBean.toString());
	long start = System.currentTimeMillis();

	if (consumerBean != null) {

	    Long consumerExternalId = getNextExternalId();
	    
	  //commenting due to ingrain issue
	    EncryptStrategy.INSTANCE.encrypt(consumerBean);
	    
	    if (consumerBean.getCustomerContexts() != null) {
		customerContextDao.save(consumerBean.getCustomerContexts());
	    }

	    if (consumerBean.getCustomerAttributes() != null && !consumerBean.getCustomerAttributes().isEmpty()) {
		customerAttributeDao.saveCustomerAttribute(consumerBean.getCustomerAttributes());
	    }

	    if(consumerBean.getCustomerCsatSurveys() != null && !consumerBean.getCustomerCsatSurveys().isEmpty()) {
		customerSurveyDao.saveSurveys(consumerBean.getCustomerCsatSurveys());
	    }

	    logger.info("Saving Contact information...");

	    List<AddressBean> addressList = consumerBean.getConsumerAddressList();
	    if (addressList != null) {
		int i = 100;
		for (AddressBean address : addressList) {

		    // Generate external id and set it to Address external id
		    // and ignore client provided external id
		    Long extId = addressDao.getNextExternalId();
		    address.setExternalId(extId);
		    address.setConsumerExternalId(consumerExternalId);
		    addressDao.persist(address);
		    List<String> addressRoles = address.getAddressRoles();
		    // For each address role make an entry in CM_CUST_ADDRESS
		    // table
		    for (String role : addressRoles) {
			saveCustomerAddressAssnRole(consumerBean, address, role);
		    }
		}
	    }
	    // Setting sequence generated unique id for Customer's ExternalId
	    consumerBean.setExternalId(consumerExternalId);
	    // When the first time customer is created, its status would be
	    // active 'A'
	    // And when it is deleted we will just change the status to Deleted
	    // 'D'
	    consumerBean.setStatus("A");

	    Set<BillingInformation> billingList = new HashSet<BillingInformation>();

	    if ((consumerBean.getBillingInfoList() != null) && (consumerBean.getBillingInfoList().size() > 0)) {
		billingList.addAll(consumerBean.getBillingInfoList());
	    }

	    Set<CustomerPaymentEvent> eventList = new HashSet<CustomerPaymentEvent>();

	    if ((consumerBean.getPaymentEvents() != null) && (consumerBean.getPaymentEvents().size() > 0)) {
		eventList.addAll(consumerBean.getPaymentEvents());
	    }

	    consumerBean.setBillingInfoList(null);
	    consumerBean.setPaymentEvents(null);

	    Set<Account> accounts = consumerBean.getAccounts();
	    if (accounts != null) {

		for (Account account : accounts) {
		    long nextExtId = accountDao.getNextExternalId();
		    account.setExternalId(nextExtId);
		    account.setConsumer(null);
		    account.setConsumerExtId(String.valueOf(consumerBean.getExternalId()));

		    accountDao.persist(account);
		}
	    }

	    if (billingList != null) {
		for (BillingInformation billingBean : billingList) {

		    long addrExtId = getBillingAddressExtID(billingBean, consumerBean);
		    billingBean.setAddressExternalId(addrExtId);
		    billingBean.setConsumer(null);

		    billingDao.persist(billingBean);
		}
	    }

	    try {
		getEntityManager().persist(consumerBean);
	    }
	    catch (Exception nuoe) {
		logger.info(nuoe.getMessage());
	    }

	    if (accounts != null) {

		for (Account account : accounts) {

		    account.setConsumer(consumerBean);
		    accountDao.persist(account);
		}
	    }

	    consumerBean.setBillingInfoList(billingList);
	    if (billingList != null) {
		for (BillingInformation billingBean : billingList) {
		    billingBean.setConsumer(consumerBean);

		    billingDao.persist(billingBean);
		}
	    }

	    consumerBean.setPaymentEvents(eventList);

	    paymentEventDao.persist(consumerBean, eventList);

	    getEntityManager().merge(consumerBean);

	    getEntityManager().flush();
	    logger.info("saveCustomerAndAddress took : " + (System.currentTimeMillis() - start) + "ms");
		logger.info("end of saveConsumerAndAddress method consumer data is.................... "+consumerBean.toString());
	    logger.info("Saved customer external id : " + consumerBean.getExternalId());
	}
    }

    public CustomerAddressAssociation saveCustomerAddressAssnRole(Consumer consumer, AddressBean address, String role) {

	CustomerAddressAssociation newAssn = null;
	boolean isExisting = Boolean.FALSE;

	List<CustomerAddressAssociation> caList = new ArrayList<CustomerAddressAssociation>(consumer.getAddresses());

	for (CustomerAddressAssociation caa : caList) {
	    if ((caa.getPk() != null) && (caa.getAddressRole().equals(role)) && (address.getAddressUniqueId().equals(caa.getPk().getUniqueId()))) {
		isExisting = Boolean.TRUE;
	    }

	}

	if (!isExisting) {
	    newAssn = new CustomerAddressAssociation();
	    newAssn.setAddress(address);
	    newAssn.setConsumer(consumer);
	    newAssn.setAddressRole(role);
	    newAssn.setUniqueId(address.getAddressUniqueId());

	    consumer.getAddresses().add(newAssn);

	}

	return newAssn;

    }

    /**
     * a method to add an address for existing customer
     */
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public List<AddressBean> addCustomerAddress(Consumer srcConsumer) {
	logger.info("Executing addCustomerAddress");
	long start = System.currentTimeMillis();

	List<AddressBean> newAddressList = new ArrayList<AddressBean>();
	Consumer destConsumer = getEntityManager().find(Consumer.class, srcConsumer.getId());

	if (srcConsumer.getCustomerAttributes() != null && !srcConsumer.getCustomerAttributes().isEmpty()) {
	    destConsumer.setCustomerAttributes(srcConsumer.getCustomerAttributes());
	    customerAttributeDao.saveCustomerAttribute(destConsumer.getCustomerAttributes());
	}
	if (srcConsumer.getCustomerContexts() != null && !srcConsumer.getCustomerContexts().isEmpty()) {
	    destConsumer.setCustomerContexts(srcConsumer.getCustomerContexts());
	    customerContextDao.save(destConsumer.getCustomerContexts());
	}
	List<AddressBean> toCreateAddressList = srcConsumer.getConsumerAddressList();
	if (toCreateAddressList != null) {
	    logger.info("CustomerDaoImpl:addCustomerAddress():toCreateAddressList:size: " + toCreateAddressList.size() + " :destConsumer.getAddresses().size(): "
		    + destConsumer.getAddresses().size());
	    for (AddressBean toCreateAddress : toCreateAddressList) {

		logger.info("CustomerDaoImpl:addCustomerAddress():toCreateAddress: " + toCreateAddress.getAddressUniqueId());
		// Generate external id and set it to Address external id and
		// ignore client provided external id
		Long extId = addressDao.getNextExternalId();

		toCreateAddress.setExternalId(extId);
		toCreateAddress.setConsumerExternalId(destConsumer.getExternalId());
		addressDao.persist(toCreateAddress);
		newAddressList.add(toCreateAddress);
		List<String> addressRoles = toCreateAddress.getAddressRoles();
		// For each address role make an entry in CM_CUST_ADDRESS
		// table
		for (String role : addressRoles) {

		    logger.info("BEFORE:CustomerDaoImpl:addCustomerAddress():destConsumer.getAddresses():size: " + destConsumer.getAddresses().size());

		    CustomerAddressAssociation newAssn = new CustomerAddressAssociation();
		    newAssn.setAddress(toCreateAddress);
		    newAssn.setConsumer(destConsumer);
		    newAssn.setAddressRole(role);
		    newAssn.setUniqueId(toCreateAddress.getAddressUniqueId());

		    destConsumer.getAddresses().add(newAssn);

		    logger.info("AFTER:CustomerDaoImpl:addCustomerAddress():destConsumer.getAddresses():size: " + destConsumer.getAddresses().size());

		    if (newAssn != null) {
			getEntityManager().persist(newAssn);
			destConsumer.getAddresses().add(newAssn);
			logger.info("CustomerDaoImpl:addCustomerAddress():newAssn != null:destConsumer.getAddresses():size: " + destConsumer.getAddresses().size());

		    }
		}

	    }
	    // TODO do not remove this following logging statement, otherwise it
	    // will start throwing hibernate exception
	    // destConsumer.getAddresses() needs to be called to retrieve lazy
	    // laoded entities
	    logger.info("All assocation keys : " + destConsumer.getAddresses().toString());
	    
	    //commenting due to ingrain issue
	    EncryptStrategy.INSTANCE.encrypt(destConsumer);
	    try {
		if (destConsumer.getId() == 0) {
		    getEntityManager().merge(destConsumer);
		}
		else {
		    getEntityManager().persist(destConsumer);
		}
	    }
	    catch (NonUniqueObjectException nuoe) {
		logger.error(nuoe.getMessage());
	    }

	    getEntityManager().flush();
	    logger.info("addCustomerAddress took : " + (System.currentTimeMillis() - start) + "ms");
	}
	return newAddressList;
    }

    private long getBillingAddressExtID(BillingInformation billingBean, Consumer consumerBean) {
	long addrExtId = 0;

	List<CustomerAddressAssociation> caList = new ArrayList<CustomerAddressAssociation>(consumerBean.getAddresses());

	for (CustomerAddressAssociation ca : caList) {
	    if (ca.getUniqueId().equalsIgnoreCase(billingBean.getAddressRefId())) {
		addrExtId = ca.getAddress().getExternalId();
		break;
	    }
	}
	return addrExtId;
    }

    /**
     * Find consumer by contact info
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Consumer> findCustomerByContactInfo(Map<String, String> contactInfo, int offSet, int totalRows) {
	logger.info("finding customer by contactInfo: " + contactInfo);
	StringBuilder FIND_BY_CONTACT_INFO = new StringBuilder(" SELECT c FROM Consumer c " + " LEFT JOIN FETCH c.paymentEvents pe" + " LEFT JOIN FETCH c.addresses assoc"
		+ " LEFT JOIN FETCH c.billingInfoList bill" + " LEFT JOIN FETCH c.accounts accounts");
	long start = System.currentTimeMillis();

	if (contactInfo.size() > 0) {
	    FIND_BY_CONTACT_INFO.append(" where ");
	    boolean append = false;

	    if (contactInfo.get("homePhone") != null) {

		if (append) FIND_BY_CONTACT_INFO.append(" AND ");
		FIND_BY_CONTACT_INFO.append(" c.homePhoneValue = '" + contactInfo.get("homePhone").trim() + "'");
		append = true;
	    }

	    if (contactInfo.get("cellPhone") != null) {
		if (append) FIND_BY_CONTACT_INFO.append(" AND ");
		FIND_BY_CONTACT_INFO.append(" c.cellPhoneValue = '" + contactInfo.get("cellPhone").trim() + "'");
		append = true;
	    }

	    if (contactInfo.get("workPhone") != null) {

		if (append) FIND_BY_CONTACT_INFO.append(" AND ");
		FIND_BY_CONTACT_INFO.append(" c.workPhoneValue = '" + contactInfo.get("workPhone").trim() + "'");
		append = true;
	    }

	    if (contactInfo.get("homeEmail") != null) {
		if (append) FIND_BY_CONTACT_INFO.append(" AND ");
		FIND_BY_CONTACT_INFO.append(" c.homeEmailValue = '" + contactInfo.get("homeEmail").trim() + "'");
		append = true;
	    }

	    if (contactInfo.get("workEmail") != null) {
		if (append) FIND_BY_CONTACT_INFO.append(" AND ");
		FIND_BY_CONTACT_INFO.append(" c.workEmailValue = '" + contactInfo.get("workEmail").trim() + "'");
		append = true;
	    }
	}

	logger.info("Query : " + FIND_BY_CONTACT_INFO.toString());

	Query q = getEntityManager().createQuery(FIND_BY_CONTACT_INFO.toString());
	q.setFirstResult(offSet);
	q.setMaxResults(totalRows);
	List<Consumer> objList = q.getResultList();
	if (objList != null && objList.size() > 0) {
	    logger.info("Found customers[" + objList.size() + "] in db ");

	    for (Consumer consumer : objList) {

		List<CustomerAddressAssociation> caList = new ArrayList<CustomerAddressAssociation>(consumer.getAddresses());

		if (caList != null) {
		    for (CustomerAddressAssociation ca : caList) {
			AddressBean address = ca.getAddress();
			address.getAddressRoles().add(ca.getAddressRole());
			consumer.getConsumerAddressList().add(address);
		    }
		}
	    }
	    logger.info("findCustomerByContact took : " + (System.currentTimeMillis() - start) + "ms");

	    return objList;
	}
	return null;

    }

    /**
     * Find consumer by Confirmation no
     */
    @Transactional
    public Consumer findCustomerByConfirmationNumber(String confirmationNumber) {
	logger.info("finding customer by customer number: " + confirmationNumber);
	long start = System.currentTimeMillis();
	Query q = getEntityManager().createQuery(FIND_BY_CUSTOMER_NUMBER);
	//changed this below one to eliminate positional parameters as its deprecated in jboss 7 
	//q.setParameter(1, confirmationNumber);
	q.setParameter("FIND_BY_CUSTOMER_NUMBER", confirmationNumber);
	
	List objList = q.getResultList();
	if (objList != null && objList.size() > 0) {
	    logger.info("found customer in db ");
	    Consumer consumer = (Consumer) objList.get(0);
	    List<CustomerContext> ctxList = consumer.getCustomerContexts();
	    if (ctxList != null) {
		for (CustomerContext ctx : ctxList) {
		    ctx.getId();
		}
	    }
	    List<CustomerAddressAssociation> caList = new ArrayList<CustomerAddressAssociation>(consumer.getAddresses());

	    if (caList != null) {
		for (CustomerAddressAssociation ca : caList) {
		    AddressBean address = ca.getAddress();
		    address.getAddressRoles().add(ca.getAddressRole());
		    consumer.getConsumerAddressList().add(address);
		}
	    }
	    logger.info("find Customer By Customer number took : " + (System.currentTimeMillis() - start) + "ms");
	    return consumer;
	}
	return null;
    }

    /**
     * Find consumer by external id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Consumer findCustomerByExternalId(long externalId) {
	logger.info("finding customer by external id : " + externalId);
	long start = System.currentTimeMillis();
	Query q = getEntityManager().createQuery(FIND_BY_EXTERNAL_ID);
//<<<<<<< .mine
	//changed this below one to eliminate positional parameters as its deprecated in jboss 7 
	//q.setParameter(Integer.valueOf(externalId), externalId);
	q.setParameter("externalID", externalId);
//=======
	//q.setParameter(1, externalId);
	//q.setParameter("externalId", externalId);
//>>>>>>> .r59416
	List objList = q.getResultList();
	if (objList != null && objList.size() > 0) {
	    logger.info("Found customer in db : " + objList.size());
	    Consumer consumer = (Consumer) objList.get(0);
	    List<CustomerContext> ctxList = consumer.getCustomerContexts();
	    if (ctxList != null) {
		for (CustomerContext ctx : ctxList) {
		    ctx.getId();
		}
	    }
	    List<CustomerAddressAssociation> caList = new ArrayList<CustomerAddressAssociation>(consumer.getAddresses());
	    if (caList != null) {
		int i = 0;
		long prevAddrId = 0;
		logger.trace("CustomerDaoImpl:findCustomerByExternalId():caList: " + caList.size());
		Set<Long> addedAddressSet = new HashSet<Long>();
		for (CustomerAddressAssociation ca : caList) {
		    AddressBean address = ca.getAddress();
		    prevAddrId = address.getId();
		    address.getAddressRoles().add(ca.getAddressRole());

		    if (0 == i++) {
			logger.trace("CustomerDaoImpl:findCustomerByExternalId():0 == i++:prevAddrId: " + prevAddrId + " :ca.getAddressRole(): " + ca.getAddressRole()
				+ " :address: " + address.getAddressUniqueId());
			addedAddressSet.add(prevAddrId);
			consumer.getConsumerAddressList().add(address);
		    }
		    else if (!addedAddressSet.contains(prevAddrId)) {
			addedAddressSet.add(prevAddrId);
			consumer.getConsumerAddressList().add(address);
			logger.trace("CustomerDaoImpl:findCustomerByExternalId():!addedAddressSet.contains(prevAddrId):prevAddrId: " + prevAddrId + " :ca.getAddressRole(): "
				+ ca.getAddressRole() + " :address: " + address.getAddressUniqueId());
		    }

		}
	    }
	    logger.info("findCustomerByExternalId took : " + (System.currentTimeMillis() - start) + "ms");
	    return consumer;
	}
	return null;
    }

    // TODO fix this method once Customer interaction code is ready
    /**
     * Find consumer by Agent id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Consumer findCustomerByAgentId(long externalId) {
	logger.info("finding customer by agent id : " + externalId);
	long start = System.currentTimeMillis();
	Query q = getEntityManager().createQuery(FIND_BY_EXTERNAL_ID);
	q.setParameter(1, externalId);
	List objList = q.getResultList();
	if (objList != null && objList.size() > 0) {
	    logger.info("found customer in db :"+objList.size());
	    Consumer consumer = (Consumer) objList.get(0);
	    List<CustomerAddressAssociation> caList = new ArrayList<CustomerAddressAssociation>(consumer.getAddresses());
	    if (caList != null) {
		for (CustomerAddressAssociation ca : caList) {
		    AddressBean address = ca.getAddress();
		    address.getAddressRoles().add(ca.getAddressRole());
		    consumer.getConsumerAddressList().add(address);
		}
	    }
	    logger.info("findCustomerByAgentId took : " + (System.currentTimeMillis() - start) + "ms");
	    return consumer;
	}
	return null;
    }

    /**
     * A method to search customer based on different search criteria
     */
    public List<Consumer> locateCustomer(SearchCriteria searchCriteria, int offSet, int totalRows) {
	logger.info("Executing locateCustomer");
	logger.debug("Search criteria : " + searchCriteria.toString());
	long start = System.currentTimeMillis();
	List<Consumer> custList = new ArrayList<Consumer>();
	// QueryBuilder qb = new QueryBuilder();
	// String sql = qb.createQuery(searchCriteria);
	String sql = SearchQueryBuilder.getLocateCustomerSearchQuery(searchCriteria, true);
	logger.info("Searching customer : " + sql);

	Query q = getEntityManager().createNativeQuery(sql, Consumer.class);
	q.setFirstResult(offSet);
	q.setMaxResults(totalRows);
	List objList = q.getResultList();
	if (objList != null && objList.size() > 0) {

	    logger.trace("Total customer found :" + objList.size());

	    List<Consumer> cList = (List<Consumer>) objList;
	    Iterator<Consumer> cIter = cList.iterator();
	    Set<Long> uniqueCust = new HashSet<Long>();
	    while (cIter.hasNext()) {
		Consumer consumer = cIter.next();
		if (uniqueCust.contains(consumer.getExternalId())) {
		    cIter.remove();
		}
		else {
		    consumer.setCustomerContexts(null);
		    consumer.setPaymentEvents(null);
		    consumer.setBillingInfoList(null);
		    consumer.setAddresses(null);
		    consumer.setAccounts(null);
		    consumer.setConsumerAddressList(null);
		    uniqueCust.add(consumer.getExternalId());
		}

	    }
	    logger.info("locateCustomer took : " + (System.currentTimeMillis() - start) + "ms");
	    return objList;
	}
	logger.info("Could not locate customer");
	return null;
    }

    /*public Long getNextExternalId() {
	Query q = getEntityManager().createNativeQuery(NEXT_VAL);

	Object obj = q.getResultList().get(0);

	return DataTypeConverter.INSTANCE.objToLong(obj);

    }*/
    
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public Long getNextExternalId() {
	  	  Session session = (Session)getEntityManager().getDelegate();
	  	  Connection conn = ((SessionImpl)session).connection();
	  	  PreparedStatement ps = null;
	  	  ResultSet rs = null;
	  	  long externalId = 0;
	  	  try {
	  	   ps = conn.prepareStatement(NEXT_VAL);
	  	   rs = ps.executeQuery();
	  	   rs.next();
	  	   externalId = rs.getLong("NEXTVAL");
	  	  } catch (SQLException e) {
	  	   // TODO Auto-generated catch block
	  	   e.printStackTrace();
	  	  } finally {
	  	   if(rs != null) {
	  	          try {
	  	           rs.close();
	  	          } catch(SQLException e) {
	  	           e.printStackTrace();
	  	          }
	  	      }
	  	   
	  	   if(ps != null) {
	  	          try {
	  	           ps.close();
	  	          } catch(SQLException e) {
	  	           e.printStackTrace();
	  	          }
	  	      }
	  	  }
	  	  return externalId;
  	 }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCustomer(Long externalId) {
	Consumer consumer = findCustomerByExternalId(externalId);
	if (consumer != null) {

	    // Removing contact details
	    phoneDao.remove(consumer.getHomePhone());
	    phoneDao.remove(consumer.getCellPhone());
	    phoneDao.remove(consumer.getWorkPhone());
	    emailDao.remove(consumer.getHomeEMail());
	    emailDao.remove(consumer.getWorkEMail());

	    List<BillingInformation> billingList = new ArrayList<BillingInformation>(consumer.getBillingInfoList());

	    if (billingList != null) {
		for (BillingInformation bean : billingList) {
		    billingDao.remove(bean);
		}
	    }

	    Set<Account> accounts = consumer.getAccounts();
	    if (accounts != null) {

		for (Account account : accounts) {
		    accountDao.remove(account);
		}
	    }

	    paymentEventDao.remove(consumer.getPaymentEvents());

	    // Removing customer address association
	    List<CustomerAddressAssociation> caList = new ArrayList<CustomerAddressAssociation>(consumer.getAddresses());
	    if (caList != null) {
		for (CustomerAddressAssociation ca : caList) {
		    getEntityManager().remove(ca);
		}

	    }

	    // removing addresses
	    if (caList != null) {
		long previousId = 0;
		int counter = 0;
		for (CustomerAddressAssociation ca : caList) {
		    previousId = ca.getAddress().getId();
		    if (0 == counter++) {
			addressDao.remove(ca.getAddress());
		    }
		    else if (previousId != ca.getAddress().getId()) {
			addressDao.remove(ca.getAddress());
		    }
		}

	    }

	    // removing customer
	    getEntityManager().remove(consumer);

	}
    }

    public Map<String, String> getCustAddrRoleMap(CustomerAddressAssociation assns) {

	Map<String, String> mapAssn = new HashMap<String, String>();

	try {

	    for (String role : assns.getAddress().getAddressRoles()) {
		mapAssn.put(role, role);
	    }
	}
	catch (Exception e) {
	    logger.error("unable to assign address roles");
	}

	return mapAssn;
    }

    public Map<String, String> getRoleMap(List<String> roles) {

	Map<String, String> mapRoles = new HashMap<String, String>();
	for (String role : roles) {
	    mapRoles.put(role, role);
	}

	return mapRoles;
    }

    public void saveCustomerAddressAssnRole(Consumer consumer) {
	logger.info("Executing saveCustomerAddressAssnRole");
	long start = System.currentTimeMillis();
	// Update address info
	List<CustomerAddressAssociation> caList = new ArrayList<CustomerAddressAssociation>(consumer.getAddresses());

	Map<String, String> updateMonitor = new HashMap<String, String>();

	if (caList != null) {
	    for (Iterator<CustomerAddressAssociation> it = caList.iterator(); it.hasNext();) {

		CustomerAddressAssociation ca = it.next();

		AddressBean address = ca.getAddress();

		if (address == null) {
		    throw new IllegalArgumentException("missing address for consumer:" + consumer.getExternalId() + ":" + ca.getUniqueId());
		}

		if (ca.isDeleted()) {
		    // remove from list
		    it.remove();

		    // remove from database
		    CustomerAddressAssociation caDBVersion = getEntityManager().merge(ca);

		    getEntityManager().remove(caDBVersion);

		}
		else {

		    // update a unique address only once
		    if (!updateMonitor.containsKey(ca.getUniqueId())) {
			if ((address.getExternalId() != null) && (address.getExternalId().longValue() != 0)) {
			    addressDao.merge(address);
			}
			else {
			    address.setExternalId(addressDao.getNextExternalId());
			    address.setConsumerExternalId(consumer.getExternalId());
			    addressDao.merge(address);
			}
			updateMonitor.put(ca.getUniqueId(), ca.getUniqueId());

		    }
		    // Add Role
		    if (ca.isNewAssn()) {
			if (ca.getAddress() == null) {
			    ca.setAddress(address);
			}
			getEntityManager().persist(ca);
		    }
		    else {
			getEntityManager().merge(ca);
		    }

		}// else not delete

	    }// for

	    Iterator<CustomerAddressAssociation> iterator = consumer.getAddresses().iterator();
	    while (iterator.hasNext()) {
		CustomerAddressAssociation element = iterator.next();
		if ((element != null) && (element.isDeleted())) {
		    iterator.remove();
		}
	    }

	}// list not null
	logger.info("saveCustomerAddressAssnRole took : " + (System.currentTimeMillis() - start) + "ms");
    }

    public void removeCurrentEntriesNotInDesiredRoleList(final Map<String, String> mapDesiredRoles, final Map<String, CustomerAddressAssociation> mapFromDBExisting) {

	Set<String> fromDBExistingKeys = mapFromDBExisting.keySet();

	// If input roles do not contain existing. remove existing
	for (String fromDBExistingKey : fromDBExistingKeys) {
	    if (!mapDesiredRoles.containsKey(fromDBExistingKey)) {
		CustomerAddressAssociation toRemove = mapFromDBExisting.get(fromDBExistingKey);
		toRemove.setConsumer(null);
		toRemove.setAddress(null);
		getEntityManager().remove(toRemove);

	    }
	}
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public void updateCustomer(Consumer consumer) {
	logger.info("Updating Customer");
	long start = System.currentTimeMillis();
	if (consumer != null) {
		
		//commenting due to ingrain issue
		EncryptStrategy.INSTANCE.encrypt(consumer);

	    // Update contact info
	    phoneDao.merge(consumer.getCellPhone());
	    phoneDao.merge(consumer.getHomePhone());
	    phoneDao.merge(consumer.getWorkPhone());
	    emailDao.merge(consumer.getHomeEMail());
	    emailDao.merge(consumer.getWorkEMail());

	    if (consumer.getCustomerContexts() != null) {
		customerContextDao.save(consumer.getCustomerContexts());
	    }

	    if (consumer.getCustomerAttributes() != null && !consumer.getCustomerAttributes().isEmpty()) {
		customerAttributeDao.saveCustomerAttribute(consumer.getCustomerAttributes());
	    }

	    if(consumer.getCustomerCsatSurveys() != null) {
		customerSurveyDao.saveSurveys(consumer.getCustomerCsatSurveys());
	    }
	    // Update billing info

	    if ((consumer.getBillingInfoList() != null) && (consumer.getBillingInfoList().size() > 0)) {
		List<BillingInformation> billingList = new ArrayList<BillingInformation>(consumer.getBillingInfoList());

		for (BillingInformation billingBean : billingList) {
		    billingDao.merge(billingBean);
		}
	    }

	    if ((consumer.getAccounts() != null) && (consumer.getAccounts().size() > 0)) {
		Set<Account> accounts = consumer.getAccounts();

		for (Account account : accounts) {
		    accountDao.merge(account);
		}
	    }

	    paymentEventDao.merge(consumer, consumer.getPaymentEvents());

	    saveCustomerAddressAssnRole(consumer);
	    getEntityManager().flush();
	    getEntityManager().merge(consumer);
	    getEntityManager().flush();
	    logger.info("updateCustomer took : " + (System.currentTimeMillis() - start) + "ms");
	    logger.info("Customer [" + consumer.getExternalId() + "] is updated in db");

	}
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public String getNextCustomerNo() {
    	/*Query q = getEntityManager().createNativeQuery(NEXT_VAL);
    	Object obj = q.getResultList().get(0);

    	Long nextVal = DataTypeConverter.INSTANCE.objToLong(obj);*/
        Long nextVal = getNextExternalId();

    	// Changes made based on AIRS 84360 requiment
    	// String acNo = "AC-";
    	// if (nextVal != null) {
    	// acNo = acNo + System.currentTimeMillis() + nextVal.longValue();
    	// }
    	return String.valueOf(nextVal);
    }

    public static void main(String[] args) {
	Map<String, String> contacts = new HashMap<String, String>();
	contacts.put("homePhone", "111-222-3333");
	CustomerDaoImpl dao = new CustomerDaoImpl();
	dao.findCustomerByContactInfo(contacts, 0, 100);
    }

    @Transactional
    public Consumer findCustomerByTaskLogId(long taskLogId) {
	logger.info("finding customer by taskLog id : " + taskLogId);
	long start = System.currentTimeMillis();
	Query q = getEntityManager().createQuery(FIND_BY_TASKLOG_ID);
	q.setParameter("tid", String.valueOf(taskLogId));
	List objList = q.getResultList();
	if (objList != null && objList.size() > 0) {
	    logger.info("found customer in db ");
	    Consumer consumer = (Consumer) objList.get(0);
	    List<CustomerContext> ctxList = consumer.getCustomerContexts();
	    if (ctxList != null) {
		for (CustomerContext ctx : ctxList) {
		    ctx.getId();
		}
	    }
	    List<CustomerAddressAssociation> caList = new ArrayList<CustomerAddressAssociation>(consumer.getAddresses());
	    if (caList != null) {
		int i = 0;
		long prevAddrId = 0;
		Set<Long> addedAddressSet = new HashSet<Long>();
		for (CustomerAddressAssociation ca : caList) {
		    AddressBean address = ca.getAddress();
		    prevAddrId = address.getId();
		    address.getAddressRoles().add(ca.getAddressRole());

		    if (0 == i++) {
			addedAddressSet.add(prevAddrId);
			consumer.getConsumerAddressList().add(address);
		    }
		    else if (!addedAddressSet.contains(prevAddrId)) {
			addedAddressSet.add(prevAddrId);
			consumer.getConsumerAddressList().add(address);
		    }

		}
	    }
	    logger.info("findCustomerByTaskLogId took : " + (System.currentTimeMillis() - start) + "ms");
	    return consumer;
	}
	return null;
    }
}
