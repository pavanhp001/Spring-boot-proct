package com.A.V.beans.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.MapKey;
import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * An entity that combines a text 'question' with a target 'answer' field, among other things.
 * 
 * @author rchapple
 * 
 */
@Entity
@Table( name = "DG_DATAFIELD" )
@org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class DialogueDataFieldBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4193013056539270467L;

	@Id
    @GeneratedValue( generator = "ddfBeanSequence" )
    @SequenceGenerator( name = "ddfBeanSequence", sequenceName = "DDF_BEAN_SEQ" )
    private long id;

    /*
     * @ManyToMany
     * 
     * @ForeignKey( name = "DATA_FIELD_FK01", inverseName = "DATA_FIELD_FK02" )
     * 
     * @Cascade( { CascadeType.ALL } ) private List<TagBean> tags;
     */

    @ElementCollection
    @MapKey( columns = @Column( name = "name" ) )
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private Map<String, String> tags;
    /**
     * The name of this datafield. At this low level, it might not be expected to be unique.
     */
    private String name;

    /**
     * Unique name to group datafields into logical group.
     */
    private String displayGroup;
    /**
     * External ID of data field, replaces PCODE in functionality.
     */
    @NotNull
    private String externalId;

    @NotNull
    private String type;

    /**
     * External ID of feature, if type = "feature".
     */

    private String featureExternalId;

    private String infoType;

    /**
     * The fieldText is the actual text of the field -- this is usually in the form of a question or something that at least
     * describes a choice or action that needs to be taken, and should elicit a response from the reader.
     */
    @ManyToOne
    @ForeignKey( name = "DATA_FIELD_FK03" )
    @Cascade( { CascadeType.PERSIST, CascadeType.SAVE_UPDATE } )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private DialogueTextBean fieldText;

    /**
     * The valueTarget is the 'location' where any data gathered as a response to this field will be stored. This 'location' will
     * most likely be an reference to abstract data model. Something in 'dot formation', like 'customer.lastname'. This is somewhat
     * like a more lexically constrained version of PCodes. {gasp}
     */
    private String valueTarget;

    /**
     * The valueType will be used as a strong-ish typing mechanism for specifiying what type of data a field expects to
     * contain/accept. Eventually, this will contain something more substantial, like a relation to another object that contains a
     * template and/or validation rules.
     */
    private String fieldType;

    private String description;

    private DCValueBean dataConstraint;

    private Boolean enabled;

    private String validation;

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

    public String getName()
    {
        return name;
    }

    public void setName( final String name )
    {
        this.name = name;
    }

    public DialogueTextBean getFieldText()
    {
        return fieldText;
    }

    public void setFieldText( final DialogueTextBean fieldText )
    {
        this.fieldText = fieldText;
    }

    public String getValueTarget()
    {
        return valueTarget;
    }

    public void setValueTarget( final String valueTarget )
    {
        this.valueTarget = valueTarget;
    }

    public String getFieldType()
    {
        return fieldType;
    }

    public void setFieldType( final String fieldType )
    {
        this.fieldType = fieldType;
    }

    public Map<String, String> getTags()
    {
        return tags;
    }

    public void setTags( final Map<String, String> tags )
    {
        this.tags = tags;
    }

    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId( final String externalId )
    {
        this.externalId = externalId;
    }

    public void setDataConstraint( final DCValueBean dataConstraint )
    {
        this.dataConstraint = dataConstraint;
    }

    public DCValueBean getDataConstraint()
    {
        return dataConstraint;
    }

    public Boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled( final Boolean enabled )
    {
        this.enabled = enabled;
    }

    public String getDisplayGroup()
    {
        return displayGroup;
    }

    public void setDisplayGroup( final String displayGroup )
    {
        this.displayGroup = displayGroup;
    }

    public String getType()
    {
        return type;
    }

    public void setType( final String type )
    {
        this.type = type;
    }

    public String getFeatureExternalId()
    {
        return featureExternalId;
    }

    public void setFeatureExternalId( final String featureExternalId )
    {
        this.featureExternalId = featureExternalId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( final String note )
    {
        this.description = note;
    }

    public String getInfoType()
    {
        return infoType;
    }

    public void setInfoType( final String infoType )
    {
        this.infoType = infoType;
    }

    public String getValidation()
    {
        return validation;
    }

    public void setValidation( final String validation )
    {
        this.validation = validation;
    }

}
