package com.A.V.beans.entity;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.beans.LineItemPriceInfo;
import com.A.V.interfaces.CommonBeanInterface;

@Entity
@NamedQueries({
    @NamedQuery(name = "SelectedFeatureValue.findAll",
            query = "select f from SelectedFeatureValue f  " +
                  "LEFT JOIN fetch f.parentNode")
  })
@Table(name = "OM_SEL_FEATURE")
public class SelectedFeatureValue implements CommonBeanInterface {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8620404375184045172L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "featureValueBeanSequence")
	@SequenceGenerator(name = "featureValueBeanSequence", sequenceName = "OM_SEL_FEATURE_VALUE_BEAN_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "FEATURE_TYPE")
	private int featureType;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private SelectedFeatureValue parentNode;

	@OneToMany(mappedBy = "parentNode")
	private Set<SelectedFeatureValue> childNodes;

	@Column(name = "EXTERNAL_ID")
	private String externalId;

	@Column(name = "FEATURE_VALUE")
	private String value;

	@Column(name = "FEATURE_DATA_TYPE")
	private String dataType;

	@Column(name = "PRICE")
	private LineItemPriceInfo price;


	@Column(name = "IS_INCLUDED")
	private Boolean included;

	@Column(name = "IS_REQUIRED")
	private Boolean required;


	@Column(name = "IS_AVAILABLE")
	private Boolean available;

	@Column(name = "IS_DISPLAYABLE")
	private Boolean display;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "IS_ACTIVE" , columnDefinition="boolean default false")
	private Boolean active;

	@Column(name = "FEATURE_DATE")
	private Calendar featureDate;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getFeatureType() {
		return featureType;
	}

	public void setFeatureType(int featureType) {
		this.featureType = featureType;
	}

	public SelectedFeatureValue getParentNode() {
		return parentNode;
	}

	public void setParentNode(SelectedFeatureValue parentNode) {
		this.parentNode = parentNode;
	}

	public Set<SelectedFeatureValue> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(Set<SelectedFeatureValue> childNodes) {
		this.childNodes = childNodes;
	}

	public LineItemPriceInfo getPrice() {
		return price;
	}

	public void setPrice(LineItemPriceInfo price) {
		this.price = price;
	}

	public Boolean getIncluded() {
		return included;
	}

	public void setIncluded(Boolean included) {
		this.included = included;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActive() {
	    return active;
	}

	public void setActive(Boolean active) {
	    this.active = active;
	}

	public Calendar getFeatureDate() {
	    return featureDate;
	}

	public void setFeatureDate(Calendar featureDate) {
	    this.featureDate = featureDate;
	}

	@Override
	public String toString() {
	    return "SelectedFeatureValue [id=" + id + ", featureType=" + featureType + ", externalId=" + externalId + ", value=" + value + ", included=" + included + ", active="
		    + active + "]";
	}

	
}
