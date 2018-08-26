package com.A.Vdao.dao;

import java.util.Set;

import com.A.V.beans.entity.CustomSelection;

public interface CustomSelectionDao
{

	void persist(Set<CustomSelection> selectionSet);
}
