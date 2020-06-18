package it.uniroma.siw.romatre.progetto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma.siw.romatre.progetto.model.Progetto;
import it.uniroma.siw.romatre.progetto.model.Utente;

public interface ProgettoRepository extends CrudRepository<Progetto, Long> {
	
	
	public List<Progetto> findByMembri(Utente membro);
	
	
	
	public List<Progetto> findByOwner(Utente owner);
	
	
	public Optional<Progetto> findByNome(String nome);
	
	


}
