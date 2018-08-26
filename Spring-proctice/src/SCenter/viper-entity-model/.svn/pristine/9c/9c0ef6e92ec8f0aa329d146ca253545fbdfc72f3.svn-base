package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * An entity that combines a text 'question' with a target 'answer' field, among other things.
 * 
 * @author jgerhard
 * 
 */
@Entity
@Table( name = "DG_DFLD_DEP" )
@org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class DialogueDataFieldDependencyBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8185431392420784903L;

	@Id
    @GeneratedValue( generator = "ddfdfBeanSequence" )
    @SequenceGenerator( name = "ddfdfBeanSequence", sequenceName = "DDF_DF_BEAN_SEQ" )
    private long id;
    
    @NotNull
    private String type; 
    
    @ElementCollection
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private List<DialogueDataFieldTextDependencyBean> dataFields;

    /**
     * External ID of parent data field.
     */
    @NotNull
    private String externalId;

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

    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId( final String externalId )
    {
        this.externalId = externalId;
    }

    public List<DialogueDataFieldTextDependencyBean> getDataFields()
    {
        return dataFields;
    }

    public void setDataFields( final List<DialogueDataFieldTextDependencyBean> dataFields )
    {
        this.dataFields = dataFields;
    }

    public String getType()
    {
        return type;
    }

    public void setType( final String type )
    {
        this.type = type;
    }

}
