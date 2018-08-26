package com.A.Vdao.dao;

import com.A.V.beans.entity.VOrderMapping;

public interface VOrderMappingDao {
	public Boolean save(VOrderMapping mapping);
	public VOrderMapping findByVOrderNoAndOrderExtIdAndLIExtId(String VOrderNo, Long orderExtId, Long liExtId);
	public int updateProviderNumber(VOrderMapping mapping);
}
