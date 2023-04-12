<%-- 
    Document   : approvePath
    Created on : Jul 21, 2022, 9:44:03 AM
    Author     : MBSL15135
--%>

<%@page import="com.mbsl.model.ApprovalPath"%>
<%@page import="com.mbsl.dao.impl.ApprovePathDaoImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="main">
    <%@include file="../base/resources.jsp" %>
    <%@include file="../base/header.jsp" %>
    <%@include file="../base/sideBar.jsp" %>


    <div class="content-wrapper" ng-controller="approvePathController">
        <%@include file="userApprPath/new_reocrd.jsp" %>

        <section class="content-header">

            <ol class="breadcrumb">
                <li class="me-2"><a href="#"><i class="fa fa-dashboard"></i>Home</a></li>
                <li class="active">&nbsp; New User</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content" cg-busy="createUserBusy">

            <div class="wrapper"style="margin-top: 75px" >
                <div id="regContainer" layout="column">
                    <md-toolbar class="md-toolbar-tools ">
                        <h2>Approval Path</h2>
                        <!--                        <span flex></span>
                                                <md-button href="test.html">Go Unit Test</md-button>-->
                    </md-toolbar>
                    <md-content layout-padding>
                        <form name="regForm" role="form" ng-click="getCCList();">
                            <div class="col-md-12 padding-none">
                                <div class="col-md-12 padding-none">
                                    <div class="col-md-2 pull-right">
                                        <label class="text-right padding-left-none padding-right-none col-md-12">Date : {{approvePath.toDay}} </label>
                                        <label class="text-right control-label padding-left-none padding-right-none col-md-12">Cost Center : ${sessionScope.branch}  </label>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-12 padding-none card" >
                                <div class="card-header text-center col-md-12 padding-top-none padding-bottom-none">
                                    <div class="col-md-2  padding-none">
                                        <input type="text" class="pull-left form-control padding-right-none" ng-model="apppath.whtFilter" placeholder="Filter"/>
                                    </div>
                                    
                                </div>
                                <div class="card-header col-md-12  padding-bottom-none">
                                    
                                        <md-radio-group ng-model="userAccess.radio" layout="row" >
                                        <md-radio-button checked="true" value="uc" class="md-primary col-md-2  justify-content-md-end">User Creation</md-radio-button>
                                        <md-radio-button  value="ut" class="md-primary col-md-2  justify-content-md-end">User Transfer</md-radio-button>
                                        <md-radio-button  value="ui" class="md-primary col-md-2  justify-content-md-end">User Inactivation</md-radio-button>
                                        <md-radio-button  value="ta" class="md-primary col-md-2  justify-content-md-end">Temporary Access </md-radio-button>
                                    </md-radio-group>
                                   
                                    
                                </div>
                                <div class="card-body padding-bottom-none " >
                                    <div class="col-md-12 padding-left-none padding-right-none table-responsive"  id="tblItemType">

                                        <table class="table table-striped table-hover table-bordered table-responsive" ng-show="userAccess.radio=='uc'" style="position: relative;" cg-busy="user.removeWhtBusy">
                                            <thead>
                                                <tr class="label-primary">
                                                    <th>Path Number</th>
                                                    <th>Request Type </th>
                                                    <th>Approved Officer</th>
                                                    <th>Approved Level</th>
                                                    
                                                    <th><div class="text-end">
                                                            <button ng-click="approvePath.clear()" class="btn btn-success form-control" style="position: relative;" data-bs-toggle="modal" data-bs-target="#newApproveModel">Add New</button>
                                                        </div></th>
                                                    <!--<th></th>-->
                                                </tr>
                                            </thead>
                                            <tbody>
                                                
                                                    <%

                                                    ApprovePathDaoImpl ydi = new ApprovePathDaoImpl();

                                                    for (ApprovalPath ltcat : ydi.getAllApprovelPaths("1")) {
                                                        String parameter = "";

                                                %>

                                               <tr>

                                                    <td><% out.print(ltcat.getPathno()); %></td>
                                                    <td><% out.print(ltcat.getRqstType()); %></td>
                                                    <td><% out.print(ltcat.getApprvlOffcer()); %></td>
                                                    <td><% out.print(ltcat.getApprvlLevel()); %></td>
                                                   
                                                   <td>
                                                       <button class="btn btn-danger form-control" style="position: relative;" data-bs-toggle="modal" ng-click="deleteApprovelPaths('<% out.print(ltcat.getPathno()); %>', '<% out.print(ltcat.getApprvlLevel()); %>', '<% out.print(ltcat.getRqstType()); %>')">Delete</button>
                                                    </td>

                                                </tr>
                                                <%}%>
                                       </tbody>
                                        </table>
                                       
                                       <table class="table table-striped table-hover table-bordered table-responsive" ng-show="userAccess.radio=='ut'" style="position: relative;" cg-busy="user.removeWhtBusy">
                                            <thead>
                                                <tr class="label-primary">
                                                    <th>Path Number</th>
                                                    <th>Request Type </th>
                                                    <th>Approved Officer</th>
                                                    <th>Approved Level</th>
                                                    
                                                    <th><div class="text-end">
                                                            <button ng-click="approvePath.clear()" class="btn btn-success form-control" style="position: relative;" data-bs-toggle="modal" data-bs-target="#newApproveModel">Add New</button>
                                                        </div></th>
                                                    <!--<th></th>-->
                                                </tr>
                                            </thead>
                                            <tbody>
                                                
                                                    <%

                                                    

                                                    for (ApprovalPath ltcat : ydi.getAllApprovelPaths("2")) {
                                                        String parameter = "";

                                                %>

                                               <tr>

                                                    <td><% out.print(ltcat.getPathno()); %></td>
                                                    <td><% out.print(ltcat.getRqstType()); %></td>
                                                    <td><% out.print(ltcat.getApprvlOffcer()); %></td>
                                                    <td><% out.print(ltcat.getApprvlLevel()); %></td>
                                                   
                                                   <td>
                                                       <button class="btn btn-danger form-control" style="position: relative;" data-bs-toggle="modal" ng-click="deleteApprovelPaths('<% out.print(ltcat.getPathno()); %>', '<% out.print(ltcat.getApprvlLevel()); %>','<% out.print(ltcat.getRqstType()); %>')">Delete</button>
                                                    </td>

                                                </tr>
                                                <%}%>
                                       </tbody>
                                        </table>
                                       
                                       <table class="table table-striped table-hover table-bordered table-responsive" ng-show="userAccess.radio=='ui'" style="position: relative;" cg-busy="user.removeWhtBusy">
                                            <thead>
                                                <tr class="label-primary">
                                                    <th>Path Number</th>
                                                    <th>Request Type </th>
                                                    <th>Approved Officer</th>
                                                    <th>Approved Level</th>
                                                    
                                                    <th><div class="text-end">
                                                            <button ng-click="approvePath.clear()" class="btn btn-success form-control" style="position: relative;" data-bs-toggle="modal" data-bs-target="#newApproveModel">Add New</button>
                                                        </div></th>
                                                    <!--<th></th>-->
                                                </tr>
                                            </thead>
                                            <tbody>
                                                
                                                    <%

                                                    

                                                    for (ApprovalPath ltcat : ydi.getAllApprovelPaths("3")) {
                                                        String parameter = "";

                                                %>

                                               <tr>

                                                    <td><% out.print(ltcat.getPathno()); %></td>
                                                    <td><% out.print(ltcat.getRqstType()); %></td>
                                                    <td><% out.print(ltcat.getApprvlOffcer()); %></td>
                                                    <td><% out.print(ltcat.getApprvlLevel()); %></td>
                                                   
                                                   <td>
                                                       <button class="btn btn-danger form-control" style="position: relative;" data-bs-toggle="modal" ng-click="deleteApprovelPaths('<% out.print(ltcat.getPathno()); %>', '<% out.print(ltcat.getApprvlLevel()); %>','<% out.print(ltcat.getRqstType()); %>')">Delete</button>
                                                    </td>

                                                </tr>
                                                <%}%>
                                       </tbody>
                                        </table>
                                       
                                       <table class="table table-striped table-hover table-bordered table-responsive" ng-show="userAccess.radio=='ta'" style="position: relative;" cg-busy="user.removeWhtBusy">
                                            <thead>
                                                <tr class="label-primary">
                                                    <th>Path Number</th>
                                                    <th>Request Type </th>
                                                    <th>Approved Officer</th>
                                                    <th>Approved Level</th>
                                                    
                                                    <th><div class="text-end">
                                                            <button ng-click="approvePath.clear()" class="btn btn-success form-control" style="position: relative;" data-bs-toggle="modal" data-bs-target="#newApproveModel">Add New</button>
                                                        </div></th>
                                                    <!--<th></th>-->
                                                </tr>
                                            </thead>
                                            <tbody>
                                                
                                                    <%

                                                    

                                                    for (ApprovalPath ltcat : ydi.getAllApprovelPaths("4")) {
                                                        String parameter = "";

                                                %>

                                               <tr>

                                                    <td><% out.print(ltcat.getPathno()); %></td>
                                                    <td><% out.print(ltcat.getRqstType()); %></td>
                                                    <td><% out.print(ltcat.getApprvlOffcer()); %></td>
                                                    <td><% out.print(ltcat.getApprvlLevel()); %></td>
                                                   
                                                   <td>
                                                       <button class="btn btn-danger form-control" style="position: relative;" data-bs-toggle="modal" ng-click="deleteApprovelPaths('<% out.print(ltcat.getPathno()); %>', '<% out.print(ltcat.getApprvlLevel()); %>','<% out.print(ltcat.getRqstType()); %>')">Delete</button>
                                                    </td>

                                                </tr>
                                                <%}%>
                                       </tbody>
                                        </table>
                                       
                                    </div>
                                </div>
                            </div>
                        </form>
                                    
                    </md-content>
                </div>
            </div>
        </section>
    </div>
    <%@include file="../base/footer.jsp" %>
    <script src="js/app/approvePathController.js?v=0.004"></script>

</html>
