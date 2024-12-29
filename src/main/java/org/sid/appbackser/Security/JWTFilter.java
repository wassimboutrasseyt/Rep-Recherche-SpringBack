package org.sid.appbackser.Security;

import java.io.IOException;

import org.sid.appbackser.services.implementations.AccountDetailsService;
import org.sid.appbackser.services.implementations.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JWTFilter extends OncePerRequestFilter{

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private ApplicationContext context;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader=request.getHeader("Authorization");
		String token=null;
		String email=null;
		Integer i = null; 
		
		
		if(authHeader !=null && authHeader.startsWith("Bearer ")) {
			token=authHeader.substring(7);
			email=jwtService.extractEmail(token);
		}
		
		if(email !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails=context.getBean(AccountDetailsService.class).loadUserByUsername(email);
			
			if(jwtService.validateToken(token,userDetails)) {
				UsernamePasswordAuthenticationToken authToken=
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);			}
		
		}
		filterChain.doFilter(request, response);
		
		
		
		
		
	}

}
