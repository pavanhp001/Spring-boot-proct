
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for coreMarketItemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="coreMarketItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="channels" type="{http://xml.A.com/v4}channelType"/>
 *         &lt;element name="accordChangeStamp" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="accordPlanId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="serviceabilitySource" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="internal"/>
 *               &lt;enumeration value="realtime"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="providerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="priceInfo" type="{http://xml.A.com/v4}priceInfoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "coreMarketItemType", propOrder = {
    "externalId",
    "channels",
    "accordChangeStamp",
    "accordPlanId",
    "serviceabilitySource",
    "providerId",
    "providerName",
    "priceInfo"
})
@XmlSeeAlso({
    MarketItemWithCapabilitiesType.class,
    MarketItemType.class
})
public class CoreMarketItemType {

    @XmlElement(required = true)
    protected String externalId;
    @XmlElement(required = true)
    protected ChannelType channels;
    protected Long accordChangeStamp;
    protected Integer accordPlanId;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String serviceabilitySource;
    @XmlElement(required = true)
    protected String providerId;
    @XmlElement(required = true)
    protected String providerName;
    @XmlElement(required = true)
    protected PriceInfoType priceInfo;

    /**
     * Gets the value of the externalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalId(String value) {
        this.externalId = value;
    }

    /**
     * Gets the value of the channels property.
     * 
     * @return
     *     possible object is
     *     {@link ChannelType }
     *     
     */
    public ChannelType getChannels() {
        return channels;
    }

    /**
     * Sets the value of the channels property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChannelType }
     *     
     */
    public void setChannels(ChannelType value) {
        this.channels = value;
    }

    /**
     * Gets the value of the accordChangeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAccordChangeStamp() {
        return accordChangeStamp;
    }

    /**
     * Sets the value of the accordChangeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAccordChangeStamp(Long value) {
        this.accordChangeStamp = value;
    }

    /**
     * Gets the value of the accordPlanId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAccordPlanId() {
        return accordPlanId;
    }

    /**
     * Sets the value of the accordPlanId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAccordPlanId(Integer value) {
        this.accordPlanId = value;
    }

    /**
     * Gets the value of the serviceabilitySource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceabilitySource() {
        return serviceabilitySource;
    }

    /**
     * Sets the value of the serviceabilitySource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceabilitySource(String value) {
        this.serviceabilitySource = value;
    }

    /**
     * Gets the value of the providerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * Sets the value of the providerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderId(String value) {
        this.providerId = value;
    }

    /**
     * Gets the value of the providerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * Sets the value of the providerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderName(String value) {
        this.providerName = value;
    }

    /**
     * Gets the value of the priceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PriceInfoType }
     *     
     */
    public PriceInfoType getPriceInfo() {
        return priceInfo;
    }

    /**
     * Sets the value of the priceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceInfoType }
     *     
     */
    public void setPriceInfo(PriceInfoType value) {
        this.priceInfo = value;
    }

}
