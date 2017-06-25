/**
 * Created by zviad on 6/23/17.
 */
chatApp.service("MessageHistService", function ($http, $modal, $location, CommonServices, RemoteService,UsersService) {

    var messageHistServices = this;

    var currentUserId = -1;

    this.setCurrentUserId = function (userId) {
        currentUserId = userId;
    }

    var users = {};

    this.getHistByHurs = function ($scope) {
        RemoteService.requestGet("message/hist", {hour: $scope.hours}, function (response) {
                $scope.messages = [];
                if (response != null && response.data != null) {
                    response.data.forEach(function (msg) {
                        $scope.messages.push(messageHistServices.parseMessage(msg, true));
                    })
                }
            },
            function (error) {
                console.log(error);
            });
    }

    this.parseMessage = function (data, hist) {

        var message = hist ? data : JSON.parse(data), out = {};
        out.message = message.message;
        out.username = message.username;
        out.messageTime = new Date(message.messageTime);
        if (message.userId == currentUserId) {
            out.self = true;
        } else {

            out.profileImage = "default-male.png"
            if(users[message.userId]==null){
               UsersService.getProfileById(message.userId,function (data) {
                   console.log(data)
                   users[message.userId]=data.data;
                   if(users[message.userId].profileImage!=null  ){
                       console.log("profileimage"+users[message.userId].profileImage)
                       out.profileImage=users[message.userId].profileImage;
                   }else if(users[message.userId].genderId==1){
                       out.profileImage="default-female.png"
                   }
               }
               );
            }else{
                if(users[message.userId].profileImage!=null  ){
                    console.log("profileimage"+users[message.userId].profileImage)
                    out.profileImage=users[message.userId].profileImage;
                }else if(users[message.userId].genderId==1){
                    out.profileImage="default-female.png"
                }
            }
        }
        return out;
    };



});