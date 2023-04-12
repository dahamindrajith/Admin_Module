/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


app.controller("userTransferController", function ($scope, $http, $mdDialog, $filter) {


    $scope.clicks = ["Department", "Branch", "Region"];

    $scope.transferUser = {

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

    $scope.tellerCreate = {

        branch: "",
        till: "",
        tillType: ""

    };


    $scope.transferUser.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

    $scope.userTransfer = function (Epf) {
        if (Epf == false) {

            $("#addItemTypModal").modal("hide");
            $("#newTransModel").modal("hide");
            $scope.showClearModal();

        } else {
            $("#addItemTypModal").modal("hide");
            $("#newTransModel").modal("hide");

            var method = "POST";
            var url = 'transfer/user_transfer';
            $scope.createTransferBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.transferUser),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllTransferUser();
                        if (data == "User Transfer already requested") {
                            sweetAlert("Alert..!", data, "error");
                        } else if (data == 'User is not available') {
                            sweetAlert("Alert..!", data, "error");
                        } else {
                            sweetAlert("Alert..!", data, "success");
                        }

                    }
                ]
            });
        }
    };

    $scope.testTellertrans = function (value) {

        if (value.trim().includes('Teller')) {
            $scope.click3 = '1';
            console.log(value);
        } else {
            $scope.click3 = '2';
            $scope.transferUser.newTill = '';
        }

    }

    $scope.getAllTransferUser = function () {

        $scope.trensferUsers = [];
        var method = "GET";
        var url = 'transfer/transfer_users';
        $scope.createTransferBusy = $http({
            method: method,
            url: url,

            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.trensferUsers = JSON.parse(data);

                }
            ]

        });
        console.log($scope.trensferUsers);
    };

    $scope.selectedDltUserT = function (Epf) {

        $scope.dltEpf = Epf;

    };

    $scope.getAllBranchOrDept = function (branchOrDept) {

        $scope.transferUser.click = branchOrDept;
        $scope.branches = [];
        var method = "POST";
        var url = 'transfer/get_branch_bept';
        $scope.createTransferBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.transferUser),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function transformResponse(data) {


                    $scope.branches = JSON.parse(data);
                    $scope.checkDate();
                    console.log($scope.branches);
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

    $scope.getProfileByFilter = function (bod, bodName) {

        $scope.user.click = bod;
        $scope.user.branchorDept = bodName;

        $scope.profiles = [];
        var method = "POST";
        var url = 'profile/get_prof_bod';
        $scope.createTransferBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.user),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.profiles = JSON.parse(data);
                    $scope.checkDate();
                    console.log($scope.profiles);
                    return data;

                }
            ]

        });

    };

    $scope.getAppInfoT = function (rqstType, epf, level, branchOrDept) {

        $scope.transferUser.rqstType = rqstType;
        $scope.transferUser.epfNo = epf;
        $scope.transferUser.status = level;
        $scope.transferUser.branchorDept = branchOrDept;


        var method = "POST";
        var url = 'transfer/get_approve_info_t';
        $scope.createTransferBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.transferUser),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.transferUser = JSON.parse(data);
                    $scope.checkDate();
                    console.log($scope.transferUser);


                }
            ]

        });

    };

    $scope.getAllTellers = function () {

        $scope.Tills = [];
        var method = "GET";
        var url = 'teller';
        $scope.createTransferBusy = $http({
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
        $scope.createTransferBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.tellerCreate),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.Tillss = JSON.parse(data);
                    $scope.checkDate();
                    console.log($scope.Tillss.branch);
                    return data;

                }
            ]

        });

    };

    $scope.getListTransferById = function (Epf) {

        $scope.transferUser.epfNo = Epf;

        $scope.assignDataById1 = [];
        var method = "POST";
        var url = 'transfer/get_list_transfer_by_epf';


        $scope.createTransferBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.transferUser),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.transferUser = JSON.parse(data);
                    $scope.checkDate();
//                    var rqstid = $scope.generateRequestId('2');

                }
            ]

        });
        console.log($scope.transferUser);

    };

    $scope.getToButtonListTransferById = function (Epf) {

        $scope.transferUser.epfNo = Epf;

        $scope.assignDataById1 = [];
        var method = "POST";
        var url = 'transfer/get_list_to_btn_transfer_by_epf';


        $scope.createTransferBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.transferUser),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.transferUser = JSON.parse(data);
                    $scope.checkDate();

                }
            ]

        });
        console.log($scope.transferUser);

    };


    $scope.showUpdateModalTransfer = function (rqstid, epf, name, branchordept, profile, designation, till, newbranchordept, newprofile, newdesignation, newtill) {

        $scope.transferUser.rqstId = rqstid;
        $scope.transferUser.epfNo = epf;
        $scope.transferUser.fullName = name;
        $scope.transferUser.branchorDept = branchordept;
        $scope.transferUser.profile = profile;
        $scope.transferUser.designation = designation;
        $scope.transferUser.currentTill = till;
        $scope.transferUser.newBranchOrDept = newbranchordept;
        $scope.transferUser.newProfile = newprofile;
        $scope.transferUser.newDesignation = newdesignation;
        $scope.transferUser.newTill = newtill;
//        $("#newAddUserModelView").modal("show");
    };

    $scope.showClearModal = function (rqstid, epf, name, branchordept, profile, designation, till, newbranchordept, newprofile, newdesignation, newtill) {

        $scope.transferUser.rqstId = '';
        $scope.transferUser.epfNo = '';
        $scope.transferUser.fullName = '';
        $scope.transferUser.branchorDept = '';
        $scope.transferUser.profile = '';
        $scope.transferUser.designation = '';
        $scope.transferUser.currentTill = '';
        $scope.transferUser.newBranchOrDept = '';
        $scope.transferUser.newProfile = '';
        $scope.transferUser.newDesignation = '';
        $scope.transferUser.newTill = '';
        $scope.transferUser.click = '';
//        $("#newAddUserModelView").modal("show");

        return 'success';
    };


    $scope.updateTransferUser = function (Epf) {

        $scope.transferUser.epfNo = Epf;

        if (Epf == false) {

            $("#newTransModelUpdate").modal("hide");
            $("#UpdateTransModel").modal("hide");
            $scope.showClearModal();

        } else {

            $("#newTransModelUpdate").modal("hide");
            $("#UpdateTransModel").modal("hide");

            var method = "PUT";
            var url = 'transfer/update_Transfer_user';
            $scope.createTransferBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.transferUser),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllTransferUser();

                        sweetAlert("Alert..!", data, "success");

                    }
                ]
            });
            console.log($scope.user.epfNo);
        }
    };

    $scope.deleteTransferUser = function (resp) {

        $scope.transferUser.epfNo = $scope.dltEpf;

        if (resp == true) {

            $("#dltITransUserTypModal").modal("hide");

            var method = "DELETE";
            var url = 'transfer/delete_Transfer_user';
            $scope.createTransferBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.transferUser),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllTransferUser();

                        sweetAlert("Alert..!", data, "success");
                    }
                ]
            });
        } else {

            $("#dltITransUserTypModal").modal("hide");

        }
        console.log($scope.user.epfNo);
    };

    $scope.getAllDesignations = function () {

        $scope.designations = [];
        var method = "GET";
        var url = 'designation';
        $scope.createTransferBusy = $http({
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
//                    $scope.transferUser.rqstId = data;
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
//            var rqstid = $scope.generateRequestId('2');
            $("#newTransModel").modal("show");

        }
    };

    $scope.closeModel = function (cValue) {

        if (cValue == 1) {
            $("#newTransModel").modal("hide");


        } else if (cValue == 2) {
            $("#newTransModelUpdate").modal("hide");


        } else if (cValue == 3) {
            $("#newTransModelView").modal("hide");


        }

    };

    $scope.checkValidation = function () {

        if ($scope.transferUser.epfNo == '' || $scope.transferUser.epfNo == null || $scope.transferUser.epfNo == "") {
            sweetAlert("Alert..!", "Please Enter User ID And Select", "error");
        } else if ($scope.transferUser.fullName == '' || $scope.transferUser.fullName == null || $scope.transferUser.fullName == "") {
            sweetAlert("Alert..!", "Please Select Button", "error");
        } else if ($scope.transferUser.click == '' || $scope.transferUser.click == null || $scope.transferUser.click == "") {
            sweetAlert("Alert..!", "Please Enter Branch or Department", "error");
        } else if ($scope.transferUser.newBranchOrDept == '' || $scope.transferUser.newBranchOrDept == null || $scope.transferUser.newBranchOrDept == "") {
            sweetAlert("Alert..!", "Please Enter Branch or Department", "error");
        } else if ($scope.transferUser.newDesignation == '' || $scope.transferUser.newDesignation == null || $scope.transferUser.newDesignation == "") {
            sweetAlert("Alert..!", "Please Enter New Designation", "error");
        } else if ($scope.transferUser.newProfile == '' || $scope.transferUser.newProfile == null || $scope.transferUser.newProfile == "") {
            sweetAlert("Alert..!", "Please Enter New Profile", "error");
        } else if (($scope.transferUser.newTill == '' || $scope.transferUser.newTill == null || $scope.transferUser.newTill == "") && $scope.click3 == '1') {
            sweetAlert("Alert..!", "Please Enter Till Number", "error");
        } else {
            return 'succeess';
        }
    };

    $scope.showSaveDislogTrans = function (value) {

        var msg = $scope.checkValidation();

        if (msg == 'succeess') {

            if (value == 1) {

                $("#addItemTypModal").modal("show");
            } else if (value == 2) {

                $("#UpdateTransModel").modal("show");
            }
        }

    };

    $scope.checkDate = function () {

        $scope.transferUser.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

    };


});