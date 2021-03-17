package com.coderland.db.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.coderland.db.service.UserDetailService;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Configuration
@Slf4j
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Bean
	public AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); 
		provider.setUserDetailsService(userDetailService);
		provider.setPasswordEncoder(bCryptPasswordEncoder);
		log.info("inside getAuthenticationProvider");
		return provider;
	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
//	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		csrf()
		.disable()
		.authorizeRequests()
		.antMatchers("/login","/register")
		.permitAll()
		.antMatchers("/admin")
		.hasAuthority("ADMIN")
		.antMatchers("/user")
		.hasAuthority("USER")
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
	}
	
	
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
}
