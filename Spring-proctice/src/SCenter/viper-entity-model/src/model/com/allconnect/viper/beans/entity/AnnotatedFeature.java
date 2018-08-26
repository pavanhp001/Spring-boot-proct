package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 *
 */

@Entity
@Table( name = "annotatedFeature" )
public class AnnotatedFeature implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5935681531021495799L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "annotatedFeatureBeanSequence" )
    @SequenceGenerator( name = "annotatedFeatureBeanSequence", sequenceName = "ANNOTATED_FEATURE_BEAN_SEQ" ,allocationSize = 1)
    private long id;

    @OneToOne
    @ForeignKey( name = "ANNOT_FEATURE_FK01" )
    private FeatureBean featureBean;
    @OneToOne
    @ForeignKey( name = "ANNOT_FEATURE_FK02" )
    private QuestionBean questionBean;
    @OneToOne
    @ForeignKey( name = "ANNOT_FEATURE_FK03" )
    private AnswerBean answerBean;

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
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

    public final FeatureBean getFeature()
    {
        return featureBean;
    }

    public final void setFeature( final FeatureBean featureBean )
    {
        this.featureBean = featureBean;
    }

    public QuestionBean getQuestionBean() 
    {
        return questionBean;
    }

    public void setQuestionBean( final QuestionBean questionBean ) 
    {
        this.questionBean = questionBean;
    }

    public AnswerBean getAnswerBean() 
    {
        return answerBean;
    }

    public void setAnswerBean( final AnswerBean answerBean ) 
    {
        this.answerBean = answerBean;
    }
}
