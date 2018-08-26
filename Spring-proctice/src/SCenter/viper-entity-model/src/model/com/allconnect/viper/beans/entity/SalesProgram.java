package com.A.V.beans.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.MapKey;

import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table( name = "program" )
public class SalesProgram implements CommonBeanInterface{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5415334329559390321L;

	@Id
    @GeneratedValue( generator = "salesProgramSequence" )
    @SequenceGenerator( name = "salesProgramSequence", sequenceName = "PROGRAM_SEQ" )
	private long id;
	
	private String externalId;
	
	private String name;
	
	@ManyToMany
	private List<SalesChannel> salesChannels;

	@ManyToMany
	private List<BusinessParty> businessParties;
	
    @ElementCollection
    @MapKey( columns = @Column( name = "name" ) )
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
	private Map<String, String> metadata;
	
    private String type; // type of call (we currently only use movers)
    private String flow; // Call flow type (confirm, nonConfirm, etc)
    private String channel; //Where this program is handled (call center, web, etc)
    private String phoneNumForOrdering; //Toll free number call came in on
    private String concertCallbackNum; //Call back number given out on Close Call page in Sales   
    private String emailCallbackNum; //Call back number to be used on emails
    private String coBrandName; //Program CoBrand
    private String urlForOrdering; //URL to be used with Whitefence swivel chair
    private String referrerFacingName;
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<SalesChannel> getSalesChannels() {
		return salesChannels;
	}

	public void setSalesChannels(List<SalesChannel> salesChannels) {
		this.salesChannels = salesChannels;
	}

	public List<BusinessParty> getBusinessParties() {
		return businessParties;
	}

	public void setBusinessParties(List<BusinessParty> businessParties) {
		this.businessParties = businessParties;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPhoneNumForOrdering() {
		return phoneNumForOrdering;
	}

	public void setPhoneNumForOrdering(String phoneNumForOrdering) {
		this.phoneNumForOrdering = phoneNumForOrdering;
	}

	public String getConcertCallbackNum() {
		return concertCallbackNum;
	}

	public void setConcertCallbackNum(String concertCallbackNum) {
		this.concertCallbackNum = concertCallbackNum;
	}

	public String getEmailCallbackNum() {
		return emailCallbackNum;
	}

	public void setEmailCallbackNum(String emailCallbackNum) {
		this.emailCallbackNum = emailCallbackNum;
	}

	public String getCoBrandName() {
		return coBrandName;
	}

	public void setCoBrandName(String coBrandName) {
		this.coBrandName = coBrandName;
	}

	public String getUrlForOrdering() {
		return urlForOrdering;
	}

	public void setUrlForOrdering(String urlForOrdering) {
		this.urlForOrdering = urlForOrdering;
	}

	public String getReferrerFacingName() {
		return referrerFacingName;
	}

	public void setReferrerFacingName(String referrerFacingName) {
		this.referrerFacingName = referrerFacingName;
	}

}
