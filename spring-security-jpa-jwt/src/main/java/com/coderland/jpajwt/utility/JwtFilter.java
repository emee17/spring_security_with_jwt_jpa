package com.coderland.jpajwt.utility;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.coderland.jpajwt.services.MyCoderDetailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtility jwtUtility;

	@Autowired
	private MyCoderDetailService myCoderDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorization = request.getHeader("Authorization");

		String username = null;
		String jwt = null;
		log.info("authorization : "+ authorization);
		if (authorization != null && authorization.startsWith("Bearer ")) {
			jwt = authorization.substring(7);
			username = jwtUtility.getUsernameFromToken(jwt);
		}

		if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
			final UserDetails userDetails = myCoderDetailService.loadUserByUsername(username);
			
			log.info("Authorities are "+ userDetails.getAuthorities());

			if (jwtUtility.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken unauthtoken = new UsernamePasswordAuthenticationToken(
						userDetails.getUsername(), null, userDetails.getAuthorities());
				unauthtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(unauthtoken);
			}
		}
		filterChain.doFilter(request, response);

	}

}
