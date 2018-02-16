package com.mau.firstsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
@EnableWebSecurity // controle et gestion de role des urls
@EnableGlobalMethodSecurity(prePostEnabled=true) // permet de dispasher les roles(admin, user, visitor) en filtrant les methodes et non urls
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		if(myPasswordEncoder ==null)
			myPasswordEncoder = new BCryptPasswordEncoder();
		return myPasswordEncoder;
	}
	
	
	private PasswordEncoder myPasswordEncoder;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// le paramettre auth passe ici permet de configurer tout 
		//ce qui concerne l'authentifivation  des compte utilisatueur 
		// cet objet utilise le pattern BUILDER
	/*	
		// ex basique avec utilisateur stocké en dur et en memoire
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN", "USER").and()
									.withUser("Maurille").password("1234").roles("USER").and()
									.withUser("elon").password("marslove").roles("VISITOR"); */
		
		// gestion des utilisateurs via base de données
		//on veut recuperer nos user/role via jpa(entites)
		// fournir notre propre service de recup des user et role
		
		/*auth.userDetailsService(userDetailsService)
			.passwordEncoder(new PlaintextPasswordEncoder()); // ATTENTION NE JAMAIS LE FAIRE, JUSTE POUR tESTER*/
		
		
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder()); 
		
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// l'objet HttpSecurity passsé en paremettre permet de configurer les droit d'acces 
		// comme plein d'autres choses ( gestion de login, session, csrf, cors)
		// tjrs le pettern Builder

		http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN")
								.antMatchers("/client").hasAnyRole("ADMIN", "USER")
								.antMatchers("/public").authenticated()
								.antMatchers("/register").permitAll()
								.antMatchers("/").permitAll()
								.and().httpBasic()
								.and().csrf().disable();
		
		
		
		
		
	}

	
}
