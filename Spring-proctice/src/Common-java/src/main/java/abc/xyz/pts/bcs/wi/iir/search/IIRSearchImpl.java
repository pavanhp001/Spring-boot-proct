/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
/**
 * @author MMotlib-Siddiqui
 */
package abc.xyz.pts.bcs.wi.iir.search;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ssa.ssase.ClieSock;
import ssa.ssautil.SSAException;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.enums.SortOrderType;
import abc.xyz.pts.bcs.common.iir.IIRSearchFieldType;
import abc.xyz.pts.bcs.common.irisk.enums.AlertMatchTypes;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.iir.IIRConstants;
import abc.xyz.pts.bcs.wi.iir.IIRFieldLayout;
import abc.xyz.pts.bcs.wi.iir.IIRFieldLayoutImpl;
import abc.xyz.pts.bcs.wi.iir.IIRUtil;
import abc.xyz.pts.bcs.wi.iir.connection.IIRConnection;
import abc.xyz.pts.bcs.wi.iir.connection.IIRConnectionPool;
import abc.xyz.pts.bcs.wi.iir.exception.IIRConnectException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchInvalidDataFormatException;

public final class IIRSearchImpl implements IIRSearch {
    private static final Logger LOG = Logger.getLogger(IIRSearchImpl.class);
    private boolean tryPrimary = true;
    private IIRConnectionPool iirConnectionPool;
    private String iirSearchName;
    private String iirSearchWidth;
    private String iirMatchTolerance;
    private final ConcurrentHashMap<String, Future<List<IIRFieldLayoutImpl>>> inputFields = new ConcurrentHashMap<String, Future<List<IIRFieldLayoutImpl>>> ();
    private final ConcurrentHashMap<String, Future<List<IIRFieldLayoutImpl>>> outputFields = new ConcurrentHashMap<String, Future<List<IIRFieldLayoutImpl>>> ();
    private static final DateTimeFormatter IIR_INPUT_DATE_FORMAT = DateTimeFormat.forPattern("dd-MMM-yyyy");
    private static final DateTimeFormatter TARGET_DATE_FORMAT = DateTimeFormat.forPattern("dd-MMM-yy");

    // All column names in IIR table are prefixed by this value
    private static final String COL_NAME_PREFIX = "IDS_";

    // Used to determine if the attribute should be used for filter or fuzzy
    private enum IsFilterOrFuzzyType {
        FILTER,             // in the where statement
        SEARCH_INPUT        // sent to IIR
    }

    public IIRSearchImpl() {
        super();
    }

    public IIRSearchImpl(final IIRConnectionPool cp) {
        this.iirConnectionPool = cp;
        this.tryPrimary = cp.isPrimaryUp();
        if (LOG.isDebugEnabled()) {
            LOG.debug("tryPrimary=" + this.tryPrimary);
        }
    }

    /**
     * Get Field layout details for the supplied details.
     *
     * @param searchName
     * @param viewType
     * @param numOfViewField
     * @return List<IIRFieldLayoutImpl>
     * @throws IIRSearchException
     * @throws IIRConnectException
     */
    private List<IIRFieldLayoutImpl> getSearchLayout (final String searchName, final String viewType, final int numOfViewField, 
            final IIRConnection iirConnection) throws IIRSearchException, IIRConnectException {
        ClieSock cs = null;
        try {
            final String[] searchViewFields = new String[numOfViewField];
            final int[] searchViewLengths = new int[numOfViewField];
            final int[] searchViewOffsets = new int[numOfViewField];
            final int[] searchViewRepeats = new int[numOfViewField];
            final String[] searchViewFormats = new String[numOfViewField];

            cs = iirConnection.getSocket();
            cs.ids_search_layout
                    ( searchName // IN: Search name
                    , viewType // IN: View type
                    , IIRConstants.SEARCH_LAYOUT_FIELD_ORDER_BY_OFFSET // IN: Field order
                    , searchViewFields // OUT:
                    , numOfViewField
                    , IIRConstants.MAX_FIELD_LENGTH
                    , searchViewLengths // OUT:
                    , numOfViewField
                    , searchViewOffsets // OUT:
                    , numOfViewField
                    , searchViewRepeats // OUT:
                    , numOfViewField
                    , searchViewFormats // OUT:
                    , numOfViewField
                    , IIRConstants.MAX_FIELD_LENGTH
                    );

            // build up a list of fields to return
            final ArrayList<IIRFieldLayoutImpl> fieldLayoutList = new ArrayList<IIRFieldLayoutImpl>(numOfViewField);

            for (int i = 0; i < numOfViewField; ++i) {
                if (LOG.isDebugEnabled()) {
                    final StringBuffer buf = new StringBuffer();
                    buf.append(i).append(':');
                    buf.append("iir-field=").append(viewType).append(':');
                    buf.append(" fieldName(").append(searchViewFields[i]).append(')');
                    buf.append(" length(").append(searchViewLengths[i]).append(')');
                    buf.append(" offset(").append(searchViewOffsets[i]).append(')');
                    buf.append(" repeat(").append(searchViewRepeats[i]).append(')');
                    buf.append(" format(").append(searchViewFormats[i].trim()).append(')');

                    if (LOG.isDebugEnabled()) {
                        LOG.debug(buf.toString());
                    }
                }

                // add to list
                final IIRFieldLayoutImpl fl = new IIRFieldLayoutImpl(searchViewFields[i], searchViewLengths[i], 
                        searchViewOffsets[i], searchViewRepeats[i], searchViewFormats[i]);
                fieldLayoutList.add(fl);
            }

            return fieldLayoutList;
        } catch (final SSAException e) {
            throw new IIRSearchException("ids_search_layout", e, IIRUtil.getIIRErrorMsg(cs));
        }
    }

    /**
     * Get a list of field attributes from IIR.
     *
     * @param searchName
     * @param viewType
     * @return fieldList
     * @throws IIRConnectException
     * @throws IIRException
     */
    private List<IIRFieldLayoutImpl> getIIRFields (final String searchName, final String viewType, final IIRConnection iirConnection) 
            throws IIRSearchException, IIRConnectException {
        ClieSock cs = null;

        try {
            final String[] viewName = new String[1];
            final int[] viewFieldsCount = new int[1];
            final int[] viewLength = new int[1];

            // * Input View name
            cs = iirConnection.getSocket();
            cs.ids_search_view_get
                    ( searchName // IN: Search name
                    , viewType // IN: View type
                    , viewName // Out: View name
                    , viewName.length // Out: View name length
                    , viewFieldsCount // Out: Field count
                    , viewLength // Out: View length
                    );

            // number of fields to process
            final int numViewFields = viewFieldsCount[0];
            final List<IIRFieldLayoutImpl> fields = getSearchLayout(searchName, viewType, numViewFields, iirConnection);

            return fields;
        } catch (final SSAException e) {
            throw new IIRSearchException("ids_search_view_get", e, IIRUtil.getIIRErrorMsg(cs));
        }
    }

    /**
     * Get a list of possible search fields (criteria) for IIR.
     *
     * @return field list
     * @throws IIRConnectException
     * @throws IIRException
     */
    private List<IIRFieldLayoutImpl> getSearchInputFields ( final String searchName, final IIRConnection iirConnection) 
            throws IIRSearchException, IIRConnectException {
        Future< List<IIRFieldLayoutImpl > > fields = inputFields.get(searchName);

        if( fields == null ) {
            final Callable< List<IIRFieldLayoutImpl > > callable = new Callable< List<IIRFieldLayoutImpl > >() {
                @Override
                public List<IIRFieldLayoutImpl > call() throws IIRException, IIRConnectException {
                    LOG.debug("Loading input fields from IIR and caching");
                    return getIIRFields(searchName, IIRConstants.VIEW_TYPE_INPUT, iirConnection);
                };
            };

            final FutureTask< List<IIRFieldLayoutImpl >> newTask = new FutureTask< List<IIRFieldLayoutImpl >>( callable );
            fields = inputFields.putIfAbsent( searchName, newTask );
            if( fields == null ) {
                fields = newTask;
                newTask.run();
            }
        }

        try {
            return  fields.get();
        } catch (final ExecutionException ex) {
            LOG.error("Unable to load input fields for IIR", ex);
            throw new IIRConnectException("Unable to read input fields from IIR for search", ex.getCause());
        } catch (final InterruptedException ex) {
            LOG.error("Unable to load input fields for IIR", ex);
            throw new IIRConnectException("Unable to read input fields from IIR for search", ex.getCause());
        }
    }

    /**
     * Get a list of search field values returned from IIR.
     *
     * @return field list
     * @throws IIRConnectException
     * @throws IIRException
     */
    private List<IIRFieldLayoutImpl> getSearchOutputFields (final String searchName, final IIRConnection iirConnection) 
            throws IIRSearchException, IIRConnectException { 
        Future< List<IIRFieldLayoutImpl > > fields = outputFields.get(searchName);
        if (fields == null) {
            final Callable< List<IIRFieldLayoutImpl > > callable = new Callable< List<IIRFieldLayoutImpl > >() {
                @Override
                public List<IIRFieldLayoutImpl > call() throws IIRException, IIRConnectException {
                    LOG.debug("Loading output Fields from IIR and caching");
                    return getIIRFields(searchName, IIRConstants.VIEW_TYPE_OUTPUT, iirConnection);
                };
            };

            final FutureTask< List<IIRFieldLayoutImpl >> newTask = new FutureTask< List<IIRFieldLayoutImpl >>( callable );
            fields = outputFields.putIfAbsent( searchName, newTask );
            if (fields == null) {
                fields = newTask;
                newTask.run();
            }
        }

        try {
            return fields.get();
        } catch( final ExecutionException ex ) {
            LOG.error( "Unable to load output fields for IIR", ex );
            throw new IIRConnectException("Unable to read output fields from IIR for search");
        } catch( final InterruptedException ex ) {
            LOG.error( "Unable to load output fields for IIR", ex );
            throw new IIRConnectException("Unable to read output fields from IIR for search");
        }
    }

    /**
     * Determine if we can use this field for Filter/Fuzzy.
     *
     * @param ft field to evaluate
     * @param usedWhere which part of the search will this be used in, input/filter
     * @param isMatch are we trying to match a target?
     * @return
     */
    private boolean isUseThisField(final IIRSearchFieldType ft, final IsFilterOrFuzzyType usedWhere, final boolean isMatch) {
        switch (ft) {
            case FORENAMES:
            case LAST_NAME:
            case FULL_NAME:
                // always pass to iir to evaluate
                return (usedWhere == IsFilterOrFuzzyType.SEARCH_INPUT);
            case GENDER:
            case BIRTH_DATE:
            case BIRTH_COUNTRY_CODE:
            case BIRTH_PLACE:
            case NATIONALITY:
            case DOC_NUM:
            case DOC_TYPE:
            case DOC_TYPE_NO:
                if (usedWhere == IsFilterOrFuzzyType.SEARCH_INPUT && isMatch) {
                    // pass to iir to evaluate only if we are doing a match
                    return true;
                } else if (usedWhere == IsFilterOrFuzzyType.FILTER && (!isMatch)) {
                    // use filter if we are not doing a match
                    return true;
                } else {
                    return false;
                }
            case ID:
                if (usedWhere == IsFilterOrFuzzyType.FILTER && isMatch) {
                    return true;  // we need to exclude itself from the result
                }
                return false;
            case VALID_UNTIL_DATE: // so we don't forget
            case WATCHLIST_ENABLED_IND:
            default:
                // we filter on rest of the fields
                return (usedWhere == IsFilterOrFuzzyType.FILTER ? true : false);
        }
    }

    /**
     * Convert String to Calendar.
     *
     * Expects date in DD-MON-YY format.
     *
     * @param str
     * @return calendar
     * @throws IIRSearchInvalidDataFormatException
     */
    private java.util.Calendar stringToCal ( final String str) throws IIRSearchInvalidDataFormatException {
        try {
            if(str == null || str.trim().length() <= 0) {
                return null;
            }
            final DateTime date = TARGET_DATE_FORMAT.parseDateTime(str);
            return date.toGregorianCalendar();
        } catch (final IllegalArgumentException e) {
            throw new IIRSearchInvalidDataFormatException(e);
        }
    }

    /**
     * IIR expects all dates to be in dd-MMM-yy format
     *
     * @param cal
     * @return
     */
    private String dateToString(final java.util.Date dt) {
        return IIR_INPUT_DATE_FORMAT.print(new DateTime(dt));
    }

    private String calToString(final Calendar cal) {
        if (cal == null) {
            return "";
        }

        return IIR_INPUT_DATE_FORMAT.print(new DateTime(cal));
    }

    private TargetItem parseToTarget ( final byte[] record, final Integer score, final List<IIRFieldLayoutImpl> fieldList) 
            throws IIRSearchInvalidDataFormatException {
        final TargetItem ti = new TargetItem();

        if (LOG.isDebugEnabled()) {
           LOG.debug("iir-data='" + new String(record) + "'");
        }

        // Score
        ti.setMatchScore(score.doubleValue());

        // Data Fields
        for (final IIRFieldLayout fl : fieldList) {
            final int len = fl.getLength();
            final int offset = fl.getOffset();
            final IIRSearchFieldType fieldType = IIRSearchFieldType.getInstance(fl.getName());
            if (fieldType != null) {
                String val;

                try {
                   val = new String(record,offset, len, "UTF-8").trim();
                } catch (final UnsupportedEncodingException e) {
                   LOG.error("Unable to convert to UTF-8 - major JDK issue!");
                   throw new IIRSearchInvalidDataFormatException(e);
                }

                // ** Update target data
                fillTarget(fieldType, ti, val);
            } else {
                LOG.warn("unrecognised field name from IIR - '" + fl.getName() + "' - not processed");
            }
        }

        return ti;
    }

    private void fillTarget ( final IIRSearchFieldType fieldType, final TargetItem ti, final String val) throws IIRSearchInvalidDataFormatException {
        if (StringUtils.isBlank(val)) {
            return;
        }

        switch (fieldType) {
            case ID:
                ti.setId(StringUtils.isNotBlank(val) ? new Long(val) : null);
                break;
            case FORENAMES:
                ti.setForename(val);
                break;
            case LAST_NAME:
                ti.setLastName(val);
                break;
            case DOC_NUM:
                ti.setDocNo(val);
                break;
            case GENDER:
                ti.setGender(val);
                break;
            case DOC_TYPE:
                ti.setDocType(val);
                break;
            case PROTOCOL_NUMBER:
                ti.setProtocolNumber(val);
                break;
            case ACTION_CODE:
                ti.setActcCode(val);
                break;
            case NATIONALITY:
                ti.setNationality(val);
                break;
            case BIRTH_DATE:
                ti.setBirthDate(stringToCal(val));
                break;
            case BIRTH_DATE_FROM:
                ti.setBirthDateFrom(stringToCal(val));
                break;
            case BIRTH_DATE_TO:
                ti.setBirthDateTo(stringToCal(val));
                break;
            case VALID_UNTIL_DATE:
                ti.setValidUntilDate(stringToCal(val));
                break;
            case BIRTH_PLACE:
                ti.setBirthPlace(val);
                break;
            case SCORE:
                ti.setMatchScore(StringUtils.isNotBlank(val) ? Double.parseDouble(val) : null);
                break;
            case WATCHLIST_NAME:
                ti.setWatlName(val);
                break;
            case REASON_CODE:
                ti.setRescCode(val);
                break;
            case BIRTH_COUNTRY_CODE:
                ti.setCountryOfBirth(val);
                break;
            case CREATED_DATETIME:
                if (ti.getDateOfLastChange() == null) {
                    ti.setDateOfLastChange(stringToCal(val));
                }
                break;
            case MODIFIED_DATETIME:
                ti.setDateOfLastChange(stringToCal(val));
                break;
            default:
                break;
        }
    }

    /**
     * Initiate IIR Search.
     *
     * @param searchName
     * @param searchRec
     * @return
     * @throws IIRSearchException
     * @throws IIRConnectException
     */
    private int doSearchStart (final String searchName, final byte[] searchRec, final IIRConnection iirConnection) 
            throws IIRSearchException, IIRConnectException {
        ClieSock cs = null;
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("iirSearchWidth=" + iirSearchWidth);
                LOG.debug("iirMatchTolerance=" + iirMatchTolerance);
            }

            /*
             * Open the System and start a search
             */
            final byte[][] parameters = new byte[0][0];
            final int[] outputRecCount = new int[1];
            final byte[][] records = new byte[0][0];

            cs = iirConnection.getSocket();
            cs.ids_search_start
                    ( searchName        // IN: Search name
                    , iirSearchWidth    // IN: Search width
                    , iirMatchTolerance // IN: Match tolerance
                    , parameters        // IN: Search values - not used
                    , searchRec         // IN: Search record
                    , searchRec.length
                    , ""                // IN: Answer prefix - not used
                    , outputRecCount    // OUT: Number of records found
                    , records           // IN: Record set - not used
                    );

            return outputRecCount[0];
        } catch (final SSAException e) {
            throw new IIRSearchException("ids_search_start", e, IIRUtil.getIIRErrorMsg(cs));
        }
    }

    private String sqlFilter(final String col, final Object val, final boolean isMatch) {
        final StringBuffer buf = new StringBuffer();
        final String colName = "UPPER(" + COL_NAME_PREFIX + col + ") ";
        final String valStr =  "UPPER('" + getStrForSQL(val.toString()) + "')";

        if (isMatch) {
            buf.append("( ");
            buf.append("NVL(").append(colName).append(',').append(getStrForSQL(valStr)).append(')');
        } else {
            buf.append(colName);
        }

        buf.append(" = ");
        buf.append(valStr);

        if (isMatch) {
            buf.append(" OR ");
            buf.append(colName).append(" IS NULL ");
            buf.append(" ) ");
        }

        return buf.toString();
    }

    /* Converts a string to an Oracle Unicode encoded String of the form \xxxx\yyyy */
    public static StringBuffer getUnicodeEscapedStr(final String str) {
        final StringBuffer sb = new StringBuffer(str.length() * 5); // Each char converts to "\xxxx"

        // Formatters aren't thread safe!
        final Formatter formatter = new Formatter(sb);

        for(int i = 0 ; i < str.length(); i++) {
            formatter.format("\\%04x", (int)str.charAt(i));
        }

        if(LOG.isDebugEnabled()) {
            LOG.debug("String '" + str + "' mapped to Unicode escape sequence '" + sb.toString() + "'");
        }

        return sb;
    }

    /* Handles the special processing needed to avoid IIR mangling the character encoding
       of the protocolNumber/owners reference field - the Oracle function UNISTR is used
       to enable the data to be encoded in an Oracle-specific way which involves only ASCII text,
       The input text is uppercased before encoding, as UPPER() won't work with UNISTR */
    private String protocolNumberSqlFilter(final String col, final Object val, final boolean isMatch) {
        final StringBuffer buf = new StringBuffer(100);
        final StringBuffer colName = new StringBuffer("UPPER(").append(COL_NAME_PREFIX).append(col).append(") ");
        final StringBuffer valStr =  new StringBuffer("UNISTR('").append(getUnicodeEscapedStr(val.toString().toUpperCase())).append("')");

        if (isMatch) {
            buf.append('(');
            buf.append("NVL(").append(colName).append(',').append(valStr).append(')');
        } else {
            buf.append(colName);
        }

        buf.append(" = ").append(valStr);

        if (isMatch) {
            buf.append(" OR ");
            buf.append(colName).append(" IS NULL ");
            buf.append(')');
        }

        return buf.toString();
    }

    /**
     * Issues a filter for IIR Search
     *
     * @param searchName
     * @param fieldList
     * @param isMatch
     * @throws IIRSearchException
     * @throws IIRConnectException
     */
    private void doSearchFilter (final String searchName, final Map<IIRSearchFieldType, Object> fieldList, final boolean isMatch, 
            final IIRConnection iirConnection) throws IIRSearchException, IIRConnectException {
        if (fieldList == null || fieldList.isEmpty()) {
            LOG.debug("search-filter=(null)");
            iirConnection.setFilter(null);
            return;
        }

        // build up filter
        boolean first = true;
        final StringBuffer buf = new StringBuffer();
        String birthDate = null;

        for (final Map.Entry<IIRSearchFieldType, Object> field : fieldList.entrySet()) {
            // empty key
            final boolean useThis = isUseThisField(field.getKey(), IsFilterOrFuzzyType.FILTER, isMatch);
            if (!useThis) {
                continue;
            }

            Object val = field.getValue();
            // empty values
            if (val == null || StringUtils.isBlank(val.toString())) {
                continue;
            }

            if (val instanceof Calendar) {
                val = calToString((Calendar)val);
            } else if (val instanceof java.util.Date) {
                val = dateToString((java.util.Date)val);
            }

            // * construct Filter SQL (using IIRSearchFieldType)
            // **************************************************
            switch (field.getKey()) {
                case ID:
                    if (!first) {
                        buf.append(" AND ");
                    }
                    buf.append(COL_NAME_PREFIX).append(field.getKey().getVal()).append(" != ").append( val);
                    first = false;
                    break;
                case BIRTH_DATE:
                    birthDate = (String)val;
                    break;
                case BIRTH_DATE_FROM:
                case BIRTH_DATE_TO:
                    // ignore - search is base on a single DOB
                    break;
                case PROTOCOL_NUMBER:
                    if (!first) {
                        buf.append(" AND ");
                    }
                    buf.append(protocolNumberSqlFilter(field.getKey().getVal(), val, isMatch));
                    first = false;
                    break;
                case SEVERITY_LEVEL:
                    if (!first) {
                        buf.append(" AND ");
                    }
                    buf.append(COL_NAME_PREFIX).append(Field.RESC_CODE).append(" IN ");
                    buf.append(" ( ");
                    buf.append("    SELECT ").append(Field.CODE);
                    buf.append("      FROM ").append(Table.REASON_CODES);
                    buf.append("     WHERE ").append(Field.SEVERITY_LEVEL).append(" = ").append(val);
                    buf.append("  )");
                    first = false;
                    break;
                case WATCHLIST_ENABLED_IND:
                    if (!first) {
                        buf.append(" AND ");
                    }
                    buf.append(COL_NAME_PREFIX).append(Field.WATL_NAME).append(" IN ");
                    buf.append(" ( ");
                    buf.append("    SELECT ").append(Field.NAME);
                    buf.append("      FROM ").append(Table.WATCH_LISTS);
                    buf.append("     WHERE ").append(Field.ENABLED_IND).append(" = 'Y' ");
                    buf.append("  )");
                    first = false;
                    break;
                case VALID_UNTIL_DATE:
                    if (!first) {
                        buf.append(" AND ");
                    }
                    buf.append("TO_DATE('").append(val).append("','DD-MON-YYYY')");
                    if (isMatch) {
                        buf.append(" <= ");
                    } else {
                        buf.append(" >= ");
                    }
                    buf.append("TO_DATE(").append(COL_NAME_PREFIX).append(field.getKey().getVal()).append(",'DD-MON-YYYY')");
                    first = false;
                    break;
                default:
                    if (!first) {
                        buf.append(" AND ");
                    }
                    buf.append(sqlFilter(field.getKey().getVal(), val, isMatch));
                    first = false;
                    break;
            }
        }

        // * Birth date Range filter
        // ****************************
        if (birthDate != null) {
            if (!first) {
                buf.append(" AND ");
            }
            buf.append(getBirthDateRangeSql(birthDate));
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("search-filter=(" + buf + ")");
        }

        // * make the call
        // ***************************
        final int stat = iirConnection.setFilter(buf.toString());
        if (LOG.isDebugEnabled()) {
            LOG.debug("status from ids_search_filter = " + stat);
        }
    }

    /**
     * Generate SQL based on the values in the BIRTH_DATE fields.
     *
     * Here are possible values for DOB columns
     * (1)   BIRTH_DATE       BIRTH_DATE_FROM         BIRTH_DATE_TO
     * (2)       YES
     *                           YES                    YES
     *
     * Scenario (1) dt should match exactly to this date
     * Scenario (2) dt should be within BIRTH_DATE_FROM and BIRTH_DATE_TO
     *
     * @param dt
     * @return SQL
     */
    private String getBirthDateRangeSql (final String dt) {       // DD-MON-YYYY
        if (dt == null) {
            return "";
        }

        final StringBuffer buf = new StringBuffer();
        final String dtToDate = "TO_DATE('" + dt + "','DD-MON-YYYY')";
        final String fromDtField = "TO_DATE(" + COL_NAME_PREFIX + IIRSearchFieldType.BIRTH_DATE_FROM + ",'DD-MON-YYYY')";
        final String toDtField = "TO_DATE(" + COL_NAME_PREFIX + IIRSearchFieldType.BIRTH_DATE_TO + ",'DD-MON-YYYY')";

        // ****
        // **
        // ****
        buf.append(" (");
        buf.append(COL_NAME_PREFIX).append(IIRSearchFieldType.BIRTH_DATE).append(" = ").append(dtToDate);
        buf.append(" OR ");
        buf.append(" (");

        // only if dob is null
        buf.append(COL_NAME_PREFIX).append(IIRSearchFieldType.BIRTH_DATE).append(" IS NULL ");
        buf.append(" AND ");

        // dt is within the range in db
        buf.append(dtToDate).append(" BETWEEN ").append(fromDtField).append(" AND NVL(").append(toDtField).append(", ").append(fromDtField).append(") ");
        buf.append(") ");
        buf.append(") ");

        return buf.toString();
    }

    /**
     * Utility to establish the record length (fixed width)
     *
     * @param outFieldLayout
     * @return record length
     */
    private int getRecordSize(final List<IIRFieldLayoutImpl> outFieldLayout) {
        int len = 0;
        for (final IIRFieldLayoutImpl fl : outFieldLayout) {
            len += fl.getLength();
        }
        return len;
    }

    /**
     * Get all data from the IIR search request.
     *
     * @param searchName
     * @param numRecords
     * @return rows of data
     * @throws IIRSearchException
     * @throws IIRConnectException
     * @throws IIRSearchInvalidDataFormatException
     */
    private List<TargetItem> doSearchGetAll (final String searchName, final int numRecords, final IIRConnection iirConnection) 
            throws IIRSearchException, IIRConnectException, IIRSearchInvalidDataFormatException {
        ClieSock cs = null;
        try {
            final List<IIRFieldLayoutImpl> outFieldLayout = getSearchOutputFields(searchName, iirConnection);
            final List<TargetItem> rowDataList = new ArrayList<TargetItem>(numRecords);
            final byte[] searchRec = new byte[getRecordSize(outFieldLayout)];
            if (numRecords > 0) {
                int stat = 0; // to start it off
                while (stat == 0) {
                    final int[] score = new int[1];
                    final int[] sreps = new int[0];
                    final int[] freps = new int[0];

                    cs = iirConnection.getSocket();
                    stat = cs.ids_search_get
                            ( searchName // IN: Search name
                            , searchRec // OUT: Search record
                            , searchRec.length //
                            , score // OUT: Score
                            , sreps // OUT: Sreps - not used
                            , sreps.length // NOT USED
                            , freps // OUT: Freps - not used
                            , freps.length // NOT USED
                            );

                    if (stat == 0) { // got some data to process
                        final TargetItem target = parseToTarget(searchRec, score[0], outFieldLayout);
                        target.setMatchType(AlertMatchTypes.PERSON_MATCH);

                        if (LOG.isDebugEnabled()) {
                            LOG.debug(target);
                        }
                        rowDataList.add(target);
                    }
                }
            }
            return rowDataList;
        } catch (final SSAException e) {
            throw new IIRSearchException("ids_search_get", e, IIRUtil.getIIRErrorMsg(cs));
        }
    }

    /**
     * Issue Search complete.
     *
     * @param searchName
     * @throws IIRSearchException
     * @throws IIRConnectException
     */
    private void doSearchFinish (final String searchName, final IIRConnection iirConnection) throws IIRSearchException, IIRConnectException {
        ClieSock cs = null;
        try {
            cs = iirConnection.getSocket();
            cs.ids_search_finish(searchName);
        } catch (final SSAException e) {
            throw new IIRSearchException("ids_search_finish", e, IIRUtil.getIIRErrorMsg(cs));
        }
    }

    /**
     * Takes in a fieldname and search request and returns null if the
     * field doesn't exist or the value it finds.
     *
     * @param fieldName
     * @param req
     * @return
     */
    private String getSearchFieldValue(final String fieldName, final IIRSearchRequest req) {
        String value = null;
        final IIRSearchFieldType key = IIRSearchFieldType.getInstance(fieldName);
        final Object fieldValue = req.getCriteria().get(key);

        if (LOG.isDebugEnabled()) {
            if (fieldValue == null) {
                LOG.debug(key + " not found");
            } else {
                LOG.debug(key + "='" + fieldValue + "'");
            }
        }

        if (fieldValue != null) {
            // skip fields that we are going to filter
            final boolean useThis = isUseThisField(key, IsFilterOrFuzzyType.SEARCH_INPUT, req.isMatch());
            if (useThis) {
                if (fieldValue instanceof java.util.Calendar) {
                    value = calToString((java.util.Calendar)fieldValue);
                } else if (fieldValue instanceof java.util.Date) {
                    value = dateToString((java.util.Date)fieldValue);
                } else {
                    value = fieldValue.toString();
                }
            }
        }
        return value;
    }

    /**
     * Get Fixed length search record for IIR.
     *
     * @return search record
     * @throws IIRConnectException
     * @throws IIRException
     */
    private byte[] getSearchRecord( final IIRSearchRequest req) throws IIRSearchException, IIRConnectException, IIRSearchInvalidDataFormatException {
        // ** get search fields
        final List<IIRFieldLayoutImpl> searchFieldList = getSearchInputFields(req.getSearchName(), req.getConnection());

        // ** construct search record
        final ByteArrayOutputStream searchRecBuf = new ByteArrayOutputStream(); // Size this correctly!!!

        // loop through all fields as search record is fixed size
        // so we need to store all values even if they are null (null's will be padded to correct size)
        for (final IIRFieldLayout fl : searchFieldList) {
            final String value = getSearchFieldValue(fl.getName(), req);
            final byte[] record = fl.getSearchReqRecord(value);
            if (LOG.isDebugEnabled()) {
                LOG.debug("search-criteria=(" + fl.getName() + "=" + value + ")");
            }
            // field to match on fuzzy logic
            try {
                searchRecBuf.write(record);
            } catch(final IOException e) {
                LOG.error("Unable to build the IIR search buffer", e);
                throw new IIRSearchInvalidDataFormatException(e);
            }
        }

        return searchRecBuf.toByteArray();
    }

    public void setIirConnectionPool(final IIRConnectionPool iirConnectionPool) {
        this.iirConnectionPool = iirConnectionPool;
    }

    /**
     * Search IIR with the supplied criteria in the req.
     *
     * At the moment the matching is done on Fullnaname only.
     * The rest of the criteria in req is used to filter the data back.
     *
     * @param req
     * @return response
     * @throws IIRConnectException
     * @throws IIRSearchException
     * @throws IIRSearchInvalidDataFormatException
     */
    private IIRSearchResponse doSearch (final IIRSearchRequest req) throws IIRConnectException, IIRSearchException, IIRSearchInvalidDataFormatException {

        // * construct a search record (fixed-width)
        // ********************************************
        final byte[] searchRecBytes = getSearchRecord(req);
        if (LOG.isDebugEnabled()) {
            LOG.debug("iir-search-record=(" + new String(searchRecBytes) + ")");
        }

        final String searchName = req.getSearchName();

        // ** Search FILTER
        // ******************
        doSearchFilter(searchName, req.getCriteria(), req.isMatch(), req.getConnection());

        // * search START
        // ******************
        int outRecCount = 0;
        outRecCount = doSearchStart(searchName, searchRecBytes, req.getConnection());

        // ** search GET data
        // ******************
        List<TargetItem> rowDataList = doSearchGetAll(searchName, outRecCount, req.getConnection());
        rowDataList = getUniqueItems(rowDataList);

        // ** search END
        // ******************
        doSearchFinish(searchName, req.getConnection());

        // ** prepare response
        // ******************
        final IIRSearchResponseImpl resp = new IIRSearchResponseImpl(rowDataList);
        if (LOG.isDebugEnabled()) {
            LOG.debug("response=" + resp);
        }

        return resp;
    }

    /**
     * Keep the one with the highest match score.
     *
     * @param rowDataList
     */
    private List<TargetItem> getUniqueItems(final List<TargetItem> rowDataList) {
        if (rowDataList == null) {
            return rowDataList;
        }

        Collections.sort(rowDataList, new TargetItem.IdComparator(SortOrderType.ASCENDING));
        final ArrayList<TargetItem> newList = new ArrayList<TargetItem>(rowDataList.size());

        TargetItem prevTi = null;
        for (TargetItem ti : rowDataList) {
            if (prevTi != null && prevTi.getId().equals(ti.getId())) {
                if (ti.getMatchScore() < prevTi.getMatchScore()) {
                    // this is < previous - no need to add
                    ti = prevTi;
                } else {
                    // this is > previous - so remove previous and keep current
                    newList.remove(prevTi);
                    newList.add(ti);
                }
            } else {
                newList.add(ti);
            }

            prevTi = ti;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("removing duplicate before=" + rowDataList.size() + " after=" + newList.size());
        }

        return newList;
    }

    private void preProcessReq(final IIRSearchRequest req) {
        // ******
        // * Pre-process the request and combine FORENAMES & LAST_NAME to form FULLNAME.
        // * Remove FORENAMES & LAST_NAME from the requst as they are replace by FULLNAME.
        // ******
        final StringBuffer fullnameBuf = new StringBuffer();

        final String forenames = (String)req.getCriteria().get(IIRSearchFieldType.FORENAMES);
        if (StringUtils.isNotBlank(forenames)) {
            fullnameBuf.append(forenames);
        }

        final String lastName = (String)req.getCriteria().get(IIRSearchFieldType.LAST_NAME);
        if (StringUtils.isNotBlank(lastName)) {
            if (StringUtils.isNotBlank(forenames)) {
                fullnameBuf.append(' ');
            }
            fullnameBuf.append(lastName);
        }

        if (StringUtils.isNotBlank(fullnameBuf.toString())) {
            req.getCriteria().remove(IIRSearchFieldType.FORENAMES);
            req.getCriteria().remove(IIRSearchFieldType.LAST_NAME);
            req.getCriteria().put(IIRSearchFieldType.FULL_NAME, fullnameBuf.toString());
        }

        // **********************
        // * Combined DocNo & DocType only if doc_type_no is not supplied.
        // * this applies only for matching
        // **********************
        if (req.getCriteria().get(IIRSearchFieldType.DOC_TYPE_NO) == null && req.isMatch()) {
            final String docType = (String) req.getCriteria().get(IIRSearchFieldType.DOC_TYPE);
            final String docNo = (String) req.getCriteria().get(IIRSearchFieldType.DOC_NUM);

            final StringBuffer docTypeNo = new StringBuffer();
            if (!StringUtils.isBlank(docType)) {
                docTypeNo.append(docType);
            }
            if (!StringUtils.isBlank(docNo)) {
                docTypeNo.append(docNo);
            }

            req.getCriteria().remove(IIRSearchFieldType.DOC_TYPE);
            req.getCriteria().remove(IIRSearchFieldType.DOC_NUM);
            req.getCriteria().remove(IIRSearchFieldType.DOC_TYPE_NO);
            req.getCriteria().put(IIRSearchFieldType.DOC_TYPE_NO, docTypeNo.toString());
        } else if (!req.isMatch()) {
            // doc_type_no not used if we are search the watchlist (instead of match)
            req.getCriteria().remove(IIRSearchFieldType.DOC_TYPE_NO);
        }

        // ****
        // * ignore dates if we are trying to match on a range (duplicate check)
        // ****
        if (req.isMatch() && req.getCriteria().get(IIRSearchFieldType.BIRTH_DATE_FROM) != null
                && req.getCriteria().get(IIRSearchFieldType.BIRTH_DATE_TO) != null) {
            // ignore as this is a duplicate check
            req.getCriteria().remove(IIRSearchFieldType.BIRTH_DATE_FROM);
            req.getCriteria().remove(IIRSearchFieldType.BIRTH_DATE_TO);
        }
    }

    /**
     *
     */
    @Override
    public IIRSearchResponse search (final IIRSearchRequest req) throws IIRConnectException, IIRSearchException, IIRSearchInvalidDataFormatException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("request=" + req);
        }
        if (req == null) {
            return null;
        }

        try {
            String searchName = this.iirSearchName;

            if (req.getIirSearchType() != null) {
                 searchName = req.getIirSearchType().getVal();
            }
            req.setSearchName(searchName);

            // *****
            // * Search Name (Type) - supplied overrides default
            // *****
            if (LOG.isDebugEnabled()) {
                LOG.debug("searchName='" + req.getSearchName() + "'");
            }

            // ** pre-process the request
            // *********************************
            preProcessReq(req);

            // * Connect to Primary First
            // ****************************
            req.setConnection(this.iirConnectionPool.allocateConnection(req.getSearchName(), this.tryPrimary));
            if (LOG.isDebugEnabled()) {
                LOG.debug("sending iir search request to " + req.getConnection().getIirConnectionServerHost() + ":" + req.getConnection().getIirConnectionServerPort());
            }

            return doSearch(req);
        } catch (final IIRException e) {
            // * we log when we switch to secondary connection
            LOG.error(e.getIirErrMsg(), e);
            LOG.warn("iir attempting to switch to the secondary server");

            // * release Primary
            // ****************************
            this.iirConnectionPool.releaseConnection(req.getConnection(), e);

            // * try secondary
            // ****************************
            try {
                final boolean isPrimary = !this.tryPrimary;
                req.setConnection(this.iirConnectionPool.allocateConnection(req.getSearchName(), isPrimary));
                if (LOG.isDebugEnabled()) {
                    LOG.debug("sending iir search request to " + req.getConnection().getIirConnectionServerHost() + ":" + req.getConnection().getIirConnectionServerPort());
                }

                // ** Next time all requests are directed to this Pool from this instance
                // ****************************************************
                switchPrimary();

            } catch(final IIRConnectException s) {
                LOG.error("Unable to get connection from secondary instance");
                this.iirConnectionPool.releaseConnection(req.getConnection(), s);
                throw s;
            }
            return doSearch(req);
        } catch (final IIRConnectException e) { // TODO: make all IIR related exceptions extend IIRException
            // * we log when we switch to secondary connection
            final Throwable cause = e.getCause();

            // Unwrap the exception to get and log a possible underlying SSA exception
            if(cause instanceof IIRException) {
               LOG.error(((IIRException)cause).getIirErrMsg(), cause);
            } else {
               LOG.error("IIR Connection error", e);
            }
            LOG.warn("iir attempting to switch to the secondary server");

            // * release Primary
            // ****************************
            this.iirConnectionPool.releaseConnection(req.getConnection(), e);

            // * try secondary
            // ****************************
            try {
                final boolean isPrimary = !this.tryPrimary;
                req.setConnection(this.iirConnectionPool.allocateConnection(req.getSearchName(), isPrimary));
                if (LOG.isDebugEnabled()) {
                    LOG.debug("sending iir search request to " + req.getConnection().getIirConnectionServerHost() + ":" + req.getConnection().getIirConnectionServerPort());
                }

                // ** Next time all requests are directed to this Pool from this instance
                // ************************************************************************
                switchPrimary();

            } catch(final IIRConnectException s) {
                LOG.error("Unable to get connection from secondary instance",s );
                this.iirConnectionPool.releaseConnection(req.getConnection(), s);
                throw s;
            }
            return doSearch(req);
        } finally {
            // * release connection primary/secondary
            // ****************************
            final IIRConnection cnx = req.getConnection();
            req.setConnection(null); // Set to null here just in case releaseConnection throws an exception
            this.iirConnectionPool.releaseConnection(cnx);
        }
    }

    public void setIirSearchName(final String iirSearchName) {
        this.iirSearchName = iirSearchName;
    }

    /**
     * Switch Pool.
     *
     * @param isPrimary
     */
    private void switchPrimary() {
        // Informing the Collection pool that we had issues
        this.iirConnectionPool.toggleConnectionPool(this.tryPrimary);

        // next time use the correct Pool
        this.tryPrimary = this.iirConnectionPool.isPrimaryUp();
    }

    /**
     * Ensure the ' character is escaped to prevent SQL Injection.
     *
     * @param inStr
     * @return
     */
    private String getStrForSQL(final String inStr) {
        return StringUtils.replace(inStr, "'", "''");
    }

    public void setIirSearchWidth(final String iirSearchWidth) {
        this.iirSearchWidth = iirSearchWidth;
    }

    public void setIirMatchTolerance(final String iirMatchTolerance) {
        this.iirMatchTolerance = iirMatchTolerance;
    }

}
