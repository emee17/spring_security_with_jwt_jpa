package com.coderland.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coderland.db.model.CustomeUserDetails;
import com.coderland.db.model.User;
import com.coderland.db.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		final User user = userRepository.findByUsername(username);
		
		if(user == null) {
			log.info("User not found");
			throw new UsernameNotFoundException("User not found");
		}
		
		UserDetails userDetails = new CustomeUserDetails(user);
		
		return userDetails;
	}

}
