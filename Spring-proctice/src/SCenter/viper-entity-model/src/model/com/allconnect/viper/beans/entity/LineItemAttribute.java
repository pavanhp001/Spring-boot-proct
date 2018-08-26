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
@Table( name = "OM_LI_ATTRIBUTE" )
public class LineItemAttribute implements CommonBeanInterface {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1825158923286112051L;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "lineItemAttributeSequence" )
    @SequenceGenerator( name = "lineItemAttributeSequence", sequenceName = "OM_LINE_ITEM_ATTRIBUTE_SEQ", allocationSize = 1)
	@Index( name = "OM_LI_ATTRIBUTE_ID_IX1", columnNames = { "id" }  )
	private long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "VALUE")
	private String value;
	
	@Column(name = "SOURCE")
	private String source;
	
	//@Column(name = "LINE_ITEM_ID")
	//private long lineItemId;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	/*public long getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(long lineItemId) {
		this.lineItemId = lineItemId;
	}*/

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "LineItemAttribute [id=" + id + ", name=" + name + ", value="
				+ value + ", source=" + source + ", description=" + description + "]";
	}

	
}
