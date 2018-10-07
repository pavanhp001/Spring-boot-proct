/**
 * 
 */
package abc.xyz.pts.bcs.common.dao.utils.support.query;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.InsertBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

/**
 * @author gerard.mchale
 *
 */
public class InsertBuilderTest {

	/**
	 * Test class {@link abc.xyz.pts.bcs.common.dao.support.query.InsertBuilder()}.
	 */
	@Test
	public final void testInsertBuilder() {

		String firstName = "BART";
		String lastName = "SIMPSON";
		String docType = "P";
		String docNumber = "USA10000014";
		Calendar validUntilDate = Calendar.getInstance();
		
		InsertBuilder builder = new InsertBuilder(new TableActionCommand(), "TARWL_ID_SEQ");
		
		builder.addTable(Table.TARGET_WATCH_LISTS);
		
		builder.addInsert(Field.FORENAME, firstName);
		builder.addInsert(Field.LAST_NAME, lastName);
		builder.addInsert(Field.DOC_TYPE, docType);
		builder.addInsert(Field.DOC_NO, docNumber);
		builder.addInsert(Field.VALID_UNTIL_DATE, validUntilDate);
		
		Object[] testCriteriaValues = new Object[5];
		testCriteriaValues[0] = firstName;
		testCriteriaValues[1] = lastName;
		testCriteriaValues[2] = docType;
		testCriteriaValues[3] = docNumber;
		testCriteriaValues[4] = validUntilDate;
		
		String sql = builder.getSql();
		
		assertTrue(sql.contains("INSERT INTO " + Table.TARGET_WATCH_LISTS));
		assertTrue(sql.contains("(ID,FORENAME,LAST_NAME,DOC_TYPE,DOC_NO,VALID_UNTIL_DATE )"));
		assertTrue(sql.contains("VALUES (TARWL_ID_SEQ.nextval,?,?,?,?,?)"));
		assertArrayEquals(testCriteriaValues, builder.getCriteriaValues());
	}
}
