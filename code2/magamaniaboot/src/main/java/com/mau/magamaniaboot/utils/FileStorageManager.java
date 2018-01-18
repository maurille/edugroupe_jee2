package com.mau.magamaniaboot.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mau.magamaniaboot.web.ImageController;


// cette annotation dit que c'est un objet injectable dans un objet beans spring
@Component
public class FileStorageManager {
	
	private static Logger log = LogManager.getLogger(ImageController.class);
	private Random rd = new Random();
	@Value("${filestorage.base-repertoire}")
	private File storageRoot; //repertoire de base  dans le quel on stockera les images

	
	
	public FileStorageManager() {
		super();
		log.info("demarage du file storage manager");
	}
	
	
	
	public  String saveNewFile(String collection, InputStream data) {
		
		if(storageRoot == null || !storageRoot.exists() || !storageRoot.isDirectory()) {
			throw new RuntimeException("starage root invalid:");
		}
		String name = collection + "#" + rd.nextLong() + LocalDateTime.now().getNano();
		String sha1Name = DigestUtils.sha1Hex(name);
		String sousRep = sha1Name.substring(0, 2);
		
		// on recupere un objet file sur le repertoire ou on stocke l'image
		File rep = Paths.get(storageRoot.getAbsolutePath(), sousRep).toFile();
		
		if(!rep.exists())
			rep.mkdirs();
		if(!rep.isDirectory())
			throw new RuntimeException("unable to craeate storage direction for" + sha1Name);
		try {
			log.info("sauvegarde de " + sha1Name);
			Files.copy(data, Paths.get(rep.getAbsolutePath(), sha1Name), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("unable to save file", e);
		}
		
		return sha1Name;	
	}
	
	
	
	public Optional<File> getImageFile(String storageId) {
		// si on a pas un storageRoot correctment accessible, on arrete les frais
		if (storageRoot == null || !storageRoot.exists() || !storageRoot.isDirectory()) {
			return Optional.empty();
		}
		// on recupere un objet File sur le répertoire ou on stocke l'image
		File rep = Paths.get(storageRoot.getAbsolutePath(), storageId.substring(0, 2)).toFile();
		// s'il n'existe pas, pas d'image
		if (!rep.exists() || !rep.isDirectory()) {
			return Optional.empty();
		}
		
		File f = Paths.get(rep.getAbsolutePath(), storageId).toFile();
		if (f != null && f.exists() && f.isFile())
			return Optional.of(f);
		else
			return Optional.empty();
	}
	
	public boolean deleteImageFile(String storageId) {
		
		// si on a pas un storageRoot correctment accessible, on arrete les frais
				if (storageRoot == null || !storageRoot.exists() || !storageRoot.isDirectory()) {
					return false;
				}
				// on recupere un objet File sur le répertoire ou on stocke l'image
				File rep = Paths.get(storageRoot.getAbsolutePath(), storageId.substring(0, 2)).toFile();
				// s'il n'existe pas, pas d'image
				if (!rep.exists() || !rep.isDirectory()) {
					return false;
				}
				
				File f = Paths.get(rep.getAbsolutePath(), storageId).toFile();
				if (f != null && f.exists() && f.isFile()){
					// effacement du fichier
					return f.delete();
				}
				
				else {
					return false;
				}
				
	}

}
