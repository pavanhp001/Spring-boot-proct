


package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.beans.PointInTimeSuperClass;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 *
 */

@Entity
@Table( name = "question" )
public class QuestionBean extends PointInTimeSuperClass implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4778710557766821309L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "questionBeanSequence" )
    @SequenceGenerator( name = "questionBeanSequence", sequenceName = "QUESTION_BEAN_SEQ",allocationSize = 1 )
    private long id;

    private String questionText;

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

    public String getQuestionText() 
    {
        return questionText;
    }

    public void setQuestionText( final String questionText ) 
    {
        this.questionText = questionText;
    }
}
