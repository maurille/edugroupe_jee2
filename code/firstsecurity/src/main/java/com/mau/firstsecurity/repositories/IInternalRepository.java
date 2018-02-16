package com.mau.firstsecurity.repositories;

import org.springframework.transaction.annotation.Transactional;

import com.mau.firstsecurity.metier.Role;
import com.mau.firstsecurity.metier.Utilisateur;

public interface IInternalRepository {

	long countUsers();

	Role createRole(String roleName);

	Utilisateur createUser(String userName, String password, Role... roles);

}