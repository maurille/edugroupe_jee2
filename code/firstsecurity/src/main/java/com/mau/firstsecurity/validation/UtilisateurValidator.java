package com.mau.firstsecurity.validation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mau.firstsecurity.config.DatabaseContentInitialiser;
import com.mau.firstsecurity.metier.Utilisateur;


// c'est un validateur d'obet utilisateur, 
// ATTENTION le nom de la classe est importante 

// de meme le de beforsave est important
// beforeSve => avant sauvegarde en base
// utilisateur => pour les entites Utilisateur
@Component(value="beforeCreateUtilisateurValidator")
public class UtilisateurValidator implements Validator {

	private static Logger log = LogManager.getLogger(UtilisateurValidator.class);
	
	
	// cett emethode sera appele par spring pour verifier si ce validateur s'appliq bien a l'entité a valider
	@Override
	public boolean supports(Class<?> clazz) {
		// je ne valide que les entitées de la classes utilisateur
		return Utilisateur.class.equals(clazz);
	}

	// target  est l'entité a verifier et valider ( ici c'est l'utilisateur)
	// error est l'objet contenant la liste des erreurs de validation a destination du framework de validation
	@Override
	public void validate(Object target, Errors errors) {
		
		log.info(" validation objet " + target);
		Utilisateur u = (Utilisateur)target;
		String name = u.getUsername();
		if(name == null || name.length() < 3 || name.length() > 100) {
			errors.rejectValue("username", " nom utilisateur nom valide");
		}

	}

}
