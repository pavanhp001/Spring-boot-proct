package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.ForeignKey;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * 
 * @author rchapple
 *
 */
public class DialogueDataFieldTypeBean implements CommonBeanInterface 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5071624193540276228L;

	// An enumeration of the valid data types 
	public static enum DataType { TEXT, INTEGER, FLOAT, BOOLEAN };
	
	/**
	 * Id (primary key) member variable.
	 */
    @Id
    @GeneratedValue( generator = "ddftBeanSequence" )
    @SequenceGenerator( name = "ddftBeanSequence", sequenceName = "DDFT_BEAN_SEQ" )
    private long id;
    
    /**
     * The dataType of the referenced DataField.
     */
    private DataType dataType;
    
    @ForeignKey( name = "D_DATAFIELD_FK01" )
    private List<DataConstraintBean> dataContraints;
    
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

	public DataType getDataType()
	{
		return dataType;
	}

	public void setDataType( final DataType dataType )
	{
		this.dataType = dataType;
	}

	public List<DataConstraintBean> getDataContraints()
	{
		return dataContraints;
	}

	public void setDataContraints( final List<DataConstraintBean> dataContraints )
	{
		this.dataContraints = dataContraints;
	}
	
}
