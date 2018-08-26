package com.AL.ome.validation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.validation.OMEValidator;
import com.AL.V.beans.LineitemScheduleInfo;
import com.AL.V.beans.entity.CustomSelection;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.SelectedDialogue;
import com.AL.V.beans.entity.SelectedFeatureValue;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class OMEValidatorTest
{

	private SalesOrder order;
	
	private OMEValidator omeValidator;
	
	@Before
	public void setUp() {
		omeValidator = new OMEValidator();
		
		order = new SalesOrder();
		order.setAgentId( "User1" );
		order.setSource( "Web" );
		order.setMoveDate( getMoveDate() );
		order.setWhenToCallBack( getWhenToCallbackDate() );
		
		//Creating lineitem
		LineItem li1 = new LineItem();
		
		//Creating scheduling info
		LineitemScheduleInfo schInfo = new LineitemScheduleInfo();
		schInfo.setDesiredStartDate( getDesiredStartDate() );
		schInfo.setScheduledStartDate( getScheduledStartDate() );
		li1.setLineitemScheduleInfo( schInfo );
		
		/*CustomSelection sel1 = new CustomSelection();
		sel1.setSelectionExtId( "A1" );
		sel1.setParentChoiceExtId( "B1" );
		Set<CustomSelection> selSet = new HashSet<CustomSelection>();
		selSet.add( sel1 );
		
		SelectedDialogue dlg1 = new SelectedDialogue();
		dlg1.setExternalId( "A1" );
		Set<SelectedDialogue> dlgSet = new HashSet<SelectedDialogue>();
		dlgSet.add( dlg1 );
		
		SelectedFeatureValue sfv1 = new SelectedFeatureValue();
		sfv1.setExternalId( null );
		List<SelectedFeatureValue> sfvList = new ArrayList<SelectedFeatureValue>();
		li1.setSelections( selSet );
		li1.setDialogues( dlgSet );
		li1.setSelectedFeatureValues( sfvList );*/
		
		List<LineItem> liList = new ArrayList<LineItem>();
		liList.add( li1 );
		order.setLineItems( liList );
		
	}
	
	@Test
	public void testOrderValidation(){
		assertNotNull(omeValidator);
		assertNotNull(order);
		Set<ConstraintViolation<SalesOrder>>  violations = omeValidator.validate( order );
		System.out.println("*************************************");
		for(ConstraintViolation<SalesOrder> violation : violations){
			System.out.println(violation.getPropertyPath().toString() + " " + violation.getMessage() );
		}
		assertTrue(violations.size() == 2 );
		System.out.println("*************************************");
		
		
	}
	
	private Calendar getMoveDate(){
		Calendar cal = getDate(2012,12,12);
	    return cal;
	}
	
	private Calendar getWhenToCallbackDate(){
		Calendar cal = getDate(2012,12,12);
	    return cal;
	}
	
	private Calendar getDesiredStartDate(){
		Calendar cal = getDate( 2001, 12, 12 );
	    return cal;
	}
	
	private Calendar getScheduledStartDate(){
		Calendar cal = getDate( 2002, 12, 12 );
	    return cal;
	}
	
	private Calendar getDate(int year, int month, int day)
	{
		Calendar cal = Calendar.getInstance();
	    String date = year + "-" + month + "-" + day;
	    java.util.Date utilDate = null;

	    try {
	      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	      utilDate = formatter.parse(date);
	      //System.out.println("utilDate:" + utilDate);
	    } catch (ParseException e) {
	      System.out.println(e.toString());
	      e.printStackTrace();
	    }
	    cal.setTime( utilDate );
		return cal;
	}
}
