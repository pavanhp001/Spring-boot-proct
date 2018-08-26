package com.AL.ui.domain.sales;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 * 
 */

@Entity
@Table(name = "SC_SIMPLE_CHOICE_OPERATING_COMPANY")
public class OperatingCompany implements Serializable {


	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;

	@Column(name = "CODE")
	private String code;
	

	@Column(name = "NAME")
	private String name;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Concert OperatingCompany [id=" + id + "]";
	}

}
