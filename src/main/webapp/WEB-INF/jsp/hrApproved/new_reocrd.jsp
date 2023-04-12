<%-- 
    Document   : new_reocrd
    Created on : Oct 27, 2022, 10:09:18 AM
    Author     : MBSL2523
--%>
<style>
    .btnhr{
        position: top;
        left: 20px;
        top: 20px;
    }
</style>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="newAddApprovelModal" class="modal fade" role="dialog" data-keyboard="false" data-backdrop="static">      
    <div class=" modal-dialog modal-lg " cg-busy="createHrSpecBusy">
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
                                <label class="padding-left-3-present">Add User</label>
                            </div>
                            <form name="regForm">
                                <div class="box-body form-group margin-top-1-present margin-left-1-present padding-none margin-right-1-present">
                                    <div class="col-md-12 padding-left-none padding-bottom-none padding-right-none">
                                        <div class="card card-body padding-left-3-present padding-right-none padding-top-none margin-bottom-1-present">
                                            <div class="row col-md-12">
                                                <div class="col-md-6" >

                                                    <div class="row col-md-12" >
                                                        <div class="col-md-6" >
                                                            <md-input-container class="md-block">
                                                                <label>User ID</label>
                                                                <input ng-model="approveList.epfNo" name="epfNo">
                                                            </md-input-container>
                                                        </div>

                                                        <div class="col-6 text-center">
                                                            <button class="btnhr btn-success form-control btn-sm" style="position: relative;" data-bs-toggle="modal" ng-click="getListHrById(approveList.epfNo)">Select</button>
                                                        </div>
                                                    </div>

                                                    <md-input-container class="md-block">
                                                        <label>Name</label>
                                                        <input type="text" class="form-control" ng-model="approveList.fullName" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Profile</label>
                                                        <input type="text" class="form-control" ng-model="approveList.profile" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                </div>
                                                <div class="col-md-6" >

                                                    <md-input-container class="md-block">
                                                        <label>Branch Or Department</label>
                                                        <input type="text" class="form-control" ng-model="approveList.branchorDept" data-toggle="modal" ng-disabled="true">
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label class="control-label padding-left-none padding-right-none  margin-top-1-present">User Profile</label>

                                                        <select class="col-md-6 pull-right" ng-model="approveList.newProfile"> 
                                                            <option value="">---Select---</option>
                                                            <option ng-repeat="profiles in assignDataP">
                                                                {{profiles.profileName}}
                                                            </option>
                                                        </select>
                                                    </md-input-container>

                                                    <div class="pull-right mt-5 btn-group">
                                                        <div class="text-start me-3">

                                                            <button class="btn btn-success btn-sm form-control" style="position: relative;" ng-click="showSaveDislogHr(1)">Save</button>  
                                                        </div>
                                                        <div class="text-end">
                                                            <button class="form-control btn-danger btn btn-sm form-control" style="position: relative;" ng-click="closeModel(1)">Cancel</button>
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
            <div class="modal fade" id="addItemTypModalHr" role="dialog" data-keyboard="false" data-backdrop="static">
                <div class="modal-dialog modal-sm padding-top-15-present">
                    <div class="modal-content">
                        <div class="modal-body text-center">
                            <label class="">Are you sure?</label>
                        </div>
                        <div class="modal-footer text-right ">
                            <div class="mt-5 btn-group">    
                                <div class="text-start me-3">
                                    <button type="button" style="position: relative;" class="btn btn-danger btn-sm form-control" ng-click="createHrList(false)">No</button>
                                </div>
                                <div class="text-end">
                                    <button type="button" style="position: relative;" class="btn btn-success btn-sm form-control" ng-click="createHrList(true)">Yes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
