<%-- 
    Document   : new_record_view
    Created on : Sep 6, 2022, 8:47:40 AM
    Author     : MBSL2523
--%>

<%@page    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="userIactModelView" class="modal fade" role="dialog" data-keyboard="false" data-backdrop="static">      
    <div class=" modal-dialog modal-lg " cg-busy="createInactivateBusy">
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
                                <label class="padding-left-3-present">Inactivation User</label>
                            </div>
                            <form name="regForm" role="form" ng-submit="showSaveDislog()">
                                <div class="box-body form-group margin-top-1-present margin-left-1-present padding-none margin-right-1-present">
                                    <div class="col-md-12 padding-left-none padding-bottom-none padding-right-none">
                                        <div class="card card-body padding-left-3-present padding-right-none padding-top-none margin-bottom-1-present">
                                            <div class="row col-md-12">
                                                <div class="col-md-6" >

                                                    <md-input-container class="md-block">
                                                        <label class="control-label padding-left-none padding-right-none ">Request ID</label>
                                                        <input type="text" ng-model="userInactivation.rqstId" data-toggle="modal" ng-disabled="true" >
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>User ID</label>
                                                        <md-icon md-svg-src="img/svgIcons/user-svgrepo-com.svg" class="name"></md-icon>
                                                        <input ng-model="userInactivation.epfNo" name="userName" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Name</label>
                                                        <input type="text" ng-model="userInactivation.fullName" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Current Branch/ Dept</label>
                                                        <input type="text" ng-model="userInactivation.branchorDept" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>



                                                </div>
                                                <div class="col-md-6" >

                                                    <md-input-container class="md-block">
                                                        <label>Current Profile</label>
                                                        <input type="text" ng-model="userInactivation.profile" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Current Designation</label>
                                                        <input type="text" ng-model="userInactivation.designation" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Current Till Number</label>
                                                        <input type="text" ng-model="userInactivation.currentTill" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Reason</label>
                                                        <input type="text" ng-model="userInactivation.reason" data-toggle="modal" ng-disabled="true">
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
