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

chatApp.controller('mainController', function ($scope,   $modal, $log, $location, UsersService) {
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

chatApp.controller("RegistrateController", function ($scope, UsersService, $location,CommonServices) {
    $scope.profile = {};
    $scope.errorMessage="";
    $scope.registerProfile = function () {
        if($scope.profile.password!=$scope.profile.password1){
            $scope.errorMessage="განმეორებით შეყვანილი პაროლი არ ემთხვევა პაროლს."
        }else{
            CommonServices.startLoading($scope );
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