package com.coderland.jpajwt.resource;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coderland.jpajwt.model.Coder;
import com.coderland.jpajwt.services.MyCoderDetailService;
import com.coderland.jpajwt.utility.JwtUtility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HomeResource {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private MyCoderDetailService myCoderDetailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtility jwtUtility;

	@GetMapping("/") // Open to all
	public String home() {
		return "Alhamdulillah! its Home";
	}

	@PostMapping("/login") // Open to all
	public ResponseEntity<?> login(@Valid @RequestBody Coder coder) {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(coder.getUsername(), coder.getPassword())
							);
		} catch (BadCredentialsException e) {
			e.printStackTrace();
		}
		
		final UserDetails userDetails = myCoderDetailService.loadUserByUsername(coder.getUsername());
		final String jwtToken = jwtUtility.generateToken(userDetails);
		
		HashMap<String, String> response = new HashMap<>();
		response.put("jwt", jwtToken);
		
		return ResponseEntity.ok(response);
	}

	@PostMapping("/register") // Open to all
	public ResponseEntity<?> register(@Valid @RequestBody Coder coder) {
		
		log.info("Coder : "+ coder);

		coder.setPassword(bCryptPasswordEncoder.encode(coder.getPassword()));

		Coder saved = myCoderDetailService.save(coder);

		return ResponseEntity.ok(saved);
	}

	@GetMapping("/admin")  // Accessible by Admin only
	public String admin() {
		return "Alhamdulillah! its Admin";
	}

	@GetMapping("/user") // Accessible by Admin or User
	public String user() {
		return "Alhamdulillah! its User";
	}
	
}
