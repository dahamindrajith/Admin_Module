/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

app.controller("reportGenerate", function ($scope, $http, $mdDialog, $filter) {

    $scope.clicks =["Available Users", "Transfer Users" ,"Inactivation Users" ,"temporary  Access"];

    $scope.repo = {

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

    $scope.repo.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');


    $scope.donloadRepo = function () {


        var method = "POST";
        var url = 'report/dwn_report';
        $scope.createUserBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.repo),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {

                    console.log($scope.repo);
                }
            ]
        });

    };

});
