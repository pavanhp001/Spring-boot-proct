package com.A.ui.service.V;

import com.A.ui.repository.ProviderCriteria;
import com.A.ui.repository.SalesContextDefault;
import com.A.ui.template.ServiceabilityTemplateConstant;
import com.A.V.domain.SalesContext;
import com.A.V.factory.SalesContextFactory;
import com.A.V.gateway.ServiceabilityClient;
import com.A.V.gateway.jms.ServiceabilityClientJMS;
import com.A.xml.se.v4.AddressType;
import com.A.xml.se.v4.ServiceabilityEnterpriseResponse;
import com.A.xml.se.v4.ProviderCriteriaType2;


public enum ESEService {
	
	INSTANCE;
	
	
	
	public ServiceabilityEnterpriseResponse getServiceabilityResponse(String inputAddress,  String dwelling) {
		
		SalesContext salesContext = SalesContextFactory.INSTANCE
				.getSalesContext(SalesContextDefault.INSTANCE
						.createSalesContext(dwelling));
		
		ServiceabilityEnterpriseResponse response = null;
		
		String seRequestTemplate = ServiceabilityTemplateConstant.INSTANCE.getServiceabilityRequest(
				inputAddress, salesContext, ProviderCriteria.INSTANCE.createProviderCriteria());
		
		ServiceabilityClient<String> client = new ServiceabilityClientJMS();
		response = client.send(seRequestTemplate);

		return response;
	}
	
	
	public ServiceabilityEnterpriseResponse getServiceabilityResponse(String inputAddress,  String dwelling,
			ProviderCriteriaType2 providerCriteria) {
		
		SalesContext salesContext = SalesContextFactory.INSTANCE
				.getSalesContext(SalesContextDefault.INSTANCE
						.createSalesContext(dwelling));
		
		ServiceabilityEnterpriseResponse response = null;
		
		String seRequestTemplate = ServiceabilityTemplateConstant.INSTANCE.getServiceabilityRequest(
				inputAddress, salesContext, providerCriteria);
		
		ServiceabilityClient<String> client = new ServiceabilityClientJMS();
		response = client.send(seRequestTemplate);

		return response;
	}
	

	public ServiceabilityEnterpriseResponse getServiceabilityResponse(String inputAddress, SalesContext salesContext, 
			ProviderCriteriaType2 providerCriteria,String se2TransactionType) {
		ServiceabilityEnterpriseResponse response = null;
		
		String seRequestTemplate = ServiceabilityTemplateConstant.INSTANCE.getServiceabilityRequest(
				inputAddress, salesContext, providerCriteria,se2TransactionType);
		
		ServiceabilityClient<String> client = new ServiceabilityClientJMS();
		response = client.send(seRequestTemplate);

		return response;
	}
	
	public ServiceabilityEnterpriseResponse getServiceabilityResponse(String inputAddress, SalesContext salesContext, 
			ProviderCriteriaType2 providerCriteria,String se2TransactionType, boolean speedMode) 
	{
		ServiceabilityEnterpriseResponse response = null;
		
		String seRequestTemplate = ServiceabilityTemplateConstant.INSTANCE.getServiceabilityRequest(
				inputAddress, salesContext, providerCriteria,se2TransactionType, speedMode);
		
		ServiceabilityClient<String> client = new ServiceabilityClientJMS();
		response = client.send(seRequestTemplate);

		return response;
	}
	
	/** This method use SE2 call with corrected address 
	 * @param addressType
	 * @param inputAddress
	 * @param salesContext
	 * @param providerCriteria
	 * @param se2TransactionType
	 * @param speedMode
	 * @return
	 */
	public ServiceabilityEnterpriseResponse getCorrectedAddressServiceabilityResponse(AddressType addressType,String inputAddress, SalesContext salesContext, 
			ProviderCriteriaType2 providerCriteria,String se2TransactionType, boolean speedMode) { 
		ServiceabilityEnterpriseResponse response = null;
		String seRequestTemplate = ServiceabilityTemplateConstant.INSTANCE.getCorrectedAddressServiceabilityRequest(addressType,
				inputAddress, salesContext, providerCriteria,se2TransactionType, speedMode);
		ServiceabilityClient<String> client = new ServiceabilityClientJMS();
		response = client.send(seRequestTemplate);
		return response;
	}
	
	public ServiceabilityEnterpriseResponse getServiceabilityResponse(String inputAddress, SalesContext salesContext, 
			ProviderCriteriaType2 providerCriteria) {
		ServiceabilityEnterpriseResponse response = null;
		
		String seRequestTemplate = ServiceabilityTemplateConstant.INSTANCE.getServiceabilityRequest(
				inputAddress, salesContext, providerCriteria);
		
		ServiceabilityClient<String> client = new ServiceabilityClientJMS();
		response = client.send(seRequestTemplate);

		return response;
	}

}
