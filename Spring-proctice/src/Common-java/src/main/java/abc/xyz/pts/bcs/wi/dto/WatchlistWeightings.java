/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dto;


public class WatchlistWeightings {
    Double fullName;
    Double birthDate;
    Double gender;
    Double nationality;
    Double birthCountry;
    Double docType;
    Double docNumber;
    private String weightType ;  

    public Double getFullName() {
        return fullName;
    }
    public void setFullName(final Double fullName) {
        this.fullName = fullName;
    }
    public Double getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(final Double birthDate) {
        this.birthDate = birthDate;
    }
    public Double getGender() {
        return gender;
    }
    public void setGender(final Double gender) {
        this.gender = gender;
    }
    public Double getNationality() {
        return nationality;
    }
    public void setNationality(final Double nationality) {
        this.nationality = nationality;
    }
    public Double getBirthCountry() {
        return birthCountry;
    }
    public void setBirthCountry(final Double birthCountry) {
        this.birthCountry = birthCountry;
    }
    public Double getDocType() {
        return docType;
    }
    public void setDocType(final Double docType) {
        this.docType = docType;
    }
    public Double getDocNumber() {
        return docNumber;
    }
    public void setDocNumber(final Double docNumber) {
        this.docNumber = docNumber;
    }
	public String getWeightType() {
		return weightType;
	}
	public void setWeightType(final String weightType) {
		this.weightType = weightType;
	}
	@Override
	public String toString() {
		return "WatchlistWeightings [birthCountry=" + birthCountry + ", birthDate=" + birthDate + ", docNumber=" + docNumber + ", docType=" + docType
				+ ", fullName=" + fullName + ", gender=" + gender + ", nationality=" + nationality + ", weightType=" + weightType + "]";
	}


}
