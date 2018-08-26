package com.A.ui.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "popup_ref_zipcode")
public class PopUpRefZipCode implements Serializable{
	
	@Id
	@Column(name = "popup_ref_id")
	private Integer popupRefId;

	@Column(name = "zip_code")
	private String zipCode;
	
}