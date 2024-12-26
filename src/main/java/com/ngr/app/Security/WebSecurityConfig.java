package com.ngr.app.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private UserDetailsService accountDetailsService;
	
	@Autowired 
	private JWTFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.csrf(customizer->customizer.disable()).
		authorizeHttpRequests(request -> request.requestMatchers("/auth/register").permitAll()
				.requestMatchers("/auth/logging").permitAll()
				.requestMatchers("/admin/**").hasAnyRole("ADMIN")
				.requestMatchers("/ruser/").hasAnyAuthority("ADMIN","REGISTRED_USER")
				.requestMatchers("/quest/**").hasAnyAuthority("ADMIN","REGISTRED_USER","GUEST")
				.anyRequest().authenticated()).
				httpBasic(Customizer.withDefaults()).
//				formLogin(Customizer.withDefaults()).
				sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider=new  DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
		provider.setUserDetailsService(this.accountDetailsService);
		return provider;
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user1=User.withDefaultPasswordEncoder().username("ayman").password("123").roles("admin").build();
//		return new InMemoryUserDetailsManager(user1);
//	}

}
