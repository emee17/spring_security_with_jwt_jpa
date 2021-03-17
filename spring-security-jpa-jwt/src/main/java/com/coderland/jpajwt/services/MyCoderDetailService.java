package com.coderland.jpajwt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coderland.jpajwt.exception.CoderNotFoundException;
import com.coderland.jpajwt.model.Coder;
import com.coderland.jpajwt.model.MyCoderDetails;
import com.coderland.jpajwt.repos.CoderRepository;

@Service
public class MyCoderDetailService implements UserDetailsService{

	@Autowired
	private CoderRepository coderRepository;
	
	public Coder save(Coder coder){
		return coderRepository.save(coder);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Coder> optionalUser = coderRepository.findByUsername(username);
		
		optionalUser.orElseThrow(()-> new UsernameNotFoundException("User not found with username "+ username));
		
		return optionalUser.map(MyCoderDetails::new).get();// <- this line = optionalUser.map((coder)-> new MyCoderDetails(coder)).get();
	}
	
	public Coder getyId(Long id) throws CoderNotFoundException{
		
		return coderRepository.findById(id).orElseThrow(()-> new CoderNotFoundException("Coder not found with id "+ id));
	}
	
	public List<Coder> getAll(){
		
		return coderRepository.findAll();
	}
}
