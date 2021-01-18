package com.dte.security.securityservice.configurations;

import com.dte.security.securityservice.filters.JwtRequestFilter;
import com.dte.security.securityservice.models.enums.Endpoints;
import com.dte.security.securityservice.services.user.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            Endpoints.HEALTH_PATH,
            Endpoints.ADVANCED_HEALTH_PATH,
            Endpoints.AUTHENTICATE_USER_PATH,
            Endpoints.CREATE_USER_PATH,
    };

    private static final String[] VERIFY_ENDPOINTS = {
            Endpoints.VERIFY_USER_PATH,
            Endpoints.VERIFY_PRIVILEGE_PATH
    };

    @Autowired
    private UserDataService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll() // allow access to all whitelisted pages to anyone
                .and()
                .authorizeRequests().antMatchers(VERIFY_ENDPOINTS).authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // disables session creation on Spring Security

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
