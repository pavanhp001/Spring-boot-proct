package com.A.V.beans.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.validator.NotNull;

import com.A.V.beans.CapabilitiesRecord;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * This entity represents an element in the real-time capabilities. It is the consolidated return from a service-provider
 * integration callout.
 * 
 * @author rporumalla
 * 
 */
@Entity
@Table( name = "rt_Capabilities" )
@org.hibernate.annotations.Table( appliesTo = "rt_Capabilities", 
   indexes = { @Index( name = "RT_CAPABILITIES_IX1", columnNames = { "GUID", "NADDR" } ) } )
public class RTCapabilitiesRecordBean extends CapabilitiesRecord implements CommonBeanInterface
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7681201768351133610L;
	private static final int ZIP5_LENGTH = 5;
    private static final int PLUS4_LENGTH = 4;

    @Id
    @GeneratedValue( generator = "rtCapabilitiesRecordBeanSequence" )
    @SequenceGenerator( name = "rtCapabilitiesRecordBeanSequence", sequenceName = "RT_CAPABILITIES_SEQ" )
    private long id;

    //@OneToOne
    //@NotNull
    //@ForeignKey( name = "RT_CAPAB_FK01" )
    //private BusinessPartyBean provider;
    
    @Column( length = ZIP5_LENGTH )
    @NotNull
    private String zip5;
    @Column( length = PLUS4_LENGTH )
    @NotNull
    private String plus4;

    private Calendar processDate;
    private int status;
    private String statusMessage;
    private String phonenumber;
    
    @NotNull
    private String guid;
    
    @NotNull
    private String naddr;
    
    @NotNull
    private long providerid;
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
     * @return the providerId
     */
    public long getProviderId()
    {
        return providerid;
    }

    /**
     * @param providerId
     *            the provider to set
     */
    public void setProviderId( final long providerid )
    {
        this.providerid = providerid;
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
     * @return the statusMessage
     */
    public String getStatusMessage()
    {
        return statusMessage;
    }

    /**
     * @param statusMessage
     *            the statusMessage to set
     */
    public void setStatusMessage( final String statusMessage )
    {
        this.statusMessage = statusMessage;
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
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus( final int status )
    {
        this.status = status;
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
    
    /**
     * @return the guid
     */
    public String getGUID()
    {
        return guid;
    }

    /**
     * @param guid
     *            the guid to set
     */
    public void setGUID( final String guid )
    {
        this.guid = guid;
    }
    
    /**
     * @return the nAddr
     */
    public String getNAddr()
    {
        return naddr;
    }

    /**
     * @param nAddr
     *            the nAddr to set
     */
    public void setNAddr( final String naddr )
    {
        this.naddr = naddr;
    }
    
    /**
     * @return the phonenumber
     */
    public String getPhoneNumber()
    {
        return phonenumber;
    }

    /**
     * @param phonenumber
     *            the phonenumber to set
     */
    public void setPhoneNumber( final String phonenumber )
    {
        this.phonenumber = phonenumber;
    }
}
