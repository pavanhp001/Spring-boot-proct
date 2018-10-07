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
package abc.xyz.pts.bcs.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * A utility class representing an Oracle server used for simple unit tests.
 * A single connection is created for use in tests.
 *
 * @version $Id: SQLTestServer.java 31 2008-04-22 08:17:18Z geoff $ $HeadURL: http://casource:7777/svn/common/tags/MovedToBCS/CommonPersistence/src/test/java/abc/xyz/pts/bcs/common/SQLTestServer.java $
 * @author geoff
 */
public class SQLTestServer {
    private String url = null;
    private String user = null;
    private String password = null;
    private Connection connection = null;

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

    private static final Logger LOG = Logger.getLogger(SQLTestServer.class);

    public SQLTestServer(
            final String pURL,
            final String pUser,
            final String pPassword) throws SQLException, ClassNotFoundException {
        this.url = pURL;
        this.user = pUser;
        this.password = pPassword;

        connection = createConnection();
    }

    /**
     * Run a particular SQL script against this Oracle server.
     *
     * @param file
     */
    public final void executeScript(final File file)
            throws IOException, SQLException {
        if (file.getName().contains(".sql")) {
            executeSQLScript(file);
        } else {
            executePLSQLScript(file);
        }
    }

    /**
     * Run a particular SQL script against this Oracle server.
     *
     * @param file an Oracle SQL script to be run against this server
     * @throws IOException
     * @throws SQLException
     */
    private void executeSQLScript(final File file)
            throws IOException, SQLException {
        assert file.length() < Integer.MAX_VALUE;

        LOG.debug("Reading from file " + file.getAbsolutePath());

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuffer sqlBuf = new StringBuffer();
        String line;

        boolean statementReady = false;
        int count = 0;
        boolean inComment = false;
        boolean inPLSQL = false;

        while ((line = reader.readLine()) != null) {
            // Cleanup line
            line = line.trim();

            if (line.indexOf("--") > 0) {
                line = line.substring(0, line.indexOf("--") - 1);
            }

            if (line.startsWith("/*")) {
                inComment = true;
            }
            if (inPLSQL) {
                sqlBuf.append(line + '\n');
            } else if (inComment) {
                if (line.indexOf("*/") > -1) {
                    inComment = false;
                }
                // ignore
            } else if (line.equals("/")) {
                // Execute finished statement
                sqlBuf.append(' ');
                statementReady = true;
            } else if (line.startsWith("@")) {
                // Recursively process another file
                String newFileName = line.substring(line.lastIndexOf('@') + 1);
                if (newFileName.indexOf(".") == -1) {
                    newFileName = newFileName + ".sql";
                }
                File newFile = new File(file.getParent(), newFileName);
                executeScript(newFile);
            } else if (line.startsWith("--")
                  || line.length() == 0
                  || line.toUpperCase().startsWith("PROMPT")
                  || line.toUpperCase().startsWith("SET SERVEROUT ON")
                  || line.toUpperCase().startsWith("SPOOL")
                  || line.toUpperCase().startsWith("EXIT")) {
                // Ignore
                continue;
            } else if (line.startsWith("BEGIN")) {
                // handle procedure block start
                sqlBuf.append(line + '\n');
                inPLSQL = true;
            } else if (line.startsWith("END")) {
                // handle procedure block
                sqlBuf.append(' ');
                sqlBuf.append(line + '\n');
                inPLSQL = false;
                statementReady = true;
            } else if (line.endsWith(";")) {
                // End of statement
                sqlBuf.append(' ');
                sqlBuf.append(line.substring(0, line.length() - 1));
                statementReady = true;
            } else {
                sqlBuf.append(' ');
                sqlBuf.append(line);
                statementReady = false;
            }

            if (statementReady) {
                if (sqlBuf.length() == 0) {
                    continue;
                }
                executeQuery(sqlBuf.toString());

                count++;
                sqlBuf.setLength(0);
            }
        }

        LOG.debug("" + count + " statements processed");
        LOG.debug("Import done sucessfully");
    }

    /**
     * Run a particular PL/SQL script against this Oracle server.
     *
     * @param file an Oracle SQL script to be run against this server
     * @throws IOException
     * @throws SQLException
     */
    private void executePLSQLScript(final File file)
            throws IOException, SQLException {
        assert file.length() < Integer.MAX_VALUE;

        LOG.debug("Reading from file " + file.getAbsolutePath());

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuffer sqlBuf = new StringBuffer();
        String line;

        while ((line = reader.readLine()) != null) {
            // Cleanup line

            if (!line.trim().equals("/")) {
                sqlBuf.append(line);
                sqlBuf.append('\n');
            }
        }

        executeQuery(sqlBuf.toString());

        LOG.debug("Import of PL/SQL sucessfull");
    }


    /**
     * Get a Callable statement from the internal Server conection.
     * @param statement
     * @return a Callable statement
     * @throws SQLException
     */
    public final CallableStatement getCallableStatement(final String statement)
            throws SQLException {
        return connection.prepareCall(statement);
    }

    private Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        Connection newConnection =
            DriverManager.getConnection(url, user, password);
        return newConnection;
    }


    private void executeQuery(final String sql)
            throws SQLException {
        LOG.debug("Executing SQL:" + sql);
        Statement statement = connection.createStatement();

        statement.execute(sql);
        statement.close();
    }
}