package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * 
 * @author ebaugh
 * 
 */
@Entity
@Table( name = "postalcoderange" )
@org.hibernate.annotations.Table( appliesTo = "postalcoderange", 
   indexes = { @Index( name = "POSTALCODERANGE_IX1", 
   columnNames = { "startPostalCode5", "startPlus4", "endPostalCode5", "endPlus4" } ) } )
public class PostalCodeRangeBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8748832300903306005L;
	private static final int MAIN_ZIP_LENGTH = 5;
    private static final int PLUS_FOUR_LENGTH = 4;
    
    @Id
    @GeneratedValue( generator = "postalCodeRangeBeanSequence" )
    @SequenceGenerator( name = "postalCodeRangeBeanSequence", sequenceName = "POSTAL_CODE_RANGE_BEAN_SEQ" )
    private long id;

    private String startPostalCode5;
    private String startPlus4;
    private String endPostalCode5;
    private String endPlus4;

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
     * @return the startPostalCode5
     */
    public String getStartPostalCode5()
    {
        return startPostalCode5;
    }

    /**
     * @param startPostalCode5
     *            the startPostalCode5 to set
     */
    public void setStartPostalCode5( final String startPostalCode5 )
    {
        this.startPostalCode5 = startPostalCode5;
    }
    
    /**
     * @param startPostalCode5
     *            the startPostalCode5 to set
     */
    public void setStartPostalCode5( final int startPostalCode5 )
    {
        // If the int is *not* 5 digits long, we need to 0 pad it.
        if ( Integer.toString( startPostalCode5 ).length() < MAIN_ZIP_LENGTH )
        {
            // Append enough 0's to fill it up
            StringBuilder sb = new StringBuilder( "00000" );
            sb.append( Integer.toString( startPostalCode5 ) );
            // Then take the last 5 chars of the string
            this.startPostalCode5 = sb.substring( sb.length() - MAIN_ZIP_LENGTH );
        } 
        else
        {
            this.startPostalCode5 = Integer.toString( startPostalCode5 );
        }
    }

    /**
     * @return the startPlus4
     */
    public String getStartPlus4()
    {
        return startPlus4;
    }

    /**
     * @param startPlus4
     *            the startPlus4 to set
     */
    public void setStartPlus4( final String startPlus4 )
    {
        this.startPlus4 = startPlus4;
    }
    
    /**
     * @param startPlus4
     *            the startPlus4 to set
     */
    public void setStartPlus4( final int startPlus4 )
    {
        // If the int is *not* 4 digits long, we need to 0 pad it.
        if ( Integer.toString( startPlus4 ).length() < PLUS_FOUR_LENGTH )
        {
            // Append enough 0's to fill it up
            StringBuilder sb = new StringBuilder( "0000" );
            sb.append( Integer.toString( startPlus4 ) );
            // Then take the last 5 chars of the string
            this.startPlus4 = sb.substring( sb.length() - PLUS_FOUR_LENGTH );
        } 
        else
        {
            this.startPlus4 = Integer.toString( startPlus4 );
        }
    }
    
    /**
     * @return the endPostalCode5
     */
    public String getEndPostalCode5()
    {
        return endPostalCode5;
    }

    /**
     * @param endPostalCode5
     *            the endPostalCode5 to set
     */
    public void setEndPostalCode5( final String endPostalCode5 )
    {
        this.endPostalCode5 = endPostalCode5;
    }
    
    /**
     * @param endPostalCode5
     *            the endPostalCode5 to set
     */
    public void setEndPostalCode5( final int endPostalCode5 )
    {
        // If the int is *not* 5 digits long, we need to 0 pad it.
        if ( Integer.toString( endPostalCode5 ).length() < MAIN_ZIP_LENGTH )
        {
            // Append enough 0's to fill it up
            StringBuilder sb = new StringBuilder( "00000" );
            sb.append( Integer.toString( endPostalCode5 ) );
            // Then take the last 5 chars of the string
            this.endPostalCode5 = sb.substring( sb.length() - MAIN_ZIP_LENGTH );
        } 
        else
        {
            this.endPostalCode5 = Integer.toString( endPostalCode5 );
        }
    }

    /**
     * @return the endPlus4
     */
    public String getEndPlus4()
    {
        return endPlus4;
    }

    /**
     * @param endPlus4
     *            the endPlus4 to set
     */
    public void setEndPlus4( final String endPlus4 )
    {
        this.endPlus4 = endPlus4;
    }

    /**
     * @param endPlus4
     *            the endPlus4 to set
     */
    public void setEndPlus4( final int endPlus4 )
    {
        // If the int is *not* 4 digits long, we need to 0 pad it.
        if ( Integer.toString( endPlus4 ).length() < PLUS_FOUR_LENGTH )
        {
            // Append enough 0's to fill it up
            StringBuilder sb = new StringBuilder( "0000" );
            sb.append( Integer.toString( endPlus4 ) );
            // Then take the last 5 chars of the string
            this.endPlus4 = sb.substring( sb.length() - PLUS_FOUR_LENGTH );
        } 
        else
        {
            this.endPlus4 = Integer.toString( endPlus4 );
        }
    }
}
