package io.myzoe.site.controllers;

import io.myzoe.config.annotation.WebController;
import io.myzoe.site.dto.UserRegistrationForm;
import io.myzoe.site.entities.LoginProviderType;
import io.myzoe.site.entities.UserPrincipal;
import io.myzoe.site.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.*;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.inject.Inject;

@WebController
@SessionAttributes("userForm")
public class UserController
{
    private static final Logger log = LogManager.getLogger();

    @Inject
    UserService userService;

    @Inject
    ProviderSignInUtils providerSignInUtils;

/*
    private final UserService userService;
    private final ProviderSignInUtils providerSignInUtils;

    @Inject
    public UserController(UserService userService,
                          ConnectionFactoryLocator connectionFactoryLocator,
                          UsersConnectionRepository connectionRepository)
    {
        this.userService = userService;
        this.providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
    }
*/
    //------------------------------Create a new User Form-----------------------------------------

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String createRegistrationForm(NativeWebRequest request, Model model)
    {
        log.debug("Rendering registration page.");

        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        log.debug("Connection: " + connection);

        UserRegistrationForm userRegistrationForm = new UserRegistrationForm();
        if(connection != null)
        {
            UserProfile userProfile = connection.fetchUserProfile();

            log.debug("userProfile: " + userProfile.getEmail() +
                            " - " + userProfile.getFirstName() +
                            " - " + userProfile.getLastName() +
                            " - " + userProfile.getName() +
                            " - " + userProfile.getUsername() +
                            " - " + userProfile.getId()
            );
            userRegistrationForm.setUsername(userProfile.getEmail());

            ConnectionKey providerKey = connection.getKey();
            userRegistrationForm.setLoginProvider(
                    LoginProviderType.valueOf(
                            providerKey.getProviderId().toUpperCase()
                    )
            );
            log.debug("Rendering registration form with information {}.", userRegistrationForm);
        }

        model.addAttribute("userForm", userRegistrationForm);
        return "user/registrationForm";
    }

    //------------------------------Submit a new User ---------------------------------------------

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String submitRegistrationForm(@ModelAttribute("userForm") UserRegistrationForm form,
                                         WebRequest request)
    {
        log.debug("Registering user account with information: {}.", form);

        try {
            this.userService.create(form);
        } catch (Exception e)
        {
            log.error("Error during registration", e);
        }
        //Load Registered User from Persistence Layer
        UserPrincipal user = this.userService.loadUserByUsername(form.getUsername());

        //Put registered User into the SecurityContext
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("User {} has been signed in", user);

        //Store the Social Connection in the Persistence Layer
        providerSignInUtils.doPostSignUp(user.getUsername(), request);
        log.debug("UserConnection {} has been stored", user);

        return "redirect:/";
    }
}
