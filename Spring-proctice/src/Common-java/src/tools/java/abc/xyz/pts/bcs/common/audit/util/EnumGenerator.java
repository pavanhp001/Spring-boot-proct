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

package abc.xyz.pts.bcs.common.audit.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EnumGenerator {

    private String jdbcURL = null;
    private String jdbcDriver = null;
    private String userID = null;
    private String userPassword = null;

    public static void main(final String[] args) {
        try {
            EnumGenerator generator = new EnumGenerator();
            generator.generate();
            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String loadTemplate(final String fileName) throws Exception {
        InputStream is = ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        return sb.toString();
    }

    private void writeFile(final String name, final String contents) throws Exception {
        System.out.println("Writing file " + name + "...");
        BufferedWriter bw = new BufferedWriter(new FileWriter(name));
        bw.write(contents);
        bw.close();
    }

    private String convertName(final String name) {
        String result = name.toUpperCase();
        return result.replace('.', '_');
    }

    private void createEventNamesJava(final List<String> names) throws Exception {
        String templateContents = loadTemplate("generator/EventTemplate.java");
        String namesText = createNamesText(names);
        templateContents = templateContents.replaceAll("<!--EVENT_NAMES-->", namesText);
        writeFile("src/main/java/abc/xyz/pts/bcs/common/audit/business/Event.java", templateContents);
    }

    private void createParamNamesJava(final List<String> names) throws Exception {
        String templateContents = loadTemplate("generator/ParameterTemplate.java");
        String namesText = createNamesText(names);
        templateContents = templateContents.replaceAll("<!--PARAMETER_NAMES-->", namesText);
        writeFile("src/main/java/abc/xyz/pts/bcs/common/audit/business/Parameter.java", templateContents);
    }

    private String createNamesText(final List<String> names) {
        StringBuilder fileBuff = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            fileBuff.append("\t" + convertName(name) + " (\"" + name + "\")");
            if (i == (names.size() - 1)) {
                fileBuff.append(";\n");
            } else {
                fileBuff.append(",\n");
            }
        }
        return fileBuff.toString();
    }

    public void generate() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver).newInstance();
            c = DriverManager.getConnection(jdbcURL, userID, userPassword);
            stmt = c.createStatement();
            String eventNamesQuery = "select name from events order by name asc";
            String paramNamesQuery = "select name from parameters order by name asc";
            ResultSet rs = stmt.executeQuery(eventNamesQuery);
            List<String> eventNames = new ArrayList<String>();
            while (rs.next()) {
                String eventName = rs.getString("name");
                eventNames.add(eventName);
            }
            createEventNamesJava(eventNames);
            rs = stmt.executeQuery(paramNamesQuery);
            List<String> paramNames = new ArrayList<String>();
            while (rs.next()) {
                String paramName = rs.getString("name");
                paramNames.add(paramName);
            }
            createParamNamesJava(paramNames);
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public EnumGenerator() throws IOException {
        Properties props = getProperties("generator/connection.properties");
    }

    public Properties getProperties(final String propFileName) throws IOException {
        Properties props = new Properties();
        props.load(this.getClass().getClassLoader().getSystemResourceAsStream(propFileName));
        System.out.println("properties loaded");
        jdbcURL = props.getProperty("jdbcURL");
        System.out.println("jdbcURL: " + jdbcURL);
        jdbcDriver = props.getProperty("jdbcDriver");
        System.out.println("jdbcDriver: " + jdbcDriver);
        userID = props.getProperty("userID");
        System.out.println("userID: " + userID);
        userPassword = props.getProperty("userPassword");
        System.out.println("userPassword: " + userPassword);

        return props;
    }
}
