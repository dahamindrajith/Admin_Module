<%-- 
    Document   : new_reocrd
    Created on : Jul 21, 2022, 10:12:57 AM
    Author     : MBSL15135
--%>

<style>
    .btntra{
        position: top;
        left: 20px;
        top: 20px;
    }
</style>

<%@page    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="userIactModel" class="modal fade" role="dialog" data-keyboard="false" data-backdrop="static">      
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
                            <form name="regForm" role="form">
                                <div class="box-body form-group margin-top-1-present margin-left-1-present padding-none margin-right-1-present">
                                    <div class="col-md-12 padding-left-none padding-bottom-none padding-right-none">
                                        <div class="card card-body padding-left-3-present padding-right-none padding-top-none margin-bottom-1-present">
                                            <div class="row col-md-12">
                                                <div class="col-md-6" >



                                                    <div class="row col-md-12">

                                                        <div class="col-md-6" >

                                                            <md-input-container class="md-block">
                                                                <md-icon md-svg-src="img/svgIcons/user-svgrepo-com.svg" class="name"></md-icon>
                                                                <label>User ID</label>
                                                                <input ng-model="userInactivation.epfNo" name="epfNo">
                                                            </md-input-container>

                                                        </div>

                                                        <div class="col-md-6">
                                                            <button class="btntra btn-success form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="getToSelectButtonById(userInactivation.epfNo)">Select</button>
                                                        </div>

                                                    </div>

                                                    <!--                                                    <md-input-container class="md-block">
                                                                                                            <label>User ID</label>
                                                                                                            <md-icon md-svg-src="img/svgIcons/user-svgrepo-com.svg" class="name"></md-icon>
                                                                                                            <input required class="form-control" ng-model="'MBSL'+userInactivation.epfNo" name="userName" ng-disabled="true">
                                                                                                            <div ng-messages="regForm.userName.$error">
                                                                                                                <div ng-message="required">This is required.</div>                                    
                                                                                                            </div>
                                                                                                        </md-input-container>-->


                                                    <md-input-container class="md-block">
                                                        <label>Name</label>
                                                        <input type="text" class="form-control" ng-model="userInactivation.fullName" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Branch or Department</label>
                                                        <input type="text" class="form-control" ng-model="userInactivation.click" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Current Branch/ Dept</label>
                                                        <input type="text" class="form-control" ng-model="userInactivation.branchorDept" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>


                                                </div>
                                                <div class="col-md-6" >



                                                    <md-input-container class="md-block">
                                                        <label>Current Profile</label>
                                                        <input type="text" class="form-control" ng-model="userInactivation.profile" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Current Designation</label>
                                                        <input type="text" class="form-control" ng-model="userInactivation.designation" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label>Current Till Number</label>
                                                        <input type="text" class="form-control" ng-model="userInactivation.currentTill" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>


                                                    <md-input-container class="md-block">
                                                        <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Reason</label>

                                                        <select class="col-md-8 pull-right" ng-model="userInactivation.reason"> 
                                                            <option value="">---Select---</option>
                                                            <option ng-repeat="reason in reasons">
                                                                {{reason}}
                                                            </option>
                                                        </select>
                                                    </md-input-container>

                                                    <div class="pull-right mt-5 btn-group"">
                                                        <div class="text-start me-3">

                                                            <button class="btn btn-success btn-sm" style="position: relative;" ng-click="showSaveDislog(1)">Confirm</button>  
                                                        </div>
                                                        <div class="text-end">
                                                            <button class="form-control btn-danger btn btn-sm" style="position: relative;" ng-click="closeModel(1)">Cancel</button>
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
                                    <button type="button" style="position: relative;" class="btn btn-danger btn-sm" ng-click="userInact(false)">No</button>
                                </div>
                                <div class="text-end">
                                    <button type="button" style="position: relative;" class="btn btn-success btn-sm" ng-click="userInact(userInactivation.epfNo)">Yes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
