package com.AL.ome.ie.arbiter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import junit.framework.TestCase;
import com.AL.vm.arbiter.converter.ArbiterMarshaller;
import org.apache.xmlbeans.XmlOptions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.ie.service.strategy.ArbiterFlowManagerDefault;
import com.AL.ie.service.strategy.MotherObjectArbiter;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.xml.v4.TransientResponseContainerType;
import com.AL.xml.v4.rtimRequestResponse.RtimRequestResponseDocument;

/**
 * @author ebthomas
 * 
 */
// @RunWith(value = SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {
// "classpath:**/applicationContextTest.xml",
// "classpath:**/arbiterContext.xml" })
// @Configurable
public class RtimDocumentParsingTest extends TestCase // implements
// ApplicationContextAware {

{
	private AtomicInteger counter = new AtomicInteger(10);

	@Autowired
	ArbiterFlowManagerDefault arbiter;

	MarshallOrder<SalesOrder> marshallOrder;

	@Autowired
	private ApplicationContext applicationContext;

	@Before
	public void setUp() {

	}

	@Test
	public void testArbiter() throws Exception {

		// Set up the validation error listener.
		ArrayList validationErrors = new ArrayList();
		XmlOptions validationOptions = new XmlOptions();
		validationOptions.setErrorListener(validationErrors);

		String path = MotherObjectArbiter.path;

		String arbiterResponse = getXMLFromFile(path
				+ "src//main//resources//xml//arbiter//arbiter-1-response.xml");

		RtimRequestResponseDocument t = RtimRequestResponseDocument.Factory
				.parse(arbiterResponse);

		 

		TransientResponseContainerType container = ArbiterMarshaller.INSTANCE.createResponseTransient(t);

		System.out.println("container--> " + container.toString());

	}

	

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * @param fileName
	 *            name to get FileName
	 * @return String contents
	 */
	public static String getXMLFromFile(final String fileName) {
		File file = new File(fileName);
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;

			// repeat until all lines is read
			while ((text = reader.readLine()) != null) {
				contents.append(text).append(
						System.getProperty("line.separator"));
			}

			return contents.toString();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
