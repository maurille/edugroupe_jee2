package com.mau.instagraph.repositories;

import org.springframework.data.repository.CrudRepository;


import com.mau.instagraph.metier.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	Role findByRoleName(String roleName);

}
