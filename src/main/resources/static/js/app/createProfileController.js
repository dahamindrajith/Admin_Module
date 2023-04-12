/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


app.controller("createProfileController", function ($scope, $http, $mdDialog, $filter) {

    $scope.sigProfiles = ["BRATELLER", "BMAINTELL", "BMGR", "BCPBMMT"];
    $scope.levels = ["BM", "RM"];
    $scope.tillTypes = ["Teller", "Main Teller", "Supervisor"];
    $scope.clicks = [{label: "Department", value: 1}, {label: "Branch", value: 2}, {label: "Region", value: 3}];


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
    $scope.profileCreate.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

    $scope.createProfile = function (resp) {

        if (resp == true) {

            $("#newAddProfileModel").modal("hide");
            $("#addItemTypModal").modal("hide");

            var method = "POST";
            var url = 'profile';
            $scope.createProfilrBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.profileCreate),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getTableLoad();
                        if (data == 'Profile Created') {
                            sweetAlert("Alert..!", data, "success");
                        } else {
                            sweetAlert("Alert..!", data, "error");
                        }


                    }
                ]
            })
        } else {
            $("#newAddProfileModel").modal("hide");
            $("#addItemTypModal").modal("hide");
        }
    };

    $scope.showCC = function (iscc) {

        if (iscc.trim() == '1') {

            $scope.click3 = '1';
            $scope.profileCreate.cc = '';
        } else {
            $scope.click3 = '0';
            $scope.profileCreate.cc = '';
        }

    }


    $scope.getAllProfiles = function () {

        $scope.allProfiles = [];
        var method = "GET";
        var url = 'profile';
        $scope.createProfilrBusy = $http({
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
        console.log($scope.allProfiles);
    };

    $scope.getTableLoad = function () {

        $scope.allProfiles = [];
        var method = "GET";
        var url = 'profile/table_load';
        $scope.createProfilrBusy = $http({
            method: method,
            url: url,

            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.allProfiles = JSON.parse(data);

                }
            ]

        });
        console.log($scope.allProfiles);
    };

    $scope.getCCList = function (branchOrDept) {

        $scope.profileCreate.cc = branchOrDept
        $scope.cclists = [];
        var method = "POST";
        var url = 'profile/get_cc_list';
        $scope.createProfilrBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.profileCreate),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function transformResponse(data) {


                    $scope.cclists = JSON.parse(data);

                    console.log($scope.cclists);
                    return data;
                }
            ]

        });

    };


    $scope.updateProfiles = function (ProfName) {

        $scope.profileCreate.profileName = ProfName;

        if (ProfName == false) {

            $("#newAddProfileModelUpdate").modal("hide");
            $("#updateItemTypModal").modal("hide");

        } else {

            $("#newAddProfileModelUpdate").modal("hide");
            $("#updateItemTypModal").modal("hide");
            var method = "PUT";
            var url = 'profile/update_profile';
            $scope.createProfilrBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.profileCreate),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getTableLoad();

                        sweetAlert("Alert..!", data, "success");

                    }
                ]
            })

        }
    };


    $scope.deleteProfiles = function (resp) {

        if (resp == true) {

            $("#dltItemTypModalPrfile").modal("hide");

            $scope.profileCreate.profileName = $scope.prfl;
            $scope.profileCreate.cc = $scope.costCenter;

            var method = "DELETE";
            var url = 'profile/delete_Profile';
            $scope.createProfilrBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.profileCreate),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getTableLoad();

                        sweetAlert("Alert..!", data, "success");

                    }
                ]
            });
        } else if (resp == false) {
            $("#dltItemTypModalPrfile").modal("hide");
        }
        console.log(data);
    };

    $scope.deleteProfilesDetails = function (Profile, CC) {

        $scope.prfl = Profile;
        $scope.costCenter = CC;

    };


    $scope.showUpdateModalProfile = function (profilename, signatureprofile, branchornot, cc, level, tilltype) {

        $scope.profileCreate.profileName = profilename;
        $scope.profileCreate.sigProfile = signatureprofile;
        $scope.profileCreate.click = branchornot;
        $scope.profileCreate.cc = cc;
        $scope.profileCreate.level = level;
        $scope.profileCreate.tillType = tilltype;

    };

    $scope.showClearModal = function (profilename, signatureprofile, branchornot, cc, level, tilltype) {

        $scope.profileCreate.profileName = '';
        $scope.profileCreate.sigProfile = '';
        $scope.profileCreate.velGroup = '';
        $scope.profileCreate.click = '';
        $scope.profileCreate.cc = '';
        $scope.profileCreate.level = '';
        $scope.profileCreate.tillType = '';

        return 'success';
    };

    $scope.getListByProfile = function (prof, CC) {

        $scope.profileCreate.profileName = prof;
        $scope.profileCreate.cc = CC;

        $scope.assignDataById = [];
        var method = "POST";
        var url = 'profile/get_list_by_profile';


        $scope.createProfilrBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.profileCreate),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.profileCreate = JSON.parse(data);
                    $scope.profileCreate.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

                }
            ]

        });
        console.log($scope.user);

    };

    $scope.checkValidation = function () {

        if ($scope.profileCreate.profileName == '' || $scope.profileCreate.profileName == null || $scope.profileCreate.profileName == "") {
            sweetAlert("Alert..!", "Please Enter Profile Name", "error");
        } else if ($scope.profileCreate.sigProfile == '' || $scope.profileCreate.sigProfile == null || $scope.profileCreate.sigProfile == "") {
            sweetAlert("Alert..!", "Please Enter Signature Profile", "error");
        } else if ($scope.profileCreate.velGroup == '' || $scope.profileCreate.velGroup == null || $scope.profileCreate.velGroup == "") {
            sweetAlert("Alert..!", "Please Enter Velocity Group", "error");
        } else if ($scope.profileCreate.click == '' || $scope.profileCreate.click == null || $scope.profileCreate.click == "") {
            sweetAlert("Alert..!", "Please Enter Branch or Department", "error");
        } else if (($scope.profileCreate.cc == '' || $scope.profileCreate.cc == null || $scope.profileCreate.cc == "") && $scope.click3 == '1') {
            sweetAlert("Alert..!", "Please Enter CC", "error");
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

    $scope.mainModelView = function () {

        var msg = $scope.showClearModal();

        if ("success" === msg) {

            $("#newAddProfileModel").modal("show");
        }
    };



    $scope.closeModel = function (cValue) {

        if (cValue == 1) {
            $("#newAddProfileModel").modal("hide");


        } else if (cValue == 2) {
            $("#newAddProfileModelUpdate").modal("hide");
        }

    };

    $scope.commmonRefresh = function () {

        location.reload();
    };

    function _success(res) {

//        $mdDialog.show(
//                $mdDialog.alert()
//                .parent(angular.element(document.querySelector('#popupContainer')))
//                .clickOutsideToClose(true)
//                .title('This is an alert title')
//                .textContent('You can specify some description text in here.')
//                .ariaLabel('Alert Dialog Demo')
//                .ok('Got it!')
//                );
    }

});