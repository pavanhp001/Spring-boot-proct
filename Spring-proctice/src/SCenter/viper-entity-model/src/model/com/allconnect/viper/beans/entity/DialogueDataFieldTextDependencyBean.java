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
@Table( name = "DG_TEXT_DFLD" )
@org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class DialogueDataFieldTextDependencyBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3934040840486703465L;

	@Id
    @GeneratedValue( generator = "ddfTextDfBeanSequence" )
    @SequenceGenerator( name = "ddfTextDfBeanSequence", sequenceName = "DGTEXT_DF_BEAN_SEQ" )
    private long id;
  
    @ElementCollection
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private List<String> externalIds;

    @NotNull
    private String textAnswer;

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

    public List<String> getExternalIds()
    {
        return externalIds;
    }

    public void setExternaldIds( final List<String> externalIds )
    {
        this.externalIds = externalIds;
    }

    public String getTextAnswer()
    {
        return textAnswer;
    }

    public void setTextAnswer( final String textAnswer )
    {
        this.textAnswer = textAnswer;
    }
}
