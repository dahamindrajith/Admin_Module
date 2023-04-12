<%-- 
    Document   : accessCofig
    Created on : Jul 21, 2022, 9:42:54 AM
    Author     : MBSL15135
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="main">
    <%@include file="../base/resources.jsp" %>
    <%@include file="../base/header.jsp" %>
    <%@include file="../base/sideBar.jsp" %>


    <div class="content-wrapper" ng-controller="accessCofigController">
        <%@include file="../jsp/configAccess/new_reocrd_branch.jsp" %>
        <%@include file="../jsp/configAccess/new_reocrd_dept.jsp" %>
        <%@include file="../jsp/configAccess/new_reocrd_branch_update.jsp" %>
        <%@include file="../jsp/configAccess/new_reocrd_branch_view.jsp" %>
        <%@include file="../jsp/configAccess/new_reocrd_dept_view.jsp" %>
        <%@include file="../jsp/configAccess/new_reocrd_dept_update.jsp" %>
        <%@include file="../jsp/configAccess/new_record_region.jsp" %>
        <%@include file="../jsp/configAccess/new_record_region_view.jsp" %>
        <%@include file="../jsp/configAccess/new_record_region_update.jsp" %>


        <section class="content-header">

            <ol class="breadcrumb">
                <li class="me-2"><a href="#"><i class="fa fa-dashboard"></i>Home</a></li>
                <li class="active">&nbsp; Configure Access</li>
            </ol>
        </section>
        <!-- Main content -->

        <section class="content" ng-init = "getAllUsers(); getAllDesignations(); getAllProfiles(); getAllRegion();" cg-busy="createAccessBusy">

            <div class="wrapper"style="margin-top: 75px" >
                <div id="regContainer" layout="column">
                    <md-toolbar style="font-size:20px;" class="md-toolbar-tools">
                        <h2>Velocity Access Configure</h2>
                        <!--                        <span flex></span>
                                                <md-button href="test.html">Go Unit Test</md-button>-->
                    </md-toolbar>
                    <md-content layout-padding>
                        <form name="regForm" role="form">
                            <div class="col-md-12 padding-none">
                                <div class="col-md-12 padding-none">
                                    <div class="col-md-2 pull-right">
                                        <label class="text-right padding-left-none padding-right-none col-md-12">Date : {{userAccess.toDay}} </label>
                                        <label class="text-right control-label padding-left-none padding-right-none col-md-12">Cost Center : ${sessionScope.branch}  </label>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-12 padding-none card" >


                                <div class="card-header col-md-12  padding-bottom-none">

                                    <md-radio-group ng-model="userAccess.radio" layout="row" >
                                        <md-radio-button checked="true" value="Branch" ng-click="getAccessLiset('branch')" class="md-primary col-md-2  justify-content-md-end">Branch</md-radio-button>
                                        <md-radio-button  value="Department" ng-click="getAccessLiset('department')" class="md-primary col-md-2  justify-content-md-end">Department</md-radio-button>
                                        <md-radio-button  value="Region" ng-click="getAccessLiset('region')" class="md-primary col-md-2  justify-content-md-end">Region</md-radio-button>

                                    </md-radio-group>

                                </div>

                                <div class="card-header text-center col-md-12 padding-top-none padding-bottom-none">
                                    <div class="col-md-2  padding-none">
                                        <input type="text" class="pull-left form-control padding-right-none" ng-model="availableData" placeholder="Filter"/>
                                    </div>
                                </div>

                                <div class="card-body padding-bottom-none " >
                                    <div class="col-md-12 padding-left-none padding-right-none table-responsive"  id="tblItemType">


                                        <table class="table table-striped table-hover table-bordered table-responsive" ng-show="userAccess.radio == 'Branch'" style="position: relative; font-size:15px;">
                                            <thead>
                                                <tr class="label-primary">

                                                    <th>Profile</th>


                                                    <th></th>
                                                    <th><div class="text-center">
                                                            <!--                                                            <button class="form-control btn-success btn" type="submit" ng-disabled="regForm.$invalid" ng-click="createUser()">Add</button>-->
                                                            <button  class="btn btn-success form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="mainModelView(1); getAllProfiles(1)">Add New</button>
                                                        </div></th>
                                                    <!--<th></th>-->
                                                </tr>
                                            </thead>

                                            <tbody>
                                                <tr ng-repeat="availableData in assignData| filter:availableData">
                                                    <td>{{availableData.profile}}</td>



                                                    <td class="col-1"><div class="text-center">
                                                            <button class="btn btn-primary form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="getAccessListByID('Branch', availableData.profile, 1)" data-bs-target="#newBranchAccessView">View</button>
                                                        </div>
                                                    </td>
                                                    <td class="col-1"><div class="text-center">
                                                            <button class="btn btn-success form-control btn-sm" style="position: relative;" data-bs-toggle="modal"  ng-click="getAccessListByID('Branch', availableData.profile, 1)" data-bs-target="#newBranchAccessUpdate">Update</button>
                                                        </div>
                                                    </td>
                                                </tr>

                                            </tbody>
                                        </table>

                                        <table class="table table-striped table-hover table-bordered table-responsive" ng-show="userAccess.radio == 'Department'" style="position: relative; font-size:15px;">
                                            <thead>
                                                <tr class="label-primary">
                                                    <th>Department</th>
                                                    <th>Profile</th>

                                                    <th></th>
                                                    <th><div class="text-center">
                                                            <!--                                                            <button class="form-control btn-success btn" type="submit" ng-disabled="regForm.$invalid" ng-click="createUser()">Add</button>-->
                                                            <button  class="btn btn-success form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="mainModelView(2); getAllDepartment()">Add New</button>
                                                        </div></th>
                                                    <!--<th></th>-->
                                                </tr>
                                            </thead>

                                            <tbody>
                                                <tr ng-repeat="availableData in assignData| filter:availableData">
                                                    <td>{{availableData.branchOrDept}}</td>
                                                    <td>{{availableData.profile}}</td>


                                                    <td class="col-1"><div class="text-center">
                                                            <button class="btn btn-primary form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="getAccessListByID('Department', availableData.profile, availableData.branchOrDept)" data-bs-target="#newDeptAccessView">View</button>
                                                        </div>
                                                    </td>
                                                    <td class="col-1"><div class="text-center">
                                                            <button class="btn btn-success form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="getAccessListByID('Department', availableData.profile, availableData.branchOrDept)" data-bs-target="#newDeptAccessUpdate">Update</button>
                                                        </div>
                                                    </td>

                                                </tr>

                                            </tbody>
                                        </table>

                                        <table class="table table-striped table-hover table-bordered table-responsive" ng-show="userAccess.radio == 'Region'" style="position: relative; font-size:15px;">
                                            <thead>
                                                <tr class="label-primary">
                                                    <th>Profile</th>

                                                    <th></th>
                                                    <th><div class="text-end">
                                                            <!--                                                            <button class="form-control btn-success btn" type="submit" ng-disabled="regForm.$invalid" ng-click="createUser()">Add</button>-->
                                                            <button  class="btn btn-success form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="mainModelView(3); getAllProfiles(3)">Add New</button>
                                                        </div></th>
                                                    <!--<th></th>-->
                                                </tr>
                                            </thead>

                                            <tbody>
                                                <tr ng-repeat="availableData in assignData| filter:availableData">
                                                    <td>{{availableData.profile}}</td>


                                                    <td class="col-1"><div class="text-center">
                                                            <button class="btn btn-primary form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="getAccessListByID('Region', availableData.profile, 2)" data-bs-target="#newRegionView">View</button>
                                                        </div>
                                                    </td>
                                                    <td class="col-1"><div class="text-center">
                                                            <button class="btn btn-success form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="getAccessListByID('Region', availableData.profile, 2)" data-bs-target="#newRegionAccessUpdate">Update</button>
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
        <!--        <section class="content" cg-busy="createUserBusy">
        
                    <div class="wrapper"style="margin-top: 75px" >
                        <div id="regContainer" layout="column">
                            <md-toolbar class="md-toolbar-tools ">
                                <h2>Configure Access</h2>
                            </md-toolbar>
                            <md-content layout-padding>
                                <form name="regForm" role="form" ng-init="getAllProfiles();">
                                    <div class="col-md-12 padding-none">
                                        <div class="col-md-12 padding-none">
                                            <div class="col-md-2 pull-right">
                                                <label class="text-right padding-left-none padding-right-none col-md-12">Date : {{userAccess.toDay}} </label>
                                                <label class="text-right control-label padding-left-none padding-right-none col-md-12">Cost Center : ${sessionScope.branch}  </label>
                                            </div>
                                        </div>
                                    </div>
        
                                    <div class="col-md-12 padding-none card" >
        
                                        <div class="card-body padding-bottom-none " >
        
        
                                            <md-radio-group ng-model="userAccess.radio" ng-click="clearBox(userAccess.radio)">
                                                <md-radio-button checked="true" value="branch" class="md-primary col-md-2 col-md-offset-4" ng-click="getAllProfiles()">Branch</md-radio-button>
                                                <md-radio-button  value="dept" class="md-primary col-md-2" ng-click="getAllDesignations()">Department</md-radio-button>
                                            </md-radio-group>
        
                                            <div class="row col-md-12">
        
                                                <div class="col-md-6" >
                                                    <md-input-container class="md-block col-md-6">
                                                        <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Designation</label>
        
                                                        <select class="col-md-6 pull-right" ng-model="userAccess.designation" ng-disabled="userAccess.radio == 'branch'"> 
                                                            <option value="">---Select---</option>
                                                            <option ng-repeat="designation in designations">
                                                                {{designation.desigType}}
                                                            </option>
                                                        </select>
                                                    </md-input-container>
                                                </div>
                                                <div class="col-md-2" >
                                                    <div class="text-start me-3">
                                                        <button class="form-control btn-success btn" ng-disabled="userAccess.radio == 'branch'"  ng-click="getAccessListByID(userAccess.radio,userAccess.designation)">Confirm</button>
                                                    </div>
                                                </div>
                                            </div>
        
                                            <div class="row col-md-12">
                                                <div class="col-md-6" >
                                                    <md-input-container class="md-block col-md-6">
                                                        <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Profile</label>
        
                                                        <select class="col-md-6 pull-right" ng-model="userAccess.profile" ng-disabled="userAccess.radio == 'dept'"> 
                                                            <option value="">---Select---</option>
                                                            <option ng-repeat="profile in profiles">
                                                                {{profile.profileName}}
                                                            </option>
                                                        </select>
                                                    </md-input-container>
                                                </div>
                                                <div class="col-md-2" >
                                                    <div class="text-start me-3">
                                                        <button class="form-control btn-success btn" ng-disabled="userAccess.radio == 'dept'" ng-click="getAccessListByID(userAccess.radio,userAccess.profile)">Confirm</button>
                                                    </div>
                                                </div>
                                            </div>
        
                                            <md-input-container class="md-block">
                                                <label>Access Code</label>
                                                <textarea ng-model="userAccess.accessCode" rows="5"></textarea>
                                            </md-input-container>
        
                                            <div class="pull-right mt-5 btn-group"">
                                                <div class="text-start me-3">
                                                    <button class="form-control btn-success btn"  ng-click="accessConfig(userAccess.radio)">Confirm</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-12 padding-none margin-bottom-1-present" align="right">
                                        <div class="col-md-offset-8 col-md-4 padding-none">
                                        </div>
                                    </div>
                                </form>
                            </md-content>
                        </div>
                    </div>
                </section>-->
    </div>
    <%@include file="../base/footer.jsp" %>
    <script src="js/app/accessCofigController.js?v=0.004"></script>

</html>
