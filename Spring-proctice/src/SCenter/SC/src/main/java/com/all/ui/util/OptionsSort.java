package com.AL.ui.util;

 
import java.util.Comparator;

import com.AL.xml.pr.v4.ChoiceType;

public class OptionsSort implements Comparator<ChoiceType> {
	
	public static final OptionsSort OPTIONS_SORT = new OptionsSort();

	public int compare(ChoiceType choice1, ChoiceType choice2) {
		
		
		if  (choice1.getDisplayOrder().longValue() == choice2.getDisplayOrder().longValue()) {
			return 0;
		}
		
		return (choice1.getDisplayOrder().longValue() > choice2.getDisplayOrder().longValue()) ? 1 : -1; 
	}
	
}