package it.uniroma.siw.romatre.progetto.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma.siw.romatre.progetto.model.Progetto;
import it.uniroma.siw.romatre.progetto.service.ProgettoService;


@Component
public class ProgettoValidator implements Validator {
	
	
	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;
	final Integer MAX_DESCRIPTION_LENGTH = 1000;
	
	
	
	@Autowired
	ProgettoService progettoService;


	@Override
	public void validate(Object target, Errors errors) {
		
		Progetto project = (Progetto) target;
		if(project.getNome().trim().isBlank())
			errors.rejectValue("nome", "required");
		if(project.getNome().trim().length()<MIN_NAME_LENGTH || project.getNome().length()>MAX_NAME_LENGTH)
			errors.rejectValue("nome", "size");
		if(project.getDescrizione().trim().length()>MAX_DESCRIPTION_LENGTH)
			errors.rejectValue("descrizione", "size");
		if(project.getDescrizione().trim().isBlank())
			errors.rejectValue("descrizione", "required");
		if(progettoService.getProject(project.getNome().trim())!=null)
			errors.reject("nome", "duplicate");
		
			
		
	}
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Progetto.class.equals(clazz);
	}

}
