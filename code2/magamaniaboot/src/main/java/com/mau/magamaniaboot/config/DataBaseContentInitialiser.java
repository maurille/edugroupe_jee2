package com.mau.magamaniaboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mau.magamaniaboot.metier.Role;
import com.mau.magamaniaboot.metier.Utilisateur;
import com.mau.magamaniaboot.repositories.RoleRepository;
import com.mau.magamaniaboot.repositories.UtilisateurRepository;

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
			Role r_contributeur = roleRepository.save(new Role(0,"ROLE_CONTRIB"));
			Role r_visitor = roleRepository.save(new Role(0,"ROLE_VISITOR"));
			
			Utilisateur u1 = new Utilisateur(0, "admin", myPasswordEncoder.encode("admin"), true);
			u1.getRoles().add(r_admin);
			u1.getRoles().add(r_contributeur);
			utilisateurRepository.save(u1);
			
			
			Utilisateur u2 = new Utilisateur(0, "maurille", myPasswordEncoder.encode("1234"), true);
			u2.getRoles().add(r_contributeur);
			utilisateurRepository.save(u2);
			
			Utilisateur u3 = new Utilisateur(0, "visiteur", myPasswordEncoder.encode(""), true);
			u3.getRoles().add(r_visitor);
			utilisateurRepository.save(u3);
		}
		else {
			this.log.info(" Base has already users ");
		}
		
		
	}

}
