<%-- 
    Document   : tempAccess
    Created on : Jul 21, 2022, 9:50:10 AM
    Author     : MBSL15135
--%>

<%@page import="com.mbsl.dao.impl.TempAccessDaoImpl"%>
<%@page import="com.mbsl.model.User"%>
<%@page import="com.mbsl.dao.impl.UserDaoImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="main">
    <%@include file="../base/resources.jsp" %>
    <%@include file="../base/header.jsp" %>
    <%@include file="../base/sideBar.jsp" %>


    <div class="content-wrapper" ng-controller="tempAccessController">
        <%@include file="../jsp/tempAccess/new_reocrd.jsp" %>
        <%@include file="../jsp/tempAccess/new_record_view.jsp" %>
        <%@include file="../jsp/tempAccess/new_reocrd_update.jsp" %>
        <%@include file="../jsp/tempAccess/approve_info.jsp" %>

        <section class="content-header">

            <ol class="breadcrumb">
                <li class="me-2"><a href="#"><i class="fa fa-dashboard"></i>Home</a></li>
                <li class="active">&nbsp; New User</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content" ng-init = "getAllTempAccess(); getAllDesignations();" cg-busy="createTempAccBusy">

            <div class="wrapper"style="margin-top: 75px" >
                <div id="regContainer" layout="column">
                    <md-toolbar class="md-toolbar-tools ">
                        <h2>Temporary Access Request</h2>
                        <!--                        <span flex></span>
                                                <md-button href="test.html">Go Unit Test</md-button>-->
                    </md-toolbar>
                    <md-content layout-padding>
                        <form name="regForm" role="form">
                            <div class="col-md-12 padding-none">
                                <div class="col-md-12 padding-none">
                                    <div class="col-md-2 pull-right">
                                        <label class="text-right padding-left-none padding-right-none col-md-12">Date : {{tempAccess.toDay}} </label>
                                        <label class="text-right control-label padding-left-none padding-right-none col-md-12">Cost Center : ${sessionScope.branch}  </label>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-12 padding-none card" >
                                <div class="card-header text-center col-md-12 padding-top-none padding-bottom-none">
                                    <div class="col-md-2  padding-none">
                                        <input type="text" class="pull-left form-control padding-right-none" ng-model="availableData" placeholder="Filter"/>
                                    </div>
<!--                                    <div class="col-md-2 pull-right padding-top-1-present">
                                        <button class="btn btn-danger form-control btn-sm" ng-disabled="btns=='0'" style="position: relative;" data-bs-toggle="modal" ng-click="removeTempMnually()">Remove Access</button>
                                    </div>-->
                                </div>
                                <div class="card-body padding-bottom-none " >
                                    <div class="col-lg-12 padding-left-none padding-right-none table-responsive"  id="tblItemType">

                                        <table class="table table-striped table-hover table-bordered table-responsive" style="position: relative;">
                                            <thead>
                                                <tr class="label-primary">
<!--                                                    <th></th>-->
                                                    <th>User ID</th>
                                                    <th>Current Branch or Department</th>
                                                    <th>Current Profile</th>
                                                    <th>Current Designation</th>
                                                    <th>New Branch or Department</th>
                                                    <th>New Profile</th>
                                                    <th>New Designation</th>
                                                    <th>Date From</th>
                                                    <th>Date To</th>
                                                    <th>Progress</th>
                                                    <th></th>
                                                    <th></th>
                                                    <th><div class="text-end">
                                                            <button class="btn btn-success form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="mainModelView()">Add New</button>
                                                        </div></th>
                                                    <!--<th></th>-->
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr ng-repeat="availableData in assignData|filter:availableData">
<!--                                                    <td><input type="checkbox" ng-model="checkboxModel.value1" ng-click="storeData(availableData.epfNo,checkboxModel.value1)" ng-disabled="availableData.status=='Pending'"></td>-->
                                                    <td>{{availableData.epfNo}}</td>
                                                    <td>{{availableData.branchorDept}}</td>
                                                    <td >{{availableData.profile}}</td>
                                                    <td>{{availableData.designation}}</td>
                                                    <td>{{availableData.newBranchOrDept}}</td>
                                                    <td>{{availableData.newProfile}}</td>
                                                    <td>{{availableData.newDesignation}}</td>
                                                    <td>{{availableData.startDate}}</td>
                                                    <td>{{availableData.endDate}}</td>
                                                    <td class="col-1"><div class="text-center">
                                                            <button class="btn btn-secondary form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="getAppInfoTe('Temporary Access', availableData.epfNo, availableData.status, availableData.newBranchOrDept)" data-bs-target="#approveInfoUserTemp">{{availableData.status}}</button>
                                                        </div>
                                                    </td>

                                                    <td class="col-1"><div class="text-center">
                                                            <button class="btn btn-primary form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="getListTempAccessById(availableData.epfNo)" data-bs-target="#tempAccesstModelView">View</button>
                                                        </div>
                                                    </td>
                                                    <td class="col-1"><div class="text-center">
                                                            <button class="btn btn-success form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-disabled="availableData.status !== 'Pending'"  ng-click="getListTempAccessById(availableData.epfNo)" data-bs-target="#updateTempAccessModel">Update</button>
                                                        </div>
                                                    </td>
                                                    <td class="col-1"><div class="text-center">
                                                            <!--                                                            <button class="btn btn-danger form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-disabled="availableData.status !== 'Pending'"  ng-click="deleteTempAccess(availableData.epfNo)">Delete</button>-->
                                                            <button class="btn btn-danger form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-disabled="availableData.status !== 'Pending'"  ng-click="selectedDltUserTT(availableData.epfNo)" data-bs-target="#dltTempUserTypModal">Delete</button>
                                                        </div>
                                                    </td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="modal fade" id="dltTempUserTypModal" role="dialog" data-keyboard="false" data-backdrop="static">
                                <div class="modal-dialog modal-sm padding-top-15-present">
                                    <div class="modal-content">
                                        <div class="modal-body text-center">
                                            <label class="">Are you sure?</label>
                                        </div>
                                        <div class="modal-footer text-right ">
                                            <div class="mt-5 btn-group">    
                                                <div class="text-start me-3">
                                                    <button type="button" style="position: relative;" class="btn btn-danger btn-sm form-control" ng-click="deleteTempAccess(false)">No</button>
                                                </div>
                                                <div class="text-end">
                                                    <button type="button" style="position: relative;" class="btn btn-success btn-sm form-control" ng-click="deleteTempAccess(true)">Yes</button>
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
    <script src="js/app/tempAccessController.js?v=0.004"></script>

</html>
