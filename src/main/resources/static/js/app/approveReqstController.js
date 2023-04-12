/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


app.controller("approveReqstController", function ($scope, $http, $mdDialog, $filter) {

//    $scope.branches = ["Ratnapura", "Head Office", "Matara"];
//    $scope.designations = ["Manager", "Staff Assistance"];

    $scope.approveRqst = {

        rqstId: "",
        epfNo: "",
        userName: "",
        fullName: "",
        designation: "",
        emailAddress: "",
        branchorDept: "",
        profile: "",
        mobileNo: "",
        currentTill: "",
        newBranchOrDept: "",
        newProfile: "",
        newDesignation: "",
        newTill: "",
        reason: "",
        startDate: "",
        endDate: "",
        status: "",
        rqstType: "",
        click: ""

    };
    $scope.approveRqst.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');


    $scope.approvedMessage = function (resp, Epf, rqstType, bod) {

        if (resp == true) {
            $scope.approveRqst.epfNo = Epf;
            $scope.approveRqst.rqstType = rqstType;
            $scope.approveRqst.click = bod;

            if (rqstType == 'User Grant') {
                $("#addItemTypModal").modal("hide");
                $("#approveGrant").modal("hide");
            } else if (rqstType == 'User Transfer') {
                $("#transItemTypModal").modal("hide");
                $("#approveTransfer").modal("hide");
            } else if (rqstType == 'User Inactivate') {
                $("#inactivateItemTypModal").modal("hide");
                $("#approveInactivate").modal("hide");
            } else {
                $("#temppItemTypModal").modal("hide");
                $("#approveTepmory").modal("hide");
            }

            var method = "PUT";
            var url = 'approverqst/approve_done';
            $scope.createApproveRqstBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.approveRqst),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {
//                        var msg = $scope.commmonRefresh();
                        var msg = $scope.getApproveRqsts();
                        if (data == "Request Fail") {
                            sweetAlert("Alert..!", data, "error");
                        } else {
                            sweetAlert("Alert..!", data, "success");
                        }
                    }
                ]
            });
        } else {

            if (rqstType == 'User Grant') {
                $("#addItemTypModal").modal("hide");
                $("#approveGrant").modal("hide");
            } else if (rqstType == 'User Transfer') {
                $("#transItemTypModal").modal("hide");
                $("#approveTransfer").modal("hide");
            } else if (rqstType == 'User Inactivate') {
                $("#inactivateItemTypModal").modal("hide");
                $("#approveInactivate").modal("hide");
            } else {
                $("#temppItemTypModal").modal("hide");
                $("#approveTepmory").modal("hide");
            }

        }

    };

    $scope.rejecctMessage = function (resp, Epf, rqstType, bod) {

        if (resp == true) {
            $scope.approveRqst.epfNo = Epf;
            $scope.approveRqst.rqstType = rqstType;
            $scope.approveRqst.click = bod;

            if (rqstType == 'User Grant') {
                $("#addItemRejectTypModal").modal("hide");
                $("#approveGrant").modal("hide");
            } else if (rqstType == 'User Transfer') {
                $("#transItemRejectTypModal").modal("hide");
                $("#approveTransfer").modal("hide");
            } else if (rqstType == 'User Inactivate') {
                $("#inactivateItemRejectTypModal").modal("hide");
                $("#approveInactivate").modal("hide");
            }

            var method = "PUT";
            var url = 'approverqst/reject_done';
            $scope.createApproveRqstBusy = $http({
                method: method,
                url: url,
                data: angular.toJson($scope.approveRqst),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: [
                    function (data) {
                         var msg = $scope.getApproveRqsts();
                        if (data == "Request Rejected Fail") {
                            sweetAlert("Alert..!", data, "error");
                        } else {
                            sweetAlert("Alert..!", data, "success");
                        }
                    }
                ]
            });
        } else {

            if (rqstType == 'User Grant') {
                $("#addItemRejectTypModal").modal("hide");
                $("#approveGrant").modal("hide");
            } else if (rqstType == 'User Transfer') {
                $("#transItemRejectTypModal").modal("hide");
                $("#approveTransfer").modal("hide");
            } else if (rqstType == 'User Inactivate') {
                $("#inactivateItemRejectTypModal").modal("hide");
                $("#approveInactivate").modal("hide");
            }

        }

    };

    $scope.getApproveRqsts = function (session) {

        $scope.assignData = [];
        var method = "GET";
        var url = 'approverqst/approvel_rqst';
        $scope.createApproveRqstBusy = $http({
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


    $scope.getListByRqstId = function (userId, RqstType) {

        $scope.approveRqst.epfNo = userId;
        $scope.approveRqst.rqstType = RqstType;

        $scope.assignDataById = [];
        var method = "POST";
        var url = 'approverqst/get_list_by_rqstid';


        $scope.createApproveRqstBusy = $http({
            method: method,
            url: url,
            data: angular.toJson($scope.approveRqst),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: [
                function (data) {
                    $scope.approveRqst = JSON.parse(data);
                    $scope.approveRqst.toDay = $filter('date')(new Date(), 'MM/dd/yyyy');
                }
            ]

        });
        console.log($scope.approveRqst);
//        var msg = $scope.dataLoad();
    };

    $scope.viewScreens = function (rqstType) {

        if (rqstType == 'User Grant') {
            $("#approveGrant").modal("show");

        } else if (rqstType == 'User Transfer') {
            $("#approveTransfer").modal("show");

        } else if (rqstType == 'User Inactivate') {
            $("#approveInactivate").modal("show");

        } else {
            $("#approveTepmory").modal("show");

        }

    };


    $scope.showUpdateModalApprove = function (rqstid, epf, branchordept, newbranchordept, profile, newprofile, designation, newdesignation, till, newtill, startdate, enddate, reason) {

        $scope.approveRqst.rqstId = rqstid;
        $scope.approveRqst.epfNo = epf;
        $scope.approveRqst.branchorDept = branchordept;
        $scope.approveRqst.newBranchOrDept = newbranchordept;
        $scope.approveRqst.profile = profile;
        $scope.approveRqst.newProfile = newprofile;
        $scope.approveRqst.designation = designation;
        $scope.approveRqst.newDesignation = newdesignation;
        $scope.approveRqst.currentTill = till;
        $scope.approveRqst.newTill = newtill;
        $scope.approveRqst.startDate = startdate;
        $scope.approveRqst.endDate = enddate;
        $scope.approveRqst.reason = reason;
//        $("#newAddUserModelView").modal("show");
    };

//    $scope.approveUser = function (Epf) {
//
//        var method = "PUT";
//        var url = 'users/approve_user/' + Epf;
//        $scope.createUserBusy = $http({
//            method: method,
//            url: url,
//            data: angular.toJson($scope.approveRqst),
//            headers: {
//                'Content-Type': 'application/json'
//            },
//            transformResponse: [
//                function (data) {
//                    _success(data);
//                }
//            ]
//        });
//    };

    $scope.showSaveDislog = function (response) {

        if (response == 1) {

            $("#addItemTypModal").modal("show");
        } else if (response == 2) {

            $("#transItemTypModal").modal("show");
        } else if (response == 3) {

            $("#inactivateItemTypModal").modal("show");
        } else if (response == 4) {
            $("#temppItemTypModal").modal("show");
        } else if (response == 5) {
            $("#addItemRejectTypModal").modal("show");
        } else if (response == 6) {
            $("#transItemRejectTypModal").modal("show");
        } else if (response == 7) {
            $("#inactivateItemRejectTypModal").modal("show");
        }

    };

    $scope.commmonRefresh = function () {

        location.reload();
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