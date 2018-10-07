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
package abc.xyz.pts.bcs.common.dao;

import abc.xyz.pts.bcs.common.dto.PrAddGeneratedReport;
import abc.xyz.pts.bcs.common.dto.PrGetReport;
import abc.xyz.pts.bcs.common.dto.PrGetReportImage;
import abc.xyz.pts.bcs.common.dto.PrRequeryReport;
import abc.xyz.pts.bcs.common.dto.PrUpdateGeneratedReport;

public interface DownloadStatsReportDAO {

    String PKG_GENERATED_REPORT = "PKG_GENERATED_REPORT";
    String PR_GET_REPORTS = "PR_GET_REPORTS";
    String PR_REQUERY_REPORTS = "PR_REQUERY_REPORTS";
    String PR_ADD_GENERATED_REPORT = "PR_ADD_GENERATED_REPORT";
    String PR_GET_REPORT_IMAGE = "PR_GET_REPORT_IMAGE";
    String PR_UPDATE_GENERATED_REPORT = "PR_UPDATE_GENERATED_REPORT";

    String P_GENERATED_REPORT_RC = "P_GENERATED_REPORT_RC";
    String P_USERNAME = "P_USERNAME";
    String P_NUM_RECORDS = "P_NUM_RECORDS";
    String P_QUERY_COUNT = "P_QUERY_COUNT";
    String P_MESSAGE = "P_MESSAGE";
    String P_REPORT_NAME = "P_REPORT_NAME";
    String P_REPORT_PARAMETERS = "P_REPORT_PARAMETERS";
    String P_REPORT_FORMAT = "P_REPORT_FORMAT";
    String P_STATUS = "P_STATUS";
    String P_GENERATED_REPORT_ID = "P_GENERATED_REPORT_ID";
    String P_CREATED_DATETIME = "P_CREATED_DATETIME";
    String P_REPORT_IMAGE = "P_REPORT_IMAGE";
    String P_START_RECORD = "P_START_RECORD";
    String P_ORDER_BY_COL = "P_ORDER_BY_COL";
    String P_ASC_DESC = "P_ASC_DESC";

    PrGetReport getReports(final PrGetReport prGetReport);

    PrRequeryReport getReports(final PrRequeryReport prRequeryReport);

    PrAddGeneratedReport addReport(final PrAddGeneratedReport prAddGeneratedReport);

    PrGetReportImage findReportById(final PrGetReportImage prGetReportImage);

    PrUpdateGeneratedReport updateReport(final PrUpdateGeneratedReport prUpdateGeneratedReport);
}
