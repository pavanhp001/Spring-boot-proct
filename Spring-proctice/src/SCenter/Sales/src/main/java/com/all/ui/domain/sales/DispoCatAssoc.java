package com.AL.ui.domain.sales;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.AL.V.domain.IProvider;
import com.AL.V.domain.User;

/**
 * @author 
 * 
 */

@Entity
@Table(name = "DISPO_CAT_ASSOC")
public class DispoCatAssoc implements Serializable{

	@Id
	@Column(name = "DISPOSITIONID")
	private String dispositionId;

	@Column(name = "CATEGORYID")
	private String categoryId;


	public static DispoCatAssoc create(String dispositionId, String categoryId) {

		DispoCatAssoc dispoCatAssoc = new DispoCatAssoc();
		dispoCatAssoc.setDispositionId(dispositionId);
		dispoCatAssoc.setCategoryId(categoryId);
		return dispoCatAssoc;
	}

	
	public String getDispositionId() {
		return dispositionId;
	}


	public void setDispositionId(String dispositionId) {
		this.dispositionId = dispositionId;
	}

	public String getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}


	@Override
	public String toString() {
		return "DispoCatAssoc [dispositionId=" + dispositionId + ", categoryId=" + categoryId +"]";
	}

}
