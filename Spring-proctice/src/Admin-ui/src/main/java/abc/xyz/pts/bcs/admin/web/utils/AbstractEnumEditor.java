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
package abc.xyz.pts.bcs.admin.web.utils;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEnumEditor extends PropertyEditorSupport {

    private static final int NUM_WORDS = 3;

    protected String convertName(final String text) {
        StringBuilder buff = new StringBuilder(text);
        // enums arent normally longer than 3 words, the jdk default list size is 16.
        List<Integer> wordBeginnings = new ArrayList<Integer>(NUM_WORDS);
        for (int i = 0; i < buff.length(); i++) {
            if (Character.isUpperCase(buff.charAt(i)) && i != 0) {
                wordBeginnings.add(i);
            }
        }
        for (Integer integer : wordBeginnings) {
            buff.insert((int) integer, '_');
        }
        return buff.toString().toUpperCase();
    }
}
