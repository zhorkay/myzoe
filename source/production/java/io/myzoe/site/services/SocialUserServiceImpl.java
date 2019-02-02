package io.myzoe.site.services;

import io.myzoe.site.entities.UserPrincipal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

public class SocialUserServiceImpl implements SocialUserService
{
    private static final Logger log = LogManager.getLogger();

    @Inject
    UserService userService;

    @Override
    @Transactional
    public UserPrincipal loadUserByUserId(String username)
    {
        log.debug("[MYZOE] - loadUserByUserId: " + username);
        UserPrincipal principal = userService.loadUserByUsername(username);

        principal.getAuthorities().size();
        principal.getPassword();
        return principal;
    }
}
