<%-- 
    Document   : new_reocrd
    Created on : Nov 10, 2022, 10:42:07 AM
    Author     : MBSL2523
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="newBranchAccessConfig" class="modal fade" role="dialog" data-keyboard="false" data-backdrop="static">      
    <div class=" modal-dialog modal-lg " cg-busy="createAccessBusy">
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
                                <label class="padding-left-3-present">Add New Access For Branch</label>
                            </div>
                            <form name="regForm" role="form">
                                <div class="box-body form-group margin-top-1-present margin-left-1-present padding-none margin-right-1-present">
                                    <div class="col-md-12 padding-left-none padding-bottom-none padding-right-none">
                                        <div class="card card-body padding-left-3-present padding-right-none padding-top-none margin-bottom-1-present">
                                            <div class="row col-md-12">
                                            <div class="col-md-12" >

                                                <md-input-container class="md-block col-md-6">
                                                        <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Profile</label>
        
                                                        <select class="col-md-6 pull-right" ng-model="userAccess.profile"> 
                                                            <option value="">---Select---</option>
                                                            <option ng-repeat="profilelist in profiles">
                                                                {{profilelist.profileName}}
                                                            </option>
                                                        </select>
                                                    </md-input-container>
                                                
                                                <md-input-container class="md-block">
                                                <label>Access Code</label>
                                                <textarea ng-model="userAccess.accessCode" rows="5"></textarea>
                                            </md-input-container>
                                                
                                                <div class="pull-right mt-5 btn-group"">
                                                    <div class="text-start me-3">

                                                        <button class="btn btn-success btn-sm" type="submit" style="position: relative;" ng-click="showSaveDislog(1)">Confirm</button>  
                                                        </div>
                                                    <div class="text-end">
                                                            <button class="form-control btn-danger btn btn-sm" style="position: relative;" ng-click="closeMdel(1)">Cancel</button>
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
                <div class="modal fade" id="addBranchTypModal" role="dialog" data-keyboard="false" data-backdrop="static">
                <div class="modal-dialog modal-sm padding-top-15-present">
                    <div class="modal-content">
                        <div class="modal-body text-center">
                            <label class="">Are you sure?</label>
                        </div>
                        <div class="modal-footer text-right ">
                            <div class="mt-5 btn-group">    
                                <div class="text-start me-3">
                                    <button type="button" style="position: relative;" class="btn btn-danger btn-sm form-control" ng-click="accessConfig('branch',false)">No</button>
                                </div>
                                <div class="text-end">
                                    <button type="button" style="position: relative;" class="btn btn-success btn-sm form-control" ng-click="accessConfig('branch',true)">Yes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </div>
    </div>
</div>
