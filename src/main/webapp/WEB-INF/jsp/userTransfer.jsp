<%-- 
    Document   : userTransfer
    Created on : Jul 21, 2022, 9:52:05 AM
    Author     : MBSL15135
--%>
<%@page import="com.mbsl.dao.impl.UserTransferDaoImpl"%>
<%@page import="com.mbsl.model.User"%>
<%@page import="com.mbsl.dao.impl.UserDaoImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="main">
    <%@include file="../base/resources.jsp" %>
    <%@include file="../base/header.jsp" %>
    <%@include file="../base/sideBar.jsp" %>


    <div class="content-wrapper" ng-controller="userTransferController">
        <%@include file="userTrans/new_reocrd.jsp" %>
        <%@include file="userTrans/new_record_view.jsp" %>
        <%@include file="userTrans/new_record_update.jsp" %>
        <%@include file="userTrans/approve_info.jsp" %>

        <section class="content-header">

            <ol class="breadcrumb">
                <li class="me-2"><a href="#"><i class="fa fa-dashboard"></i>Home</a></li>
                <li class="active">&nbsp; New User</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content" ng-init = "getAllTransferUser(); getAllDesignations();" cg-busy="createTransferBusy">

            <div class="wrapper"style="margin-top: 75px" >
                <div id="regContainer" layout="column">
                    <md-toolbar class="md-toolbar-tools ">
                        <h2>User Transfer</h2>
                        <!--                        <span flex></span>
                                                <md-button href="test.html">Go Unit Test</md-button>-->
                    </md-toolbar>
                    <md-content layout-padding>
                        <form name="regForm" role="form">
                            <div class="col-md-12 padding-none">
                                <div class="col-md-12 padding-none">
                                    <div class="col-md-2 pull-right">
                                        <label class="text-right padding-left-none padding-right-none col-md-12">Date : {{transferUser.toDay}} </label>
                                        <label class="text-right control-label padding-left-none padding-right-none col-md-12">Cost Center : ${sessionScope.branch}  </label>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-12 padding-none card" >
                                <div class="card-header text-center col-md-12 padding-top-none padding-bottom-none">
                                    <div class="col-md-2  padding-none">
                                        <input type="text" class="pull-left form-control padding-right-none" ng-model="availableData" placeholder="Filter"/>
                                    </div>

                                </div>
                                <div class="card-body padding-bottom-none " >
                                    <div class="col-md-12 padding-left-none padding-right-none table-responsive"  id="tblItemType">

                                        <table class="table table-striped table-hover table-bordered table-responsive" style="position: relative;">
                                            <thead>
                                                <tr class="label-primary">
                                                    <th>User ID</th>
                                                    <th>Name</th>
                                                    <th>New Branch or Department</th>
                                                    <th>New Profile</th>
                                                    <th>New Designation</th>
                                                    <th>Progress</th>
                                                    <th></th>
                                                    <td></td>
                                                    <th><div class="text-center">
                                                            <button class="btn btn-success form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="mainModelView()">Add New</button>
                                                        </div></th>
                                                    <!--<th></th>-->
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr ng-repeat="availableData in trensferUsers|filter:availableData">
                                                    <td>{{availableData.epfNo}}</td>
                                                    <td>{{availableData.fullName}}</td>
                                                    <td>{{availableData.newBranchOrDept}}</td>
                                                    <td>{{availableData.newProfile}}</td>
                                                    <td>{{availableData.newDesignation}}</td>
                                                    <td class="col-1"><div class="text-center">
                                                            <button class="btn btn-secondary form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="getAppInfoT('User Transfer', availableData.epfNo, availableData.status, availableData.newBranchOrDept)" data-bs-target="#approveInfoUserTranse">{{availableData.status}}</button>
                                                        </div>
                                                    </td>

                                                    <td class="col-1"><div class="text-center">
                                                            <button class="btn btn-primary form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="getToButtonListTransferById(availableData.epfNo)" data-bs-target="#newTransModelView">View</button>
                                                        </div>
                                                    </td>
                                                    <td class="col-1"><div class="text-center">
                                                            <button class="btn btn-success form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-disabled="availableData.status !== 'Pending'"  ng-click="getToButtonListTransferById(availableData.epfNo)" data-bs-target="#newTransModelUpdate">Update</button>
                                                        </div>
                                                    </td>
                                                    <td class="col-1"><div class="text-center">
                                                            <!--                                                            <button class="btn btn-danger form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-disabled="availableData.status !== 'Pending'"  ng-click="deleteTransferUser(availableData.epfNo)">Delete</button>-->
                                                            <button class="btn btn-danger form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-disabled="availableData.status !== 'Pending'"  ng-click="selectedDltUserT(availableData.epfNo)" data-bs-target="#dltITransUserTypModal">Delete</button>
                                                        </div>
                                                    </td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="modal fade" id="dltITransUserTypModal" role="dialog" data-keyboard="false" data-backdrop="static">
                                <div class="modal-dialog modal-sm padding-top-15-present">
                                    <div class="modal-content">
                                        <div class="modal-body text-center">
                                            <label class="">Are you sure?</label>
                                        </div>
                                        <div class="modal-footer text-right ">
                                            <div class="mt-5 btn-group">    
                                                <div class="text-start me-3">
                                                    <button type="button" style="position: relative;" class="btn btn-danger btn-sm form-control" ng-click="deleteTransferUser(false)">No</button>
                                                </div>
                                                <div class="text-end">
                                                    <button type="button" style="position: relative;" class="btn btn-success btn-sm form-control" ng-click="deleteTransferUser(true)">Yes</button>
                                                </div>
                                            </div>
                                        </div>
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
    <script src="js/app/userTransferController.js?v=0.004"></script>

</html>
