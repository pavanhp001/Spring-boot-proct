package com.A.rules.groovy.directrules;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.runtime.InvokerHelper;

 

/**
 * Rule that is created directly from a string, not from a script.
 * 
 * @author Eban Thomas
 */
public class DirectRuleImpl extends RuleAbstract {
	private static final long serialVersionUID = 14545654L;

    private final static String DESCRIPTION = "Direct Rule Impl";
    
    private final String ruleName;
    
    // TODO: Merge properties with file impl, move to superclass.
    private Map properties = new HashMap(); 

    /**
     * Static helper to create a Groovy class for the rule
     */
    private final static Class<?> createRulesClass(String rulesString) {
    	
    
        Class<?> returnValue = null;
        GroovyClassLoader groovyLoader = new GroovyClassLoader();
        try {
            returnValue = groovyLoader.parseClass(rulesString);
        } catch (CompilationFailedException exception) {
            throw new RuntimeException("Compliation Failed for " + rulesString, exception);
        }

        return returnValue;
    }
    
         private final Binding createBinding(List<Object> input){
        Binding returnValue  = new Binding();
        // Set the default element
        returnValue.setVariable("data", input);
        //  Now we have to loop through the properties (which should be Integers as keys and Strings as values)
        // grabbing the appropriate element from the input list
        Iterator inputIterator = properties.keySet().iterator();
        while (inputIterator.hasNext()){
            Integer key = (Integer) inputIterator.next();
            returnValue.setVariable((String)properties.get(key), input.get(key.intValue()));
        }
        
        return returnValue;
    }
 
         private Class<?> clazz;
    /**
     * Constructor, this creates the rule
     */
    public DirectRuleImpl(String ruleName, String rule){
        clazz = createRulesClass(rule); 
        this.ruleName = ruleName;
    }
    
    /**
     * Execute the rules against the inputs
     * @param inputs
     * @return the returned data from the rules
     */
    public void execute(RuleData context, Class<?> rulesClass) {
         
    	
    	List returnValue = null;
        Binding binding = createBinding(context.getObjects());
         
        // Now we have to execute the class we have created
        Script scriptObject = InvokerHelper.createScript(rulesClass, binding);
        Object result = scriptObject.run();
        
    }

    /**
     * @see javax.rules.admin.Rule#getName()
     */
    public String getName() {
        return this.ruleName;
    }

    /**
     * @see javax.rules.admin.Rule#getDescription()
     */
    public String getDescription() {
        return DESCRIPTION;
    }

    /**
     * @see javax.rules.admin.Rule#getProperty(java.lang.Object)
     */
    public Object getProperty(Object arg0) {
        return this.properties.get(arg0);
    }

    /**
     * @see javax.rules.admin.Rule#setProperty(java.lang.Object, java.lang.Object)
     */
    public void setProperty(Object arg0, Object arg1) {
        this.properties.put(arg0,arg1);

    }

 
    public void execute() {
    	
    	RuleData context = null;
    	
    	execute(context, this.clazz);
    	
    }
    
    
    
	
	
 
	@SuppressWarnings("unchecked")
	public void execute(Map<String, Object> map) {
 
		RuleData data = new RuleData();
		  
		data.add(map);
	 
		
    	execute(data, this.clazz);
		
	}

	@Override
	public void execute(RuleData context) {
 
    	
    	execute(context, this.clazz);
		
	}

	@Override
	public void setProperties(Map properties) {
		// TODO Auto-generated method stub
		
	}
 

    

}
