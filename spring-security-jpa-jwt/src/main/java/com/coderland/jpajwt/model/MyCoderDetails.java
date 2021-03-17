package com.coderland.jpajwt.model;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyCoderDetails implements UserDetails{

	/**
	 *  Added generated serialVersionUID
	 */
	private static final long serialVersionUID = -4036693382382639923L;
	private String username;
	private String password;
	private boolean enabled;
	private Set<GrantedAuthority> authorities;
	
	//Bearer_ //ROLE_
	
	public MyCoderDetails(Coder coder) {
		
		//Map<String, String> auth = ImmutableMap.of(ApiRole.ADMIN.name(), ApiPermissions.CODER_READ.getPermissions());
		this.username = coder.getUsername();
		this.password = coder.getPassword();
		this.enabled = coder.isEnabled();
		
//		this.authorities = coder.getRoles().stream().map(new Function<Role, SimpleGrantedAuthority>() {
//			@Override
//			public SimpleGrantedAuthority apply(Role t) {
//				return new SimpleGrantedAuthority(auth.get(t.getRole().substring(5)));
//			}
//		} ).collect(Collectors.toSet());
		
		this.authorities = coder.getRoles().stream().map(new Function<Role, SimpleGrantedAuthority>() {
			@Override
			public SimpleGrantedAuthority apply(Role t) {
				
				return new SimpleGrantedAuthority(t.getRole());
			}
		} ).collect(Collectors.toSet());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		log.info("getAuthorities : "+authorities);
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
