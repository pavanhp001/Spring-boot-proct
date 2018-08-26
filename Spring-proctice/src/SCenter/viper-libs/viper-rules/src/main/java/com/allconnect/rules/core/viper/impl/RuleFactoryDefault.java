package com.A.rules.core.V.impl;


import java.util.Properties;

import org.drools.KnowledgeBase;

import com.A.rules.core.RuleSessionFactoryBase;

/**
 * @author ebthomas
 * 
 */
public final class RuleFactoryDefault extends RuleSessionFactoryBase {



	static {
		// load();
	}
	
	public RuleFactoryDefault(final KnowledgeBase knowledgeBase) {
		this.setKnowledgeBase(knowledgeBase);
		initChangeAgent();
	}

	/**
	 * Manage loading Drools Rule Factory.
	 */
	public RuleFactoryDefault(final String[] listOfConfig) {
		super();

		if (listOfConfig == null) {
			return;
		}

		String[] ruleFiles = listOfConfig; // new String[] { listOfConfig };

		try {
			setup(ruleFiles, RuleSessionFactoryBase.DEFAULT_FLOW_FILES);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	 
	public String getAgentName() {
		return "rulesAgent";
	}

	public static Properties loadPropertiesFile(String filename) {
		Properties metadata = new Properties();

		ClassLoader clazzLoader = Thread.currentThread()
				.getContextClassLoader();

		boolean isLoaded = loadPropertiesFile(metadata, clazzLoader, filename);

		if (!isLoaded) {
			isLoaded = loadPropertiesFile(metadata, clazzLoader.getParent(),
					filename);

			if (!isLoaded) {
				throw new IllegalArgumentException(
						"unable to load properties file from classloader and parent classloader:"
								+ filename);
			}
		}

		return metadata;
	}

	public static boolean loadPropertiesFile(Properties metadata,
			ClassLoader clazzLoader, String filename) {
		try {

			metadata.load(clazzLoader.getResource(filename).openStream());

			return Boolean.TRUE;
		} catch (Exception e1) {
			e1.printStackTrace();

		}

		return Boolean.FALSE;
	}
}
