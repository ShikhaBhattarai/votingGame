var chatApp = angular.module('voting-game', []);

chatApp.controller('ChatController', function($scope, $http) {

    var connection = new WebSocket('ws://localhost:8080/voting-game/chat');

    // When the connection is open, send some data to the server 
    connection.onopen = function() {
        connection.send('Ping'); // Send the message 'Ping' to the server
    };

    // Log errors b
    connection.onerror = function(error) {
        console.log('WebSocket Error ' + error);
    };

    // Log messages from the server
    connection.onmessage = function(e) {
        displayData(e.data);
        data = e.data.split("|");
        if ($scope.otherUser != data[0] && $scope.username != data[0]) {
            showNotification(data[0])
        }
    };

    $scope.getUsers = function() {
        $http.get("/voting-game/users?action=findall")
            .then(function(resp) {
                $scope.users = resp.data;
                if (typeof $scope.otherUser == 'undefined') {
                    $scope.selectUser($scope.lastchattedwith, $scope.users);
                }
            });
        setTimeout($scope.getUsers, 2000);
    }

    $scope.populateUnreadList = function() {
        $http.get("/voting-game/users?action=getunread&user="+ $scope.username)
            .then(function(resp) {
                $scope.myUnreadUserList = resp.data;
            });
        setTimeout($scope.populateUnreadList, 500);
    }

    $scope.checkUnreadList = function(user, myUnreadUserList) {
        for(u in myUnreadUserList){
            if($scope.otherUser == user && user == myUnreadUserList[u]){
                $scope.clearUnread($scope.username, myUnreadUserList[u]);
            } else if(user==myUnreadUserList[u]){
                return true;
            }
        }
        return false;
    }

    $scope.clearUnread = function(user, hasunreadfrom) {
        $http.get("/voting-game/users?action=clearunread&user="+user+"&hasunreadfrom="+hasunreadfrom)
            .then(function(resp) {
            });
    }

    $scope.getCurrentUser = function() {
        $http.get("/voting-game/users?action=getcurrent")
            .then(function(resp) {
                var data = resp.data;
                $scope.username = data.username;
                $scope.firstname = data.firstname;
                $scope.lastname = data.lastname;
                $scope.lastchattedwith = data.lastchattedwith;
                $scope.lastlogoff = data.lastlogoff;
                $scope.registerToSocket(data.username);
                $scope.getUsers();
                $scope.populateUnreadList();
            });

    }

    function getMessages(user1, user2) {
        $http.post("/voting-game/messages", {}, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            params: {
                "user1": user1,
                "user2": user2
            }
        })
            .then(function(resp) {
                var messages = resp.data;
                for (var m in messages) {
                    var data = messages[m].sender + "|" + messages[m].recipient + "|" + messages[m].messagetext;
                    if(m != 0){
                        var datetime =parseDate(messages[m].datetime)
                        var prevDatetime =parseDate(messages[m-1].datetime)
                        var diff = Math.abs(datetime - prevDatetime);
                        if(diff > (60*1000*15)){
                            var dateString= datetime.getMonth() + "/" + datetime.getDate() + "/" + datetime.getFullYear() + " " + ((datetime.getHours() + 11) % 12 + 1) + ":" + ((datetime.getMinutes()<10?'0':'') + datetime.getMinutes());
                            displayData("-------------"+dateString+"-------------");
                        }
                    }
                    displayData(data);
                }
            });
    }

    function parseDate(jsonObject){
        var year=jsonObject.date.year;
        var month=jsonObject.date.month;
        var day=jsonObject.date.day;
        var hour=jsonObject.time.hour;
        var minute=jsonObject.time.minute;
        var second=jsonObject.time.second;
        d = new Date(year,month,day,hour,minute,second);
        return d;
    }

    $scope.selectUser = function(selectedUsername, users) {
        if (connection && selectedUsername != $scope.username && selectedUsername != null) {
            var chatBox = document.getElementById('chat-box-1');
            for (var u in users) {
                if (users[u].username == selectedUsername) {
                    $scope.otherUser = users[u].username;
                    $scope.otherUserF = users[u].firstname;
                    $scope.otherUserL = users[u].lastname;
                }
            }
            $scope.clearUnread($scope.username, selectedUsername);
            chatBox.innerHTML = "";
            getMessages($scope.username, selectedUsername);
        }
    }

    $scope.registerToSocket = function(username) {
        sendMessage("register=" + username);
    }

    $scope.chat = function() {
        var text = $scope.toSend;
        var from = $scope.username;
        var to = $scope.otherUser;
        if (text != null) {
            sendMessage(from + "|" + to + "|" + text);
            document.getElementById("chatInput").value = "";
        }
    }

    function displayData(data) {
        var chat = data;
        var data = data.split("|");
        var chatBox = document.getElementById('chat-box-1');
        if (data[1] != null && data[2] != null) {
            if ($scope.otherUser == data[0] || $scope.username == data[0]) {
                chatBox.innerHTML = chatBox.innerHTML + "<br/>" + data[0] + ": " + data[2];
            }
        } else {
            chatBox.innerHTML = chatBox.innerHTML + "<br/>" + chat;
        }
        chatBox.scrollTop = chatBox.scrollHeight;
    }

    function sendMessage(msg) {
        waitForSocketConnection(connection, function() {
            connection.send(msg);
        });
    }

    function waitForSocketConnection(socket, callback) {
        setTimeout(
            function() {
                if (socket.readyState === 1) {
                    if (callback != null) {
                        callback();
                    }
                    return;

                } else {
                    waitForSocketConnection(socket, callback);
                }

            }, 5); // wait 5 milisecond for the connection...
    }

    function showNotification(username) {
        if (Notification.permission !== "granted") {
            Notification.requestPermission();
        } else {
            var notification = new Notification('New Message ', {
                icon: 'http://www.clipartbest.com/cliparts/dT7/eGE/dT7eGEonc.png',
                body: "Hey there! " + username + " has sent you a message!",
            });

            notification.onclick = function() {
                $scope.selectUser(username, $scope.users);
                window.focus();
            };
        }
    }

});

chatApp.controller('LoginController', function($scope, $http) {

    $scope.login = function() {
        $http.post("/voting-game/register", {}, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            params: {
                "action": "login",
                "username": $scope.username
            }
        })
            .then(function(resp) {
                var json = resp.data;
                console.log(json.result)
                if (json.result == "loginSuccess") {
                    window.location = "/voting-game/index.html";
                } else {
                    $scope.showReg = true;
                }
            });
    }

    $scope.register = function() {
        $http.post("/voting-game/register", {}, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            params: {
                "action": "register",
                "username": $scope.username,
                "firstname": $scope.firstname,
                "lastname": $scope.lastname
            }
        })
            .then(function(resp) {
                var json = resp.data;
                if (json.result == "registerSuccess") {
                    window.location = "/voting-game/index.html";
                } else if (json.result == "alreadyRegistered") {
                    window.location = "/voting-game/index.html";
                }
            });
    }

});/**
 * Created by shikhabhattarai on 11/2/15.
 */
