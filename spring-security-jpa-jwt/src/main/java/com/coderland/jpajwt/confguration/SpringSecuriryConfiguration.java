package com.coderland.jpajwt.confguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.coderland.jpajwt.services.MyCoderDetailService;
import com.coderland.jpajwt.utility.JwtFilter;

@EnableWebSecurity
@Configuration
public class SpringSecuriryConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyCoderDetailService myCoderDetailService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myCoderDetailService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable()
		.authorizeRequests()
//		.antMatchers("/admin").hasAuthority("ADMIN")
//		.antMatchers("/user").hasAnyAuthority("ADMIN", "USER")
//		.antMatchers(HttpMethod.DELETE,"/api/coder/**").hasAuthority(ApiPermissions.CODER_WRITE.name())
//		.antMatchers(HttpMethod.PUT,"/api/coder/**").hasAuthority(ApiPermissions.CODER_WRITE.name())
//		.antMatchers(HttpMethod.POST,"/api/coder/**").hasAuthority(ApiPermissions.CODER_WRITE.name())
//		.antMatchers(HttpMethod.GET,"/api/coder/**").hasAnyRole(ApiRole.ADMIN.name(),ApiRole.USER.name())
//		.antMatchers("/login","/register","/")
		.antMatchers("/login","/register","/")
		.permitAll()
		.antMatchers("/admin").hasAuthority("ROLE_ADMIN")
		.antMatchers("/user").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
		.antMatchers(HttpMethod.DELETE,"/api/coder/**").hasAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.PUT,"/api/coder/**").hasAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.POST,"/api/coder/**").hasAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.GET,"/api/coder/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
		.anyRequest()
		.authenticated()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.httpBasic();
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
