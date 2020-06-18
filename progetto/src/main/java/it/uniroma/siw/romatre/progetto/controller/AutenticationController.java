package it.uniroma.siw.romatre.progetto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma.siw.romatre.progetto.controller.session.SessionData;
import it.uniroma.siw.romatre.progetto.controller.validator.UtenteValidator;
import it.uniroma.siw.romatre.progetto.model.Utente;
import it.uniroma.siw.romatre.progetto.service.UtenteService;



@Controller
public class AutenticationController {
	
	
	@Autowired
	UtenteService utenteService;
	
	
	@Autowired
	UtenteValidator utenteValidator;
	
	@Autowired
	SessionData sessionData;
	
	
	
	
	@RequestMapping(value = {"/users/register"},method = RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute("utenteForm", new Utente());
		
		
		return "formRegister";
	}
	
	@RequestMapping(value = "/users/register",method = RequestMethod.POST)
	public String showSuccesful(@ModelAttribute("utenteForm") Utente user,
			                    BindingResult userBindingResult,
			                    Model model) {
		this.utenteValidator.validate(user, userBindingResult);
		
		if(!userBindingResult.hasErrors()) {
			this.utenteService.setUtente(user);
			return "index";
		}
		return "formRegister";
	}

}
