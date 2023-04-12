<%-- 
    Document   : new_record1
    Created on : Jul 21, 2022, 9:58:51 AM
    Author     : MBSL15135
--%>

<%@page    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style>
    md-select-menu-container {
        z-index: 101;
    }

</style>
<div id="newAddUserModel" class="modal fade" role="dialog" data-keyboard="false" data-backdrop="static">      
    <div class=" modal-dialog modal-lg" cg-busy="createUserBusy">
        <div class="modal-content"  class="modal-body padding-none">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div>
                <!-- Main content -->
                <div class="row">
                    <div class="col-xs-12">
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
                                                    <md-icon md-svg-src="img/svgIcons/user-svgrepo-com.svg" class="name"></md-icon>
                                                    <label>User ID</label>
                                                    <input ng-model="user.epfNo" name="epfNo">
                                                </md-input-container>


                                                <!--                                                    <md-input-container class="md-block">
                                                                                                        <label>User ID</label>
                                                                                                        <md-icon md-svg-src="img/svgIcons/user-svgrepo-com.svg" class="name"></md-icon>
                                                                                                        <input required class="form-control" ng-model="'MBSL'+user.epfNo" name="userName" ng-disabled="true">
                                                                                                        <div ng-messages="regForm.userName.$error">
                                                                                                            <div ng-message="required">This is required.</div>                                    
                                                                                                        </div>
                                                                                                    </md-input-container>-->


                                                <md-input-container class="md-block">
                                                    <label>Full Name</label>
                                                    <input ng-model="user.fullName" name="fullName">
                                                </md-input-container>

                                                <md-input-container class="md-block">
                                                    <label class="control-label padding-left-none padding-right-none ">Email</label>
                                                    <input ng-model="user.emailAddress" data-toggle="modal" name="emailAddress">
                                                </md-input-container>

                                                <md-input-container class="md-block">
                                                    <!--                                                    <label>Branch</label>-->
                                                    <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Branch or Department</label>

                                                    <select class="col-md-6 pull-right" ng-model="user.click" ng-click="getAllBranchOrDept(user.click);"> 
                                                        <option value="">---Select---</option>
                                                        <option ng-repeat="click in clicks">
                                                            {{click}}
                                                        </option>
                                                    </select>
                                                </md-input-container>


                                            </div>
                                            <div class="col-md-6" >

                                                <md-input-container class="md-block">
                                                    <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Department / Branch </label>

                                                    <select class="col-md-6 pull-right" ng-model="user.branchorDept"> 
                                                        <option value="">---Select---</option>
                                                        <option ng-repeat="branch in branches">
                                                            {{branch.branchorDept}}
                                                        </option>
                                                    </select>
                                                </md-input-container>

                                                <md-input-container class="md-block">
                                                    <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Designation </label>

                                                    <select class="col-md-6 pull-right" ng-model="user.designation" ng-click="getAllProfilesByBoD(user.click, user.branchorDept); getTellerByUBranch(user.branchorDept)"> 
                                                        <option value="">---Select---</option>
                                                        <option ng-repeat="desigType in designations">
                                                            {{desigType.desigType}}
                                                        </option>
                                                    </select>
                                                </md-input-container>

                                                <md-input-container class="md-block">
                                                    <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Use Profile</label>

                                                    <select class="col-md-6 pull-right" ng-model="user.profile"  ng-click="testTeller(user.profile)"> 
                                                        <option value="">---Select---</option>
                                                        <option ng-repeat="profile in profiles">
                                                            {{profile.profileName}}
                                                        </option>
                                                    </select>
                                                </md-input-container>

                                                <!--                                                    <md-input-container class="md-block">
                                                                                                                                                            <label>Branch</label>
                                                                                                        <md-checkbox ng-model="user.click2" aria-label="Checkbox 2" ng-value="1">Teller Or Main Teller</md-checkbox>
                                                                                                    </md-input-container>-->

                                                <md-input-container class="md-block" ng-show="click3 == '1'">
                                                    <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Till Number</label>

                                                    <select class="col-md-6 pull-right" ng-model="user.currentTill" > 
                                                        <option value="">---Select---</option>
                                                        <option ng-repeat="newTill in Tillss">
                                                            {{newTill.branch}}
                                                        </option>
                                                    </select>
                                                </md-input-container>

                                                <md-input-container class="md-block">
                                                    <label>Password</label>
                                                    <input type="password" class="form-control" ng-model="user.password" data-toggle="modal" ng-disabled="true">
                                                </md-input-container>


                                                <md-input-container class="md-block">
                                                    <label class="control-label padding-left-none padding-right-none ">Mobile No</label>
                                                    <input ng-maxlength="10" ng-model="user.mobileNo" name="mobileNo">

                                                </md-input-container>

                                                <div class="pull-right mt-5 btn-group"">
                                                    <div class="text-start me-3">

                                                        <button class="btn btn-success btn-sm form-control" style="position: relative;" ng-disabled="regForm.$invalid" ng-click="showSaveDislog(1)">Save</button>  
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

            <div class="modal fade" id="addItemTypModal" role="dialog" data-keyboard="false" data-backdrop="static">
                <div class="modal-dialog modal-sm padding-top-15-present">
                    <div class="modal-content">
                        <div class="modal-body text-center">
                            <label class="">Are you sure?</label>
                        </div>
                        <div class="modal-footer text-right ">
                            <div class="mt-5 btn-group">    
                                <div class="text-start me-3">
                                    <button type="button" style="position: relative;" class="btn btn-danger btn-sm form-control" ng-click="createUser(false)">No</button>
                                </div>
                                <div class="text-end">
                                    <button type="button" style="position: relative;" class="btn btn-success btn-sm form-control" ng-click="createUser(true)">Yes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
