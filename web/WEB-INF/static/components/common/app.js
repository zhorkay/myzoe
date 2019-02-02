var app = angular.module("myZoeDiaryApp", [ "auth.controllers.module",
                                            "auth.services.module",
                                            "auth.constants.module",
                                            "ngResource",
                                            "ngRoute",
                                            "ngCookies",
                                            "ui.router",
                                            "ngAnimate",
                                            "angularGrid",
                                            "angular-timeline",
                                            "infinite-scroll"
                                            ]);
/*
app.config(["$httpProvider", function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common["X-Requested-With"];
}]);
*/

app.config(function ($stateProvider, $urlRouterProvider, $locationProvider,$httpProvider, USER_ROLES) {
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: true
    });

    $stateProvider
        .state("home", {
            url: "/home",
            templateUrl: "/static/views/home.html",
            controller: "ApplicationCtrl",
            controllerAs: "ApplicationCtrl",
            data: {
                authorizedRoles: [USER_ROLES.admin]
            }
        })
        .state("login", {
            url: "/login",
            templateUrl: "/static/views/login.html",
            controller: "AuthenticationCtrl",
            controllerAs: "AuthenticationCtrl"
        })
        .state("gallery", {
            url: "/gallery",
            templateUrl: "/static/views/gallery.html",
            controller: "galleryCtrl",
            controllerAs: "galleryCtrl",
            data: {
                authorizedRoles: [USER_ROLES.admin]
            }
        })
        .state("timeline", {
            url: "/timeline",
            templateUrl: "/static/views/timeline.html",
            controller: "eventCtrl",
            controllerAs: "eventCtrl",
            data: {
                authorizedRoles: [USER_ROLES.admin]
            }
        });

    //$urlRouterProvider.otherwise("/home");
});

app.config(function ($httpProvider) {
    $httpProvider.interceptors.push([
        '$injector',
        function ($injector) {
            return $injector.get('AuthInterceptor');
        }
    ]);
});

app.run(function ($rootScope, $state, AuthService, AUTH_EVENTS) {
    $rootScope.$on("$stateChangeStart", function (event, toState, toParams, fromState, fromParams) {
        if ("data" in toState && "authorizedRoles" in toState.data)
        {
            var authorizedRoles = toState.data.authorizedRoles;

            if (!AuthService.isAuthorized(authorizedRoles)) {
                console.log("AuthService.isAuthorized.false");
                event.preventDefault();
                if (AuthService.isAuthenticated()) {
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
                    console.log("AuthService.isAuthenticated.true");
                } else {
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
                    console.log("AuthService.isAuthenticated.false");
                    $state.go("login");
                }
            } else {
                console.log("State changed");
                console.log($state.current);
            }
        }
    });
 });

app.controller("ApplicationCtrl", function ($scope, $state, AuthService) {
    $scope.currentUser = null;

    $scope.setCurrentUser = function (user) {
        $scope.currentUser = user;
    };

    $scope.isLoginState = function () {
        return $state.current.name == "login";
    };

    $scope.getUser = function () {
        AuthService.getUserProfile().then(
            function (user) {
                $scope.setCurrentUser(user);
                console.log(user);
                //TODO: Nem jó, valahogy at kene irni, hogy csak sikeres User kinyeres eseten probalhat meg a HOME-ra lepni
                console.log("setCurrentUser + Go HOME State");
                $state.go("home");
            },
            function (error) {
                console.log("LoginCtrl.getUser.error");
                console.log(error);
                $scope.setCurrentUser(null);
            }
        );
    };

    $scope.getUser();

});

app.directive("loginDialog", function (AUTH_EVENTS) {
    return {
        restrict: 'A',
        template: '<div ng-if="visible" ng-include="\'static/views/login.html\'">',
        link: function (scope) {
            var showDialog = function () {
                scope.visible = true;
            };

            scope.visible = false;
            scope.$on(AUTH_EVENTS.notAuthenticated, showDialog);
            scope.$on(AUTH_EVENTS.sessionTimeout, showDialog)
        }
    };
});

app.factory('AuthInterceptor', function ($rootScope, $q, AUTH_EVENTS) {
    return {
        responseError: function (response) {
            $rootScope.$broadcast({
                401: AUTH_EVENTS.notAuthenticated,
                403: AUTH_EVENTS.notAuthorized,
                419: AUTH_EVENTS.sessionTimeout,
                440: AUTH_EVENTS.sessionTimeout
            }[response.status], response);
            return $q.reject(response);
        }
    };
});

app.controller("galleryCtrl", function ($scope, PostService, angularGridInstance) {

    $scope.galleryPosts = {};

    var page = 0;
    var totalPages = 0;

    PostService.getPosts(page).then(
        function (posts) {
            console.log("galleryCtrl.PostService.getPosts");
            console.log(posts);
            $scope.galleryPosts = posts.data.content;
            totalPages = posts.data.totalPages;
            console.log("Posts returned to GalleryCtrl", $scope.galleryPosts);
        }, function (error) {
            console.log("Posts retrieval failed.");
            console.log(error);
        });


    $scope.loadMorePosts = function () {
        page++;
        console.log("Page: " + page);
        if (page < totalPages)
        {
            console.log("loadMorePosts.next.getPosts.invoked");
            PostService.getPosts(page).then(
                function (posts) {
                    $scope.galleryPosts = $scope.galleryPosts.concat(posts.data.content);
                    console.log("Next Posts returned to GalleryCtrl", $scope.galleryPosts);
                },
                function (error) {
                    console.log("Next Posts retrieval failed.");
                    console.log(error);
                }
            );
        }
    };

    $scope.refresh = function(){
        angularGridInstance.gallery.refresh();
    }
});

app.service("PostService", function ($http, DEF_URLS) {

    // interface
    var service = {
        getPosts: getPosts
    };
    return service;

    // implementation
    function getPosts(page) {
        var req = {
            method  : "GET",
            url     :   DEF_URLS.basic + "/api/post",
            params  : {
                pageindex   : page,
                pagesize    : 30
            }
        };
        return $http(req)
            .then(function (data) {
                console.log("Success Posts Retrieving via Service");
                console.log(data);
                return data;
            });
    }
});

app.service("EventService", function ($http, DEF_URLS) {

    // interface
    var service = {
        getEvents: getEvents
    };
    return service;

    // implementation
    function getEvents(page) {
        var req = {
            method  : "GET",
            url     :   DEF_URLS.basic + "/api/event",
            params  : {
                pageindex   : page,
                pagesize    : 30
            }
        };
        return $http(req)
            .then(function (data) {
                console.log("Success Events Retrieving via Service");
                console.log(data);
                return data;
            });
    }
});

app.controller("eventCtrl", function ($scope, EventService) {

    $scope.timelineEvents = [];

    var page = 0;
    var totalPages = 1;

    $scope.loadEvents= function () {
        console.log("Page: " + page);
        if(page < totalPages)
        {
            EventService.getEvents(page).then(
                function (result) {
                    console.log("eventCtrl.EventService.getEvents");
                    console.log(result);
                    angular.forEach(result.data.content, function (event) {
                        $scope.timelineEvents.push({
                            badgeClass: "info",
                            badgeIconClass: "glyphicon-check",
                            title: event.eventName,
                            content: event.eventDesc,
                            eventDate: event.eventStartDate
                        });
                    });

                    totalPages = result.data.totalPages;
                    console.log("Events returned to eventCtrl", $scope.timelineEvents);
                }, function (error) {
                    console.log("Events retrieval failed.");
                    console.log(error);
                }
            );
        }

        page++;
    };

    $scope.loadEvents();

});