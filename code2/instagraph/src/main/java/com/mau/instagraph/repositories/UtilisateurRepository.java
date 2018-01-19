package com.mau.instagraph.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mau.instagraph.metier.Utilisateur;


public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer> {
	
	Utilisateur findByUsername(String username);

}
