package com.A.vm.util.converter.unmarshall;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.A.V.beans.entity.CustomSelection;
import com.A.xml.v4.CustomSelectionsType;
import com.A.xml.v4.LineItemSelectionType;
import com.A.xml.v4.LineItemSelectionsType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.SelectionChoiceType;

public class UnmarshallCustomSelection {
	private static final Logger logger = Logger
			.getLogger(UnmarshallCustomSelection.class);

	public static Set<CustomSelection> buildCustomSelection(LineItemType src,
			Set<CustomSelection> existingCustomSelections, boolean isAppend) {
		logger.debug("Unmarshalling custom selection");

		Set<CustomSelection> customSelections = null;

		if (isAppend) {
			customSelections = existingCustomSelections;
		}
		if ((src.getCustomSelections() != null)
				&& (src.getCustomSelections().getSelections() != null)
				&& (src.getCustomSelections().getSelections()
						.getSelectionList() != null)
				&& (src.getCustomSelections().getSelections()
						.getSelectionList().size() > 0)) {
			
			logger.debug("Custom selection size: " + src.getCustomSelections().getSelections().getSelectionList().size());
			
			if (customSelections == null) {
				customSelections = new HashSet<CustomSelection>();
			}
			CustomSelectionsType srcCustSelType = src.getCustomSelections();
			LineItemSelectionsType srcLISelType = srcCustSelType
					.getSelections();
			List<LineItemSelectionType> srcSelectionTypeList = srcLISelType
					.getSelectionList();
			for (LineItemSelectionType srcSelection : srcSelectionTypeList) { //srcSelection (selection)
				List<SelectionChoiceType> srcChoiceList = srcSelection
						.getSelectionChoiceList();
				for (SelectionChoiceType srcChoice : srcChoiceList) {
					CustomSelection destCustomSel = new CustomSelection();
					destCustomSel.setSelectionExtId(srcSelection.getExternalId());//srcSelection
					destCustomSel.setSelectionDisplayOrder(srcSelection.getDisplayOrder());
					destCustomSel.setSelectionType(srcSelection.getType());
					destCustomSel.setSelectionShortDesc(srcSelection.getShortDescription());
					destCustomSel.setSelectionName(srcSelection.getName());
					
					destCustomSel.setParentChoiceExtId(srcSelection.getParentChoiceExternalId());//srcSelection
					destCustomSel.setChoiceExtId(srcChoice.getExternalId());//srcChoice
					destCustomSel.setChoiceDisplayOrder(srcChoice.getDisplayOrder());
					destCustomSel.setChoiceShortDes(srcChoice.getShortDescription());
					destCustomSel.setChoiceName(srcChoice.getName());
					destCustomSel.setChoiceDetail(srcChoice.getDetail());//srcChoice
					
					if(srcChoice.getPrice()!=null){
						destCustomSel.setBaseNonRecurringPrice(srcChoice.getPrice().getBaseNonRecurringPrice());
						destCustomSel.setBaseNonRecurringPriceUnits(srcChoice.getPrice().getBaseNonRecurringPriceUnits());
						destCustomSel.setBaseRecurringPrice(srcChoice.getPrice().getBaseRecurringPrice());
						destCustomSel.setBaseRecurringPriceUnits(srcChoice.getPrice().getBaseNonRecurringPriceUnits());
						if(srcChoice.getPrice().isSetIncludeInTotalPrice()){
							destCustomSel.setIncludeInTotalPrice(srcChoice.getPrice().isSetIncludeInTotalPrice());	
						}
					}
					
					customSelections.add(destCustomSel);
				}

			}
		}
		return customSelections;
	}
}
