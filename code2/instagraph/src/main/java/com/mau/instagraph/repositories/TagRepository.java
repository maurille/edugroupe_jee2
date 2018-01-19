package com.mau.instagraph.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mau.instagraph.metier.Tag;

@RepositoryRestResource
public interface TagRepository extends PagingAndSortingRepository<Tag, Integer> {

}
