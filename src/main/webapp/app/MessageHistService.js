/**
 * Created by zviad on 6/23/17.
 */
chatApp.service("MessageHistService", function ($http, $modal, $location, CommonServices) {


    this.getHistByHurs = function ($scope) {

        var req = {
            method: 'GET',
            url: 'message/hist',
            headers: {
                'Content-Type': 'application/json'
            },
            params:{
                hour:$scope.hours
            }
        };

        $http(req).then(function (response) {
                console.log(response);
                $scope.messages=response.data;
            },
            function (error) {
                console.log(error);
            }
        )

    }


});