package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.A.V.interfaces.CommonBeanInterface;
@Entity
@Table( name = "OM_SALES_ORDER_CONTEXT" )
public class SalesOrderContext implements CommonBeanInterface
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7096825327628536773L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "salesOrderContextSequence" )
    @SequenceGenerator( name = "salesOrderContextSequence", sequenceName = "OM_SALESORDER_CONTEXT_SEQ",allocationSize = 1 )
    @Index( name = "IDX_ORDER_CONTEXT_ID", columnNames = { "id" } )
    private long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "VALUE")
	private String value;
	
	@Column(name = "ENTITY_NAME")
	private String entityName;
	
	@Column(name = "ORDER_SOURCE")
	private String orderSource;
	
	@Column(name = "SALES_ORDER_ID")
	private Long salesOrderId;
	
	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue( String value )
	{
		this.value = value;
	}

	public String getOrderSource()
	{
		return orderSource;
	}

	public void setOrderSource( String orderSource )
	{
		this.orderSource = orderSource;
	}

	@Override
	public long getId()
	{
		return id;
	}

	@Override
	public void setId( long id )
	{
		this.id = id;
	}

	public Long getSalesOrderId()
	{
		return salesOrderId;
	}

	public void setSalesOrderId( Long salesOrderId )
	{
		this.salesOrderId = salesOrderId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Override
	public String toString() {
		return "SalesOrderContext [id=" + id + ", name=" + name + ", value="
				+ value + ", entityName=" + entityName + ", orderSource="
				+ orderSource + ", salesOrderId=" + salesOrderId + "]";
	}

	
}
