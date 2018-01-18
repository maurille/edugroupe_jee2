package com.mau.magamaniaboot.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mau.magamaniaboot.metier.Image;


public interface ImageRepository extends PagingAndSortingRepository<Image, Long>, ImageRepositoryCustom {
	

}
