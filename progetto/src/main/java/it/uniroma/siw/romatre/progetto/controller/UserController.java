package it.uniroma.siw.romatre.progetto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma.siw.romatre.progetto.controller.session.SessionData;
import it.uniroma.siw.romatre.progetto.model.Utente;
import it.uniroma.siw.romatre.progetto.repository.UtenteRepository;
import it.uniroma.siw.romatre.progetto.service.UtenteService;


@Controller
public class UserController {
	
	
	
	@Autowired
	UtenteRepository utenteRepository;
	
	
	@Autowired
	SessionData sessionData;
	
	@Autowired
	UtenteService utenteService;
	
	
	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public String showHome(Model model) {
		Utente user = this.sessionData.getLoggedUser();
		model.addAttribute("user", user);
		return "home";
	}
	
    @RequestMapping(value = { "/users/me" }, method = RequestMethod.GET)
    public String me(Model model) {
        Utente loggedUser = sessionData.getLoggedUser();
        System.out.println(loggedUser.getPassword());
        model.addAttribute("user", loggedUser);

        return "userProfile";
    }
	
	
	@RequestMapping(value = "/admin",method = RequestMethod.GET)
	public String showAdmin(Model model) {
		Utente loggedUser = sessionData.getLoggedUser();
		model.addAttribute("user",loggedUser);
		return "admin";
	}
	
	
	@RequestMapping(value = "/admin/users",method = RequestMethod.GET)
	public String showUsersList(Model model) {
		Utente loggedUser = sessionData.getLoggedUser();
		List<Utente> utenti=this.utenteService.getAllUsers();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("users", utenti);
		
		
		return "allUsers";
	}
	
	
	@RequestMapping(value = "/admin/users/{username}/delete", method = RequestMethod.POST)
	public String removeUser(Model model, @PathVariable String username) {
		this.utenteService.deleteUser(username);
		return "redirect:/admin/users";
	}
	
	
	
	@RequestMapping(value= "/users/modify", method = RequestMethod.GET)
		public String showUserModifyForm(Model model) {
			Utente loggedUser=sessionData.getLoggedUser();
			
			model.addAttribute("user", loggedUser);
			model.addAttribute("modifyUserForm",new Utente());
			
			return "userModifyForm";
	}
	
	
	@RequestMapping(value= "/users/modify/success", method = RequestMethod.POST)
	public String successModifyForm(@ModelAttribute("modifyUserForm") Utente utenteModificato,
			                         Model model) {
		Utente loggedUser=sessionData.getLoggedUser();
		
		
		loggedUser.setCognome(utenteModificato.getCognome());
		loggedUser.setNome(utenteModificato.getNome());
		utenteService.saveUser(loggedUser);
		model.addAttribute("user", loggedUser);
		
		return "userProfile";
	}

}
