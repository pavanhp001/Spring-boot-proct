package com.A.V.beans.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.validator.NotNull;

import com.A.V.beans.CapabilitiesRecord;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * This entity represents an element in the real-time serviceability cache. It is the consolidated return from a service-provider
 * integration callout.
 * 
 * @author rchapple
 * 
 */
@Entity
@Table( name = "serviceabilityCache" )
@org.hibernate.annotations.Table( appliesTo = "serviceabilityCache", 
   indexes = { @Index( name = "SERVICEABILITYCACHE_IX1", columnNames = { "ZIP5", "PLUS4" } ) } )
public class ServiceabilityCacheRecordBean extends CapabilitiesRecord implements CommonBeanInterface
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -4607204675995088345L;
	private static final int ZIP5_LENGTH = 5;
    private static final int PLUS4_LENGTH = 4;

    @Id
    @GeneratedValue( generator = "serviceabilityCacheRecordBeanSequence" )
    @SequenceGenerator( name = "serviceabilityCacheRecordBeanSequence", sequenceName = "SRV_CACHERECORD_BEAN_SEQ" )
    private long id;

    @OneToOne
    @NotNull
    @ForeignKey( name = "SERVICE_CACHE_FK01" )
    private BusinessParty provider;

    @Column( length = ZIP5_LENGTH )
    @NotNull
    private String zip5;
    @Column( length = PLUS4_LENGTH )
    @NotNull
    private String plus4;

    private Calendar processDate;
    private int processingTime;

    /**
     * This is a convenience method that keeps us from having to set all of the fields individually when dealing with a complete
     * CapabilitiesRecord.
     * 
     * @param cr record to get data from to set.
     */
    public void setCapabilities( final CapabilitiesRecord cr )
    {
        this.setAnalogCableAvail( cr.getAnalogCableAvail() );
        this.setDialUpInternetAvail( cr.getDialUpInternetAvail() );
        this.setDigitalCableAvail( cr.getDigitalCableAvail() );
        this.setElectricityAvail( cr.getElectricityAvail() );
        this.setHDTVAvail( cr.getHDTVAvail() );
        this.setFiberDataDownSpeedAvail( cr.getFiberDataDownSpeedAvail() );
        this.setFiberDataUpSpeedAvail( cr.getFiberDataUpSpeedAvail() );
        this.setWiredDataDownSpeedAvail( cr.getWiredDataDownSpeedAvail() );
        this.setWiredDataUpSpeedAvail( cr.getWiredDataUpSpeedAvail() );
        this.setHomeSecurityAvail( cr.getHomeSecurityAvail() );
        this.setHomeWireProtectionAvail( cr.getHomeWireProtectionAvail() );
        this.setLocalNewspaperAvail( cr.getLocalNewspaperAvail() );
        this.setLocalPhoneAvail( cr.getLocalPhoneAvail() );
        this.setLongDistancePhoneAvail( cr.getLongDistancePhoneAvail() );
        this.setNationalNewspaperAvail( cr.getNationalNewspaperAvail() );
        this.setNaturalGasAvail( cr.getNaturalGasAvail() );
        this.setOffersAvail( cr.getOffersAvail() );
        this.setSatelliteAvail( cr.getSatelliteAvail() );
        this.setWasteRemovalAvail( cr.getWasteRemovalAvail() );
        this.setWaterAvail( cr.getWaterAvail() );
        this.setWirelessPhoneAvail( cr.getWirelessPhoneAvail() );
        this.setVoipAvail( cr.getVoipAvail() );
        this.setIptvAvail( cr.getIptvAvail() );
        this.setWarrantyAvail( cr.getWarrantyAvail() );
        this.setEnergyManagementAvail( cr.getEnergyManagementAvail() );
        this.setWirelessDataDownSpeedAvail( cr.getWirelessDataDownSpeedAvail() );
        this.setWirelessDataUpSpeedAvail( cr.getWirelessDataUpSpeedAvail() );
        
        // New Additions - Ticket #53213 IP-DSLAM
        this.setIpDslamDataDownSpeedAvail( cr.getIpDslamDataDownSpeedAvail() );
        this.setIpDslamDataUpSpeedAvail( cr.getIpDslamDataUpSpeedAvail() );
        this.setIpDslamIptvAvail( cr.getIpDslamIptvAvail() );
        this.setIpDslamVoipAvail( cr.getIpDslamVoipAvail() );
    }

    /**
     * @return the provider
     */
    public BusinessParty getProvider()
    {
        return provider;
    }

    /**
     * @param provider
     *            the provider to set
     */
    public void setProvider( final BusinessParty provider )
    {
        this.provider = provider;
    }

    /**
     * @return the processDate
     */
    public Calendar getProcessDate()
    {
        return processDate;
    }

    /**
     * @param processDate
     *            the processDate to set
     */
    public void setProcessDate( final Calendar processDate )
    {
        this.processDate = processDate;
    }

    /**
     * @return the zip5
     */
    public String getZip5()
    {
        return zip5;
    }

    /**
     * @param zip5
     *            the zip5 to set
     */
    public void setZip5( final String zip5 )
    {
        this.zip5 = zip5;
    }

    /**
     * @return the plus4
     */
    public String getPlus4()
    {
        return plus4;
    }

    /**
     * @param plus4
     *            the plus4 to set
     */
    public void setPlus4( final String plus4 )
    {
        this.plus4 = plus4;
    }

    /**
     * @return the processingTime
     */
    public int getProcessingTime()
    {
        return processingTime;
    }

    /**
     * @param processingTime
     *            the processingTime to set
     */
    public void setProcessingTime( final int processingTime )
    {
        this.processingTime = processingTime;
    }

    /**
     * @return the id
     */
    public long getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId( final long id )
    {
        this.id = id;
    }

}
