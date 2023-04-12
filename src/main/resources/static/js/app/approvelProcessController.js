/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

app.controller("approvelProcessController", function ($scope, $http, $mdDialog, $filter) {





    $scope.approvelProcess = {

        test: "",

    };

    $scope.user.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');


    $scope.approveConfirm = function () {

        var method = "POST";
        var url = 'appProcess';
        $scope.createUserBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.approvelProcess),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    location.reload();
                }
            ]
        });
    };




});

