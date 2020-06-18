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
import it.uniroma.siw.romatre.progetto.controller.validator.TaskValidator;
import it.uniroma.siw.romatre.progetto.model.Progetto;
import it.uniroma.siw.romatre.progetto.model.Task;
import it.uniroma.siw.romatre.progetto.model.Utente;
import it.uniroma.siw.romatre.progetto.service.ProgettoService;
import it.uniroma.siw.romatre.progetto.service.TaskService;
import it.uniroma.siw.romatre.progetto.service.UtenteService;


@Controller
public class TaskController {
	
	
	
	
	@Autowired
	SessionData sessionData;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	UtenteService utenteService;
	
	
	@Autowired
	ProgettoService progettoService;
	
	
	@Autowired
	TaskValidator taskValidator;
	
	
	
	
	@RequestMapping(value = "/task",method = RequestMethod.GET)
	public String showProjectsList(Model model) {
		Utente loggedUser = sessionData.getLoggedUser();
		List<Progetto> ownedByUser = progettoService.retrieveProjectsOwnedBy(loggedUser);
		if(ownedByUser==null) {
			return "redirect:/home";
		}
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("progetti",ownedByUser);
		
		
		return "projectsList";
		
	}
	
	
	@RequestMapping(value = "/task/{projectId}",method = RequestMethod.GET)
	public String showNewTaskForm(Model model,@PathVariable Long projectId) {
		Utente loggedUser = sessionData.getLoggedUser();
		Progetto progetto = progettoService.getProject(projectId);
		model.addAttribute("progetto",progetto);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("taskForm", new Task());
		
		
		return "taskForm";
		
	}
	
	
	
	@RequestMapping(value = "/task/{projectId}/success", method = RequestMethod.POST)
	public String addNewTask(@ModelAttribute("taskForm") Task task,
			                 BindingResult taskBindingResult,@PathVariable Long projectId,
			                 Model model) {
		
		Utente loggedUser = sessionData.getLoggedUser();
		Progetto project=this.progettoService.getProject(projectId);
		this.taskValidator.validate(task, taskBindingResult);
		
		if(!taskBindingResult.hasErrors()) {
			project.addTask(task);
			progettoService.setProject(project);
			model.addAttribute("loggedUser", loggedUser);
			progettoService.setProject(project);
			return "redirect:/projects/" + project.getId();
			
		}
		model.addAttribute("loggedUser", loggedUser);
		return "redirect:/task" + project.getId();
		
		
		
		
		
		
	}
	
	@RequestMapping(value = "/{taskId}/delete",method = RequestMethod.POST)
	public String deleteTask(@PathVariable Long taskId,Model model) {
		Utente loggedUser=sessionData.getLoggedUser();
		Task task=taskService.getTask(taskId);
		taskService.deleteTask(task);
		model.addAttribute("user", loggedUser);
		
		
		return "home";
	}
	
	@RequestMapping(value = "/{taskId}/assign", method=RequestMethod.GET)
	public String assignMemberToProject(@PathVariable Long taskId,Model model) {
		Utente loggedUser=sessionData.getLoggedUser();
		if(progettoService.retrieveProjectsOwnedBy(loggedUser)!=null) {
			Task task = taskService.getTask(taskId);
			List<Progetto> progetti = progettoService.retrieveProjectsOwnedBy(loggedUser);
			model.addAttribute("task", task);
			model.addAttribute("projects", progetti);
			model.addAttribute("loggedUser",loggedUser);
			
			return "listaProgetti";
			
			
		}
		
		model.addAttribute("user", loggedUser);
		return "redirect:/home";
			

		
	
	
	}
	
	
	
	@RequestMapping(value = "/{taskId}/assign/member", method=RequestMethod.GET)
	public String showMembers(@PathVariable Long taskId,Model model) {
		Utente loggedUser = sessionData.getLoggedUser();
		Task task = taskService.getTask(taskId);
		
			model.addAttribute("user", loggedUser);
			model.addAttribute("task", task);
			model.addAttribute("memberFindForm", new Utente());
			model.addAttribute("progettoForm",new Progetto());
			return "listaMembri";
		
		
	}
	
	@RequestMapping(value="/{taskId}/assign/member/success",method = RequestMethod.POST)
	public String assignMemberToATask(@ModelAttribute("memberFindForm") Utente member,
			                          @ModelAttribute("task") Task task,
			                          @ModelAttribute("progettoForm") Progetto progetto,
	                                  @PathVariable Long taskId,Model model) {
		
		Utente loggedUser = sessionData.getLoggedUser();
		List<Progetto> progetti =progettoService.retrieveProjectsOwnedBy(loggedUser);
		progetto=progettoService.getProject(progetto.getNome());
		if(progetto==null) {
			return "redirect:/home";
		}
			
		System.out.println(member.getUsername());
		if(member.getUsername()!=null) {
			member=utenteService.getUtente(member.getUsername());
			task = taskService.getTask(taskId);
			task.setUtenteAssegnato(member);
			this.taskService.setTask(task);
			model.addAttribute("user", loggedUser);
			model.addAttribute("projectsList",progetti);
			
			return "redirect:/projects";
			
		}
		
		return "redirect:/home";
		
		
		
	}
	
	
	@RequestMapping(value="/{taskId}/mod",method = RequestMethod.GET)
	public String getModifyForm(@PathVariable Long taskId,Model model) {
		
		Utente loggedUser = sessionData.getLoggedUser();
		model.addAttribute("taskModifyForm", new Task());
		model.addAttribute("user", loggedUser);
		Task task = taskService.getTask(taskId);
		model.addAttribute("task", task);
		
		return "modifyTaskForm";
		
	}
	
	@RequestMapping(value="/{taskId}/mod/success",method = RequestMethod.POST)
	public String modifyTask(@PathVariable Long taskId,
			                  @ModelAttribute("taskModifyForm") Task compito,
			                  Model model) {
		
		Utente loggedUser=sessionData.getLoggedUser();
		if(compito.getNome()!=null) {
			Task task=taskService.getTask(taskId);
			task.setNome(compito.getNome());
			task.setDescrizione(compito.getDescrizione());
			taskService.setTask(task);
			model.addAttribute("user", loggedUser);
			return "redirect:/projects";
		}
		
		model.addAttribute("user", loggedUser);
		return "redirect:/home";
			
		
	}
	

}


