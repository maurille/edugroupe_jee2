package com.mau.firstsecurity.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.validation.Validator;

@Configuration
public class ValidatorEventRegister implements InitializingBean {
	
	@Autowired
	private ValidatingRepositoryEventListener validatingRepositoryEventListener;
	
	// la liste de ts les validateurs détecté par spring
	@Autowired
	private Map<String, Validator> validators;

	@Override
	public void afterPropertiesSet() throws Exception {
		List<String> events = Arrays.asList("beforeCreate");
		for(Entry<String, Validator> entry : validators.entrySet()) {
			events.stream()
				  .filter(p -> entry.getKey().startsWith(p))
				  .findFirst().ifPresent(
					v -> validatingRepositoryEventListener.addValidator(v , entry.getValue())
					);
		}
	}

}
