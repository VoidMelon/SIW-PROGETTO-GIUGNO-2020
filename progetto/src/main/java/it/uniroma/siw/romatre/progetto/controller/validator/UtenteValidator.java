package it.uniroma.siw.romatre.progetto.controller.validator;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma.siw.romatre.progetto.model.Utente;
import it.uniroma.siw.romatre.progetto.service.UtenteService;



@Component
public class UtenteValidator implements Validator {
	
	
	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;
	final Integer MAX_PASSWORD_LENGTH = 20;
	final Integer MIN_PASSWORD_LENGTH = 6;
	final Integer MAX_USERNAME_LENGTH = 20;
	final Integer MIN_USERNAME_LENGTH = 4;
	
	
	@Autowired
	UtenteService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Utente user=(Utente) target;
		String nome = user.getNome().trim();
		String cognome = user.getCognome().trim();
		String username = user.getUsername().trim();
		String password = user.getPassword().trim();
		
		if(nome.isBlank())
			errors.rejectValue("nome", "required");
		
		if(nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
			errors.rejectValue("nome","size");
		
		if(cognome.isBlank())
			errors.rejectValue("cognome", "required");
		
		if(cognome.length() < MIN_NAME_LENGTH || cognome.length() > MAX_NAME_LENGTH)
			errors.rejectValue("cognome","size");
		
		if(username.isBlank())
			errors.rejectValue("username", "required");
		if(username.length() < MIN_USERNAME_LENGTH || username.length()>MAX_USERNAME_LENGTH)
			errors.rejectValue("username","size");
		if((this.userService.getUtente(username))!= null)
			errors.rejectValue("username", "duplicate");
		
		if(password.isBlank())
			errors.rejectValue("password", "required");
		if(password.length()<MIN_PASSWORD_LENGTH || password.length()>MAX_PASSWORD_LENGTH)
			errors.rejectValue("password","size");

	}

}
