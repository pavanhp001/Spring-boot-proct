package abc.xyz.pts.bcs.common.audit.aspect;

import abc.xyz.pts.bcs.common.audit.annotation.AuditableBeanProperty;
import abc.xyz.pts.bcs.common.audit.business.Parameter;

public class NestedBeanTestImpl {

	private String property1 = "test nested property";
	@AuditableBeanProperty(key=Parameter.TRAVEL_TYPE)
	private String property2 = "test nested property2";

	public String getProperty1() {
		return property1;
	}

	public void setProperty1(final String property1) {
		this.property1 = property1;
	}

	public String getProperty2() {
		return property2;
	}

	public void setProperty2(final String property2) {
		this.property2 = property2;
	}
	
}
