package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author jgerhard
 * 
 */

@Entity
@Table( name = "SALES_CONTEXT" )

public class SalesContextBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7682180772239987501L;

	@Id
    @GeneratedValue( generator = "salesContextBeanSequence" )
    @SequenceGenerator( name = "salesContextBeanSequence", sequenceName = "SLS_CTX_BEAN_SEQ" )
    private long id;
    
    private String guid;
    
    @OneToMany( cascade = CascadeType.ALL , mappedBy = "salesContextId" )
    private List<SalesContextAttributeBean> attributes;
    
    public long getId()
    {
        return id;
    }
    public void setId( final long id )
    {
        this.id = id;
    }
    public void setAttributes( final List<SalesContextAttributeBean> attributes )
    {
        this.attributes = attributes;
    }
    public List<SalesContextAttributeBean> getAttributes()
    {
        return attributes;
    }
    public String getGuid()
    {
        return guid;
    }
    public void setGuid( final String guid )
    {
        this.guid = guid;
    }
}
