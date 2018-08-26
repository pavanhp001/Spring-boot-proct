package com.A.V.beans.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.IndexColumn;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author jgerhard
 *
 */
@Entity
@org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
@Table( name = "featureValueList" )

public class FeatureValueListBean implements CommonBeanInterface,Serializable
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 3810792331108039071L;

	@Id
    @GeneratedValue( generator = "featureValueListBeanSequence" )
    @SequenceGenerator( name = "featureValueListBeanSequence", sequenceName = "FT_VALUE_LIST_BEAN_SEQ" ,allocationSize = 1)
    private long id;
    
    @Basic( optional = false )
    @Column( nullable = false )
    private String externalId;

            
    @ElementCollection 
    @IndexColumn( name = "listOrder", base = 0 )
    //may need this later, slows performance now
    //@org.hibernate.annotations.BatchSize( size = 10 )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )

    private List<FeatureValueBean> featureValueBeans;

    public long getId() 
    {
        return id;
    }

    public void setId( final long id ) 
    {
        this.id = id;
    }
        
    public String getExternalId() 
    {
        return externalId;
    }

    public void setExternalId( final String externalId ) 
    {
        this.externalId = externalId;
    }

    public List<FeatureValueBean> getFeatureValueBeans() 
    {
        return featureValueBeans;
    }

    public void setFeatureValueBeans( final List<FeatureValueBean> featureValueBeans ) 
    {
        this.featureValueBeans = featureValueBeans;
    }
}
