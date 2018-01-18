package com.mau.magamaniaboot.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mau.magamaniaboot.metier.Manga;


public interface MangaRepository extends PagingAndSortingRepository<Manga, Long>{
List<Manga> findByTitreContaining(String titre);
Page<Manga> findByTitreContaining(String search, Pageable page);

Page<Manga> findByRatingGreaterThanEqual(int rating, Pageable page);
Page<Manga> findByTitreContainingAndRatingGreaterThanEqual(String search,int rating, Pageable page);
}
