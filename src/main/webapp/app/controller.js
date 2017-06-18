chatApp.controller("ChatCtrl", function($scope, ChatService) {
    $scope.messages = [];
    $scope.message = "";
    $scope.max = 140;
    $scope.addMessage = function() {
        ChatService.send($scope.message);
        $scope.message = "";
    };
    ChatService.receive().then(null, null, function(message) {
        $scope.messages.push(message);
    });
});

chatApp.controller('logoutController', function ($scope, $location, UsersService) {
    $scope.isAdmin=false;
    console.log("4545");
    UsersService.logout($scope);
});

chatApp.controller('mainController', function ($scope,$rootScope,$modal,$log, $location, UsersService) {
    UsersService.isAuthorized($scope)
    $scope.username = "";
    $scope.password = "";
    $scope.isAdmin=false;
    $scope.isUser=false;
    $rootScope.viewer = null;
    $rootScope.tmpImages = [];
    $scope.login = function () {
        UsersService.login($scope,UsersService.isAuthorized);
    }
});




chatApp.controller("Registrate", function($scope, UsersService) {
     $scope.profile={};

});