package com.AL.ui.mapper;

import java.util.ArrayList;
import java.util.List;

import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataFieldDependency;
import com.AL.ui.domain.dynamic.dialogue.DataFieldMatrix;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.DependentDataFields;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.domain.dynamic.dialogue.DialogueTag;
import com.AL.ui.domain.sales.BooleanConstraint;
import com.AL.ui.domain.sales.IntegerConstraint;
import com.AL.ui.domain.sales.SalesContext;
import com.AL.ui.domain.sales.StringConstraint;
import com.AL.xml.di.v4.DataFieldDependencyType;
import com.AL.xml.di.v4.DataFieldMatrixType;
import com.AL.xml.di.v4.DataFieldRefType;
import com.AL.xml.di.v4.DataFieldType;
import com.AL.xml.di.v4.DataGroupType;
import com.AL.xml.di.v4.DependentDataFieldsType;
import com.AL.xml.di.v4.DialogueListType;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.di.v4.DialogueTransactionType;
import com.AL.xml.di.v4.DialogueTransactionTypeType;
import com.AL.xml.di.v4.DialogueType;
import com.AL.xml.di.v4.EnterpriseRequestDocumentType;
import com.AL.xml.di.v4.InfoType;
import com.AL.xml.di.v4.SalesContextType;
import com.AL.xml.di.v4.TagType;
//import com.AL.ui.exception.UndefinedFlowException;

public class DialogueMapper extends AbstractMapper {
   
   static public EnterpriseRequestDocumentType createProvisioningDialogueRequest(String marketId) {
	   EnterpriseRequestDocumentType retval = createDialogueRequest("Provisioning");
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.marketItem", marketId));
	   return retval;
   }
   
   static public EnterpriseRequestDocumentType createProvisioningDialogueRequest(String marketId,String businessParty) {
	   EnterpriseRequestDocumentType retval = createDialogueRequest("Provisioning");
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.marketItem", marketId));
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.businessParty", businessParty));
	   return retval;
   }
   
   static public EnterpriseRequestDocumentType createProvisioningDialogueRequest(String marketId,String businessParty, String serviceCategory) {
	   EnterpriseRequestDocumentType retval = createDialogueRequest("Provisioning");
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.marketItem", marketId));
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.businessParty", businessParty));
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.serviceCategory", serviceCategory));
	   return retval;
   }
   
   static public EnterpriseRequestDocumentType createProvisioningDialogueRequest(String marketId,String businessParty, String serviceCategory, String businessParent) {
	   EnterpriseRequestDocumentType retval = createDialogueRequest("Provisioning");
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.marketItem", marketId));
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.businessParty", businessParty));
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.serviceCategory", serviceCategory));
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.serviceCategory", businessParent));
	   return retval;
   }
   
   static public EnterpriseRequestDocumentType createProvisioningDialogueRequest(String marketId, String productId, String businessParty, String serviceCategory, String businessParent) {
	   EnterpriseRequestDocumentType retval = createDialogueRequest("Provisioning");
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.marketItem", marketId));
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.item", productId));
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.businessParty", businessParty));
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.serviceCategory", serviceCategory));
	   addSubcontext(retval.getSalesContext(), createSalesContextEntity("provisioning", "provisioning.businessParty.parent", businessParent));
	   return retval;
   }

   static public EnterpriseRequestDocumentType createDialogueRequest(String phaseName) {
	   SalesContext model = new SalesContext();

	   model.setPhaseName(phaseName);

	   EnterpriseRequestDocumentType request = new EnterpriseRequestDocumentType();
	   SalesContextType context = new SalesContextType();
	   addSubcontext(context, createSalesContextEntityFlow(model));
	   request.setSalesContext(context);

	   DialogueTransactionTypeType txnType = new DialogueTransactionTypeType();
	   txnType.setDialogueTransactionType(DialogueTransactionType.GET_DIALOGUES.value());
	   request.setTransactionType(txnType);
	   request.setGUID(model.getGUID());
	   return request;
   }

   static public List<Dialogue> processResponse(DialogueResponseType response) {
	   List<Dialogue>retval = new ArrayList<Dialogue>();

	   for (DialogueType dt : response.getResults().getDialogueList().getDialogue()) {
		   Dialogue d = new Dialogue();
		   d.setExternalId(dt.getExternalId());
		   d.setName(dt.getName());

		   if (dt.getDataGroupList() != null) {
			   List<DataGroupType> dgts = dt.getDataGroupList().getDataGroup();
			   for (DataGroupType dgt : dgts) {
				   addDataGroupType(d, dgt);
			   }
		   }
		   if (dt.getTags() != null) {
			   for (TagType tt : dt.getTags().getTag()) {
				   // TODO: Not certain what a TagType is supposed to look like.
				   DialogueTag dtag = new DialogueTag();
				   dtag.setName(tt.getName().toString());
				   dtag.setValue(tt.getValue().toString());
				   d.addTag(dtag);
			   }
		   }
		   retval.add(d);
	   }
	   return retval;
   }
   
   static public List<Dialogue> processResponse(DialogueListType dialogueListType) {
	   List<Dialogue>retval = new ArrayList<Dialogue>();

	   for (DialogueType dt : dialogueListType.getDialogue()) {
		   Dialogue d = new Dialogue();
		   d.setExternalId(dt.getExternalId());
		   d.setName(dt.getName());

		   if (dt.getDataGroupList() != null) {
			   List<DataGroupType> dgts = dt.getDataGroupList().getDataGroup();
			   for (DataGroupType dgt : dgts) {
				   addDataGroupType(d, dgt);
			   }
		   }
		   if (dt.getTags() != null) {
			   for (TagType tt : dt.getTags().getTag()) {
				   // TODO: Not certain what a TagType is supposed to look like.
				   DialogueTag dtag = new DialogueTag();
				   dtag.setName(tt.getName().toString());
				   dtag.setValue(tt.getValue().toString());
				   d.addTag(dtag);
			   }
		   }
		   retval.add(d);
	   }
	   return retval;
   }

   static protected void addDataGroupType(Dialogue d, DataGroupType dgt) {
	   if ( (d == null) || (dgt == null)) {
		   return;
	   }
	   
	   DataGroup dg = new DataGroup();
	   dg.setDisplayName(dgt.getDisplayName());
	   dg.setName(dgt.getName());

	   List<DataFieldType> dfts = dgt.getDataFieldList().getDataField();
	   for (DataFieldType dft : dfts) {
		   DataField df = new DataField();
		   if (dft.getDataConstraints() != null) {

			   BooleanConstraint bc = AbstractMapper.createBooleanConstraint(dft.getDataConstraints().getBooleanConstraint());
			   if (bc != null) {
				   df.setBooleanConstraint(bc);
			   }

			   IntegerConstraint ic = AbstractMapper.createIntegerConstraint(dft.getDataConstraints().getIntegerConstraint());
			   if (ic != null) {
				   df.setIntegerConstraint(ic);
			   }

			   StringConstraint sc = AbstractMapper.createStringConstraint(dft.getDataConstraints().getStringConstraint());
			   if (sc != null) {
				   df.setStringConstraint(sc);
			   }
		   }

		   df.setContentRefId(dft.getContentRefId());
		   df.setDescription(dft.getDescription());
		   df.setEnabled(dft.isEnabled());
		   df.setExternalId(dft.getExternalId());
		   df.setFeatureExternalId(dft.getFeatureExternalId());
		   InfoType it = dft.getInfoType();
		   if (it != null) {
			   df.setInfoType(it.toString());
		   }
		   df.setName(dft.getName());
		   try {
			   df.setRequired(dft.isRequired());
		   }catch(NullPointerException npe) {
			   // if you are here, then dft.required is null and dft.isRequired throws an NPE.  Assume the DataField is not required.
			   df.setRequired(false);
		   }
		   df.setText(dft.getText());
		   if (dft.getType() != null) {
			   df.setType(dft.getType().value());
		   }

		   df.setValidation(dft.getValidation());
		   df.setValueTarget(dft.getValueTarget());		   
		   dg.addDataField(df);
	   }

	  if (dgt.getTags() != null) {
		  List<TagType> tags = dgt.getTags().getTag();
		  for (TagType tt : tags) {
			  DialogueTag dtag = new DialogueTag();
			  dtag.setName(tt.getName().toString());
			  dtag.setValue(tt.getValue().toString());
			  dg.addTag(dtag);
		  }
	  }

	   DataFieldMatrixType dfmt = dgt.getDataFieldMatrix();
	   List<DataFieldMatrix> dfms = new ArrayList<DataFieldMatrix>();
	   DataFieldMatrix dfm = null;;
	   if (dfmt != null) {
		   dfm = new DataFieldMatrix();
		   List<DataFieldDependencyType>dfdts = dfmt.getDependency();
		   addDependenciesToDataGroup(dg, dfdts, dfm);
		   dfms.add(dfm);
	   }
	   dg.setDataFieldMatrixist(dfms);
	   d.getDataGroupList().add(dg);
   }
   
   static protected void addDependenciesToDataGroup(DataGroup dg, List<DataFieldDependencyType> dfdtList, DataFieldMatrix dfm) {
	   if ( (dg == null) || (dfdtList == null) ) {
		   return;
	   }

	   for (DataFieldDependencyType dfdt : dfdtList) {
		   DataFieldDependency dfd = new DataFieldDependency();
		   dfd.setExternalId(dfdt.getExternalId());
		   dfd.setParentDataFieldType(dfdt.getType().value());
		   if (dfdt.getEnabledDataFields() != null) {
			   addChildrenToDataFieldDependency(dfd, dfdt.getEnabledDataFields());
		   }
		   
		   dfm.getDependencyList().add(dfd);
	
		   //dg.addDataFieldDependency(dfd);
	   }
   }
   
   static protected void addChildrenToDataFieldDependency(DataFieldDependency dfd, List<DependentDataFieldsType> ddfts) {
	   for (DependentDataFieldsType ddft : ddfts) {
		   DependentDataFields ddf = new DependentDataFields();
		   ddf.setValue(ddft.getValue());
		   for (DataFieldRefType dfrt : ddft.getRef()) {
			   ddf.addDataFieldExternalId(dfrt.getExternalId());
		   }
		   dfd.addEnabledDataField(ddf);
	   }
   }
}
