/**
 *
 */
package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;
//import org.jboss.seam.annotations.Out;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 *
 */

@Entity
@Table( name = "appProfile" )
public class AppProfileBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7918178301698622659L;

	@Id
    @GeneratedValue( generator = "appProfileBeanSequence" )
    @SequenceGenerator( name = "appProfileBeanSequence", sequenceName = "APP_PROFILE_BEAN_SEQ" )
    private long id;
    
    private String appName;
    
    private String dynamicRegionId;

    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    //@Out
    private List<String> toolBar;
    
    /**
     * Default Constructor.
     */
    public AppProfileBean()
    {
    }  
       
    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public void setId( final long id )
    {
        this.id = id;
    }

    public String getAppName()
    {
        return appName;
    }

    public void setAppName( final String appName )
    {
        this.appName = appName;
    }

    public List<String> getToolBar()
    {
        return toolBar;
    }
    
    public String getDynamicRegionId()
    {
        return dynamicRegionId;
    }
    
    public void setDynamicRegionId( final String dynamicRegionId )
    {
        this.dynamicRegionId = dynamicRegionId;
    }
}
