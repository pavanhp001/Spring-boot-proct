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
import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * Top-level entity for the Dialogue service. This holds all the DataGroups which, in turn, hold all the DataFields, which are the
 * atomic unit of dialogue with the client. This is used to catalog dialogues in transactional database so that we can keep a record
 * of what dialogues were displayed to a customer for an order identified by guid.
 *
 * @author gdesai
 *
 */
@Entity
@Table( name = "DG_DIALOGUE" )
@org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class DialogueCatalogBean implements CommonBeanInterface, Comparable<DialogueCatalogBean>
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3706959502444185667L;


	@Id
    @GeneratedValue( generator = "dialogueBeanSequence" )
    @SequenceGenerator( name = "dialogueBeanSequence", sequenceName = "DIALOGUE_BEAN_SEQ" )
    private long id;


    private String guid;

    @Column( name="product_external_id")
    private String productExternalId;

    /**
     * Compare to.
     *
     * @param compare the compare
     * @return the int
     */
    public int compareTo( final DialogueCatalogBean compare )
    {
        Integer priorityInt = new Integer( this.priority );
        return priorityInt.compareTo( new Integer( compare.getPriority() ) );
    }

    /**
     * The externalId of this dialogue. It *should* be unique,
     * but we don't want to enforce that at the data layer.
     * If there are issues,
     * that's a "data" problem, and should be handled there.
     */
    @NotNull
    private String externalId;

    private String name;

    private int priority;
    /**
     * These are the actual data fields that are contained in this Group, in order.
     */
    @ManyToMany
    @IndexColumn( name = "listOrder", base = 0 )
    @JoinTable( name = "DG_DIALOGUE_DATAGROUP" )
    @ForeignKey( name = "DIALOGUE_FK01", inverseName = "DIALOGUE_FK02" )
    @Cascade( { CascadeType.PERSIST, CascadeType.SAVE_UPDATE } )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private List<DialogueDataGroupBean> dataGroups;

    /**
     * A referenced set of criteria that enable search/retrieval of DialogueBeans.
     */

    @ElementCollection
    @MapKey( columns = @Column( name = "externalId" ) )
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private Map<String, String> tags;

    public List<DialogueDataGroupBean> getDataGroups()
    {
        return dataGroups;
    }

    public void setDataGroups( final List<DialogueDataGroupBean> dataGroups )
    {
        this.dataGroups = dataGroups;
    }

    @Override
    public long getId()
    {
        return this.id;
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

    public void setExternalId( final String name )
    {
        this.externalId = name;
    }

    public Map<String, String> getTags()
    {
        return tags;
    }

    public void setTags( final Map<String, String> tags )
    {
        this.tags = tags;
    }

    public String getName()
    {
        return name;
    }

    public void setName( final String name )
    {
        this.name = name;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority( final int priority )
    {
        this.priority = priority;
    }

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getProductExternalId() {
		return productExternalId;
	}

	public void setProductExternalId(String productExternalId) {
		this.productExternalId = productExternalId;
	}
}
