package com.A.V.beans.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.validator.NotNull;
import com.A.V.interfaces.CommonBeanInterface;
import com.A.V.utility.Status;


/**
 * Status Code Bean to store instances 
 * of all status codes for all VES Services
 * 
 * 
 * @author klyons
 *
 */

@Entity
@Table( name = "StatusCode" )
public class StatusCodeBean implements CommonBeanInterface
{
		
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1596636780295888347L;

	@Id
    @GeneratedValue( generator = "StatusCodeBeanSequence" )
    @SequenceGenerator( name = "StatusCodeBeanSequence", sequenceName = "STATUS_CODE_BEAN_SEQ" )
    private long id;
    
    @Column
    private int statusCode;
    
    @Column
    private String statusName;
    
    @Column
    private String description;
    
    @Column 
    @Enumerated(EnumType.STRING)
    private Status.Type type;
    
    @ManyToMany
    private List<ReasonCodeBean> reasonCodes = new ArrayList<ReasonCodeBean>();
    
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


	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusName
	 *            the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param reasonCodes
	 *            the reasonCodes to set
	 */
	public void setReasonCodes( final List<ReasonCodeBean> reasonCodes) {
		this.reasonCodes = reasonCodes;
	}

	/**
	 * @return the reasonCodes
	 */
	public List<ReasonCodeBean> getReasonCodes() {
		return reasonCodes;
	}

	/**
	 * @param type the type to set
	 */
	public void setType( final Status.Type type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public Status.Type getType() {
		return type;
	}



	
}
