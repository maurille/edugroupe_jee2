package com.mau.instagraph.metier;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor 
@Entity @Inheritance(strategy=InheritanceType.JOINED)
public class Content {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 		private long id;
																private  String nom;
																private String description;
																private LocalDateTime dateAdded;
	@ManyToMany @JsonIgnore													private Set<Tag> tags;
	
	public Set<Tag> getTags(){
		if (tags == null)
			tags = new HashSet<>();
		return tags;
	}

	public Content(long id, String nom, String description, LocalDateTime dateAdded) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.dateAdded = dateAdded;
	}
	

	

}
