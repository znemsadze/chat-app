/**
 * Created by zviad on 6/14/17.
 * app main configs
 */
var chatApp= angular.module("chatApp", [
    'ui.bootstrap',
    'ngRoute',
    'ngCookies'
]);

// angular.module("chatApp.controllers", []);
// angular.module("chatApp.services", []);
chatApp.config(function ($routeProvider) {
    $routeProvider
        .when('/chat', {
            templateUrl: 'pages/chat.html',
            controller: 'ChatCtrl'
        })
        .when('/home', {
            templateUrl: 'pages/chat.html',
            controller: 'ChatCtrl'
        })
        .when('/logout', {
            templateUrl: 'pages/login.html',
            controller: 'logoutController'
        })
        .otherwise({
            templateUrl: 'pages/login.html',
            controller: 'mainController'
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


