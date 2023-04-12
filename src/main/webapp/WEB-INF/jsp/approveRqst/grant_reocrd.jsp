<%-- 
    Document   : new_reocrd
    Created on : Sep 6, 2022, 12:32:21 PM
    Author     : MBSL2523
--%>

<%@page    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style>
    md-select-menu-container {
        z-index: 101;
    }

</style>
<div id="approveGrant" class="modal fade" role="dialog" data-keyboard="false" data-backdrop="static">      
    <div class=" modal-dialog modal-lg " cg-busy="createApproveRqstBusy">
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
                                                        <input ng-model="approveRqst.rqstId" data-toggle="modal" ng-disabled="true" >
                                                    </md-input-container>



                                                    <md-input-container class="md-block">
                                                        <label>User ID</label>
                                                        <md-icon md-svg-src="img/svgIcons/user-svgrepo-com.svg" class="name"></md-icon>
                                                        <input ng-model="approveRqst.epfNo" name="userName" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Full Name</label>
                                                        <input ng-model="approveRqst.fullName" name="fullName" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Designation</label>
                                                        <input ng-model="approveRqst.designation" name="designation" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Email</label>
                                                        <input ng-model="approveRqst.emailAddress" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>


                                                </div>
                                                <div class="col-md-6" >
                                                    
                                                    <md-input-container class="md-block">
                                                        <label>Department Or Branch</label>
                                                        <input ng-model="approveRqst.click" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Department / Branch</label>
                                                        <input ng-model="approveRqst.branchorDept" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Use Profile</label>
                                                        <input ng-model="approveRqst.profile" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Password</label>
                                                        <input type="password" ng-model="approveRqst.password" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label class="control-label padding-left-none padding-right-none ">Mobile No</label>
                                                        <input ng-maxlength="10" ng-model="approveRqst.mobileNo" name="mobileNo" ng-disabled="true">
                                                    </md-input-container>

                                                    <div class="pull-right mt-5 btn-group"">
                                                        <div class="text-start me-3">

                                                            <button class="btn btn-success btn-sm" style="position: relative;" ng-disabled="regForm.$invalid" ng-click=" showSaveDislog(1)">Approve</button>  
                                                        </div>
                                                        <div class="text-end">
                                                            <button class="form-control btn-danger btn btn-sm" style="position: relative;" ng-click="showSaveDislog(5)">Reject</button>
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
            <div class="modal fade" id="addItemTypModal" role="dialog" data-keyboard="false" data-backdrop="static">
                <div class="modal-dialog modal-sm padding-top-15-present">
                    <div class="modal-content">
                        <div class="modal-body text-center">
                            <label class="">Are you sure?</label>
                        </div>
                        <div class="modal-footer text-right ">
                            <div class="mt-5 btn-group">    
                                <div class="text-start me-3">
                                    <button type="button" style="position: relative;" class="btn btn-danger btn-sm" ng-click="approvedMessage(false,approveRqst.epfNo,'User Grant',approveRqst.click)">No</button>
                                </div>
                                <div class="text-end">
                                    <button type="button" style="position: relative;" class="btn btn-success btn-sm" ng-click="approvedMessage(true,approveRqst.epfNo,'User Grant',approveRqst.click)">Yes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="addItemRejectTypModal" role="dialog" data-keyboard="false" data-backdrop="static">
                <div class="modal-dialog modal-sm padding-top-15-present">
                    <div class="modal-content">
                        <div class="modal-body text-center">
                            <label class="">Are you sure?</label>
                        </div>
                        <div class="modal-footer text-right ">
                            <div class="mt-5 btn-group">    
                                <div class="text-start me-3">
                                    <button type="button" style="position: relative;" class="btn btn-danger btn-sm" ng-click="rejecctMessage(false,approveRqst.epfNo,'User Grant',approveRqst.click)">No</button>
                                </div>
                                <div class="text-end">
                                    <button type="button" style="position: relative;" class="btn btn-success btn-sm" ng-click="rejecctMessage(true,approveRqst.epfNo,'User Grant',approveRqst.click)">Yes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
