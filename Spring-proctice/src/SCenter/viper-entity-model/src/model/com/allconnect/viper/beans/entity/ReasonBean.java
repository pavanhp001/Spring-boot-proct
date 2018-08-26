/**
 *
 */
package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author 
 *
 */

@Entity
@Table(name = "REASON")
public class ReasonBean implements CommonBeanInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7640852432411123149L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "reasonBeanSequence")
	@SequenceGenerator(name = "reasonBeanSequence", sequenceName = "REASON_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private long id;

	@Column(name = "REASON_ID")
	private Long reasonId;

	@Column(name = "REASON_TYPE_ID")
	private Long reasonTypeId;

	@Column(name = "REASON_CATEGORY_ID")
	private Long reasonCategoryId;

	@Column(name = "LINE_ITEM_ID")
	private Long lineItemId;

	@Column(name = "PRIORITY_ID")
	private Integer priorityId;
	
	@Transient
	private String reasonDesc;

	@Transient
	private String reasonCategory;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getReasonId() {
		return reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

	public Long getReasonTypeId() {
		return reasonTypeId;
	}

	public void setReasonTypeId(Long reasonTypeId) {
		this.reasonTypeId = reasonTypeId;
	}

	public Long getReasonCategoryId() {
		return reasonCategoryId;
	}

	public void setReasonCategoryId(Long reasonCategoryId) {
		this.reasonCategoryId = reasonCategoryId;
	}

	public Long getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(Long lineItemId) {
		this.lineItemId = lineItemId;
	}

	public Integer getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(Integer priorityId) {
		this.priorityId = priorityId;
	}
	
	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	public String getReasonCategory() {
		return reasonCategory;
	}

	public void setReasonCategory(String reasonCategory) {
		this.reasonCategory = reasonCategory;
	}
	
	/**
    *
    */
	public ReasonBean() {
	}


}
