package com.A.V.beans.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.MapKey;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * A DataGroup represents the lowest level of composition in the Dialog datamodel. DataFields are grouped (in order) to create
 * (potentially) reusable groups of DataFields.
 * 
 * @author rchapple
 * 
 */
@Entity
@Table( name = "DG_DATAGROUP" )
@org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class DialogueDataGroupBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -705535975489577877L;

	@Id
    @GeneratedValue( generator = "ddgBeanSequence" )
    @SequenceGenerator( name = "ddgBeanSequence", sequenceName = "DDG_BEAN_SEQ" )
    private long id;

    /*@ManyToMany
    @ForeignKey( name = "D_DATAGROUP_FK01", inverseName = "D_DATAGROUP_FK02" )
    @Cascade( { CascadeType.PERSIST, CascadeType.SAVE_UPDATE } )
    private List<TagBean> tags;*/
    
    @ElementCollection
    @MapKey( columns = @Column( name = "name" ) )
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )    
    private Map<String, String> tags;

    /**
     * These are the actual data fields that are contained in this Group, in order.
     */
    @ManyToMany
    @IndexColumn( name = "listOrder", base = 0 )
    @JoinTable( name = "DG_DataGroup_DataField" )
    @ForeignKey( name = "D_DATAGROUP_FK03", inverseName = "D_DATAGROUP_FK04" )
    @Cascade( { CascadeType.PERSIST, CascadeType.SAVE_UPDATE } )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private List<DialogueDataFieldBean> dataFields;
    
    @ElementCollection
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private List<DialogueDataFieldDependencyBean> dependencies;

    /**
     * A human referenceable name for this group, i.e. "Basic Info".
     */
    private String name;
    
    private String displayName;

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

    public List<DialogueDataFieldBean> getDataFields()
    {
        return dataFields;
    }

    public void setDataFields( final List<DialogueDataFieldBean> dataFields )
    {
        this.dataFields = dataFields;
    }

    public String getName()
    {
        return name;
    }

    public void setName( final String name )
    {
        this.name = name;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName( final String displayName )
    {
        this.displayName = displayName;
    }

    public Map<String, String> getTags()
    {
        return tags;
    }

    public void setTags( final Map<String, String> tags )
    {
        this.tags = tags;
    }

    public List<DialogueDataFieldDependencyBean> getDependencies()
    {
        return dependencies;
    }

    public void setDependencies( final List<DialogueDataFieldDependencyBean> dependencies )
    {
        this.dependencies = dependencies;
    }

}
