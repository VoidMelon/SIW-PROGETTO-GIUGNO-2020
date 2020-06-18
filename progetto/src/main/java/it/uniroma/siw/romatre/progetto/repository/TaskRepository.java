package it.uniroma.siw.romatre.progetto.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma.siw.romatre.progetto.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
	
	
	public Optional<Task> findByNome(String nome);

}
