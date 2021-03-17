package com.coderland.db.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.coderland.db.service.UserDetailService;

/**
 * 
 * @author Namaz Shaikh
 * This Class will only be used when we want Custom Authentication Provider
 * then we need to pass this CustomAuthenticationProvider class object to overridden configure(AuthenticationManagerBuilder  auth)
 * like auth.authenticationProvider(new CustomAuthenticationProvider());
 *
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
	@Autowired
	private UserDetailService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<UserDetails> authenticatedUser = Optional.ofNullable(userDetailService.loadUserByUsername(name));

        if(!authenticatedUser.isPresent()){
            throw new BadCredentialsException("Some Text");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(authenticatedUser.get().getAuthorities());
        Authentication auth = new UsernamePasswordAuthenticationToken(name, password, authorities);
        return auth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}