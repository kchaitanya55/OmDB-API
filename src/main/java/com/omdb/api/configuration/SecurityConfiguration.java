package com.omdb.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /*Uncomment the below Annotation to view Data in H2-Console*/
    //@Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests()
//                .antMatchers("/h2-console/**").permitAll();
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
//    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}123456").roles("BASIC")
                .and()
                .withUser("admin").password("{noop}123456").roles("PREMIUM");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .formLogin()
                .and()
                .authorizeRequests()
                .antMatchers("/movies/**").hasAnyRole("BASIC","PREMIUM")
                .and()
                .csrf().disable().headers().frameOptions().disable();
    }
}