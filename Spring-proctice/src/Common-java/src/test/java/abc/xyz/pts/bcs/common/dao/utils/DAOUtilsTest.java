package abc.xyz.pts.bcs.common.dao.utils;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;


public class DAOUtilsTest {
    
    private Mockery context = new Mockery();
    
    private ResultSet mockResultSet;
    
    @Before
    public void setUp() throws Exception {
        mockResultSet = context.mock(ResultSet.class);
    }

    @After
    public void tearDown() throws Exception {
        context.assertIsSatisfied();
    }

    @Test
    public void testGetLongWhenColumnValueExist() throws SQLException {
        
        context.checking(new Expectations(){
            {
                oneOf(mockResultSet).getString("Column_Name"); will(returnValue("10"));
            }
        });
        
        assertThat(DAOUtils.getLong(mockResultSet, "Column_Name"), is(10L));
    }
    
    @Test
    public void testGetLongWhenColumnValueDoesntExist() throws SQLException {
        
        context.checking(new Expectations(){
            {
                oneOf(mockResultSet).getString("Column_Name"); will(returnValue(null));
            }
        });
        
        assertThat(DAOUtils.getLong(mockResultSet, "Column_Name"), nullValue());
    }
    
    
    @Test
    public void testGetIntegerWhenColumnValueExist() throws SQLException {
        
        context.checking(new Expectations(){
            {
                oneOf(mockResultSet).getString("Column_Name"); will(returnValue("10"));
            }
        });
        
        assertThat(DAOUtils.getInteger(mockResultSet, "Column_Name"), is(10));
    }

    
    @Test
    public void testGetIntegerWhenColumnValueDoesntExist() throws SQLException {
        
        context.checking(new Expectations(){
            {
                oneOf(mockResultSet).getString("Column_Name"); will(returnValue(null));
            }
        });
        
        assertThat(DAOUtils.getInteger(mockResultSet, "Column_Name"), nullValue());
    }
    
    @Test
    public void testConstructParameterValuesArray() throws Exception {
        Object[] paramValuesArray = DAOUtils.constructParameterValuesArray("1234", 304L);
        
        assertThat(paramValuesArray.length, is(2));
        
        assertThat((String)paramValuesArray[0], is("1234"));
        
        assertThat((Long)paramValuesArray[1], is(304L));
    }
    
    @Test
    public void testConstructParameterValuesArrayWhenNullParamsPassed() throws Exception {
        Object[] paramValuesArray = DAOUtils.constructParameterValuesArray((Object)null);
        assertThat(paramValuesArray.length, is(1));
        assertThat(paramValuesArray[0],  nullValue());
    }
    
    @Test
    public void testSetLongWhenPassedValueNotNull() throws Exception {
    	final PreparedStatement mockPreparedStatement = context.mock(PreparedStatement.class);
    	final int columnNumber = 10; 
        context.checking(new Expectations(){
            {
                oneOf(mockPreparedStatement).setLong(columnNumber, 400L);
            }
        });
    	
    	DAOUtils.setLong(mockPreparedStatement, columnNumber, 400L);
	}
    
    @Test
    public void testSetLongWhenPassedValueNull() throws Exception {
    	final PreparedStatement mockPreparedStatement = context.mock(PreparedStatement.class);
    	final int columnNumber = 10; 
        context.checking(new Expectations(){
            {
                oneOf(mockPreparedStatement).setNull(columnNumber, Types.NULL);
            }
        });
    	
    	DAOUtils.setLong(mockPreparedStatement, columnNumber, null);
	}
    
    @Test
    public void testSetIntWhenPassedValueNotNull() throws Exception {
    	final PreparedStatement mockPreparedStatement = context.mock(PreparedStatement.class);
    	final int columnNumber = 10; 
        context.checking(new Expectations(){
            {
                oneOf(mockPreparedStatement).setInt(columnNumber, 100);
            }
        });
    	
    	DAOUtils.setInt(mockPreparedStatement, columnNumber, 100);
	}
    
    @Test
    public void testSetIntWhenPassedValueNull() throws Exception {
    	final PreparedStatement mockPreparedStatement = context.mock(PreparedStatement.class);
    	final int columnNumber = 10; 
        context.checking(new Expectations(){
            {
                oneOf(mockPreparedStatement).setNull(columnNumber, Types.NULL);
            }
        });
    	
    	DAOUtils.setInt(mockPreparedStatement, columnNumber, null);
	}    
}
