package com.mau.allocine.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mau.allocine.metier.Film;

@Service
public class FilmDepot implements IFilm
{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public List<Film> findAll(){
		return em.createQuery("from Film", Film.class).getResultList();
	}

	@Override
	@Transactional
	public Film save(Film f) {
		if (f.getId() == 0)
			em.persist(f);
		else
			f = em.merge(f);
		return f;
	}

}
