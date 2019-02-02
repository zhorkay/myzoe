package io.myzoe.config;

import io.myzoe.site.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@Order(5)
public class OAuth2AuthorizationServerContextConfiguration extends AuthorizationServerConfigurerAdapter
{
    private static final Logger log = LogManager.getLogger();

    @Autowired
    @Qualifier("authenticationManagerBean")
    public AuthenticationManager authenticationManager;

    @Inject
    UserService userService;

    @Autowired
    @Qualifier("myZoeMySqlDatasourceBean")
    public DataSource myZoeMySqlDatasource;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception
    {
        log.info("[MYZOE]:SecurityConfiguration - OAuth2AuthorizationServerConfig - AuthorizationServerSecurityConfigurer started");
        security
                //.sslOnly()
                .allowFormAuthenticationForClients()
        ;
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception
    {
        log.info("[MYZOE]:SecurityConfiguration - OAuth2AuthorizationServerConfig - ClientDetailsServiceConfigurer started");
        clients
                .jdbc(myZoeMySqlDatasource)
        ;
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception
    {
        log.info("[MYZOE]:SecurityConfiguration - Auth2AuthorizationServerConfig - AuthorizationServerEndpointsConfigurer started");
        endpoints
                .tokenServices(customTokenServices())
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userService)
        ;
    }


    @Bean
    @Primary
    public AuthorizationServerTokenServices customTokenServices()
    {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setAccessTokenValiditySeconds(600);
        defaultTokenServices.setRefreshTokenValiditySeconds(3600);

        return defaultTokenServices;
    }

    @Bean
    public JdbcTokenStore tokenStore()
    {
        return new JdbcTokenStore(myZoeMySqlDatasource);
    }

}