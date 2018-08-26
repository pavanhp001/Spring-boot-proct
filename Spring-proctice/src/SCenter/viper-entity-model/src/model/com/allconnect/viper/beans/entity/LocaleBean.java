package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 *
 */

@Entity
@Table( name = "locale" )
public class LocaleBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4379260116182602808L;

	@Id
    @GeneratedValue( generator = "localeBeanSequence" )
    @SequenceGenerator( name = "localeBeanSequence", sequenceName = "LOCALE_BEAN_SEQ" )
    private long id;

    private String name;
    private float latitude;
    private float longitude;
    private String type;
    private String geoCode;
    private String defaultCurrency;
    private String timeZone;
    private Boolean daylightSavingsObserved;

    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> languages;

    private String defaultLanguage;

    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<LocaleBean> stateOrProvinceList;

 
    /**
     *
     */
    public LocaleBean()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setId( final long id )
    {
        this.id = id;
    }


    public final String getName()
    {
        return name;
    }

    public final void setName( final String name )
    {
        this.name = name;
    }

    public final float getLatitude()
    {
        return latitude;
    }

    public final void setLatitude( final float latitude )
    {
        this.latitude = latitude;
    }

    public final float getLongitude()
    {
        return longitude;
    }

    public final void setLongitude( final float longitude )
    {
        this.longitude = longitude;
    }

    public final String getType()
    {
        return type;
    }

    public final void setType( final String type )
    {
        this.type = type;
    }


    public final String getGeoCode()
    {
        return geoCode;
    }

    public final void setGeoCode( final String geoCode )
    {
        this.geoCode = geoCode;
    }

    public final String getDefaultCurrency()
    {
        return defaultCurrency;
    }

    public final void setDefaultCurrency( final String defaultCurrency )
    {
        this.defaultCurrency = defaultCurrency;
    }

    public final String getTimeZone()
    {
        return timeZone;
    }

    public final void setTimeZone( final String timeZone )
    {
        this.timeZone = timeZone;
    }

    public final Boolean getDaylightSavingsObserved()
    {
        return daylightSavingsObserved;
    }

    public final void setDaylightSavingsObserved( final Boolean observed )
    {
        daylightSavingsObserved = observed;
    }

    public final List<String> getLanguages()
    {
        return languages;
    }

    public final void setLanguages( final List<String> languages )
    {
        this.languages = languages;
    }

    public final String getDefaultLanguage()
    {
        return defaultLanguage;
    }

    public final void setDefaultLanguage( final String defaultLanguage )
    {
        this.defaultLanguage = defaultLanguage;
    }

    public final List<LocaleBean> getStateOrProvinceList()
    {
        return stateOrProvinceList;
    }

    public final void setStateOrProvinceList( final List<LocaleBean> stateOrProvinceList )
    {
        this.stateOrProvinceList = stateOrProvinceList;
    }
}
