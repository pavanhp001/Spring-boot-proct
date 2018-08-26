package com.A.vm.util.converter.marshall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.SelectedDialogue;
import com.A.xml.v4.DialogValueType;
import com.A.xml.v4.DialogValueType.Value;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.SelectedDialogsType;
import com.A.xml.v4.SelectedDialogsType.Dialogs;

public class MarshallSelectedDialogue {
    private static final Logger logger = Logger.getLogger(MarshallSelectedDialogue.class);

    public static void buildSelectedDialogue(LineItem src, LineItemType destLineItemType) {
	logger.debug("Building active dialogue for lineitem");

	SelectedDialogsType destDialogsType = destLineItemType.addNewActiveDialogs();

	if (src.getDialogues() != null) {
	    try {
		Set<SelectedDialogue> srcDialoguesList = src.getDialogues();
		List<SelectedDialogue> sortedList = new ArrayList<SelectedDialogue>(srcDialoguesList);
		logger.trace("MarshallSelectedDialogue:buildSelectedDialogue:sortedList: " + sortedList.size());

		// Get Unique ActiveDialogues

		Collections.sort(sortedList, new DialogueComparator());

		Dialogs destDialogs = destDialogsType.addNewDialogs();
		int i = 0;
		String dlgExtId = "";
		DialogValueType destDialog = null;
		long dlgDate = 0L;
		for (SelectedDialogue srcDialogue : sortedList) {
		    if (i == 0) {
			destDialog = destDialogs.addNewDialog();
			dlgExtId = srcDialogue.getExternalId();
			logger.trace("MarshallSelectedDialogue:buildSelectedDialogue:i == 0:dlgExtId: " + dlgExtId);
			dlgDate = srcDialogue.getDialogueDate().getTimeInMillis();
			destDialog.setExternalId(dlgExtId);
			destDialog.setDialogueDate(srcDialogue.getDialogueDate());
			setDialogueValue(srcDialogue, destDialog);
		    }
		    else if (dlgExtId.equalsIgnoreCase(srcDialogue.getExternalId()) && srcDialogue.getDialogueDate().getTimeInMillis() == dlgDate) {
			dlgExtId = srcDialogue.getExternalId();
			logger.trace("MarshallSelectedDialogue:buildSelectedDialogue:dlgExtId.equalsIgnoreCase( srcDialogue.getExternalId() ):dlgExtId: " + dlgExtId);
			destDialog.setExternalId(dlgExtId);
			destDialog.setDialogueDate(srcDialogue.getDialogueDate());
			setDialogueValue(srcDialogue, destDialog);
		    }
		    else {
			destDialog = destDialogs.addNewDialog();
			dlgExtId = srcDialogue.getExternalId();
			logger.trace("MarshallSelectedDialogue:buildSelectedDialogue:!dlgExtId.equalsIgnoreCase( srcDialogue.getExternalId() ):dlgExtId: " + dlgExtId);
			destDialog.setExternalId(dlgExtId);
			destDialog.setDialogueDate(srcDialogue.getDialogueDate());
			setDialogueValue(srcDialogue, destDialog);
		    }
		    i++;
		}
	    }
	    catch (Exception e) {
		logger.warn(e.getMessage());
	    }
	}
    }

    private static void setDialogueValue(SelectedDialogue srcDialogue, DialogValueType destDialog) {
//	if (SecureDialogueProvider.getSecureDialogueList().contains(srcDialogue.getExternalId())) {
//	    logger.debug("Decrypting dialogue : " + srcDialogue.toString());
//	    DefaultDecryptListener.INSTANCE.decrypt(srcDialogue);
//	}
	Value destDlgValue = destDialog.addNewValue();
	destDlgValue.setSelected(srcDialogue.isSelected());
	destDlgValue.setType(destDlgValue.getType().forString(srcDialogue.getType().trim()));
	destDlgValue.setStringValue(srcDialogue.getValue());
    }
}
