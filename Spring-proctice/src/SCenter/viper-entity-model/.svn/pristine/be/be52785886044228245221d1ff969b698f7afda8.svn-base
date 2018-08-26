package com.A.V.beans.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.SelectedDialogueUtil;
import com.A.V.crypt.DefaultDecryptListener;
import com.A.V.crypt.DialogueEncryptStrategy;
import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table(name = "OM_SEL_DIALOGUE")
public class SelectedDialogue implements CommonBeanInterface {


    /**
	 * 
	 */
	private static final long serialVersionUID = 846253786566983279L;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "selDialogueSequence")
    @SequenceGenerator(name = "selDialogueSequence", sequenceName = "OM_SEL_DIALOGUE_SEQ", allocationSize = 1)
    private long id;

    @Column(name = "EXTERNAL_ID")
    private String externalId;

    @Column(name = "DIALOGUE_VALUE")
    private String value;

    @Column(name = "IS_SELECTED")
    private boolean isSelected;

    @Column(name = "DIALOGUE_VALUE_TYPE")
    private String type;

    @Column(name = "DIALOGUE_DATE")
    private Calendar dialogueDate;

    @Override
    public long getId() {
	return id;
    }

    @Override
    public void setId(long id) {
	this.id = id;
    }

    @PostLoad
    public void decryptLoadData() {
	if(SelectedDialogueUtil.getSecureDialogueList().contains(this.externalId.trim().replace("/", ""))) {
	    DefaultDecryptListener.INSTANCE.decrypt(this);
	}
    }

    @PreUpdate
    public void beforeUpdate() {
	if(SelectedDialogueUtil.getSecureDialogueList().contains(this.externalId.trim().replace("/", ""))) {
	    DialogueEncryptStrategy.INSTANCE.encryptDialogue(this);
	}
    }

    @PrePersist
    public void beforeInsert() {
	if(SelectedDialogueUtil.getSecureDialogueList().contains(this.externalId.trim().replace("/", ""))) {
	    DialogueEncryptStrategy.INSTANCE.encryptDialogue(this);
	}
    }

    /**
     *
     * {@inheritDoc}
     */
    public boolean equals(final Object obj) {
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof SelectedDialogue)) {
	    return false;
	}
	SelectedDialogue toCompare = (SelectedDialogue) obj;
	return this.externalId.equals(toCompare.externalId);
    }

    /**
     *
     * {@inheritDoc}
     */
    public int hashCode() {
	return this.externalId.hashCode() + (int) id;
    }

    public String getExternalId() {
	return externalId;
    }

    public void setExternalId(String externalId) {
	this.externalId = externalId;
    }

    public boolean isSelected() {
	return isSelected;
    }

    public void setSelected(boolean isSelected) {
	this.isSelected = isSelected;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public Calendar getDialogueDate() {
	return dialogueDate;
    }

    public void setDialogueDate(Calendar dialogueDate) {
	this.dialogueDate = dialogueDate;
    }

    @Override
    public String toString() {
	return "SelectedDialogue [id=" + id + ", externalId=" + externalId + ", value=" + value + ", isSelected=" + isSelected + ", type=" + type + "]";
    }

}
