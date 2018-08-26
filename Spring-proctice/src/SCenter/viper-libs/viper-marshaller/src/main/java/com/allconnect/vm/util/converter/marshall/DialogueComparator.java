package com.A.vm.util.converter.marshall;

import java.util.Comparator;

import com.A.V.beans.entity.SelectedDialogue;

public class DialogueComparator implements Comparator<SelectedDialogue> {
	public int compare(SelectedDialogue dlg1, SelectedDialogue dlg2) {

		int nameComp = dlg1.getExternalId().compareTo(dlg2.getExternalId());

		return nameComp;
	}
}
