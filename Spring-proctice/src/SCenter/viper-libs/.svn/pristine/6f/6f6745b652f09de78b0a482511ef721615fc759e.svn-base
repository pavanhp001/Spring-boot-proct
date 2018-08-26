package com.A.vm.util.converter.marshall;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.A.V.beans.entity.CustomSelection;
import com.A.V.beans.entity.LineItem;
import com.A.xml.v4.CustomSelectionsType;
import com.A.xml.v4.EmptyElementType;
import com.A.xml.v4.LineItemSelectionType;
import com.A.xml.v4.LineItemSelectionsType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.PriceInfoType;
import com.A.xml.v4.SelectionChoiceType;

public class MarshallCustomSelection {
	private static final Logger logger = Logger
			.getLogger(MarshallCustomSelection.class);

	public static void buildCustomSelection(LineItem srcLineItemBean,
			LineItemType destLineItemType) {
		logger.debug("Building custom selection for lineitem");
		CustomSelectionsType destSelectionsType = destLineItemType
				.addNewCustomSelections();
		if (srcLineItemBean.getSelections() != null) {
			Set<CustomSelection> srcCustSelectionSet = srcLineItemBean
					.getSelections();

			try {
				List<CustomSelection> srcCustSelectionList = new ArrayList<CustomSelection>(
						srcCustSelectionSet);
				Collections.sort(srcCustSelectionList,
						new CustomSelectionComparator());

				LineItemSelectionsType destLISelType = destSelectionsType
						.addNewSelections();
				int counter = 0;
				String selExtId = "";
				LineItemSelectionType destSelType = null;
				for (CustomSelection selection : srcCustSelectionList) {
					if (counter == 0) {
						destSelType = destLISelType.addNewSelection();
						selExtId = selection.getSelectionExtId();
						destSelType.setExternalId(selExtId);
						destSelType.setParentChoiceExternalId(selection.getParentChoiceExtId());
						destSelType.setDisplayOrder(selection.getSelectionDisplayOrder());
						destSelType.setType(selection.getSelectionType());
						destSelType.setName(selection.getSelectionName());
						destSelType.setShortDescription(selection.getSelectionShortDesc());
						setSelectionChoice(selection, destSelType);
					} else if (selExtId.equalsIgnoreCase(selection
							.getSelectionExtId())) {
						destSelType.setExternalId(selExtId);
						destSelType.setParentChoiceExternalId(selection.getParentChoiceExtId());
						destSelType.setDisplayOrder(selection.getSelectionDisplayOrder());
						destSelType.setType(selection.getSelectionType());
						destSelType.setName(selection.getSelectionName());
						destSelType.setShortDescription(selection.getSelectionShortDesc());
						setSelectionChoice(selection, destSelType);
					} else {
						destSelType = destLISelType.addNewSelection();
						selExtId = selection.getSelectionExtId();
						destSelType.setExternalId(selExtId);
						destSelType.setParentChoiceExternalId(selection.getParentChoiceExtId());
						destSelType.setDisplayOrder(selection.getSelectionDisplayOrder());
						destSelType.setType(selection.getSelectionType());
						destSelType.setName(selection.getSelectionName());
						destSelType.setShortDescription(selection.getSelectionShortDesc());
						setSelectionChoice(selection, destSelType);
					}
					counter++;
				}

			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}
	}

	private static void setSelectionChoice(CustomSelection selection,
			LineItemSelectionType destSelType) {
		SelectionChoiceType destChoiceType = destSelType
				.addNewSelectionChoice();
		destChoiceType.setExternalId(selection.getChoiceExtId());
		destChoiceType.setDetail(selection.getChoiceDetail());
		destChoiceType.setDisplayOrder(selection.getChoiceDisplayOrder());
		destChoiceType.setName(selection.getChoiceName());
		destChoiceType.setShortDescription(selection.getChoiceShortDes());
		PriceInfoType pricingInfo =destChoiceType.addNewPrice();
		pricingInfo.setBaseNonRecurringPrice(selection.getBaseNonRecurringPrice());
		pricingInfo.setBaseNonRecurringPriceUnits(selection.getBaseNonRecurringPriceUnits());
		pricingInfo.setBaseRecurringPrice(selection.getBaseRecurringPrice());
		pricingInfo.setBaseRecurringPriceUnits(selection.getBaseRecurringPriceUnits());
		if(selection.isIncludeInTotalPrice()){
			pricingInfo.setIncludeInTotalPrice(EmptyElementType.Factory.newInstance());	
		}

	}
}
