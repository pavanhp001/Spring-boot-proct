package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 *
 */

@Entity
@Table( name = "answer" )
public class AnswerBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8377166010970892305L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "answerBeanSequence" )
    @SequenceGenerator( name = "answerBeanSequence", sequenceName = "ANSWER_BEAN_SEQ" ,allocationSize = 1)
    private long id;

    @OneToOne
    @ForeignKey( name = "QUESTION_FK01" )
    private QuestionBean question;
    
    private String dataType;
    private String stringValue;
    private Integer integerValue;
    private Boolean booleanValue;

    /**
     *
     */
    public AnswerBean()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setId( final long id )
    {
        this.id = id;
    }

    /**
     *
     * @return the type of the field related to this question
     */
    public final String getDataType()
    {
        return dataType;
    }

    /**
     *
     * @param dataType the type of the field related to this question
     */
    public final void setDataType( final String dataType )
    {
        this.dataType = dataType;
    }

    public String getStringValue() 
    {
        return stringValue;
    }

    public void setStringValue( final String stringValue ) 
    {
        this.stringValue = stringValue;
    }

    public Integer getIntegerValue() 
    {
        return integerValue;
    }

    public void setIntegerValue( final Integer integerValue ) 
    {
        this.integerValue = integerValue;
    }

    public Boolean getBooleanValue() 
    {
        return booleanValue;
    }

    public void setBooleanValue( final Boolean booleanValue ) 
    {
        this.booleanValue = booleanValue;
    }

    public QuestionBean getQuestion() 
    {
        return question;
    }

    public void setQuestion( final QuestionBean question ) 
    {
        this.question = question;
    }
}
