/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dto;

import java.util.Calendar;

/**
 * @author ryattapu
 *
 */
public class Traveller {

    private Long id;
    private Long flightSegId;
    private String apiDataStatus;
    private String seqNo;
    private String dcsDataStatus;
    private String pnrDataStatus;
    private String travellerType;
    private String priWorkingAirportCode;
    private String middleName;
    private String title;
    private Calendar createdDatetime;
    private Long boonlId;
    private Long boonlIdHasPreScan;
    private Long flightCheckInId;
    private String birthProvinceCode;
    private Long flightManifestId;
    private String foreNameThreeChars;
    private String foreName;
    private String updateVersionNo;
    private String lastNameThreeChars;
    private String lastName;
    private String originCntryCode;
    private String priDocType;
    private String destCntryCode;
    private String priDocNo;
    private String latestUpdateType;
    private Calendar priDocIssueDate;
    private Long pnrId;
    private Calendar priDocExpiryDate;
    private Long nameId;
    private String priDocIssueCntryCode;
    private Long dcsId;
    private String visaNo;
    private Long apiId;
    private String visaIssueCntryCode;
    private Calendar visaIssueDate;
    private Long carprId;
    private Calendar visaExpiryDate;
    private String visaIssuePlace;
    private String authFlag;
    private Calendar authUsername;
    private String gender;
    private Calendar authDatetime;
    private String nationality;
    private String authFullName;
    private String bcsUsername;
    private Calendar birthDate;
    private String birthPlace;
    private String birthCountryCode;
    private String sdiUsername;
    private String residentCntryCode;
    private Long sdiNsisTotalWeighting;
    private Long sdiNsisThreshold;
    private String originAirportCode;
    private String movementType;
    private String destAirportCode;
    private String birthDateString;
    private String clearanceAirportCode;
    private String fullName;
    private Calendar modifiedDatetime;
    private String addrType;
    private String addrName;
    private String addrArea;
    private String addrCity;
    private String addrCntry;
    private String addrPostcode;
    private String phone;
    private String email;
    private String seatNo;
    private String bagTagNos;
    private String bookingRefNo;
    private String apiDcsPerc;
    private String apiPnrPerc;
    private String dcsPnrPerc;
    private String ticketStatus;
    private String freqFlyerFlag;
    private String freqFlyerCarrierCode;
    private String freqFlyerNo;
    private String secWorkingAirportCode;
    private String reRoutedDepAirportCode;
    private String reRoutedArrAirportCode;
    private Calendar activeTravStatusDatetime;
    private Long linkageId;
    private String preScanFlag;
    private String traFltsAtRiskFlag;
    private String atRiskFlag;
    private String notInLatestApiFlag;
    private Long traaId;
    private String fltsAtRiskFlag;
    private String ticketNo;
    private String ticketType;
    private Long maHitsId;
    private Long maHitCode;
    private String maHitStatus;
    private String riskAssessmentStatus; // Column RISK_ASSESSMENT_STATUS -  values 'NOTSTARTED','INPROGRESS','FAILED','COMPLETED'
    private String depAirportCode;
    private String arrAirportCode;
    private String operCarrierCode;
    private String operFlightNo;
    private Calendar arrDatetime;
    private Calendar depDatetime;
    private String arrTime;
    private String depTime;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getFlightSegId() {
        return flightSegId;
    }

    public void setFlightSegId(final Long flightSegId) {
        this.flightSegId = flightSegId;
    }

    public String getApiDataStatus() {
        return apiDataStatus;
    }

    public void setApiDataStatus(final String apiDataStatus) {
        this.apiDataStatus = apiDataStatus;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(final String seqNo) {
        this.seqNo = seqNo;
    }

    public String getDcsDataStatus() {
        return dcsDataStatus;
    }

    public void setDcsDataStatus(final String dcsDataStatus) {
        this.dcsDataStatus = dcsDataStatus;
    }

    public String getPnrDataStatus() {
        return pnrDataStatus;
    }

    public void setPnrDataStatus(final String pnrDataStatus) {
        this.pnrDataStatus = pnrDataStatus;
    }

    public String getTravellerType() {
        return travellerType;
    }

    public void setTravellerType(final String travellerType) {
        this.travellerType = travellerType;
    }

    public String getPriWorkingAirportCode() {
        return priWorkingAirportCode;
    }

    public void setPriWorkingAirportCode(final String priWorkingAirportCode) {
        this.priWorkingAirportCode = priWorkingAirportCode;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Calendar getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(final Calendar createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Long getBoonlId() {
        return boonlId;
    }

    public void setBoonlId(final Long boonlId) {
        this.boonlId = boonlId;
    }

    public Long getBoonlIdHasPreScan() {
        return boonlIdHasPreScan;
    }

    public void setBoonlIdHasPreScan(final Long boonlIdHasPreScan) {
        this.boonlIdHasPreScan = boonlIdHasPreScan;
    }

    public Long getFlightCheckInId() {
        return flightCheckInId;
    }

    public void setFlightCheckInId(final Long flightCheckInId) {
        this.flightCheckInId = flightCheckInId;
    }

    public String getBirthProvinceCode() {
        return birthProvinceCode;
    }

    public void setBirthProvinceCode(final String birthProvinceCode) {
        this.birthProvinceCode = birthProvinceCode;
    }

    public Long getFlightManifestId() {
        return flightManifestId;
    }

    public void setFlightManifestId(final Long flightManifestId) {
        this.flightManifestId = flightManifestId;
    }

    public String getForeNameThreeChars() {
        return foreNameThreeChars;
    }

    public void setForeNameThreeChars(final String foreNameThreeChars) {
        this.foreNameThreeChars = foreNameThreeChars;
    }

    public String getForeName() {
        return foreName;
    }

    public void setForeName(final String foreName) {
        this.foreName = foreName;
    }

    public String getLastNameThreeChars() {
        return lastNameThreeChars;
    }

    public void setLastNameThreeChars(final String lastNameThreeChars) {
        this.lastNameThreeChars = lastNameThreeChars;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getOriginCntryCode() {
        return originCntryCode;
    }

    public void setOriginCntryCode(final String originCntryCode) {
        this.originCntryCode = originCntryCode;
    }

    public String getPriDocType() {
        return priDocType;
    }

    public void setPriDocType(final String priDocType) {
        this.priDocType = priDocType;
    }

    public String getDestCntryCode() {
        return destCntryCode;
    }

    public void setDestCntryCode(final String destCntryCode) {
        this.destCntryCode = destCntryCode;
    }

    public String getPriDocNo() {
        return priDocNo;
    }

    public void setPriDocNo(final String priDocNo) {
        this.priDocNo = priDocNo;
    }

    public String getLatestUpdateType() {
        return latestUpdateType;
    }

    public void setLatestUpdateType(final String latestUpdateType) {
        this.latestUpdateType = latestUpdateType;
    }

    public Calendar getPriDocIssueDate() {
        return priDocIssueDate;
    }

    public void setPriDocIssueDate(final Calendar priDocIssueDate) {
        this.priDocIssueDate = priDocIssueDate;
    }

    public Long getPnrId() {
        return pnrId;
    }

    public void setPnrId(final Long pnrId) {
        this.pnrId = pnrId;
    }

    public Calendar getPriDocExpiryDate() {
        return priDocExpiryDate;
    }

    public void setPriDocExpiryDate(final Calendar priDocExpiryDate) {
        this.priDocExpiryDate = priDocExpiryDate;
    }

    public Long getNameId() {
        return nameId;
    }

    public void setNameId(final Long nameId) {
        this.nameId = nameId;
    }

    public String getPriDocIssueCntryCode() {
        return priDocIssueCntryCode;
    }

    public void setPriDocIssueCntryCode(final String priDocIssueCntryCode) {
        this.priDocIssueCntryCode = priDocIssueCntryCode;
    }

    public Long getDcsId() {
        return dcsId;
    }

    public void setDcsId(final Long dcsId) {
        this.dcsId = dcsId;
    }

    public String getVisaNo() {
        return visaNo;
    }

    public void setVisaNo(final String visaNo) {
        this.visaNo = visaNo;
    }

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(final Long apiId) {
        this.apiId = apiId;
    }

    public String getVisaIssueCntryCode() {
        return visaIssueCntryCode;
    }

    public void setVisaIssueCntryCode(final String visaIssueCntryCode) {
        this.visaIssueCntryCode = visaIssueCntryCode;
    }

    public Calendar getVisaIssueDate() {
        return visaIssueDate;
    }

    public void setVisaIssueDate(final Calendar visaIssueDate) {
        this.visaIssueDate = visaIssueDate;
    }

    public Long getCarprId() {
        return carprId;
    }

    public void setCarprId(final Long carprId) {
        this.carprId = carprId;
    }

    public Calendar getVisaExpiryDate() {
        return visaExpiryDate;
    }

    public void setVisaExpiryDate(final Calendar visaExpiryDate) {
        this.visaExpiryDate = visaExpiryDate;
    }

    public String getVisaIssuePlace() {
        return visaIssuePlace;
    }

    public void setVisaIssuePlace(final String visaIssuePlace) {
        this.visaIssuePlace = visaIssuePlace;
    }

    public String getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(final String authFlag) {
        this.authFlag = authFlag;
    }

    public Calendar getAuthUsername() {
        return authUsername;
    }

    public void setAuthUsername(final Calendar authUsername) {
        this.authUsername = authUsername;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public Calendar getAuthDatetime() {
        return authDatetime;
    }

    public void setAuthDatetime(final Calendar authDatetime) {
        this.authDatetime = authDatetime;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }

    public String getAuthFullName() {
        return authFullName;
    }

    public void setAuthFullName(final String authFullName) {
        this.authFullName = authFullName;
    }

    public String getBcsUsername() {
        return bcsUsername;
    }

    public void setBcsUsername(final String bcsUsername) {
        this.bcsUsername = bcsUsername;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(final String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthCountryCode() {
        return birthCountryCode;
    }

    public void setBirthCountryCode(final String birthCountryCode) {
        this.birthCountryCode = birthCountryCode;
    }

    public String getSdiUsername() {
        return sdiUsername;
    }

    public void setSdiUsername(final String sdiUsername) {
        this.sdiUsername = sdiUsername;
    }

    public String getResidentCntryCode() {
        return residentCntryCode;
    }

    public void setResidentCntryCode(final String residentCntryCode) {
        this.residentCntryCode = residentCntryCode;
    }

    public Long getSdiNsisTotalWeighting() {
        return sdiNsisTotalWeighting;
    }

    public void setSdiNsisTotalWeighting(final Long sdiNsisTotalWeighting) {
        this.sdiNsisTotalWeighting = sdiNsisTotalWeighting;
    }

    public Long getSdiNsisThreshold() {
        return sdiNsisThreshold;
    }

    public void setSdiNsisThreshold(final Long sdiNsisThreshold) {
        this.sdiNsisThreshold = sdiNsisThreshold;
    }

    public String getOriginAirportCode() {
        return originAirportCode;
    }

    public void setOriginAirportCode(final String originAirportCode) {
        this.originAirportCode = originAirportCode;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(final String movementType) {
        this.movementType = movementType;
    }

    public String getDestAirportCode() {
        return destAirportCode;
    }

    public void setDestAirportCode(final String destAirportCode) {
        this.destAirportCode = destAirportCode;
    }

    public String getBirthDateString() {
        return birthDateString;
    }

    public void setBirthDateString(final String birthDateString) {
        this.birthDateString = birthDateString;
    }

    public String getClearanceAirportCode() {
        return clearanceAirportCode;
    }

    public void setClearanceAirportCode(final String clearanceAirportCode) {
        this.clearanceAirportCode = clearanceAirportCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public Calendar getModifiedDatetime() {
        return modifiedDatetime;
    }

    public void setModifiedDatetime(final Calendar modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }

    public String getAddrType() {
        return addrType;
    }

    public void setAddrType(final String addrType) {
        this.addrType = addrType;
    }

    public String getAddrName() {
        return addrName;
    }

    public void setAddrName(final String addrName) {
        this.addrName = addrName;
    }

    public String getAddrArea() {
        return addrArea;
    }

    public void setAddrArea(final String addrArea) {
        this.addrArea = addrArea;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(final String addrCity) {
        this.addrCity = addrCity;
    }

    public String getAddrCntry() {
        return addrCntry;
    }

    public void setAddrCntry(final String addrCntry) {
        this.addrCntry = addrCntry;
    }

    public String getAddrPostcode() {
        return addrPostcode;
    }

    public void setAddrPostcode(final String addrPostcode) {
        this.addrPostcode = addrPostcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(final String seatNo) {
        this.seatNo = seatNo;
    }

    public String getBagTagNos() {
        return bagTagNos;
    }

    public void setBagTagNos(final String bagTagNos) {
        this.bagTagNos = bagTagNos;
    }

    public String getBookingRefNo() {
        return bookingRefNo;
    }

    public void setBookingRefNo(final String bookingRefNo) {
        this.bookingRefNo = bookingRefNo;
    }

    public String getApiDcsPerc() {
        return apiDcsPerc;
    }

    public void setApiDcsPerc(final String apiDcsPerc) {
        this.apiDcsPerc = apiDcsPerc;
    }

    public String getApiPnrPerc() {
        return apiPnrPerc;
    }

    public void setApiPnrPerc(final String apiPnrPerc) {
        this.apiPnrPerc = apiPnrPerc;
    }

    public String getDcsPnrPerc() {
        return dcsPnrPerc;
    }

    public void setDcsPnrPerc(final String dcsPnrPerc) {
        this.dcsPnrPerc = dcsPnrPerc;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(final String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getFreqFlyerFlag() {
        return freqFlyerFlag;
    }

    public void setFreqFlyerFlag(final String freqFlyerFlag) {
        this.freqFlyerFlag = freqFlyerFlag;
    }

    public String getFreqFlyerCarrierCode() {
        return freqFlyerCarrierCode;
    }

    public void setFreqFlyerCarrierCode(final String freqFlyerCarrierCode) {
        this.freqFlyerCarrierCode = freqFlyerCarrierCode;
    }

    public String getFreqFlyerNo() {
        return freqFlyerNo;
    }

    public void setFreqFlyerNo(final String freqFlyerNo) {
        this.freqFlyerNo = freqFlyerNo;
    }

    public String getSecWorkingAirportCode() {
        return secWorkingAirportCode;
    }

    public void setSecWorkingAirportCode(final String secWorkingAirportCode) {
        this.secWorkingAirportCode = secWorkingAirportCode;
    }

    public String getReRoutedDepAirportCode() {
        return reRoutedDepAirportCode;
    }

    public void setReRoutedDepAirportCode(final String reRoutedDepAirportCode) {
        this.reRoutedDepAirportCode = reRoutedDepAirportCode;
    }

    public String getReRoutedArrAirportCode() {
        return reRoutedArrAirportCode;
    }

    public void setReRoutedArrAirportCode(final String reRoutedArrAirportCode) {
        this.reRoutedArrAirportCode = reRoutedArrAirportCode;
    }

    public Calendar getActiveTravStatusDatetime() {
        return activeTravStatusDatetime;
    }

    public void setActiveTravStatusDatetime(final Calendar activeTravStatusDatetime) {
        this.activeTravStatusDatetime = activeTravStatusDatetime;
    }

    public Long getLinkageId() {
        return linkageId;
    }

    public void setLinkageId(final Long linkageId) {
        this.linkageId = linkageId;
    }

    public String getPreScanFlag() {
        return preScanFlag;
    }

    public void setPreScanFlag(final String preScanFlag) {
        this.preScanFlag = preScanFlag;
    }

    public String getTraFltsAtRiskFlag() {
        return traFltsAtRiskFlag;
    }

    public void setTraFltsAtRiskFlag(final String traFltsAtRiskFlag) {
        this.traFltsAtRiskFlag = traFltsAtRiskFlag;
    }

    public String getAtRiskFlag() {
        return atRiskFlag;
    }

    public void setAtRiskFlag(final String atRiskFlag) {
        this.atRiskFlag = atRiskFlag;
    }

    public String getNotInLatestApiFlag() {
        return notInLatestApiFlag;
    }

    public void setNotInLatestApiFlag(final String notInLatestApiFlag) {
        this.notInLatestApiFlag = notInLatestApiFlag;
    }
    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(final String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(final String ticketType) {
        this.ticketType = ticketType;
    }
    public String getUpdateVersionNo() {
        return updateVersionNo;
    }

    public void setUpdateVersionNo(final String updateVersionNo) {
        this.updateVersionNo = updateVersionNo;
    }
    public Long getTraaId() {
        return traaId;
    }

    public void setTraaId(final Long traaId) {
        this.traaId = traaId;
    }

    public String getFltsAtRiskFlag() {
        return fltsAtRiskFlag;
    }

    public void setFltsAtRiskFlag(final String fltsAtRiskFlag) {
        this.fltsAtRiskFlag = fltsAtRiskFlag;
    }

    public Long getMaHitsId() {
        return maHitsId;
    }

    public void setMaHitsId(final Long maHitsId) {
        this.maHitsId = maHitsId;
    }

    public Long getMaHitCode() {
        return maHitCode;
    }

    public void setMaHitCode(final Long maHitCode) {
        this.maHitCode = maHitCode;
    }

    public String getMaHitStatus() {
        return maHitStatus;
    }

    public void setMaHitStatus(final String maHitStatus) {
        this.maHitStatus = maHitStatus;
    }

    /*
     * Possible values for this field are :  'NOTSTARTED','INPROGRESS','FAILED','COMPLETED'
     */
    public String getRiskAssessmentStatus() {
        return riskAssessmentStatus;
    }
    public void setRiskAssessmentStatus(final String riskAssessmentStatus) {
        this.riskAssessmentStatus = riskAssessmentStatus;
    }

	public String getDepAirportCode() {
		return depAirportCode;
	}

	public void setDepAirportCode(final String depAirportCode) {
		this.depAirportCode = depAirportCode;
	}

	public String getArrAirportCode() {
		return arrAirportCode;
	}

	public void setArrAirportCode(final String arrAirportCode) {
		this.arrAirportCode = arrAirportCode;
	}

	public String getOperCarrierCode() {
		return operCarrierCode;
	}

	public void setOperCarrierCode(final String operCarrierCode) {
		this.operCarrierCode = operCarrierCode;
	}

	public String getOperFlightNo() {
		return operFlightNo;
	}

	public void setOperFlightNo(final String operFlightNo) {
		this.operFlightNo = operFlightNo;
	}

	public Calendar getArrDatetime() {
		return arrDatetime;
	}

	public void setArrDatetime(final Calendar arrDatetime) {
		this.arrDatetime = arrDatetime;
	}

	public Calendar getDepDatetime() {
		return depDatetime;
	}

	public void setDepDatetime(final Calendar depDatetime) {
		this.depDatetime = depDatetime;
	}

	public String getArrTime() {
		return arrTime;
	}

	public void setArrTime(final String arrTime) {
		this.arrTime = arrTime;
	}

	public String getDepTime() {
		return depTime;
	}

	public void setDepTime(final String depTime) {
		this.depTime = depTime;
	}
}

