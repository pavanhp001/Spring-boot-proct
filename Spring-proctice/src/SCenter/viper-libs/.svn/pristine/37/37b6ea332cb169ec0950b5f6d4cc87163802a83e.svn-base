package com.A.vm.util.converter.marshall;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.V.beans.entity.CustomerInteraction;
import com.A.xml.v4.CommentCollectionType;
import com.A.xml.v4.CustomerInteractionList;
import com.A.xml.v4.CustomerInteractionType;
import com.A.xml.v4.JobType;

@Component
public class MarshallCustomerInteraction {
	private final Logger logger = Logger
			.getLogger(MarshallCustomerInteraction.class);

	/**
	 * A method to copy Consumer Interaction details from ConsumerInteraction
	 * bean to XML
	 *
	 * @param srcCI
	 * @param destConsumerType
	 */

	public void buildCIResponse(Set<CustomerInteraction> srcCIList,
			final JobType jobType) {

		if ((jobType == null) || (srcCIList == null) || (srcCIList.size() == 0)) {
			return;
		}

		CommentCollectionType ccType = jobType.getComments();

		if (ccType == null) {
			ccType = jobType.addNewComments();
			jobType.setComments(ccType);
		}

		if (ccType.getCommentList() == null) {
			throw new IllegalArgumentException("comment collection is null");
		}

		for (CustomerInteraction ci : srcCIList) {
			buildCIResponse(jobType.getComments().addNewComment(), ci);
		}

	}

	public void buildCIResponse(CustomerInteraction srcCI,
			CustomerInteractionList ciList) {
		logger.trace("Marshalling Consumer Interaction");

		// CustomerInteractionList ciList =
		// destConsumerType.addNewCustomerInteractionList();
		CustomerInteractionType ciType = ciList.addNewCustomerInteraction();
		if (srcCI != null) {
			buildCIResponse(ciType, srcCI);
		}
	}

	public void buildCIResponse(CustomerInteractionType ciType,
			CustomerInteraction srcCI) {
		logger.trace("Marshalling Consumer Interaction");
		ciType.setCustomerId(srcCI.getCustomerExternalId());
		ciType.setOrderId(srcCI.getOrderExternalId() != null ? srcCI
				.getOrderExternalId() : 0L);
		ciType.setSource(srcCI.getSource() != null ? srcCI.getSource() : "");
		ciType.setAgentId(srcCI.getAgentExternalId() != null ? srcCI
				.getAgentExternalId() : "");
		ciType.setNotes(srcCI.getNotes());
		ciType.setInteractionDate(srcCI.getDateOfInteraction());
		ciType.setCustomerFullName(srcCI.getCustomerName());
		ciType.setAgentName(srcCI.getAgentName());
		ciType.setLineItemId(srcCI.getLineItemExternalId());
		if (srcCI.getProviderId() != null) {
			ciType.setProviderId(srcCI.getProviderId());
		}

		if (srcCI.getExternalId() != null) {
		    ciType.setExternalId(srcCI.getExternalId());
		}

		if(srcCI.getServiceType() != null) {
		    ciType.setServiceType(srcCI.getServiceType());
		}
	}

}
