package com.coderland.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coderland.db.model.User;
import com.coderland.db.service.UserDetailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@SuppressWarnings("unused")
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@GetMapping("/user")
	public String home() {
		return "Alhamdulillah its user";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "Alhamdulillah its admin";
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user) {
		
		//authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		
		Authentication authenticate = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		
		authenticate.getPrincipal();
		
		log.info("principle",authenticate.getPrincipal());
		
		
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userDetailService.save(user);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
