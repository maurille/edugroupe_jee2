package com.mau.magamaniaboot.repositories;

import org.springframework.data.repository.CrudRepository;
import com.mau.magamaniaboot.metier.Utilisateur;



public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer> {
	
	Utilisateur findByUsername(String username);

}
