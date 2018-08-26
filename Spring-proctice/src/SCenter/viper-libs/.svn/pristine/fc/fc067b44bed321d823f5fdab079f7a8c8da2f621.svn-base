/**
 *
 */
package com.A.rules.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.DecisionTableConfiguration;
import org.drools.builder.DecisionTableInputType;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.DecisionTableFactory;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;
import org.apache.log4j.Logger;

import com.A.rules.core.impl.RuleServiceBase;

/**
 * @author ebthomas
 * 
 */
public abstract class RuleSessionFactoryBase implements RuleSessionFactory,
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7401324050964698332L;
	
	private static final Logger logger = Logger.getLogger(RuleSessionFactoryBase.class);

	private KnowledgeBase knowledgeBase;
	private KnowledgeAgent changeAgent;
	private final Lock initializationLock = new ReentrantLock();
	public static final String[] DEFAULT_FLOW_FILES = {};
	private static final String XLS_RESOURCE_TYPE = ".xls";

	/**
	 * @return Properties
	 */
	private Properties getProperties() {
		Properties props = new Properties();
		// props.setProperty( "drools.dialect.java.compiler", "JANINO" );

		return props;
	}

	/**
	 * @return agent name
	 */
	public abstract String getAgentName();

	/**
	 * @param ruleFiles
	 *            list of rule files
	 * @param flowFiles
	 *            list of flow files
	 */
	public void setup(final String[] ruleFiles, final String[] flowFiles) {
		if (knowledgeBase == null) {
			KnowledgeBuilderConfiguration config = null;

			RulesLogger.INSTANCE.logger.info("establish the setup lock");
			initializationLock.lock();
			try { // Critical Section

				if (getKnowledgeAgent() == null) {
					if ((ruleFiles == null) || (ruleFiles.length == 0)) {
						throw new IllegalArgumentException(
								"at least one rule file is required");
					}

					RulesLogger.INSTANCE.logger
							.info("create knowledge builder");
					config = KnowledgeBuilderFactory
							.newKnowledgeBuilderConfiguration(getProperties(),
									null);
					KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
							.newKnowledgeBuilder(config);

					RulesLogger.INSTANCE.logger
							.info("load rule and flow files");
					loadRuleFiles(kbuilder, ruleFiles);
					loadFlowFiles(kbuilder, flowFiles);

					try {
						RulesLogger.INSTANCE.logger.info("handle build errors");
						handleBuilderErrors(kbuilder);
					} catch (Exception e) {
						e.printStackTrace();
					}

					RulesLogger.INSTANCE.logger
							.info("initialize knowledge base");
					setKnowledgeBase(KnowledgeBaseFactory.newKnowledgeBase());
					getKnowledgeBase().addKnowledgePackages(
							kbuilder.getKnowledgePackages());

					RulesLogger.INSTANCE.logger.info("initialize change agent");
					initChangeAgent();
				}
			} finally {

				RulesLogger.INSTANCE.logger.info("releasing the setup lock");
				initializationLock.unlock();
			}

		}
	}

	/**
	 * @param kbuilder
	 *            builder that is adding the flow files
	 * @param flowFiles
	 *            flow files to be added to the knowledge base configuration
	 */
	public void setupFlowFiles(final KnowledgeBuilder kbuilder,
			final String[] flowFiles) {

		for (String flowFilename : flowFiles) {
			kbuilder.add(ResourceFactory.newClassPathResource(flowFilename,
					RuleServiceBase.class), ResourceType.DRF);
		}

	}

	public void loadFlowFiles(final KnowledgeBuilder kbuilder,
			final String[] flowFiles) {
		if (flowFiles == null) {
			setupFlowFiles(kbuilder, DEFAULT_FLOW_FILES);
		} else {
			setupFlowFiles(kbuilder, flowFiles);
		}
	}

	/**
	 * @param kbuilder
	 *            knowledge builder that is adding the rule files
	 * @param ruleFiles
	 *            files to be added to the knowledge base configuration
	 */
	public void loadRuleFiles(final KnowledgeBuilder kbuilder,
			final String[] ruleFiles) {
		for (String ruleFilename : ruleFiles) {

			if (ruleFilename != null) {
				if ((ruleFilename.toLowerCase().endsWith(XLS_RESOURCE_TYPE))) {
					loadDecisionTable(kbuilder, ruleFilename);
				} else {
					loadDRLFile(kbuilder, ruleFilename);
				}
			}
		}
	}

	public void loadDRLFile(final KnowledgeBuilder kbuilder,
			final String ruleFilename) {
		
		//ResourceFactory.newFileResource(ruleFilename);
		kbuilder.add(ResourceFactory.newClassPathResource(ruleFilename,
				RuleServiceBase.class), ResourceType.DRL);
		
		String path = Thread.currentThread().getContextClassLoader().getResource(ruleFilename).getPath();
		
		String content =  getStringContent(path);
		
		 
	}
	
	public void loadDecisionTable(final KnowledgeBuilder kbuilder,
			final String xlsFile) {
		try {
			DecisionTableConfiguration dtConfig = KnowledgeBuilderFactory
					.newDecisionTableConfiguration();
			dtConfig.setInputType(DecisionTableInputType.XLS);
			dtConfig.setWorksheetName("Tables");
			
			
			//ResourceFactory.newFileResource(xlsFile);
			
			kbuilder.add(ResourceFactory.newClassPathResource(xlsFile),
					ResourceType.DTABLE, dtConfig);

			viewDecisionTable(dtConfig, xlsFile);
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
				logger.warn(e.getMessage());
			}
		}
	}
 
    public static String getStringContent(final String fileName) {
        File file = new File(fileName);
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(System.getProperty("line.separator"));
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

	

	public void viewDecisionTable(final DecisionTableConfiguration dtConfig,
			final String xlsFile) {
		try {
			String drlString = DecisionTableFactory.loadFromInputStream(
					ResourceFactory.newClassPathResource(xlsFile)
							.getInputStream(), dtConfig);
			logger.debug(drlString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param build
	 *            KnowledgeBuilder that is attempting to be created.
	 * @return String containing errors that occurred during the KnowledgeBase
	 *         configuration
	 */
	public String handleBuilderErrors(final KnowledgeBuilder build) {
		StringBuilder sb = new StringBuilder();

		if (build.hasErrors()) {
			KnowledgeBuilderErrors knowledgeBuilderErrors = build.getErrors();

			for (KnowledgeBuilderError knowledgeBuilderError : knowledgeBuilderErrors) {

				int[] line = knowledgeBuilderError.getErrorLines();

				if (line.length > 0) {
					sb.append("Error at:" + line[0]);
				}
				if (line.length > 1) {
					sb.append(":" + line[1]);
				}

				sb.append("\n").append(knowledgeBuilderError.getMessage())
						.append("\n");
			}
		}

		logger.info(sb.toString());
		return sb.toString();
	}

	/**
	 * Initialize the Ome Change agent used to monitor system resources for
	 * changes. It will automatically update the KnowledgeBase using a polling
	 * mechanism to identify changes.
	 */
	public void initChangeAgent() {
		if (changeAgent == null) {
			initResourceMonitor();
			initKnowledgeAgent();
		}
	}

	public void initKnowledgeAgent() {

		KnowledgeAgentConfiguration conf = KnowledgeAgentFactory
				.newKnowledgeAgentConfiguration();
		conf.setProperty("drools.agent.scanDirectories", "true"); // reload all
																	// if any
																	// files in
																	// the
																	// directory
																	// changes
		conf.setProperty("drools.resource.scanner.interval", "120"); // 900 =
																		// 60*15
																		// =
																		// Every
																		// 15
																		// Minutes
		conf.setProperty("drools.agent.newInstance", "false");

		changeAgent = KnowledgeAgentFactory.newKnowledgeAgent(getAgentName(),
				knowledgeBase, conf);

	}

	public void initResourceMonitor() {
		ResourceChangeScannerConfiguration sconf = ResourceFactory
				.getResourceChangeScannerService()
				.newResourceChangeScannerConfiguration();
		sconf.setProperty("drools.resource.scanner.interval", "120");
		ResourceFactory.getResourceChangeScannerService().configure(sconf);
		ResourceFactory.getResourceChangeScannerService().start();
		ResourceFactory.getResourceChangeNotifierService().start();
	}

	/**
	 * @return getter for the Ome Knowledge Agent
	 */
	public KnowledgeAgent getKnowledgeAgent() {
		return changeAgent;
	}

	/**
	 * @param agent
	 *            setter for the Ome Knowledge Agent
	 */
	public void setAgent(final KnowledgeAgent agent) {
		this.changeAgent = agent;
	}

	/**
	 * @return getter for the Ome KnowledgeBase
	 */
	public KnowledgeBase getKnowledgeBase() {
		return knowledgeBase;
	}

	/**
	 * @param omeKnowledgeBase
	 *            setter for the Ome KnowledgeBase
	 */
	public void setKnowledgeBase(final KnowledgeBase omeKnowledgeBase) {
		this.knowledgeBase = omeKnowledgeBase;
	}

	public KnowledgeAgent getOmeChangeAgent() {
		return changeAgent;
	}

	public void setChangeAgent(final KnowledgeAgent omeChangeAgent) {
		this.changeAgent = omeChangeAgent;
	}

	public KnowledgeAgent getChangeAgent() {
		return changeAgent;
	}

	public Lock getLock() {
		return initializationLock;
	}

}
