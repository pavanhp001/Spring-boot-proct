package com.A.V.utility;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.TypedValue;

/**
 * 
 * @author ebaugh
 * 
 */
public class RestrictedSoundexCriterion implements Criterion
{
    private static final long serialVersionUID = 3532412388L;

    private String fieldName;
    private String phoneticMatchValue;
    private Long rowNum;

    private static List<Character> group1 = Arrays.asList( new Character[] {'B', 'P', 'F', 'V'} );
    private static List<Character> group2 = Arrays.asList( new Character[] {'C', 'S', 'K', 'G', 'J', 'Q', 'X', 'Z'} );
    private static List<Character> group3 = Arrays.asList( new Character[] {'D', 'T'} );
    private static List<Character> group4 = Arrays.asList( new Character[] {'M', 'N'} );

    /**
     * Default Constructor.
     */
    public RestrictedSoundexCriterion()
    {
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName( final String fieldName )
    {
        this.fieldName = fieldName;
    }

    public String getPhoneticMatchValue()
    {
        return phoneticMatchValue;
    }

    /**
     * Setter for phoneticMatch value.
     * @param phoneticMatchValue value to set.
     */
    public void setPhoneticMatchValue( final String phoneticMatchValue )
    {
        char firstChar = phoneticMatchValue.charAt( 0 );
        String value = phoneticMatchValue.substring( 1 );
        List<Character> matchingGroup = null;
        if ( group1.contains( firstChar ) )
        {
            matchingGroup = group1;
        }
        else if ( group2.contains( firstChar ) )
        {
            matchingGroup = group2;
        }
        else if ( group3.contains( firstChar ) )
        {
            matchingGroup = group3;
        }
        else if ( group4.contains( firstChar ) )
        {
            matchingGroup = group4;
        }

        if ( matchingGroup != null )
        {
            this.phoneticMatchValue = "";
            boolean commaFlag = false;
            for ( Character c : matchingGroup )
            {
                if ( commaFlag  )
                {
                    this.phoneticMatchValue = this.phoneticMatchValue + ',';
                }
                else
                {
                    commaFlag = true;
                }

                this.phoneticMatchValue = this.phoneticMatchValue + "'" + c + value + "'";
            }
        }
        else
        {
            this.phoneticMatchValue = "'" + phoneticMatchValue + "'";
        }
    }

    public Long getRowNum()
    {
        return rowNum;
    }

    public void setRowNum( final Long rowNum )
    {
        this.rowNum = rowNum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedValue[] getTypedValues( final Criteria arg0, final CriteriaQuery arg1 )
    {
        return new TypedValue[] {new TypedValue( StandardBasicTypes.LONG, rowNum, EntityMode.POJO )};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toSqlString( final Criteria arg0, final CriteriaQuery arg1 )
    {
        return "SOUNDEX( " + fieldName + " ) IN ( " + phoneticMatchValue + " ) and ROWNUM < ?";
        // This is how we use to do this, now we use the IN clause.
        // return "SOUNDEX( " + fieldName + " ) = ? and ROWNUM < ?";
    }

}
