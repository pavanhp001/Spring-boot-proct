package com.A.Vdao.transactional.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.util.DateUtil;
import com.A.V.beans.LineItemPriceInfo;
import com.A.V.beans.LineitemScheduleInfo;
import com.A.V.beans.entity.AccountHolder;
import com.A.V.beans.entity.BusinessParty;
import com.A.V.beans.entity.CustomSelection;
import com.A.V.beans.entity.CustomerPaymentEvent;
import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.LineItemAttribute;
import com.A.V.beans.entity.Product;
import com.A.V.beans.entity.SalesOrder;
import com.A.V.beans.entity.SalesOrderContext;
import com.A.V.beans.entity.SelectedDialogue;
import com.A.V.beans.entity.SelectedFeatureValue;
import com.A.V.beans.entity.StatusRecordBean;
import com.A.V.beans.entity.User;
import com.A.V.beans.entity.VAccordOrderMapping;
import com.A.Vdao.dao.AccountHolderDao;
import com.A.Vdao.dao.AddressDao;
import com.A.Vdao.dao.BillingDao;
import com.A.Vdao.dao.BusinessPartyDao;
import com.A.Vdao.dao.CatalogProductDao;
import com.A.Vdao.dao.CustomSelectionDao;
import com.A.Vdao.dao.CustomerDao;
import com.A.Vdao.dao.EmailDao;
import com.A.Vdao.dao.LineItemAttributeDao;
import com.A.Vdao.dao.LineItemDao;
import com.A.Vdao.dao.LineItemDetailDao;
import com.A.Vdao.dao.OrderManagementDao;
import com.A.Vdao.dao.PhoneDao;
import com.A.Vdao.dao.SelectedDialogueDao;
import com.A.Vdao.dao.SelectedFeatureValueDao;
import com.A.Vdao.dao.StatusDao;
import com.A.Vdao.dao.UserDao;
import com.A.Vdao.transactional.dao.util.CriteriaBuilder;

/**
 * @author ebthomas
 *
 */

@Component
public class OrderManagementDaoImpl extends BaseTransactionalJpaDao implements
		OrderManagementDao {
	private static final String DW = "dw";
	private static final String SRVC_SLCTN_ID = "srvc_slctn_id";
	private static final List<SalesOrder> EMPTY_SALES_ORDER_LIST = new ArrayList<SalesOrder>();
	private static final String PRODUCT_PROMOTION = "productPromotion";
	private static final String PRODUCT = "product";
	private static final Logger logger = Logger
			.getLogger(OrderManagementDaoImpl.class);
	private static final String NEXT_VAL = "SELECT nextval('OM_SALESORDER_BEAN_SEQ')";

	@Autowired
	private UserDao userDao;

	@Autowired
	private SelectedFeatureValueDao selectedFeatureValueDao;

	@Autowired
	private AddressDao addressDao;

	@Autowired
	private PhoneDao phoneDao;

	@Autowired
	private BillingDao billingDao;

	@Autowired
	private EmailDao emailDao;

	@Autowired
	private StatusDao statusDao;

	@Autowired
	private LineItemDetailDao lineItemDetailDao;

	@Autowired
	private LineItemDao lineItemDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private CatalogProductDao catalogProductDao;

	@Autowired
	private BusinessPartyDao businessPartyDao;

	@Autowired
	private SelectedDialogueDao selectedDialogue;

	@Autowired
	private CustomSelectionDao customSelectionDao;

	@Autowired
	private LineItemAttributeDao lineItemAttributeDao;
	
	@Autowired
	private AccountHolderDao accountHolderDao;
	
	/**
	 * Order Management.
	 */
	public OrderManagementDaoImpl() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public SalesOrder remove(final SalesOrder salesOrder) {
		if (getEntityManager() == null) {
			throw new IllegalArgumentException(
					"Entity Manager should not be NULL");
		}

		if (salesOrder == null) {
			return salesOrder;
		}

		try {

			List<StatusRecordBean> statusRecordHistoryList = salesOrder
					.getHistoricStatus();
			StatusRecordBean currentStatus = salesOrder.getCurrentStatus();
			List<LineItem> lineItemBeanList = salesOrder.getLineItems();

			if (currentStatus != null) {
				statusDao.remove(currentStatus);
			}

			if ((statusRecordHistoryList != null)
					&& (statusRecordHistoryList.size() > 0)) {
				statusDao.remove(statusRecordHistoryList);
			}

			if (lineItemBeanList != null) {
				lineItemDao.remove(lineItemBeanList);
			}

			getEntityManager().remove(salesOrder);
			getEntityManager().flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return salesOrder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public SalesOrder removeSalesOrderById(final String salesOrderId) {
		if (getEntityManager() == null) {
			throw new IllegalArgumentException(
					"Entity Manager should not be NULL");
		}

		if (salesOrderId == null) {
			throw new IllegalArgumentException("sales.order.id.is.null");
		}

		SalesOrder salesOrder = null;

		try {
			salesOrder = findSalesOrderByOrderId(salesOrderId.trim());

			if (salesOrder != null) {
				List<StatusRecordBean> statusRecordHistoryList = salesOrder
						.getHistoricStatus();
				StatusRecordBean currentStatus = salesOrder.getCurrentStatus();
				List<LineItem> lineItemBeanList = salesOrder.getLineItems();

				if (currentStatus != null) {
					getEntityManager().remove(
							getEntityManager().getReference(
									StatusRecordBean.class,
									currentStatus.getId()));
				}
				salesOrder.setCurrentStatus(null);
				/*
				 * billingDao.remove( salesOrder.getBilling() );
				 * salesOrder.setBilling( null );
				 */
				statusDao.remove(statusRecordHistoryList);
				salesOrder.setHistoricStatus(null);
				lineItemDao.remove(lineItemBeanList);
				salesOrder.setLineItems(null);
				/*
				 * phoneDao.remove( salesOrder.getFirstPhoneNumber() );
				 * salesOrder.setFirstPhoneNumber( null ); phoneDao.remove(
				 * salesOrder.getSecondPhoneNumber() );
				 * salesOrder.setSecondPhoneNumber( null );
				 */
				getEntityManager().remove(
						getEntityManager().getReference(SalesOrder.class,
								salesOrder.getId()));
				getEntityManager().flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return salesOrder;

	}

	/**
	 * @param em
	 *            Entity Manager that will perform the operation
	 * @param salesOrderId
	 *            id of the sales order
	 * @return returns the sales order bean identified by the salesOrderId
	 */
	@Transactional
	public SalesOrder findSalesOrderByOrderId(final String salesOrderId) {
		logger.info("findSalesOrderByOrderId");
		long start = System.currentTimeMillis();
		if (salesOrderId == null) {
			return null;
		}

		if (getEntityManager() == null) {
			throw new IllegalArgumentException(
					"Entity Manager should not be NULL");
		}

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT o from SalesOrder o ");
		sb.append(" LEFT JOIN FETCH o.lineItems li ");
		sb.append(" LEFT JOIN FETCH o.historicStatus");
		// sb.append(" LEFT JOIN FETCH o.salesOrderContexts");
		//sb.append(" LEFT JOIN FETCH li.selectedFeatureValues ");
		sb.append(" LEFT JOIN FETCH li.dialogues");
		sb.append(" LEFT JOIN FETCH li.selections");
		sb.append(" LEFT JOIN FETCH li.lineItemAttribute");
		sb.append(" LEFT JOIN FETCH li.historicStatus");
		sb.append(" LEFT JOIN FETCH li.historicStatus");
		sb.append(" WHERE ");
		sb.append(" o.externalId = :salesOrderId ");
		logger.info("SalesOrderByOrderId query : "+sb.toString() );//
		Query query = getEntityManager().createQuery(sb.toString());

		//query.setParameter(1, Long.valueOf(salesOrderId.trim()));
		query.setParameter("salesOrderId", Long.valueOf(salesOrderId.trim()));

		try {
			Object obj = query.getSingleResult();

			if (obj == null) {
				return null;
			}

			if (obj instanceof SalesOrder) {
				SalesOrder salesOrder = (SalesOrder) obj;
				touchSalesContext(salesOrder);
				for(LineItem li : salesOrder.getLineItems()){
					if (li != null && li.getSelectedFeatureValues() != null) {
						Set<SelectedFeatureValue> sfvSet = li
								.getSelectedFeatureValues();
						for (SelectedFeatureValue sfv : sfvSet) {
							sfv.getExternalId();
						}
					}
				}
				logger.info("findSalesOrderByOrderId took : " +(System.currentTimeMillis() - start) +"ms" );
				return salesOrder;
			}

		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
			return null;
		}

		return null;

	}

	public SalesOrder findByConfirmationNumber(final String confirmationNumber) {
		logger.info("findByConfirmationNumber");
		long start = System.currentTimeMillis();
		SalesOrder order =  findBy("AConfirmNumber", confirmationNumber);
		logger.info("findByConfirmationNumber took : " + (System.currentTimeMillis() - start) +"ms" );
		return order;
	}

	public SalesOrder findByAccountNumber(final String accountNumber) {
		logger.info("findByAccountNumber");
		long start = System.currentTimeMillis();
		SalesOrder order= findBy("AAccountNumber", accountNumber);
		logger.info("findByAccountNumber took : " + (System.currentTimeMillis() - start) +"ms");
		return order;
	}

	@Transactional
	public List<SalesOrder> findByCustomer(final Long customerNo, int offSet,
			int totalRows) {
		logger.info("Inside modified findByCustomer : " + customerNo);
		long start = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT o from SalesOrder o ");
		sb.append(" LEFT JOIN FETCH o.lineItems li ");
		//sb.append(" LEFT JOIN FETCH o.historicStatus");
		// sb.append(" LEFT JOIN FETCH o.salesOrderContexts");
		//sb.append(" LEFT JOIN FETCH li.selectedFeatureValues ");
		/*sb.append(" LEFT JOIN FETCH li.dialogues");
		sb.append(" LEFT JOIN FETCH li.selections");
		sb.append(" LEFT JOIN FETCH li.lineItemAttribute");
		sb.append(" LEFT JOIN FETCH li.historicStatus");*/
		sb.append(" WHERE ");
		sb.append(" o.consumerExternalId = ?1 order by o.id ");

		Query query = getEntityManager().createQuery(sb.toString());
		query.setParameter(1, customerNo);
		query.setFirstResult(offSet);
		query.setMaxResults(totalRows);
		try {
			List<Object> objList = query.getResultList();

			if (objList == null) {
				return null;
			}

			List<SalesOrder> sobList = new ArrayList<SalesOrder>();

			for (Object obj : objList) {
				if (obj instanceof SalesOrder) {
					SalesOrder salesOrder = (SalesOrder) obj;
					for(LineItem li : salesOrder.getLineItems()){

						if (li != null){ 
							if(li.getSelectedFeatureValues() != null) {
								/*Set<SelectedFeatureValue> sfvSet = li
										.getSelectedFeatureValues();
								for (SelectedFeatureValue sfv : sfvSet) {
									sfv.getExternalId();
								}*/
								Hibernate.initialize(li.getSelectedFeatureValues());
							}
							if(li.getDialogues() != null){
								Hibernate.initialize(li.getDialogues());
							}
							if(li.getSelections() != null){
								Hibernate.initialize(li.getSelections());
							}
							if(li.getLineItemAttribute() != null){
								Hibernate.initialize(li.getLineItemAttribute());
							}
							if(li.getHistoricStatus() != null){
								Hibernate.initialize(li.getHistoricStatus());
							}
						}
					}
					if(salesOrder.getHistoricStatus() != null){
						Hibernate.initialize(salesOrder.getHistoricStatus());
					}
					sobList.add(salesOrder);
				}
			}
			logger.info("findByCustomerId took : " + (System.currentTimeMillis() - start) +"ms");
			return sobList;

		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
			return null;
		}
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public List<SalesOrder> findByDate(final Date moveDate, int offSet,
			int totalRows) {
		logger.info("findByDate date[ " + moveDate +" ] ");
		long start = System.currentTimeMillis();
		List<SalesOrder> ordList =  findBy("lineitemScheduleInfo.scheduledStartDate",
				DateUtil.startOfDay(moveDate), DateUtil.endOfDay(moveDate),
				offSet, totalRows);
		logger.info("findByDate took : " + (System.currentTimeMillis() - start) +"ms");
		return ordList;
	}

	public List<SalesOrder> findByScheduleDate(final Date scheduleDate,
			final String status, final String reason, int offSet, int totalRows) {

		logger.info("Searching order by scheduleDate" + " [Status : " + status + "]  [Reason : " + reason + " ] [OffSet : " + offSet + " ] [totalRows : " + totalRows + "]");
		long startTime = System.currentTimeMillis();

		final Date start = DateUtil.startOfDay(scheduleDate);
		final Date end = DateUtil.endOfDay(scheduleDate);
		final Boolean isReason = (reason != null) && (reason.length() > 0);
		final Boolean isStatus = (status != null) && (status.length() > 0);

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT o from SalesOrder o ");
		sb.append(" LEFT JOIN FETCH o.lineItems li ");
		sb.append(" LEFT JOIN FETCH o.historicStatus");
		// sb.append(" LEFT JOIN FETCH o.salesOrderContexts");
		sb.append(" LEFT JOIN FETCH li.selections");
		sb.append(" LEFT JOIN FETCH li.historicStatus");
		sb.append(" LEFT JOIN FETCH li.lineItemAttribute");

		sb.append(" WHERE ");
		sb.append(" li.lineitemScheduleInfo.scheduledStartDate BETWEEN ?1 AND ?2 ");

		if (isStatus) {
			sb.append(" AND '");
			sb.append(status);
			sb.append("' = li.currentStatus.status ");
		}

		if (isReason) {
			sb.append(" AND '");
			sb.append(reason);
			sb.append("'  in elements(li.currentStatus.reasons) ");
		}

		sb.append(" ORDER BY o.id ");

		Query query = getEntityManager().createQuery(sb.toString());

		query.setParameter(1, start, TemporalType.TIME);
		query.setParameter(2, end, TemporalType.TIME);

		query.setFirstResult(offSet);
		query.setMaxResults(totalRows);
		try {
			List<Object> objList = query.getResultList();

			List<SalesOrder> sobList = new ArrayList<SalesOrder>();

			try {
				for (Object obj : objList) {
					if (obj instanceof SalesOrder) {
						SalesOrder salesOrder = (SalesOrder) obj;

						for (LineItem li : salesOrder.getLineItems()) {
							li.setSelectedFeatureValues(null);
							li.setDialogues(null);
							li.setSelections(null);
							li.setSelectedFeatureValues(null);
						}

						sobList.add(salesOrder);
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

			logger.info("findByScheduleDate took : " + (System.currentTimeMillis() - startTime) +"ms");
			return sobList;

		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
			return new ArrayList<SalesOrder>();

		}

	}

	@Transactional
	public List<SalesOrder> findBy(final String criteriaField,
			final Date start, final Date end, int offSet, int totalRows) {
		logger.info("Searching order by " + criteriaField +" [StartDate : " + start + "]  [EndDate : " + end + " ] [OffSet : " + offSet + " ] [totalRows : " + totalRows + "]");
		long stTime = System.currentTimeMillis();
		if (criteriaField == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT o from SalesOrder o ");
		sb.append(" LEFT JOIN FETCH o.lineItems li ");
		sb.append(" LEFT JOIN FETCH o.historicStatus");
//		// sb.append(" LEFT JOIN FETCH o.salesOrderContexts");
		//sb.append(" LEFT JOIN FETCH li.selectedFeatureValues ");
		sb.append(" LEFT JOIN FETCH li.dialogues");
		sb.append(" LEFT JOIN FETCH li.selections");
		sb.append(" LEFT JOIN FETCH li.lineItemAttribute");
		sb.append(" LEFT JOIN FETCH li.historicStatus");
		sb.append(" WHERE ");
		sb.append(" li." + criteriaField + " BETWEEN ?1 AND ?2 ORDER BY o.id");

		Query query = getEntityManager().createQuery(sb.toString());
		query.setParameter(1, start, TemporalType.TIMESTAMP);
		query.setParameter(2, end, TemporalType.TIMESTAMP);
		query.setFirstResult(offSet);
		query.setMaxResults(totalRows);
		try {

			List<Object> objList = query.getResultList();


			if (objList == null) {
				return null;
			}

			List<SalesOrder> sobList = new ArrayList<SalesOrder>();

			for (Object obj : objList) {
				if (obj instanceof SalesOrder) {
					SalesOrder salesOrder = (SalesOrder) obj;
					for(LineItem li : salesOrder.getLineItems()){

						if (li != null && li.getSelectedFeatureValues() != null) {
							Set<SelectedFeatureValue> sfvSet = li
									.getSelectedFeatureValues();
							for (SelectedFeatureValue sfv : sfvSet) {
								sfv.getExternalId();
							}
						}
					}
					sobList.add(salesOrder);
				}
			}
			logger.debug("Order size : " + sobList.size());
			logger.debug("Total time taken :  " + (System.currentTimeMillis() - stTime) + "ms");
			return sobList;

		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public SalesOrder findBy(final String criteriaField,
			final String criteriaValue) {
		if ((criteriaField == null) || (criteriaValue == null)) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT o from SalesOrder o ");
		sb.append(" LEFT JOIN FETCH o.lineItems li ");
		sb.append(" LEFT JOIN FETCH o.historicStatus");
		// sb.append(" LEFT JOIN FETCH o.salesOrderContexts");
		//sb.append(" LEFT JOIN FETCH li.selectedFeatureValues ");
		sb.append(" LEFT JOIN FETCH li.dialogues");
		sb.append(" LEFT JOIN FETCH li.selections");
		sb.append(" LEFT JOIN FETCH li.lineItemAttribute");
		sb.append(" LEFT JOIN FETCH li.historicStatus");
		sb.append(" WHERE ");
		sb.append(" o." + criteriaField + " = ?1");

		Query query = getEntityManager().createQuery(sb.toString());
		query.setParameter(1, criteriaValue.trim());

		try {
			Object obj = query.getSingleResult();

			if (obj == null) {
				return null;
			}

			if (obj instanceof SalesOrder) {
				SalesOrder salesOrder = (SalesOrder) obj;
				for(LineItem li : salesOrder.getLineItems()){

					if (li != null && li.getSelectedFeatureValues() != null) {
						Set<SelectedFeatureValue> sfvSet = li
								.getSelectedFeatureValues();
						for (SelectedFeatureValue sfv : sfvSet) {
							sfv.getExternalId();
						}
					}
				}
				return salesOrder;
			}

		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
			return null;
		}

		throw new IllegalArgumentException("Invalid Query Result");
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public SalesOrder findBasicSalesOrderById(final Long id) {
		return findBasicSalesOrderById(id, false);
	}
	/**
	 * {@inheritDoc}
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public SalesOrder findBasicSalesOrderById(final Long id, boolean includeAccountHolders) {
		long stTime = System.currentTimeMillis();
		logger.info("Inside modified Seaching for basic order external id : " + id);
		if (id == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT o from SalesOrder o ");
		sb.append(" LEFT JOIN FETCH o.lineItems li ");
		//sb.append(" LEFT JOIN FETCH o.historicStatus");
		//sb.append(" LEFT JOIN FETCH li.selectedFeatureValues ");
		/*sb.append(" LEFT JOIN FETCH li.dialogues");
		sb.append(" LEFT JOIN FETCH li.selections");
		sb.append(" LEFT JOIN FETCH li.lineItemAttribute");
		sb.append(" LEFT JOIN FETCH li.historicStatus");*/
		if(includeAccountHolders) {
			logger.debug("Adding left join with lineItem payment events");
			sb.append(" LEFT JOIN FETCH li.paymentEvents");
		}
		sb.append(" WHERE ");
		//sb.append(" o.externalId = ?1 ");
		sb.append(" o.externalId = :salesOrderId ");

		Query query = getEntityManager().createQuery(sb.toString());
		//query.setParameter(1, id);
		query.setParameter("salesOrderId", id);

		try {
			Object obj = query.getSingleResult();

			if (obj == null) {
				return null;
			}

			if (obj instanceof SalesOrder) {

				SalesOrder salesOrder = (SalesOrder) obj;
				for(LineItem li : salesOrder.getLineItems()){

					if (li != null){
						if(li.getSelectedFeatureValues() != null) {
							/*Set<SelectedFeatureValue> sfvSet = li
									.getSelectedFeatureValues();
							for (SelectedFeatureValue sfv : sfvSet) {
								sfv.getExternalId();
							}*/
							Hibernate.initialize(li.getSelectedFeatureValues());
						}
						if(li.getDialogues() != null){
							Hibernate.initialize(li.getDialogues());
						}
						if(li.getSelections() != null){
							Hibernate.initialize(li.getSelections());
						}
						if(li.getLineItemAttribute() != null){
							Hibernate.initialize(li.getLineItemAttribute());
						}
						if(li.getHistoricStatus() != null){
							Hibernate.initialize(li.getHistoricStatus());
						}
					}
				}

				if (salesOrder.getHistoricStatus() != null) {
					/*List<StatusRecordBean> hsList = salesOrder
							.getHistoricStatus();
					for (StatusRecordBean hs : hsList) {
						hs.getId();
					}*/
					Hibernate.initialize(salesOrder.getHistoricStatus());
				}
				if(includeAccountHolders) {
					List<AccountHolder> savedAccountHolders = accountHolderDao
						.getAllAccountHoldersByOrderExternalId(id);
					salesOrder.setAccountHolders(savedAccountHolders);
				}
				
				logger.info("findBasicSalesOrderById took : " + (System.currentTimeMillis() - stTime) +"ms");
				return salesOrder;
			}

		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
			return null;
		}

		throw new IllegalArgumentException("Invalid Query Result");
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(value = "transactional", propagation = Propagation.NOT_SUPPORTED)
	public SalesOrder findById(final Long id) {
		return findById(id, false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(value = "transactional", propagation = Propagation.NOT_SUPPORTED)
	public SalesOrder findById(final Long id, boolean includeAccountHolders) {
		long stTime = System.currentTimeMillis();
		logger.info("Inside modified findById, Seaching for order external id : " + id);
		if (id == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT o from SalesOrder o ");
		sb.append(" LEFT JOIN FETCH o.lineItems li ");
		//sb.append(" LEFT JOIN FETCH o.historicStatus");
		//sb.append(" LEFT JOIN FETCH li.selectedFeatureValues ");
		//sb.append(" LEFT JOIN FETCH li.dialogues");
		//sb.append(" LEFT JOIN FETCH li.selections");
		//sb.append(" LEFT JOIN FETCH li.lineItemAttribute");
		//sb.append(" LEFT JOIN FETCH li.historicStatus");
		if(includeAccountHolders) {
			logger.debug("Adding left join with lineItem payment events");
			sb.append(" LEFT JOIN FETCH li.paymentEvents");
		}
		sb.append(" WHERE ");
		//sb.append(" o.externalId = ?1 ");
		sb.append(" o.externalId = :salesOrderId ");

		Query query = getEntityManager().createQuery(sb.toString());
		//query.setParameter(1, id);
		query.setParameter("salesOrderId", id);

		try {
			Object obj = query.getSingleResult();

			if (obj == null) {
				return null;
			}

			if (obj instanceof SalesOrder) {

				SalesOrder salesOrder = (SalesOrder) obj;

				for(LineItem li : salesOrder.getLineItems()){
					if (li != null && li.getSelectedFeatureValues() != null) {
						/*Set<SelectedFeatureValue> sfvSet = li
								.getSelectedFeatureValues();
						for (SelectedFeatureValue sfv : sfvSet) {
							sfv.getExternalId();
						}*/
						Hibernate.initialize(li.getSelectedFeatureValues());
					}
					if(li.getDialogues() != null){
						Hibernate.initialize(li.getDialogues());
					}
					if(li.getSelections() != null){
						Hibernate.initialize(li.getSelections());
					}
					if(li.getLineItemAttribute() != null){
						Hibernate.initialize(li.getLineItemAttribute());
					}
					if(li.getHistoricStatus() != null){
						Hibernate.initialize(li.getHistoricStatus());
					}
				}

				if (salesOrder.getHistoricStatus() != null) {
					/*List<StatusRecordBean> hsList = salesOrder
							.getHistoricStatus();
					for (StatusRecordBean hs : hsList) {
						hs.getId();
					}*/
					Hibernate.initialize(salesOrder.getHistoricStatus());
				}
				
				logger.info("includeAccountHolders : "+includeAccountHolders);//remove
				if(includeAccountHolders) {
					List<AccountHolder> savedAccountHolders = accountHolderDao
						.getAllAccountHoldersByOrderExternalId(id);
					salesOrder.setAccountHolders(savedAccountHolders);
				}
				logger.info("findById["+ id +"]  took : " + (System.currentTimeMillis() - stTime) +"ms");
				return salesOrder;
			}

		} catch (NoResultException nre) {
			logger.warn(nre.getMessage());
			return null;
		}

		throw new IllegalArgumentException("Invalid Query Result");
	}


	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public SalesOrder findByOrderId(final Long id) {
		if (id == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT o from SalesOrder o ");
		sb.append(" LEFT JOIN FETCH o.lineItems li ");
		sb.append(" LEFT JOIN FETCH o.historicStatus");
		// sb.append(" LEFT JOIN FETCH o.salesOrderContexts");
		//sb.append(" LEFT JOIN FETCH li.selectedFeatureValues ");
		sb.append(" LEFT JOIN FETCH li.dialogues");
		sb.append(" LEFT JOIN FETCH li.selections");
		sb.append(" LEFT JOIN FETCH li.lineItemAttribute");
		sb.append(" LEFT JOIN FETCH li.historicStatus");
		sb.append(" WHERE ");
		sb.append(" o.id = ?1 ");

		Query query = getEntityManager().createQuery(sb.toString());
		query.setParameter(1, id);

		try {
			Object obj = query.getSingleResult();

			if (obj == null) {
				return null;
			}

			if (obj instanceof SalesOrder) {
				SalesOrder salesOrder = (SalesOrder) obj;
				for(LineItem li : salesOrder.getLineItems()){
					if (li != null && li.getSelectedFeatureValues() != null) {
						Set<SelectedFeatureValue> sfvSet = li
								.getSelectedFeatureValues();
						for (SelectedFeatureValue sfv : sfvSet) {
							sfv.getExternalId();
						}
					}
				}
				touchSalesContext(salesOrder);

				return salesOrder;
			}

		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
			return null;
		}

		throw new IllegalArgumentException("Invalid Query Result");
	}

	private void touchSalesContext(SalesOrder so) {

		if ((so != null) && (so.getSalesOrderContexts() != null)) {
			for (SalesOrderContext soc : so.getSalesOrderContexts()) {
				soc.getId();

			}
		}
	}

	protected void saveLineItemStatus(final LineItem lineItemBean) {
		logger.info("saveLineItemStatus");
		long start = System.currentTimeMillis();
		StatusRecordBean statusRecord = lineItemBean.getCurrentStatus();

		if ((statusRecord != null) && (statusRecord.getId() != 0)) {
			getEntityManager().merge(statusRecord);
		} else {
			getEntityManager().persist(statusRecord);
		}
		logger.info("saveLineItemStatus took : " + (System.currentTimeMillis()-start) +"ms");
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean update(List<LineItem> lineItemBeans, boolean isStatusIncluded) {
		logger.info("update lineitems");
		long start = System.currentTimeMillis();
		if ((lineItemBeans != null) && (lineItemBeans.size() > 0)
				&& (getEntityManager() != null)) {
			try {

				for (LineItem lineItemBean : lineItemBeans) {

					if (lineItemBean != null) {

						if ((isStatusIncluded)
								&& (lineItemBean.getCurrentStatus() != null))
							statusDao.merge(lineItemBean.getCurrentStatus());
						if ((isStatusIncluded)
								&& (lineItemBean.getHistoricStatus() != null))
							statusDao.merge(lineItemBean.getHistoricStatus());

						if (lineItemBean.getLineItemDetailBean() != null)
							lineItemDetailDao.merge(lineItemBean
									.getLineItemDetailBean());
						if (lineItemBean.getSelectedFeatureValues() != null) {
							selectedFeatureValueDao.merge(lineItemBean
									.getSelectedFeatureValues());
							getEntityManager().flush();
						}
						getEntityManager().merge(lineItemBean);
					}
				}

				getEntityManager().flush();
				logger.info("update lineitems took : " + (System.currentTimeMillis()-start) +"ms");
				logger.info("Persisted updated lineitems");
				return Boolean.TRUE;
			} catch (Exception e) {
				logger.error("Exception thrown while update Lineitem", e);
				e.printStackTrace();
			}

		}

		return Boolean.FALSE;
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean update(List<LineItem> lineItemBeans) {

		return update(lineItemBeans, Boolean.TRUE);

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<SelectedFeatureValue> persistFeatures(
			final List<SelectedFeatureValue> featureValueBeanList) {
		logger.info("Persist features");
		long start=System.currentTimeMillis();
		List<SelectedFeatureValue> newFeatures = new ArrayList<SelectedFeatureValue>();
		if (featureValueBeanList != null && !featureValueBeanList.isEmpty()) {
			try {

				for (SelectedFeatureValue bean : featureValueBeanList) {
					if (bean != null) {

						getEntityManager().persist(bean);
						newFeatures.add(bean);
					}
				}
				getEntityManager().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Persist features took : " +(System.currentTimeMillis()-start)+"ms");
		return newFeatures;
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean updateLineItemStatus(final LineItem lineItem) {
		logger.info("updateLineItemStatus");
		long start = System.currentTimeMillis();
		if ((lineItem != null) && (getEntityManager() != null)) {
			try {
				updateLineItemStatus(lineItem.getCurrentStatus());
				lineItemAttributeDao.saveLineItemAttributes(lineItem.getLineItemAttribute());
				getEntityManager().merge(lineItem);
				getEntityManager().flush();
				logger.info("updateLineItemStatus took : " +(System.currentTimeMillis() - start) +"ms");
				return Boolean.TRUE;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return Boolean.FALSE;
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean updateLineItemStatusAndAttribute(final LineItem lineItem, Set<LineItemAttribute> liAttrubuteList) {
		logger.info("updateLineItemStatus");
		long start = System.currentTimeMillis();
		if (lineItem != null) {
			try {
				updateLineItemStatus(lineItem.getCurrentStatus());
				//save historic status
				for(StatusRecordBean statusRecordBean : lineItem.getHistoricStatus()) {
					updateLineItemStatus(statusRecordBean);
				}
				lineItemAttributeDao.saveLineItemAttributes(lineItem.getLineItemAttribute());
				//save lineItem attribute for other lineItems
				if(liAttrubuteList != null) {
					lineItemAttributeDao.saveLineItemAttributes(liAttrubuteList);
				}
				//Below line commeted for submit call api duplicate pk issue
				//getEntityManager().merge(lineItem);
				getEntityManager().flush();
				logger.info("updateLineItemStatus took : " +(System.currentTimeMillis() - start) +"ms");
				return Boolean.TRUE;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Boolean.FALSE;
	}
	
	protected void updateLineItemStatus(final StatusRecordBean statusRecord) {
		logger.info("updateLineItemStatus");
		long start = System.currentTimeMillis();
		if (statusRecord == null) {
			return;
		}
		if (statusRecord.getDateTimeStamp() == null) {
			statusRecord.setDateTimeStamp(Calendar.getInstance());
		}
		if (statusRecord.getId() != 0) {
			getEntityManager().merge(statusRecord);
		} else {
			getEntityManager().persist(statusRecord);
		}
		logger.info("updateLineItemStatus took : " +(System.currentTimeMillis() - start) +"ms");
	}

	protected void updateSalesOrderStatus(
			List<StatusRecordBean> statusRecordList) {
		logger.info("updateSalesOrderStatus");
		long start = System.currentTimeMillis();

		for (StatusRecordBean srb : statusRecordList) {
			updateSalesOrderStatus(srb);
		}
		logger.info("updateLineItemStatus took : " +(System.currentTimeMillis() - start) +"ms");
	}

	protected void updateSalesOrderStatus(final EntityManager em,
			final StatusRecordBean statusRecord) {
		if ((statusRecord != null) && (statusRecord.getId() != 0)) {
			em.merge(statusRecord);
		} else {
			em.persist(statusRecord);
		}
	}

	protected void updateSalesOrderStatus(StatusRecordBean statusRecord) {

		try {
			if ((statusRecord != null) && (statusRecord.getId() != 0)) {
				getEntityManager().merge(statusRecord);
			} else {
				getEntityManager().persist(statusRecord);
			}

		} catch (ConstraintViolationException constraint) {

			logger.info("status already present:" + constraint.getMessage());
		}
	}

	protected void updateSalesOrderStatus(final SalesOrder salesOrder) {
		updateSalesOrderCurrentStatus(getEntityManager(), salesOrder);
	}

	protected void updateSalesOrderCurrentStatus(final EntityManager em,
			final SalesOrder salesOrder) {

		logger.info("updateSalesOrderCurrentStatus");
		long start = System.currentTimeMillis();

		StatusRecordBean statusRecord = salesOrder.getCurrentStatus();

		try {
			if ((statusRecord != null) && (statusRecord.getId() != 0)) {
				em.merge(statusRecord);
			} else {
				em.persist(statusRecord);
			}
		} catch (ConstraintViolationException constraint) {

			logger.info("status already present:" + constraint.getMessage());
		}
		logger.info("updateSalesOrderCurrentStatus took : " +(System.currentTimeMillis()-start)+"ms");
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean updateOrderStatus(final SalesOrder salesOrder) {
		return updateOrderStatus(salesOrder, true);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean updateOrderStatus(final SalesOrder salesOrder, boolean updateLineItem) {
		logger.info("updateOrderStatus");
		long start = System.currentTimeMillis();
		if ((salesOrder != null) && (getEntityManager() != null)) {

			EntityManager em = getEntityManager();
			try {

				//////start update SalesOrderCurrentStatus
				try {
					StatusRecordBean statusRecord = salesOrder.getCurrentStatus();
					if ((statusRecord != null) && (statusRecord.getId() != 0)) {
						em.merge(statusRecord);
					} else {
						em.persist(statusRecord);
					}
				} catch (ConstraintViolationException constraint) {
					logger.info("status already present:" + constraint.getMessage());
				}
				////End update SalesOrderCurrentStatus
				////start update orderHistory status
				List<StatusRecordBean> historicStatus = salesOrder.getHistoricStatus();
				if ((historicStatus != null) && (historicStatus.size() > 0)) {
					for (StatusRecordBean srb : historicStatus) {
						try {
							if ((srb != null) && (srb.getId() != 0)) {
								em.merge(srb);
							} else {
								em.persist(srb);
							}
						} catch (ConstraintViolationException constraint) {
							logger.info("status already present:" + constraint.getMessage());
						}
					}
				}
				////end update orderHistory status
				// If line item update is needed
				if(updateLineItem){
					List<LineItem> lineItemBeans = salesOrder.getLineItems();
					for (LineItem lineItemBean : lineItemBeans) {
						if (lineItemBean.getPrice() == null) {
							LineItemPriceInfo priceInfo = new LineItemPriceInfo();
							priceInfo.setBaseNonRecurringPrice(0);
							priceInfo.setBaseRecurringPrice(0);
							lineItemBean.setPrice(priceInfo);
						}
						updateLineItemStatus(lineItemBean.getCurrentStatus());
						//save historic status
						for(StatusRecordBean statusRecordBean : lineItemBean.getHistoricStatus()) {
							updateLineItemStatus(statusRecordBean);
						}
						lineItemAttributeDao.saveLineItemAttributes(lineItemBean.getLineItemAttribute());
						em.merge(lineItemBean);
					}
				}
				if(null != salesOrder.getSalesOrderContexts()){
					for(SalesOrderContext salesOrderContext : salesOrder.getSalesOrderContexts()){
						if(salesOrderContext.getId() == 0)
							em.persist(salesOrderContext);
						else
							salesOrderContext = em.merge(salesOrderContext);
					}
				}
				em.merge(salesOrder);
				em.flush();
				logger.info("updateOrderStatus took : "+(System.currentTimeMillis()-start)+"ms");
				return Boolean.TRUE;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean updateOrderHistoryStatus(final EntityManager em,
			final SalesOrder salesOrder) {
		logger.info("updateOrderHistoryStatus");
		long start = System.currentTimeMillis();
		if ((salesOrder != null) && (em != null)) {

			List<StatusRecordBean> historicStatus = salesOrder
					.getHistoricStatus();

			if ((historicStatus != null) && (historicStatus.size() > 0)) {
				for (StatusRecordBean srb : historicStatus) {

					try {

						updateSalesOrderStatus(srb);
						logger.info("updateOrderHistoryStatus took : " +(System.currentTimeMillis()-start)+"ms");
						return Boolean.TRUE;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				em.flush();
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean updateOrderHistoryStatus(final SalesOrder salesOrder) {

		return updateOrderHistoryStatus(this.getEntityManager(), salesOrder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean update(final SalesOrder salesOrder) {
		logger.info("Persisting updated order");
		if ((salesOrder != null) && (getEntityManager() != null)) {
			try {

				StatusRecordBean statusRecord = salesOrder.getCurrentStatus();
				List<LineItem> lineItemBeans = salesOrder.getLineItems();

				List<StatusRecordBean> statusRecordList = salesOrder
						.getHistoricStatus();

				if (statusRecord != null) {
					getEntityManager().merge(statusRecord);
				}

				if (statusRecordList != null) {
					for (StatusRecordBean statusHistory : statusRecordList) {
						statusDao.merge(statusHistory);
					}
				}

				for (LineItem lineItemBean : lineItemBeans) {

					if (lineItemBean.getPrice() == null) {
						LineItemPriceInfo priceInfo = new LineItemPriceInfo();
						priceInfo.setBaseNonRecurringPrice(0);
						priceInfo.setBaseRecurringPrice(0);
						lineItemBean.setPrice(priceInfo);
					}

					statusDao.merge(lineItemBean.getCurrentStatus());
					statusDao.merge(lineItemBean.getHistoricStatus());
					lineItemAttributeDao.saveLineItemAttributes(lineItemBean.getLineItemAttribute());
					getEntityManager().merge(lineItemBean);
				}
				getEntityManager().flush();
				getEntityManager().merge(salesOrder);
				getEntityManager().flush();

				return Boolean.TRUE;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return Boolean.FALSE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean updateLW(final SalesOrder salesOrder) {
		logger.info("started updateLW");
		long start = System.currentTimeMillis();
		if ((salesOrder != null) && (getEntityManager() != null)) {
			EntityManager em = getEntityManager();
			try {
				for (LineItem lineItemBean : salesOrder.getLineItems()) {
					if (lineItemBean.getPrice() == null) {
						LineItemPriceInfo priceInfo = new LineItemPriceInfo();
						priceInfo.setBaseNonRecurringPrice(0);
						priceInfo.setBaseRecurringPrice(0);
						lineItemBean.setPrice(priceInfo);
					}
					em.merge(lineItemBean);
				}
				if(null != salesOrder.getSalesOrderContexts()){
					for(SalesOrderContext salesOrderContext : salesOrder.getSalesOrderContexts()){
						if(salesOrderContext.getId() == 0)
							em.persist(salesOrderContext);
						else
							salesOrderContext = em.merge(salesOrderContext);
					}
				}
				em.merge(salesOrder);
				em.flush();
				logger.info("updateLW took : "+(System.currentTimeMillis()-start)+"ms");
				return Boolean.TRUE;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean update(final LineItem lineItemBean) {
		if ((lineItemBean != null) && (getEntityManager() != null)) {
			try {
				if (lineItemBean.getCurrentStatus() != null)
					statusDao.merge(lineItemBean.getCurrentStatus());
				if (lineItemBean.getHistoricStatus() != null)
					statusDao.merge(lineItemBean.getHistoricStatus());
				if (lineItemBean.getLineItemDetailBean() != null)
					lineItemDetailDao.merge(lineItemBean
							.getLineItemDetailBean());
				if (lineItemBean.getSelectedFeatureValues() != null) {
					selectedFeatureValueDao.merge(lineItemBean
							.getSelectedFeatureValues());
				}
				getEntityManager().merge(lineItemBean);
				getEntityManager().flush();
				return Boolean.TRUE;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean updateLineItemAndStatus(final LineItem lineItem) {
		if (lineItem != null) {
			try {
				updateLineItemStatus(lineItem.getCurrentStatus());
				//save historic status
				for(StatusRecordBean statusRecordBean : lineItem.getHistoricStatus()) {
					updateLineItemStatus(statusRecordBean);
				}
				getEntityManager().merge(lineItem);
				getEntityManager().flush();
				return Boolean.TRUE;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean saveCurrentStatus(final StatusRecordBean statusRecord) {
		logger.info("saveCurrentStatus");
		long start = System.currentTimeMillis();
		if ((statusRecord != null) && (getEntityManager() != null)) {
			try {
				getEntityManager().persist(statusRecord);
				getEntityManager().flush();
				logger.info("saveCurrentStatus took : " + (System.currentTimeMillis()-start)+"ms");
				return Boolean.TRUE;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean update(final StatusRecordBean statusRecord) {
		logger.info("update");
		long start = System.currentTimeMillis();
		if ((statusRecord != null) && (getEntityManager() != null)) {

			try {

				getEntityManager().merge(statusRecord);

				getEntityManager().flush();
				logger.info("update took : " + (System.currentTimeMillis() - start) +"ms");
				return Boolean.TRUE;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return Boolean.FALSE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean save(final SalesOrder salesOrder, final Long custExtId) {
		logger.info("save");
		long start = System.currentTimeMillis();
		if (salesOrder != null) {
			try {

				List<LineItem> lineItemBeans = salesOrder.getLineItems();

//				 if(salesOrder.getSalesOrderContexts() != null) {
//				    	Set<SalesOrderContext> socSet = salesOrder.getSalesOrderContexts();
//				    	salesOrderContextDao.save(socSet);
//                    	          }

				StatusRecordBean statusRecord = salesOrder.getCurrentStatus();
				if (statusRecord != null) {
					statusDao.persist(statusRecord);
				}

				List<StatusRecordBean> statusRecordList = salesOrder
						.getHistoricStatus();
				if (statusRecordList != null) {
					for (StatusRecordBean statusHistory : statusRecordList) {
						statusDao.persist(statusHistory);
					}
				}

				for (LineItem lineItemBean : lineItemBeans) {

					if (lineItemBean.getPrice() == null) {
						LineItemPriceInfo priceInfo = new LineItemPriceInfo();
						priceInfo.setBaseNonRecurringPrice(0);
						priceInfo.setBaseRecurringPrice(0);
						lineItemBean.setPrice(priceInfo);
					}

					// This code will set installation fee to zero if client is
					// not providing any sch info
					// otherwise xmlbean will throw error of invalid big decimal
					// value
					if (lineItemBean.getLineitemScheduleInfo() == null) {
						LineitemScheduleInfo schInfo = new LineitemScheduleInfo();
						schInfo.setInstallationFee(BigDecimal.valueOf(0l));
						lineItemBean.setLineitemScheduleInfo(schInfo);
					} else if (lineItemBean.getLineitemScheduleInfo()
							.getInstallationFee() == null) {
						lineItemBean.getLineitemScheduleInfo()
								.setInstallationFee(BigDecimal.valueOf(0l));
					}

					if ((lineItemBean.getCurrentStatus() != null)
							&& (lineItemBean.getCurrentStatus().getId() == 0)) {
						statusDao.persist(lineItemBean.getCurrentStatus());
					}

					statusRecordList = lineItemBean.getHistoricStatus();
					if (statusRecordList != null) {
						for (StatusRecordBean statusHistory : statusRecordList) {

							if (statusHistory.getId() == 0) {
								statusDao.persist(statusHistory);
							}
						}
					}

					statusDao.persist(lineItemBean.getHistoricStatus());
					lineItemDetailDao.persist(lineItemBean
							.getLineItemDetailBean());
					selectedFeatureValueDao.persist(lineItemBean
							.getSelectedFeatureValues());
//					Set<SelectedDialogue> dlgSet = lineItemBean.getDialogues();
//					if (dlgSet != null) {
//					    for (SelectedDialogue dialogue : dlgSet) {
//						if (SecureDialogueProvider.getSecureDialogueList().contains(dialogue.getExternalId())) {
//						    EncryptStrategy.INSTANCE.encryptDialogue(dialogue);
//						}
//						logger.debug("Encrypted dialogue : " + dialogue.toString());
//					    }
//					}
					selectedDialogue.persist(lineItemBean.getDialogues());
					customSelectionDao.persist(lineItemBean.getSelections());
					if (lineItemBean.getLineItemAttribute() != null) {
						lineItemAttributeDao
								.saveLineItemAttributes(lineItemBean
										.getLineItemAttribute());
					}
					// setting system generated ExternalId for each Lineitem
					lineItemBean.setExternalId(lineItemDao.getNextExternalId());
					getEntityManager().persist(lineItemBean);
				}

				// adding system generated external id for SalesOrder
				salesOrder.setExternalId(getNextExternalId());

				salesOrder.setAConfirmNumber(String.valueOf(getNextExternalId()));
				getEntityManager().persist(salesOrder);
				getEntityManager().flush();
				logger.info("save order took : " + (System.currentTimeMillis()-start));
				logger.info("Saved sales order with external id : "
						+ salesOrder.getExternalId());
				return Boolean.TRUE;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return Boolean.FALSE;
	}

	private void syncExternalId(final SalesOrder salesOrder) {
		if ((salesOrder.getExternalId() == null)
				|| (salesOrder.getExternalId() == 0)) {
			salesOrder.setExternalId(salesOrder.getId());
			getEntityManager().persist(salesOrder);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Product findProductById(final String id) {
		logger.info("findProductById");
		long start = System.currentTimeMillis();
		if (id == null) {
			return null;
		}

		Product p  = catalogProductDao.findCatalogProductById(id.trim());
		logger.info("findProductById took : " + (System.currentTimeMillis() - start)+"ms");
		return p;
	}

	public BusinessParty findBusinessPartyById(final String externalId) {
		logger.info("findBysinessPartyById");
		long start = System.currentTimeMillis();
		if (externalId == null) {
			return null;
		}
		BusinessParty b= businessPartyDao.findBusinessPartyById(externalId.trim());
		logger.info("findBusinessPartyById took : " + (System.currentTimeMillis()-start)+"ms");
		return b;
	}

	/**
	 * {@inheritDoc}
	 */
	public User findAgentById(final String id) {
		return userDao.findUserByUserLogin(id);
	}

	public List<SalesOrder> findAllOrders(final EntityManager em, int offSet,
			int totalRows) {

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT o from SalesOrder o ");
		sb.append(" LEFT JOIN FETCH o.lineItems li ");
		sb.append(" LEFT JOIN FETCH o.historicStatus");
		sb.append(" LEFT JOIN FETCH li.selectedFeatureValues ");
		sb.append(" LEFT JOIN FETCH li.dialogues");
		sb.append(" LEFT JOIN FETCH li.selections");
		sb.append(" LEFT JOIN FETCH li.lineItemAttribute");
		sb.append(" LEFT JOIN FETCH li.historicStatus");
		sb.append(" ORDER BY o.id ");

		List<SalesOrder> orderList = null;
		Query q = em.createQuery(sb.toString());
		q.setFirstResult(offSet);
		q.setMaxResults(totalRows);
		orderList = q.getResultList();
		return orderList;
	}

	/*public Long getNextExternalId() {
	Query q = getEntityManager().createNativeQuery(NEXT_VAL);
	Object obj = q.getResultList().get(0);

	return DataTypeConverter.INSTANCE.objToLong(obj);
}*/

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
		  } 
		  finally {
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


	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public <T> T findAll(final Class<?> clazz) {
		if (getEntityManager() == null) {
			throw new IllegalArgumentException(
					"Entity Manager should not be NULL");
		}

		if (clazz == null) {
			throw new IllegalArgumentException(
					"ClassName is required should not be NULL");
		}

		Query query = getEntityManager().createQuery(
				"SELECT sob from " + clazz.getSimpleName() + " sob  ");

		try {
			return (T) query.getResultList();

		} catch (NoResultException nre) {

			logger.error(nre.getMessage());
			return null;
		}

	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public SelectedFeatureValueDao getSelectedFeatureValueDao() {
		return selectedFeatureValueDao;
	}

	public void setSelectedFeatureValueDao(
			SelectedFeatureValueDao selectedFeatureValueDao) {
		this.selectedFeatureValueDao = selectedFeatureValueDao;
	}

	public AddressDao getAddressDao() {
		return addressDao;
	}

	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

	public PhoneDao getPhoneDao() {
		return phoneDao;
	}

	public void setPhoneDao(PhoneDao phoneDao) {
		this.phoneDao = phoneDao;
	}

	public BillingDao getBillingDao() {
		return billingDao;
	}

	public void setBillingDao(BillingDao billingDao) {
		this.billingDao = billingDao;
	}

	public EmailDao getEmailDao() {
		return emailDao;
	}

	public void setEmailDao(EmailDao emailDao) {
		this.emailDao = emailDao;
	}

	public StatusDao getStatusDao() {
		return statusDao;
	}

	public void setStatusDao(StatusDao statusDao) {
		this.statusDao = statusDao;
	}

	public LineItemDetailDao getLineItemDetailDao() {
		return lineItemDetailDao;
	}

	public void setLineItemDetailDao(LineItemDetailDao lineItemDetailDao) {
		this.lineItemDetailDao = lineItemDetailDao;
	}

	public LineItemDao getLineItemDao() {
		return lineItemDao;
	}

	public void setLineItemDao(LineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}

	public BusinessPartyDao getBusinessPartyDao() {
		return businessPartyDao;
	}

	public void setBusinessPartyDao(BusinessPartyDao businessPartyDao) {
		this.businessPartyDao = businessPartyDao;
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public String getNextConfirmationNo() {
		/*Query q = getEntityManager().createNativeQuery(NEXT_VAL);
		Object obj = q.getResultList().get(0);

		Long nextVal = DataTypeConverter.INSTANCE.objToLong(obj);*/
		Long nextVal = getNextExternalId();

		String acNo = "CNF-";
		if (nextVal != null) {
			acNo = acNo + System.currentTimeMillis() + nextVal.longValue();
		}
		return acNo;
	}

	@Transactional
	public SalesOrder findOrderByLineItemExtId(Long orderExtId, Long liExtId) {
		return findOrderByLineItemExtId(orderExtId, liExtId, false);
	}
	/**
	 * This method will search order based on provided lineitem and order ext
	 * id. If order ext id is not provided then it will search based only on
	 * lineitem ext id.
	 *
	 *
	 * TODO this method needs to be revisited as query used is not filtering
	 * child entities and that is why manual filtration is added.
	 */
	@Transactional
	public SalesOrder findOrderByLineItemExtId(Long orderExtId, Long liExtId, boolean includeAccountHolders) {
		logger.info("Inside modified Searching Order by Order ext Id (" + orderExtId
				+ ") and lineitem ext id(" + liExtId + ")");
		long start = System.currentTimeMillis();
		if (orderExtId == null || liExtId == null) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT o from SalesOrder o ");
		sb.append(" LEFT JOIN FETCH o.lineItems li ");
		//sb.append(" LEFT JOIN FETCH o.historicStatus");
		//sb.append(" LEFT JOIN FETCH li.selectedFeatureValues ");
		/*sb.append(" LEFT JOIN FETCH li.dialogues");
		sb.append(" LEFT JOIN FETCH li.selections");
		sb.append(" LEFT JOIN FETCH li.lineItemAttribute");
		sb.append(" LEFT JOIN FETCH li.historicStatus");*/
		if(includeAccountHolders) {
			logger.debug("Adding left join with lineItem payment events");
			sb.append(" LEFT JOIN FETCH li.paymentEvents");
		}
		sb.append(" WHERE ");

		Query query = null;
		if (orderExtId > 0) {

			sb.append(" o.externalId=  :orderExtId ");
			sb.append(" AND ");
			sb.append(" li.externalId = :liExtId  ");

			query = getEntityManager().createQuery(sb.toString());
			query.setParameter("orderExtId", orderExtId);
			query.setParameter("liExtId", liExtId);
		} else {
			sb.append(" li.externalId = :liExtId  ");

			query = getEntityManager().createQuery(sb.toString());
			query.setParameter("liExtId", liExtId);
		}

		try {
			Object obj = query.getSingleResult();

			if (obj == null) {
				return null;
			}

			if (obj instanceof SalesOrder) {

				SalesOrder salesOrder = (SalesOrder) obj;
				touchSalesContext(salesOrder);
				salesOrder = filterLineItems(salesOrder, liExtId);
				
				for(LineItem li : salesOrder.getLineItems()){
					
					if (li != null){ 
						if(li.getSelectedFeatureValues() != null) {
							/*Set<SelectedFeatureValue> sfvSet = li.getSelectedFeatureValues();
							for (SelectedFeatureValue sfv : sfvSet) {
								sfv.getExternalId();
							}*/
							Hibernate.initialize(li.getSelectedFeatureValues());
						}
						if(li.getDialogues() != null){
							Hibernate.initialize(li.getDialogues());
						}
						if(li.getSelections() != null){
							Hibernate.initialize(li.getSelections());
						}
						if(li.getLineItemAttribute() != null){
							Hibernate.initialize(li.getLineItemAttribute());
						}
						if(li.getHistoricStatus() != null){
							Hibernate.initialize(li.getHistoricStatus());
						}
					}
				}
				if(salesOrder.getHistoricStatus() != null){
					Hibernate.initialize(salesOrder.getHistoricStatus());
				}
				
				if(includeAccountHolders) {
					List<AccountHolder> savedAccountHolders = accountHolderDao
						.getAllAccountHoldersByOrderExternalId(orderExtId);
					salesOrder.setAccountHolders(savedAccountHolders);
				}
				
				logger.info("No of lineitem after filtration : "
						+ salesOrder.getLineItems().size());
				logger.info("findOrderByLineItemExtId took : " + (System.currentTimeMillis()-start)+"ms");
				return salesOrder;
			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
			logger.error("Exception thrown by findOrderByLineItemExtId :", nre);
			return null;
		}

		throw new IllegalArgumentException("Invalid Query Result");
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public SalesOrder findOrderByServiceSelectionId(String searchBy, Long liExtId) {
		return findOrderByServiceSelectionId(searchBy, liExtId, false);
	}
	/**
	 * This method will search order based on provided lineitem Service Selection Id.
	 *
	 *
	 * TODO this method needs to be revisited as query used is not filtering
	 * child entities and that is why manual filtration is added.
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public SalesOrder findOrderByServiceSelectionId(String searchBy, Long liExtId, boolean includeAccountHolders) {
		logger.info("Searching Order by lineitem ext id(" + liExtId + ")");
		long start = System.currentTimeMillis();
		if (liExtId == null) {
			return null;
		}

		Query q = getEntityManager().createQuery("SELECT o FROM VAccordOrderMapping o WHERE o.id.accordOrderId= :accordOrderId");
	    q.setParameter("accordOrderId", liExtId);

		try {
			List<VAccordOrderMapping> obj = (List<VAccordOrderMapping>) q.getResultList();

			logger.info("findServiceSelectionId via VAccordOrderMapping took : " + (System.currentTimeMillis()-start)+"ms");

			logger.info("Searching lineitem based on ServiceSelId : Size (" + obj.size() + ")");

			SalesOrder salesOrder = null;

			if (obj == null) {
				return null;
			}

			if (obj.size() > 0) {

				for (VAccordOrderMapping vaom: obj)
				{
					if (vaom != null)
					{
						logger.info("Searching lineitem based on ServiceSelId : vaom.toString(): " + vaom.toString());

						salesOrder = findOrderByLineItemExtId(0L, vaom.getAccordOrderLICompositeKey().getLiExtId(), includeAccountHolders);

						if (salesOrder != null)
						{
							logger.info("findOrderByServiceSelectionId: salesOrder.getExternalId(): " + salesOrder.getExternalId());
							break;
						}
					}
				}
				logger.info("findOrderByServiceSelectionId took : " + (System.currentTimeMillis()-start)+"ms");

				return salesOrder;
			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
			logger.error("Exception thrown by findOrderByServiceSelectionId :", nre);
			return null;
		}

		throw new IllegalArgumentException("Invalid Query Result");
	}

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void saveAccordOrderMapping(SalesOrder salesOrder) {
	    logger.info("Saving accord V order mapping");
	    long start = System.currentTimeMillis();
	    Map<Long,Long> liMapping = getAccordIdForLineItem(salesOrder);
	    if(liMapping != null) {
		Iterator<Long> iter = liMapping.keySet().iterator();
		while(iter.hasNext()) {
		    Long liExtId = iter.next();
		    Long accordId = liMapping.get(liExtId);
		    logger.info("Checking mapping exist or not for lineitem[" + liExtId +"] and AccordId[" + accordId +"]");
		    String sql = "SELECT count(*) FROM om_V_accord_order_mapping ovaom WHERE ovaom.li_ext_id=" +liExtId+" AND ovaom.accord_order_id=" +accordId;
		    Query q = getEntityManager().createNativeQuery(sql);
		    BigInteger i = (BigInteger)q.getSingleResult();
		    logger.info("Total record found for mapping : " + i);
		    if(i.intValue() <= 0) {
		    	String insert = "INSERT INTO om_V_accord_order_mapping(order_ext_id,li_ext_id,accord_order_id) VALUES(?,?,?)";
		    	Query insertQ = getEntityManager().createNativeQuery(insert);
		    	insertQ.setParameter(1, salesOrder.getExternalId());
		    	insertQ.setParameter(2, liExtId);
		    	insertQ.setParameter(3, accordId);
		    	int j = insertQ.executeUpdate();
		    	logger.info("Saved accord order mapping ");
		    	getEntityManager().flush();
		    }
		}
	    }
	    logger.info("saveAccordOrderMapping took : " + (System.currentTimeMillis() - start) + "ms");
	}

	@Transactional
	private Map<Long, Long> getAccordIdForLineItem(SalesOrder salesOrder) {
	    logger.info("Finding accord order id from lineitem attributes");
	    Map<Long,Long> liAccordMap = new HashMap<Long,Long>();
	    if(salesOrder != null && salesOrder.getLineItems() != null) {
		List<LineItem> liList = salesOrder.getLineItems();
		for(LineItem li : liList) {
		    if(li != null && li.getLineItemDetail() != null ) {
			if(li.getLineItemDetail().getType().equalsIgnoreCase(PRODUCT)) {
			    Long liExtId = li.getExternalId();
			    if(li.getLineItemAttribute() != null) {
				Set<LineItemAttribute> attrSet = li.getLineItemAttribute();
				for(LineItemAttribute attrb : attrSet) {
				    if(attrb.getSource().equalsIgnoreCase(DW)) {
					if(attrb.getName().equalsIgnoreCase(SRVC_SLCTN_ID)) {
					    if(NumberUtils.isNumber(attrb.getValue().trim())) {
						Long value =Long.valueOf(attrb.getValue());
						logger.debug("Found accord order id["+ value +"] for lineitem["+liExtId+"]");
						liAccordMap.put(liExtId, value);
						break;
					    }
					}
				    }
				}
			    }
			}
		    }
		}
	    }
	    return liAccordMap;
	}

	public List<SalesOrder> findOrderByStatusReason(final String status,
			final String reason, final int offset, final int totalRows) {
		logger.debug("Searching Order by reason code and status  ");
		long start = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT o from SalesOrder o ");
		sb.append(" INNER JOIN o.lineItems li ");
		sb.append(" WHERE ");
		sb.append(" :status = li.currentStatus.status ");
		sb.append(" AND ");
		sb.append(" :reason in elements(li.currentStatus.reasons) ");
		sb.append(" ORDER BY o.id ");

		Query query = getEntityManager().createQuery(sb.toString());
		query.setParameter("status", status);
		query.setParameter("reason", reason);

		try {
			Object obj = query.getResultList();

			if (obj == null) {
				return null;
			}

			if (obj instanceof List) {

				List<SalesOrder> salesOrderList = (List<SalesOrder>) obj;
				logger.info("findOrderByStatusReason took : " + (System.currentTimeMillis()-start)+"ms");
				return salesOrderList;
			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
			logger.error("Exception thrown by findOrderByStatusReason :", nre);
			return null;
		}

		throw new IllegalArgumentException("Invalid Query Result");
	}

	public static void main(String[] arg) {

		List<String> statusList = new ArrayList<String>();
		statusList.add("1");
		statusList.add("2");
		statusList.add("3");
		statusList.add("4");

		List<String> convertedStatusList = new ArrayList<String>();

		for (String convert : statusList) {
			if ((convert.indexOf("'")) == -1) {
				convert = "'" + convert + "'";
			}
			convertedStatusList.add(convert);
		}

		List<String> reasonList = new ArrayList<String>();
		reasonList.add("A");
		reasonList.add("B");
		reasonList.add("C");
		reasonList.add("D");

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT o from SalesOrder o ");
		sb.append(" INNER JOIN o.lineItems li ");
		sb.append(" WHERE ");
		sb.append(CriteriaBuilder.INSTANCE.buildCriteria(convertedStatusList,
				" = li.currentStatus.status", "or"));
		sb.append(" AND  ");
		sb.append(CriteriaBuilder.INSTANCE.buildCriteria(reasonList,
				" in elements(li.currentStatus.reasons)", "or"));
		sb.append(" ORDER BY o.id ");

	}

	/**
	 * This method will return sales order with provided provider external id.
	 *
	 */
	public List<SalesOrder> findOrderByStatusReason(
			final List<String> statusList, final List<String> reasonList,
			int offset, int totalRows) {
		logger.info("Searching Order by reason code and status  ");
		long start = System.currentTimeMillis();
		if (reasonList == null || statusList == null || statusList.size() == 0
				|| reasonList.size() == 0) {
			return EMPTY_SALES_ORDER_LIST;
		}

		List<String> convertedStatusList = new ArrayList<String>();

		for (String convert : statusList) {
			if ((convert.indexOf("'")) == -1) {
				convert = "'" + convert + "'";
			}
			convertedStatusList.add(convert);
		}

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT o from SalesOrder o ");
		sb.append(" INNER JOIN o.lineItems li ");
		sb.append(" WHERE ");
		sb.append(CriteriaBuilder.INSTANCE.buildCriteria(convertedStatusList,
				" = li.currentStatus.status", "or"));
		sb.append(" AND  ");
		sb.append(CriteriaBuilder.INSTANCE.buildCriteria(reasonList,
				" in elements(li.currentStatus.reasons)", "or"));
		sb.append(" ORDER BY o.id ");

		Query query = getEntityManager().createQuery(sb.toString());

		try {
			query.setFirstResult(offset);
			query.setMaxResults(totalRows);
			Object obj = query.getResultList();

			if (obj == null) {
				return null;
			}

			if (obj instanceof List) {

				List<SalesOrder> salesOrderList = (List<SalesOrder>) obj;
				logger.info("findOrderByStatusAndReason took : " +(System.currentTimeMillis()-start)+"ms");
				return salesOrderList;
			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
			logger.error("Exception thrown by findOrderByStatusReason :", nre);
			return null;
		}

		throw new IllegalArgumentException("Invalid Query Result");
	}

	/**
	 * This method will return sales order with provided provider external id.
	 *
	 */
	@Transactional
	public SalesOrder findOrderByProviderExtId(Long orderExtId,
			Long providerExtId) {
		logger.info("Searching Order by Order ext Id (" + orderExtId
				+ ") and provider ext id(" + providerExtId + ")");
		long start = System.currentTimeMillis();
		if (orderExtId == null || providerExtId == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT o from SalesOrder o ");
		sb.append(" LEFT JOIN FETCH o.lineItems li ");
		sb.append(" LEFT JOIN FETCH o.historicStatus");
		//sb.append(" LEFT JOIN FETCH o.salesOrderContexts");
		//sb.append(" LEFT JOIN FETCH li.selectedFeatureValues ");
		sb.append(" LEFT JOIN FETCH li.dialogues");
		sb.append(" LEFT JOIN FETCH li.selections");
		sb.append(" LEFT JOIN FETCH li.lineItemAttribute");
		sb.append(" LEFT JOIN FETCH li.historicStatus");
		sb.append(" WHERE ");
		//sb.append(" o.externalId = ?1 ");
		sb.append(" o.externalId = :salesOrderId ");

		Query query = getEntityManager().createQuery(sb.toString());
		//query.setParameter(1, orderExtId);
		query.setParameter("salesOrderId", orderExtId);
		try {
			Object obj = query.getSingleResult();

			if (obj == null) {
				return null;
			}

			if (obj instanceof SalesOrder) {

				SalesOrder salesOrder = (SalesOrder) obj;
				touchSalesContext(salesOrder);
				salesOrder = filterLineItemsByProvider(salesOrder,
						providerExtId);
				for(LineItem li : salesOrder.getLineItems()){
					if (li != null && li.getSelectedFeatureValues() != null) {
						Set<SelectedFeatureValue> sfvSet = li
								.getSelectedFeatureValues();
						for (SelectedFeatureValue sfv : sfvSet) {
							sfv.getExternalId();
						}
					}
				}
				logger.info("No of lineitem after filtration : "
						+ salesOrder.getLineItems().size());
				logger.info("findOrderByProviderExtId took : " + (System.currentTimeMillis()-start)+"ms");
				return salesOrder;
			}

		} catch (NoResultException nre) {
			nre.printStackTrace();
			logger.error("Exception thrown by findOrderByProviderExtId :", nre);
			return null;
		}

		throw new IllegalArgumentException("Invalid Query Result");
	}

	/**
	 * Helper method to filter lineitems based on provided lineitem external id
	 *
	 * @param salesOrder
	 * @param liExtId
	 * @return
	 */
	private SalesOrder filterLineItems(SalesOrder salesOrder, Long liExtId) {
		List<LineItem> liList = salesOrder.getLineItems();
		Iterator<LineItem> iterator = liList.iterator();
		while (iterator.hasNext()) {
			LineItem li = iterator.next();
			if (li != null) {
				if (li.getExternalId().longValue() != liExtId.longValue()) {
					iterator.remove();
				}
			} else {
				// remove null entity from list
				iterator.remove();
			}
		}
		return salesOrder;
	}

	/**
	 * Helper method to filter lineitems based on provider external id. For
	 * promotion it will retrieve applies to and check if the applies lineitem's
	 * provider id is same as requested provider id. If it is then that
	 * promotion will be included in response other wise removed.
	 *
	 * @param salesOrder
	 * @param providerExtId
	 * @return
	 */
	private SalesOrder filterLineItemsByProvider(SalesOrder salesOrder,
			Long providerExtId) {
		List<LineItem> liList = salesOrder.getLineItems();
		List<LineItem> tmpList = new ArrayList<LineItem>();
		tmpList.addAll(liList);
		Iterator<LineItem> iterator = liList.iterator();

		while (iterator.hasNext()) {
			LineItem li = iterator.next();
			if (li.getLineItemDetail().getType().equalsIgnoreCase(PRODUCT)
					&& !li.getProviderExternalId().equals(
							String.valueOf(providerExtId))) {
				iterator.remove();
			} else if (li.getLineItemDetail().getType()
					.equalsIgnoreCase(PRODUCT_PROMOTION)) {
				// Get the applies to and split it
				String[] appliesToArray = li.getLineItemDetail().getAppliesTo()
						.split(",");
				Boolean isPromoApliesToLineItemForProviderExist = Boolean.FALSE;
				for (String appliesTo : appliesToArray) {
					for (LineItem tmpLi : tmpList) {
						// Check applies to lineitem's provider id is same as
						// requested provider id, if it is same then set the
						// boolean value to TRUE
						if (tmpLi.getLineItemDetail().getType()
								.equalsIgnoreCase(PRODUCT)) {
							if (tmpLi.getLineItemNumber() == Integer
									.parseInt(appliesTo)
									&& tmpLi.getProviderExternalId().equals(
											String.valueOf(providerExtId))) {
								isPromoApliesToLineItemForProviderExist = Boolean.TRUE;
							}
						}
					}
				}
				// If applies to lineitem's provider id does not match to
				// requested provider id then remove that promotion
				if (!isPromoApliesToLineItemForProviderExist) {
					iterator.remove();
				}
			}
		}
		return salesOrder;
	}
	
	@Transactional
	public List<SalesOrder> findByIds(String externalId) {
		return findByIds(externalId, false);
	}

	@Transactional
	public List<SalesOrder> findByIds(String externalId, boolean includeAccountHolders) {

		logger.info("findByIds : " + externalId);
		long start = System.currentTimeMillis();
		if (externalId == null) {
			return null;
		}
		logger.info("Searching for order external ids : " + externalId);
		/*
		 * StringBuilder sb = new StringBuilder();
		 * sb.append(" SELECT o from SalesOrder o ");
		 * sb.append(" LEFT JOIN FETCH o.lineItems li ");
		 * sb.append(" LEFT JOIN FETCH o.historicStatus");
		 * sb.append(" LEFT JOIN FETCH li.selectedFeatureValues ");
		 * sb.append(" LEFT JOIN FETCH li.dialogues");
		 * sb.append(" LEFT JOIN FETCH li.selections");
		 * sb.append(" LEFT JOIN FETCH li.lineItemAttribute");
		 * sb.append(" LEFT JOIN FETCH li.historicStatus");
		 * sb.append(" WHERE "); sb.append(" o.externalId in ( "+ externalId +
		 * " )");
		 */
		// Query query = getEntityManager().createQuery(sb.toString());

		long stTime = System.currentTimeMillis();
		Query query = getEntityManager().createQuery(
				"SELECT sob from SalesOrder sob where sob.externalId in ("
						+ externalId + " ) order by id");

		List<SalesOrder> sobList = new ArrayList<SalesOrder>();

		try {
			List<SalesOrder> orderList = query.getResultList();

			if (orderList == null) {
				return null;
			}
			for (Object obj : orderList) {
				if (obj instanceof SalesOrder) {
					SalesOrder salesOrder = (SalesOrder) obj;
					List<LineItem> liList = salesOrder.getLineItems();
					if (liList != null) {
						for (LineItem li : liList) {
							Set<SelectedFeatureValue> featuresList = li
									.getSelectedFeatureValues();
							if (featuresList != null) {
								for (SelectedFeatureValue feature : featuresList) {
									feature.getId();
								}
							}
							Set<CustomSelection> custSelList = li
									.getSelections();
							if (custSelList != null) {
								for (CustomSelection sel : custSelList) {
									sel.getId();
								}
							}
							Set<SelectedDialogue> dlgList = li.getDialogues();
							if (dlgList != null) {
								for (SelectedDialogue dlg : dlgList) {
									dlg.getId();
								}
							}
							Set<LineItemAttribute> attrbList = li
									.getLineItemAttribute();
							if (attrbList != null) {
								for (LineItemAttribute attrb : attrbList) {
									attrb.getId();
								}
							}
							List<StatusRecordBean> statList = li
									.getHistoricStatus();
							if (statList != null) {
								for (StatusRecordBean stat : statList) {
									stat.getId();
								}
							}
							Set<CustomerPaymentEvent> paymentEvents = li.getPaymentEvents();
							if(paymentEvents != null) {
								for(CustomerPaymentEvent paymentEvent : paymentEvents) {
									paymentEvent.getId();
								}
							}
						}
					}
					
					if(includeAccountHolders) {
						if(includeAccountHolders) {
							List<AccountHolder> savedAccountHolders = accountHolderDao
								.getAllAccountHoldersByOrderExternalId(salesOrder.getExternalId());
							salesOrder.setAccountHolders(savedAccountHolders);
						}
					}
					
					List<StatusRecordBean> hsList = salesOrder
							.getHistoricStatus();
					for (StatusRecordBean hs : hsList) {
						hs.getId();
					}
					sobList.add(salesOrder);
				}
			}
		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
			return null;
		}
		logger.info("findByIds took : " + (System.currentTimeMillis() - stTime) +"ms");
		return sobList;
	}
	
	@Transactional
	public SalesOrder findOrderByLineItemExtId(final Long lineItemExtId) {
		return findOrderByLineItemExtId(lineItemExtId, false);
	}
	/**
	 * It retrieves the whole order with its associated line items by taking 
	 * Line Item External Id as its parameter, returns NULL when the provided 
	 * line item external is not valid or not found<br>
	 * <b><u>Revision History</u></b><br>
	 * New element added for Encore search ticket #114887
	 * 
	 * @param lineItemExtId
	 * @return SalesOrder
	 */
	@Transactional
	public SalesOrder findOrderByLineItemExtId(final Long lineItemExtId, boolean includeAccountHolders) {
		String sql = "select oso.external_id as externalId from om_sales_order oso " 
						+ "left outer join om_sales_order_om_line_item osooli on oso.id=osooli.om_sales_order_id "
						+ "left outer join om_line_item oli on osooli.lineItems_id=oli.id "
						+ "where oli.external_id="+lineItemExtId;
		
		Query q = getEntityManager().createNativeQuery(sql);
		BigInteger orderExtId = (BigInteger) q.getSingleResult();
		SalesOrder salesOrder = null;
		if(orderExtId != null && orderExtId.intValue() > 0){
			salesOrder = findById(Long.valueOf(orderExtId.longValue()), includeAccountHolders);
		} 
		return salesOrder;
    }
	

	/* (non-Javadoc)
	 * @see com.A.Vdao.dao.OrderManagementDao#updateSalesOrderForStatusUpdate(com.A.V.beans.entity.SalesOrder)
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean updateSalesOrderForStatusUpdate(SalesOrder salesOrder){
		
		logger.info("start updateSalesOrderForStatusUpdate");
		long startTime = System.currentTimeMillis();
		
		try{
			EntityManager entityManager = getEntityManager();
			StatusRecordBean statusRecord = salesOrder.getCurrentStatus();
			List<StatusRecordBean> statusRecordList = salesOrder.getHistoricStatus();

			if (statusRecord != null) {
				if(statusRecord.getId() > 0){
					statusRecord = entityManager.merge(statusRecord);
				}
				else{
					entityManager.persist(statusRecord);
				}
			}
			if (statusRecordList != null) {
				for (StatusRecordBean statusHistory : statusRecordList) {
					if(statusHistory.getId() == 0)
						entityManager.persist(statusHistory);
					else
						statusHistory = entityManager.merge(statusHistory);
				}
			}
			for (LineItem lineItemBean : salesOrder.getLineItems()) {

				if (lineItemBean.getPrice() == null) {
					LineItemPriceInfo priceInfo = new LineItemPriceInfo();
					priceInfo.setBaseNonRecurringPrice(0);
					priceInfo.setBaseRecurringPrice(0);
					lineItemBean.setPrice(priceInfo);
				}

				StatusRecordBean liStatusRecordBean= lineItemBean.getCurrentStatus();
				if(liStatusRecordBean.getId() > 0){
					liStatusRecordBean = entityManager.merge(liStatusRecordBean);
				}
				else{
					entityManager.persist(liStatusRecordBean);
				}
				if(null != lineItemBean.getHistoricStatus()){
					for(StatusRecordBean orderHistStatus : lineItemBean.getHistoricStatus()){
						if(null == orderHistStatus.getDateTimeStamp())
							orderHistStatus.setDateTimeStamp(Calendar.getInstance());
						orderHistStatus = entityManager.merge(orderHistStatus);
					}
				}
			
				if(null != lineItemBean.getLineItemAttribute()){
					for (LineItemAttribute liAttrib : lineItemBean.getLineItemAttribute()) {
						if (liAttrib.getId() == 0) {
						    getEntityManager().persist(liAttrib);
						}
						else {
							liAttrib = getEntityManager().merge(liAttrib);
						}
				    }
				}
				lineItemBean = entityManager.merge(lineItemBean);
			}
			
			if(null != salesOrder.getSalesOrderContexts()){
				for(SalesOrderContext salesOrderContext : salesOrder.getSalesOrderContexts()){
					if(salesOrderContext.getId() == 0)
						entityManager.persist(salesOrderContext);
					else
						salesOrderContext = entityManager.merge(salesOrderContext);
				}
			}
			salesOrder = entityManager.merge(salesOrder);
			entityManager.flush();
			
			logger.info("updateSalesOrderForStatusUpdate completed in "+(System.currentTimeMillis() - startTime)+" ms");
			return Boolean.TRUE;
		}
		catch(Exception rte){
			
			logger.error(rte.getMessage());
			rte.printStackTrace();
		}
		return Boolean.FALSE;
	}
	
	/* (non-Javadoc)
	 * @see com.A.Vdao.dao.OrderManagementDao#updateSalesOrder(com.A.V.beans.entity.SalesOrder)
	 */
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Boolean updateSalesOrder(final SalesOrder salesOrder) {
		logger.info("Persisting updated order from AddLineItem call");
		if ((salesOrder != null) && (getEntityManager() != null)) {
			try {

				StatusRecordBean statusRecord = salesOrder.getCurrentStatus();
				if (statusRecord != null) {
					getEntityManager().merge(statusRecord);
				}

				List<StatusRecordBean> statusRecordList = salesOrder.getHistoricStatus();
				if (statusRecordList != null) {
					for (StatusRecordBean statusHistory : statusRecordList) {
						statusDao.merge(statusHistory);
					}
				}
				
				getEntityManager().flush();
				getEntityManager().merge(salesOrder);
				getEntityManager().flush();

				return Boolean.TRUE;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return Boolean.FALSE;
	}

}
