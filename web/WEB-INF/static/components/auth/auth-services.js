var app = angular.module("auth.services.module", ["auth.constants.module", "ngCookies"]);

app.service("AuthService", function ($http, $rootScope, SessionService, $httpParamSerializer, AUTH_EVENTS, DEF_URLS, $cookies) {

    var authService = {};

    authService.obtainAccessToken = function (params){
        var req = {
            method: "POST",
            url: DEF_URLS.basic + "/oauth/token",
            headers: {"Content-type": "application/x-www-form-urlencoded; charset=utf-8"},
            data: $httpParamSerializer(params)
        };

        return $http(req)
            .then(function(data){
                $http.defaults.headers.common.Authorization= 'Bearer ' + data.data.access_token;
                var expireDate = new Date (new Date().getTime() + (1000 * data.data.expires_in));
                $cookies.put("access_token", data.data.access_token, {expires: expireDate, secure: true});
                $cookies.put("refresh_token", data.data.refresh_token, {secure: true});
            },
            function (error) {
                $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
                console.log("AuthService.obtainAccess.error");
                console.log(error);
                return SessionService.destroy();
            }
        );
    };

    authService.login = function (loginData) {
        return authService.obtainAccessToken(loginData)
            .then(
                function (resp) {
                    return authService.getUserProfile()
                        .then(function (user) {
                            return user;
                        })
                }
            );
    };

    authService.getUserProfile = function () {
        var req = {
            method: "GET",
            url: DEF_URLS.basic + "/api/user/profile"
        };

        return $http(req).then(
            function (resp) {
                console.log(resp);
                SessionService.create(
                    resp.data.principal.id,
                    resp.data.principal.username,
                    resp.data.principal.loginProvider,
                    resp.data.authorities
                );
                return SessionService.session;
            },
            function (error) {
                console.log("AuthService.getUserProfile.error");
                console.log(error);
                return SessionService.destroy;
            }
        )
    };

    authService.isAuthenticated = function () {
        var isAllowed = false;
        if (angular.isDefined(SessionService.session))
        {
            isAllowed = !!SessionService.session.userId;
        }
        return isAllowed;
    };

    authService.isAuthorized = function (authorizedRoles) {
        var isAllowed = false;

        if (!angular.isArray(authorizedRoles)) {
            authorizedRoles = [authorizedRoles];
        }

        if (angular.isDefined(SessionService.session) && SessionService.session !== null)
        {
            angular.forEach(SessionService.session.userRoles, function (role) {
                if (authService.isAuthenticated() && authorizedRoles.indexOf(role.authority) !== -1)
                {
                    isAllowed = true;
                }
            })
        }

        return isAllowed;
    };

    return authService;
});

app.service("SessionService", function () {
    var session = {};

    this.create = function (userId, username, loginProvider, userRoles) {
        console.log("SessionService.create.invoked");
        session.userId = userId;
        session.username = username;
        session.loginProvider = loginProvider;
        session.userRoles = userRoles;
        this.session = session;
    };
    this.destroy = function () {
        this.session = null;
    };
});
