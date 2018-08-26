package com.A.V.beans.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.MapKey;
import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * An set of criteria that users of the Dialogue service can use want to search for appropriate Dialogues for a given set of
 * criteria.
 * 
 * @author rchapple
 * 
 */
@Entity
@Table( name = "DG_CRITERIA" )
@org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class DialogueCriteria implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7906680276864525550L;
	public static final String NAME_PARAM = "name";
    public static final String TYPE_PARAM = "dialogueType";
    public static final String CATEGORY_PARAM = "serviceCategory";
    public static final String REFERRER_PARAM = "referrer";
    public static final String CHANNEL_PARAM = "channel";

    @Id
    @GeneratedValue( generator = "dialogueCriSequence" )
    @SequenceGenerator( name = "dialogueCriSequence", sequenceName = "DIALOGUE_CRI_SEQ" )
    private long id;

    @ManyToOne
    @JoinTable( name = "DG_DIALOGUE_CRITERIA" )
    @ForeignKey( name = "DG_CRITERIA_FK01", inverseName = "DG_CRITERIA_FK02" )
    @NotNull
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private DialogueBean targetDialogue;

    @ElementCollection
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @MapKey( columns = @Column( name = "name" ) )
    // @org.hibernate.annotations.BatchSize( size = FVB_BATCH_SIZE )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private Map<String, String> criteria;
    //orderSource.referrer=123
    //orderSource.channel=web
    //orderSource.serviceCategory=localPhone
    //have to escape . notation MVEL allows direct access to this Map's keys criteriaNameValue.referrer
    
    /**
     * Default Constructor.
     */
    public DialogueCriteria()
    {

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

    public DialogueBean getTargetDialogue()
    {
        return targetDialogue;
    }

    public void setTargetDialogue( final DialogueBean targetDialogue )
    {
        this.targetDialogue = targetDialogue;
    }

    public Map<String, String> getCriteria()
    {
        return criteria;
    }

    public void setCriteria( final Map<String, String> criteria )
    {
        this.criteria = criteria;
    }

}
