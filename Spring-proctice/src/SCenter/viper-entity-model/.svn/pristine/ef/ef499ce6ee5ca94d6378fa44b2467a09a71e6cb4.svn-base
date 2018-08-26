package com.A.V.beans.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * 
 * @author ebaugh
 * 
 */
@Entity
@Table( name = "algorithmScale" )
public class AlgorithmScaleBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2348604518391841843L;

	private static final int MAX_VALUES = 4;

    @Id
    @GeneratedValue( generator = "algorithmScaleBeanSequence" )
    @SequenceGenerator( name = "algorithmScaleBeanSequence", sequenceName = "ALGORITHM_SCALE_BEAN_SEQ" )
    private long id;

    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<Double> lowScores;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<Double> highScores;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> colorValues;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> colorLabels;


    /**
     * Generic constructor for the AgentBean class.
     */
    public AlgorithmScaleBean()
    {
        lowScores = new ArrayList<Double>( MAX_VALUES );
        highScores = new ArrayList<Double>( MAX_VALUES );
        colorValues = new ArrayList<String>( MAX_VALUES );
        colorLabels = new ArrayList<String>( MAX_VALUES );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId( final long id )
    {
        this.id = id;
    }

    public List<Double> getLowScores()
    {
       return lowScores;
    }

    /**
     * Set the Low Score values.
     * 
     * @param lowScores
     *            new values to set.
     * @return true if successful.
     */
    public boolean setLowScores( final List<Double> lowScores )
    {
        if ( lowScores.size() != MAX_VALUES )
        {
            return false;
        }

        this.lowScores.clear();
        this.lowScores.addAll( lowScores );

        return true;
    }

    public List<Double> getHighScores()
    {
       return highScores;
    }

    /**
     * Set the High Score values.
     * 
     * @param highScores
     *            new values to set.
     * @return true if successful.
     */
    public boolean setHighScores( final List<Double> highScores )
    {
        if ( highScores.size() != MAX_VALUES )
        {
            return false;
        }

        this.highScores.clear();
        this.highScores.addAll( highScores );

        return true;
    }

    public List<String> getColorValues()
    {
        return colorValues;
    }

    /**
     * Default setter.
     * @param colorValues values to set.
     * @return false if trying to set too many values.
     */
    public boolean setColorValues( final List<String> colorValues )
    {
        if ( colorValues.size() != MAX_VALUES )
        {
            return false;
        }
        
        this.colorValues.clear();
        this.colorValues.addAll( colorValues );
        
        return true;
    }
    
    
    public List<String> getColorLabels()
    {
        return colorLabels;
    }

    /**
     * Default setter.
     * @param colorLabels values to set.
     * @return false if trying to set too many values.
     */
    public boolean setColorLabels( final List<String> colorLabels )
    {
        if ( colorLabels.size() != MAX_VALUES )
        {
            return false;
        }
        
        this.colorLabels.clear();
        this.colorLabels.addAll( colorLabels );
        
        return true;
    }
    
    
    
    /**
     * Utility function to return a color value for a score.
     * @param score value to check.
     * @return string containing the color descriptor.
     */
    public String getColorValueForScore( final Double score )
    {
        String result = "#FFFFFF";
        
        for ( int i = 0; i < lowScores.size(); i++ )
        {
            if ( lowScores.get( i ) <= score 
                 && highScores.get( i ) >= score )
            {
                result = colorValues.get( i );
            }
        }
        return result;
    }
    
    /**
     * Utility function to return a color value for a score.
     * @param score value to check.
     * @return string containing the color descriptor.
     */
    public String getColorLabelForScore( final Double score )
    {
        String result = "White";
        
        for ( int i = 0; i < lowScores.size(); i++ )
        {
            if ( lowScores.get( i ) <= score 
                 && highScores.get( i ) >= score )
            {
                result = colorLabels.get( i );
            }
        }
        return result;
    }
}
