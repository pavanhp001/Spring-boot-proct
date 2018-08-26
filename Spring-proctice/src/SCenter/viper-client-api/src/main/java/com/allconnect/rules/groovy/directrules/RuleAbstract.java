package com.A.rules.groovy.directrules;

 

import java.util.Map;

import javax.rules.admin.Rule;

 
public abstract class RuleAbstract implements Rule{
    
    /**
     * Execute the rule against the data
     * @param data The data to process with the rule
     * @return the returned data from the rules
     */
    public abstract void execute(RuleData data);
    
    /**
     * Set the properties for the rule in bulk
     */
    public abstract void setProperties(Map properties);
    
}
