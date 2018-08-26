package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * An entity to hold a simple snippet of text, meant to be used in dialogues with a client.
 * 
 * @author rchapple
 * 
 */
@Entity
@Table( name = "DG_TEXT" )
@org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class DialogueTextBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8987722061149524510L;

	@Id
    @GeneratedValue( generator = "dtBeanSequence" )
    @SequenceGenerator( name = "dtBeanSequence", sequenceName = "DT_BEAN_SEQ" )
    private long id;

    /**
     * Simply, the text value of this entity. Typically, it will represent the verbiage of a question to be asked/answered, but it
     * could also just be some amount of text to be displayed.
     */
    @Column( length = 1024 )
    private String text;

    /**
     * Variable that will be used to store an externally-valid value that will be used to retrieve content from some other (as yet
     * undecided) system. Something like a content management system, etc. Using a String instead of something more DB-like (a Long,
     * perhaps), because I'm not sure what the 'key' will end up looking like, and besides a String can always be parsed into a
     * Long.
     */
    private String contentRefId;

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

    public String getText()
    {
        return text;
    }

    public void setText( final String text )
    {
        this.text = text;
    }

    public String getContentRefId()
    {
        return contentRefId;
    }

    public void setContentRefId( final String contentRefId )
    {
        this.contentRefId = contentRefId;
    }

}
