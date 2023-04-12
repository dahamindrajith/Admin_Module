<%-- 
    Document   : sideBar.jsp
    Created on : Mar 28, 2022, 11:47:28 AM
    Author     : MBSL2395
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<aside class="main-sidebar">
    <section class="sidebar">
        <ul class="sidebar-menu" ng-init="getActiveTab()">
            <li class="header">Admin Module V1.0</li>

            <li ng-class="mainTb2" class="treeview">
                <a href="#" class="text-decoration-none">
                    <i class="fa fa-cogs" ></i> <span>Configuration</span>
                    <span class="pull-right-container">
                        <span class="fa fa-angle-left pull-right"></span>
                    </span>
                </a>
                <ul class="treeview-menu">
                    <li ng-class="subTb6"><a href="aprove_path" class="text-decoration-none"><i class="fa fa-share-square-o"></i>Approval Path</a></li>
                    <li ng-class="subTb7"><a href="profile_create" class="text-decoration-none"><i class="fa fa-files-o"></i>Configure Profile</a></li>
                    <li ng-class="subTb8"><a href="teller_create" class="text-decoration-none"><i class="fa fa-files-o"></i>Configure Teller</a></li>
                    <li ng-class="subTb9"><a href="designation_create" class="text-decoration-none"><i class="fa fa-files-o"></i>Configure Designation</a></li>
                    <li ng-class="subTb10"><a href="access_config" class="text-decoration-none"><i class="fa fa-files-o"></i>Velocity Access Configure</a></li>
                    <li ng-class="subTb11"><a href="hr_table_update" class="text-decoration-none"><i class="fa fa-table"></i>HR Table Update </a></li>
                </ul>
            </li>
            <li ng-class="mainTb1" class="treeview">
                <a href="#" class="text-decoration-none">
                    <i class="fa fa-users" ></i> <span>User Access</span>
                    <span  class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu">
                    <li ng-class="subTb1"><a href="add_user" class="text-decoration-none"><i class="fa fa-user"></i>New User</a></li>
                    <li ng-class="subTb2"><a href="user_transfer" class="text-decoration-none"><i class="fa fa-user"></i>User Transfer</a></li>
                    <li ng-class="subTb3"><a href="user_inactivation" class="text-decoration-none"><i class="fa fa-user-times"></i>User Inactivation</a></li>
                    <li ng-class="subTb4"><a href="temp_access" class="text-decoration-none"><i class="fa fa-user"></i>Temporary Access </a></li>
                    <li ng-class="subTb5"><a href="aprove_reqst" class="text-decoration-none"><i class="fa fa-share-square-o"></i>Approve Request </a></li>
                    <!--                    <li ng-class="subTb2"><a href="sub_asset" class="text-decoration-none"><i class="fa fa-edit"></i>Sub Asset Type</a></li>
                    <li ng-class="subTb3"><a href="approval_path" class="text-decoration-none"><i class="fa fa-edit"></i>Approval Path</a></li>-->
                </ul>
            </li>
            <li ng-class="mainTb1" class="treeview">
                <a href="#" class="text-decoration-none">
                    <i class="fa fa-file" ></i> <span>Others</span>
                    <span  class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu">
                    <li ng-class="subTb12"><a href="repot_gen" class="text-decoration-none"><i class="fa fa-user"></i>Available Users</a></li>
                    <li ng-class="subTb13"><a href="create_email" class="text-decoration-none"><i class="fa fa-user"></i>Create Email</a></li>
                    <li ng-class="subTb14"><a href="t4s_access" class="text-decoration-none"><i class="fa fa-user"></i>T4S Access</a></li>
                </ul>
            </li>
        </ul>     
    </section>
</aside> 
<style>
    .main-sidebar {
        position: fixed ;
    }
</style>
