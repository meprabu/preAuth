package com.jp.preauth.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	DmhUserDetails userdetailsService;
	
	@Bean
	public FilterRegistrationBean registration(RequestHeaderAuthenticationFilter filter) {
	    FilterRegistrationBean registration = new FilterRegistrationBean(filter);
	    registration.setEnabled(false);
	    return registration;
	}
	
	@Bean
	public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider(){

	   // log.info("Configuring pre authentication provider");

	    UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = 
	            new UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>(
	            		userdetailsService);

	    PreAuthenticatedAuthenticationProvider it = new PreAuthenticatedAuthenticationProvider();
	    it.setPreAuthenticatedUserDetailsService(wrapper);

	    return it;      
	}
	
	@Bean
	public RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter() throws Exception{

	    RequestHeaderAuthenticationFilter it = new RequestHeaderAuthenticationFilter();
	    it.setPrincipalRequestHeader("iv_user");
	    it.setAuthenticationManager(authenticationManager());
	    it.setExceptionIfHeaderMissing(true);
	    return it;
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
	   // log.info("configure authentication provider");
	    auth.authenticationProvider(preAuthenticatedAuthenticationProvider());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
        .authorizeRequests()
            .antMatchers("/permitted/**", "/css/**", "/js/**", "/images/**", "/webjars/**")
                .permitAll()
            .anyRequest()
                    .authenticated()
            .and().addFilter(requestHeaderAuthenticationFilter());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		 web
	        .ignoring()
	            .antMatchers("/permitted/**", "/css/**", "/js/**", "/images/**", "/webjars/**");
	}
	
	
	
	

}
