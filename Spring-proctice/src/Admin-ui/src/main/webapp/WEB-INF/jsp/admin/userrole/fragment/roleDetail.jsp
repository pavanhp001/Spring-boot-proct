<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/taglib/UserRole.tld" prefix="u"%>

        <form:hidden path="action" />

        <!-- Show asterisk mandatory fields -->
        <c:set var="showStar" value="true" scope="request"/>

        <jsp:include page="/WEB-INF/jsp/fragment/pageErrors.jsp"/>

        <div id="searchFormDiv">
            <div id="searchCriteria">
                <jsp:include page="/WEB-INF/jsp/admin/userrole/fragment/roleFields.jsp" />
                <br />
                <br />
                <jsp:include page="/WEB-INF/jsp/admin/userrole/fragment/rolePermissions.jsp" />

                <fieldset class="oneCol">
                    <div class="searchControl">
                        <c:if test="${userRoleCommand.action == 'addUserRole'}" >  
                            <button class="textButton" type="submit" id="add" name="addUserRole" >
                                <fmt:message key="button.add" />
                            </button>
                            <button class="textButton" type="submit" id="cancel" name="cancel">
                                <fmt:message key="cancel" />
                            </button>      
                        </c:if>
                        <c:if test="${userRoleCommand.action == 'viewUserRole'}" >  
                            <button class="textButton" type="submit" id="edit" name="editUserRole" >
                                <fmt:message key="button.edit" />
                            </button>  
                            <button class="textButton" type="submit" id="close" name="close">
                                <fmt:message key="button.close" />
                            </button>         
                        </c:if>     
                        <c:if test="${userRoleCommand.action == 'editUserRole'}" >  
                            <button class="textButton" type="submit" id="edit" name="saveUserRole" >
                                <fmt:message key="button.save" />
                            </button>   
                            <button class="textButton" type="submit" id="cancel" name="cancel">
                                <fmt:message key="button.cancel" />
                            </button>    
                        </c:if>  

                        <button class="hideOnLoad" type="submit" id="reset" name="reset">
                            <fmt:message key="reset" />
                        </button>
                    </div>
                </fieldset>

            </div>
        </div>

