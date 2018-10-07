package abc.xyz.pts.bcs.common.dao.support.query.arg;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import static org.junit.Assert.*;

public class AbstractTimeRangeQueryArgTest {

    @Test
    public void timeRangeQuery_Given_FromTime_LessThan_ToTime() {
        TimeRangeQueryArg arg = new TimeRangeQueryArg("12:00", "13:00", Field.DEP_TIME, Table.TRAVELLERS);
        assertEquals(Table.TRAVELLERS.getValue() + "." + Field.DEP_TIME + " BETWEEN ? AND ? ", arg.getWhereClause());
    }

    @Test
    public void timeRangeQuery_Given_FromTime_EqualTo_ToTime() {
        TimeRangeQueryArg arg = new TimeRangeQueryArg("13:00", "13:00", Field.DEP_TIME, Table.TRAVELLERS);
        assertEquals(" (" + Table.TRAVELLERS.getValue() + "." + Field.DEP_TIME + " >= ? OR " + Table.TRAVELLERS.getValue() + "." + Field.DEP_TIME + " <= ? ) ", arg.getWhereClause());
    }

    @Test
    public void timeRangeQuery_Given_FromTime_Without_ToTime() {
        TimeRangeQueryArg arg = new TimeRangeQueryArg("12:01", null, Field.DEP_TIME, Table.TRAVELLERS);
        assertEquals(Table.TRAVELLERS.getValue() + "." + Field.DEP_TIME + " = ? ", arg.getWhereClause());
    }

    @Test
    public void timeRangeQuery_Given_FromTime_Verify_CriteriaValue() {
        TimeRangeQueryArg arg = new TimeRangeQueryArg("12:01", null, Field.DEP_TIME, Table.TRAVELLERS);
        assertEquals(43260, arg.getCriteriaValues().get(0));
    }

    @Test
    public void timeRangeQuery_Given_ToTime_Verify_CriteriaValue() {
        TimeRangeQueryArg arg = new TimeRangeQueryArg("12:01", "13:09", Field.DEP_TIME, Table.TRAVELLERS);
        assertEquals(47340, arg.getCriteriaValues().get(1));
    }

    @Test
    public void timeRangePastMidnightQuery_Given_FromTime_Verify_CriteriaValue() {
        TimeRangeMinutesPastMidnightQueryArg arg = new TimeRangeMinutesPastMidnightQueryArg("09:02", "13:01", Field.DEP_TIME, Table.TRAVELLERS);
        assertEquals((9 * 60) + 2, arg.getCriteriaValues().get(0));
    }

    @Test
    public void timeRangePastMidnightQuery_Given_ToTime_Verify_CriteriaValue() {
        TimeRangeMinutesPastMidnightQueryArg arg = new TimeRangeMinutesPastMidnightQueryArg("12:01", "13:09", Field.DEP_TIME, Table.TRAVELLERS);
        assertEquals((13 * 60) + 9, arg.getCriteriaValues().get(1));
    }
    
    @Test
    public void timeRangeQuery_Given_ToTime_MultilpeInstances_Verify_CriteriaValue() {
        TimeRangeQueryArg arg = new TimeRangeQueryArg("12:01", "13:09", Field.DEP_TIME, Table.TRAVELLERS);
        assertEquals(47340, arg.getCriteriaValues().get(1));
        
        TimeRangeQueryArg arg1 = new TimeRangeQueryArg("11:04", "15:10", Field.DEP_TIME, Table.TRAVELLERS);
        assertEquals((15*3600)+60*10, arg1.getCriteriaValues().get(1));
        
        assertEquals(47340, arg.getCriteriaValues().get(1));// to show no interdependency between the static member classes
    }
}
