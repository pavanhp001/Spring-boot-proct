package com.A.vm.service;

 

import com.A.V.beans.entity.BundleBean;

public interface BundleService {
	BundleBean findBundleById(final String id);

	BundleBean findBundleById(final Long id);

	 
}
