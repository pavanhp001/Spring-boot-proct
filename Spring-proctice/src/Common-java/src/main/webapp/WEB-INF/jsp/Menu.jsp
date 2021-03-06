<%--
THIS IS MENU LAYOUT
ALL MENU ITEMS SHOULD BE ADD HERE
--%>
<jsp:useBean id="bcsSystemProperties" class="abc.xyz.pts.bcs.common.util.BcsSystemProperties" scope="application" />
<%@ include file="/WEB-INF/jsp/include.jsp" %>
    <div class="headerNav">     
        <div class="headerMenu">
              <div class="menu">
                  <ul class="menuList">
                     <u:checkRole roleGroup="APP_ACCESS">
                          <li class="menuItem"><fmt:message key="menu.preclearance"/>
                            <ul class="submenu">
                              <u:checkRole roleGroup="APP_READ">
                                  <li><a href="/app/checkInSearch.form"><fmt:message key="checkin.search"/></a></li>
                                  <li><a href="/app/movementSearch.form"><fmt:message key="movement.search"/></a></li>
                              </u:checkRole>
                              <u:checkRole roleGroup="APP_R_ORS">
                                  <li><a href="/app/overrideSearch.form"><fmt:message key="override.search"/></a></li>
                              </u:checkRole>
                              <u:checkRole roleGroup="APP_RPT_BILLING">
                                 <li><a href="/app/billingSearch.form"><fmt:message key="billing"/></a></li>
                              </u:checkRole>
                              <u:checkRole roleGroup="APP_RPT_STATISTICS">
                                 <li><a href="/app/statisticsSearch.form"><fmt:message key="statistics"/></a></li>
                              </u:checkRole>
                            </ul>
                      </u:checkRole>
                      <u:checkRole roleGroup="RISK_MENU">
                          <li class="menuItem"><fmt:message key="menu.risks"/>
                            <ul class="submenu">
                               <u:checkRole roleGroup="RISK_ALERTS_SEARCH_ITEM">
                                  <li><a href="/irisk/iRiskReferralSearch.form"><fmt:message key="menu.risks.referrallist"/></a></li>
                               </u:checkRole>
                               <u:checkRole roleGroup="REFERRAL_INTERVENE">
                                  <li><a href="/irisk/activeReferral.form"><fmt:message key="menu.risks.active.referrallist"/></a></li>
                               </u:checkRole>
                               <u:checkRole roleGroup="REFERRAL_UNQUALIFIED_READ">
                                  <li><a href="/irisk/unknownHitReferrals.form"><fmt:message key="menu.risks.unknown.hit.referrallist"/></a></li>
                               </u:checkRole>
                               <u:checkRole roleGroup="RISK_SEARCH_TARGET_ITEM">
                                   <li><a href="/wi/viewTargets.form"><fmt:message key="menu.risks.targetlist"/></a></li>
                               </u:checkRole>
                               <u:checkRole roleGroup="RISK_ADD_TARGET_ITEM">
                                   <li><a href="/wi/addTarget.form"><fmt:message key="menu.risks.addtarget"/></a></li>
                               </u:checkRole>
                               <u:checkRole roleGroup="RISK_IMPORT_TARGET_ITEM">
                                   <li><a href="/wi/importTargetSummary.form"><fmt:message key="menu.risks.importTargetSummary"/></a></li>
                               </u:checkRole>                               
                            </ul>
                          </li>
                      </u:checkRole>
                      <u:checkRole roleGroup="TRAVEL_DATA_MENU">
                        <li class="menuItem"><a href="#"><span><fmt:message key="menu.traveldata"/></span></a>
                              <ul class="submenu">
                                 <c:set var="showTravMenuItems" value="false" />
                                 <u:checkRole roleGroup="TRAVEL_DATA_FLT_TRV_ITEM">
                                    <c:set var="showTravMenuItems" value="true" />
                                 </u:checkRole>
                                 <u:checkRole roleGroup="VIEW_TRAVEL_DATA">
                                    <c:set var="showTravMenuItems" value="true" />
                                 </u:checkRole>
                 
                                 <c:if test="${showTravMenuItems}">
                                    <li><a href="/idetectdb/flightSearch.form"><fmt:message key="menu.traveldata.flights"/></a></li>
                                    <li><a href="/idetectdb/travellerSearch.form"><fmt:message key="menu.traveldata.travellers"/></a></li>
                                 </c:if>
                              </ul>
                        </li>
                      </u:checkRole>
                      <u:checkRole roleGroup="CPR_MENU">
                         <li class="menuItem"><a href="#"><span><fmt:message key="menu.cpr"/></span></a>
                               <ul class="submenu">
                                       <u:checkRole roleGroup="ADMIN_SEARCH_ROUTE">
                                       <li><a href="/cpr/ruleSearch.form"><fmt:message key="menu.cpr.rulesearch"/></a></li>
                                    </u:checkRole>
                                    <u:checkRole roleGroup="ADMIN_ADD_ROUTE">
                                         <li><a href="/cpr/routeAdd.form"><fmt:message key="menu.cpr.addrule"/></a></li>
                                    </u:checkRole>
                                </ul>
                            </li>
                    </u:checkRole>
                    <u:checkRole roleGroup="ADMIN_MENU">
                          <li class="menuItem"><fmt:message key="menu.admin"/>
                            <ul class="submenu">
                               <u:checkRole roleGroup="ADMIN_SEARCH_USER_ITEM">
                                  <li><a href="/admin/adminUserSearch.form"><fmt:message key="menu.admin.userlist"/></a></li>
                               </u:checkRole>
                               <u:checkRole roleGroup="ADMIN_ADD_USER_ITEM">
                                  <li><a href="/admin/adminUserAdd.form"><fmt:message key="menu.admin.adduser"/></a></li>
                               </u:checkRole>
                               <u:checkRole roleGroup="ADMIN_VIEW_USER_AUD_ITEM">
                                   <li><a href="/audit/auditSearch.form"><fmt:message key="menu.admin.audituserevents"/></a></li>
                               </u:checkRole>
                               <u:checkRole roleGroup="ADMIN_ADD_USER_ITEM">
                                   <li><a href="/admin/userRoleList.form"><fmt:message key="admin.user.role.list"/></a></li>
                                   <li><a href="/admin/addUserRole.form"><fmt:message key="admin.user.role.add"/></a></li>
                               </u:checkRole>
                               <u:checkRole roleGroup="ADMIN_ADD_ACTION_CODE">
                                   <li><a href="/wi/addActionCode.form"><fmt:message key="menu.admin.addactioncode"/></a></li>
                               </u:checkRole>
                               
                               <!--  SEARCH Action Codes  -->
                               <u:checkRole roleGroup="ADMIN_SEARCH_ACTION_CODE">
                                   <li><a href="/wi/searchActionCode.form"><fmt:message key="menu.admin.searchactioncode"/></a></li>
                               </u:checkRole>

                               <u:checkRole roleGroup="ADMIN_ADD_REASON_CODE">
                                   <li><a href="/wi/addReasonCode.form"><fmt:message key="menu.admin.add.reason.code"/></a></li>
                               </u:checkRole>  
                               <u:checkRole roleGroup="ADMIN_SEARCH_REASON_CODE">
                                   <li><a href="/wi/searchReasonCode.form"><fmt:message key="menu.admin.search.reason.code"/></a></li>
                               </u:checkRole>
                                <u:checkRole roleGroup="ADMIN_ADD_WATCHLIST">
                                   <li><a href="/wi/addWatchListName.form"><fmt:message key="menu.admin.add.watchlist.name"/></a></li>
                               </u:checkRole>
                                <u:checkRole roleGroup="ADMIN_SEARCH_WATCHLIST">
                                   <li><a href="/wi/searchWatchListName.form"><fmt:message key="menu.admin.search.watchlist.name"/></a></li>
                               </u:checkRole>
                                 <u:checkRole roleGroup="ADMIN_ADD_SCHEDULE">
                                     <li><a href="/scheduler/addSchedule.form"><fmt:message key="menu.admin.schedule.add"/></a></li>
                                 </u:checkRole>
                                 <u:checkRole roleGroup="ADMIN_SEARCH_SCHEDULE">
                                     <li><a href="/scheduler/searchSchedule.form"><fmt:message key="menu.admin.schedule.search"/></a></li>
                                 </u:checkRole>
                            </ul>
                          </li>
                      </u:checkRole>
                      <u:checkRole roleGroup="REPORT_MENU">
                         <li class="menuItem"><a href="#"><span><fmt:message key="menu.reports"/></span></a>
                               <ul class="submenu">
                                    <u:checkRole roleGroup="CRYSTAL_REPORTS">
                                       <li><a target="_blank" href="${bcsSystemProperties['crystal.reports.server.url']}"><fmt:message key="menu.crystal.reports"/></a></li>
                                    </u:checkRole>
                                </ul>
                            </li>
                     </u:checkRole>
                      <li class="menuItem">(${userProfileKey.name})
                        <ul class="submenu">
                           <li><a href="/admin/adminUserProfile.cform"><fmt:message key="view.user.profile"/></a></li>
                            <u:checkRole roleGroup="UPDATE_OWN_PROFILE">
                               <li><a href="/admin/adminChangePassword.form"><fmt:message key="change.password"/></a></li>
                            </u:checkRole>
                         </ul>
                      </li>
                 </ul>
              </div>

              <div class="icons">
                     
                    <jsp:include page="/WEB-INF/jsp/menuitem/Help.jsp"/> 
                    
                    <%-- <jsp:include page="/WEB-INF/jsp/menuitem/RefKey.jsp"/> --%>
                    <jsp:include page="/WEB-INF/jsp/menuitem/Home.jsp"/>
                    <jsp:include page="/WEB-INF/jsp/menuitem/Back.jsp"/>
                    <jsp:include page="/WEB-INF/jsp/menuitem/Logout.jsp"/>
              </div>
              <div class="languageSwitch">
                    <a id="locale1" class="lang1" href="?locale=<fmt:message key="language1.code"/>"><fmt:message key="language1"/></a>
                    <a id="locale2" class="lang2" href="?locale=<fmt:message key="language2.code"/>"><fmt:message key="language2"/></a>
            </div>
        </div>
    </div>
    <span id="printPopinWin" style="display:none;">
          <fmt:message key="file.page.range"/><br>
          <br>
           <input type="radio" name="page" value="current" id="defaultPrintOption" onchange="radioChanged(this);" checked/><fmt:message key="file.page.current"/><br>
           <input type="radio" name="page" value="all" onchange="radioChanged(this);"/><fmt:message key="file.page.all"/>
    </span>
    