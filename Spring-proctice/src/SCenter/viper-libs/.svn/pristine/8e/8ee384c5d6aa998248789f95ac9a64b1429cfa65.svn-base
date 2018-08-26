package com.A.vm.util.converter.unmarshall;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.A.util.XmlUtil;
import com.A.V.beans.entity.SelectedDialogue;
import com.A.xml.v4.DialogValueType;
import com.A.xml.v4.DialogValueType.Value;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.SelectedDialogsType;
import com.A.xml.v4.SelectedDialogsType.Dialogs;

public class UnmarshallSelectedDialogue {
    private static final Logger logger = Logger.getLogger(UnmarshallSelectedDialogue.class);

    // public static Set<SelectedDialogue> buildSelectedDialogue(LineItemType src,
    // Set<SelectedDialogue> dialogs, final Boolean isAppend) {
    //
    // logger.debug("Unmarshalling active dialogue info");
    //
    // Set<SelectedDialogue> activeDialoguesSet = null;
    //
    // if (isAppend) {
    // activeDialoguesSet = dialogs;
    // }
    //
    // if ((activeDialoguesSet == null) || (!isAppend)) {
    // activeDialoguesSet = new HashSet<SelectedDialogue>();
    // }
    //
    // if ((null != src.getActiveDialogs())
    // && (src.getActiveDialogs().getDialogs() != null)
    // && (src.getActiveDialogs().getDialogs().getDialogList() != null)
    // && (src.getActiveDialogs().getDialogs().getDialogList().size() > 0)) {
    //
    // SelectedDialogsType dialoguesType = src.getActiveDialogs();
    // Dialogs dlgs = dialoguesType.getDialogs();
    // List<DialogValueType> dialogList = dlgs.getDialogList();
    // logger.debug("UnmarshallSelectedDialogue:buildSelectedDialogue():dialogList.size(): " + dialogList.size());
    // for (DialogValueType srcDialog : dialogList) {
    // List<Value> values = srcDialog.getValueList();
    // SelectedDialogue activeDialogue = new SelectedDialogue();
    // activeDialogue.setExternalId(srcDialog.getExternalId());
    // if (srcDialog.getDialogueDate() != null
    // && !XmlUtil.isElementNil(srcDialog.newCursor(),
    // "dialogueDate")) {
    // activeDialogue.setDialogueDate(srcDialog
    // .getDialogueDate());
    // } else {
    // activeDialogue.setDialogueDate(Calendar.getInstance());
    // }
    //
    // logger.trace("UnmarshallSelectedDialogue:buildSelectedDialogue():values.size(): " + values.size());
    //
    // for (Value value : values) {
    // activeDialogue.setSelected(value.getSelected());
    // activeDialogue.setType(value.getType().toString().trim());
    // activeDialogue.setValue(value.getStringValue());
    // }
    //
    // logger.trace("UnmarshallSelectedDialogue:buildSelectedDialogue():Sets only one(last) Value in each dialog based on current design. ");
    //
    // activeDialoguesSet.add(activeDialogue);
    // }
    // }
    //
    // return activeDialoguesSet;
    // }

    public static Set<SelectedDialogue> buildSelectedDialogue(LineItemType src, Set<SelectedDialogue> existingDialogs) {
	logger.debug("Unmarshalling dialogs");
	Set<SelectedDialogue> activeDialoguesSet = new HashSet<SelectedDialogue>();

	if ((null != src.getActiveDialogs()) && (src.getActiveDialogs().getDialogs() != null) && (src.getActiveDialogs().getDialogs().getDialogList() != null)
		&& (src.getActiveDialogs().getDialogs().getDialogList().size() > 0)) {

	    SelectedDialogsType dialoguesType = src.getActiveDialogs();
	    Dialogs dlgs = dialoguesType.getDialogs();
	    List<DialogValueType> dialogList = dlgs.getDialogList();
	    for (DialogValueType newDlg : dialogList) {

		// find and update existing dialog
		SelectedDialogue activeDialogue = getUpdatedExistingDialog(newDlg, existingDialogs);

		// if not existing dialog not found, then add as new
		if (null == activeDialogue) {
		    logger.debug("Adding new dialog : " + newDlg.getExternalId());
		    activeDialogue = new SelectedDialogue();
		    activeDialogue.setExternalId(newDlg.getExternalId());
		    List<Value> values = newDlg.getValueList();
		    if (newDlg.getDialogueDate() != null && !XmlUtil.isElementNil(newDlg.newCursor(), "dialogueDate")) {
			activeDialogue.setDialogueDate(newDlg.getDialogueDate());
		    }
		    else {
			activeDialogue.setDialogueDate(Calendar.getInstance());
		    }

		    for (Value value : values) {
			activeDialogue.setSelected(value.getSelected());
			activeDialogue.setType(value.getType().toString().trim());
			activeDialogue.setValue(value.getStringValue());
		    }
		}
		activeDialoguesSet.add(activeDialogue);
	    }

	}

	// Add non updated existing dialgoues which were not sent from client

	if (activeDialoguesSet != null && existingDialogs != null) {
	    logger.trace("Before old dlg removal. ext dlg set: " + existingDialogs);
	    logger.trace("Before old dlg removal. act dlg set: " + activeDialoguesSet);

	    Iterator<SelectedDialogue> extIter = existingDialogs.iterator();
	    while (extIter.hasNext()) {
		SelectedDialogue extDlg = extIter.next();
		for (SelectedDialogue newDlg : activeDialoguesSet) {
		    if (extDlg.getExternalId().equalsIgnoreCase(newDlg.getExternalId())) {
			extIter.remove();
			break;
		    }
		}
	    }
	}

	//Make final set with filtered existing dlg set and new updated dlg set
	Set<SelectedDialogue> finalDlgSet = new HashSet<SelectedDialogue>();
	if(activeDialoguesSet != null && !activeDialoguesSet.isEmpty()) {
	    logger.trace("After old dlg removal. act dlg set: " + activeDialoguesSet);
	    finalDlgSet.addAll(activeDialoguesSet);
	}
	if(existingDialogs != null && !existingDialogs.isEmpty()) {
	    logger.trace("After old dlg removal. ext dlg set: " + existingDialogs);
	    finalDlgSet.addAll(existingDialogs);
	}
	return finalDlgSet;

    }

    private static SelectedDialogue getUpdatedExistingDialog(DialogValueType newDlg, Set<SelectedDialogue> existingDialogs) {
	if (newDlg != null && existingDialogs != null && !existingDialogs.isEmpty()) {
	    logger.debug("Finding existing dialogs");
	    for (SelectedDialogue extDlg : existingDialogs) {
		if (extDlg.getExternalId().equalsIgnoreCase(newDlg.getExternalId())) {
		    logger.trace("Found existing dialog. Updating it.");
		    extDlg.setDialogueDate(Calendar.getInstance());
		    List<Value> values = newDlg.getValueList();
		    for (Value value : values) {
			extDlg.setSelected(value.getSelected());
			extDlg.setType(value.getType().toString().trim());
			extDlg.setValue(value.getStringValue());
			break;
		    }
		    logger.debug("Existing updated dlg : " + extDlg.toString());
		    return extDlg;
		}
	    }
	}
	return null;

    }
}
