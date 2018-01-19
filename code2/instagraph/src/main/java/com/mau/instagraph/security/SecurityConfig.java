package com.mau.instagraph.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		if(myPasswordEncoder ==null)
			myPasswordEncoder = new BCryptPasswordEncoder();
		return myPasswordEncoder;
	}
	
	
	private PasswordEncoder myPasswordEncoder;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// le paramettre auth passe ici permet de configurer tout 
		//ce qui concerne l'authentifivation  des compte utilisatueur 
		// cet objet utilise le pattern BUILDER
		auth.userDetailsService(myUserDetailsService)
		.passwordEncoder(passwordEncoder()); 
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// l'objet HttpSecurity passsé en paremettre permet de configurer les droit d'acces 
				// comme plein d'autres choses ( gestion de login, session, csrf, cors)
				// tjrs le pettern Builder
				
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeRequests().antMatchers("/extendedapi/auth/**").permitAll()
				.and().httpBasic()
				.and().csrf().disable();
	}

}
