package io.myzoe.site.common.controller;

import io.myzoe.config.annotation.RestEndpoint;
import io.myzoe.site.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

@RestEndpoint
public class UserProfileController
{
    private static final Logger log = LogManager.getLogger();

    @Inject
    UserService userService;

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public ResponseEntity<Authentication> getCurrentUser()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null)
        {
            return new ResponseEntity<Authentication>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Authentication>(auth, HttpStatus.OK);
    }
}
