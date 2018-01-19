package com.mau.instagraph.repositories;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mau.instagraph.metier.Image;

// c'est les methodes non générées par par jpaSpring
@RepositoryRestResource
public interface ImageRepositoryCustom {
	
	// sauvegarde du fichier uniquement
	boolean saveImageFile(Image img,InputStream f);
	//
	Optional<File> getImageFile(String storageId);
	
	boolean deleteImageFile(Image image);
	
	// recherche d'image par tags
	
	Page<Image> searchWithTags(List<Integer> includedTags, List<Integer> excludedTags, Pageable  PageRequest);

}
