package io.myzoe.site.services;

import io.myzoe.site.dto.UserRegistrationForm;
import io.myzoe.site.entities.UserPrincipal;
import io.myzoe.site.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class UserServiceImpl implements UserService
{
    private static final Logger log = LogManager.getLogger();

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserPrincipal loadUserByUsername(String username)
    {
        UserPrincipal principal = userRepository.getByUsername(username);
        // make sure the authorities and password are loaded
        principal.getAuthorities().size();
        principal.getPassword();
        return principal;
    }

    @Override
    @Transactional
    public void saveUser(UserPrincipal principal, String newPassword)
    {
        if(newPassword != null && newPassword.length() > 0)
        {
            principal.setHashedPassword(
                    passwordEncoder.encode(newPassword).getBytes()
            );
        }

        this.userRepository.save(principal);
    }

    @Override
    @Transactional
    public void create(UserRegistrationForm userForm)
    {
        if (userForm.getPassword() != null && userForm.getPassword().length() > 0)
        {
            UserPrincipal newUser = new UserPrincipal();
            newUser.setUsername(userForm.getUsername());
            newUser.setHashedPassword(
                    passwordEncoder.encode(userForm.getPassword()).getBytes());
            newUser.setAccountNonExpired(true);
            newUser.setAccountNonLocked(true);
            newUser.setCredentialsNonExpired(true);
            newUser.setEnabled(true);
            newUser.setLoginProvider(userForm.getLoginProvider());

            this.userRepository.save(newUser);
        }
    }
}
