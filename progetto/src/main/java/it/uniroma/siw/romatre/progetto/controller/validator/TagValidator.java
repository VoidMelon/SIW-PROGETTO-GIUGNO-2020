package it.uniroma.siw.romatre.progetto.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma.siw.romatre.progetto.model.Tag;
import it.uniroma.siw.romatre.progetto.service.TagService;


@Component
public class TagValidator implements Validator {
	
	
	final Integer MIN_LENGTH_NAME = 2;
	final Integer MAX_LENGTH_NAME = 100;
	final Integer MAX_LENGTH_DESCRIPTION = 1000;
	final Integer MIN_LENGTH_COLOR = 3;
	final Integer MAX_LENGTH_COLOR = 20;
	
	@Autowired
	TagService tagService;
	

	@Override
	public boolean supports(Class<?> clazz) {
		return Tag.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Tag tag = (Tag) target;
		if(tag.getNome().trim().length()>MAX_LENGTH_NAME && tag.getNome().trim().length()<MIN_LENGTH_NAME)
			errors.rejectValue("name", "size");
		if(tagService.getTag(tag.getNome().trim())!=null)
			errors.rejectValue("name", "duplicate");
		if(tag.getColore().trim().length()>MAX_LENGTH_COLOR && tag.getColore().trim().length()<MIN_LENGTH_COLOR)
			errors.rejectValue("color", "size");
		if(tag.getDescrizione().trim().length()>MAX_LENGTH_DESCRIPTION)
			errors.rejectValue("description", "size");
		if(tag.getColore().isBlank())
			errors.rejectValue("color", "blank");
		if(tag.getNome().isBlank())
			errors.rejectValue("name", "blank");
			

	}

}
