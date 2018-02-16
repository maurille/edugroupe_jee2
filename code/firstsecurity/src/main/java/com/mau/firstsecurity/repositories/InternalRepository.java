package com.mau.firstsecurity.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mau.firstsecurity.metier.Role;
import com.mau.firstsecurity.metier.Utilisateur;

@Service
public class InternalRepository implements IInternalRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	/* (non-Javadoc)
	 * @see com.mau.firstsecurity.repositories.IInternalRepository#countUsers()
	 */
	@Override
	@Transactional(readOnly=true)
	public long countUsers() {
		return em.createQuery("select count(u) from Utilisateur as u" , Long.class).getSingleResult();
	}
	
	/* (non-Javadoc)
	 * @see com.mau.firstsecurity.repositories.IInternalRepository#createRole(java.lang.String)
	 */
	@Override
	@Transactional
	public Role createRole(String roleName) {
		Role r = new Role(0, roleName);
		em.persist(r);
		return r;
	}
	
	/* (non-Javadoc)
	 * @see com.mau.firstsecurity.repositories.IInternalRepository#createUser(java.lang.String, java.lang.String, com.mau.firstsecurity.metier.Role)
	 */
	@Override
	@Transactional
	public Utilisateur createUser(String userName, String password, Role...roles) {
		Utilisateur u = new Utilisateur(0, userName, password, true);
		for(Role r : roles) {
			u.getRoles().add(r);
		}
		em.persist(u);
		return u;
	}

}
