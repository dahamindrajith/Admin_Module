/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


app.controller("createTellerController", function ($scope, $http, $mdDialog, $filter) {


//    $scope.tills = ["1", "2", "3"];
    $scope.tillTypes = ["TL", "ML", "BTL", "BML"];
    $scope.tellerCreate = {

        branch: "",
        till: "",
        tillType: ""

    };
    $scope.oldBranch = "";
    $scope.oldTill = "";
    $scope.btn1 = false;
    $scope.tellerCreate.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

    $scope.createTeller = function (response) {



        if (response == true) {

            $("#addItemTypModal").modal("hide");
            $("#newAddTelleteModel").modal("hide");

            var method = "POST";
            var url = 'teller';
            $scope.createTellerBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.tellerCreate),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllTellers();
                        if (data == 'Teller Created') {
                            sweetAlert("Alert..!", data, "success");
                        } else {
                            sweetAlert("Alert..!", data, "error");
                        }
                        console.log(tellerCreate);
                    }
                ]
            })
        } else {
            $("#addItemTypModal").modal("hide");
            $("#newAddTelleteModel").modal("hide");
        }

    };


    $scope.getAllTellers = function () {

        $("#newUpdateTelleteModel").modal("hide");
        $("#newAddTelleteModel").modal("hide");

        $scope.allTellers = [];
        var method = "GET";
        var url = 'teller';
        $scope.createTellerBusy = $http({
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

    $scope.deleteTellerDetails = function (Branch, Till) {

        $scope.brnch = Branch;
        $scope.tll = Till;

    };

    $scope.btnClick = function (response) {

        $scope.btn1 = response;
    };


    $scope.updateTellers = function (Teller, till, tilltype) {

        $scope.tellerCreate.branch = $scope.oldBranch;
        $scope.tellerCreate.till = $scope.oldTill;

        var obranch = $scope.oldBranch;
        var otill = $scope.oldTill
        if (Teller == false) {

            $("#newUpdateTelleteModel").modal("hide");
            $("#updateItemTypModal").modal("hide");

        } else {

            $("#newUpdateTelleteModel").modal("hide");
            $("#updateItemTypModal").modal("hide");

            var method = "PUT";
            var url = 'teller/update_tellers';
            $scope.createTellerBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.tellerCreate),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllTellers();
                        if (data == 'Teller Updated') {
                            sweetAlert("Alert..!", data, "success");
                        } else {
                            sweetAlert("Alert..!", data, "error");
                        }

                    }
                ]
            });
        }
    };


    $scope.deleteTellers = function (resp) {

        if (resp == true) {

            $("#dltItemTypModalTeller").modal("hide");

            $scope.tellerCreate.branch = $scope.brnch;
            $scope.tellerCreate.till = $scope.tll;

            var method = "DELETE";
            var url = 'teller/delete_tellers';
            $scope.createTellerBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.tellerCreate),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {

                        var msg = $scope.getAllTellers();

                        sweetAlert("Alert..!", data, "success");

                    }
                ]
            });
        } else {
            $("#dltItemTypModalTeller").modal("hide");
        }
        console.log($scope.user.epfNo);
    };

    $scope.getTill = function (branch) {

        $scope.tellerCreate.branch = branch;

        $scope.tillNo = [];
        var method = "POST";
        var url = 'teller/get_till_by_branch';


        $scope.createTellerBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.tellerCreate),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.tillNo = JSON.parse(data);

                }
            ]

        });
        return 'succcess';
    };

    $scope.getTellerByBranch = function (branch, till) {

        $scope.tellerCreate.branch = branch;
        $scope.tellerCreate.till = till;

        $scope.oldBranch = branch;
        $scope.oldTill = till;

        $scope.tillNo = [];
        var method = "POST";
        var url = 'teller/get_teller_by_branch';


        $scope.createTellerBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.tellerCreate),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.tellerCreate = JSON.parse(data);
                    $scope.tellerCreate.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');
                }
            ]

        });
        console.log($scope.tellerCreate);
    };


    $scope.showUpdateTellerModal = function (branch, till, tilltype) {

        $scope.tellerCreate.branch = branch;
        $scope.tellerCreate.till = till;
        $scope.tellerCreate.tillType = tilltype;

    };

    $scope.showClearModal = function (branch, till, tilltype) {

        $scope.tellerCreate.branch = '';
        $scope.tellerCreate.till = '';
        $scope.tellerCreate.tillType = '';

        return 'success';
    };

    $scope.checkValidation = function () {

        if ($scope.tellerCreate.branch == '' || $scope.tellerCreate.branch == null || $scope.tellerCreate.branch == "") {
            sweetAlert("Alert..!", "Please Select Branch Press Select", "error");
        } else if ($scope.tellerCreate.till == '' || $scope.tellerCreate.till == null || $scope.tellerCreate.till == "") {
            sweetAlert("Alert..!", "Please Select Till", "error");
        } else if ($scope.tellerCreate.tillType == '' || $scope.tellerCreate.tillType == null || $scope.tellerCreate.tillType == "") {
            sweetAlert("Alert..!", "Please Select Till Type", "error");
        } else {
            return 'succeess';
        }
    };

    $scope.getAllBranchOrDept = function () {

        $scope.branches = [];
        var method = "GET";
        var url = 'teller/get_branch_dept';
        $scope.createTellerBusy = $http({
            method: method,
            url: url,

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

    $scope.mainModelView = function () {

        var msg = $scope.showClearModal();
        $scope.btn1 = false;

        if ("success" === msg) {

            $("#newAddTelleteModel").modal("show");

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

    $scope.closeModel = function (cValue) {

        if (cValue == 1) {
            $("#newAddTelleteModel").modal("hide");


        } else if (cValue == 2) {
            $("#newUpdateTelleteModel").modal("hide");


        }

    };

    function _success(res) {

        $mdDialog.show(
                $mdDialog.alert()
                .parent(angular.element(document.querySelector('#popupContainer')))
                .clickOutsideToClose(true)
                .title('This is an alert title')
                .textContent('You can specify some description text in here.')
                .ariaLabel('Alert Dialog Demo')
                .ok('Got it!')
                );
    }

});