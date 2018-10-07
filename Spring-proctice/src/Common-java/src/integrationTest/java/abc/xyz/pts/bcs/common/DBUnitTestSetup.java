/**
 * 
 */
package abc.xyz.pts.bcs.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.ListUtils;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sudheendra.Singh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:dbunitPersistenceContextTest.xml"})  
@Transactional(propagation=Propagation.REQUIRED)
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class DBUnitTestSetup extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired  
    private DataSource dataSource;
    
    private static final String PR_SET_SESSION_PARAMETERS = "{exec pkg_user_session.pr_set_session_parameters('SUP','SUP','SUP','SUP')}";
    
    @Before  
    @Transactional(propagation=Propagation.REQUIRED)
    public void init() throws Exception{  
        // insert data into db  
    	IDataSet ds = getDataSet();
    	//DatabaseOperation.TRANSACTION(DatabaseOperation.CLEAN_INSERT);
    	IDatabaseConnection conn = getConnection();
    	//FlatDtdDataSet.write(conn.createDataSet(), new FileOutputStream("test.dtd"));
    	
    	conn.getConnection().prepareCall(PR_SET_SESSION_PARAMETERS);
    	DatabaseOperation.REFRESH.execute(conn, ds);
    }  
  
    @After  
    public void after() throws Exception{  
        // insert data into db  
        DatabaseOperation.DELETE_ALL.execute(getConnection(), getDataSet());  
    }  

    private IDatabaseConnection getConnection() throws Exception{  
    // get connection  
        Connection con = dataSource.getConnection();  
        DatabaseMetaData  databaseMetaData = con.getMetaData();  
        // oracle schema name is the user name  
        IDatabaseConnection connection = new DatabaseConnection(con,databaseMetaData.getUserName().toUpperCase());  
        DatabaseConfig config = connection.getConfig();  
        // oracle 10g  
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Oracle10DataTypeFactory());  
        // receycle bin  
        config.setFeature(DatabaseConfig.FEATURE_SKIP_ORACLE_RECYCLEBIN_TABLES, Boolean.TRUE);
        //config.setFeature(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, true);
        //config.setFeature(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
        //config.setFeature(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
        //config.setProperty(DatabaseConfig.PROPERTY_TABLE_TYPE, new String[] {"TABLE", "SYNONYM", "ALIAS"});
        //config.setProperty(DatabaseConfig.PROPERTY_TABLE_TYPE, new String[] {"TABLE", "SYNONYM"});
        //config.setFeature(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, Boolean.TRUE);
        return connection;  
    }  
  
    /**
     * Override this in test methods to specify a list of tables that were modified, including all referenced
     * tables (even if they were not touched). DBunit <em>should</em> be able to figure out the ordering.
     *
     * @return a list of table names
     */
    @SuppressWarnings("unchecked")
    protected List<String> getModifiedTables() { 
    	return ListUtils.EMPTY_LIST; 
    }
    
    private IDataSet getDataSet() throws Exception{
    	IDataSet ds = new FlatXmlDataSetBuilder().build(new FileInputStream("src/integrationTest/resources/dbunit_data.xml"));
    	//IDataSet ds = new XmlDataSet(new FileInputStream("src/integrationTest/resources/dbunit_data.xml"));
    	//return new FlatXmlDataSet(new FileInputStream("src/integrationTest/resources/dbunit_data.xml"));
    	return ds;
    }  
  
}
