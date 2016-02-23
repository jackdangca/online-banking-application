"use strict";
angular.module("yapp", ["ui.router", "ngAnimate"]).config(["$stateProvider", "$urlRouterProvider", function (r, t) {
    t.when("/dashboard", "/dashboard/settings", "/dashboard/accounts", "/dashboard/accounts/addAccount"), t.otherwise("/login"), r.state("base", {
        "abstract": !0,
        url: "",
        templateUrl: "/views/base.html"
    }).state("login", {
        url: "/login",
        parent: "base",
        templateUrl: "/views/login.html",
        controller: "LoginCtrl"
    }).state("dashboard", {
        url: "/dashboard",
        parent: "base",
        templateUrl: "/views/dashboard.html",
        controller: "DashboardCtrl"
    }).state("settings", {
        url: "/settings",
        parent: "dashboard",
        templateUrl: "/views/dashboard/settings.html"
    }).state("accounts", {
        url: "/accounts",
        parent: "dashboard",
        templateUrl: "/views/dashboard/accounts.html",
        controller: "AccountsCtrl"
    }).state("addAccount", {
        url: "/accounts/addAccount",
        parent: "dashboard",
        templateUrl: "/views/dashboard/add_account.html",
        controller: "AccountCtrl"
    }).state("transaction", {
        url: "/transaction",
        parent: "dashboard",
        templateUrl: "/views/dashboard/transaction.html",
        controller: "TransactionCtrl"
    })
}]), angular.module("yapp").controller("LoginCtrl", function ($scope, $http, $location, $route) {
    $scope.submit = function () {
        $http.post({
                url: "/",
                data: $scope.credentials,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                }
            })
            .then(function (data) {
                location.href = '/#/dashboard/settings';
            });
    }
}), angular.module("yapp").controller("DashboardCtrl", function ($scope, $http, $state, $location) {

    $scope.$state = $state;
    $scope.editFailed = false;
    $scope.editPassed = false;

    $http.get("/api/user/me")
        .success(function (data) {
            if (!data.result) {
                $location.path('/login');
            }
            $scope.user = data.result;
        })
        .error(function () {
            $location.path('/login');
        });
    $scope.editUser = function () {
        $http({
            method: 'POST',
            url: '/api/user/' + $scope.user.userId + '/edit',
            data: $scope.user,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function (obj) {
                var str = [];
                for (var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
        })
            .success(function () {
                $scope.editPassed = true;
                $scope.editFailed = false;
            }).error(function () {
            $scope.editPassed = false;
            $scope.editFailed = true;
        });
    }
}) , angular.module("yapp").controller("AccountsCtrl", function ($scope, $http, $state, $location) {

    $('.modal-trigger').leanModal();

    $http.get("/api/user/me")
        .success(function (data) {
            if (!data.result) {
                $location.path('/login');
            }
            $scope.user = data.result;
        })
        .error(function () {
            $location.path('/login');
        });

    $http.get('/api/user/' + $scope.user.userId + '/account').
    success(function (data) {
        $scope.accounts = data.result;

        angular.forEach($scope.accounts, function (account) {
            $http.get('/api/user/' + $scope.user.userId + '/account/' + account.accountId + '/transaction')
                .success(function (data) {
                    account.transaction = data.result;
                    console.log($scope.accounts);
                });
        });
    });

    $scope.showAccountModal = function (id) {
        $('#accountModal' + id).openModal();
    };

    $scope.accountEdit = function (account) {
        var url = '/api/user/'
            + $scope.user.userId
            + '/account/'
            + account.accountId
            + '/edit/'
            + account.accountType;

        $http({
            url: url,
            method: 'post',
            data: {label: account.label},
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function (obj) {
                var str = [];
                for (var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
        }).success(function () {
            console.log(account);
        });
    };

}), angular.module("yapp").controller("AccountCtrl", function ($scope, $http, $state, $location) {

    $scope.creationPassed = false;
    $scope.creationFailed = false;

    $http.get('/api/promotion').
    success(function (data) {
        $scope.promotions = data.result;
    });

    $scope.submitSavingForm = function () {
        $http({
            url: '/api/user/' + $scope.user.userId + '/account/add/saving',
            method: 'post',
            data: $scope.SavingAccount,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function (obj) {
                var str = [];
                for (var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
        }).success(function () {
            $scope.creationPassed = true;
            $scope.creationFailed = false;
        }).error(function () {
            $scope.creationPassed = false;
            $scope.creationFailed = true;
        });
    }

    $scope.submitTermForm = function () {
        $http({
            url: '/api/user/' + $scope.user.userId + '/account/add/term',
            method: 'post',
            data: $scope.TermAccount,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function (obj) {
                var str = [];
                for (var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
        }).success(function () {
            $scope.creationPassed = true;
            $scope.creationFailed = false;
        }).error(function () {
            $scope.creationPassed = false;
            $scope.creationFailed = true;
        });
    }

    $scope.submitCurrentForm = function () {
        $http({
            url: '/api/user/' + $scope.user.userId + '/account/add/current',
            method: 'post',
            data: $scope.CurrentAccount,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function (obj) {
                var str = [];
                for (var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
        }).success(function () {
            $scope.creationPassed = true;
            $scope.creationFailed = false;
        }).error(function () {
            $scope.creationPassed = false;
            $scope.creationFailed = true;
        });
    }

}), angular.module("yapp").controller("TransactionCtrl", function ($scope, $http, $state, $location) {


    $scope.transactionPassed = false;
    $scope.transactionFailed = false;

    $http.get("/api/user/me")
        .success(function (data) {
            if (!data.result) {
                $location.path('/login');
            }
            $scope.user = data.result;
        })
        .error(function () {
            $location.path('/login');
        });

    $http.get('/api/user/' + $scope.user.userId + '/account').
    success(function (data) {
        $scope.accounts = data.result;
    });


    $scope.send = function () {
        $http({
            url: '/api/user/' + $scope.user.userId + '/account/' + $scope.Transaction.srcAccountId + '/transfer',
            method: 'post',
            data: {
                dstAccountNum: $scope.Transaction.dstAccountNum,
                balance: $scope.Transaction.balance,
            },
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function (obj) {
                var str = [];
                for (var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
        }).success(function () {
            $scope.transactionPassed = true;
            $scope.transactionFailed = false;
        }).error(function () {
            $scope.transactionPassed = false;
            $scope.transactionFailed = true;
        });
    }

});


function selectHandler() {
    var select = document.getElementById("account_select");
    $("#saving_form").hide();
    $("#term_form").hide();
    $("#current_form").hide();
    switch (select.value) {
        case "1" :
            $("#saving_form").show();
            break;
        case "2":
            $("#term_form").show();
            break;
        case "3":
            $("#current_form").show();
            break;
    }
}