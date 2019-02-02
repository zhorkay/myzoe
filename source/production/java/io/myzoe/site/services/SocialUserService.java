package io.myzoe.site.services;

import io.myzoe.site.entities.UserPrincipal;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SocialUserService extends SocialUserDetailsService
{
    @Override
    UserPrincipal loadUserByUserId(String username);
}
