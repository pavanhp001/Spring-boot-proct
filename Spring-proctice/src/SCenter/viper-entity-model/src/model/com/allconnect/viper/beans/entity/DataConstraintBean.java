package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author jgerhard
 *
 */

@Entity
@Table( name = "dataConstraint" )
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class DataConstraintBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2267534689740236987L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "dcBeanSequence" )
    @SequenceGenerator( name = "dcBeanSequence", sequenceName = "DC_BEAN_SEQ" ,allocationSize = 1)
    private long id;

    private String dataType;
    //key is item or market item externalId
    /*@CollectionOfElements 
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @MapKey( columns = @Column( name = "externalId" ) )
    private Map<String, DCValueBean> dcValues;*/
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

    public final String getDataType()
    {
        return dataType;
    }

    public final void setDataType( final String dataType )
    {
        this.dataType = dataType;
    }

   /* public Map<String, DCValueBean> getDataConstraintValues() 
    {
        return dcValues;
    }

    public void setDataConstraintValues(
            final Map<String, DCValueBean> dataConstraintValues ) 
    {
        this.dcValues = dataConstraintValues;
    }*/ 
}
