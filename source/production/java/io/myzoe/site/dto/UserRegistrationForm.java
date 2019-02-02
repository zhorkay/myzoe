package io.myzoe.site.dto;

import io.myzoe.site.entities.LoginProviderType;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class UserRegistrationForm implements Serializable
{
    private String username;
    private String password;
    private String passwordVerification;
    private LoginProviderType loginProvider;

    public UserRegistrationForm() {

    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordVerification() {
        return this.passwordVerification;
    }

    public void setPasswordVerification(String passwordVerification) {
        this.passwordVerification = passwordVerification;
    }

    public LoginProviderType getLoginProvider() {
        return this.loginProvider;
    }

    public void setLoginProvider(LoginProviderType loginProvider) {
        this.loginProvider = loginProvider;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("username", username)
                .append("loginProvider", loginProvider)
                .toString();

    }
}
