package abc.xyz.pts.bcs.common.audit.aspect;

import abc.xyz.pts.bcs.common.audit.annotation.AuditableBeanProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableCommand;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableCompositeBeanProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableExpressionBeanProperties;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableExpressionBeanProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableProperty;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.business.Parameter;

@AuditableCommand(Event.ADD_ACTION_CODE)
public class AuditTestCommand {

    @AuditableProperty(key=Parameter.ACTION_CODE)
    private String property1;
    @AuditableBeanProperty(key=Parameter.APP_ACTION_CODE, ignoreEvent={Event.ADD_ACTION_CODE})
    private String property2;
    @AuditableBeanProperty(key=Parameter.ARR_AIRPORT_CODE)
    private String property3;

    private String property4;
    private String property5 = null;
    @AuditableExpressionBeanProperties(properties={
    		@AuditableExpressionBeanProperty(key=Parameter.AUDIT_EVENT_TYPE, expression="nestedProperty1.property1")
    })
    private NestedBeanTestImpl nestedProperty1;
    @AuditableCompositeBeanProperty
    private NestedBeanTestImpl nestedProperty2;
    @AuditableBeanProperty(key=Parameter.APPLICATION_NAME)
    private String[] arrayField1 = {"value1", "value2", "value3"};

    public String[] getArrayField1() {
		return (String[])arrayField1.clone();
	}
	public void setArrayField1(final String[] arrayField1) {
		this.arrayField1 = (String[])arrayField1.clone();
	}
	public NestedBeanTestImpl getNestedProperty1() {
		return nestedProperty1;
	}
	public void setNestedProperty1(final NestedBeanTestImpl nestedProperty1) {
		this.nestedProperty1 = nestedProperty1;
	}
	/**
     * @return the property1
     */
    public String getProperty1() {
        return property1;
    }
    /**
     * @param property1 the property1 to set
     */
    public void setProperty1(final String property1) {
        this.property1 = property1;
    }
    /**
     * @return the property2
     */
    public String getProperty2() {
        return property2;
    }
    /**
     * @param property2 the property2 to set
     */
    public void setProperty2(final String property2) {
        this.property2 = property2;
    }
    /**
     * @return the property3
     */
    public String getProperty3() {
        return property3;
    }
    /**
     * @param property3 the property3 to set
     */
    public void setProperty3(final String property3) {
        this.property3 = property3;
    }
	public NestedBeanTestImpl getNestedProperty2() {
		return nestedProperty2;
	}
	public void setNestedProperty2(final NestedBeanTestImpl nestedProperty2) {
		this.nestedProperty2 = nestedProperty2;
	}
	public String getProperty4() {
		return property4;
	}
	public void setProperty4(final String property4) {
		this.property4 = property4;
	}
	public String getProperty5() {
		return property5;
	}
	public void setProperty5(final String property5) {
		this.property5 = property5;
	}
}
