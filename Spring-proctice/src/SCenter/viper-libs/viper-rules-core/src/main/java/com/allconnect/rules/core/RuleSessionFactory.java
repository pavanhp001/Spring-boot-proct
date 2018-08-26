/**
 *
 */
package com.A.rules.core;

import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.builder.KnowledgeBuilder;

/**
 * @author ebthomas
 * 
 */
public interface RuleSessionFactory
{
	

    /**
     * @param ruleFiles rule files
     * @param flowFiles flow files
     */
    void setup( final String[] ruleFiles, final String[] flowFiles );

    /**
     * @param kbuilder knowledge builder
     * @param flowFiles flow files
     */
    void loadFlowFiles( final KnowledgeBuilder kbuilder, final String[] flowFiles );

    /**
     * @param kbuilder knowledge builder
     * @param ruleFiles rule files
     */
    void loadRuleFiles( final KnowledgeBuilder kbuilder, final String[] ruleFiles );

    /**
     *  initialize change agent.
     */
    void initChangeAgent();

    /**
     * @return getter for knowledge agent
     */
    KnowledgeAgent getKnowledgeAgent();

    /**
     * @param agent setter for knowledge agent
     */
    void setAgent( final KnowledgeAgent agent );

    /**
     * @return getter for knowledge agent
     */
    KnowledgeBase getKnowledgeBase();

    /**
     * @param omeKnowledgeBase setter for knowledge agent
     */
    void setKnowledgeBase( final KnowledgeBase knowledgeBase );

    /**
     * @return getter for change agent
     */
    KnowledgeAgent getOmeChangeAgent();

    /**
     * @param omeChangeAgent setter for change agent
     */
    void setChangeAgent( KnowledgeAgent changeAgent );

}
