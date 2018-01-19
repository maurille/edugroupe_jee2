package com.mau.instagraph.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mau.instagraph.metier.Content;

@RepositoryRestResource
public interface ContentRepository extends PagingAndSortingRepository<Content, Long> {

}
