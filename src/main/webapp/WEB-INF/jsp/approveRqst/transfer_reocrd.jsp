<%-- 
    Document   : transfer_reocrd
    Created on : Sep 6, 2022, 2:10:19 PM
    Author     : MBSL2523
--%>

<%@page    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="approveTransfer" class="modal fade" role="dialog" data-keyboard="false" data-backdrop="static">      
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
                                <label class="padding-left-3-present">Transfer User</label>
                            </div>
                            <form name="regForm" role="form">
                                <div class="box-body form-group margin-top-1-present margin-left-1-present padding-none margin-right-1-present">
                                    <div class="col-md-12 padding-left-none padding-bottom-none padding-right-none">
                                        <div class="card card-body padding-left-3-present padding-right-none padding-top-none margin-bottom-1-present">
                                            <div class="row col-md-12">
                                                <div class="col-md-6" >

                                                    <md-input-container class="md-block">
                                                        <label class="control-label padding-left-none padding-right-none ">Request ID</label>
                                                        <input type="text" ng-model="approveRqst.rqstId" data-toggle="modal" ng-disabled="true" >
                                                    </md-input-container>

                                                    
                                                    <md-input-container class="md-block">
                                                        <label>User ID</label>
                                                        <md-icon md-svg-src="img/svgIcons/user-svgrepo-com.svg" class="name"></md-icon>
                                                        <input ng-model="approveRqst.epfNo" name="epfNo" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Name</label>
                                                        <input type="text" ng-model="approveRqst.fullName" data-toggle="modal" ng-disabled="true" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Current Branch/ Dept</label>
                                                        <input type="text" ng-model="approveRqst.branchorDept" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Current Profile</label>
                                                        <input type="text" ng-model="approveRqst.profile" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>
                                                    
                                                      <md-input-container class="md-block">
                                                        <label>Current Designation</label>
                                                        <input type="text" ng-model="approveRqst.designation" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>



                                                </div>
                                                <div class="col-md-6" >


                                                  

                                                    <md-input-container class="md-block">
                                                        <label>Current Till Number</label>
                                                        <input type="text" ng-model="approveRqst.currentTill" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>
                                                    
                                                    <md-input-container class="md-block">
                                                        <label>Department Or Branch</label>
                                                        <input type="text" ng-model="approveRqst.click" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                                                             
                                                    <md-input-container class="md-block">
                                                        <label>New Branch/ Dept</label>
                                                        <input type="text" ng-model="approveRqst.newBranchOrDept" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                                                                      
                                                    <md-input-container class="md-block">
                                                        <label>New Profile</label>
                                                        <input type="text" ng-model="approveRqst.newProfile" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                                                                    
                                                    <md-input-container class="md-block">
                                                        <label>New Designation</label>
                                                        <input type="text" ng-model="approveRqst.newDesignation" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                                                                       
                                                    <md-input-container class="md-block">
                                                        <label>New Till Number</label>
                                                        <input type="text" ng-model="approveRqst.newTill" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>


                                                    <div class="pull-right mt-5 btn-group"">
                                                    <div class="text-start me-3">

                                                        <button class="btn btn-success btn-sm" style="position: relative;" ng-disabled="regForm.$invalid" ng-click="showSaveDislog(2)">Approve</button>  
                                                        </div>
                                                    <div class="text-end">
                                                            <button class="form-control btn-danger btn btn-sm" style="position: relative;" ng-click="showSaveDislog(6)">Reject</button>
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
            <div class="modal fade" id="transItemTypModal" role="dialog" data-keyboard="false" data-backdrop="static">
                <div class="modal-dialog modal-sm padding-top-15-present">
                    <div class="modal-content">
                        <div class="modal-body text-center">
                            <label class="">Are you sure?</label>
                        </div>
                        <div class="modal-footer text-right ">
                            <div class="mt-5 btn-group">    
                                <div class="text-start me-3">
                                    <button type="button" style="position: relative;" class="btn btn-danger btn-sm" ng-click="approvedMessage(false,approveRqst.epfNo,'User Transfer',approveRqst.click)">No</button>
                                </div>
                                <div class="text-end">
                                    <button type="button" style="position: relative;" class="btn btn-success btn-sm" ng-click="approvedMessage(true,approveRqst.epfNo,'User Transfer',approveRqst.click)">Yes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="transItemRejectTypModal" role="dialog" data-keyboard="false" data-backdrop="static">
                <div class="modal-dialog modal-sm padding-top-15-present">
                    <div class="modal-content">
                        <div class="modal-body text-center">
                            <label class="">Are you sure?</label>
                        </div>
                        <div class="modal-footer text-right ">
                            <div class="mt-5 btn-group">    
                                <div class="text-start me-3">
                                    <button type="button" style="position: relative;" class="btn btn-danger btn-sm" ng-click="rejecctMessage(false,approveRqst.epfNo,'User Transfer',approveRqst.click)">No</button>
                                </div>
                                <div class="text-end">
                                    <button type="button" style="position: relative;" class="btn btn-success btn-sm" ng-click="rejecctMessage(true,approveRqst.epfNo,'User Transfer',approveRqst.click)">Yes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
