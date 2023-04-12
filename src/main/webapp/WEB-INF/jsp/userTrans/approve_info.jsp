<%-- 
    Document   : approve_info
    Created on : Jan 30, 2023, 9:03:41 AM
    Author     : MBSL2523
--%>

<%@page    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="approveInfoUserTranse" class="modal fade" role="dialog" data-keyboard="false" data-backdrop="static">      
    <div class=" modal-dialog modal-sm " cg-busy="subasset.viewSubAsstDetailsBusy">
        <div class="modal-content" cg-busy="subasset.updateRecordBusy">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body padding-none">
                <div cg-busy="subasset.saveNewRecordBusy">
                    <!-- Main content -->
                    <div class="row" >
                        <div class="col-md-12" >
                            <form name="regForm" role="form">
                                <div class="box-body form-group margin-top-1-present margin-left-1-present padding-none margin-right-1-present">
                                    <div class="col-md-12 padding-left-none padding-bottom-none padding-right-none">
                                        <div class="card card-body padding-left-3-present padding-right-none padding-top-none margin-bottom-1-present">
                                            <div class="row col-md-12">

                                                <md-input-container class="md-block">
                                                    <label>Approve User</label>
                                                    <input class="text-center" ng-model="transferUser.fullName" name="profileName" ng-disabled="true">
                                                </md-input-container>
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
