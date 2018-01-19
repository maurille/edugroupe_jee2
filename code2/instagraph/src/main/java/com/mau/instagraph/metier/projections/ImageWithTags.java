package com.mau.instagraph.metier.projections;

import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.data.rest.core.config.Projection;
import com.mau.instagraph.metier.Image;
import com.mau.instagraph.metier.Tag;


@Projection(name ="ImageWithTags", types = Image.class)
public interface ImageWithTags {
	
	 long getId();
	 String getNom();
	 String getDescription();
	 LocalDateTime getDateAdded();
	 Set<Tag> getTags();
	 String getFileName(); 
	 String getContentType();
	 long getFileSize(); 
	 int getWidth(); 
	 int getHeight();

}
