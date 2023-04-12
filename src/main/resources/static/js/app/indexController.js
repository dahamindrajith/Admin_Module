/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
app.controller("indexController", function ($scope, $http, $mdDialog, $filter) {

    $scope.branches = ["Ratnapura", "Head Office", "Matara"];
    $scope.designations = ["Manager", "Staff Assistance"];




    $scope.index = {

        rqstId:"",
        epfNo: "",
        userName: "",
        fullName: "",
        designation: "",
        emailAddress: "",
        branch: "",
        profile: "",
        password: "",
        mobileNo: ""
//        branch: 1,
//        password: "",
//        fullName: "",
//        
//        userName: "",
//        firstName: "",
//        lastName: "",
//        designation: 1,
//        mobileNo: "",
//        emailAddress: "",
//        emailGroup: ""

    };
    $scope.index.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');


    $scope.indexdea = function () {
        
        $("#addItemTypModal").modal("hide");
        $("#newAddUserModel").modal("hide");
//alert($scope.user.epfNo);
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
                    _success(data);
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

