package com.A.ui.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Transient;


@Entity
@Table(name = "qual_popup_ref_details")
public class QualificationPopUpRefDetails implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "referrer")
	private String referrer;
	
	@Column(name = "provider_name")
	private String providerName;
	
	@Column(name = "popup_message")
	private String popupMessage;
	
	@Column(name = "single_play_points")
	private Float singlePlayPoints;
	
	@Column(name = "double_play_points")
	private Float doublePlayPoints;	
	
	@Column(name = "triple_play_points")
	private Float triplePlayPoints;	
	
	@Column(name = "double_play_pi_points")
	private Float doublePlayPIPoints;	
	
	@Column(name = "double_play_pv_points")
	private Float doublePlayPVPoints;	
	
	@Column(name = "double_play_vi_points")
	private Float doublePlayVIPoints;	
	
	@Column(name = "phone_points")
	private Float phonePoints;	
	
	@Column(name = "video_points")
	private Float videoPoints;	
	
	@Column(name = "internet_points")
	private Float internetPoints;	
	
	public Float getDoublePlayPIPoints() {
		return doublePlayPIPoints;
	}

	public void setDoublePlayPIPoints(Float doublePlayPIPoints) {
		this.doublePlayPIPoints = doublePlayPIPoints;
	}

	public Float getDoublePlayPVPoints() {
		return doublePlayPVPoints;
	}

	public void setDoublePlayPVPoints(Float doublePlayPVPoints) {
		this.doublePlayPVPoints = doublePlayPVPoints;
	}

	public Float getDoublePlayVIPoints() {
		return doublePlayVIPoints;
	}

	public void setDoublePlayVIPoints(Float doublePlayVIPoints) {
		this.doublePlayVIPoints = doublePlayVIPoints;
	}

	public Float getPhonePoints() {
		return phonePoints;
	}

	public void setPhonePoints(Float phonePoints) {
		this.phonePoints = phonePoints;
	}

	public Float getVideoPoints() {
		return videoPoints;
	}

	public void setVideoPoints(Float videoPoints) {
		this.videoPoints = videoPoints;
	}

	public Float getInternetPoints() {
		return internetPoints;
	}

	public void setInternetPoints(Float internetPoints) {
		this.internetPoints = internetPoints;
	}

	@Transient
	private List<String> zipCodesList = new ArrayList<String>();
	
	@Transient
	private String zipCode;

	@Override
	public String toString() {
		return "QualificationPopUpRefDetails [id=" + id + ", referrer="
				+ referrer + ", providerName=" + providerName
				+ ", popupMessage=" + popupMessage + ", singlePlayPoints="
				+ singlePlayPoints + ", doublePlayPoints=" + doublePlayPoints
				+ ", triplePlayPoints=" + triplePlayPoints + ", zipCodesList="
				+ zipCodesList + ", zipCode=" + zipCode + "]";
	}

	public Float getSinglePlayPoints() {
		return singlePlayPoints;
	}

	public void setSinglePlayPoints(Float singlePlayPoints) {
		this.singlePlayPoints = singlePlayPoints;
	}

	public Float getDoublePlayPoints() {
		return doublePlayPoints;
	}

	public void setDoublePlayPoints(Float doublePlayPoints) {
		this.doublePlayPoints = doublePlayPoints;
	}

	public Float getTriplePlayPoints() {
		return triplePlayPoints;
	}

	public void setTriplePlayPoints(Float triplePlayPoints) {
		this.triplePlayPoints = triplePlayPoints;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public List<String> getZipCodesList() {
		return zipCodesList;
	}

	public void setZipCodesList(List<String> zipCodesList) {
		this.zipCodesList = zipCodesList;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getPopupMessage() {
		return popupMessage;
	}

	public void setPopupMessage(String popupMessage) {
		this.popupMessage = popupMessage;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

}