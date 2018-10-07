package abc.xyz.pts.bcs.common.audit.messages;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class DataTypeConverterTest {

	@Test
	public void parseDateTimeTest() throws Exception{
		Date d = new Date(1000);
		Date testResult = DatatypeConverter.parseDateTime("1000");
		Assert.assertEquals(d.getTime(), testResult.getTime());
	}
	
	@Test
	public void printDateTimeTest() throws Exception{
		Date d = new Date(15000);
		String result = DatatypeConverter.printDateTime(d);
		Assert.assertEquals("15000", result);
	}
	
	@Test
	public void printDateTimeNullTest() throws Exception{
		String result = DatatypeConverter.printDateTime(null);
		Assert.assertNull(result);
	}
}
