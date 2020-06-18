package it.uniroma.siw.romatre.progetto.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma.siw.romatre.progetto.model.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {
	
	
	
	public Optional<Tag> findByNome(String nome);
	

}
