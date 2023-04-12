<%-- 
    Document   : new_record_update
    Created on : Sep 1, 2022, 11:30:39 AM
    Author     : MBSL2523
--%>

<%@page    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="newTransModelView" class="modal fade" role="dialog" data-keyboard="false" data-backdrop="static">      
    <div class=" modal-dialog modal-lg " cg-busy="createTransferBusy">
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
                                                        <input type="text" ng-model="transferUser.rqstId" data-toggle="modal" ng-disabled="true" >
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>User ID</label>
                                                        <md-icon md-svg-src="img/svgIcons/user-svgrepo-com.svg" class="name"></md-icon>
                                                        <input ng-model="transferUser.epfNo" name="userName" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Name</label>
                                                        <input type="text" ng-model="transferUser.fullName" data-toggle="modal" ng-disabled="true" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Current Branch/ Dept</label>
                                                        <input type="text" ng-model="transferUser.branchorDept" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                                                                        
                                                      <md-input-container class="md-block">
                                                        <label>Current Designation</label>
                                                        <input type="text" ng-model="transferUser.designation" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>
                                                    
                                                    <md-input-container class="md-block">
                                                        <label>Current Profile</label>
                                                        <input type="text" ng-model="transferUser.profile" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>



                                                </div>
                                                <div class="col-md-6" >


                                                    <md-input-container class="md-block">
                                                        <label>Current Till Number</label>
                                                        <input type="text" ng-model="transferUser.currentTill" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>New Branch/ Dept</label>
                                                        <input type="text" ng-model="transferUser.newBranchOrDept" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    
                                                    <md-input-container class="md-block">
                                                        <label>New Designation</label>
                                                        <input type="text" ng-model="transferUser.newDesignation" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>
                                                    
                                                    <md-input-container class="md-block">
                                                        <label>New Profile</label>
                                                        <input type="text" ng-model="transferUser.newProfile" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>New Till Number</label>
                                                        <input type="text" ng-model="transferUser.newTill" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>


                                                    <div class="pull-right mt-5 btn-group"">
                                                        <div class="text-end">
                                                            <button class="form-control btn-danger btn btn-sm" style="position: relative;" ng-click="closeModel(3)">Cancel</button>
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
