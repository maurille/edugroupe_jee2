package com.mau.instagraph.metier;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
public class Image extends Content {
							private String fileName;
	@Column(length=100)  	private String contentType;
							private long fileSize;
							private int width;
							private int height;
	@Column(length=60)@JsonIgnore 		private String fileHash;
	@Column(length=60)@JsonIgnore		private String storageId;
	@Column(length=60)@JsonIgnore		private String thumbStorageId;
	


	public Image(long id,
				String nom, 
				String description, 
				LocalDateTime dateAdded, 
				String fileName, 
				String contentType,
				long fileSize, 
				int width, 
				int height, 
				String fileHash, 
				String storageId,
				String thumbStorageId) {
		super(id, nom, description, dateAdded);
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
