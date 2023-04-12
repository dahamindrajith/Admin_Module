<%-- 
    Document   : new_record_view
    Created on : Sep 12, 2022, 11:44:47 AM
    Author     : MBSL2523
--%>

<div id="tempAccesstModelView" class="modal fade" role="dialog" data-keyboard="false" data-backdrop="static">      
    <div class=" modal-dialog modal-lg " cg-busy="createTempAccBusy">
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
                                <label class="padding-left-3-present">Request Temporary Access</label>
                            </div>
                            <form name="regForm" role="form" ng-submit="showSaveDislog()">
                                <div class="box-body form-group margin-top-1-present margin-left-1-present padding-none margin-right-1-present">
                                    <div class="col-md-12 padding-left-none padding-bottom-none padding-right-none">
                                        <div class="card card-body padding-left-3-present padding-right-none padding-top-none margin-bottom-1-present">
                                            <div class="row col-md-12">
                                                <div class="col-md-6" >

                                                    <md-input-container class="md-block">
                                                        <label class="control-label padding-left-none padding-right-none ">Request ID</label>
                                                        <input type="text" ng-model="tempAccess.rqstId" data-toggle="modal" ng-disabled="true" >
                                                    </md-input-container>

                                                    
                                                    <md-input-container class="md-block">
                                                        <label>User ID</label>
                                                        <md-icon md-svg-src="img/svgIcons/user-svgrepo-com.svg" class="name"></md-icon>
                                                        <input ng-model="tempAccess.epfNo" name="userName" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Current Branch/ Dept</label>
                                                        <input type="text" ng-model="tempAccess.branchorDept" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Current Profile</label>
                                                        <input type="text" ng-model="tempAccess.profile" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Current Designation</label>
                                                        <input type="text" ng-model="tempAccess.designation" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>
                                                    
                                                     <md-input-container class="md-block">
                                                        <label>Current Till Number</label>
                                                        <input type="text" ng-model="tempAccess.currentTill" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    

                                                </div>
                                                <div class="col-md-6" >
                                                    
                                                   
                                                    <md-input-container class="col-md-5 margin-bottom-none" > 
                                                        <label class="col-md-12 padding-left-none padding-right-none margin-bottom-none">Start Date</label>
                                                        <md-datepicker ng-model="tempAccess.startDate" class="col-md-12 padding-left-none padding-right-none" ng-disabled="true"></md-datepicker>                                                                
                                                    </md-input-container>

                                                    <md-input-container class="col-md-5 margin-bottom-none" > 
                                                        <label class="col-md-12 padding-left-none padding-right-none margin-bottom-none">End Date</label>
                                                        <md-datepicker ng-model="tempAccess.endDate" class="col-md-12 padding-left-none padding-right-none" ng-disabled="true"></md-datepicker>                                                                
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>New Branch/ Dept</label>
                                                        <input type="text" ng-model="tempAccess.newBranchOrDept" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>New Profile</label>
                                                        <input type="text" ng-model="tempAccess.newProfile" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>New Designation</label>
                                                        <input type="text" ng-model="tempAccess.newDesignation" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>New Till Number</label>
                                                        <input type="text" ng-model="tempAccess.newTill" data-toggle="modal" ng-disabled="true">
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
            <div class="modal fade" id="addItemTypModal" role="dialog" data-keyboard="false" data-backdrop="static">
                <div class="modal-dialog modal-sm padding-top-15-present">
                    <div class="modal-content">
                        <div class="modal-body text-center">
                            <label class="">Are you sure?</label>
                        </div>
                        <div class="modal-footer text-right ">
                            <div class="mt-5 btn-group">    
                                <div class="text-start me-3">
                                    <button type="button" style="position: relative;" class="btn btn-danger" ng-click="tempTrans(false)">No</button>
                                </div>
                                <div class="text-end">
                                    <button type="button" style="position: relative;" class="btn btn-success" ng-click="tempAccessGrant(tempAccess.epfNo)">Yes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
