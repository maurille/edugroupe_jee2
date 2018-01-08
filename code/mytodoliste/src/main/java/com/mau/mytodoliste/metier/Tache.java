package com.mau.mytodoliste.metier;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Tache {
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int id;
		private String libelle;
		private String description;
		private int priorite;
		//@JsonFormat(pattern="yyyy-MM-dd")
		private LocalDate dateCreation;
		//@JsonFormat(pattern="yyyy-MM-dd")
		private LocalDate dateLimite;
		private String contexte;
		private boolean termine;
		public Tache() {}
		
		public Tache(int id, String libelle, String description, int priorite, LocalDate dateCreation, LocalDate dateLimite,
				String contexte, boolean termine) {
			super();
			this.id = id;
			this.libelle = libelle;
			this.description = description;
			this.priorite = priorite;
			this.dateCreation = dateCreation;
			this.dateLimite = dateLimite;
			this.contexte = contexte;
			this.termine = termine;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getLibelle() {
			return libelle;
		}

		public void setLibelle(String libelle) {
			this.libelle = libelle;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getPriorite() {
			return priorite;
		}

		public void setPriorite(int priorite) {
			this.priorite = priorite;
		}

		public LocalDate getDateCreation() {
			return dateCreation;
		}

		public void setDateCreation(LocalDate dateCreation) {
			this.dateCreation = dateCreation;
		}

		public LocalDate getDateLimite() {
			return dateLimite;
		}

		public void setDateLimite(LocalDate dateLimite) {
			this.dateLimite = dateLimite;
		}

		public String getContexte() {
			return contexte;
		}

		public void setContexte(String contexte) {
			this.contexte = contexte;
		}

		public boolean isTermine() {
			return termine;
		}

		public void setTermine(boolean termine) {
			this.termine = termine;
		}
		
		

	}



