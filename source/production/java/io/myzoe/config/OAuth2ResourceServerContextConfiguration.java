package io.myzoe.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableResourceServer
@Order(6)
public class OAuth2ResourceServerContextConfiguration extends ResourceServerConfigurerAdapter
{

    private static final Logger log = LogManager.getLogger();
    private static final String OA_RESOURCE_ID = "rest_api";

    @Autowired
    @Qualifier("myZoeMySqlDatasourceBean")
    public DataSource myZoeMySqlDatasource;

    @Bean
    @Primary
    public ResourceServerTokenServices customTokenServices()
    {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setRefreshTokenValiditySeconds(3600);
        defaultTokenServices.setAccessTokenValiditySeconds(600);

        return defaultTokenServices;
    }

    @Bean
    public JdbcTokenStore tokenStore()
    {
        return new JdbcTokenStore(myZoeMySqlDatasource);
    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) throws Exception
    {
        log.info("[MYZOE]:SecurityConfiguration - OAuth2ResourceServerConfig - ResourceServerSecurityConfigurer started");
        resources
                .tokenServices(customTokenServices())
                .resourceId(OA_RESOURCE_ID)
                .stateless(false);
    }

    SpringSocialConfigurer socialConfigurer = new SpringSocialConfigurer()
            .connectionAddedRedirectUrl("/home");

    @Override
    public void configure(final HttpSecurity http) throws Exception
    {
        log.info("[MYZOE]:SecurityConfiguration - OAuth2ResourceServerConfig - HttpSecurity started");
        http
                .apply(socialConfigurer)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers()
                .antMatchers("/api/**")
                .and()
                .authorizeRequests().anyRequest().authenticated()
        ///.and()
        ///    .requiresChannel()
        ///        .anyRequest().requiresSecure()
        ;
    }

}
