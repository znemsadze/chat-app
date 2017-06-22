chatApp.controller("ChatCtrl", function ($scope, ChatService) {
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
});

chatApp.controller('logoutController', function ($scope, $location, UsersService) {
    $scope.isAdmin = false;
    console.log("4545");
    UsersService.logout($scope);

});

chatApp.controller('mainController', function ($scope, $rootScope, $modal, $log, $location, UsersService) {
    UsersService.isAuthorized($scope)
    $scope.username = "";
    $scope.password = "";
    $scope.isAdmin = false;
    $scope.isUser = false;
    $rootScope.viewer = null;
    $rootScope.tmpImages = [];
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


chatApp.controller("RegistrateController", function ($scope, UsersService, $location) {
    $scope.profile = {};
    $scope.registrateProfile = function () {
        $scope.startLoading();
        UsersService.saveUser($scope);
    }
    $scope.loadCaptcha = function () {
        UsersService.loadCaptchaName($scope);

    }
    $scope.loadCaptcha();
    $scope.login = function () {
        $location.path("login");
    }

});