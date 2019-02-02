var app = angular.module("auth.controllers.module", ["auth.constants.module", "auth.services.module"]);

app.controller("AuthenticationCtrl", function ($scope, $rootScope, AUTH_EVENTS, AuthService, $state, $cookies) {
    $scope.loginData = {
        grant_type: "password",
        username:   "",
        password:   "",
        client_id:  "MyZoeAppTestClientId1"
    };


    $scope.login = function () {
        console.log("AuthenticationCtrl.login.invoked");
        AuthService.login($scope.loginData).then(
            function (user) {
                $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
                $scope.setCurrentUser(user);
                $state.go("home");
            }, function (error) {
                $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
                $scope.setCurrentUser(null);
                $scope.error = error;
                console.log("Failed Login:");
                console.log(error);
            }
        );
    };
});
