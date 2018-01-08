package com.mau.mytodoliste.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.mau.mytodoliste.metier.Tache;


public interface TacheRepository extends PagingAndSortingRepository<Tache, Integer>{
	
	List<Tache> findByLibelleContaining(String libelle);

	Page<Tache> findByLibelleContaining(String search, Pageable page);

	Page<Tache> findByPrioriteGreaterThanEqual(Integer integer, Pageable page);

	Page<Tache> findByLibelleContainingAndPrioriteGreaterThanEqual(String search, Integer integer, Pageable page);



}
