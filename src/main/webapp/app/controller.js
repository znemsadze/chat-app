chatApp.controller("ChatCtrl", function ($scope, ChatService, MessageHistService,UsersService) {
    $scope.messages = [];
    $scope.message = "";
    $scope.max = 140;
    $scope.addMessage = function () {
        ChatService.send($scope.message);
        $scope.message = "";
    };
    ChatService.receive().then(null, null, function (message) {
        $scope.messages.push(message);
    });

    $scope.loadMessageHistory = function (hours) {
        $scope.hours = hours;
        UsersService.getUser($scope,function (data) {
            MessageHistService.setCurrentUserId(data.userId);
            MessageHistService.getHistByHurs($scope);
        });

    }

    $scope.loadMessageHistory(2);


});

chatApp.controller('logoutController', function ($scope, $location, UsersService) {
    $scope.isAdmin = false;
    console.log("4545");
    UsersService.logout($scope);

});

chatApp.controller('mainController', function ($scope, $modal, $log, $location, UsersService) {
    UsersService.isAuthorized($scope)
    $scope.username = "";
    $scope.password = "";
    $scope.isAdmin = false;
    $scope.isUser = false;

    $scope.login = function () {
        UsersService.login($scope, UsersService.isAuthorized);
    }

    $scope.registrate = function () {
        $location.path("registrate");
    }

    $scope.startLoding = function () {
        $scope.modalInstance = $modal.open({
            templateUrl: 'LoadingModal.html',
            backdrop: 'static',
            keyboard: false,
            windowClass: 'center-modal'

        });
    };

    $scope.stopLoading = function () {
        $scope.modalInstance.close();
    }
});

chatApp.controller('LoadingController', function ($scope, $location, UsersService) {


});

chatApp.controller("RegistrateController", function ($scope, UsersService, $location, CommonServices) {
    $scope.profile = {};
    $scope.errorMessage = "";
    $scope.registerProfile = function () {
        if ($scope.profile.password != $scope.profile.password1) {
            $scope.errorMessage = "განმეორებით შეყვანილი პაროლი არ ემთხვევა პაროლს."
        } else {
            CommonServices.startLoading($scope);
            UsersService.saveUser($scope);
        }

    }
    $scope.loadCaptcha = function () {

        UsersService.loadCaptchaName($scope);

    }
    $scope.loadCaptcha();

    $scope.login = function () {
        $location.path("login");
    }
});

chatApp.controller("ProfileController", function ($scope, UsersService, $location,
                                                  CommonServices, UploadService,RemoteService) {
    $scope.profile = {};
    $scope.genders=[];
    $scope.getProfile = function () {
        UsersService.getUser($scope, function (data) {

                UsersService.getProfileById(data.userId,function (data) {
                    $scope.profile = data.data;
                });
            }
        );
    }
    $scope.loadStaticParams=function(){
        RemoteService.requestGet("dictionary/gender",null,function (data) {
            console.log(data);
            $scope.genders=data.data;
        });
    }
    $scope.editProfile = function () {
        $location.path("edit-profile");
    }

    $scope.uploadFile = function (files) {
        $scope.actionDisabled = true;
        $scope.saveButtonClass = "fa fa-spinner fa-spin";
        UploadService.uploadFile(files, $scope,
            function (data) {
                $scope.profile.profileImage = data.fileName;
                console.log(data);
            },
            function (data) {
                alert("სურათის ატვირთვა ვერ მოხრეხდა");
                console.log(data);
            });
    };

    $scope.saveProfile = function () {
        CommonServices.startLoading($scope);
        UsersService.saveProfile($scope.profile,
            function (data) {
                CommonServices.stopLoading($scope);
                $scope.profile = data.data;
                $location.path("profile");
                $scope.getProfile();
            }, function (data) {
                console.log(data);
                alert("მონაცემების შენახვა ვერ მოხეხდა")
                CommonServices.stopLoading($scope);
            })
    }

    $scope.loadStaticParams();
    $scope.getProfile();

});