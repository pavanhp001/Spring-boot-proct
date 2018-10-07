<%--
    Basic tiles page that inserts a header and a body and is intended to be mainly used with BasicPage.jsp.

    The required Tiles attributes are:
        header - what to use as the header
        body - what to use as the body
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/UserRole.tld" prefix="u" %>


<c:set var="pdfPrintUrl" scope="request"><tiles:insertAttribute name="pdf.print.url" /></c:set>
<c:set var="xlsExportUrl" scope="request"><tiles:insertAttribute name="xls.export.url" /></c:set>

<c:choose>
    <c:when test="${pageContext.servletContext.contextPath eq '/audit'}">
        <c:set var="helppage" value="audit_csh.htm" scope="request"/>
        <c:set var="helpUrl" value="/help/audit/" scope="request"/>
    </c:when>
    <c:when test="${pageContext.servletContext.contextPath eq '/admin'}">
        <c:set var="helppage" value="admin_csh.htm" scope="request"/>
        <c:set var="helpUrl" value="/help/admin/" scope="request"/>
    </c:when>
    <c:when test="${pageContext.servletContext.contextPath eq '/wi'}">
        <c:set var="helppage" value="wi_csh.htm" scope="request"/>
        <c:set var="helpUrl" value="/help/wi/" scope="request"/>
    </c:when>
    <c:when test="${pageContext.servletContext.contextPath eq '/home'}">
        <c:set var="helppage" value="admin_csh.htm" scope="request"/>
        <c:set var="helpUrl" value="/help/home/" scope="request"/>
    </c:when>
    <c:otherwise>
        <!-- help file remains as specified in common ui tilesdef -->
        <c:set var="helppage" scope="request"><tiles:insertAttribute name="help.page" /></c:set>
        <c:set var="helpUrl" scope="request"><tiles:insertAttribute name="help.url" /></c:set>
    </c:otherwise>
</c:choose>
<c:set var="helpContext" scope="request"><tiles:insertAttribute name="help.context" /></c:set>

<%-- Message board display and supporting Javascript --%>
<div id="urgentMsgs">
</div>

<div id="normalMsgs">
</div>

<script type="text/javascript">

     function closeDialog(targetDiv) {
        targetDiv.dialog("close");
     }

     function setAccessCookie(cookieName, path) {
        var currentDate = new Date();
        var lastAccess = currentDate.getTime();
        var cookieExpiry = new Date(lastAccess + (365 * 24 * 60 * 60 * 1000)); /* 1 year ahead */
        document.cookie=cookieName + "=" + lastAccess + ";expires=" + cookieExpiry.toGMTString() + ";path=/" + path + "/";
     }

     function viewAlert(targetDiv) {
        closeDialog(targetDiv);
        window.location="/irisk/iRiskActiveAlerts.form";
     }

     function viewMsg(targetDiv) {
        closeDialog(targetDiv);
        window.open("/home/messageBoard.form");
     }

     function newMessage(type, msg, link) {

       var targetMsgDivId ;

       if(type == 'urgent') {
         targetMsgDivId = '#urgentMsgs';
       } else {
         targetMsgDivId = '#normalMsgs';
       }

       var cookieName;
       var cookiePath;
       var viewFunc;

       var targetMsgDiv = $(targetMsgDivId);
       createDialog('', 90, 350, targetMsgDivId);

       if(type == 'urgent') {
         cookieName = 'lastAlertCheck';
         cookiePath = 'irisk';
         viewFunc = viewAlert;
       } else {
         cookieName = 'mbacctime';
         cookiePath = 'home';
         viewFunc = viewMsg;
       }

       /* Note that there is an issue with Firefox - the dialog should be created in the "document ready" section.
          but this would involve delaying the iframe init until after dialog registered - todo */
      targetMsgDiv.dialog('option', 'buttons', { '<fmt:message key="cancel"/>': function() { $(this).dialog("close") }, '<fmt:message key="view"/>': function() { viewFunc(targetMsgDiv); } });

     targetMsgDiv.dialog('option', 'show', 'show');
     targetMsgDiv.text(msg);
     targetMsgDiv.bind('dialogbeforeclose', function(event, ui) {
        setAccessCookie(cookieName, cookiePath);
     });

     targetMsgDiv.dialog("open");

  }


</script>

<!-- HEADER: <tiles:getAsString name="page.header"/>-->
<tiles:insertAttribute name="page.header"/>
<!-- BODY: <tiles:getAsString name="page.body"/> -->
<tiles:insertAttribute name="page.body"/>

<script type="text/javascript">
<!--
function getBrowserLanguage() {
    return '<c:out value="${fn:toUpperCase(pageContext.request.locale.language)}"/>';
}

function getRootHelpPage() {
    var page = '<c:out value="${helppage}" />';
    return page;
}

<%-- Register and close child windows on exit - implemented as a callback from child to avoid having to change all the links in the pages --%>
var childWindows = [];
var childWindowCount = 0;

function registerChildWindow(windref) {
      childWindows[childWindowCount++] = windref;
}

function closeChildWindows() {
      while(--childWindowCount >= 0) {
         childWindows[childWindowCount].close();
      }
}

$(document).ready(function(){

    <%-- When the page is unloaded, close all registered child windows --%>
    $(window).unload( function () { closeChildWindows() } );

    var rowHoverColour;
    var rowSelectedHoverColour;

      var rules = document.styleSheets[0].rules || document.styleSheets[0].cssRules;
       for (var i=0; i < rules.length; i++) {
          var cssrule = rules[i];

          var text = cssrule.selectorText.toLowerCase(); <%-- browsers can change the case of these, so normalise --%>

          <%-- The standard says to use rule.style.getPropertyValue, but IE doesn't support it. This method supported IE and FF --%>
          if(text == ".rowhover") {
              rowHoverColour = cssrule.style['backgroundColor'];
          } else if(text == ".rowselectedhover") {
              rowSelectedHoverColour = cssrule.style['backgroundColor'];
          }
       }

    $('TR.results, TR.results2').hover(
        function() {
           var colour = $(this).is('tr.results2') ? rowSelectedHoverColour : rowHoverColour;
           $(this).css('background-color',colour);
        },
        function() {
           $(this).css('background-color', '');
        }
    );

    $('TR.resultsbold, TR.resultsbold2').hover(
        function() {
           var colour = $(this).is('tr.resultsbold2') ? rowSelectedHoverColour : rowHoverColour;
           $(this).css('background-color',colour);
        },
        function() {
           $(this).css('background-color', '');
        }
    );

     <%-- Message board related --%>
     $('#msgBoardClose').click(function() {$('#messageBoard').hide();});

    <%-- Set the default date format and locale for the date picker -->
    jQuery.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>'] = {dateFormat: 'dd-mm-yy', firstDay: 0, speed: '', yearRange: '-5:+0'};
    jQuery.datepicker.setDefaults(jQuery.datepicker.regional['<c:out value="${pageContext.request.locale.language}"/>']);

      <%-- jQuery Date Picker Related --%>
         //Setting styles for date calender image icon
        $('iframe.ui-datepicker-cover').css({'display':'none'});
        $('.ui-datepicker-cover').css({'display':'none'});
        $("button.ui-datepicker-trigger").css({'padding' : '0px', 'border' : '0px', 'margin-left' : '3px', 'margin-top' : '0px','margin-bottom' : '-2px', 'cursor':'hand'});
        $("button.airport").css({'padding' : '0px', 'border' : '0px', 'margin-left' : '3px', 'margin-top' : '0px','margin-bottom' : '0px', 'cursor':'hand'});
        $('button.ui-datepicker-trigger img').css({'width':'14px', 'height':'15px', 'border' : '0px', 'padding' : '0px'});
        $('button.airport img').css({'width':'16px', 'height':'16px', 'border' : '0px', 'padding' : '0px'});
        $('button.ui-datepicker-trigge').attr({'width':'14px', 'height':'15px', 'border' : '0px', 'padding' : '0px'});
        $('button.ui-datepicker-trigge img').attr({'width':'14px', 'height':'15px', 'border' : '0px', 'padding' : '0px'});
        $('button.airport').attr({'width':'16px', 'height':'16px'});
        $('button.airport img').attr({'width':'16px', 'height':'16px'});
        //Swith focus to date picker dailog when hit enter key on date button
        $('button.ui-datepicker-trigger').keydown(function(e) {
            if(e.keyCode == 9){
                //$.datepicker._hideDatepicker();
                $('td a.ui-state-default').filter(':first').attr('id','dpStartDate');
                $('td a.ui-state-default').filter(':last').attr('id','dpEndDate');
                $('td a.ui-state-default').filter(':first').focus();
        }
            return true;
        });
        //Tab navigation on date picker
        $('td a.ui-state-default').live('keydown', function(event){
        var target = $(event.target);

            if( (event.keyCode == 9 || event.keyCode == 16 ) && target.attr('id') == 'dpEndDate'){
                //$.datepicker._hideDatepicker();
                $('a.ui-datepicker-next.ui-corner-all').click();
                $('td a.ui-state-default').filter(':first').attr('id','dpStartDate');
                $('td a.ui-state-default').filter(':last').attr('id','dpEndDate');
                //$('td a.ui-state-default:first').click();
                $('td a.ui-state-default:first').focus();
            }else if((event.keyCode == 9 || event.keyCode == 16 ) && target.attr('id') == 'dpStartDate'){
                $('a.ui-datepicker-prev.ui-corner-all').click();
                $('td a.ui-state-default').filter(':first').attr('id','dpStartDate');
                $('td a.ui-state-default').filter(':last').attr('id','dpEndDate');
                //$('td a.ui-state-default:last').click();
                $('td a.ui-state-default:last').focus();
            }
            return true;
        });

});
//-->
</script>

<%-- The IFRAME to check the message board for new messages --%>
<u:checkRole roleGroup="ACTIVE_ALERTS_MENU_ITEM">
   <IFRAME height="0" width="0" scrolling="no" frameborder="0" src="/irisk/iRiskNewAlert.popup"></IFRAME>
</u:checkRole>

<IFRAME height="0" width="0" scrolling="no" frameborder="0" src="/home/messageBoard.popup"></IFRAME>
