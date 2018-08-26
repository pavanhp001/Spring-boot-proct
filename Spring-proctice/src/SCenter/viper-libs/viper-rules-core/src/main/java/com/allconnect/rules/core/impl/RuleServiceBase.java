package com.A.rules.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import com.A.rules.core.RuleSessionFactory;
import com.A.validation.Message;
import com.A.validation.OrchestrationContext;
import com.A.validation.ReportFactory;
import com.A.validation.ValidationEnum;
import com.A.validation.ValidationReport;
import com.A.validation.impl.DefaultReportFactory;
import com.A.validation.impl.DefaultValidationReport;

/**
 * @author ebthomas
 * 
 */
public abstract class RuleServiceBase {

	Logger logger = Logger.getLogger(RuleServiceBase.class);

	/**
     * 
     */
	public RuleServiceBase() {
		super();

	}

	/**
	 * @param parameters
	 *            task request parameters
	 * @param response
	 *            response result
	 */
	public abstract void preserveRequestParameters(
			final OrchestrationContext context);

	public Boolean validInit(RuleSessionFactory ruleSessionFactory,
			OrchestrationContext response) {

		if (ruleSessionFactory == null) {
			logger.fatal("rules engine not initialized.");
			response.getValidationReport().addMessage(Message.Type.FATAL, 1L,
					"uninitialized fact or and rule factory");
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	/**
	 * @param session
	 *            current session
	 * @param newFacts
	 *            List of new facts
	 */
	private void addToSession(final StatefulKnowledgeSession session,
			final Set<Object> newFacts) {

		if (newFacts == null) {
			return;
		}

		for (Object fact : newFacts) {
			session.insert(fact);
		}
	}

	/**
	 * @param ruleSessionFactory
	 *            current rule factory
	 * @param newFacts
	 *            new facts
	 * @return Validation Report
	 */
	public ValidationReport executeRules(
			final RuleSessionFactory ruleSessionFactory,
			final Set<Object> newFacts) {

		logger.info("creating session");
		final StatefulKnowledgeSession session = createSession(ruleSessionFactory);

		if (newFacts != null) {
			logger.info("adding facts to session fact count:" + newFacts.size());
		}
		addToSession(session, newFacts);

		logger.info("execute stateful knowledge session");
		return executeRules(session);
	}

	/**
	 * @param ruleSessionFactory
	 *            current rule factory
	 * @return Validation Report containing Rule Engine results
	 */
	public ValidationReport executeRules(
			final RuleSessionFactory ruleSessionFactory) {
		final StatefulKnowledgeSession session = createSession(ruleSessionFactory);

		return executeRules(session);
	}

	/**
	 * @param session
	 *            current session to execute rules agains
	 * @return Validation Report that contains rule engine results
	 */
	private ValidationReport executeRules(final StatefulKnowledgeSession session) {
		ValidationReport validationReport = null;
		try {

			session.fireAllRules();

			validationReport = (ValidationReport) session
					.getGlobal(ValidationEnum.validationReport.name());
		} finally {
			session.dispose();
		}

		if (validationReport == null) {
			validationReport = new DefaultValidationReport();
		}

		return validationReport;
	}

	/**
	 * @param session
	 *            current session
	 * @param facts
	 *            list of facts
	 * @return Validation Report
	 */
	public ValidationReport fireAllRules(
			final StatelessKnowledgeSession session, final List<Object> facts) {
		List<Command<?>> cmds = new ArrayList<Command<?>>();

		for (Object obj : facts) {
			cmds.add(CommandFactory.newInsert(obj));
		}

		cmds.add(CommandFactory.newSetGlobal(
				ValidationEnum.reportFactory.name(), new DefaultReportFactory()));
		cmds.add(CommandFactory.newSetGlobal(
				ValidationEnum.validationReport.name(),
				new DefaultValidationReport()));

		ExecutionResults results = session.execute(CommandFactory
				.newBatchExecution(cmds));

		ValidationReport validationReport = (ValidationReport) results
				.getValue(ValidationEnum.validationReport.name());

		if (validationReport == null) {
			validationReport = new DefaultValidationReport();
		}

		return validationReport;
	}

	/**
	 * @param ruleSessionFactory
	 *            Rule Factory that will be used to create the session
	 * @return Newly created session
	 */
	public StatefulKnowledgeSession createSession(
			final RuleSessionFactory ruleSessionFactory) {

		final StatefulKnowledgeSession session = ruleSessionFactory
				.getKnowledgeBase().newStatefulKnowledgeSession();

		ReportFactory reportFactory = new DefaultReportFactory();
		ValidationReport validationReport = new DefaultValidationReport();

		session.setGlobal(ValidationEnum.reportFactory.name(), reportFactory);
		session.setGlobal(ValidationEnum.validationReport.name(),
				validationReport);

		return session;
	}

}
