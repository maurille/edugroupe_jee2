package com.mau.firstBoot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.mau.firstBoot.metier.SpaceShip;

// @configuration est necessaire pour spécifier que cette classe est une cless de configuration de l'application spring
@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
	
	// configuration specifique au repository spring data rest
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		
		super.configureRepositoryRestConfiguration(config);
		
		// demande a spring data rest de renvoyer la cle primaire avec
		//les autres de l'objet dans le json pour les entitées spécifiés
		config.exposeIdsFor(SpaceShip.class);
	}

}
