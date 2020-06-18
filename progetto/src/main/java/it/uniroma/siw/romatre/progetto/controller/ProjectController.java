package it.uniroma.siw.romatre.progetto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma.siw.romatre.progetto.controller.session.SessionData;
import it.uniroma.siw.romatre.progetto.controller.validator.ProgettoValidator;
import it.uniroma.siw.romatre.progetto.model.Progetto;
import it.uniroma.siw.romatre.progetto.model.Utente;
import it.uniroma.siw.romatre.progetto.service.ProgettoService;
import it.uniroma.siw.romatre.progetto.service.UtenteService;

@Controller
public class ProjectController {
	
	
	
	@Autowired
	SessionData sessionData;
	
	
	@Autowired
	ProgettoService progettoService;
	
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	ProgettoValidator progettoValidator;
	
	
	@RequestMapping(value= "/projects", method = RequestMethod.GET)
	public String showMyOwnedProjects (Model model) {
		
		Utente loggedUser = this.sessionData.getLoggedUser();
		List<Progetto> progetti = this.progettoService.retrieveProjectsOwnedBy(loggedUser);
		model.addAttribute("user", loggedUser);
		model.addAttribute("projectsList", progetti);
		
		
		
		return "myOwnedProjects";
	}
	
	
	
	@RequestMapping(value= "/projects/{projectId}",method = RequestMethod.GET)
	public String showProjectInf(Model model,@PathVariable Long projectId) {
		
		
		
		Utente loggedUser=sessionData.getLoggedUser();
		Progetto project = this.progettoService.getProject(projectId);
		
		
		List<Utente> members = utenteService.getMembers(project);
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", project);
		model.addAttribute("members", members);
		
		
		return "project";
		
	}
	
	
	@RequestMapping(value= "/projects/add", method = RequestMethod.GET)
	public String showProjectForm(Model model) {
		Utente loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectForm", new Progetto());
		
		
		return "addProject";
		
		
	}
	
	@RequestMapping(value= "/projects/add", method = RequestMethod.POST)
	public String createProject(@ModelAttribute("projectForm") Progetto project,
			                     BindingResult projectBindingResult,
			                     Model model) {
		Utente loggedUser = sessionData.getLoggedUser();
		progettoValidator.validate(project, projectBindingResult);
		
		if(!projectBindingResult.hasErrors()) {
			project.setOwner(loggedUser);
			this.progettoService.setProject(project);
			return "redirect:/projects/" + project.getId();
		}
		
		model.addAttribute("loggedUser", loggedUser);
		return "addProject";
		
	}
	
	
	
	@RequestMapping(value= "/projects/{projectId}/modify", method = RequestMethod.GET)
	public String showModidyForm(Model model, @PathVariable Long projectId) {
		
		Utente loggedUser=sessionData.getLoggedUser();
		Progetto project1=progettoService.getProject(projectId);
		
		Progetto projectForm = new Progetto();
		
		System.out.println("showModifyForm");
		System.out.println(project1.getId());
		System.out.println(project1.toString());
		System.out.println();
		model.addAttribute("user",loggedUser);
		model.addAttribute("project1", project1);
		model.addAttribute("projectForm", projectForm);
		
		
		return "projectModifyForm";
		
	}
	
	@RequestMapping(value="/projects/{projectId}/modify/success",method = RequestMethod.POST)
		public String successModidy( @ModelAttribute("projectForm") Progetto project,
				                        BindingResult projectBindingResult,
                                     @PathVariable Long projectId,Model model) {
		System.out.println("successModify");
		System.out.println(project.toString());
		Utente loggedUser = sessionData.getLoggedUser();
		Progetto progetto = progettoService.getProject(projectId);
	
		/*progetto.setDescrizione(project.getDescrizione());
		progetto.setNome(project.getNome());*/
		
		
		System.out.println(progetto.toString());
		progettoValidator.validate(project, projectBindingResult);
		
		System.out.println(projectBindingResult.toString());
		
		if(!projectBindingResult.hasErrors()) {
			progetto.setDescrizione(project.getDescrizione());
			progetto.setNome(project.getNome());
			progettoService.setProject(progetto);
			model.addAttribute("project", progetto);
			model.addAttribute("loggedUser", loggedUser);
			System.out.println(progetto.getId());
			return "redirect:/projects/" + progetto.getId();
		}
		
		model.addAttribute("loggedUser", loggedUser);
			
		
		return "redirect:/projects/" + progetto.getId() + "/modify";
	}
	
	@RequestMapping(value = "/projects/{projectId}/delete",method = RequestMethod.POST)
	public String deleteProject(@PathVariable Long projectId,Model model) {
		Utente loggedUser=sessionData.getLoggedUser();
		Progetto progetto=progettoService.getProject(projectId);
		progettoService.deleteProject(progetto);
		model.addAttribute("user", loggedUser);
		
		
		return "home";
	}
	
	@RequestMapping(value = "/projects/{projectId}/form",method = RequestMethod.GET)
	public String showMemberForm(@PathVariable Long projectId,Model model) {
		Utente loggedUser = sessionData.getLoggedUser();
		Progetto progetto = progettoService.getProject(projectId);
		model.addAttribute("memberForm", new Utente());
		model.addAttribute("user", loggedUser);
		model.addAttribute("project", progetto);
		System.out.println("sono qui");
		
		
		
		return "memberForm";
	}
	
	@RequestMapping(value = "/projects/{projectId}/form/success",method = RequestMethod.POST)
	public String findMember(@ModelAttribute("memberForm")Utente user,
			                  @PathVariable Long projectId,Model model) {
		System.out.println("sono qui");
		Utente loggedUser = sessionData.getLoggedUser();
		if(user.getUsername().isBlank()||user.getUsername()==null) {
			model.addAttribute("user",loggedUser);
			return "redirect:/projects/{projectId}/member";
		}
		Utente member = utenteService.getUtente(user.getUsername());
		Progetto progetto=progettoService.getProject(projectId);
		progetto.addMember(member);
		progettoService.setProject(progetto);
		model.addAttribute("user", loggedUser);
		
		
		
		return "home";
	}
	
	@RequestMapping(value = "/projects/sharedProjects",method = RequestMethod.GET)
	public String showProjectsSharedWithMe(Model model) {
		Utente loggedUser=sessionData.getLoggedUser();
		List<Progetto> progetti =progettoService.getProjectSharedWith(loggedUser);
		model.addAttribute("visibili", progetti);
		model.addAttribute("loggedUser", loggedUser);
		
		return "showVisibleProjects";
	}

}
