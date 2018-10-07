/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.messageinjector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileList;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.ResourceCollection;
import org.apache.tools.ant.types.resources.Resources;
import org.apache.tools.ant.types.resources.Restrict;
import org.apache.tools.ant.types.resources.StringResource;
import org.apache.tools.ant.types.resources.selectors.Exists;
import org.apache.tools.ant.types.resources.selectors.Not;
import org.apache.tools.ant.types.resources.selectors.ResourceSelector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;



public class MessageInjectorTask extends Task {

    private static Logger log = Logger.getLogger(MessageInjectorTask.class);

    private File applicationContext;

    private Resources rc;
    private static final ResourceSelector EXISTS = new Exists();
    private static final ResourceSelector NOT_EXISTS = new Not(EXISTS);
    private ResourceCollection inputFiles = null;
    private File input;
    private File logConfig;
    private JmsTemplate jmsTemplate;

    public File getLogConfig() {
        return logConfig;
    }

    public void setLogConfig(final File logConfig) {
        this.logConfig = logConfig;
    }

    public File getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(final File applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setDataloadQueue(final String dataloadQueue) {
        System.setProperty("data.injection.mq.queue", dataloadQueue);
    }

    public void setDataloadQueueManager(final String dataloadQueueManager) {
        System.setProperty("data.injection.mq.qm", dataloadQueueManager);
    }

    public void setDataloadChannel(final String dataloadChannel) {
        System.setProperty("data.injection.mq.channel", dataloadChannel);
    }

    public void setDataloadHost(final String dataloadHost) {
        System.setProperty("data.injection.mq.host", dataloadHost);
    }

    public void setDataloadPort(final String dataloadPort) {
        System.setProperty("data.injection.mq.port", dataloadPort);
    }


    /**
     * Set of files to concatenate.
     * @param set the set of files
     */
    public void addFileset(final FileSet set) {
        add(set);
    }

    /**
     * List of files to concatenate.
     * @param list the list of files
     */
    public void addFilelist(final FileList list) {
        add(list);
    }

    /**
     * Add an arbitrary ResourceCollection.
     * @param c the ResourceCollection to add.
     * @since Ant 1.7
     */
    public void add(final ResourceCollection c) {
        rc = rc == null ? new Resources() : rc;
        rc.add(c);
    }

    /************************************************************************
     **  Ant execute method (main entry point)
     ************************************************************************/
    public void execute() {
        System.out.println("start MessageInjector");
        DOMConfigurator.configure(logConfig.getAbsolutePath());
        validateAttributes();
        ApplicationContext ctx = new FileSystemXmlApplicationContext("file:" + applicationContext.getAbsolutePath());
        jmsTemplate = (JmsTemplate)ctx.getBean("jmsTemplate");

        Iterator it = inputFiles.iterator();
        while (it.hasNext()) {
            File file = new File(it.next().toString());
            System.out.println(file);
//            System.out.println(extactTextMessage(file));
            log.info(file);
//            log.info(extactTextMessage(file));
            sendTextMessage(extactTextMessage(file));
        }
        log.info("Injection complete....");
    }

    public void sendTextMessage(final String message) {
        jmsTemplate.send(new MessageCreator() {

            public Message createMessage(final Session session) throws JMSException {
                log.info("Sending TextMessage");
                return session.createTextMessage(message);
            }
        });
    }


    /**
     * Ensure we have a consistent and legal set of attributes, and set
     * any internal flags necessary based on different combinations
     * of attributes.  Side effect of creating file list to work.
     * @exception BuildException if an error occurs.
     */
    protected void validateAttributes() {
        log.info("validate attributes");
        if (input == null && rc == null) {
            throw new BuildException(
                "No input file specified.  Use input attribute or nested FileSet");
        }
        if (input != null && rc != null) {
            throw new BuildException(
                "Use input attribute or nested FileSet, not both, to specify input file");
        }
        if (rc != null) {
            // If using resources, disallow inline text. This is similar to
            // using GNU 'cat' with file arguments -- stdin is simply
            // ignored.
            if (input != null) {
                throw new BuildException(
                    "No input file specified.  Use input attribute or nested FileSet");
            }
            Restrict noexistRc = new Restrict();
            noexistRc.add(NOT_EXISTS);
            noexistRc.add(rc);
            for (Iterator i = noexistRc.iterator(); i.hasNext();) {
                log(i.next() + " does not exist.", Project.MSG_ERR);
            }

            Restrict existRc = new Restrict();
            existRc.add(EXISTS);
            existRc.add(rc);

            inputFiles = existRc;
        } else {
            StringResource s = new StringResource();
            s.setProject(getProject());
            s.setValue(input.toString());
            inputFiles = s;
        }
    }

    /**
     * Clean out the internals for re-use.
     */
    protected void reset() {
        inputFiles = null;
    }

    private static String extactTextMessage(final File file) {
        StringBuffer result = new StringBuffer();

        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                result.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
