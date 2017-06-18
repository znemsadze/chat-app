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
