<%-- 
    Document   : new_record1_view
    Created on : Aug 24, 2022, 9:15:56 AM
    Author     : MBSL2523
--%>

<%@page import="com.mbsl.model.User"%>
<%@page import="com.mbsl.dao.impl.UserDaoImpl"%>
<%@page    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style>
    md-select-menu-container {
        z-index: 101;
    }

</style>
<div id="newAddUserModelView" class="modal fade" role="dialog" data-keyboard="false" data-backdrop="static">      
    <div class=" modal-dialog modal-lg " cg-busy="createUserBusy">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body padding-none">
                <div>
                    <!-- Main content -->
                    <div class="row" >
                        <div class="col-xs-12" >
                            <div class="col-md-12 text-center">
                                <label class="padding-left-3-present">Add New User</label>
                            </div>
                            <form name="regForm" role="form">
                                <div class="box-body form-group margin-top-1-present margin-left-1-present padding-none margin-right-1-present">
                                    <div class="col-md-12 padding-left-none padding-bottom-none padding-right-none">
                                        <div class="card card-body padding-left-3-present padding-right-none padding-top-none margin-bottom-1-present">
                                            <div class="row col-md-12">
                                                <div class="col-md-6" >




                                                    <md-input-container class="md-block">
                                                        <label>Request ID</label>
                                                        <input ng-model="user.rqstId" data-toggle="modal" ng-disabled="true" >
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>User ID</label>
                                                        <md-icon md-svg-src="img/svgIcons/user-svgrepo-com.svg" class="name"></md-icon>
                                                        <input ng-model="user.epfNo" name="userName" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Full Name</label>
                                                        <input ng-model="user.fullName" name="fullName" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Email</label>
                                                        <input ng-model="user.emailAddress" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Department / Branch</label>
                                                        <input ng-model="user.branchorDept" name="fullName" ng-disabled="true">
                                                    </md-input-container>



                                                </div>
                                                <div class="col-md-6" >

                                                    <md-input-container class="md-block">
                                                        <label>Designation</label>
                                                        <input ng-model="user.designation" name="fullName" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Use Profile</label>
                                                        <input ng-model="user.profile" name="fullName" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Till Number</label>
                                                        <input ng-model="user.currentTill" name="fullName" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Password</label>
                                                        <input type="password" ng-model="user.password" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label class="control-label padding-left-none padding-right-none ">Mobile No</label>
                                                        <input ng-maxlength="10" ng-model="user.mobileNo" name="mobileNo" ng-disabled="true">
                                                    </md-input-container>

                                                    <div class="pull-right mt-5 btn-group"">
                                                        <div class="text-end">
                                                            <button class="btn-danger btn btn-sm form-control" style="position: relative;" ng-click="closeModel(3)">Cancel</button>
                                                        </div>
                                                    </div>

                                                </div>                                            

                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
