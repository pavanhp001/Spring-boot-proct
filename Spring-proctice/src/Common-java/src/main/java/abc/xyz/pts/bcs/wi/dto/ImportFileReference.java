/* **************************************************************************
 *                              - CONFIDENTIAL                           *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dto;

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.w3._2001.xmlschema.Adapter1;

public class ImportFileReference implements Serializable {

    private static final long serialVersionUID = -6558507290902226110L;

    /** Primary ID */
    @XmlAttribute(name = "ID")
    protected Long id;
    @XmlAttribute(name = "FILE_REFERENCE_NUMBER")
    protected String fileReferenceNumber;
    @XmlAttribute(name = "TARGET_COUNT")
    protected Long targetCount;
    @XmlJavaTypeAdapter(Adapter1.class)
    @XmlSchemaType(name = "CREATED_DATETIME")
    protected Calendar createdDatetime;
    @XmlAttribute(name = "CREATED_BY")
    protected String createdBy;

    protected String fileStatus;
    protected String errorsCount;
    protected String duplicatesCount;
    protected String readyCount;
    protected String inProgressCount;
    protected String activeCount;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    public String getFileReferenceNumber() {
        return fileReferenceNumber;
    }
    public void setFileReferenceNumber(final String fileReferenceNumber) {
        this.fileReferenceNumber = fileReferenceNumber;
    }
    /**
     * @return the targetCount
     */
    public Long getTargetCount() {
        return targetCount;
    }
    /**
     * @param targetCount the targetCount to set
     */
    public void setTargetCount(final Long targetCount) {
        this.targetCount = targetCount;
    }


    public Calendar getCreatedDatetime() {
        return createdDatetime;
    }
    public void setCreatedDatetime(final Calendar createdDatetime) {
        this.createdDatetime = createdDatetime;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }
    public String getFileStatus() {
        return fileStatus;
    }
    public void setFileStatus(final String fileStatus) {
        this.fileStatus = fileStatus;
    }
    public String getErrorsCount() {
        return errorsCount;
    }
    public void setErrorsCount(final String errorsCount) {
        this.errorsCount = errorsCount;
    }
    public String getDuplicatesCount() {
        return duplicatesCount;
    }
    public void setDuplicatesCount(final String duplicatesCount) {
        this.duplicatesCount = duplicatesCount;
    }
    public String getReadyCount() {
        return readyCount;
    }
    public void setReadyCount(final String readyCount) {
        this.readyCount = readyCount;
    }
    public String getInProgressCount() {
        return inProgressCount;
    }
    public void setInProgressCount(final String inProgressCount) {
        this.inProgressCount = inProgressCount;
    }
    public String getActiveCount() {
        return activeCount;
    }
    public void setActiveCount(final String activeCount) {
        this.activeCount = activeCount;
    }

}
