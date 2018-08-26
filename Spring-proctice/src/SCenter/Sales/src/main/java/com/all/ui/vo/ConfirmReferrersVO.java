package com.AL.ui.vo;

 


import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author rajasekhar.n
 * 
 */

@Entity
@Table(name = "CONFIRM_REFERRERS")
public class ConfirmReferrersVO implements Serializable {


	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;

	@Column(name = "REFERRER_ID")
	private Long referrerId;
	
	@Column(name = "REFERRER_NAME")
	private String referrerName;
	
	public Long getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(Long referrerId) {
		this.referrerId = referrerId;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	@Override
	public String toString() {
		return "Confirm Referrers [id=" + id + "]";
	}

	 
	 

	 
	
	
	

}
