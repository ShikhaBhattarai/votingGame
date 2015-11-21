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
            });
        setTimeout($scope.getPlayers, 2000);
    }

    $scope.getLeaders = function() {
            $http.get("/the-voting-game/players?action=getleaders")
                .then(function(resp) {
                    $scope.leaders = resp.data;
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
                //$scope.getGameid();
            });
    }

    // createGameid is functional - Angel
    $scope.createGameid = function() {
        $http.post("/the-voting-game/gamecreator", {}, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            params: {
                "action": "creategameid",
                "g_creator": $scope.u_name
            }
        })
        .then(function(resp) {
            var json = resp.data;
            console.log(json.result);
            if (json.result == "idcreated") {
                $scope.g_id = json.g_id;
                $scope.g_creator = $scope.u_name;
            }
        });
        window.location = "/the-voting-game/invite.html";
    }

    $scope.selectPlayer = function(selectedE_mail) {
        console.log(selectedE_mail);
        //$scope.getGameid();
        $http.post("/the-voting-game/gamecreator", {}, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            params: {
                "action": "getgameid",
                "g_creator": $scope.u_name
            }
        })
        .then(function(resp) {
            var json = resp.data;
            console.log(json.result)
            if (json.result == "idretrieved") {
                $scope.g_id = json.g_id;
                $scope.g_creator = $scope.u_name;
                $scope.is_started = json.is_started;
                $scope.p_joined = json.p_joined;
                console.log("the retrieved id is: " + $scope.g_id);
                console.log("the retrieved creator is: " + $scope.g_creator);
                console.log("the retrieved status is: " + $scope.is_started);
                console.log("the retrieved players joined is: " + $scope.p_joined);
                if ($scope.g_id >= 1) {
                            if (selectedE_mail != $scope.e_mail) {
                                $scope.invitePlayer($scope.g_id, $scope.f_name, selectedE_mail);
                            }
                        } else {
                            console.log("Game id is invalid");
                        }
            }
        });
    }

    $scope.invitePlayer = function(g_id, g_creator, e_mail) {
        $http.post("/the-voting-game/invite", {}, {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            params: {
              "g_id": g_id,
              "g_creator": g_creator,
              "e_mail": e_mail
            }
        })
        .then(function(resp) {
            // stay where you are
        });
    }

    $scope.getCurrentGames = function() {
        $http.get("/the-voting-game/gamecreator?action=getcurrentgames")
            .then(function(resp) {
                $scope.currentgames = resp.data;
            });
        setTimeout($scope.getCurrentGames, 2000);
    }

    $scope.registerToSocket = function(u_name) {
        sendMessage("register=" + u_name);
    }

    $scope.join = function() {
        window.location = "/the-voting-game/gameplay.html";
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
                $scope.selectedPlayer(u_name, $scope.players);
                window.focus();
            };
        }
    }

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
