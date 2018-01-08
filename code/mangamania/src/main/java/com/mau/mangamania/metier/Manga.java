package com.mau.mangamania.metier;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Manga {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String titre;
	private String auteur;
	//@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dateParution;
	private String genre;
	private int rating;
	
	public Manga() {};
	public Manga(long id, String titre, String auteur, LocalDate dateParution, String genre, int rating) {
		super();
		this.id = id;
		this.titre = titre;
		this.auteur = auteur;
		this.dateParution = dateParution;
		this.genre = genre;
		this.rating = rating;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getAuteur() {
		return auteur;
	}
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	public LocalDate getDateParution() {
		return dateParution;
	}
	public void setDateParution(LocalDate dateParution) {
		this.dateParution = dateParution;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	

}
