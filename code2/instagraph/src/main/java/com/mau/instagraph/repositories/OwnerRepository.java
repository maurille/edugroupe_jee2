package com.mau.instagraph.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import com.mau.instagraph.metier.CompositeOwnerKey;
import com.mau.instagraph.metier.Owner;

@PreAuthorize("permitAll")
public interface OwnerRepository extends CrudRepository<Owner, CompositeOwnerKey> {
	List<Owner> findByClef_ImageId(Long id);
	

}
