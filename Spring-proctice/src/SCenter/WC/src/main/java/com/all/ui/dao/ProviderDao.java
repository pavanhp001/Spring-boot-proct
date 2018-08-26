package com.A.ui.dao;

import java.util.List;

import com.A.ui.domain.Provider;

public interface ProviderDao {

	Provider get(Long providerId);

	List<Provider> getProviders();
}
