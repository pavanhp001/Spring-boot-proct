package abc.xyz.pts.bcs.wi.iir.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import ssa.ssase.ClieSock;
import abc.xyz.pts.bcs.common.iir.IIRSearchFieldType;
import abc.xyz.pts.bcs.wi.iir.connection.IIRConnection;
import abc.xyz.pts.bcs.wi.iir.connection.IIRConnectionPool;

public final class IIRSearchImplTest 
{
    private Mockery context;
    
    private IIRConnectionPool cp;
    private IIRConnection con;
    private IIRSearchImpl iirSearch;
    private ClieSock clieSock;
//    private ClieSock clieSockMock;
    
    // ***************************************************************
    // ****         WE ARE TESTING LOGIC NOT IIR                  ****
    // ****  BEFORE AMENDING THIS TEST YOU NEED TO UNDERSTAND HOW ****
    // ****  IIRSearch WORKS.                                     ****
    // ***************************************************************
    
    // ** Pretender
    private class ClieSockTest extends ClieSock
    {
        private static final long serialVersionUID = 4356627172616184615L;
        private int NUM_FIELDS = 24;
        private int NUM_RECORDS = 2;
        
        private int recCount = 0;
        
        public ClieSockTest() throws Exception
        {
            
        }
        
        private String[] recs;
        
        
        @Override
        public int ids_search_view_get(final String s1, final String s2, final String[] s3, final int i1
                , final int[] viewFieldsCount, final int[] i3)
        {
            viewFieldsCount[0] = NUM_FIELDS;
            return 0; // success
        }
        
        @Override
        public synchronized int ids_search_layout
            ( final String arg0, final String arg1, final String arg2
             , final String[] searchViewFields
             , final int arg4, final int arg5
             , final int[] searchViewLengths, final int arg7
             , final int[] searchViewOffsets, final int arg9
             , final int[] searchViewRepeats, final int arg11
             , final String[] searchViewFormats
             , final int arg13, final int arg14
             )
        {
            int i = 0;
            searchViewFields[i] = "ID";
            searchViewLengths[i] = 19;   
            searchViewOffsets[i] = 0;
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "FORENAME";
            searchViewLengths[i] = 162;   
            searchViewOffsets[i] = 19;
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "LAST_NAME";
            searchViewLengths[i] = 80;   
            searchViewOffsets[i] = 181;        
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "FULL_NAME";
            searchViewLengths[i] = 242;   
            searchViewOffsets[i] = 261;   
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "DOC_TYPE";
            searchViewLengths[i] = 2;   
            searchViewOffsets[i] = 503;   
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "DOC_NO";
            searchViewLengths[i] = 70;   
            searchViewOffsets[i] = 505;
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "NATIONALITY";
            searchViewLengths[i] = 6;   
            searchViewOffsets[i] = 575;
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "BIRTH_DATE";
            searchViewLengths[i] = 64;   
            searchViewOffsets[i] = 581;  
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "BIRTH_PLACE";
            searchViewLengths[i] = 80;   
            searchViewOffsets[i] = 645;
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "GENDER";
            searchViewLengths[i] = 2;   
            searchViewOffsets[i] = 725;  
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "RESC_CODE";
            searchViewLengths[i] = 10;   
            searchViewOffsets[i] = 727; 
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "PROTOCOL_NUMBER";
            searchViewLengths[i] = 40;   
            searchViewOffsets[i] = 737; 
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "WATL_NAME";
            searchViewLengths[i] = 10;   
            searchViewOffsets[i] = 777;  
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "ACTC_CODE";
            searchViewLengths[i] = 10;   
            searchViewOffsets[i] = 787; 
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "BIRTH_CNTRY_CODE";
            searchViewLengths[i] = 6;   
            searchViewOffsets[i] = 797;  
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "VALID_UNTIL_DATE";
            searchViewLengths[i] = 64;   
            searchViewOffsets[i] = 803; 
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "BIRTH_DATE_TO";
            searchViewLengths[i] = 64;   
            searchViewOffsets[i] = 867;
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "BIRTH_DATE_FROM";
            searchViewLengths[i] = 64;   
            searchViewOffsets[i] = 931; 
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "CREATED_DATETIME";
            searchViewLengths[i] = 64;   
            searchViewOffsets[i] = 995; 
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "CREATED_BY";
            searchViewLengths[i] = 50;   
            searchViewOffsets[i] = 1059;
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "MODIFIED_DATETIME";
            searchViewLengths[i] = 64;   
            searchViewOffsets[i] = 1109;
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "MODIFIED_BY";
            searchViewLengths[i] = 50;   
            searchViewOffsets[i] = 1173;
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "DOC_TYPE_NO";
            searchViewLengths[i] = 72;
            searchViewOffsets[i] = 1223;
            searchViewFormats[i] = "";
            i++;
            searchViewFields[i] = "CL_ID";
            searchViewLengths[i] = 2;
            searchViewOffsets[i] = 1295;
            searchViewFormats[i] = "";
            
            /*
             * This is taken from debug output when doing a Target search.
             */
            /*
                fieldName(ID) length(19) offset(0) repeat(1) format(RV30T10N0000NN)
                fieldName(FORENAME) length(162) offset(19) repeat(1) format(LV20T00T0000WW)
                fieldName(LAST_NAME) length(80) offset(181) repeat(1) format(LV20T00T0000WW)
                fieldName(FULL_NAME) length(242) offset(261) repeat(1) format(LV20T00T0000WW)
                fieldName(DOC_TYPE) length(2) offset(503) repeat(1) format(LV20T00T0000WW)
                fieldName(DOC_NO) length(70) offset(505) repeat(1) format(LV20T00T0000WW)
                fieldName(NATIONALITY) length(6) offset(575) repeat(1) format(LV20T00T0000WW)
                fieldName(BIRTH_DATE) length(64) offset(581) repeat(1) format(LV20T00T0000WW)
                fieldName(BIRTH_PLACE) length(80) offset(645) repeat(1) format(LV20T00T0000WW)
                fieldName(GENDER) length(2) offset(725) repeat(1) format(LV20T00T0000WW)
                fieldName(RESC_CODE) length(10) offset(727) repeat(1) format(LV20T00T0000WW)
                fieldName(PROTOCOL_NUMBER) length(40) offset(737) repeat(1) format(LV20T00T0000WW)
                fieldName(WATL_NAME) length(10) offset(777) repeat(1) format(LV20T00T0000NC)
                fieldName(ACTC_CODE) length(10) offset(787) repeat(1) format(LV20T00T0000NC)
                fieldName(BIRTH_CNTRY_CODE) length(6) offset(797) repeat(1) format(LV20T00T0000WW)
                fieldName(VALID_UNTIL_DATE) length(64) offset(803) repeat(1) format(LV20T00T0000NC)
                fieldName(BIRTH_DATE_TO) length(64) offset(867) repeat(1) format(LV20T00T0000NC)
                fieldName(BIRTH_DATE_FROM) length(64) offset(931) repeat(1) format(LV20T00T0000NC)
                fieldName(CREATED_DATETIME) length(64) offset(995) repeat(1) format(LV20T00T0000NC)
                fieldName(CREATED_BY) length(50) offset(1059) repeat(1) format(LV20T00T0000NC)
                fieldName(MODIFIED_DATETIME) length(64) offset(1109) repeat(1) format(LV20T00T0000NC)
                fieldName(MODIFIED_BY) length(50) offset(1173) repeat(1) format(LV20T00T0000NC)
                fieldName(DOC_TYPE_NO) length(72) offset(1223) repeat(1) format(LV20T00T0000WW)
                fieldName(CL_ID) length(2) offset(1295) repeat(1) format(LV20T00T0000NC)
             */
            
            return 0; // success
        }
        
        
        @Override
        public int ids_search_filter(final java.lang.String arg0, final java.lang.String arg1)
        {
            return 0; // success
        }

        @Override
        public int ids_search_start(final java.lang.String arg0, final java.lang.String arg1, final java.lang.String arg2
                , final byte[][] arg3, final byte[] arg4, final int arg5, final java.lang.String arg6
                , final int[] outputRecCount
                , final byte[][] arg8
                ) 
        {
            this.recCount = 0;
            outputRecCount[0] = NUM_RECORDS;
            
            recs = new String[3];
            recs[0] = "0000000000000000058MICHAEL                                                                                                                                                           BERRY                                                                           MICHAEL BERRY                                                                                                                                                                                                                                     U 335695                                                                NGA   07-DEC-1925                                                     LAGOS                                                                           M 100       2222222268                              TERRORISM NOEX      NGA   03-JUN-2011                                                                                                                                                                                     03-FEB-2011                                                     SUP                                                                                                                                                                 U 335695                                                                  ";
            recs[1] = "0000000000000000057MARIA DEL PILAR                                                                                                                                                   HUESOMUNOZ                                                                      MARIA DEL PILAR HUESOMUNOZ                                                                                                                                                                                                                        P BD100002                                                              ESP                                                                   BARCELONA                                                                       F 001       1234                                    DRUGTFK   ARST      ESP   10-APR-2011                                                     01-JAN-1975                                                     01-JAN-1975                                                     10-MAR-2011                                                     CANOOSUP                                                                                                                                                            P BD100002                                                                ";
            recs[2] = "0000000000000000057MARIA DEL PILAR                                                                                                                                                   HUESOMUNOZ                                                                      MARIA DEL PILAR HUESOMUNOZ                                                                                                                                                                                                                        P BD100002                                                              ESP                                                                   BARCELONA                                                                       F 001       1234                                    DRUGTFK   ARST      ESP   10-APR-2011                                                     01-JAN-1975                                                     01-JAN-1975                                                     10-MAR-2011                                                     CANOOSUP                                                                                                                                                            P BD100002                                                                ";

            return 0; // success record count
        }
        
        @Override
        public int ids_search_get
            ( final java.lang.String arg0
            , final byte[] searchRec
            , final int searchRecLen
            , final int[] score
            , final int[] arg4, final int arg5, final int[] arg6, final int arg7
            )
        {
            score[0] = 95;
            
            // Taken from debug output on Target Search (IIR Data)

            byte[] recByte = recs[recCount].getBytes();
            for (int i=0; i < searchRecLen; i++)
                searchRec[i] = recByte[i];
            
            recCount++;
            return (NUM_RECORDS >= this.recCount ? 0 : -1);
        }
        

        @Override
        public int ids_search_finish(final java.lang.String arg0)
        {
            return 0; // success
        }

    }
    

    
    public IIRSearchImplTest()
    {
        super();
    }

    @Before
    public void setUp() throws Exception
    {
        context = new Mockery() {{
            setImposteriser(ClassImposteriser.INSTANCE);
        }};
        
        cp = context.mock(IIRConnectionPool.class);
        con = context.mock(IIRConnection.class);
        clieSock = new ClieSockTest();
        
        setupConPool();
        iirSearch = new IIRSearchImpl(cp);
    }



    private void setupConPool() throws Exception
    {
        context.checking(new Expectations() {
        {   
            atLeast(0).of(cp).allocateConnection(IIRSearchType.INTERNATIONAL.getVal(), true);
            will(returnValue(con));

            atLeast(0).of(cp).releaseConnection(with(any(IIRConnection.class)));
            
            atLeast(0).of(cp).isPrimaryUp();
            will(returnValue(true));
        }  
        });
    }
    
    private void setupCon(final ClieSock cs) throws Exception
    {
        context.checking(new Expectations() {
        {
            atLeast(0).of(con).connect();
            atLeast(0).of(con).disconnect();
            atLeast(0).of(con).getSocket();
            will(returnValue(cs));
            atLeast(0).of(con).setFilter(with(any(String.class)));
            will(returnValue(0));
        }  
        });
    }
    

    @Test
    public void testSimpleMatch() throws Exception
    {
        setupCon(clieSock);
        
        IIRSearchRequestImpl req = new IIRSearchRequestImpl();
        req.getCriteria().put(IIRSearchFieldType.FORENAMES, "JOE");
        req.getCriteria().put(IIRSearchFieldType.LAST_NAME, "SMITH");
        req.setMatch(true);
        req.setIirSearchType(IIRSearchType.INTERNATIONAL);
        
        IIRSearchResponse resp = iirSearch.search(req);
        
        assertNotNull(resp);
        assertNotNull(resp.getTargetList());
        assertNotNull(resp.getTargetList().size() == 1);
    }

    @Test
    public void testSimpleSearch() throws Exception
    {
        setupCon(clieSock);
        
        IIRSearchRequestImpl req = new IIRSearchRequestImpl();
        req.getCriteria().put(IIRSearchFieldType.FORENAMES, "JOE");
        req.getCriteria().put(IIRSearchFieldType.FORENAMES, "SMITH");
        req.getCriteria().put(IIRSearchFieldType.WATCHLIST_NAME, "TEST");
        req.getCriteria().put(IIRSearchFieldType.BIRTH_DATE_FROM, Calendar.getInstance());
        req.getCriteria().put(IIRSearchFieldType.BIRTH_DATE_TO, Calendar.getInstance());
        req.getCriteria().put(IIRSearchFieldType.SEVERITY_LEVEL, 2);
        req.setMatch(false);
        req.setIirSearchType(IIRSearchType.INTERNATIONAL);
        
        IIRSearchResponse resp = iirSearch.search(req);
        
        assertNotNull(resp);
        assertNotNull(resp.getTargetList());
        assertNotNull(resp.getTargetList().size() == 1);
    }
    
    @Test
    public void testMatch() throws Exception
    {
        setupCon(clieSock);
        
        IIRSearchRequestImpl req = new IIRSearchRequestImpl();
        req.getCriteria().put(IIRSearchFieldType.FORENAMES, "JOE");
        req.getCriteria().put(IIRSearchFieldType.FORENAMES, "SMITH");
        req.getCriteria().put(IIRSearchFieldType.VALID_UNTIL_DATE, Calendar.getInstance());
        req.getCriteria().put(IIRSearchFieldType.BIRTH_DATE_FROM, Calendar.getInstance());
        req.getCriteria().put(IIRSearchFieldType.BIRTH_DATE_TO, Calendar.getInstance());
        req.setMatch(true);
        req.setIirSearchType(IIRSearchType.INTERNATIONAL);
        
        IIRSearchResponse resp = iirSearch.search(req);
        
        assertNotNull(resp);
        assertNotNull(resp.getTargetList());
        assertNotNull(resp.getTargetList().size() == 1);
    }
    
    /*
    public void testExceptionFrom_ids_search_layout() throws Exception
    {    
        clieSockMock = context.mock(ClieSock.class);
        
        setupCon(clieSockMock);
        
        context.checking(new Expectations() {
            {
                one(clieSock).ids_search_start
                    ( with(any(String.class)), with(any(String.class)), with(any(String.class))
                    , with(any(byte[][].class)), with(any(byte[].class)), with(any(int.class))
                    , with(any(String.class)), with(any(int[].class))
                    , with(any(byte[][].class))
                    );
                will(throwException(with(any(SSAException.class))));
                
                atLeast(0).of(clieSock).ids_search_layout
                    ( with(any(String.class)), with(any(String.class)), with(any(String.class))
                    , with(any(String[].class)), with(any(int.class)), with(any(int.class))
                    , with(any(int[].class)), with(any(int.class))
                    , with(any(int[].class)), with(any(int.class))
                    , with(any(int[].class)), with(any(int.class))
                    , with(any(String[].class))
                    , with(any(int.class)), with(any(int.class))
                    );
            }
        });
        
        
        IIRSearchRequest req = new IIRSearchRequestImpl();
        req.getCriteria().put(IIRSearchFieldType.FORENAMES, "JOE");
        req.getCriteria().put(IIRSearchFieldType.FORENAMES, "SMITH");
        req.getCriteria().put(IIRSearchFieldType.VALID_UNTIL_DATE, Calendar.getInstance());
        req.getCriteria().put(IIRSearchFieldType.BIRTH_DATE_FROM, Calendar.getInstance());
        req.getCriteria().put(IIRSearchFieldType.BIRTH_DATE_TO, Calendar.getInstance());
        req.setMatch(true);

        IIRSearchResponse resp;
        try
        {
            resp = iirSearch.search(req);
            
            assertNotNull(resp);
            assertNotNull(resp.getTargetList());
            assertNotNull(resp.getTargetList().size() == 1);

*/
    @Test
    public void testUnicodeEscape() throws Exception
    {
        String arabic = "\u0639\u0645\u0627\u0631"; // java unicode escapes from native2ascii

        IIRSearchImpl req = new IIRSearchImpl();
        assertEquals("\\0639\\0645\\0627\\0631", req.getUnicodeEscapedStr(arabic).toString()); 
    }
}
