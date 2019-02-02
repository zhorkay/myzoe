package io.myzoe.site.controllers;

import io.myzoe.config.annotation.WebController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@WebController
public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger();

    protected static final String VIEW_NAME_LOGIN_PAGE = "/user/login";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        LOGGER.debug("Rendering login page.");
        return VIEW_NAME_LOGIN_PAGE;
    }
}
