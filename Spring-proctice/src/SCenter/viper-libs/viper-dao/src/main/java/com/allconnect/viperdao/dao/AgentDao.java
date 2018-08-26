package com.A.Vdao.dao;
 
import com.A.V.beans.entity.AgentBean;

public interface AgentDao {

	 /**
     * {@inheritDoc}
     */
    public   AgentBean findAgentById(   final String id ) ;
}
