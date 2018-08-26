package com.AL.roundrobin.rr.client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import com.AL.ui.util.Utils;
import com.AL.ui.service.config.ConfigRepo;
///import com.AL.util.ALProperties;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Wrap the calls to the round robin client. Derived from Bhaskar's
 * SelectMemberClient.java.
 * 
 * @author dcaulkins
 */
public class RoundRobinService
{
	private final static Logger smLogger = Logger
			.getLogger( RoundRobinService.class );
	//private static final String BASE_URI = ALProperties.getProperty( "ROUND_ROBIN_SERVICE_URL" );
    //private static final String BASE_URI = "http://roundrobin-gateway.rndprd001st.AL.com/roundrobin/webresources";
	
	//private static final String BASE_URI = "http://accord.dev2.AL.com/roundrobin/webresources";
	private static final String BASE_URI = ConfigRepo.getString("roundrobin.rr_url"); 
	private final static int OK = 200;
	private final static String OFFEREDMEMBERID = "<offeredMemberId>";
	private final static String OFFERTRACKID = "<offerTrackId>";
	private final Client client;
	private final WebResource webResourceCallRoundRobin;
	private final WebResource webResourceAccept;
	private final WebResource webResourceReject;
	private final WebResource webResourceOnlyOne;
	private final WebResource webResourceCustomerSelected;
	/* Maintain the last offer track id */
	private String mOfferTrackId;
	

	public String getmOfferTrackId() {
		return mOfferTrackId;
	}

	public void setmOfferTrackId(String mOfferTrackId) {
		this.mOfferTrackId = mOfferTrackId;
	}

	/**
	 * Constructor - Sets the URI for the REST service
	 */
	public RoundRobinService ()
	{
		smLogger.debug( "Entering_RoundRobinService_constructor" );
		smLogger.info("round_robin_url="+ConfigRepo.getString("roundrobin.rr_url")); 

		ClientConfig config = new DefaultClientConfig();
		client = Client.create( config );
		webResourceCallRoundRobin = client.resource( BASE_URI ).path(
				"selectmemberlist/getmember" );
		webResourceAccept = client.resource( BASE_URI ).path(
				"selectmemberlist/memberaccepted" );
		webResourceReject = client.resource( BASE_URI ).path(
				"selectmemberlist/memberrejected" );
		webResourceOnlyOne = client.resource( BASE_URI ).path(
				"selectmemberlist/onlyonemember" );
		webResourceCustomerSelected = client.resource( BASE_URI ).path(
				"selectmemberlist/customerselected" );
	}

	/**
	 * Call the round robin to determine the next supplier given the list of
	 * suppliers
	 * 
	 * @param correlateId
	 *          the correlate id
	 * @param memberType
	 * @param supplierList
	 * @return the next supplier
	 */
	public String getRoundRobinSupplier( String correlateId, String memberType,
			List<String> supplierList )
	{
		smLogger.info( "Entering_getRoundRobinSupplier_method" );
		String roundRobinSupplier = "";
		String response = "";
		mOfferTrackId = "";
		/* Build the xml request */
		StringBuilder xmlBuilder = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<SelectMember>\n" );
		xmlBuilder.append( "  <memberType>" ).append( memberType )
				.append( "</memberType>\n" );
		xmlBuilder.append( "  <CorrelateId>" ).append( correlateId )
				.append( "</CorrelateId>\n" );
		for (String supplier : supplierList)
		{
			xmlBuilder.append( "  <memberId>" ).append( supplier )
					.append( "</memberId>\n" );
		}
		xmlBuilder.append( "</SelectMember>" );
		/* Send the request */
		String xmlRequest = xmlBuilder.toString();
		smLogger.info("xmlRequest_in_Round_Robin_Service="+xmlRequest);
		ClientResponse xmlResponse = submitRequest( webResourceCallRoundRobin,
				xmlRequest, "Round Robin Supplier" );
		if ( xmlResponse != null )
		{
			response = xmlResponse.getEntity( String.class );
			smLogger.info( "Round_robin_supplier_response=" + response);
			int offerTrackIdBeginIndex = response.indexOf( OFFERTRACKID )
					+ OFFERTRACKID.length();
			int offerTrackIdEndIndex = response.indexOf( "</offerTrackId>" );
			if ( offerTrackIdEndIndex >= offerTrackIdBeginIndex )
			{
				mOfferTrackId = response.substring( offerTrackIdBeginIndex,
						offerTrackIdEndIndex );
			}
			int supplierBeginIndex = response.indexOf( OFFEREDMEMBERID )
					+ OFFEREDMEMBERID.length();
			int supplierEndIndex = response.indexOf( "</offeredMemberId>" );
			if ( supplierEndIndex >= supplierBeginIndex )
			{
				roundRobinSupplier = response.substring( supplierBeginIndex,
						supplierEndIndex );
			}
			this.setmOfferTrackId(mOfferTrackId);
		}
		return response;
	}

	/**
	 * Update that a the customer had a preferred supplier
	 * 
	 * @param correlateId
	 *          the correlate id
	 * @param memberType
	 * @param supplier
	 *          the preferred supplier
	 * @return true if successful
	 */
	public boolean updatePreferredSupplierAccepted( String correlateId,
			String memberType, String acceptedSupplier )
	{
		smLogger.info( "Entering_updatePreferredSupplierAccepted_method" );
		boolean success = false;
		StringBuilder xmlBuilder = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
						+ "<CustomerSelectedNotice>\n" );
		xmlBuilder.append( "<CorrelateId>" ).append( correlateId )
				.append( "</CorrelateId>\n" );
		xmlBuilder.append( "<memberId>" ).append( acceptedSupplier )
				.append( "</memberId>\n" );
		xmlBuilder.append( "  <memberType>" ).append( memberType )
				.append( "</memberType>\n" );
		xmlBuilder.append( "</CustomerSelectedNotice>" );
		smLogger.info( "Update_that_preferred_supplier_was_selected"+ acceptedSupplier );
		/* Send the request */
		String xmlRequest = xmlBuilder.toString();
		ClientResponse xmlResponse = submitRequest( webResourceCustomerSelected,
				xmlRequest, "Preferred Supplier Accepted" );
		if ( xmlResponse != null )
		{
			String response = xmlResponse.getEntity( String.class );
			smLogger.info( "Preferred_Supplier_Accepted_response" + response);
			int status = xmlResponse.getStatus();
			success = ( status == OK );
		}
		/* Done with this round robin offer - if there was one */
		this.setmOfferTrackId("");
		return success;
	}

	/**
	 * The customer rejected the round robin selection.
	 * 
	 * @param correlateId
	 *          the correlate id
	 * @param rejectedSupplier
	 *          the rejected supplier
	 * @return true if successful
	 */
	public boolean updateRoundRobinRejected( String correlateId,
			String rejectedSupplier, String offerTrackId)
	{
		if (Utils.isBlank(offerTrackId)) {
			offerTrackId = this.getmOfferTrackId();
		}
		smLogger.info( "Entering_updateRoundRobinRejected_method" );
		boolean success = false;
		StringBuilder xmlBuilder = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
						+ "<MemberRejectedNotice>\n" );
		xmlBuilder.append( "<CorrelateId>" ).append( correlateId )
				.append( "</CorrelateId>\n" );
		xmlBuilder.append( "<offerTrackId>" ).append(offerTrackId)
				.append( "</offerTrackId>\n" );
		xmlBuilder.append( "<memberId>" ).append( rejectedSupplier )
				.append( "</memberId>\n" );
		xmlBuilder.append( "</MemberRejectedNotice>" );
		smLogger.info( "Update_that_a_round_robin_supplier_was_rejected="+rejectedSupplier );
		/* Send the request */
		String xmlRequest = xmlBuilder.toString();
		ClientResponse xmlResponse = submitRequest( webResourceReject, xmlRequest,
				"Round Robin Rejected" );
		if ( xmlResponse != null )
		{
			String response = xmlResponse.getEntity( String.class );
			smLogger.info( "Round_Robin_Rejected_response="+ response);
			int status = xmlResponse.getStatus();
			success = ( status == OK );
		}
		/* Done with this round robin offer */
		this.setmOfferTrackId("");
		return success;
	}

	/**
	 * The customer accepted the round robin selection
	 * 
	 * @param correlateId
	 *          the correlate id
	 * @param acceptedSupplier
	 *          the selected supplier
	 * @return true if successful
	 */
	public boolean updateRoundRobinAccepted( String correlateId,
			String acceptedSupplier, String offerTrackId)
	{
		if (Utils.isBlank(offerTrackId)) {
			offerTrackId = this.getmOfferTrackId();
		}
		smLogger.info( "Entering_updateRoundRobinAccepted_method" );
		boolean success = false;
		StringBuilder xmlBuilder = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
						+ "<MemberAcceptedNotice>\n" );
		xmlBuilder.append( "<CorrelateId>" ).append( correlateId )
				.append( "</CorrelateId>\n" );
		xmlBuilder.append( "<offerTrackId>" ).append(offerTrackId)
				.append( "</offerTrackId>\n" );
		xmlBuilder.append( "<memberId>" ).append( acceptedSupplier )
				.append( "</memberId>\n" );
		xmlBuilder.append( "</MemberAcceptedNotice>" );
		smLogger.info( "Update that a round robin supplier was accepted: "
				+ acceptedSupplier );
		/* Send the request */
		String xmlRequest = xmlBuilder.toString();
		ClientResponse xmlResponse = submitRequest( webResourceAccept, xmlRequest,
				"Round Robin Accepted" );
		if ( xmlResponse != null )
		{
			String response = xmlResponse.getEntity( String.class );
			smLogger.info( "Round_Robin_Accepted_response=" + response);
			int status = xmlResponse.getStatus();
			success = ( status == OK );
		}
		/* Done with this round robin offer */
		this.setmOfferTrackId("");
		return success;
	}

	/**
	 * There is only a single supplier. This is a degenerate case. It is incorrect
	 * to offer the customer a choice - either via round robin or the preferred
	 * supplier dropdown - if there is no choice.
	 * 
	 * @param correlateId
	 *          the correlate id
	 * @param memberType
	 * @param singleSupplier
	 *          the rejected supplier
	 * @return true if successful
	 */
	public boolean updateSingleSupplier( String correlateId, String memberType,
			String singleSupplier )
	{
		smLogger.info( "Entering_updateSingleSupplier_method" );
		boolean success = false;
		StringBuilder xmlBuilder = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
						+ "<OnlyOneMemberNotice>\n" );
		xmlBuilder.append( "<CorrelateId>" ).append( correlateId )
				.append( "</CorrelateId>\n" );
		xmlBuilder.append( "<memberType>" ).append( memberType )
				.append( "</memberType>\n" );
		xmlBuilder.append( "<memberId>" ).append( singleSupplier )
				.append( "</memberId>\n" );
		xmlBuilder.append( "</OnlyOneMemberNotice>" );
		smLogger.info( "Update_that_there_was_only_a_single_supplier="
				+ singleSupplier );
		/* Send the request */
		String xmlRequest = xmlBuilder.toString();
		ClientResponse xmlResponse = submitRequest( webResourceOnlyOne, xmlRequest,
				"Single Supplier" );
		if ( xmlResponse != null )
		{
			String response = xmlResponse.getEntity( String.class );
			smLogger.info( "Single_Supplier_response=" + response);
			int status = xmlResponse.getStatus();
			success = ( status == OK );
		}
		return success;
	}

	/**
	 * Submit an xml request to the round robin service
	 * 
	 * @param webResource
	 *          the web resource
	 * @param xmlRequest
	 *          the request
	 * @param actionName
	 *          text for logging messages about this request
	 * @return the response
	 */
	private ClientResponse submitRequest( WebResource webResource,
			String xmlRequest, String actionName )
	{
		smLogger.info( actionName + " request: " + xmlRequest );
		ClientResponse xmlResponse = null;
		try
		{
			xmlResponse = webResource.type( MediaType.TEXT_XML ).put(
					ClientResponse.class, xmlRequest );
		}
		catch (Exception sexc)
		{
			smLogger.error( "Error_with " + xmlRequest, sexc );
		}
		if ( xmlResponse == null )
		{
			smLogger.error( actionName + " response is null" );
		}
		return xmlResponse;
	}

	/**
	 * Destroy the round robin service
	 */
	public void destroy()
	{
		smLogger.debug( "Round robin service ending. " );
		client.destroy();
	}

	/**
	 * Demos the various services
	 */
	public static void main( String[] argv )
	{
		BasicConfigurator.configure(); /* Log4j */
		smLogger.info( "BASE_URI: " + RoundRobinService.BASE_URI + "\n" );
		boolean success;
		String selectedSupplier;
		String[] correlateIdArr = new String[] { "333175" };
		long startTime = System.currentTimeMillis();
		String memberType = "SC PA";
		RoundRobinService roundRobinClient = new RoundRobinService();
		List<String> supplierList = new ArrayList<String>();
		supplierList.add( "First Energy Solutions 5" );
		supplierList.add( "Interstate Gas Supply 5" );
		supplierList.add( "Next Era 5" );
		supplierList.add( "Washington Gas Energy Service 5" );
		supplierList.add( "Bene Gesserit Spice 12" );
		/* First test case */
		System.out.println( "SelectMemberClient: Started\n" );
		//selectedSupplier = roundRobinClient.getRoundRobinSupplier(
		//		correlateIdArr[0], memberType, supplierList );
		//System.out.println( "Next Member for your request: " + selectedSupplier );
		//success = roundRobinClient.updateRoundRobinRejected( correlateIdArr[0],
		//		selectedSupplier );
		//System.out.println( "Round robin rejected: " + success );
		//selectedSupplier = roundRobinClient.getRoundRobinSupplier(
		//		correlateIdArr[0], memberType, supplierList );
		//System.out.println( "Next Member for your request: " + selectedSupplier );
		//success = roundRobinClient.updateRoundRobinAccepted( correlateIdArr[0],
		//		selectedSupplier );
		//System.out.println( "Round robin accepted: " + success );
		long reqDur = System.currentTimeMillis() - startTime;
		System.out.println( "SelectMemberClient: Request processing time is "
				+ reqDur + " millisecs.\n" );
		System.out.println( "SelectMemberClient: Ended OK.\n" );
		roundRobinClient.destroy();
	}
}
