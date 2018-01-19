package com.mau.instagraph.repositories;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.mau.instagraph.metier.Image;
import com.mau.instagraph.util.FileStorageManager;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.Antialiasing;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;

public class ImageRepositoryImpl implements ImageRepositoryCustom {
	
	private static Logger log = LogManager.getLogger(ImageRepositoryImpl.class);
	
	public static final int THUMB_WIDTH = 164;
	public static final int THUMB_HEIGHT = 164;
	@Autowired
	private FileStorageManager fileStorageManager;

		// sauvegarde du fichier uniquement
	@Override
	public boolean saveImageFile(Image img, InputStream f) {
		String storageId = fileStorageManager.saveNewFile("images", f);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();// va generer un tableau de bit
		try {
			Thumbnails.of(fileStorageManager.getImageFile(storageId).get())
											.size(THUMB_WIDTH, THUMB_HEIGHT)
											.scalingMode(ScalingMode.BICUBIC)
											.antialiasing(Antialiasing.ON)
											.outputFormat("jpg")
											.toOutputStream(bos);
			
			String thumbStorageId = fileStorageManager.saveNewFile("images thumd",
					new ByteArrayInputStream(bos.toByteArray()));
			img.setThumbStorageId(thumbStorageId);
			
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			log.error("erreur a la generation miniature" + e);
		}
		
		img.setStorageId(storageId);
		return true;
	}

	@Override
	public Optional<File> getImageFile(String storageId) {
			return fileStorageManager.getImageFile(storageId);
	}

	@Override
	public boolean deleteImageFile(Image image) {
		if(image == null)
			return false;
		boolean successA = fileStorageManager.deleteImageFile(image.getStorageId());
		boolean successB = fileStorageManager.deleteImageFile(image.getThumbStorageId());
		return successA & successB;
		
	}
	
	@PersistenceContext
	private EntityManager em;
	/***********************Methode de recherche*******************************/
	// remarquer que dans cett emethode on a fait deux requettes, car quand spring Data lance 
	//ses requettes  corporées avec pagination, elle lance 2 requettes, la requette en elle même
	//: et celle de la pagination
	@Override
	@Transactional(readOnly=true)
	public Page<Image> searchWithTags(List<Integer> includedTags,
									  List<Integer> excludedTags,
									  Pageable  pageRequest) {
		
		StringBuilder sb = new StringBuilder(" from Image as img");
		if(!includedTags.isEmpty()) {
			StringBuilder sbJoin = new StringBuilder();
			StringBuilder sbWhere = new StringBuilder(" WHERE ");

			for(int position = 1; position <= includedTags.size(); position ++) {
				sbJoin.append(" , In(img.tags) ta" + position); // cela donne ",IN(img.tags° tal....."
				//sbJoin.append(" , Join img.tags as ta" + position); // c'est la meme requette de façon plus simple
				if(position > 1)
					sbWhere.append(" AND ");
				sbWhere.append(" ta" + position).append(".id=:tincid" + position);
			}
			
			sb.append(sbJoin);
			sb.append(sbWhere);
			
		}
		log.info("requette générée" + sb.toString());
		//creation de  la requettes
		TypedQuery<Image>  query = em.createQuery(" select img " + sb.toString(), Image.class);
		TypedQuery<Long>  countQuery = em.createQuery(" select count(img) " + sb.toString(), Long.class);
		// passage des parametres a la requettes
		for( int position=1 ; position <= includedTags.size(); position++) {
			query.setParameter("tincid" + position, includedTags.get(position -1));
			countQuery.setParameter("tincid" + position, includedTags.get(position -1));
		}
		// pagination du resultat ou de la requette
		query.setFirstResult(pageRequest.getOffset());// position de demarrage de la requette
		query.setMaxResults(pageRequest.getPageSize());// combien d'image renvoyés
		
		// retourne la page
		return new PageImpl<>(query.getResultList(), pageRequest, countQuery.getSingleResult());
		
	}

}
