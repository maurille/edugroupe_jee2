package com.mau.instagraph.metier;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Embeddable
public class CompositeOwnerKey implements Serializable{
	@ManyToOne private Image image;
	@ManyToOne private Utilisateur utilisateur;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((image == null) ? 0 : image.getId()));
		result = prime * result + ((utilisateur == null) ? 0 : utilisateur.getId());
		return result;
	}
	
	//Pour ce qui sert de clé composées, il faut redéfinir le hashcode et le equel et surtout implementer Serialization
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeOwnerKey other = (CompositeOwnerKey) obj;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (image.getId() != other.image.getId())
			return false;
		if (utilisateur == null) {
			if (other.utilisateur != null)
				return false;
		} else if (utilisateur.getId() != other.utilisateur.getId())
			return false;
		return true;
	}
	
	

		
}
