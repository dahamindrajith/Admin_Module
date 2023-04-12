/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

/* global app,angular, user, epfNo, SweetAlert */
// Controller Part
app.controller("userController", function ($scope, $http, $mdDialog, $filter) {

    $scope.clicks = ["Department", "Branch", "Region"];


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

    $scope.tellerCreate = {

        branch: "",
        till: "",
        tillType: ""

    };

    $scope.user.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');


    $scope.createUser = function (response) {


        if (response == true) {
            $("#addItemTypModal").modal("hide");
            $("#newAddUserModel").modal("hide");

            var method = "POST";
            var url = 'users';
            $scope.createUserBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.user),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllUsers();
                        if (data == "User Already Registered") {
                            sweetAlert("Alert..!", data, "error");
                        } else {
                            sweetAlert("Alert..!", data, "success");
                        }

                    }
                ]
            });
        } else {
            $("#addItemTypModal").modal("hide");
            $("#newAddUserModel").modal("hide");
            $scope.showClearModal();
        }
    };

    $scope.testTeller = function (value) {

        if (value.trim().includes('Teller')) {
            $scope.click3 = '1';

        } else {
            $scope.click3 = '2';
            $scope.user.currentTill = '';
        }

    }

    $scope.selectedDltUser = function (Epf) {

        $scope.dltEpf = Epf;

    };

    $scope.getAllUsers = function () {

        $scope.assignData = [];
        var method = "GET";
        var url = 'users';
        $scope.createUserBusy = $http({
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

    };

    $scope.getAllBranchOrDept = function (branchOrDept) {

        $scope.user.click = branchOrDept;
        $scope.branches = [];
        var method = "POST";
        var url = 'users/get_branch_bept';
        $scope.createUserBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.user),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function transformResponse(data) {
                    $scope.branches = JSON.parse(data);

                    console.log($scope.branches);
                    return data;
                }
            ]

        });

    };
//post method modified
    $scope.getListById = function (Epf) {

        $scope.user.epfNo = Epf;
        $scope.assignDataById = [];
        var method = "POST";
        var url = 'users/get_list_by_epf';


        $scope.createUserBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.user),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.user = JSON.parse(data);

                    $scope.user.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');
                }
            ]

        });
        console.log($scope.user);

    };


    $scope.showUpdateModal = function (rqstid, epf, name, designation, email, department, userprofile, mobile, pws, cunttill, click, stts) {

        $scope.user.rqstId = rqstid;
        $scope.user.epfNo = epf;
        $scope.user.fullName = name;
        $scope.user.designation = designation;
        $scope.user.emailAddress = email;
        $scope.user.branchorDept = department;
        $scope.user.profile = userprofile;
        $scope.user.mobileNo = mobile;
        $scope.user.password = pws;
        $scope.user.currentTill = cunttill;
        $scope.user.click = click;
        $scope.user.status = stts;

        return 'success';


    };

    $scope.showClearModal = function (rqstid, epf, name, designation, email, department, userprofile, mobile, pws, cunttill) {

        $scope.user.rqstId = '';
        $scope.user.epfNo = '';
        $scope.user.fullName = '';
        $scope.user.designation = '';
        $scope.user.emailAddress = '';
        $scope.user.branchorDept = '';
        $scope.user.profile = '';
        $scope.user.mobileNo = '';
        $scope.user.password = '';
        $scope.user.currentTill = '';
        $scope.user.click = '';


        return 'success';

    };




    $scope.updateUser = function (Epf) {

        $scope.user.epfNo = Epf;

        if (Epf == false) {

            $("#updateItemTypModal").modal("hide");
            $("#newUpdateUserModel").modal("hide");
            $scope.showClearModal();

        } else {

            $("#updateItemTypModal").modal("hide");
            $("#newUpdateUserModel").modal("hide");

            var method = "PUT";
            var url = 'users/update_user';
            $scope.createUserBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.user),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {
//                        var msg = $scope.commmonRefresh();

                        var msg = $scope.getAllUsers();

                        sweetAlert("Alert..!", data, "success");


                    }
                ]
            });

        }
    };

    $scope.deleteUser = function (resp) {

        $scope.user.epfNo = $scope.dltEpf;

        if (resp == true) {
            
              $("#dltInewUserTypModal").modal("hide");

            var method = "DELETE";
            var url = 'users/delete_user';
            $scope.createUserBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.user),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllUsers();

                        sweetAlert("Alert..!", data, "success");

                    }
                ]
            });
        } else {
            
             $("#dltInewUserTypModal").modal("hide");

        }
//        console.log($scope.user.epfNo);
    };


    $scope.getAllDesignations = function () {

        $scope.designations = [];
        var method = "GET";
        var url = 'designation';
        $scope.createUserBusy = $http({
            method: method,
            url: url,

            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.designations = JSON.parse(data);

                    console.log($scope.designations);
                    return data;

                }
            ]

        });

    };

//    $scope.getAllProfiles = function () {
//
//        $scope.profiles = [];
//        var method = "GET";
//        var url = 'profile';
//        $scope.createUserBusy = $http({
//            method: method,
//            url: url,
//
//            headers: {
//                'Content-Type': 'application/json'
//            },
//            transformResponse: [
//                function (data) {
//                    $scope.profiles = JSON.parse(data);
//
//                    console.log($scope.profiles);
//                    return data;
//
//                }
//            ]
//
//        });
//
//    };

    $scope.getAllProfilesByBoD = function (bod, bodName) {

        $scope.user.click = bod;
        $scope.user.branchorDept = bodName;

        $scope.profiles = [];
        var method = "POST";
        var url = 'profile/get_prof_bod';
        $scope.createUserBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.user),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.profiles = JSON.parse(data);

                    console.log($scope.profiles);
                    return data;

                }
            ]

        });

    };

    $scope.getAllTellers = function () {

        $scope.Tills = [];
        var method = "GET";
        var url = 'teller';
        $scope.createUserBusy = $http({
            method: method,
            url: url,

            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.Tills = JSON.parse(data);

                    console.log($scope.Tills);
                    return data;

                }
            ]

        });

    };

    $scope.getTellerByUBranch = function (branch) {

        $scope.tellerCreate.branch = branch;

        $scope.Tillss = [];
        var method = "POST";
        var url = 'teller/get_teller_by_ubranch';
        $scope.createUserBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.tellerCreate),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.Tillss = JSON.parse(data);

                    console.log($scope.Tillss.branch);
                    return data;

                }
            ]

        });

    };

    $scope.getAppInfoC = function (rqstType, epf, level, branchOrDept) {


        $scope.user.rqstType = rqstType;
        $scope.user.epfNo = epf;
        $scope.user.status = level;
        $scope.user.branchorDept = branchOrDept;


        var method = "POST";
        var url = 'users/get_approve_info';
        $scope.createUserBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.user),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.user = JSON.parse(data);

                    console.log($scope.user);

                    $scope.user.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');
                }
            ]

        });

    };

    $scope.generatePassword = function () {

        $scope.assignDataById = [];
        var method = "GET";
        var url = 'password';


        $scope.createUserBusy = $http({
            method: method,
            url: url,

            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.user.password = data;

                }
            ]

        });

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
//                    $scope.user.rqstId = data;
//
//                }
//            ]
//
//        });
//    };

    $scope.closeModel = function (cValue) {

        if (cValue == 1) {
            $("#newAddUserModel").modal("hide");


        } else if (cValue == 2) {
            $("#newUpdateUserModel").modal("hide");


        } else if (cValue == 3) {
            $("#newAddUserModelView").modal("hide");


        }

    };

    $scope.commmonRefresh = function () {

        location.reload();
    };

    $scope.mainModelView = function () {

        var msg = $scope.showClearModal();

        if ("success" === msg) {
            var pws = $scope.generatePassword();
//            var rqstid = $scope.generateRequestId('1');
            $("#newAddUserModel").modal("show");

        }
    };

    $scope.isDisable = function (stts, epf) {

        if (stts == 'Pending') {
            return true;
        } else {
            return false;
        }
    };

    $scope.checkValidation = function () {

        if ($scope.user.epfNo == '' || $scope.user.epfNo == null || $scope.user.epfNo == "") {
            sweetAlert("Alert..!", "Please Enter User ID", "error");
        } else if ($scope.user.fullName == '' || $scope.user.fullName == null || $scope.user.fullName == "") {
            sweetAlert("Alert..!", "Please Enter Full Name", "error");
        } else if (($scope.user.emailAddress == '' || $scope.user.emailAddress == null || $scope.user.emailAddress == "") || !($scope.user.emailAddress.includes('@'))) {
            sweetAlert("Alert..!", "Please Enter Valid email Address", "error");
        } else if ($scope.user.click == '' || $scope.user.click == null || $scope.user.click == "") {
            sweetAlert("Alert..!", "Please Enter Branch or Department", "error");
        } else if ($scope.user.branchorDept == '' || $scope.user.branchorDept == null || $scope.user.branchorDept == "") {
            sweetAlert("Alert..!", "Please Enter Branch or Department", "error");
        } else if ($scope.user.designation == '' || $scope.user.designation == null || $scope.user.designation == "") {
            sweetAlert("Alert..!", "Please Enter Designation", "error");
        } else if ($scope.user.profile == '' || $scope.user.profile == null || $scope.user.profile == "") {
            sweetAlert("Alert..!", "Please Enter Profile", "error");
        } else if (($scope.user.currentTill == '' || $scope.user.currentTill == null || $scope.user.currentTill == "") && $scope.click3 == '1') {
            sweetAlert("Alert..!", "Please Enter Till Number", "error");
        } else if ($scope.user.mobileNo == '' || $scope.user.mobileNo == null || $scope.user.mobileNo == "") {
            sweetAlert("Alert..!", "Please Enter Mobile Number", "error");
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
//        else if (value == 3) {
//
//            $("#dltItemTypModal").modal("show");
//        }

    };


//    function _success(res) {
//
//
//        $mdDialog.show(
//                $mdDialog.alert()
//                .parent(angular.element(document.querySelector('#popupContainer')))
//                .clickOutsideToClose(true)
//                .title('Alert')
//                .textContent(res)
//                .ariaLabel('Alert Dialog Demo')
//                .ok('Got it!')
//                );
//
//    }

});

