package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

/**
 * An entity to model an user who is external to A.
 * 
 * @author rchapple
 * 
 */
@Entity
@Table( name = "EXTERNALUSER" )
public class ExternalUserBean extends UserBean
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3948985389986422173L;
	
	@ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    @Column( name = "AUTHORIZEDBUSINESSPARTY_ID" )
    private List<BusinessParty> authorizedBusinessPartyList;

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public Boolean authenticate()
    {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * Default getter.
     * @return getAuthorizedBusinessPartyList.
     */
    public List<BusinessParty> getauthorizedBusinessPartyList()
    {
        return authorizedBusinessPartyList;
    }

    public void setAuthorizedBusinessPartyList( final List<BusinessParty> parties )
    {
        this.authorizedBusinessPartyList = parties;
    }

}
