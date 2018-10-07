package abc.xyz.pts.bcs.common.dao.utils.support.query;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertTrue;
import org.junit.Test;


import abc.xyz.pts.bcs.common.dao.support.query.AdvancedField;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.NullField;
import abc.xyz.pts.bcs.common.dao.support.query.QueryBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.QueryBuilderAdvance;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.WithSelectQueryBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.arg.DateRangeQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.SimpleQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.WildCardQueryArg;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;


public class QueryBuilderAdvanceTest {
    private TableActionCommand actionCmd;
    private Field orderByField;
    private QueryBuilderAdvance queryBuilderAdvance;
    private WithSelectQueryBuilder withSelectQueryBuilder;
    private QueryBuilder referralQb;
    private QueryBuilder referralTravelQb;
    
    private String expectedReferralNumber = "Some Referral Number";
    private String expectedFlightCareerCode = "Flight Career Code";
    private String expectedFlightNumber = "Flight Number";
    private String expectedDepartureAirport = "Departure Airport";
    private String expectedForeNames = "Fore Name";
    private String expectedLastName = "Last Name";
    private String expectedGender = "Gender";

    

    @Test
    public void getQuerySql() {
	
	// Given
	
	boolean multipleResults = true;
	boolean page = true;
	
	actionCmd = new TableActionCommand();	
	orderByField = Field.REF_ID;
	
	withSelectQueryBuilder = prepareWithSelectQuery();	
	referralQb = prepareReferralQueryBuilder();	
	referralTravelQb = prepareReferralTravelQueryBuilder();	
	
	List<Object> crietria = withSelectQueryBuilder.getCriteria();
	
	queryBuilderAdvance = new QueryBuilderAdvance(actionCmd, orderByField);
	
	queryBuilderAdvance.setWithQb(withSelectQueryBuilder);
	queryBuilderAdvance.getCriteria().addAll(crietria);
	
		
	queryBuilderAdvance.getQueryBuilderList().add(referralQb);
	queryBuilderAdvance.getQueryBuilderList().add(referralTravelQb);        

	// When

	String sqlQuery = queryBuilderAdvance.getQuerySql(multipleResults, page);

	// Then
	
	//Assert criteria has all the values we set
        assertTrue(crietria.contains(expectedDepartureAirport));
        assertTrue(crietria.contains(expectedFlightCareerCode));
        assertTrue(crietria.contains(expectedFlightNumber));
        assertTrue(crietria.contains(expectedForeNames));
        assertTrue(crietria.contains(expectedGender));
        assertTrue(crietria.contains(expectedLastName));
        assertTrue(crietria.contains(expectedReferralNumber));
        
        
        //Assert sql Query was formed as per expectation
	assertTrue(sqlQuery.contains("WITH  "+  Table.MATCHED_RFRL.getValue() + " AS"));
	assertTrue(sqlQuery.contains("UNION ALL"));
	assertTrue(sqlQuery.contains("FROM " + Table.REFERRALS+" "+ Table.RFRL + "," + Table.TRAVELLERS+ " " + Table.TRVLR + "," +
		Table.FLIGHT_SEGMENTS + " "+ Table.FLTS + "," + Table.FLIGHT_MANIFESTS+ " " + Table.FLTM));
	assertTrue(sqlQuery.contains("ORDER BY"+ " " + orderByField+ " ASC"));
	
    }

    private WithSelectQueryBuilder prepareWithSelectQuery() {
    	WithSelectQueryBuilder qb = new WithSelectQueryBuilder(Table.MATCHED_RFRL.getValue(), actionCmd, Field.ID);
    	qb.addSelect(Field.ID, Field.REF_ID, Table.RFRL);
    	qb.addSelect(AdvancedField.VALUE_ZERO, Field.REFERRAL_LEVEL);
    	qb.addSelect(Field.STATUS, Field.REFERRAL_STATUS, Table.RFRL);
    	qb.addSelect(Field.CREATED_DATETIME, Table.RFRL);
    
    	// * FROM
    	qb.addTable(Table.REFERRALS, Table.RFRL);
    	qb.addTable(Table.TRAVELLERS, Table.TRVLR);
    	qb.addTable(Table.FLIGHT_SEGMENTS, Table.FLTS);
    	qb.addTable(Table.FLIGHT_MANIFESTS, Table.FLTM);
    
    	// **********
    	// * WHERE
    	// **********
    	qb.addTableJoin(Table.TRVLR, Field.FLTS_FLIGHT_SEG_ID, Table.FLTS, Field.FLIGHT_SEG_ID);
    	qb.addTableJoin(Table.TRVLR, Field.ID, Table.RFRL, Field.TRA_ID);
    	qb.addOuterTableJoin(Table.TRVLR, Field.FLTM_ID, Table.FLTM, Field.ID, Table.FLTM, Field.FLTM_ID);
    
    	// ** Referral
    	qb.addWhereClause(new SimpleQueryArg(Field.ID, Table.RFRL, expectedReferralNumber));
    	qb.addWhereClause(new DateRangeQueryArg(Calendar.getInstance(), Calendar.getInstance(), Field.CREATED_DATETIME,
    		Table.RFRL));
    
    	// ** Flights
    	qb.addWhereClause(new SimpleQueryArg(Field.OPER_CARRIER_CODE, Table.FLTS, expectedFlightCareerCode));
    	qb.addWhereClause(new SimpleQueryArg(Field.OPER_FLIGHT_NO, Table.FLTS, expectedFlightNumber));
    	qb.addWhereClause(new SimpleQueryArg(Field.DEP_AIRPORT_CODE, Table.FLTS, expectedDepartureAirport));
    
    	// ** Traveller
    	qb.addWhereClause(new WildCardQueryArg(expectedForeNames, Field.FORENAME, Table.TRVLR));
    	qb.addWhereClause(new WildCardQueryArg(expectedLastName, Field.LAST_NAME, Table.TRVLR));
    	qb.addWhereClause(new SimpleQueryArg(Field.GENDER, Table.TRVLR, expectedGender));

    	return qb;
    }

    private QueryBuilder prepareReferralTravelQueryBuilder() {
    	QueryBuilder qb = new QueryBuilder(actionCmd, Field.ID, Table.MATCHED_RFRL);
    
    	qb.addSelect(Field.REF_ID, Table.MRFRL);
    	qb.addSelect(AdvancedField.HIT_TYPE_SEQ, Field.REFERRAL_LEVEL);
    	qb.addSelect(Field.REFERRAL_STATUS, Table.MRFRL);
    	qb.addSelect(Field.CREATED_DATETIME, Table.MRFRL);
    
    	// * FROM
    	qb.addTable(Table.REFERRAL_HITS, Table.RFRL_HIT);
    	qb.addTable(Table.MATCHED_RFRL, Table.MRFRL);
    
    	// * WHERE
    	qb.addTableJoin(Table.MRFRL, Field.REF_ID, Table.RFRL_HIT, Field.REF_ID);
    
    	return qb;
    }

    private QueryBuilder prepareReferralQueryBuilder() {
    	QueryBuilder qb = new QueryBuilder(actionCmd, Field.REF_ID, Table.MATCHED_RFRL);
    
    	// * Select
    	qb.addSelect(AdvancedField.VALUE_ZERO, Field.REFERRAL_LEVEL);
    	qb.addSelect(Field.REFERRAL_STATUS, Table.MATCHED_RFRL);
    	qb.addSelect(Field.CREATED_DATETIME, Table.MATCHED_RFRL);
    	qb.addSelect(Field.REFERRAL_REC_ACTION_CODE, Table.MATCHED_RFRL);
    	qb.addSelect(NullField.getInstance(Field.SEVERITY_LEVEL));
    	qb.addSelect(NullField.getInstance(Field.ACTION_CODE));
    
    	// * FROM
    	qb.addTable(Table.MATCHED_RFRL);
    
    	return qb;
    }
}
