/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
/* global app */

app.controller("hrUpdateController", function ($scope, $http, $mdDialog, $filter) {

    $scope.approveList = {

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

    $scope.approveList.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

    $scope.createHrList = function (resp) {

        if (resp == true) {

            $("#addItemTypModalHr").modal("hide");
            $("#newAddApprovelModal").modal("hide");

            var method = "POST";
            var url = 'hrupdate/save_list';
            $scope.createHrSpecBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.approveList),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getList();
                        if (data == "User already added") {
                            sweetAlert("Alert..!", data, "error");
                        } else if (data == 'User is not available') {
                            sweetAlert("Alert..!", data, "error");
                        } else {
                            sweetAlert("Alert..!", data, "success");
                        }

                    }
                ]
            });
        } else {
            $("#addItemTypModalHr").modal("hide");
            $("#newAddApprovelModal").modal("hide");
            $scope.showClearModal();
        }
    };

    $scope.getListHrById = function (Epf) {

        $scope.approveList.epfNo = Epf;

        $scope.assignDataById1 = [];
        var method = "POST";
        var url = 'hrupdate/get_list_hr_by_epf';


        $scope.createHrSpecBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.approveList),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.approveList = JSON.parse(data);
 $scope.approveList.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

                }
            ]

        });
        console.log($scope.approveList);

    };

    $scope.showClearModal = function () {


        $scope.approveList.epfNo = '';
        $scope.approveList.fullName = '';
        $scope.approveList.profile = '';
        $scope.approveList.branchorDept='';
        

        return 'success';
    };

    $scope.checkValidation = function () {

        if ($scope.approveList.epfNo == '' || $scope.approveList.epfNo == null || $scope.approveList.epfNo == "") {
            sweetAlert("Alert..!", "Please Enter User ID And Select", "error");
        } else if ($scope.approveList.fullName == '' || $scope.approveList.fullName == null || $scope.approveList.fullName == "") {
            sweetAlert("Alert..!", "Please Select Button", "error");
        } else if ($scope.approveList.profile == '' || $scope.approveList.profile == null || $scope.approveList.profile == "") {
            sweetAlert("Alert..!", "Please Enter Profile", "error");
        } else if ($scope.approveList.newProfile == '' || $scope.approveList.newProfile == null || $scope.approveList.newProfile == "") {
            sweetAlert("Alert..!", "Please Enter New Profile", "error");
        }else {
            return 'succeess';
        }
    };

    $scope.showSaveDislogHr = function (value) {

        var msg = $scope.checkValidation();

        if (msg == 'succeess') {

            if (value == 1) {

                $("#addItemTypModalHr").modal("show");
            }
        }

    };
    
    $scope.closeModel=function(values){
        
        if(values==1){
            
            $("#newAddApprovelModal").modal("hide");
        }
        
    }

    $scope.getList = function () {

        $scope.assignData = [];
        var method = "GET";
        var url = 'hrupdate';
        $scope.createHrSpecBusy = $http({
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
    
      $scope.getBProfile = function () {

        $scope.assignDataP = [];
        var method = "GET";
        var url = 'hrupdate/get_bprof';
        $scope.createHrSpecBusy = $http({
            method: method,
            url: url,

            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.assignDataP = JSON.parse(data);

                }
            ]

        });
        console.log($scope.assignDataP);
    };

    $scope.deleteList = function (Epf) {

        $scope.approveList.epfNo = Epf;
        var method = "DELETE";
        var url = 'hrupdate/delete_list';
        $scope.createHrSpecBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.approveList),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {

                    var msg = $scope.getList();
                   
                        sweetAlert("Alert..!", data, "success");
                   
                }
            ]
        });
        console.log($scope.user.epfNo);
    };

//    $scope.showClearModal = function () {
//
//
//        $scope.approveList.epfNo = '';
//        $scope.approveList.fullName = '';
//        $scope.approveList.profile = '';
//
//        return 'success';
//
//    };

    $scope.mainModelView = function () {

        var msg = $scope.showClearModal();

        if ("success" === msg) {
            $("#newAddApprovelModal").modal("show");

        }
    };

    $scope.commmonRefresh = function () {

        location.reload();
    };

});

