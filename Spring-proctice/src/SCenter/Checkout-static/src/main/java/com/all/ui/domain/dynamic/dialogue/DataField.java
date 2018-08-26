package com.AL.ui.domain.dynamic.dialogue;

import java.util.List;

import com.AL.ui.domain.sales.BooleanConstraint;
import com.AL.ui.domain.sales.IntegerConstraint;
import com.AL.ui.domain.sales.StringConstraint;

public class DataField {
    private String externalId;
    private String featureExternalId;
    private String displayGroup;
    private boolean enabled;
    private boolean required;
    private List<DialogueTag> tags;
    private String text;
    private String contentRefId;
    private String valueTarget;
    private BooleanConstraint booleanConstraint;
    private IntegerConstraint integerConstraint;
    private StringConstraint stringConstraint;
    
    private String description;
    private String infoType;
    private String validation;
    private String name;
    private String type;
    private String dialogueName;
    private String dialogueGroupName;

	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getFeatureExternalId() {
		return featureExternalId;
	}
	public void setFeatureExternalId(String featureExternalId) {
		this.featureExternalId = featureExternalId;
	}
	public String getDisplayGroup() {
		return displayGroup;
	}
	public void setDisplayGroup(String displayGroup) {
		this.displayGroup = displayGroup;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public List<DialogueTag> getTags() {
		return tags;
	}
	public void setTags(List<DialogueTag> tags) {
		this.tags = tags;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getContentRefId() {
		return contentRefId;
	}
	public void setContentRefId(String contentRefId) {
		this.contentRefId = contentRefId;
	}
	public String getValueTarget() {
		return valueTarget;
	}
	public void setValueTarget(String valueTarget) {
		this.valueTarget = valueTarget;
	}
	public BooleanConstraint getBooleanConstraint() {
		return booleanConstraint;
	}
	public void setBooleanConstraint(BooleanConstraint booleanConstraint) {
		this.booleanConstraint = booleanConstraint;
	}
	public IntegerConstraint getIntegerConstraint() {
		return integerConstraint;
	}
	public void setIntegerConstraint(IntegerConstraint integerConstraint) {
		this.integerConstraint = integerConstraint;
	}
	public StringConstraint getStringConstraint() {
		return stringConstraint;
	}
	public void setStringConstraint(StringConstraint stringConstraint) {
		this.stringConstraint = stringConstraint;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getValidation() {
		return validation;
	}
	public void setValidation(String validation) {
		this.validation = validation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	
	public String toString() {
		return "Data Group  : " +
		"externalId         :" + externalId         +
		"featureExternalId  :" + featureExternalId  +
		"displayGroup       :" + displayGroup       +
		"enabled            :" + enabled            +
		"required           :" + required           +
		"tags               :" + tags               +
		"text               :" + text               + 
		"contentRefId       :" + contentRefId       + 
		"valueTarget        :" + valueTarget        + 
		"booleanConstraint  :" + booleanConstraint  + 
		"integerConstraint  :" + integerConstraint  + 
		"stringConstraint   :" + stringConstraint   + 
		"description        :" + description        + 
		"infoType           :" + infoType           + 
		"validation         :" + validation         + 
		"name               :" + name               + 
		"type               :"+type;

	}
	public void setDialogueName(String dialogueName) {
		this.dialogueName = dialogueName;
	}
	public String getDialogueName() {
		return dialogueName;
	}
	public void setDialogueGroupName(String dialogueGroupName) {
		this.dialogueGroupName = dialogueGroupName;
	}
	public String getDialogueGroupName() {
		return dialogueGroupName;
	}
	
}
