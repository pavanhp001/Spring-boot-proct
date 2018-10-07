package abc.xyz.pts.bcs.common.audit.aspect.propertyResolver;

import junit.framework.Assert;

import org.junit.Test;

import abc.xyz.pts.bcs.common.audit.aspect.AuditTestCommand;
import abc.xyz.pts.bcs.common.audit.aspect.NestedBeanTestImpl;

public class ExpressionResolverTest {

	@Test
	public void testResolve() throws Exception{
		AuditTestCommand obj = new AuditTestCommand();
		NestedBeanTestImpl obj2 = new NestedBeanTestImpl();
		obj.setNestedProperty1(obj2);
		Object result = ExpressionResolver.resolve(obj, "nestedProperty1.property1");
		Assert.assertTrue(result != null && result instanceof String && result.equals(obj2.getProperty1()));
	}
	
	@Test
	public void testCallPropertyGetter() throws Exception{
		NestedBeanTestImpl obj2 = new NestedBeanTestImpl();
		Object result = ExpressionResolver.callPropertyGetter("property1", obj2);
		Assert.assertTrue(result != null && result instanceof String && result.equals(obj2.getProperty1()));
	}
}
