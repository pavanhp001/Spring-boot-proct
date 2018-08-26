package com.A.V.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


/**
 * 
 * @author ebaugh
 *
 */
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class CapabilitiesRecord
{
    private int satelliteAvail;
    private int analogCableAvail;
    private int digitalCableAvail;
    private int hdtvAvail;
    private int fiberDataDownSpeedAvail;
    private int fiberDataUpSpeedAvail;
    private int wiredDataUpSpeedAvail;
    private int wiredDataDownSpeedAvail;
    
    private int wirelessDataDownSpeedAvail;
    private int wirelessDataUpSpeedAvail;
    
    private int dialUpInternetAvail;
    private int electricityAvail;
    private int naturalGasAvail;
    private int localPhoneAvail;
    private int longDistancePhoneAvail;
    private int homeWireProtectionAvail;
    private int wirelessPhoneAvail;
    private int wasteRemovalAvail;
    private int localNewspaperAvail;
    private int nationalNewspaperAvail;
    private int homeSecurityAvail;
    private int waterAvail;
    
    // TODO This shouldn't be here, should be modeled differently.  This is cruft from Accord.
    private int offersAvail;
    
    private int voipAvail;
    private int iptvAvail;
    private int warrantyAvail;
    private int energyManagementAvail;

    // New Additions - Ticket #53213 IP-DSLAM
    private int ipDslamDataDownSpeedAvail;
    private int ipDslamDataUpSpeedAvail;
    private int ipDslamVoipAvail;
    private int ipDslamIptvAvail;
 
    public static final transient String SATELLITE_AVAIL_STRING                 = "satellite";
    public static final transient String ANALOG_CABLE_AVAIL_STRING              = "analogCable";
    public static final transient String DIGITAL_CABLE_AVAIL_STRING             = "digitalCable";
    public static final transient String HDTV_AVAIL_STRING                      = "hdtv";
    public static final transient String FIBER_DATA_DOWN_SPEED_AVAIL_STRING     = "fiberDataDownSpeed";
    public static final transient String FIBER_DATA_UP_SPEED_AVAIL_STRING       = "fiberDataUpSpeed";
    public static final transient String WIRED_DATA_UP_SPEED_AVAIL_STRING       = "wiredDataUpSpeed";
    public static final transient String WIRED_DATA_DOWN_SPEED_AVAIL_STRING     = "wiredDataDownSpeed";
    public static final transient String WIRELESS_DATA_UP_SPEED_AVAIL_STRING    = "wirelessDataUpSpeed";
    public static final transient String WIRELESS_DATA_DOWN_SPEED_AVAIL_STRING  = "wirelessDataDownSpeed";
    public static final transient String DIAL_UP_INTERNET_AVAIL_STRING          = "dialUpInternet";
    public static final transient String ELECTRICITY_AVAIL_STRING               = "electricity";
    public static final transient String NATURAL_GAS_AVAIL_STRING               = "naturalGas";
    public static final transient String LOCAL_PHONE_AVAIL_STRING               = "localPhone";
    public static final transient String LONG_DISTANCE_PHONE_AVAIL_STRING       = "longDistancePhone";
    public static final transient String HOME_WIRE_PROTECTION_AVAIL_STRING      = "homeWireProtection";
    public static final transient String WIRELESS_PHONE_AVAIL_STRING            = "wirelessPhone";
    public static final transient String WASTE_REMOVAL_AVAIL_STRING             = "wasteRemoval";
    public static final transient String LOCAL_NEWSPAPER_AVAIL_STRING           = "localNewspaper";
    public static final transient String NATIONAL_NEWSPAPER_AVAIL_STRING        = "nationalNewspaper";
    public static final transient String HOME_SECURITY_AVAIL_STRING             = "homeSecurity";
    public static final transient String WATER_AVAIL_STRING                     = "water";
    public static final transient String OFFERS_AVAIL_STRING                    = "offers";
    public static final transient String VOIP_AVAIL_STRING                      = "voip";
    public static final transient String IPTV_AVAIL_STRING                      = "iptv";
    public static final transient String WARRANTY_AVAIL_STRING                  = "warranty";
    public static final transient String ENERGY_MANAGEMENT_AVAIL_STRING         = "energyManagement";

    // New Additions - Ticket #53213 IP-DSLAM
    public static final transient String IPDSLAM_DATA_DOWN_SPEED_AVAIL_STRING 	= "ipDslamDataDownSpeed";
    public static final transient String IPDSLAM_DATA_UP_SPEED_AVAIL_STRING 	= "ipDslamDataUpSpeed";
    public static final transient String IPDSLAM_VOIP_AVAIL_STRING 				= "ipDslamVoip";
    public static final transient String IPDSLAM_IPTV_AVAIL_STRING 				= "ipDslamIptv";
    
    public int getSatelliteAvail()
    {
        return satelliteAvail;
    }

    public void setSatelliteAvail( final int satelliteAvail )
    {
        this.satelliteAvail = satelliteAvail;
    }

    public int getAnalogCableAvail()
    {
        return analogCableAvail;
    }

    public void setAnalogCableAvail( final int analogCableAvail )
    {
        this.analogCableAvail = analogCableAvail;
    }

    public int getDigitalCableAvail()
    {
        return digitalCableAvail;
    }

    public void setDigitalCableAvail( final int digitalCableAvail )
    {
        this.digitalCableAvail = digitalCableAvail;
    }

    public int getHDTVAvail()
    {
        return hdtvAvail;
    }

    public void setHDTVAvail( final int hdtvAvail )
    {
        this.hdtvAvail = hdtvAvail;
    }

    public int getFiberDataDownSpeedAvail()
    {
        return fiberDataDownSpeedAvail;
    }

    public void setFiberDataDownSpeedAvail( final int fiberDataDownSpeedAvail )
    {
        this.fiberDataDownSpeedAvail = fiberDataDownSpeedAvail;
    }

    public int getFiberDataUpSpeedAvail()
    {
        return fiberDataUpSpeedAvail;
    }

    public void setFiberDataUpSpeedAvail( final int fiberDataUpSpeedAvail )
    {
        this.fiberDataUpSpeedAvail = fiberDataUpSpeedAvail;
    }

    public int getWiredDataUpSpeedAvail()
    {
        return wiredDataUpSpeedAvail;
    }

    public void setWiredDataUpSpeedAvail( final int wiredDataUpSpeedAvail )
    {
        this.wiredDataUpSpeedAvail = wiredDataUpSpeedAvail;
    }

    public int getWiredDataDownSpeedAvail()
    {
        return wiredDataDownSpeedAvail;
    }

    public void setWiredDataDownSpeedAvail( final int wiredDataDownSpeedAvail )
    {
        this.wiredDataDownSpeedAvail = wiredDataDownSpeedAvail;
    }

    public int getWirelessDataDownSpeedAvail()
    {
        return wirelessDataDownSpeedAvail;
    }

    public void setWirelessDataDownSpeedAvail( final int wirelessDataDownSpeedAvail )
    {
        this.wirelessDataDownSpeedAvail = wirelessDataDownSpeedAvail;
    }

    public int getWirelessDataUpSpeedAvail()
    {
        return wirelessDataUpSpeedAvail;
    }

    public void setWirelessDataUpSpeedAvail( final int wirelessDataUpSpeedAvail )
    {
        this.wirelessDataUpSpeedAvail = wirelessDataUpSpeedAvail;
    }

    public int getDialUpInternetAvail()
    {
        return dialUpInternetAvail;
    }

    public void setDialUpInternetAvail( final int dialUpInternetAvail )
    {
        this.dialUpInternetAvail = dialUpInternetAvail;
    }

    public int getElectricityAvail()
    {
        return electricityAvail;
    }

    public void setElectricityAvail( final int electricityAvail )
    {
        this.electricityAvail = electricityAvail;
    }

    public int getNaturalGasAvail()
    {
        return naturalGasAvail;
    }

    public void setNaturalGasAvail( final int naturalGasAvail )
    {
        this.naturalGasAvail = naturalGasAvail;
    }

    public int getLocalPhoneAvail()
    {
        return localPhoneAvail;
    }

    public void setLocalPhoneAvail( final int localPhoneAvail )
    {
        this.localPhoneAvail = localPhoneAvail;
    }

    public int getLongDistancePhoneAvail()
    {
        return longDistancePhoneAvail;
    }

    public void setLongDistancePhoneAvail( final int longDistancePhoneAvail )
    {
        this.longDistancePhoneAvail = longDistancePhoneAvail;
    }

    public int getHomeWireProtectionAvail()
    {
        return homeWireProtectionAvail;
    }

    public void setHomeWireProtectionAvail( final int homeWireProtectionAvail )
    {
        this.homeWireProtectionAvail = homeWireProtectionAvail;
    }

    public int getWirelessPhoneAvail()
    {
        return wirelessPhoneAvail;
    }

    public void setWirelessPhoneAvail( final int wirelessPhoneAvail )
    {
        this.wirelessPhoneAvail = wirelessPhoneAvail;
    }

    public int getWasteRemovalAvail()
    {
        return wasteRemovalAvail;
    }

    public void setWasteRemovalAvail( final int wasteRemovalAvail )
    {
        this.wasteRemovalAvail = wasteRemovalAvail;
    }

    public int getLocalNewspaperAvail()
    {
        return localNewspaperAvail;
    }

    public void setLocalNewspaperAvail( final int localNewspaperAvail )
    {
        this.localNewspaperAvail = localNewspaperAvail;
    }

    public int getNationalNewspaperAvail()
    {
        return nationalNewspaperAvail;
    }

    public void setNationalNewspaperAvail( final int nationalNewspaperAvail )
    {
        this.nationalNewspaperAvail = nationalNewspaperAvail;
    }

    public int getHomeSecurityAvail()
    {
        return homeSecurityAvail;
    }

    public void setHomeSecurityAvail( final int homeSecurityAvail )
    {
        this.homeSecurityAvail = homeSecurityAvail;
    }

    public int getWaterAvail()
    {
        return waterAvail;
    }

    public void setWaterAvail( final int waterAvail )
    {
        this.waterAvail = waterAvail;
    }

    public int getOffersAvail()
    {
        return offersAvail;
    }

    public void setOffersAvail( final int offersAvail )
    {
        this.offersAvail = offersAvail;
    }
    
    public int getVoipAvail()
    {
        return voipAvail;
    }

    public void setVoipAvail( final int voipAvail )
    {
        this.voipAvail = voipAvail;
    }

    public int getIptvAvail()
    {
        return iptvAvail;
    }

    public void setIptvAvail( final int iptvAvail )
    {
        this.iptvAvail = iptvAvail;
    }

    public int getWarrantyAvail()
    {
        return warrantyAvail;
    }

    public void setWarrantyAvail( final int warrantyAvail )
    {
        this.warrantyAvail = warrantyAvail;
    }

    public int getEnergyManagementAvail()
    {
        return energyManagementAvail;
    }

    public void setEnergyManagementAvail( final int energyManagementAvail )
    {
        this.energyManagementAvail = energyManagementAvail;
    }
    
    // New Additions - Ticket #53213 IP-DSLAM
    public int getIpDslamDataDownSpeedAvail()
    {
        return ipDslamDataDownSpeedAvail;
    }

    public void setIpDslamDataDownSpeedAvail( final int ipDslamDataDownSpeedAvail )
    {
        this.ipDslamDataDownSpeedAvail = ipDslamDataDownSpeedAvail;
    }

    public int getIpDslamDataUpSpeedAvail()
    {
        return ipDslamDataUpSpeedAvail;
    }

    public void setIpDslamDataUpSpeedAvail( final int ipDslamDataUpSpeedAvail )
    {
        this.ipDslamDataUpSpeedAvail = ipDslamDataUpSpeedAvail;
    }
    
    public int getIpDslamVoipAvail()
    {
        return ipDslamVoipAvail;
    }

    public void setIpDslamVoipAvail( final int ipDslamVoipAvail )
    {
        this.ipDslamVoipAvail = ipDslamVoipAvail;
    }
    
    public int getIpDslamIptvAvail()
    {
        return ipDslamIptvAvail;
    }

    public void setIpDslamIptvAvail( final int ipDslamIptvAvail )
    {
        this.ipDslamIptvAvail = ipDslamIptvAvail;
    }
    
    /**
     * Utility Function to return a string representation of the capabilities currently set.
     * @return String list of capabilities.
     */
    public List<String> getCapabilitesStringList()
    {
        ArrayList<String> results = new ArrayList<String>();
    
        if ( satelliteAvail != 0 )
        {
            results.add( SATELLITE_AVAIL_STRING );
        }
        
        if ( analogCableAvail != 0 )
        {
            results.add( ANALOG_CABLE_AVAIL_STRING );
        }
        
        if ( digitalCableAvail != 0 )
        {
            results.add( DIGITAL_CABLE_AVAIL_STRING );
        }

        if ( hdtvAvail != 0 )
        {
            results.add(  HDTV_AVAIL_STRING  );
        }
        
        if ( fiberDataDownSpeedAvail != 0 )
        {
            results.add( FIBER_DATA_DOWN_SPEED_AVAIL_STRING );
        }
        if ( fiberDataUpSpeedAvail != 0 )
        {
            results.add( FIBER_DATA_UP_SPEED_AVAIL_STRING );
        }
        
        if ( wiredDataUpSpeedAvail != 0 )
        {
            results.add( WIRED_DATA_UP_SPEED_AVAIL_STRING  );
        }   
        
        if ( wiredDataDownSpeedAvail != 0 )
        {
            results.add( WIRED_DATA_DOWN_SPEED_AVAIL_STRING );
        }
        
        if ( wirelessDataDownSpeedAvail != 0 )
        {
            results.add( WIRELESS_DATA_DOWN_SPEED_AVAIL_STRING );
        }
        
        if ( wirelessDataDownSpeedAvail != 0 )
        {
            results.add( WIRELESS_DATA_UP_SPEED_AVAIL_STRING );
        }
        
        if ( dialUpInternetAvail != 0 )
        {
            results.add( DIAL_UP_INTERNET_AVAIL_STRING );
        }
        
        if ( electricityAvail != 0 )
        {
            results.add( ELECTRICITY_AVAIL_STRING );
        }
        
        if ( naturalGasAvail != 0 )
        {
            results.add( NATURAL_GAS_AVAIL_STRING );
        }
        
        if ( localPhoneAvail != 0 )
        {
            results.add( LOCAL_PHONE_AVAIL_STRING );
        }
        
        if ( longDistancePhoneAvail != 0 )
        {
            results.add( LONG_DISTANCE_PHONE_AVAIL_STRING );
        }
        
        if ( homeWireProtectionAvail != 0 )
        {
            results.add( HOME_WIRE_PROTECTION_AVAIL_STRING );
        }
        
        if ( wirelessPhoneAvail != 0 )
        {
            results.add( WIRELESS_PHONE_AVAIL_STRING );
        }
        
        if ( wasteRemovalAvail != 0 )
        {
            results.add( WASTE_REMOVAL_AVAIL_STRING );
        }
        
        if ( localNewspaperAvail != 0 )
        {
            results.add( LOCAL_NEWSPAPER_AVAIL_STRING );
        }
        
        if ( nationalNewspaperAvail != 0 )
        {
            results.add( NATIONAL_NEWSPAPER_AVAIL_STRING );
        }
        
        if ( homeSecurityAvail != 0 )
        {
            results.add( HOME_SECURITY_AVAIL_STRING );
        }
        
        if ( waterAvail != 0 )
        {
            results.add( WATER_AVAIL_STRING );
        }
        
        if ( offersAvail != 0 )
        {
            results.add( OFFERS_AVAIL_STRING );
        }
        
        if ( voipAvail != 0 )
        {
            results.add( VOIP_AVAIL_STRING );
        }
        
        if ( iptvAvail != 0 )
        {
            results.add( IPTV_AVAIL_STRING );
        }
        
        if ( warrantyAvail != 0 )
        {
            results.add( WARRANTY_AVAIL_STRING );
        }
        
        if ( energyManagementAvail != 0 )
        {
            results.add( ENERGY_MANAGEMENT_AVAIL_STRING );
        }

        // New Additions - Ticket #53213 IP-DSLAM
        if ( ipDslamDataDownSpeedAvail != 0 )
        {
            results.add( IPDSLAM_DATA_DOWN_SPEED_AVAIL_STRING );
        }
        
        if ( ipDslamDataUpSpeedAvail != 0 )
        {
            results.add( IPDSLAM_DATA_UP_SPEED_AVAIL_STRING );
        }
        
        if ( ipDslamVoipAvail != 0 )
        {
            results.add( IPDSLAM_VOIP_AVAIL_STRING );
        }
        
        if ( ipDslamIptvAvail != 0 )
        {
            results.add( IPDSLAM_IPTV_AVAIL_STRING );
        }
        
        return results;
    }
   
    /**
     * 
     * {@inheritDoc}
     */
    public String toString()
    {
        String result = "Has: ";
        for ( String value : getCapabilitesStringList() )
        {
            result = result + " " + value;
        }
        
        return result;
    }
    
    
    /**
     * Map capabilities from individual market items/ items to a bundle containing them.
     *
     * @return the product base capabilities
     */
    public ProductBase getProductBaseCapabilities()
    {
        ProductBase productBase = new ProductBase();
        
        if ( this.getAnalogCableAvail() > 0 )
        {    
            productBase.setAnalogCableAvail( this.getAnalogCableAvail() );
        }
        if ( this.getDialUpInternetAvail() > 0 )
        {
            productBase.setDialUpInternetAvail( this.getDialUpInternetAvail() );
        }
        if ( this.getDigitalCableAvail() > 0 )
        {
            productBase.setDigitalCableAvail( this.getDigitalCableAvail() );
        }
        if ( this.getElectricityAvail() > 0 )
        {        
            productBase.setElectricityAvail( this.getElectricityAvail() );
        }
        if ( this.getEnergyManagementAvail() > 0 )
        {
            productBase.setEnergyManagementAvail( this.getEnergyManagementAvail() );
        }
        if ( this.getFiberDataDownSpeedAvail() > 0 )
        {
            productBase.setFiberDataDownSpeedAvail( this.getFiberDataDownSpeedAvail() );
        }
        if ( this.getFiberDataUpSpeedAvail() > 0 )
        {
            productBase.setFiberDataUpSpeedAvail( this.getFiberDataUpSpeedAvail() );
        }
        if ( this.getHDTVAvail() > 0 )
        {
            productBase.setHDTVAvail( this.getHDTVAvail() );
        }
        if ( this.getHomeSecurityAvail() > 0 )
        {
            productBase.setHomeSecurityAvail( this.getHomeSecurityAvail() );
        }
        if ( this.getHomeWireProtectionAvail() > 0 )
        {
            productBase.setHomeWireProtectionAvail( this.getHomeWireProtectionAvail() );
        }
        if ( this.getIptvAvail() > 0 )
        {
            productBase.setIptvAvail( this.getIptvAvail() );
        }
        if ( this.getLocalNewspaperAvail() > 0 )
        {
            productBase.setLocalNewspaperAvail( this.getLocalNewspaperAvail() );
        }
        if ( this.getLocalPhoneAvail() > 0 )
        {
            productBase.setLocalPhoneAvail( this.getLocalPhoneAvail() );
        }
        if ( this.getLongDistancePhoneAvail() > 0 )
        {
            productBase.setLongDistancePhoneAvail( this.getLongDistancePhoneAvail() );
        }
        if ( this.getNationalNewspaperAvail() > 0 )
        {
            productBase.setNationalNewspaperAvail( this.getNationalNewspaperAvail() );
        }
        if ( this.getNaturalGasAvail() > 0 )
        {
            productBase.setNaturalGasAvail( this.getNaturalGasAvail() );
        }
        if ( this.getOffersAvail() > 0 )
        {
            productBase.setOffersAvail( this.getOffersAvail() );
        }
        if ( this.getSatelliteAvail() > 0 )
        {
            productBase.setSatelliteAvail( this.getSatelliteAvail() );
        }
        if ( this.getVoipAvail() > 0 )
        {
            productBase.setVoipAvail( this.getVoipAvail() );
        }
        if ( this.getWarrantyAvail() > 0 )
        {
            productBase.setWarrantyAvail( this.getWarrantyAvail() );
        }
        if ( this.getWasteRemovalAvail() > 0 )
        {
            productBase.setWasteRemovalAvail( this.getWasteRemovalAvail() );
        }
        if ( this.getWaterAvail() > 0 )
        {
            productBase.setWaterAvail( this.getWaterAvail() );
        }
        if ( this.getWiredDataDownSpeedAvail() > 0 )
        {
            productBase.setWiredDataDownSpeedAvail( this.getWiredDataDownSpeedAvail() );
        }
        if ( this.getWiredDataUpSpeedAvail() > 0 )
        {
            productBase.setWiredDataUpSpeedAvail( this.getWiredDataUpSpeedAvail() );
        }
        if ( this.getWirelessDataDownSpeedAvail() > 0 )
        {
            productBase.setWirelessDataDownSpeedAvail( this.getWirelessDataDownSpeedAvail() );
        }
        if ( this.getWirelessDataUpSpeedAvail() > 0 )
        {
            productBase.setWirelessDataUpSpeedAvail( this.getWirelessDataUpSpeedAvail() );
        }
        if ( this.getWirelessPhoneAvail() > 0 )
        {
            productBase.setWirelessPhoneAvail( this.getWirelessPhoneAvail() );
        }
        
        if ( this.getIpDslamDataDownSpeedAvail() > 0 )
        {
            productBase.setIpDslamDataDownSpeedAvail( this.getIpDslamDataDownSpeedAvail() );
        }
        if ( this.getIpDslamDataUpSpeedAvail() > 0 )
        {
            productBase.setIpDslamDataUpSpeedAvail( this.getIpDslamDataUpSpeedAvail() );
        }
        if ( this.getIpDslamIptvAvail() > 0 )
        {
            productBase.setIpDslamIptvAvail( this.getIpDslamIptvAvail() );
        }
        if ( this.getIpDslamVoipAvail() > 0 )
        {
            productBase.setIpDslamVoipAvail( this.getIpDslamVoipAvail() );
        }
        productBase.setOfferType( OfferTypeEnum.NEW );
        return productBase;
    }
}
