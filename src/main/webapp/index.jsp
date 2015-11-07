<%--
  index.jsp
  for The Voting Game

  Created by IntelliJ IDEA.
  User: shikhabhattarai
  Date: 11/2/15
  Time: 12:58 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel='shortcut icon' type='image/x-icon' href='favicon.ico' />
    <link rel="stylesheet" href="https://storage.googleapis.com/code.getmdl.io/1.0.5/material.indigo-pink.min.css">
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:300,400,500,700" type="text/css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="https://storage.googleapis.com/code.getmdl.io/1.0.5/material.min.js"></script>

    <style>
        .mdl-layout {
            align-items: center;
            justify-content: center;
        }
        .mdl-layout__content {
            padding: 50px;
            flex: none;
        }
    </style>

</head>
<body>

<div class="mdl-layout mdl-js-layout mdl-color--grey-100" ng-controller="LoginController">
    <main class="mdl-layout__content">
        <div class="mdl-card mdl-shadow--6dp">
            <div class="mdl-card__title mdl-color--primary mdl-color-text--white">
                <h4 class="mdl-card__title-text">The Voting Game</h4>
            </div>
			<div class="mdl-layout__content">
			<p> Welcome to the Voting Game!</p>
			<p>Please log in or register to play.</p>
			</div>
                <div class="mdl-card__actions mdl-card--border">
                    <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" ng-hide="showReg" ng-click="login.html" ng-disabled="usernameSection.username.$invalid">Log in</button>
                    <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" ng-click="showReg=true" ng-hide="showReg">Register</button>
                    <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" ng-click="register();" ng-show="showReg" ng-disabled="firstnameSection.firstname.$invalid || lastnameSection.lastname.$invalid || usernameSection.username.$invalid">Register</button>
                </div>
            </div>
        </div>
    </main>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular.min.js"></script>

<script src="ng-voting-game.js"></script>


</body>
</html>