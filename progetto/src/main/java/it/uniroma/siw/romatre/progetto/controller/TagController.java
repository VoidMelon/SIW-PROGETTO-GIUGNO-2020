package it.uniroma.siw.romatre.progetto.controller;

import java.util.ArrayList;
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
import it.uniroma.siw.romatre.progetto.controller.validator.TagValidator;
import it.uniroma.siw.romatre.progetto.model.Progetto;
import it.uniroma.siw.romatre.progetto.model.Tag;
import it.uniroma.siw.romatre.progetto.model.Task;
import it.uniroma.siw.romatre.progetto.model.Utente;
import it.uniroma.siw.romatre.progetto.service.ProgettoService;
import it.uniroma.siw.romatre.progetto.service.TagService;
import it.uniroma.siw.romatre.progetto.service.TaskService;

@Controller
public class TagController {
	
	
	@Autowired
	SessionData sessionData;
	
	
	@Autowired
	TagService tagService;
	
	
	@Autowired
	ProgettoService progettoService;
	
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	TagValidator tagValidator;
	
	
	
	@RequestMapping(value = "/tag",method = RequestMethod.GET)
	public String showTagForm(Model model) {
		Utente loggedUser = sessionData.getLoggedUser();
		model.addAttribute("tagForm", new Tag());
		model.addAttribute("user", loggedUser);
		
		return "createTag";
	}
	
	
	@RequestMapping(value = "/tag/taglist",method = RequestMethod.POST)
	public String createTag(@ModelAttribute("tagForm") Tag tag,
			                BindingResult tagBindingResult,Model model) {
		
		Utente loggedUser = sessionData.getLoggedUser();
		this.tagValidator.validate(tag, tagBindingResult);
		
		if(!tagBindingResult.hasErrors()) {
			tagService.setTask(tag);
			List<Tag> tags = new ArrayList<>();
			tags=tagService.getAllTags();
			model.addAttribute("user", loggedUser);
			model.addAttribute("tags", tags);
			
			return "tagList";
		}
		model.addAttribute("user", loggedUser);
		
		return "createTag";
		
		
	}
	
	
	@RequestMapping(value = "/tags",method = RequestMethod.GET)
	public String showTagList(Model model) {
		Utente loggedUser = sessionData.getLoggedUser();
		List<Tag> tags = new ArrayList<>();
		tags=tagService.getAllTags();
		
		model.addAttribute("tags", tags);
		model.addAttribute("user", loggedUser);
		
		return "tags";
		
	}
	
	
	@RequestMapping(value = "/tags/{tagId}",method = RequestMethod.GET)
	public String showProjectList(@PathVariable Long tagId, Model model) {
		Utente loggedUser=sessionData.getLoggedUser();
		List<Progetto> progetti = progettoService.retrieveProjectsOwnedBy(loggedUser);
		Tag tag = tagService.getTag(tagId);
		model.addAttribute("progetti", progetti);
		model.addAttribute("tag", tag);
		model.addAttribute("user", loggedUser);
		
		return "list";
		
	}
	
	@RequestMapping(value = "/tags/{tagId}/success/{projectId}",method = RequestMethod.POST)
	public String assignTagToAProject(@PathVariable Long tagId,
			                          @PathVariable Long projectId,Model model){
		Utente loggedUser=sessionData.getLoggedUser();
		Tag tag = tagService.getTag(tagId);
		Progetto project =progettoService.getProject(projectId);
		project.addTag(tag);
		progettoService.setProject(project);
		model.addAttribute("loggedUser", loggedUser);
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/tagfortask",method = RequestMethod.GET)
	public String showTagForTaskList(Model model) {
		Utente loggedUser = sessionData.getLoggedUser();
		List<Tag> tags = new ArrayList<>();
		tags=tagService.getAllTags();
		
		model.addAttribute("tags", tags);
		model.addAttribute("user", loggedUser);
		
		return "tagsForTasks";
		
	}
	
	@RequestMapping(value = "/tagfortask/{tagId}",method = RequestMethod.GET)
	public String showLists(@PathVariable Long tagId, Model model) {
		Utente loggedUser=sessionData.getLoggedUser();
		List<Progetto> progetti = progettoService.retrieveProjectsOwnedBy(loggedUser);
		Tag tag = tagService.getTag(tagId);
		model.addAttribute("progetti", progetti);
		model.addAttribute("tag", tag);
		model.addAttribute("user", loggedUser);
		
		return "lists";
		
	}
	
	@RequestMapping(value = "/tagfortask/{tagId}/{projectId}",method = RequestMethod.GET)
	public String showLists(@PathVariable Long tagId,@PathVariable Long projectId, Model model) {
		Utente loggedUser=sessionData.getLoggedUser();
		Tag tag = tagService.getTag(tagId);
		Progetto project=progettoService.getProject(projectId);
		List<Task> tasks=project.getTasks();
		model.addAttribute("tasks", tasks);
		model.addAttribute("project", project);
		model.addAttribute("tag", tag);
		model.addAttribute("user", loggedUser);
		
		return "taskList";
		
	}
	
	
	@RequestMapping(value = "/tagfortask/{tagId}/{projectId}/success/{taskId}",
			        method = RequestMethod.POST)
	public String assignTagToATask(@PathVariable Long tagId,@PathVariable Long projectId,
			                          @PathVariable Long taskId,Model model){
		Utente loggedUser=sessionData.getLoggedUser();
		Tag tag = tagService.getTag(tagId);
		Task task =taskService.getTask(taskId);
		Progetto project = progettoService.getProject(projectId);
		task.addTag(tag);
		taskService.setTask(task);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", project);
		return "redirect:/projects/" + projectId;
	}

}
