package com.AL.ui.domain.sales;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 * 
 */

@Entity
@Table(name = "DISPOSITION_CATEGORY")
public class DispositionCategory implements Serializable{

	@Id
	@Column(name = "CATEGORYID")
	private String categoryId;

	@Column(name = "CATEGORY_NAME")
	private String categoryName;
	

	public static DispositionCategory create(String categoryId, String categoryName) {

		DispositionCategory dispositionCategory = new DispositionCategory();
		dispositionCategory.setCategoryId(categoryId);
		dispositionCategory.setCategoryName(categoryName);		
		return dispositionCategory;
	}

	
	public String getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	@Override
	public String toString() {
		return "DispositionCategory [categoryId=" + categoryId + ", categoryName=" + categoryName +"]";
	}

}
