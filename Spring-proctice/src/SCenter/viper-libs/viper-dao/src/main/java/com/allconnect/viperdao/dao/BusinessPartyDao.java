/**
 *
 */
package com.A.Vdao.dao;

import com.A.V.beans.entity.BusinessParty;

/**
 * @author ebthomas
 *
 */
public interface BusinessPartyDao
{
        BusinessParty  findBusinessPartyById(   final String externalId );
}
