package com.A.vm.util.converter.marshall;

import java.util.Comparator;

import com.A.V.beans.entity.CustomSelection;

public class CustomSelectionComparator implements Comparator
{

	public int compare( Object o1, Object o2 )
	{
		CustomSelection sel1 = (CustomSelection)o1;
		CustomSelection sel2 = (CustomSelection)o2;
		int nameComp = sel1.getSelectionExtId().compareTo(sel2.getSelectionExtId());
		return nameComp;
	}

}

