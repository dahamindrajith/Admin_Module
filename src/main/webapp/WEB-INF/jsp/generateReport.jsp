<%-- 
    Document   : generateReport
    Created on : Jan 31, 2023, 10:35:46 AM
    Author     : MBSL2523
--%>

<%@page import="com.mbsl.model.User"%>
<%@page import="com.mbsl.dao.impl.UserDaoImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="main">
    <%@include file="../base/resources.jsp" %>
    <%@include file="../base/header.jsp" %>
    <%@include file="../base/sideBar.jsp" %>


    <div class="content-wrapper" ng-controller="reportGenerate">

        <section class="content-header">
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i>Home</a></li>
                <li class="active">New user</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content" cg-busy="createUserBusy">

            <div class="wrapper"style="margin-top: 75px" >
                <div id="regContainer" layout="column">
                    <md-toolbar style="font-size:20px;" class="md-toolbar-tools">
                        <h2>New User</h2>
                        <!--                        <span flex></span>
                                                <md-button href="test.html">Go Unit Test</md-button>-->
                    </md-toolbar>
                    <md-content layout-padding>
                        <form name="regForm" role="form">
                            <div class="col-md-12 padding-none">
                                <div class="col-md-12 padding-none">
                                    <div class="col-md-2 pull-right">
                                        <label class="text-right padding-left-none padding-right-none col-md-12">Date : {{repo.toDay}} </label>
                                        <label class="text-right control-label padding-left-none padding-right-none col-md-12">Cost Center : ${sessionScope.branch}  </label>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-12 padding-none card" >
                                <div class="card-header text-center col-md-12 padding-top-none padding-bottom-none">
                                    <div class="col-md-2  padding-none">
                                        <input type="text" class="pull-left form-control padding-right-none" ng-model="availableData" placeholder="Filter"/>
                                    </div>

                                </div>
                                <div class="card-body padding-bottom-none " >
                                    <div class="col-md-12 padding-left-none padding-right-none table-responsive"  id="tblItemType">
                                        <div class="row col-md-12" >
                                            <div class="col-md-6" >

                                                <md-input-container class="md-block">

                                                    <label class="control-label padding-left-none padding-right-none  margin-top-1-present">Report Type</label>

                                                    <select class="col-md-6 pull-right" ng-model="repo.rqstType"> 
                                                        <option value="">---Select---</option>
                                                        <option ng-repeat="click in clicks">
                                                            {{click}}
                                                        </option>
                                                    </select>
                                                </md-input-container>

                                            </div>
                                            <div class="col-md-6" >

                                                <div class="pull-right mt-5 btn-group"">
                                                       
                                                        <div class="text-end">
                                                            <button class="form-control btn-danger btn btn-sm form-control" style="position: relative;" ng-click="donloadRepo()">Download</button>
                                                        </div>
                                                    </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                           </form>
                    </md-content>
                </div>
            </div>
        </section>
    </div>
    <%@include file="../base/footer.jsp" %>
    <script src="js/app/reportGenerate.js?v=0.004"></script>

</html>
