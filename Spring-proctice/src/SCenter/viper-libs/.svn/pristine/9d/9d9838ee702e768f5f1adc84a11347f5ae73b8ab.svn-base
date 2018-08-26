package com.A.Vdao.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import com.A.V.beans.entity.BusinessParty;
import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.Product;
import com.A.V.beans.entity.SalesOrder;
import com.A.V.beans.entity.SelectedFeatureValue;
import com.A.V.beans.entity.StatusRecordBean;
import com.A.V.beans.entity.User;
import com.A.V.beans.entity.LineItemAttribute;

/**
 * @author ethomas
 *
 */
public interface OrderManagementDao {

	EntityManager getEntityManager();

	void setEntityManager(EntityManager entityManager);

	public Long getNextExternalId();

	public   List<SelectedFeatureValue> persistFeatures( List<SelectedFeatureValue> featureValueBeanList );

	Boolean updateOrderStatus(final SalesOrder salesOrder);

	Boolean updateLineItemStatus(final LineItem lineItemBean);

	Boolean update(final LineItem lineItemBean);

	Boolean update(List<LineItem> lineItemBeans);

	Boolean update(List<LineItem> lineItemBeans, boolean isStatusIncluded);

	Boolean update(final StatusRecordBean statusRecord);

	public Boolean saveCurrentStatus( final StatusRecordBean statusRecord );
	/**
	 * @param em
	 *            Entity Manager that will be used for JPA operations
	 * @param salesOrder
	 *            new SalesOrder that will be persisted
	 * @return boolean that specifies the success of the operation
	 */
	 public Boolean save( final SalesOrder salesOrder, final Long custExtId );

	/**
	 * @param em
	 *            Entity Manager that will be used for JPA operations
	 * @param salesOrder
	 *            existing SalesOrder that will be updated
	 * @return boolean that specifies the success of the operation
	 */
	Boolean update(final SalesOrder salesOrder);
	
	Boolean updateLW(final SalesOrder salesOrder);
	
	/**
	 * @param em
	 *            Entity Manager
	 * @param externalId
	 *            id that uniquely identified the SalesOrder
	 * @return SalesOrder identified by externalId
	 */
	SalesOrder findById(final Long externalId);
	
	SalesOrder findById(final Long id, boolean includeAccountHolders) ;

    SalesOrder findBasicSalesOrderById(final Long id);
    
    SalesOrder findBasicSalesOrderById(final Long id, boolean includeAccountHolders);

	List<SalesOrder> findByIds(final String externalId);
	
	List<SalesOrder> findByIds(final String externalId, boolean includeAccountHolders);

	SalesOrder findByOrderId(final Long id);

	SalesOrder findByConfirmationNumber(final String externalId);

	List<SalesOrder> findByCustomer(final Long externalId, int offSet, int totalRows);

	List<SalesOrder> findByDate(final Date moveDate,int offSet, int totalRows);

	List<SalesOrder> findByScheduleDate(final Date scheduleDate, final String status, final String reason, int offSet,
			int totalRows);

	List<SalesOrder> findOrderByStatusReason(final List<String> statusList, final List<String> reasonList, int offSet, int totalRows);

	List<SalesOrder> findOrderByStatusReason(final String status, final String reason, int offSet, int totalRows);

	/**
	 * @param <T>
	 *            class as member of return type
	 * @param em
	 *            Entity Manager
	 * @param clazz
	 *            class
	 * @return List
	 */
	<T> T findAll(final Class<?> clazz);

	/**
	 * @param salesOrderBean
	 *            salesOrderBean that will be removed
	 * @param em
	 *            Entity Manager
	 * @return SalesOrder identified by externalId
	 */
	SalesOrder remove(final SalesOrder salesOrderBean);

	/**
	 * @param externalId
	 *            id of the SalesOrder that will be removed
	 * @param em
	 *            Entity Manager
	 * @return SalesOrder identified by externalId
	 */
	SalesOrder removeSalesOrderById(final String externalId);

	/**
	 * @param em
	 *            Entity Manager
	 * @param externalId
	 *            existing MarketItem that will be located by externalId
	 * @return SalesOrder removed by externalId
	 */
	//MarketItemBean findMarketItemById(final String externalId);
	 Product findProductById(final String id);
	/**
	 * @param em
	 *            Entity Manager
	 * @param externalId
	 *            existing MarketItem that will be located by externalId
	 * @return MarketItemBean identified by externalId
	 */
	User findAgentById(final String externalId);

	/**
	 * @param em
	 *            Entity Manager
	 * @param externalId
	 *            existing MarketItem that will be located by externalId
	 * @return MarketItemBean identified by externalId
	 */
	BusinessParty findBusinessPartyById(final String externalId);

	SalesOrder findOrderByLineItemExtId(final Long orderExtId, final Long liExtId);
	SalesOrder findOrderByLineItemExtId(final Long orderExtId, final Long liExtId, boolean includeAccountHolders);
	SalesOrder findOrderByServiceSelectionId(final String searchBy, final Long liExtId);
	SalesOrder findOrderByServiceSelectionId(final String searchBy, final Long liExtId, boolean inlcludeAccountHolders);
	SalesOrder findOrderByProviderExtId(final Long orderExtId, final Long providerExtId);

	void saveAccordOrderMapping(SalesOrder salesOrder);
	
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
	SalesOrder findOrderByLineItemExtId(final Long lineItemExtId);
	
	SalesOrder findOrderByLineItemExtId(final Long lineItemExtId, boolean includeAccountHolders);

	
	/**
	 * Persists sales order object and its associated changed objects for Update Line item status API</br>
	 * @param salesOrder
	 * @return Boolean
	 */
	Boolean updateSalesOrderForStatusUpdate(SalesOrder salesOrder);
	
	/**
	 * Added a new method to persist updated order for AddLineItem API call. It just saves
	 * <br><b>Sales Order Current Status & Status History</b>
	 * @param salesOrder
	 * @return Boolean
	 */
	Boolean updateSalesOrder(final SalesOrder salesOrder);
	
	/*
	 * Added a new method to persist current lineItem status, historic status and
	 * lineItem attributes of the lineItem for TaskUpdateLineItemStatus
	 * This method also updates the lineItemAttributes(is_updated_status to no) of other lineItems whose
	 * status is not updated.
	 */
	public Boolean updateLineItemStatusAndAttribute(final LineItem lineItem, Set<LineItemAttribute> liAttrubuteList);

	public Boolean updateOrderStatus(final SalesOrder salesOrder, boolean updateLineItem);
	
	
	/*
	 * This method updates only lineItem current status and historic status and lineItem bean
	 * It doesn't update attributes, lineItemDetail, lineItem features etc
	 */
	public Boolean updateLineItemAndStatus(final LineItem lineItem);
}
