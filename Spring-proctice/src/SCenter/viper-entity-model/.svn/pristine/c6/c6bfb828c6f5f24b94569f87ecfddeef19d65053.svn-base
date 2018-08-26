package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table( name = "CM_CUSTOMER_SURVEY" )
public class CustomerSurvey implements CommonBeanInterface {


    /**
	 * 
	 */
	private static final long serialVersionUID = 8318911312010680473L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "customerCsatSurveySequence" )
    @SequenceGenerator( name = "customerCsatSurveySequence", sequenceName = "CM_CUST_CSAT_SURVEY_SEQ" ,allocationSize = 1)
    private long id;

    @Column(name = "SURVEY_ID")
    private int surveyId;

    //@Column(name = "SURVEY_NAME")
    //private String name;

    @Column(name = "CONSUMER_ID")
    private Long consumerId;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public int getSurveyId() {
	return surveyId;
    }

    public void setSurveyId(int surveyId) {
	this.surveyId = surveyId;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public String toString() {
	return "CustomerSurvey [id=" + id + ", surveyId=" + surveyId + ", consumerId=" + consumerId + "]";
    }



}
