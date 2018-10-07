package abc.xyz.pts.bcs.common.audit.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class PropertyFormatterTest {

	@Test
	public void testFormat() throws Exception{
		String s = "test";
		String result = PropertyFormatter.format(s);
		Assert.assertTrue(result.equals(s));
		String testDate = "01-02-2008 11:52";
		Date d1 = new SimpleDateFormat(PropertyFormatter.DATE_FORMAT).parse(testDate);
		result = PropertyFormatter.format(d1);
		Assert.assertTrue(result.equals(testDate));
		testDate = "02-03-2008";
		Date d2 = new SimpleDateFormat(PropertyFormatter.SIMPLE_DATE_FORMAT).parse(testDate);
		result = PropertyFormatter.format(d2);
		Assert.assertTrue(result.equals(testDate));
		testDate = "01-02-2008 11:52";
		Date d3 = new SimpleDateFormat(PropertyFormatter.DATE_FORMAT).parse(testDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d3);
		result = PropertyFormatter.format(cal);
		Assert.assertTrue(result.equals(testDate));
		testDate = "01-02-2008 00:52";
		Date d4 = new SimpleDateFormat(PropertyFormatter.DATE_FORMAT).parse(testDate);
		cal = Calendar.getInstance();
		cal.setTime(d4);
		result = PropertyFormatter.format(cal);
		Assert.assertTrue(result.equals(testDate));
	}
	
	@Test
	public void testFormatNull() throws Exception{
		String result = PropertyFormatter.format(null);
		Assert.assertTrue(result.equals(""));
	}
}
