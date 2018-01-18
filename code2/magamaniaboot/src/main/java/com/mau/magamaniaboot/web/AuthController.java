package com.mau.magamaniaboot.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import com.mau.magamaniaboot.metier.Utilisateur;
import com.mau.magamaniaboot.repositories.RoleRepository;
import com.mau.magamaniaboot.repositories.UtilisateurRepository;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/extendedapi/auth")
@Log4j
public class AuthController {
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder myPasswordEncoder;
	
	@CrossOrigin(origins = {"http://localhost:4200"}, methods = {RequestMethod.POST, RequestMethod.OPTIONS})
	@RequestMapping(value="/login", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Utilisateur login(@RequestBody Utilisateur utilisateur){
		
		log.info("login asked with " + utilisateur.getUsername());
		
		Utilisateur u = utilisateurRepository.findByUsername(utilisateur.getUsername());
		if(u != null) {
			return u;
		}else
			throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "login failled");
	}
	
}
