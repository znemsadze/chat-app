chatApp.service("UsersService", function ($http, $location, CommonServices, RemoteService) {
    var instance = this;
    this.isAuthorized = function ($scope) {
        $scope.user = {};
        RemoteService.requestGet("rest/api/users/current", null, function (data) {
            $scope.user = data.data;
            console.log("asdasd");
            if ($scope.user == null || $scope.user.username == "anonymousUser") {
                $location.path("login");
            } else {
                $location.path("chat");
            }
        }, function (data) {
            $location.path("login");

        });
    }


    this.getUser = function ($scope, loadFn) {
        RemoteService.requestGet('rest/api/users/current', null, function (data) {
            $scope.user = data.data;
            loadFn(data.data);
            if ($scope.user == null || $scope.user.username == "anonymousUser") {
                $location.path("login");
            } else {
                if ($scope.user.roleId == 1) {
                    $scope.isAdmin = true;
                    $scope.isUser = false;
                } else if ($scope.user.roleId == 2) {
                    $scope.isUser = true;
                    $scope.isAdmin = false;
                } else {
                    $scope.isUser = false;
                    $scope.isAdmin = false;
                }
            }
        }, function (data) {
            $location.path("login");
        });
    }


    this.login = function ($scope, loadUser) {
        $scope.user = {};
        RemoteService.requestPost("api/login", {username: $scope.username, password: $scope.password},
            function (data) {
                $scope.user = data.data;
                if ($scope.user != null && $scope.user.username != "anonymousUser") {
                    loadUser($scope);
                } else {
                    $scope.errorMessage = "არასწორი მომხმარებლის სახელი ან პაროლი";
                }
            }, function (data) {
                $scope.errorMessage = "არასწორი მომხმარებლის სახელი ან პაროლი";
            })
    }

    this.logout = function ($scope) {
        RemoteService.requestPost("rest/api/logout", null, function (data) {
            $scope.user = data.data;
            if ($scope.user != null && $scope.user.username != "anonymousUser") {
                $location.path("home");
            } else {
                $location.path("login");
            }
        }, function (data) {
        });
    }

    this.getAllRoles = function ($scope) {
        RemoteService.requestGet('rest/dictionary/users/role', null, function (data) {
            $scope.roles = data.data;
        }, function (data) {
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

    this.getUserById = function (userId, $scope) {
        RemoteService.requestGet('rest/admin/api/users/byid/' + userId, null,
            function (data) {
                $scope.edtUser = data.data;
            },
            function (data) {
            })
    }

    this.saveUser = function ($scope) {
        RemoteService.postObj('rest/admin/api/users/byid', $scope.profile, function (data) {
            $location.path("login");
        }, function (data) {
            if (data.status == 409) {
                $scope.errorMessage = "დამცავი კოდი არასწორია";
            } else if (data.status == 400) {
                instance.loadCaptchaName($scope)
                $scope.errorMessage = "მომხმარებლის სახელი უკვე არსებობს";
            }
            instance.loadCaptchaName($scope)
            console.log(data);
        }, function () {
            CommonServices.stopLoading($scope);
        })
    }


    this.saveProfile = function (profile, succescb, errorcb) {
            RemoteService.postObj("rest/admin/api/users/byid",profile,succescb,errorcb);
    }


    this.getProfileById=function ( profileId,succescb,errorcb) {
        RemoteService.requestGet("rest/admin/api/users/byid/"+profileId,null,succescb,errorcb);
    }


    this.loadCaptchaName = function ($scope) {
        RemoteService.requestGet("files/filename",null,function (data) {
            $scope.profile.captchaName = data.data.text;
        }, function (data) {
            console.log(data);
        });
    }

});