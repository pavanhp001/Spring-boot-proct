/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import java.util.List;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.enums.WatchListDataType;


/**
 *    Travel Type arg object
 */
public class AppWatchListHitQueryArg extends SimpleInQueryArg {


    public AppWatchListHitQueryArg
        ( final Table table
        , final String[] hitTypes)
    {
        boolean pwl=false;
        boolean dwl=false;
        boolean mwl=false;
        boolean notify=false;
        boolean nohit=false;

        for(final String listType : hitTypes) {
          if("pwlHit".equals(listType)) {
             pwl = true;
          } else if("dwlHit".equals(listType)) {
             dwl = true;
          } else if("mwlHit".equals(listType)) {
             mwl = true;
          } else if("notifyHit".equals(listType)) {
             notify = true;
          } else if("noHit".equals(listType)) {
             nohit = true;
          }
        }

        final boolean isAlertType = (pwl || dwl || mwl || notify);

        if(!isAlertType && !nohit) {
           return; // Nothing to do
        }

        addToWhereClause(" ( ");

        if(isAlertType) {
           addToWhereClause(" 1=0 ");

           if(pwl) {
              addToWhereClause(" OR " + table.getValue() + "." + Field.PAL_STATUS + " in ('NotifyHit','Stop')");
           }

           if(dwl) {
              addToWhereClause(" OR " + table.getValue() + "." + Field.DAL_STATUS + " in ('NotifyHit','Stop')");
           }

           if(mwl) {
              addToWhereClause(" OR " + table.getValue() + "." + Field.MAL_STATUS + " in ('NotifyHit','Stop')");
           }

           if(notify) {
              addToWhereClause(" OR " + table.getValue() + "." + Field.NOTIFY_STATUS_FLAG + " = 'Y'");
           }

           }

           if(nohit) {
              if(isAlertType) {
                  addToWhereClause(" OR (");
              } else {
                  addToWhereClause("(");
              }

              addToWhereClause( table.getValue() + "." + Field.PAL_STATUS + " NOT IN ('NotifyHit','Stop') ");
              addToWhereClause( " AND " + table.getValue() + "." + Field.DAL_STATUS + " NOT IN ('NotifyHit','Stop') ");
              addToWhereClause( " AND " + table.getValue() + "." + Field.MAL_STATUS + " NOT IN ('NotifyHit','Stop') ");
              addToWhereClause(" ) ");
           }

           addToWhereClause(")");
    }

    /**
     *
     * @param table
     * @param wldList
     */
    public AppWatchListHitQueryArg(final Table table, final List<WatchListDataType> wldList)
    {
        if (wldList == null || wldList.size() <= 0)
        {
            addToWhereClause(" 1=0 ");  // ensure no data is returned
            return; // Nothing to do
        }

        final String notifyHitStr  = "NotifyHit";
        final String stopStr  = "Stop";

        boolean pwl = false;
        boolean dwl = false;
        boolean mwl = false;
        boolean notify = false;
        boolean nohit = false;

        for (final WatchListDataType wld : wldList)
        {
            if (WatchListDataType.WLD_PERSON == wld)
            {
                pwl = true;
            }
            else if (WatchListDataType.WLD_DOCUMENT == wld)
            {
                dwl = true;
            }
            else if (WatchListDataType.WLD_AP_RULE == wld)
            {
                mwl = true;
            }
            else if (WatchListDataType.WLD_NOTIFY_HIT == wld)
            {
                notify = true;
            }
            else if (WatchListDataType.WLD_NO_HIT == wld)
            {
                nohit = true;
            }
        }

        final boolean isAlertType = (pwl || dwl || mwl || notify);

        if (!isAlertType && !nohit)
        {
            addToWhereClause(" 1=0 ");  // ensure no data is returned
            return; // Nothing to do
        }

        addToWhereClause(" ( ");

        if (isAlertType)
        {
            addToWhereClause(" 1=0 ");

            if (pwl)
            {
                addToWhereClause(" OR " + table.getValue() + "." + Field.PAL_STATUS + " in (?,?)");
                addSearchCriteria(notifyHitStr);
                addSearchCriteria("Stop");
            }

            if (dwl)
            {
                addToWhereClause(" OR " + table.getValue() + "." + Field.DAL_STATUS + " in (?,?)");
                addSearchCriteria(notifyHitStr);
                addSearchCriteria(stopStr);
            }

            if (mwl)
            {
                addToWhereClause(" OR " + table.getValue() + "." + Field.MAL_STATUS + " in (?,?)");
                addSearchCriteria(notifyHitStr);
                addSearchCriteria(stopStr);
            }

            if (notify)
            {
                addToWhereClause(" OR " + table.getValue() + "." + Field.NOTIFY_STATUS_FLAG + " = ? ");
                addSearchCriteria("Y");
            }
        }

        if (nohit)
        {
            if (isAlertType)
            {
                addToWhereClause(" OR (");
            }
            else
            {
                addToWhereClause("(");
            }

            addToWhereClause(table.getValue() + "." + Field.PAL_STATUS + " NOT IN (?,?) ");
            addSearchCriteria(notifyHitStr);
            addSearchCriteria(stopStr);

            addToWhereClause(" AND " + table.getValue() + "." + Field.DAL_STATUS + " NOT IN (?,?) ");
            addSearchCriteria(notifyHitStr);
            addSearchCriteria(stopStr);

            addToWhereClause(" AND " + table.getValue() + "." + Field.MAL_STATUS + " NOT IN (?,?) ");
            addSearchCriteria(notifyHitStr);
            addSearchCriteria(stopStr);

            addToWhereClause(" ) ");
        }

        addToWhereClause(")");
    }

}
