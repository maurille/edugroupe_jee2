package com.mau.magamaniaboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.mau.magamaniaboot.metier.Image;
import com.mau.magamaniaboot.metier.Manga;



// @configuration est necessaire pour spécifier que cette classe est une cless de configuration de l'application spring
@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
	
	// configuration specifique au repository spring data rest
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		
		super.configureRepositoryRestConfiguration(config);
		
		// demander a spring data rest de renvoyer la cle primaire avec
		//les autres données( variables)  de l'objet dans le json pour les entitées spécifiés
		config.exposeIdsFor(Manga.class, Image.class);
	}

}
