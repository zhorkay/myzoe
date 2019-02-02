package io.myzoe.config;

import io.myzoe.site.services.SocialUserServiceImpl;
import io.myzoe.site.services.UserService;
import io.myzoe.site.services.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true, order = 0, mode = AdviceMode.PROXY,
        proxyTargetClass = false
)
public class SecurityContextConfiguration extends WebSecurityConfigurerAdapter
{
    private static final Logger log = LogManager.getLogger();

    @Bean
    public UserService userService()
    {
        return new UserServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Bean
    public SessionRegistry sessionRegistryImpl()
    {
        log.info("[MYZOE]:SecurityConfiguration - sessionRegistryImpl started");
        return new SessionRegistryImpl();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        log.info("[MYZOE]:SecurityConfiguration - authenticationManagerBean started");
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder)
            throws Exception
    {
        log.info("[MYZOE]:SecurityConfiguration - AuthenticationManagerBuilder started");
        builder
                .userDetailsService(this.userService())
                        .passwordEncoder(passwordEncoder())
                .and()
                .eraseCredentials(true)
        ;
    }

    @Override
    public void configure(WebSecurity security)
    {
        log.info("[MYZOE]:SecurityConfiguration - WebSecurity started");
        security.ignoring().antMatchers("/resource/**", "/favicon.ico", "/static/**");
        security.ignoring().antMatchers("services/Rest/posts"); //TODO: Veszelyes Kikapcsolni Security Test
    }

    @Override
    public void configure(HttpSecurity security) throws Exception
    {
        log.info("[MYZOE]:SecurityConfiguration - HttpSecurity started");
        security
                .formLogin()
                    .loginPage("/login").loginProcessingUrl("/login/submit").failureUrl("/login?loginFailed")
                    .defaultSuccessUrl("/home")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .and()
                .logout()
                    .logoutUrl("/logout").logoutSuccessUrl("/login?loggedOut")
                    .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                    .and()
                .authorizeRequests()
                //Anyone can access the urls
                    .antMatchers(
                            "/auth/**",
                            "/connect/**",
                            "/login",
                            "/signup/**",
                            "/signin/**",
                            "/user/**"
                    ).permitAll()
                .and()
                    .apply(new SpringSocialConfigurer())
                              /*.and().requiresChannel()
                    .anyRequest().requiresSecure()*/
                            .and().sessionManagement()
                            .sessionFixation().changeSessionId()
                            .maximumSessions(1).maxSessionsPreventsLogin(true)
                            .sessionRegistry(this.sessionRegistryImpl())
                            .and().and()
                            .csrf().disable()//TODO: Bekapcsolni CSRF
                /*
                .csrf().csrfTokenRepository(csrfTokenRepository()).and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)*/

        ;
    }

    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
                        .getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrf.getToken();
                    if (cookie == null || token != null
                            && !token.equals(cookie.getValue())) {
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Bean
    public SocialUserDetailsService socialUserDetailsService()
    {
        return new SocialUserServiceImpl();
    }
}
