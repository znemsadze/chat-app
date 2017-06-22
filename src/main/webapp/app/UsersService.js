chatApp.service("UsersService", function ($http, $location) {
    this.isAuthorized = function ($scope) {
        $scope.user = {};
        var req = {
            method: 'GET',
            url: 'rest/api/users/current',
            headers: {
                'Content-Type': 'application/json'
            },
        };
        $http(req).then(function (data) {
            $scope.user = data.data;
            if ($scope.user == null || $scope.user.username == "anonymousUser") {
                $location.path("login");
            } else {
                $location.path("chat");
            }

        }, function (data) {
            $location.path("login");

        }).finally(function () {
        });
    }


    this.getUser = function ($scope) {
        var req = {
            method: 'GET',
            url: 'rest/api/users/current',
            headers: {
                'Content-Type': 'application/json'
            },
        };
        $http(req).then(function (data) {
            $scope.user = data.data;
            if ($scope.user == null || $scope.user.username == "anonymousUser") {
                $location.path("login");
            } else {

                if ($scope.user.roleId ==1) {
                    $scope.isAdmin = true;
                    $scope.isUser = false;
                } else if($scope.user.roleId ==2) {
                    $scope.isUser = true;
                    $scope.isAdmin = false;
                }else{
                    $scope.isUser = false;
                    $scope.isAdmin = false;
                }

            }

        }, function (data) {
            $location.path("login");

        }).finally(function () {

        });
    }


    this.login = function ($scope, loadUser) {
        $scope.user = {};

        var req = {
            method: 'POST',
            url: 'api/login',
            headers: {
                'Content-Type': 'application/json'
            },
            params: {username: $scope.username, password: $scope.password}
        };

        $http(req).then(function (data) {
            $scope.user = data.data;
            if ($scope.user != null && $scope.user.username != "anonymousUser") {
                loadUser($scope);
            } else {
                $scope.errorMessage = "არასწორი მომხმარებლის სახელი ან პაროლი";
            }
        }, function (data) {
            $scope.errorMessage = "არასწორი მომხმარებლის სახელი ან პაროლი";
        }).finally(function () {
        });
    }

    this.logout = function ($scope) {
        var req = {
            method: 'POST',
            url: 'rest/api/logout',
            headers: {
                'Content-Type': 'application/json'
            }
        };
        $http(req).then(function (data) {
            $scope.user = data.data;

            if ($scope.user != null && $scope.user.username != "anonymousUser") {
                $location.path("home");
            } else {
                $location.path("login");
            }
        }, function (data) {
        }).finally(function () {
        });
    }

    this.getAllRoles = function ($scope) {
        var req = {
            method: 'GET',
            url: 'rest/dictionary/users/role',
            headers: {
                'Content-Type': 'application/json'
            }
        };
        $http(req).then(function (data) {
            $scope.roles = data.data;
        }, function (data) {
        }).finally(function () {
        });
    }

    this.getAllUsers = function ($scope) {
        var req = {
            method: 'GET',
            url: 'rest/admin/api/users',
            headers: {
                'Content-Type': 'application/json'
            }
        };
        $http(req).then(function (data) {
            $scope.users = data.data;
        }, function (data) {
        }).finally(function () {
        });
    }

    this.getUserById=function(userId,$scope){
        var req={
            method: 'GET',
            url: 'rest/admin/api/users/byid/'+userId,
            headers: {
                'Content-Type': 'application/json'
            }
        };
        $http(req).then(function (data) {

            $scope.edtUser = data.data;
            console.log($scope.edtUser);
        }, function (data) {

        }).finally(function () {

        });

    }
    this.saveUser=function($scope){
        var req={
            method: 'POST',
            url: 'rest/admin/api/users/byid',
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify($scope.profile)
        };
        $http(req).then(function (data) {
            $scope.edtUser = data.data;
        }, function (data) {

        }).finally(function () {
            $scope.stopLoading();
        });
    }

    this.loadCaptchaName=function($scope){
        console.log("dsdfsdfsdfdfsf")
        var req={
            method: 'GET',
            url: 'files/filename',
            headers: {
                'Content-Type': 'application/json'
            }
        };
        $http(req).then(function (data) {
            $scope.profile.captchaName = data.data.text ;
        }, function (data) {
            console.log(data);
        }).finally(function () {

        });
    }

});