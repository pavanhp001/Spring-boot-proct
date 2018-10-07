<!--
/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2012
 * All rights reserved.
 */
-->

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:set var="repeatSeriesFlight">
    <fmt:message key="repeat.type.S"/>
</c:set>
<c:set var="repeatSpecificFlight">
    <fmt:message key="repeat.type.F"/>
</c:set>

<c:set var="disableItem" value="${disableInputFields}" />
<label class="helpinfolabel">
    <fmt:message key="repeat.type"/>
</label>

<table>
    <tr>
        <td class="labelAndCheck" title="${repeatSeriesFlight}"><form:radiobutton
                path="repeatType" id="repeatSeriesFlight" value="S"
                title="${repeatSeriesFlight}" disabled="${disableItem}"/> <label class="radioLabel">${repeatSeriesFlight}</label>
        </td>
        <td class="labelAndCheck" title="${repeatSpecificFlight}"><form:radiobutton
                path="repeatType" id="repeatSpecificFlight" value="F"
                title="${repeatSpecificFlight}" disabled="${disableItem}"/> <label class="radioLabel">${repeatSpecificFlight}</label>
        </td>
    </tr>
</table>
