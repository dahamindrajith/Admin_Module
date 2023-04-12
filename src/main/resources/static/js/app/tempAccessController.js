/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


app.controller("tempAccessController", function ($scope, $http, $mdDialog, $filter) {


    $scope.clicks = ["Department", "Branch", "Region"];
//    $scope.Tills = ["10", "20", "30", "40"];

    $scope.tempAccess = {

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

    $scope.storeData = function (Epf, value) {

        $scope.btns = 0;
        $scope.tempAccessManual = "";
        if (value === false) {
            $scope.btns = 1;

            $scope.tempAccessManual = Epf;

        }
    };

    $scope.removeTempMnually = function () {
        $scope.tempAccess.epfNo = $scope.tempAccessManual;

        alert($scope.tempAccess.epfNo);
        var method = "PUT";
        var url = 'tempacc/remove_manualtemp';
        $scope.createTempAccBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.tempAccess),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {

                    var msg = $scope.getAllTempAccess();


                }
            ]
        })

    }

    $scope.tempAccess.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

    $scope.tempAccessGrant = function (Epf) {


        if (Epf == false) {
            $("#addItemTypModal").modal("hide");
            $("#tempAccesstModel").modal("hide");
            $scope.showClearModal();
        } else {

            $("#addItemTypModal").modal("hide");
            $("#tempAccesstModel").modal("hide");

            var method = "PUT";
            var url = 'tempacc/grant_temp_access';
            $scope.createTempAccBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.tempAccess),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllTempAccess();
                        if (data == "Temporary Access Requested Already Requested") {
                            sweetAlert("Alert..!", data, "error");
                        } else if (data == 'User not available') {
                            sweetAlert("Alert..!", data, "error");
                        } else {
                            sweetAlert("Alert..!", data, "success");
                        }

                    }
                ]
            })
        }
    };

    $scope.testTellerTemp = function (value) {

        if (value.trim().includes('Teller')) {
            $scope.click3 = '1';
            console.log(value);
        } else {
            $scope.click3 = '2';
            $scope.tempAccess.newTill = '';
        }

    }

    $scope.getAllTempAccess = function () {

        $scope.assignData = [];
        var method = "GET";
        var url = 'tempacc/get_all_temp_access';
        $scope.createTempAccBusy = $http({
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
        console.log($scope.tempAccessUsers);
    };

    $scope.getAllBranchOrDept = function (branchOrDept) {

        $scope.tempAccess.click = branchOrDept;
        $scope.newBranchs = [];
        var method = "POST";
        var url = 'tempacc/get_branch_bept';
        $scope.createTempAccBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.tempAccess),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {


                    $scope.newBranchs = JSON.parse(data);
                    $scope.checkDate();
                    console.log($scope.newBranchs);
                    return data;
                }
            ]

        });

    };

    $scope.selectedDltUserTT = function (Epf) {

        $scope.dltEpf = Epf;

    };

    $scope.getAppInfoTe = function (rqstType, epf, level, branchOrDept) {

        $scope.tempAccess.rqstType = rqstType;
        $scope.tempAccess.epfNo = epf;
        $scope.tempAccess.status = level;
        $scope.tempAccess.newBranchOrDept = branchOrDept;


        var method = "POST";
        var url = 'tempacc/get_approve_info_te';
        $scope.createTempAccBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.tempAccess),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.tempAccess = JSON.parse(data);
                    $scope.checkDate();
                    console.log($scope.tempAccess);


                }
            ]

        });

    };

//       $scope.getAllProfiles = function () {
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
        $scope.createTempAccBusy = $http({
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

    $scope.getAllDesignations = function () {

        $scope.designations = [];
        var method = "GET";
        var url = 'designation';
        $scope.createTempAccBusy = $http({
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

    $scope.getAllTellers = function () {

        $scope.Tills = [];
        var method = "GET";
        var url = 'teller';
        $scope.createTempAccBusy = $http({
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
        $scope.tellerCreate.tillType = "A";

        $scope.Tillss = [];
        var method = "POST";
        var url = 'teller/get_teller_by_ubranch';
        $scope.createTempAccBusy = $http({
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



    $scope.getListTempAccessById = function (Epf) {

        $scope.tempAccess.epfNo = Epf;
        $scope.tempAccessUser = [];
        var method = "POST";
        var url = 'tempacc/get_list_TempAccess_by_epf';


        $scope.createTempAccBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.tempAccess),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.tempAccess = JSON.parse(data);
                    $scope.checkDate();

                }
            ]

        });
        console.log($scope.tempAccessUser);
//        var msg = $scope.dataLoad();
    };

    $scope.getToButtonListTempAccessById = function (Epf) {

        $scope.tempAccess.epfNo = Epf;

        $scope.selectedData = [];
        var method = "POST";
        var url = 'tempacc/get_list_to_select_btn_temp_by_epf';


        $scope.createTempAccBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.tempAccess),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.tempAccess = JSON.parse(data);
//                    var rqstid = $scope.generateRequestId('4');
                    $scope.checkDate();

                }
            ]

        });
        console.log($scope.tempAccess);

    };



    $scope.showUpdateModalTempAccess = function (rqstid, epf, branchordept, profile, designation, till, startdate, enddate, newbranchordept, newprofile, newdesignation, newtill) {


        $scope.tempAccess.rqstId = rqstid;
        $scope.tempAccess.epfNo = epf;
        $scope.tempAccess.branchorDept = branchordept;
        $scope.tempAccess.profile = profile;
        $scope.tempAccess.designation = designation;
        $scope.tempAccess.currentTill = till;
        $scope.tempAccess.startDate = startdate;
        $scope.tempAccess.endDate = enddate;
        $scope.tempAccess.newBranchOrDept = newbranchordept;
        $scope.tempAccess.newProfile = newprofile;
        $scope.tempAccess.newDesignation = newdesignation;
        $scope.tempAccess.newTill = newtill;
//        $("#newAddUserModelView").modal("show");
    };

    $scope.showClearModal = function (rqstid, epf, name, designation, email, department, userprofile, mobile, pws, cunttill) {

        $scope.tempAccess.rqstId = '';
        $scope.tempAccess.epfNo = '';
        $scope.tempAccess.branchorDept = '';
        $scope.tempAccess.profile = '';
        $scope.tempAccess.designation = '';
        $scope.tempAccess.currentTill = '';
        $scope.tempAccess.startDate = '';
        $scope.tempAccess.endDate = '';
        $scope.tempAccess.newBranchOrDept = '';
        $scope.tempAccess.newProfile = '';
        $scope.tempAccess.newDesignation = '';
        $scope.tempAccess.newTill = '';

        return 'success';
//        $("#newAddUserModelView").modal("show");
    };

    $scope.updateTempAccess = function (Epf) {

        if (Epf == false) {

            $("#updateItemTypModal").modal("hide");
            $("#updateTempAccessModel").modal("hide");
        } else {

            $scope.tempAccess.epfNo = Epf;

            $("#updateItemTypModal").modal("hide");
            $("#updateTempAccessModel").modal("hide");

            var method = "PUT";
            var url = 'tempacc/update_TempAccess';
            $scope.createTempAccBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.tempAccess),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {
                        var msg = $scope.getAllTempAccess();

                        sweetAlert("Alert..!", data, "success");
                    }
                ]
            });
        }
        console.log($scope.user.epfNo);
    };

    $scope.deleteTempAccess = function (resp) {

        $scope.tempAccess.epfNo = $scope.dltEpf;

        if (resp == true) {

            $("#dltTempUserTypModal").modal("hide");

            var method = "DELETE";
            var url = 'tempacc/delete_TempAccess';
            $scope.createTempAccBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.tempAccess),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllTempAccess();

                        sweetAlert("Alert..!", data, "success");

                    }
                ]
            });
        } else {

            $("#dltTempUserTypModal").modal("hide");

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
//                    $scope.tempAccess.rqstId = data;
//
//                }
//            ]
//
//        });
//    };

    $scope.commmonRefresh = function () {

        location.reload();
    };

    $scope.closeModel = function (cValue) {

        if (cValue == 1) {
            $("#tempAccesstModel").modal("hide");


        } else if (cValue == 2) {
            $("#updateTempAccessModel").modal("hide");


        } else if (cValue == 3) {
            $("#tempAccesstModelView").modal("hide");


        }

    };

    $scope.mainModelView = function () {

        var msg = $scope.showClearModal();

        if ("success" === msg) {
//            var rqstid = $scope.generateRequestId('4');
            $("#tempAccesstModel").modal("show");

        }
    };

    $scope.checkValidation = function () {

        if ($scope.tempAccess.epfNo == '' || $scope.tempAccess.epfNo == null || $scope.tempAccess.epfNo == "") {
            sweetAlert("Alert..!", "Please Enter User ID And Select", "error");
        } else if ($scope.tempAccess.profile == '' || $scope.tempAccess.profile == null || $scope.tempAccess.profile == "") {
            sweetAlert("Alert..!", "Please Select Button", "error");
        } else if ($scope.tempAccess.startDate == '' || $scope.tempAccess.startDate == null || $scope.tempAccess.startDate == "") {
            sweetAlert("Alert..!", "Please Enter Start Date", "error");
        } else if ($scope.tempAccess.endDate == '' || $scope.tempAccess.endDate == null || $scope.tempAccess.endDate == "") {
            sweetAlert("Alert..!", "Please Enter End Date", "error");
        } else if ($scope.tempAccess.click == '' || $scope.tempAccess.click == null || $scope.tempAccess.click == "") {
            sweetAlert("Alert..!", "Please Enter Branch or Department", "error");
        } else if ($scope.tempAccess.newBranchOrDept == '' || $scope.tempAccess.newBranchOrDept == null || $scope.tempAccess.newBranchOrDept == "") {
            sweetAlert("Alert..!", "Please Enter Branch or Department", "error");
        } else if ($scope.tempAccess.newDesignation == '' || $scope.tempAccess.newDesignation == null || $scope.tempAccess.newDesignation == "") {
            sweetAlert("Alert..!", "Please Enter New Designation", "error");
        } else if ($scope.tempAccess.newProfile == '' || $scope.tempAccess.newProfile == null || $scope.tempAccess.newProfile == "") {
            sweetAlert("Alert..!", "Please Enter New Profile", "error");
        } else if (($scope.tempAccess.newTill == '' || $scope.tempAccess.newTill == null || $scope.tempAccess.newTill == "") && $scope.click3 == '1') {
            sweetAlert("Alert..!", "Please Enter Till Number", "error");
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

        $scope.tempAccess.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

    };



});