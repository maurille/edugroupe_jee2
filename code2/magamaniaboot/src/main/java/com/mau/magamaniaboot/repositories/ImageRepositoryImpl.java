package com.mau.magamaniaboot.repositories;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.mau.magamaniaboot.metier.Image;
import com.mau.magamaniaboot.utils.FileStorageManager;
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
	public boolean saveImageFile(Image img, InputStream f) 
	{
		
		String storageId = fileStorageManager.saveNewFile("images", f);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();// va generer un tableau de bit
		try {
			Thumbnails.of(fileStorageManager.getImageFile(storageId).get())
											.size(THUMB_WIDTH, THUMB_HEIGHT)
											.scalingMode(ScalingMode.BICUBIC)
											.antialiasing(Antialiasing.ON)
											.outputFormat("jpg")
											.toOutputStream(bos);
			
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
		boolean success = fileStorageManager.deleteImageFile(image.getStorageId());

		return success;
		
	}
	

}
