package com.mau.magamaniaboot.metier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
public class Image {	
	
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)		private long id;
																private String fileName;
		@Column(length=100)  									private String contentType;
																private long fileSize;
																private int width;
																private int height;
		@Column(length=60)@JsonIgnore	 						private String fileHash;
		@Column(length=60)@JsonIgnore							private String storageId;
		@Column(length=60)@JsonIgnore							private String thumbStorageId;
		@OneToMany(mappedBy="image")@JsonIgnore					private Set<Manga> mangas;
	
	
	
	public Set<Manga> getManga(){
		if (mangas == null)
			mangas = new HashSet<>();
		return mangas;
	}



	public Image(long id, String fileName, String contentType, long fileSize, int width, int height, String fileHash,
			String storageId, String thumbStorageId) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.contentType = contentType;
		this.fileSize = fileSize;
		this.width = width;
		this.height = height;
		this.fileHash = fileHash;
		this.storageId = storageId;
		this.thumbStorageId = thumbStorageId;

	}
	
	

}
