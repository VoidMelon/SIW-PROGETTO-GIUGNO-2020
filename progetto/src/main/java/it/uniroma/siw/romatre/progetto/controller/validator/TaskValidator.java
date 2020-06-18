package it.uniroma.siw.romatre.progetto.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma.siw.romatre.progetto.model.Task;
import it.uniroma.siw.romatre.progetto.service.TaskService;


@Component
public class TaskValidator implements Validator {
	
	final Integer MIN_LENGTH_NAME = 2;
	final Integer MAX_LENGTH_NAME = 100;
	final Integer MAX_LENGTH_DESCRIPTION = 1000;
	
	
	
	@Autowired
	TaskService taskService;
	
	
	
	

	@Override
	public boolean supports(Class<?> clazz) {
		return Task.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Task task = (Task) target;
		
		if(task.getNome().trim().length() < MIN_LENGTH_NAME && task.getNome().length() > MAX_LENGTH_NAME)
			errors.rejectValue("nome", "length");
		if(task.getNome().trim().isBlank())
			errors.reject("nome", "required");
		if(task.getDescrizione().trim().length() > MAX_LENGTH_DESCRIPTION)
			errors.reject("descrizione", "length");
		if(taskService.getTask(task.getNome().trim())!=null)
			errors.reject("nome", "duplicate");
	}

}
