<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="sunday">
    <fmt:message key="schedule.weeklyRecurrence.sunday" />
</c:set>
<c:set var="monday">
    <fmt:message key="schedule.weeklyRecurrence.monday" />
</c:set>
<c:set var="tuesday">
    <fmt:message key="schedule.weeklyRecurrence.tuesday" />
</c:set>
<c:set var="wednesday">
    <fmt:message key="schedule.weeklyRecurrence.wednesday" />
</c:set>
<c:set var="thursday">
    <fmt:message key="schedule.weeklyRecurrence.thursday" />
</c:set>
<c:set var="friday">
    <fmt:message key="schedule.weeklyRecurrence.friday" />
</c:set>
<c:set var="saturday">
    <fmt:message key="schedule.weeklyRecurrence.saturday" />
</c:set>

<c:choose>
    <c:when test="${not empty daysOfWeekErrors}">
        <label class="formError"> <fmt:message key="days.of.week" />
        </label>
    </c:when>
    <c:otherwise>
        <label> <fmt:message key="days.of.week" />
        </label>
    </c:otherwise>
</c:choose>

<table>
<tr>
<td class="labelAndCheckWeekDays" title="${sunday}">
    <form:checkbox path="sundayFlag" value="false"
        title="${weekly}" disabled="${disableItem}" />
    <label class="radioLabel">${sunday}</label>
</td>
<td class="labelAndCheckWeekDays" title="${monday}">
    <form:checkbox path="mondayFlag" value="false"
        title="${weekly}" disabled="${disableItem}" />
    <label class="radioLabel">${monday}</label>
</td>
<td class="labelAndCheckWeekDays" title="${tuesday}">
    <form:checkbox path="tuesdayFlag" value="false"
        title="${tuesday}" disabled="${disableItem}" />
    <label class="radioLabel">${tuesday}</label>
</td>
<td class="labelAndCheckWeekDays" title="${wednesday}">
    <form:checkbox path="wednesdayFlag" value="false"
        title="${wednesday}" disabled="${disableItem}" />
    <label class="radioLabel">${wednesday}</label>
</td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
<td class="labelAndCheckWeekDays" title="${thursday}">
    <form:checkbox path="thursdayFlag" value="false"
        title="${weekly}" disabled="${disableItem}" />
    <label class="radioLabel">${thursday}</label>
</td>
<td class="labelAndCheckWeekDays" title="${friday}">
    <form:checkbox path="fridayFlag" value="false"
        title="${friday}" disabled="${disableItem}" />
    <label class="radioLabel">${friday}</label>
</td>
<td class="labelAndCheckWeekDays" title="${saturday}">
    <form:checkbox path="saturdayFlag" value="false"
        title="${saturday}" disabled="${disableItem}" />
    <label class="radioLabel">${saturday}</label>
</td>
</tr>
<tr><td>&nbsp;</td></tr></table>
