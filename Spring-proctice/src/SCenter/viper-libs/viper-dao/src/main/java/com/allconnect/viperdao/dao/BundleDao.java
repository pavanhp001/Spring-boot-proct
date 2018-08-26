package com.A.Vdao.dao;


import com.A.V.beans.entity.BundleBean;

public interface BundleDao {
	BundleBean findBundleById( final String externalId);
}
