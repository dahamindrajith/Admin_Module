<%-- 
    Document   : testPage
    Created on : Oct 18, 2022, 2:46:56 PM
    Author     : MBSL2523
--%>


<%@page import="com.mbsl.model.User"%>
<%@page import="com.mbsl.dao.impl.HrUpdateDaoImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="main">
    <%@include file="../base/resources.jsp" %>
    <%@include file="../base/header.jsp" %>
    <%@include file="../base/sideBar.jsp" %>


    <div class="content-wrapper" ng-controller="hrUpdateController">
        <%@include file="../jsp/hrApproved/new_reocrd.jsp" %>

        <section class="content-header">
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i>Home</a></li>
                <li class="active">New user</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content" ng-init = "getList();" cg-busy="createHrSpecBusy">

            <div class="wrapper"style="margin-top: 75px" >
                <div id="regContainer" layout="column">
                    <md-toolbar style="font-size:20px;" class="md-toolbar-tools">
                        <h2>HR Approved List</h2>
                        <!--                        <span flex></span>
                                                <md-button href="test.html">Go Unit Test</md-button>-->
                    </md-toolbar>
                    <md-content layout-padding>
                        <form name="regForm" role="form">
                            <div class="col-md-12 padding-none">
                                <div class="col-md-12 padding-none">
                                    <div class="col-md-2 pull-right">
                                        <label class="text-right padding-left-none padding-right-none col-md-12">Date : {{approveList.toDay}} </label>
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

                                        <table class="table table-striped table-hover table-bordered table-responsive" style="position: relative; font-size:15px;">
                                            <thead>
                                                <tr class="label-primary">
                                                    <th>User ID</th>
                                                    <th>Name</th>
                                                    <th>Profile</th>
                                                    <th>Branch Or Department</th>
                                                    <th>Approved Profile</th>
                                                    <th><div class="text-center">
                                                            <!--                                                            <button class="form-control btn-success btn" type="submit" ng-disabled="regForm.$invalid" ng-click="createUser()">Add</button>-->
                                                            <button  class="btn btn-success btn-sm form-control" style="position: relative;" data-bs-toggle="modal" ng-click="mainModelView() ; getBProfile()">Add New</button>
                                                        </div></th>
                                                    <!--<th></th>-->
                                                </tr>
                                            </thead>

                                            <tbody>
                                                <tr ng-repeat="availableData in assignData|filter:availableData">
                                                    <td>{{availableData.epfNo}}</td>
                                                    <td>{{availableData.fullName}}</td>
                                                    <td>{{availableData.profile}}</td>
                                                    <td>{{availableData.branchorDept}}</td>
                                                    <td>{{availableData.newProfile}}</td>
                                                    <td class="col-1"><div class="text-center">
                                                            <button class="btn btn-danger btn-sm form-control" style="position: relative;" data-bs-toggle="modal" ng-click="deleteList(availableData.epfNo)">Delete</button>
                                                        </div>
                                                    </td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="modal fade" id="dltItemTypModal" role="dialog" data-keyboard="false" data-backdrop="static">
                                <div class="modal-dialog modal-sm padding-top-15-present">
                                    <div class="modal-content">
                                        <div class="modal-body text-center">
                                            <label class="">Are you sure?</label>
                                        </div>
                                        <div class="modal-footer text-right ">
                                            <div class="mt-5 btn-group">    
                                                <div class="text-start me-3">
                                                    <button type="button" style="position: relative;" class="btn btn-danger" ng-click="deleteUser(false)">No</button>
                                                </div>
                                                <div class="text-end">
                                                    <button type="button" style="position: relative;" class="btn btn-success" ng-click="deleteUser(true)">Yes</button>
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
    <script src="js/app/hrUpdateController.js?v=0.004"></script>

</html>
