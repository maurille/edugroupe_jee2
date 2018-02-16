package com.mau.firstsecurity.web;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mau.firstsecurity.metier.Role;
import com.mau.firstsecurity.metier.Utilisateur;
import com.mau.firstsecurity.repositories.IInternalRepository;
import com.mau.firstsecurity.repositories.RoleRepository;
import com.mau.firstsecurity.repositories.UtilisateurRepository;

@Controller
@RequestMapping("/")
public class indexController {
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private IInternalRepository internalRepository;
	
	@Autowired
	private PasswordEncoder myPasswordEncoder;
	
	@RequestMapping(value="/", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String , Object> index(){
		Map<String, Object> result = new HashMap<>();
		result.put("message", "bienvenu sur la page d'accueil ");
		result.put("date", new Date() );
		return result;
		
	}
	@PreAuthorize("hasRole('ROLE_VISITOR')")
	@RequestMapping(value="/toto", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String , Object> toto(){
		Map<String, Object> result = new HashMap<>();
		result.put("message", "bienvenu sur la page toto ");
		result.put("date", new Date() );
		return result;
		
	}
	
	@RequestMapping(value="/public", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String , Object> indexpublic(){
		Map<String, Object> result = new HashMap<>();
		result.put("message", "vous etes  sur une page public");
		result.put("date", new Date() );
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		result.put("utilisateur",auth.getName());
		result.put(" ",auth.getAuthorities().stream()
												 .map(au -> au.getAuthority())
												 .collect(Collectors.toList()));
		
		return result;
		
	}
	
	@RequestMapping(value="/client", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String , Object> indexclient(Principal principal){
		Map<String, Object> result = new HashMap<>();
		result.put("message", "vous etes  sur la page du client  ");
		result.put("date", new Date() );
		
		// permet d'avoir des information sur l'utilisateur
		// par ex bonjour Maurille
		result.put("utilisateur",principal.getName());
		return result;
		
	}
	
	@RequestMapping(value="/admin", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String , Object> indexadmin(Authentication authentification){
		Map<String, Object> result = new HashMap<>();
		result.put("message", "vous etes  sur la  page de l'Administrateur ");
		result.put("date", new Date() );
		result.put("utilisateur",authentification.getName());
		result.put(" ",authentification.getAuthorities().stream()
												 .map(au -> au.getAuthority())
												 .collect(Collectors.toList()));
		return result;
		
	}
	
	// cette methode register permer de creer un user ici dans l'url car GET, mais n'attribut pas encore de role
	// pour attribuer de role voir dans roleRepository
	@RequestMapping(value="/register", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Utilisateur register(@RequestParam("username") String username,
								@RequestParam("password") String password) {
		//Utilisateur u = new Utilisateur(0, username, myPasswordEncoder.encode(password), true);
		
		// cela permet donc d'attribuer un role a ce user
		Role r = roleRepository.findByRoleName("ROLE_USER");
		Utilisateur u  = internalRepository.createUser(username, myPasswordEncoder.encode(password), r);
		//utilisateurRepository.save(u);
		return u;
		
	}

}
