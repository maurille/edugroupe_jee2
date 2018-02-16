package com.mau.firstsecurity.config;




import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mau.firstsecurity.metier.Role;
import com.mau.firstsecurity.metier.Utilisateur;
import com.mau.firstsecurity.repositories.IInternalRepository;



@Service
public class DatabaseContentInitialiser implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private IInternalRepository internalRepository;
	
	@Autowired
	private PasswordEncoder myPasswordEncoder;
	
	private static Logger log = LogManager.getLogger(DatabaseContentInitialiser.class);
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if(internalRepository.countUsers() == 0) {
			Role r_admin = internalRepository.createRole("ROLE_ADMIN");
			Role r_user = internalRepository.createRole("ROLE_USER");
			Role r_visitor = internalRepository.createRole("ROLE_VISITOR");
			
			Utilisateur u_admin = internalRepository.createUser("admin", myPasswordEncoder.encode("admin"),
					r_admin, r_user);
			Utilisateur u_maurille = internalRepository.createUser("maurille", myPasswordEncoder.encode("1234"),
					 r_user);
			Utilisateur u_elon = internalRepository.createUser("elon", myPasswordEncoder.encode("marslove"),
					r_visitor);
			
		}
		else
			log.info("base non vide pas besoin d'initialiser");
		
	}

}
