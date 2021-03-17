package com.coderland.jwt.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coderland.jwt.model.JwtRequest;
import com.coderland.jwt.model.JwtResponse;
import com.coderland.jwt.service.UserService;
import com.coderland.jwt.utility.JWTUtility;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
//@CrossOrigin("*")
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@GetMapping("/")
	public String Home() {
		return "Alhamdulillah it works";
	}
	
	@PostMapping("/authenticate")
	public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
		
		try {
			Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword(),
                            new ArrayList<>()
                    )
            ); 
			System.out.println(authenticate.isAuthenticated());
			
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS");
		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
		
		final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
		
		String generateToken = jwtUtility.generateToken(userDetails);
		
		return new JwtResponse(generateToken);
	}
}
