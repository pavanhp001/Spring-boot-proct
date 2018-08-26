package com.A.V.crypt;

import com.A.V.beans.entity.SelectedDialogue;
import com.A.V.crypt.service.EncryptService;

public enum DialogueEncryptStrategy {

    INSTANCE;

    public void encryptDialogue(SelectedDialogue dialogue) {
	String encryptedValue = encryptToHex(dialogue.getValue());
	dialogue.setValue(encryptedValue);
    }

    private String encryptToHex(Object raw) {

	if (raw == null) {
	    return null;
	}

	String hexString = "";

	if (raw instanceof String) {

	    hexString = (String) raw;

	    if (hexString.length() == 0) {
		return "";
	    }

	    try {
		String crypt = EncryptService.INSTANCE.encryptHex(hexString);

		return crypt;

	    }
	    catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new IllegalArgumentException("Exception while encrypting data.");
	    }

	}

	return hexString;
    }

}
