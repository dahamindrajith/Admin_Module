<%-- 
    Document   : approveReqst
    Created on : Jul 21, 2022, 9:45:24 AM
    Author     : MBSL15135
--%>
<%@page import="com.mbsl.dao.impl.ApproveReqstDaoImpl"%>
<%@page import="com.mbsl.model.User"%>
<%@page import="com.mbsl.dao.impl.UserDaoImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="main">
    <%@include file="../base/resources.jsp" %>
    <%@include file="../base/header.jsp" %>
    <%@include file="../base/sideBar.jsp" %>


    <div class="content-wrapper" ng-controller="approveReqstController">
        <%@include file="../jsp/approveRqst/grant_reocrd.jsp" %>
        <%@include file="../jsp/approveRqst/transfer_reocrd.jsp" %>
        <%@include file="../jsp/approveRqst/inactivate_reocrd.jsp" %>
        <%@include file="../jsp/approveRqst/temp_trance_record.jsp" %>


        <section class="content-header">

            <ol class="breadcrumb">
                <li class="me-2"><a href="#"><i class="fa fa-dashboard"></i>Home</a></li>
                <li class="active">&nbsp; New User</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content" ng-init="getApproveRqsts()" cg-busy="createApproveRqstBusy">

            <div class="wrapper"style="margin-top: 75px" >
                <div id="regContainer" layout="column">
                    <md-toolbar class="md-toolbar-tools ">
                        <h2>Approve Request</h2>
                        <!--                        <span flex></span>
                                                <md-button href="test.html">Go Unit Test</md-button>-->
                    </md-toolbar>
                    <md-content layout-padding>
                        <form name="regForm" role="form">
                            <div class="col-md-12 padding-none">
                                <div class="col-md-12 padding-none">
                                    <div class="col-md-2 pull-right">
                                        <label class="text-right padding-left-none padding-right-none col-md-12">Date : {{approveRqst.toDay}} </label>
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
                                                    <th>Request ID</th>
                                                    <th>User ID</th>
                                                    <th>Branch or Department</th>
                                                    <th>Profile</th>
                                                    <th>Designation</th>
                                                    <th>Request Type</th>
                                                    <th>Branch/Department</th>
                                                    <th></th>
                                                    <!--<th></th>-->
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr ng-repeat="availableData in assignData|filter:availableData">
                                                    <td>{{availableData.rqstId}}</td>
                                                    <td>{{availableData.epfNo}}</td>
                                                    <td >{{availableData.branchorDept||availableData.newBranchOrDept}}</td>
                                                    <td>{{availableData.profile||availableData.newProfile}}</td>
                                                    <td>{{availableData.designation||availableData.newDesignation}}</td>
                                                    <td>{{availableData.rqstType}}</td>
                                                    <td>{{availableData.click}}</td>

                                                    <td class="col-1"><div class="text-center">
                                                            <button class="btn btn-primary form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="viewScreens(availableData.rqstType); getListByRqstId(availableData.epfNo, availableData.rqstType)">View</button>
                                                        </div>
                                                    </td>

                                                </tr>

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
    <script src="js/app/approveReqstController.js?v=0.004"></script>

</html>
