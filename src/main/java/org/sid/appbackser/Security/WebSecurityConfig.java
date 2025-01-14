package org.sid.appbackser.Security;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
		cors(cors->{}).
		authorizeHttpRequests(request -> request.requestMatchers("/auth/**").permitAll()
				.requestMatchers("/ws/chat/**").hasAnyAuthority("ADMIN","REGISTRED_USER")  // Allow WebSocket URL
				// .requestMatchers("/auth/login").permitAll()
				.requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
				.requestMatchers("/registred-user/**").hasAnyAuthority("ADMIN","REGISTRED_USER")
				.requestMatchers("/guest/**").hasAnyAuthority("ADMIN","REGISTRED_USER","GUEST")
				.anyRequest().authenticated()).
				httpBasic(Customizer.withDefaults()).
				// formLogin(Customizer.withDefaults()).
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
}
