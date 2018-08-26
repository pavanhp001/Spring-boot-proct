package com.AL.ui.product;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.AL.html.Fieldset;
import com.AL.html.Input;
import com.AL.html.Label;
import com.AL.html.Legend;
import com.AL.html.ObjectFactory;
import com.AL.html.P;
import com.AL.ui.factory.HtmlFactory;
import com.AL.ui.service.V.ProductServiceUI;
import com.AL.V.gateway.util.JaxbUtil;
import com.AL.xml.pr.v4.ChoiceType;
import com.AL.xml.pr.v4.CustomizationType;
import com.AL.xml.pr.v4.ProductInfoType;

/**
 * @author ebthomas
 * 
 */
// @RunWith(value = SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml"
// })
public class Uverse3ParseTest {

	int i=0;
	
	@Before
	public void setUp() {

	}

	@Test
	public void testListCustomization() {

		ProductInfoType productInfo = ProductServiceUI.INSTANCE.getAttStiSample();

		List<Fieldset> fsList = HtmlFactory.INSTANCE.customizationToFieldSet(productInfo);
		
  
		
		JaxbUtil<Fieldset> util = new JaxbUtil<Fieldset>();

		for (Fieldset fieldset : fsList) {

			i++;
			
			String pr = util.toCleanString(fieldset, "ns2", Fieldset.class);

	 		System.out.println(pr);
			
			 
			System.out
					.println("===========================================================================");

		}
		
		System.out.println("FINAL CONVERT COUNT:"+i);

	}

	 

 
}