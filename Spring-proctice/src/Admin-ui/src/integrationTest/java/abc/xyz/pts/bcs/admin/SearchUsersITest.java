/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.admin;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import abc.xyz.pts.bcs.admin.business.AdminService;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.web.command.AdminUserSearchCommand;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

@Ignore
public final class SearchUsersITest {

    private ApplicationContext context = null;
    private static final String[] CONFIG_FILES = {"file:src/main/webapp/WEB-INF/SpringConfig/admin_ldap.xml",
            "file:src/main/webapp/WEB-INF/SpringConfig/admin_business.xml" };

    private static final int NUM_RECORDS = 100;
    private static final int PAGE_SIZE = 3;

    private SearchUsersITest() {
        context = new ClassPathXmlApplicationContext(CONFIG_FILES);
        runTests();
    }

    private void runTests() {
        runGetAllUsersTest();
        runPagingTest();
        runFilterTest();
        runReverseFilterTest();
        runFilterRoleTest();
    }

    private void runGetAllUsersTest() {
        System.out.println("get all test...");
        AdminService adminService = (AdminService) context.getBean("adminService");
        AdminUserSearchCommand adminUserSearchCommand = new AdminUserSearchCommand();
        TableActionCommand adminSearchTableCommand = new TableActionCommand();
        adminSearchTableCommand.setMaxRowCount(NUM_RECORDS);
        
        String cookie = "testcookie" + new Date().getTime();
        List<User> userList = adminService.getSearchUsers(adminUserSearchCommand, adminSearchTableCommand);
        printResults(userList);
    }

    private void runPagingTest() {
        System.out.println("paging test...");
        AdminService adminService = (AdminService) context.getBean("adminService");
        String cookie = "";
        
        AdminUserSearchCommand adminUserSearchCommand = new AdminUserSearchCommand();
        TableActionCommand adminSearchTableCommand = new TableActionCommand();
        adminSearchTableCommand.setPageSize(PAGE_SIZE);
        
        List<User> searchResults1 = adminService.getSearchUsers(adminUserSearchCommand, adminSearchTableCommand);
        printResults(searchResults1);
        // search again for next page
        adminSearchTableCommand.nextPage();
        List<User> searchResults2 = adminService.getSearchUsers(adminUserSearchCommand, adminSearchTableCommand);
        System.out.println("page 2...");
        printResults(searchResults2);
        // search again for next page
        adminSearchTableCommand.nextPage();
        List<User> searchResults3 = adminService.getSearchUsers(adminUserSearchCommand, adminSearchTableCommand);
        System.out.println("page 3...");
        printResults(searchResults3);
        // search again for next page
        adminSearchTableCommand.nextPage();
        List<User> searchResults4 = adminService.getSearchUsers(adminUserSearchCommand, adminSearchTableCommand);
        System.out.println("page 4...");
        printResults(searchResults4);
    }

    private void runFilterTest() {
        System.out.println("filter test...");
        AdminService adminService = (AdminService) context.getBean("adminService");
        User userParams = new User();
        userParams.setForename("tim");
        AdminUserSearchCommand adminUserSearchCommand = new AdminUserSearchCommand();
        adminUserSearchCommand.setUser(userParams);
        TableActionCommand adminSearchTableCommand = new TableActionCommand();
        adminSearchTableCommand.setMaxRowCount(NUM_RECORDS);
        String cookie = "testcookie" + new Date().getTime();
        List<User> searchResults = adminService.getSearchUsers(adminUserSearchCommand, adminSearchTableCommand);
        printResults(searchResults);
    }

    private void runFilterRoleTest() {
        System.out.println("filter role test...");
        AdminService adminService = (AdminService) context.getBean("adminService");
        User userParams = new User();
        userParams.setRoles(new String[] {"ccs"});
        AdminUserSearchCommand adminUserSearchCommand = new AdminUserSearchCommand();
        adminUserSearchCommand.setUser(userParams);
        TableActionCommand adminSearchTableCommand = new TableActionCommand();
        adminSearchTableCommand.setMaxRowCount(NUM_RECORDS);
        String cookie = "testcookie" + new Date().getTime();
        List<User> searchResults = adminService.getSearchUsers(adminUserSearchCommand, adminSearchTableCommand);
        printResults(searchResults);
    }

    private void runReverseFilterTest() {
        System.out.println("reverse filter test...");
        AdminService adminService = (AdminService) context.getBean("adminService");
        User userParams = new User();
        userParams.setForename("tim");
        AdminUserSearchCommand adminUserSearchCommand = new AdminUserSearchCommand();
        adminUserSearchCommand.setUser(userParams);
        TableActionCommand adminSearchTableCommand = new TableActionCommand();
        adminSearchTableCommand.setMaxRowCount(NUM_RECORDS);
        adminSearchTableCommand.setAscDesc("DESC");
        String cookie = "testcookie" + new Date().getTime();
        List<User> searchResults = adminService.getSearchUsers(adminUserSearchCommand, adminSearchTableCommand);
        printResults(searchResults);
    }

    private void printResults(final List<User> users) {
        System.out.println("#####################");
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
            User user = iterator.next();
            System.out.println("User: " + user.getUsername());
        }
        System.out.println("#####################");
    }

    public static void main(final String[] args) {
        SearchUsersITest test = new SearchUsersITest();
    }
}
