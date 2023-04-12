<%-- 
    Document   : newjsp_update
    Created on : Sep 8, 2022, 12:22:13 PM
    Author     : MBSL2523
--%>

<%@page    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="updateDesignationModel" class="modal fade" role="dialog" data-keyboard="false" data-backdrop="static">      
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
                                <label class="padding-left-3-present">Add New Designation</label>
                            </div>
                             <form name="regForm" role="form" ng-submit="showSaveDislog(2)">
                            <div class="box-body form-group margin-top-1-present margin-left-1-present padding-none margin-right-1-present">
                                <div class="col-md-12 padding-left-none padding-bottom-none padding-right-none">
                                    <div class="card card-body padding-left-3-present padding-right-none padding-top-none margin-bottom-1-present">
                                        <div class="row col-md-12">
                                            <div class="col-md-12" >

                                                <md-input-container class="md-block">
                                                    <label>Designation Type</label>
                                                    <input required ng-model="createDesignation.desigType" name="desigType">
                                                    <div ng-messages="regForm.desigType.$error">
                                                        <div ng-message="required">This is required.</div>                                    
                                                    </div>
                                                </md-input-container>
                                                
                                                <div class="pull-right mt-5 btn-group"">
                                                    <div class="text-start me-3">

                                                            <button class="btn btn-success" type="submit" style="position: relative;" ng-disabled="regForm.$invalid"">Update</button>  
                                                        </div>
                                                    <div class="text-end">
                                                            <button class="form-control btn-danger btn" style="position: relative;" ng-click="userTransfer()">Cancel</button>
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
            <div class="modal fade" id="updateDesigItemTypModal" role="dialog" data-keyboard="false" data-backdrop="static">
                <div class="modal-dialog modal-sm padding-top-15-present">
                    <div class="modal-content">
                        <div class="modal-body text-center">
                            <label class="">Are you sure?</label>
                        </div>
                        <div class="modal-footer text-right ">
                            <div class="mt-5 btn-group">    
                                <div class="text-start me-3">
                                <button type="button" style="position: relative;" class="btn btn-danger" ng-click="createDesig(false)">No</button>
                                </div>
                                <div class="text-end">
                                <button type="button" style="position: relative;" class="btn btn-success" ng-click="updateDesignation(createDesignation.desigType)">Yes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
