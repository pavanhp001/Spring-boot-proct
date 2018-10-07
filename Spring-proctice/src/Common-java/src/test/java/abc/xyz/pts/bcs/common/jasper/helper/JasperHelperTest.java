
package abc.xyz.pts.bcs.common.jasper.helper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.ListResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import abc.xyz.pts.bcs.common.enums.DirectionType;
import abc.xyz.pts.bcs.common.enums.MovementStatusType;
import abc.xyz.pts.bcs.common.enums.OverrideType;


/**
 * @author Deepesh.Rathore
 *
 */
public class JasperHelperTest {
	
	class MsgResourceBundle extends ListResourceBundle {

		private Object[][] contents = new Object[][] {
				
		{ "comma", "," },

		{ "inbound", "Inbound" },

		{ "outbound", "Outbound" },
		
		{ "ap.transit.transfer", "Transit" },
		
		{ "movement.status.D", "Denied" },
		
		{ "movement.status.E", "Expected" },
		
		{ "movement.status.C", "Cancelled" }
		

		};

		protected Object[][] getContents() {

			return contents;

		}

	};
	
	private MsgResourceBundle bundle;
	
	
	@Before
	public void setup() {
		bundle = new MsgResourceBundle();
	}
	
	@Test
	public void testFormatListForJasper() throws Exception {
		String returnedFormattedlist = JasperHelper.formatListForJasper(bundle, Arrays.asList(MovementStatusType.values()));
		assertThat(returnedFormattedlist, is("Denied, Expected, Cancelled"));
	}
	
	@Test
	public void testFormatListForJasper_ReturnsAnEmptyString_IfEnumsListPassedIsEmpty() throws Exception {
		String returnedFormattedlist = JasperHelper.formatListForJasper(bundle, Collections.EMPTY_LIST);
		assertThat(returnedFormattedlist, is(StringUtils.EMPTY));
	}
	
   @Test
    public void testFormatListForJasper_ReturnsAnEmptyString_IfNoKeysInDataDictionary() throws Exception {
        String returnedFormattedlist = JasperHelper.formatListForJasper(bundle,  Arrays.asList(OverrideType.values()));
        assertThat(returnedFormattedlist, is(StringUtils.EMPTY));
    }

	@Test
	public void testGetLegendFor(){
		
		String expectedLegend = createExpectedLegend(bundle);
		
		String returnedLegend = JasperHelper.getLegendFor(bundle, DirectionType.values());
		
		assertThat(returnedLegend, is(expectedLegend));
	}


	/**
	 * @param bundle 
	 * @return
	 */
	private String createExpectedLegend(final MsgResourceBundle bundle) {
		StringBuilder buf = new StringBuilder();
        
        for (DirectionType obj : DirectionType.values()) 
        {
            if (buf.length() > 0)
            {
                buf.append(bundle.getString("comma"));
                buf.append(" ");
            }

            buf.append(obj.getLegend());
            buf.append(" - ");
            buf.append(bundle.getString(obj.getDataDictionaryKey()));
        }
        return buf.toString();
	}
	
}
