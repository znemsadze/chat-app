chatApp.service("ChatService", function ($q, $timeout, $cookies, $cookieStore,MessageHistService) {

    var service = {}, listener = $q.defer(), socket = {
        client: null,
        stomp: null
    }, messageIds = [], users = {};
    var chatService = this;
    var currentUserId = -1;

    this.addUser = function (user) {
        user[user.id] = user;
    }

    service.setCurrentUserId = function (userId) {
        currentUserId = userId;
    }


    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = "/chat-app/chat";
    service.CHAT_TOPIC = "/topic/message";
    service.CHAT_BROKER = "/app/chat";

    service.receive = function () {
        return listener.promise;
    };

    service.send = function (message) {
        var id = Math.floor(Math.random() * 1000000);
        socket.stomp.send(service.CHAT_BROKER, {
            priority: 9
        }, JSON.stringify({
            message: message,
            id: id,
            xAuthToken: $cookies.get('X-AUTH-TOKEN')
        }));
        messageIds.push(id);
    };

    var reconnect = function () {
        $timeout(function () {
            initialize();
        }, this.RECONNECT_TIMEOUT);
    };

    var getMessage = function (data) {
        var message = JSON.parse(data), out = {};
        out.message = message.message;
        out.username = message.username;
        out.messageTime = new Date(message.messageTime);
        //if (_.includes(messageIds, message.id)) {
        if (message.userId == currentUserId) {
            out.self = true;
        }
        return out;
    };

    var startListener = function () {
        socket.stomp.subscribe(service.CHAT_TOPIC, function (data) {
            listener.notify(MessageHistService.parseMessage(data.body));
        });
    };

    var initialize = function () {
        socket.client = new SockJS(service.SOCKET_URL);
        socket.stomp = Stomp.over(socket.client);
        socket.stomp.debug = null;
        socket.stomp.connect({}, startListener);
        socket.stomp.onclose = reconnect;
    };

    initialize();
    return service;
});

