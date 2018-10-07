package abc.xyz.pts.bcs.common.dao.utils.support.query;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.QueryBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.arg.SimpleQueryArg;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

import junit.framework.TestCase;

public class QueryBuilderTest extends TestCase {

	@Test
	public void testQueryBuilder() {
		
		TableActionCommand tableCommand = new TableActionCommand();
		List<String> sortByColumns = new ArrayList<String>();
		sortByColumns.add(Field.ARR_AIRPORT_CODE.name());
		tableCommand.setSortByColumns(sortByColumns);
		tableCommand.setAscDesc("DESC");
		
		QueryBuilder builder = new QueryBuilder(tableCommand);
		
		builder.addSelect(Field.ARR_AIRPORT_CODE, Table.FLIGHT_SEGMENTS);
		builder.addSelect(Field.DEP_AIRPORT_CODE, Table.FLIGHT_SEGMENTS);
		
		builder.addTable(Table.FLIGHT_SEGMENTS);
		builder.addTable(Table.TRAVELLERS);
		
		builder.addTableJoin(Table.FLIGHT_SEGMENTS, Field.ARR_AIRPORT_CODE, Table.TRAVELLERS, Field.DEP_AIRPORT_CODE);
		
		builder.addWhereClause(new SimpleQueryArg(Field.ARR_AIRPORT_CODE, Table.FLIGHT_SEGMENTS, "X"));
		builder.addWhereClause(new SimpleQueryArg(Field.ARR_AIRPORT_CODE, Table.FLIGHT_SEGMENTS, "Y"));
		
		builder.addGroupBy(Field.CARRIER_CODE);
		builder.addGroupBy(Field.CODE_SHARE);
		
		String sql = builder.getPagedQuerySql();
		
		assertTrue(sql.contains("SELECT " + Table.FLIGHT_SEGMENTS.getValue() + "." + Field.ARR_AIRPORT_CODE + ","));
		assertTrue(sql.contains("FROM " + Table.FLIGHT_SEGMENTS.getValue() + ","));
		assertTrue(sql.contains("WHERE " + Table.FLIGHT_SEGMENTS.getValue() + "." + Field.ARR_AIRPORT_CODE + " = "));
		assertTrue(sql.contains(Table.FLIGHT_SEGMENTS.getValue() + "." + Field.ARR_AIRPORT_CODE + "=?"));
		assertTrue(sql.contains("AND " + Table.FLIGHT_SEGMENTS.getValue() + "." + Field.ARR_AIRPORT_CODE + "=?"));
		assertTrue(sql.contains("ORDER BY " + Field.ARR_AIRPORT_CODE + " DESC"));
		assertTrue(sql.contains("GROUP BY " + Field.CARRIER_CODE + ","));
		
		assertEquals("X", builder.getCriteriaValues()[0]);
		assertEquals("Y", builder.getCriteriaValues()[1]);
	}
}
