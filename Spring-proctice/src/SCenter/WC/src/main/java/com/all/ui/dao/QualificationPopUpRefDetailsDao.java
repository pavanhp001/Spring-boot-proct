package com.A.ui.dao;

import java.util.Map;

import com.A.ui.domain.QualificationPopUpRefDetails;

public interface QualificationPopUpRefDetailsDao {

	Map<String, Map<String, QualificationPopUpRefDetails>> getAllQualificationPopUpRefDetails();
	
}