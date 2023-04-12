/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


app.controller("userInactivationController", function ($scope, $http, $mdDialog, $filter) {

    $scope.reasons = ["Termination", "Resignation", "End of Probation Period", " Any other"];


    $scope.userInactivation = {

        rqstId: "",
        epfNo: "",
        userName: "",
        fullName: "",
        designation: "",
        emailAddress: "",
        branchorDept: "",
        profile: "",
        password: "",
        mobileNo: "",
        click: "",
        status: "",
        currentTill: "",
        click2: "",
        newBranchOrDept: "",
        newProfile: "",
        newDesignation: "",
        newTill: "",
        reason: "",
        startDate: "",
        endDate: "",
        rqstType: ""

    };

    $scope.user = {

        rqstId: "",
        epfNo: "",
        userName: "",
        fullName: "",
        designation: "",
        emailAddress: "",
        branchorDept: "",
        profile: "",
        password: "",
        mobileNo: "",
        click: "",
        status: "",
        currentTill: "",
        click2: "",
        newBranchOrDept: "",
        newProfile: "",
        newDesignation: "",
        newTill: "",
        reason: "",
        startDate: "",
        endDate: "",
        rqstType: ""

    };

    $scope.userInactivation.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');



    $scope.userInact = function (Epf) {

        if (Epf == false) {

            $("#addItemTypModal").modal("hide");
            $("#userIactModel").modal("hide");
            $scope.showClearModal();

        } else {


            $("#addItemTypModal").modal("hide");
            $("#userIactModel").modal("hide");

            var method = "POST";
            var url = 'inactivate/user_inactivate';
            $scope.createInactivateBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.userInactivation),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllInactUsers();
                        if (data == "User already inactivated") {
                            sweetAlert("Alert..!", data, "error");
                        } else if (data == 'User not available') {
                            sweetAlert("Alert..!", data, "error");
                        } else {
                            sweetAlert("Alert..!", data, "success");
                        }

                    }
                ]
            });
        }
    };

    $scope.getAllInactUsers = function () {

        $scope.assignData = [];
        var method = "GET";
        var url = 'inactivate/inactivate_users';
        $scope.createInactivateBusy = $http({
            method: method,
            url: url,

            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.assignData = JSON.parse(data);

                }
            ]

        });
        console.log($scope.assignData);
    };

    $scope.getListUserIactById = function (Epf) {

        $scope.userInactivation.epfNo = Epf;

        $scope.getUserInactById = [];
        var method = "POST";
        var url = 'inactivate/get_list_user_inact_by_epf';


        $scope.createInactivateBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.userInactivation),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.userInactivation = JSON.parse(data);
                    $scope.checkDate();

                }
            ]

        });
        console.log($scope.userInactivation);

    };

    $scope.selectedDltUserI = function (Epf) {

        $scope.dltEpf = Epf;

    };

    $scope.getAppInfoI = function (rqstType, epf, level, branchOrDept) {

        $scope.userInactivation.rqstType = rqstType;
        $scope.userInactivation.epfNo = epf;
        $scope.userInactivation.status = level;
        $scope.userInactivation.newBranchOrDept = branchOrDept;


        var method = "POST";
        var url = 'inactivate/get_approve_info_I';
        $scope.createInactivateBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.userInactivation),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.userInactivation = JSON.parse(data);
                    $scope.checkDate();
                    console.log($scope.userInactivation);


                }
            ]

        });

    };

    $scope.getToSelectButtonById = function (Epf) {

        $scope.userInactivation.epfNo = Epf;

        $scope.selectedData = [];
        var method = "POST";
        var url = 'inactivate/get_list_to_select_btn_inact_by_epf';


        $scope.createInactivateBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.userInactivation),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.userInactivation = JSON.parse(data);
                    $scope.checkDate();
//                    var rqstid = $scope.generateRequestId('3');

                }
            ]

        });
        console.log($scope.userInactivation);

    };


    $scope.showUpdateModalInact = function (rqstid, epf, name, branchordept, profile, designation, till, reason) {

        $scope.userInactivation.rqstId = rqstid;
        $scope.userInactivation.epfNo = epf;
        $scope.userInactivation.fullName = name;
        $scope.userInactivation.branchorDept = branchordept;
        $scope.userInactivation.profile = profile;
        $scope.userInactivation.designation = designation;
        $scope.userInactivation.currentTill = till;
        $scope.userInactivation.reason = reason;

//        $("#newAddUserModelView").modal("show");
    };

    $scope.showClearModal = function (rqstid, epf, name, branchordept, profile, designation, till, reason) {

        $scope.userInactivation.rqstId = '';
        $scope.userInactivation.epfNo = '';
        $scope.userInactivation.fullName = '';
        $scope.userInactivation.branchorDept = '';
        $scope.userInactivation.profile = '';
        $scope.userInactivation.designation = '';
        $scope.userInactivation.currentTill = '';
        $scope.userInactivation.reason = '';
//        $("#newAddUserModelView").modal("show");

        return 'success';
    };

    $scope.updateUserIact = function (Epf) {

        $scope.userInactivation.epfNo = Epf;

        if (Epf == false) {

            $("#userIactModelUpdate").modal("hide");
            $("#updateItemTypModal").modal("hide");
            $scope.showClearModal();

        } else {

            $("#userIactModelUpdate").modal("hide");
            $("#updateItemTypModal").modal("hide");

            var method = "PUT";
            var url = 'inactivate/update_user_inact';
            $scope.createInactivateBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.userInactivation),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllInactUsers();

                        sweetAlert("Alert..!", data, "success");


                    }
                ]
            });
            console.log($scope.userInactivation.epfNo);
        }
    };

    $scope.deleteInactUser = function (resp) {

        $scope.userInactivation.epfNo = $scope.dltEpf;

        if (resp == true) {

            $("#dltInactUserTypModal").modal("hide");

            var method = "DELETE";
            var url = 'inactivate/delete_inact_user';
            $scope.createInactivateBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.userInactivation),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllInactUsers();

                        sweetAlert("Alert..!", data, "success");

                    }
                ]
            });
        } else {

            $("#dltInactUserTypModal").modal("hide");

        }
        console.log($scope.user.epfNo);
    };

//    $scope.generateRequestId = function (path) {
//
//        $scope.user.rqstType = path;
//
//        $scope.assignDataById = [];
//        var method = "POST";
//        var url = 'requsetid';
//
//
//        $scope.createUserBusy = $http({
//            method: method,
//            url: url,
//            data: angular.toJson($scope.user),
//            headers: {
//                'Content-Type': 'application/json'
//            },
//            transformResponse: [
//                function (data) {
//                    $scope.userInactivation.rqstId = data;
//
//                }
//            ]
//
//        });
//    };

    $scope.commmonRefresh = function () {

        location.reload();
    };

    $scope.mainModelView = function () {

        var msg = $scope.showClearModal();

        if ("success" === msg) {
//            var rqstid = $scope.generateRequestId('3');
            $("#userIactModel").modal("show");

        }
    };

    $scope.closeModel = function (cValue) {

        if (cValue == 1) {
            $("#userIactModel").modal("hide");


        } else if (cValue == 2) {
            $("#userIactModelUpdate").modal("hide");


        } else if (cValue == 3) {
            $("#userIactModelView").modal("hide");


        }

    };

    $scope.checkValidation = function () {

        if ($scope.userInactivation.epfNo == '' || $scope.userInactivation.epfNo == null || $scope.userInactivation.epfNo == "") {
            sweetAlert("Alert..!", "Please Enter User ID And Select", "error");
        } else if ($scope.userInactivation.fullName == '' || $scope.userInactivation.fullName == null || $scope.userInactivation.fullName == "") {
            sweetAlert("Alert..!", "Please Select Button", "error");
        } else if ($scope.userInactivation.reason == '' || $scope.userInactivation.reason == null || $scope.userInactivation.reason == "") {
            sweetAlert("Alert..!", "Please Select Reason", "error");
        } else {
            return 'succeess';
        }
    };

    $scope.showSaveDislog = function (value) {

        var msg = $scope.checkValidation();

        if (msg == 'succeess') {

            if (value == 1) {
                $("#addItemTypModal").modal("show");

            } else if (value == 2) {

                $("#updateItemTypModal").modal("show");
            }
        }


    };

    $scope.checkDate = function () {

        $scope.userInactivation.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

    };



});