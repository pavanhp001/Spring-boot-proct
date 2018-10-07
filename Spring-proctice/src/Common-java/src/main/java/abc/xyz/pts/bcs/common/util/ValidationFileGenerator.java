/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ValidationFileGenerator {

    public static void main(final String[] args) {

        String regexFileName = args[0];
        String sourceFileName = args[1];
        String validationFileName = args[2];

        assert regexFileName != null : "The Validation src file name is not valid.";
        assert sourceFileName != null : "The Validation regex file name is not valid.";
        File regexFile = new File(regexFileName);

        try {
            String sourceFileContent = getSourceContents(sourceFileName);

            ResourceBundle bundle = new PropertyResourceBundle(new FileInputStream(regexFile));

            Enumeration<String> keys = bundle.getKeys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                sourceFileContent = sourceFileContent.replace("${" + key + "}", bundle.getString(key));
            }

            writeValidationFile(validationFileName, sourceFileContent);

            System.out.println(validationFileName + " generated.");

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    private static String getSourceContents(final String sourceFileName) throws IOException {

        BufferedReader reader = null;

        try {
            StringBuffer sourceFileContentBuffer = new StringBuffer(1000);
            reader = new BufferedReader(new FileReader(sourceFileName));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                sourceFileContentBuffer.append(readData);
                buf = new char[1024];
            }

            return sourceFileContentBuffer.toString();

        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                // let it go, you've done your best.
            }
        }
    }

    private static void writeValidationFile(final String validationFileName, final String newContent) throws FileNotFoundException {

        PrintStream out = null;

        try {
            File validationFile = new File(validationFileName);
            FileOutputStream fout = new FileOutputStream(validationFile);
            out = new PrintStream(fout);
            out.print(newContent);

        } finally {
            try {
                out.close();
            } catch (Exception e) {
                // let it go, you've done your best.
            }
        }
    }
}
