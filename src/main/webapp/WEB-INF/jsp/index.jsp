<%-- 
    Document   : index
    Created on : Mar 31, 2022, 2:25:50 PM
    Author     : MBSL2395
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="main">
    <%@include file="../base/resources.jsp" %>
    <%@include file="../base/header.jsp" %>
    <%@include file="../base/sideBar.jsp" %>

    <div class="content-wrapper" ng-controller="indexController">

        <section class="content-header">        
            <h1>
                HOME
                <small>Preview</small>
            </h1>
            <ol class="breadcrumb">
                <li class="me-2"><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                <li class="active">&nbsp; HOME</li>
            </ol>     
        </section>
        <!-- Main content -->
        <section class="content">            
            <div class="row" >
                <div class="col-xs-12" id="scrollBillEntry">
                    <div class="box box-primary margin-bottom-none">
                        <div class="box-header with-border">
                        </div>
                        <div class="box-body form-group margin-top-1-present margin-left-1-present margin-left-1-present padding-none margin-right-1-present">
                            <div class="col-md-12 padding-none" >
                                <div class="col-md-2 pull-right">
                                    <label class="text-right padding-left-none padding-right-none col-md-12">Entry Date : {{index.toDay}} </label>
                                    <label class="text-right control-label padding-left-none padding-right-none col-md-12">Branch :${sessionScope.branch} </label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>     
    </div>
    <%@include file="../base/footer.jsp" %> 
<script src="js/app/indexController.js?v=0.004"></script>    
</html>
