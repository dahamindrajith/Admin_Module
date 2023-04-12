<%-- 
    Document   : new_reocrd
    Created on : Jul 21, 2022, 10:02:16 AM
    Author     : MBSL15135
--%>

<%@page    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="newApproveModel" class="modal fade" role="dialog" data-keyboard="false" data-backdrop="static">      
    <div class=" modal-dialog modal-lg " cg-busy="subasset.viewSubAsstDetailsBusy">
        <div class="modal-content" cg-busy="subasset.updateRecordBusy">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body padding-none">
                <div cg-busy="subasset.saveNewRecordBusy">
                    <!-- Main content -->
                    <div class="row" >
                        <div class="col-xs-12" >
                            <div class="col-md-12 text-center">
                                <label class="padding-left-3-present">Add New Path</label>
                            </div>
                            <form name="regForm" role="form" ng-submit="showSaveDislog()">
                                <div class="box-body form-group margin-top-1-present margin-left-1-present padding-none margin-right-1-present">
                                    <div class="col-md-12 padding-left-none padding-bottom-none padding-right-none">
                                        <div class="card card-body padding-left-3-present padding-right-none padding-top-none margin-bottom-1-present">
                                            <div class="row col-md-12">

                                                <div class="col-md-6" >

                                                    <md-input-container class="md-block">
                                                        <label>Path Number</label>
                                                        <input required ng-model="approvePath.pathno" name="pathno">
                                                        <div ng-messages="regForm.pathno.$error">
                                                            <div ng-message="required">This is required.</div>                                    
                                                        </div>
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Request Type </label>
                                                        <select class="col-md-6 pull-right" ng-model="approvePath.rqstType">
                                                            <option value="">---Select---</option>
                                                            <option ng-repeat="rqstType in ReqstTypes">
                                                                {{rqstType}}
                                                            </option>
                                                        </select>
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <!--                                                    <label>Branch</label>-->
                                                        <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Branch or Department</label>

                                                        <select class="col-md-6 pull-right" ng-model="approvePath.click"> 
                                                            <option value="">---Select---</option>
                                                            <option ng-repeat="click in clicks">
                                                                {{click}}
                                                            </option>
                                                        </select>
                                                    </md-input-container>

                                                </div>

                                                <div class="col-md-6" >

                                                    <md-input-container class="md-block">
                                                        <!--                                                    <label>Branch</label>-->
                                                        <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Specific CC</label>

                                                        <select class="col-md-6 pull-right" ng-model="approvePath.cc"> 
                                                            <option value="">---Select---</option>
                                                            <option ng-repeat="cclist in cclists">
                                                                {{cclist.cc}}
                                                            </option>
                                                        </select>
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <!--                                                    <label>Branch</label>-->
                                                        <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Approval Officer</label>

                                                        <select class="col-md-6 pull-right" ng-model="approvePath.apprvlOffcer"> 
                                                            <option value="">---Select---</option>
                                                            <option ng-repeat="officer in officers">
                                                                {{officer}}
                                                            </option>
                                                        </select>
                                                    </md-input-container>

                                                    <md-input-container class="md-block">
                                                        <label>Approval Level</label>
                                                        <input required ng-model="approvePath.apprvlLevel" name="apprvlLevel">
                                                        <div ng-messages="regForm.apprvlLevel.$error">
                                                            <div ng-message="required">This is required.</div>                                    
                                                        </div>
                                                    </md-input-container>



                                                </div>

                                                <div class="justify-content-md-end btn-group"">
                                                    <div class="text-start me-3">
                                                        <button class="btn btn-success" type="submit" style="position: relative;" ng-disabled="regForm.$invalid"">Save</button>  
                                                    </div>
                                                    <div class="text-end">
                                                        <button class="form-control btn-danger btn" style="position: relative;" ng-click="userTransfer()">Cancel</button>
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
                                    <button type="button" style="position: relative;" class="btn btn-danger" ng-click="createApprovel(false)">No</button>
                                </div>
                                <div class="text-end">
                                    <button type="button" style="position: relative;" class="btn btn-success" ng-click="createApprovel(true)">Yes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
