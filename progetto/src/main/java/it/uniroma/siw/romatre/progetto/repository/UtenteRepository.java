package it.uniroma.siw.romatre.progetto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma.siw.romatre.progetto.model.Progetto;
import it.uniroma.siw.romatre.progetto.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long> {
	
	
	public List<Utente> findByVisible(Progetto project);
	
	
	public Optional<Utente> findByUsername(String username);
	
	

}
