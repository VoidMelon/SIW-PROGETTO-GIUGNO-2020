package it.uniroma.siw.romatre.progetto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma.siw.romatre.progetto.model.Task;
import it.uniroma.siw.romatre.progetto.repository.TaskRepository;

@Service
public class TaskService {
	
	
	
	@Autowired
	protected TaskRepository taskRepository;
	
	
	
	
	@Transactional
	public Task getTask(Long id){
		Optional<Task> optional = taskRepository.findById(id);
		return optional.orElse(null);
	}
	
	@Transactional
	public Task getTask(String nome){
		Optional<Task> optional = taskRepository.findByNome(nome);
		return optional.orElse(null);
	}
	
	
	@Transactional
	public void setTask(Task task) {
		this.taskRepository.save(task);
	}
	
	
	
	@Transactional
	public void deleteTask(Task task) {
		this.taskRepository.delete(task);
	}
	
	
	

}
