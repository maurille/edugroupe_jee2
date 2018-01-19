package com.mau.instagraph.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mau.instagraph.metier.Role;
import com.mau.instagraph.metier.Utilisateur;
import com.mau.instagraph.repositories.RoleRepository;
import com.mau.instagraph.repositories.UtilisateurRepository;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class DataBaseContentInitialiser implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private UtilisateurRepository utilisateurRepository ;
	
	@Autowired
	private RoleRepository roleRepository ;
	
	@Autowired
	private PasswordEncoder myPasswordEncoder;
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if(utilisateurRepository.count() == 0) {
			this.log.info(" base seem empty, initialising default users and roles");
			Role r_admin = roleRepository.save(new Role(0,"ROLE_ADMIN"));
			Role r_user = roleRepository.save(new Role(0,"ROLE_USER"));
			
			Utilisateur u1 = new Utilisateur(0, "admin", myPasswordEncoder.encode("admin"), true);
			u1.getRoles().add(r_admin);
			u1.getRoles().add(r_user);
			utilisateurRepository.save(u1);
			
			
			Utilisateur u2 = new Utilisateur(0, "maurille", myPasswordEncoder.encode("1234"), true);
			u2.getRoles().add(r_user);
			utilisateurRepository.save(u2);
		}
		else {
			this.log.info(" Base has already users ");
		}
		
		
	}

}
