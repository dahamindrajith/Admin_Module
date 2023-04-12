<%-- 
    Document   : new_reocrd
    Created on : Jul 21, 2022, 10:10:44 AM
    Author     : MBSL15135
--%>

<%@page    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="newAddProfileModel" class="modal fade" role="dialog" data-keyboard="false" data-backdrop="static">      
    <div class=" modal-dialog modal-lg " cg-busy="createProfilrBusy">
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
                                <label class="padding-left-3-present">Add New profile</label>
                            </div>
                            <form name="regForm" role="form">
                                <div class="box-body form-group margin-top-1-present margin-left-1-present padding-none margin-right-1-present">
                                    <div class="col-md-12 padding-left-none padding-bottom-none padding-right-none">
                                        <div class="card card-body padding-left-3-present padding-right-none padding-top-none margin-bottom-1-present">
                                            <div class="row col-md-12">
                                                <div class="col-md-6" >

                                                    <md-input-container class="md-block">
                                                        <label>Profile name</label>
                                                        <input ng-model="profileCreate.profileName" name="profileName">
                                                    </md-input-container>
                                                    
                                                    <md-input-container class="md-block">
                                                        <label>Signature Profile</label>
                                                        <input ng-model="profileCreate.sigProfile" name="profileName">
                                                    </md-input-container>
                                                    
                                                    <md-input-container class="md-block">
                                                        <label>Velocity Group</label>
                                                        <input ng-model="profileCreate.velGroup" name="profileName">
                                                    </md-input-container>


<!--                                                    <md-input-container class="md-block">
                                                        <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Signature Profile</label>

                                                        <select class="col-md-6 pull-right" ng-model="profileCreate.sigProfile">
                                                            <option value="">---Select---</option>
                                                            <option ng-repeat="sigProfile in sigProfiles">
                                                                {{sigProfile}}
                                                            </option>
                                                        </select>
                                                    </md-input-container>-->


                                                    


                                                </div>
                                                <div class="col-md-6" >
                                                    
                                                    <md-input-container class="md-block">
                                                        <!--                                                    <label>Branch</label>-->
                                                        <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Branch or Department</label>

                                                        <select class="col-md-6 pull-right" ng-model="profileCreate.click" ng-click="getCCList(profileCreate.click) ; showCC(profileCreate.click)"> 
                                                            <option value="">---Select---</option>
                                                            <option ng-repeat="click in clicks" value="{{click.value}}">
                                                                {{click.label}}
                                                            </option>
                                                        </select>
                                                    </md-input-container>

                                                    <md-input-container class="md-block" ng-show="click3=='1'">
                                                        <label class="control-label padding-left-none padding-right-none  margin-top-1-present">CC</label>

                                                        <select class="col-md-6 pull-right" ng-model="profileCreate.cc"> 
                                                            <option value="">---Select---</option>
                                                            <option ng-repeat="cclist in cclists">
                                                                {{cclist.cc}}
                                                            </option>
                                                        </select>
                                                    </md-input-container>

<!--                                                    <md-input-container class="md-block">
                                                        <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Level</label>

                                                        <select class="col-md-6 pull-right" ng-model="profileCreate.level">
                                                            <option value="">---Select---</option>
                                                            <option ng-repeat="level in levels">
                                                                {{level}}
                                                            </option>
                                                        </select>
                                                    </md-input-container>-->


                                                    <md-input-container class="md-block">
                                                        <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Till type</label>

                                                        <select class="col-md-6 pull-right" ng-model="profileCreate.tillType">
                                                            <option value="">---Select---</option>
                                                            <option ng-repeat="tillType in tillTypes">
                                                                {{tillType}}
                                                            </option>
                                                        </select>
                                                    </md-input-container>

                                                    <div class="pull-right mt-5 btn-group"">
                                                        <div class="text-start me-3">

                                                            <button class="btn btn-success btn-sm" style="position: relative;" ng-disabled="regForm.$invalid" ng-click="showSaveDislog(1)">Confirm</button>  
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
                                    <button type="button" style="position: relative;" class="btn btn-danger btn-sm" ng-click="createProfile(false)">No</button>
                                </div>
                                <div class="text-end">
                                    <button type="button" style="position: relative;" class="btn btn-success btn-sm" ng-click="createProfile(true)">Yes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
