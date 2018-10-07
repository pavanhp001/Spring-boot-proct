/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.audit.bean;

import java.io.Serializable;
import java.util.List;

import abc.xyz.pts.bcs.common.audit.business.Parameter;

/**
 * @author Reddy.Yattapu
 *
 */
public class AuditParameter implements Serializable {
    private Parameter name;
    private String value;
    private List<String> multipleValues;
    public Parameter getName() {
        return name;
    }
    public void setName(final Parameter name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(final String value) {
        this.value = value;
    }
    public List<String> getMultipleValues() {
        return multipleValues;
    }
    public void setMultipleValues(final List<String> multipleValues) {
        this.multipleValues = multipleValues;
    }
}
