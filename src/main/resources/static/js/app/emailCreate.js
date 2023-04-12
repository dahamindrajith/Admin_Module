/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

app.controller("emailCreate", function ($scope, $http, $mdDialog, $filter) {

    $scope.mailCreate = {

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

    $scope.mailCreate.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

    $scope.cofrmSubmition = function (value) {

        if (value == true) {

            $("#cofrmTypModal").modal("hide");
            $("#createEmail").modal("hide");

            var method = "POST";
            var url = 'emlctrt/confrm_sub';
            $scope.createEmailBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.mailCreate),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getEmailToCreate();
                        if (data == "Request Fail") {
                            sweetAlert("Alert..!", data, "error");
                        } else {
                            sweetAlert("Alert..!", data, "success");
                        }

                    }
                ]
            });
        } else {

            $("#cofrmTypModal").modal("hide");
            $("#createEmail").modal("hide");
        }
    };

    $scope.getEmailToCreate = function () {


        $scope.assignData = [];
        var method = "GET";
        var url = 'emlctrt/get_users';
        $scope.createEmailBusy = $http({
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

    $scope.getDataByRqstId = function (epf) {

        $scope.mailCreate.epfNo = epf;
        var method = "POST";
        var url = 'emlctrt/rqst_bydate';
        $scope.createEmailBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.mailCreate),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.mailCreate = JSON.parse(data);
                    $scope.mailCreate.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');
                    console.log($scope.mailCreate);
                }
            ]
        });

    };

    $scope.showSaveDislog = function () {

        $("#cofrmTypModal").modal("show");
    }

});
