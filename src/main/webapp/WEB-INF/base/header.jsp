<%-- 
    Document   : header
    Created on : Mar 28, 2022, 11:04:10 AM
    Author     : MBSL2395
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">   
    <title>Admin</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <header class="main-header">
        <!-- Logo -->
        <a href="/" class="logo text-decoration-none">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini">
                <img class="icon" src="/img/velocity_icon50_5.png" />
            </span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><img class="icon" src="/img/velocity_icon50_5.png" />
                <b> V E L O C I T Y</b></span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top pb-0 pt-0">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle text-decoration-none" style="height: 3rem" data-toggle="offcanvas" role="button" ng-click="setSideBar()">
            </a>
            <div class="navbar-custom-menu me-3">
                <ul class="nav navbar-nav">
                    <li class="dropdown user user-menu">
                        <a href="#" class="text-decoration-none" data-bs-toggle="dropdown">
                            <img src="/velocity-services/getUserImage?user=${sessionScope.user}" class="user-image" alt="User Image">
                            <span class="hidden-xs">${sessionScope.full_name}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- User image -->
                            <li class="user-header">
                                <img src="/velocity-services/getUserImage?user=${sessionScope.user}" class="rounded-circle" alt="User Image">
                                <p>
                                    ${sessionScope.full_name} - ${sessionScope.designation} (${sessionScope.dept_branch})
                                </p>
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-right">
                                    <a href="sign_out" class="btn btn-default btn-flat text-decoration-none">Sign out</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
</body>
<style>
    .logo{
        position: fixed;
    }
    .skin-blue .main-header {
        position: fixed;
        width: 100%;
    }
</style>
<script type="text/javascript">
    //alert("Time STARTED!");
    var IDLE_TIMEOUT = 900; //seconds
    var _idleSecondsCounter = 0;
    document.onclick = function () {
        _idleSecondsCounter = 0;
    };
    document.onmousemove = function () {
        _idleSecondsCounter = 0;
    };
    document.onkeypress = function () {
        _idleSecondsCounter = 0;
    };
    window.setInterval(CheckIdleTime, 1000);

    function CheckIdleTime() {
        _idleSecondsCounter++;
        var oPanel = document.getElementById("SecondsUntilExpire");
        if (oPanel)
            oPanel.innerHTML = (IDLE_TIMEOUT - _idleSecondsCounter) + "";
        if (_idleSecondsCounter >= IDLE_TIMEOUT) {
            //alert("Time expired!");
            document.location.href = "/velocity-admin/sign_out";
            window.close();
        }
    }
</script>
