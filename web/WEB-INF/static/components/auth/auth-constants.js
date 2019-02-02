var app = angular.module("auth.constants.module", []);

app.constant("AUTH_EVENTS", {
    loginSuccess: "auth-login-success",
    loginFailed: "auth-login-failed",
    logoutSuccess: "auth-logout-success",
    tokenAccessSuccess: "auth-token-access-success",
    tokenAccessFailed: "auth-token-access-failed",
    sessionTimeout: "auth-session-timeout",
    notAuthenticated: "auth-not-authenticated",
    notAuthorized: "auth-not-authorized"
});

app.constant("USER_ROLES", {
    all: "*",
    admin: "ADMIN",
    editor: "editor",
    close_family: "close_family",
    family: "family",
    close_friend: "close_friend",
    friend: "friend",
    acquaintance: "acquaintance",
    guest: "guest",
    dticket: "DELETE_TICKET"
});

app.constant("DEF_URLS", {
    basic: "http://localhost:8080"
})
;