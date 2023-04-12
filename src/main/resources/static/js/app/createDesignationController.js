/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


app.controller("createDesignationController", function ($scope, $http, $mdDialog, $filter) {

//    $scope.branches = ["Ratnapura", "Head Office", "Matara"];
//    $scope.designations = ["Manager", "Staff Assistance"];

    $scope.createDesignation = {

        desigType: ""

    };
    $scope.createDesignation.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

    $scope.createDesig = function (resp) {

        if (resp == true) {
            $("#addItemTypModal").modal("hide");
            $("#newAddDesignationModel").modal("hide");

            var method = "POST";
            var url = 'designation';
            $scope.createDesignBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.createDesignation),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllDesignations();
                        if (data == 'Designation Created') {
                            sweetAlert("Alert..!", data, "success");
                        } else {
                            sweetAlert("Alert..!", data, "error");
                        }

                    }
                ]
            })
        } else {
            $("#addItemTypModal").modal("hide");
            $("#newAddDesignationModel").modal("hide");
        }
    };

    $scope.deleteDesigDetails = function (Type) {

        $scope.dsc = Type;


    };

    $scope.getAllDesignations = function () {

        $scope.assignData = [];
        var method = "GET";
        var url = 'designation';
        $scope.createDesignBusy = $http({
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

    $scope.deleteDesignation = function (resp) {

        if (resp == true) {
            $("#dltItemTypModalDesig").modal("hide");

            $scope.createDesignation.desigType = $scope.dsc;

            var method = "DELETE";
            var url = 'designation/delete_designation';
            $scope.createDesignBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.createDesignation),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllDesignations();

                        sweetAlert("Alert..!", data, "success");

                    }
                ]
            });
        } else {
            $("#dltItemTypModalDesig").modal("hide");
        }
        console.log($scope.createDesignation.desigType);
    };

    $scope.checkValidation = function () {

        if ($scope.createDesignation.desigType == '' || $scope.createDesignation.desigType == null || $scope.createDesignation.desigType == "") {
            sweetAlert("Alert..!", "Please Enter Designation", "error");
        } else {
            return 'succeess';
        }
    };

    $scope.showClearModal = function (branch, till, tilltype) {

        $scope.createDesignation.desigType = '';

        return 'success';
    };

    $scope.mainModelView = function () {

        var msg = $scope.showClearModal();
        if ("success" === msg) {
            $("#newAddDesignationModel").modal("show");
        }
    };

    $scope.showSaveDislog = function (value) {

        var msg = $scope.checkValidation();

        if (msg == 'succeess') {

            if (value == 1) {
                $("#addItemTypModal").modal("show");
            } else if (value == 2) {
                $("#updateDesigItemTypModal").modal("show");
            }
        }
    };

    $scope.closeModel = function (value) {

        if (value == 1) {
            $("#newAddDesignationModel").modal("hide");
        } else if (value == 2) {
            $("#updateDesigItemTypModal").modal("hide");
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