<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:set var="recurrenceTypeErrors"><form:errors path="recurrenceType"/></c:set>

<c:set var="daily">
    <fmt:message key="schedule.recurrenceType.daily" />
</c:set>

<c:set var="weekly">
    <fmt:message key="schedule.recurrenceType.weekly" />
</c:set>

<c:set var="timeInterval">
    <fmt:message key="schedule.recurrenceType.timeInterval" />
</c:set>


<c:set var="disableItem" value="${disableInputFields}" />

<label class="helpinfolabel">
    <fmt:message key="repeat.pattern"/>
</label>

<table>
<tr>
<td class="labelAndCheck" title="${daily}">
    <form:radiobutton path="recurrenceType" id="recurrenceTypeDaily" value="DAILY"
        title="${sameDay}"/>
    <label class="radioLabel">${daily}</label>

</td>

<td class="labelAndCheck" title="${weekly}">
    <form:radiobutton path="recurrenceType" id="recurrenceTypeWeekly" value="WEEKLY"
        title="${weekly}"/>
    <label class="radioLabel">${weekly}</label>
</td>

<td class="labelAndCheck" title="${timeInterval}">
    <form:radiobutton path="recurrenceType" id="recurrenceTypeTimeInterval" value="TIMEINTERVAL"
        title="${timeInterval}"/>
    <label class="radioLabel">${timeInterval}</label>
</td>

</tr></table>

