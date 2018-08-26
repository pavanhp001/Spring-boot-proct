package com.A.ui.dao;

import com.A.ui.domain.WebLookupCollection;

public interface WebLookupDao {

	WebLookupCollection findCountries();

	WebLookupCollection findUSStates();

	WebLookupCollection findNamePrefix();

	WebLookupCollection findNameSuffix();

	WebLookupCollection findUnitType();

	WebLookupCollection findRentOwn();
	
	WebLookupCollection findServiceAddressType();
}
