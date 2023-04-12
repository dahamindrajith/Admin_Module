/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

app.controller("accessCofigController", function ($scope, $http, $mdDialog, $filter) {


//    $scope.designations = ["Manager", "Staff Assistant", "Head of Department"];
//    $scope.profiles = ["Back Officer", "Second Officer", "Branch Manager"];

    $scope.userAccess = {

        designation: "",
        profile: "",
        accessCode: "",
        radio: "",
        branchOrDept: ""

    };
    $scope.profileCreate = {

        profileName: "",
        sigProfile: "",
        cc: "",
        level: "",
        tillType: "",
        click: "",
        status: "",
        velGroup: ""

    };

    $scope.userAccess.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

    $scope.clearBox = function (value) {

        if (value == 'branch') {
            $scope.designations = [];
        } else if (value == 'dept') {
            $scope.profiles = [];
        }
    }

    $scope.closeMdel = function (index) {

        if (index == 1) {
            $("#newBranchAccessConfig").modal("hide");
        } else if (index == 2) {
            $("#newDeptAccessConfig").modal("hide");
        } else if (index == 3) {
            $("#newBranchAccessView").modal("hide");
        } else if (index == 4) {
            $("#newBranchAccessUpdate").modal("hide");
        } else if (index == 5) {
            $("#newDeptAccessView").modal("hide");
        } else if (index == 6) {
            $("#newDeptAccessUpdate").modal("hide");
        } else if (index == 7) {
            $("#newReagionAccessConfig").modal("hide");
        } else if (index == 8) {
            $("#newRegionView").modal("hide");
        } else if (index == 9) {
            $("#newRegionAccessUpdate").modal("hide");
        }

    }

    $scope.accessConfig = function (radio, resp) {

        $scope.userAccess.radio = radio;

        if (resp == true) {

            if (radio == 'branch') {
                $("#addBranchTypModal").modal("hide");
                $("#newBranchAccessConfig").modal("hide");
            } else if (radio == 'department') {
                $("#addDeptTypModal").modal("hide");
                $("#newDeptAccessConfig").modal("hide");
            } else if (radio == 'region') {
                $("#addRegTypModal").modal("hide");
                $("#newReagionAccessConfig").modal("hide");
            }


            var method = "POST";
            var url = 'accessconfig';
            $scope.createAccessBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.userAccess),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var values = $scope.radbtn;
                        var msg = $scope.getAccessLiset(values);
                        if (data == "Access Already Available") {
                            sweetAlert("Alert..!", data, "error");
                        } else {
                            sweetAlert("Alert..!", data, "success");
                        }

                    }
                ]
            })
        } else {

            if (radio == 'branch') {
                $("#addBranchTypModal").modal("hide");
                $("#newBranchAccessConfig").modal("hide");
            } else if (radio == 'department') {
                $("#addDeptTypModal").modal("hide");
                $("#newDeptAccessConfig").modal("hide");
            } else if (radio == 'region') {
                $("#addRegTypModal").modal("hide");
                $("#newReagionAccessConfig").modal("hide");
            }

        }
    };

    $scope.getAllDesignations = function () {

        $scope.designations = [];
        var method = "GET";
        var url = 'designation';
        $scope.createAccessBusy = $http({
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

    $scope.getAllProfiles = function (value) {

        $scope.profileCreate.click = value;

        $scope.profiles = [];
        var method = "POST";
        var url = 'profile/get_all_prof';
        $scope.createAccessBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.profileCreate),
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

    $scope.getAllDepartment = function () {

        $scope.department = [];
        var method = "GET";
        var url = 'accessconfig/get_dept';
        $scope.createAccessBusy = $http({
            method: method,
            url: url,

            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.department = JSON.parse(data);

                    console.log($scope.department);
                    return data;

                }
            ]

        });

    };

    $scope.getAllRegion = function () {

        $scope.regions = [];
        var method = "GET";
        var url = 'accessconfig/get_reg';
        $scope.createAccessBusy = $http({
            method: method,
            url: url,

            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.regions = JSON.parse(data);

                    console.log($scope.region);
                    return data;

                }
            ]

        });

    };

    $scope.getAccessListByID = function (bod, selectedone, ccode) {

        $scope.userAccess.branchOrDept = bod;
        $scope.userAccess.profile = selectedone;
        $scope.userAccess.designation = ccode;

        $scope.assignDataById = [];
        var method = "POST";
        var url = 'accessconfig/get_acce_by_id';


        $scope.createAccessBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.userAccess),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.userAccess = JSON.parse(data);
                    $scope.userAccess.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

                }
            ]

        });

        console.log($scope.userAccess);

    };

    $scope.getAccessLiset = function (value) {

        $scope.userAccess.radio = value;

        $scope.radbtn = value;
        $scope.assignData = [];
        var method = "POST";
        var url = 'accessconfig/get_access_list';
        $scope.createAccessBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.userAccess),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.assignData = JSON.parse(data);

                    console.log($scope.assignData);
                    return data;

                }
            ]

        });

    };

    $scope.updateaccessConfig = function (dop, seletone, ccode, resp) {

        $scope.userAccess.branchOrDept = dop;
        $scope.userAccess.profile = seletone;
        $scope.userAccess.designation = ccode;

        if (resp == true) {

            if ($scope.userAccess.branchOrDept == 'Branch') {
                $("#addBranchUpdate").modal("hide");
                $("#newBranchAccessUpdate").modal("hide");
            } else if ($scope.userAccess.branchOrDept == 'Department') {
                $("#addDeptUpdate").modal("hide");
                $("#newDeptAccessUpdate").modal("hide");
            } else {
                $("#addRegionUpdate").modal("hide");
                $("#newRegionAccessUpdate").modal("hide");
            }
            var method = "PUT";
            var url = 'accessconfig/updaet_assess_config';
            $scope.createAccessBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.userAccess),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var values = $scope.radbtn;

                        var msg = $scope.getAccessLiset(values);

                        sweetAlert("Alert..!", data, "success");

                    }
                ]
            });

        } else {

            if ($scope.userAccess.branchOrDept == 'Branch') {
                $("#addBranchUpdate").modal("hide");
                $("#newBranchAccessUpdate").modal("hide");
            } else if ($scope.userAccess.branchOrDept == 'Department') {
                $("#addDeptUpdate").modal("hide");
                $("#newDeptAccessUpdate").modal("hide");
            } else {
                 $("#addRegionUpdate").modal("hide");
                $("#newRegionAccessUpdate").modal("hide");
            }

        }

    };

    $scope.checkValidation = function (rd) {

        if (rd == 'branch') {

            if ($scope.userAccess.profile == '' || $scope.userAccess.profile == null || $scope.userAccess.profile == "") {
                sweetAlert("Alert..!", "Please Select Profile", "error");
            } else if ($scope.userAccess.accessCode == '' || $scope.userAccess.accessCode == null || $scope.userAccess.accessCode == "") {
                sweetAlert("Alert..!", "Please Enter Access Code", "error");
            } else {
                return 'succeess';
            }
        } else if (rd == 'region') {

            if ($scope.userAccess.profile == '' || $scope.userAccess.profile == null || $scope.userAccess.profile == "") {
                sweetAlert("Alert..!", "Please Select Profile", "error");
            } else if ($scope.userAccess.accessCode == '' || $scope.userAccess.accessCode == null || $scope.userAccess.accessCode == "") {
                sweetAlert("Alert..!", "Please Enter Access Code", "error");
            } else {
                return 'succeess';
            }
        } else {

            if ($scope.userAccess.branchOrDept == '' || $scope.userAccess.branchOrDept == null || $scope.userAccess.branchOrDept == "") {
                sweetAlert("Alert..!", "Please Select Department", "error");
            } else if ($scope.userAccess.profile == '' || $scope.userAccess.profile == null || $scope.userAccess.profile == "") {
                sweetAlert("Alert..!", "Please Select Profile", "error");
            } else if ($scope.userAccess.accessCode == '' || $scope.userAccess.accessCode == null || $scope.userAccess.accessCode == "") {
                sweetAlert("Alert..!", "Please Enter Access Code", "error");
            } else {
                return 'succeess';
            }
        }
    };

    $scope.showClearModal = function () {

        $scope.userAccess.designation = '';
        $scope.userAccess.profile = '';
        $scope.userAccess.accessCode = '';

        return 'success';

    };

    $scope.mainModelView = function (value) {

        var msg = $scope.showClearModal();

        if (value == 1) {
            $("#newBranchAccessConfig").modal("show");
        } else if (value == 2) {
            $("#newDeptAccessConfig").modal("show");
        } else if (value == 3) {
            $("#newReagionAccessConfig").modal("show");
        }

    };

    $scope.showSaveDislog = function (value) {


        if (value == 1) {

            var msg = $scope.checkValidation('branch');
            if (msg == 'succeess') {

                $("#addBranchTypModal").modal("show");
            }
        } else if (value == 2) {

            var msg = $scope.checkValidation('department');
            if (msg == 'succeess') {

                $("#addDeptTypModal").modal("show");
            }

        } else if (value == 3) {

            var msg = $scope.checkValidation('region');
            if (msg == 'succeess') {

                $("#addRegTypModal").modal("show");
            }

        } else if (value == 4) {

            var msg = $scope.checkValidation('branch');
            if (msg == 'succeess') {

                $("#addBranchUpdate").modal("show");
            }

        } else if (value == 5) {

            var msg = $scope.checkValidation('department');
            if (msg == 'succeess') {

                $("#addDeptUpdate").modal("show");
            }

        } else if (value == 6) {

            var msg = $scope.checkValidation('region');
            if (msg == 'succeess') {

                $("#addRegionUpdate").modal("show");
            }

        }


    };


//    function _success(res) {
//
//        $mdDialog.show(
//                $mdDialog.alert()
//                .parent(angular.element(document.querySelector('#popupContainer')))
//                .clickOutsideToClose(true)
//                .title('This is an alert title')
//                .textContent('You can specify some description text in here.')
//                .ariaLabel('Alert Dialog Demo')
//                .ok('Got it!')
//                );
//    }

});
