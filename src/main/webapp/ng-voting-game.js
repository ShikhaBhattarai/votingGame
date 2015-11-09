// ng-voting-game.js
// for The Voting Game

var gameApp = angular.module('voting-game', []);

gameApp.controller('GameController', function($scope, $http) {

    //var connection = new WebSocket('ws://52.89.91.232:8080/the-voting-game/');
    var connection = new WebSocket('ws://localhost:8080/the-voting-game/game');

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
        if ($scope.otherPlayer != data[0] && $scope.u_name != data[0]) {
            showNotification(data[0])
        }
    };

    $scope.getPlayers = function() {
        $http.get("/the-voting-game/players?action=findall")
            .then(function(resp) {
                $scope.players = resp.data;
                if (typeof $scope.otherPlayer == 'undefined') {
                    $scope.selectPlayer($scope.players);
                }
            });
        setTimeout($scope.getPlayers, 2000);
    }

    $scope.getLeaders = function() {
            $http.get("/the-voting-game/players?action=getleaders")
                .then(function(resp) {
                    //window.location = "/the-voting-game/leaderboard.html";
                    $scope.leaders = resp.data;
                    /*if (typeof $scope.otherPlayer == 'undefined') {
                        $scope.selectPlayer($scope.players);
                    }*/
                });
            setTimeout($scope.getLeaders, 2000);
        }

    $scope.bulkUpload = function() {
                window.location = "/the-voting-game/upload.html";
    }

    $scope.getCurrentPlayer = function() {
        $http.get("/the-voting-game/players?action=currentplayer")
            .then(function(resp) {
                var data = resp.data;
                $scope.e_mail = data.e_mail;
                $scope.f_name = data.f_name;
                $scope.l_name = data.l_name;
                $scope.u_name = data.u_name;
                $scope.g_won = data.g_won;
                $scope.getPlayers();
                //$scope.populateUnreadList();
            });
    }

    $scope.getInvitedPlayer = function() {
            $http.get("/the-voting-game/players?action=invitedplayer")
                .then(function(resp) {
                    var data = resp.data;
                    $scope.e_mail = data.e_mail;
                    $scope.f_name = data.f_name;
                    $scope.l_name = data.l_name;
                    //$scope.u_name = data.u_name;
                    //$scope.g_won = data.g_won;
                    //$scope.getPlayers();
                    //$scope.populateUnreadList();
                    $scope.invitePlayer;
                });
        }

    $scope.invitePlayer = function(String e_mail, String f_name, String l_name) {
            $http.get("/the-voting-game/invite?action=inviteplayer")
                .then(function(resp) {
                    $scope.players = resp.data;
                    if (typeof $scope.otherPlayer == 'undefined') {
                        $scope.selectPlayer($scope.players);
                    }
                });
            setTimeout($scope.getPlayers, 2000);
        }

    $scope.invitePlayer = function(invitedPlayer, players) {
        if (connection && invitedPlayer != $scope.u_name && invitedPlayer != null) {
            //var chatBox = document.getElementById('chat-box-1');
            for (var p in players) {
                if (players[p].u_name == invitedPlayer) {
                    $scope.invitedPlayerE = players[p].e_mail;
                    //$scope.otherPlayerF = players[p].f_name;
                    //$scope.otherPlayerL = players[p].l_name;
                    //$scope.otherPlayerU = players[p].u_name;
                }
            }
        }
    }

    $scope.registerToSocket = function(u_name) {
        sendMessage("register=" + u_name);
    }

    function displayData(data) {
        var chat = data;
        var data = data.split("|");
        var chatBox = document.getElementById('chat-box-1');
        if (data[1] != null && data[2] != null) {
            if ($scope.otherPlayerU == data[0] || $scope.u_name == data[0]) {
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

    function showNotification(u_name) {
        if (Notification.permission !== "granted") {
            Notification.requestPermission();
        } else {
            var notification = new Notification('New Message ', {
                icon: 'http://www.clipartbest.com/cliparts/dT7/eGE/dT7eGEonc.png',
                body: "Hey there! " + u_name + " wants to play The Voting Game!",
            });

            notification.onclick = function() {
                $scope.selectPlayer(u_name, $scope.players);
                window.focus();
            };
        }
    }

    $scope.creategame = function(String u_name) {
            $http.post("/the-voting-game/creategame", {}, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                params: {
                    "action": "creategame",
                    "u_name": $scope.u_name
                }
            })
                .then(function(resp) {
                    var json = resp.data;
                    console.log(json.result)
                    if (json.result == "created") {
                        window.location = "/the-voting-game/invite.html";
                    //} else {
                    //    $scope.showStart = true;
                    //}
                });
        }

         /*$scope.populateUnreadList = function() {
                $http.get("/the-voting-game/users?action=getunread&user="+ $scope.u_name)
                    .then(function(resp) {
                        $scope.myUnreadUserList = resp.data;
                    });
                setTimeout($scope.populateUnreadList, 500);
            }

            $scope.checkUnreadList = function(user, myUnreadUserList) {
                for(u in myUnreadUserList){
                    if($scope.otherUser == user && user == myUnreadUserList[u]){
                        $scope.clearUnread($scope.u_name, myUnreadUserList[u]);
                    } else if(user==myUnreadUserList[u]){
                        return true;
                    }
                }
                return false;
            }

            $scope.clearUnread = function(user, hasunreadfrom) {
                $http.get("/the-voting-game/users?action=clearunread&user="+user+"&hasunreadfrom="+hasunreadfrom)
                    .then(function(resp) {
                    });
            }

            function getMessages(user1, user2) {
                    $http.post("/the-voting-game/messages", {}, {
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

                $scope.chat = function() {
                        var text = $scope.toSend;
                        var from = $scope.u_name;
                        var to = $scope.otherUser;
                        if (text != null) {
                            sendMessage(from + "|" + to + "|" + text);
                            document.getElementById("chatInput").value = "";
                        }
                    }*/

});

gameApp.controller('LoginController', function($scope, $http) {

    $scope.login = function() {
        $http.post("/the-voting-game/authenticate", {}, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            params: {
                "action": "login",
                "u_name": $scope.u_name
            }
        })
            .then(function(resp) {
                var json = resp.data;
                console.log(json.result)
                if (json.result == "loginSuccess") {
                    window.location = "/the-voting-game/home.html";
                } else {
                    $scope.showReg = true;
                }
            });
    }

    $scope.register = function() {
        $http.post("/the-voting-game/authenticate", {}, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            params: {
                "action": "register",
                "e_mail": $scope.e_mail,
                "f_name": $scope.f_name,
                "l_name": $scope.l_name,
                "u_name": $scope.u_name,
            }
        })
            .then(function(resp) {
                var json = resp.data;
                if (json.result == "registerSuccess") {
                    window.location = "/the-voting-game/home.html";
                } else if (json.result == "alreadyRegistered") {
                    window.location = "/the-voting-game/home.html";
                }
            });
    }

});

/**
 * Created by shikhabhattarai on 11/2/15.
 */
