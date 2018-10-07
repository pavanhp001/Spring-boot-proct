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

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<label class="helpinfolabel">
    <fmt:message key="target.remarks" />
</label>

<c:set var="disableItem" value="${disableInputFields}" />
<c:if test="${readonlyInputFields == true}">
    <c:set var="disableItem" value="${readonlyInputFields}" />
</c:if>

<div>
    <form:textarea path="remarks" readonly="${disableItem}" onmouseout="limitText(this, 500);" 
        onblur = "limitText(this, 500);" rows="4" data-maxsize="500" />
</div>
