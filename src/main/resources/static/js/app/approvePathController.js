/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

app.controller("approvePathController", function ($scope, $http, $mdDialog, $filter) {

    $scope.ReqstTypes = ["User Creation", "User Transfer", "User Inactivation", "Temperory Access"];
    $scope.clicks = ["Department", "Branch", "Region Branch"];


    $scope.approvePath = {

        pathno: "",
        rqstType: "",
        click: "",
        cc: "",
        apprvlOffcer: "",
        apprvlLevel: "",
        radio: ""

    };
    $scope.approvePath.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');

    $scope.createApprovel = function () {

        $("#addItemTypModal").modal("hide");
        $("#newApproveModel").modal("hide");

        var method = "POST";
        var url = 'approvalpath';
        $scope.createApprovelBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.approvePath),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    location.reload();
                }
            ]
        })
    };


    $scope.getAllApprovelPaths = function (value) {

        $scope.assignData = [];
        var method = "GET";
        var url = 'approvalpath/get_all_approlal_path/' + value;
        $scope.createUserBusy = $http({
            method: method,
            url: url,

            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.assignData.push(data);

                }
            ]

        });
        console.log($scope.assignData);
    };


    $scope.deleteApprovelPaths = function (pathNo, approvedLvl, rqstType) {


        var method = "DELETE";
        var url = 'approvalpath/delete_approvalpath/' + pathNo + '/' + approvedLvl+'/'+rqstType;
        $scope.createUserBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.approvePath),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    location.reload();
                }
            ]
        });
        console.log($scope.user.epfNo);
    };
    
    $scope.getCCList = function () {

        $scope.cclists = [];
        var method = "GET";
        var url = 'approvalpath/get_cc_list';
        $scope.createUserBusy = $http({
            method: method,
            url: url,

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


    $scope.showSaveDislog = function () {


        $("#addItemTypModal").modal("show");

//        var msg = $scope.isValidItem();
//        if ("success" === msg) {
//            var data = {
//                assetCode: $scope.subasset.subasstCode.toUpperCase()
//            };
//            $scope.subasset.saveNewRecordBusy = ajax.ajaxget("validate_sub_asset_code", data, false).then(function (response) {
//                if ("exist" === response[0]) {
//                    Notification.error({message: 'Sub asset code already exist'});
//                } else {
//                    $("#addItemTypModal").modal("show");
//                }
//            });            
//        }
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
