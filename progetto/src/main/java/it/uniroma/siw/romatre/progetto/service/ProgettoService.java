package it.uniroma.siw.romatre.progetto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma.siw.romatre.progetto.model.Progetto;
import it.uniroma.siw.romatre.progetto.model.Utente;
import it.uniroma.siw.romatre.progetto.repository.ProgettoRepository;

@Service
public class ProgettoService {
	
	
	@Autowired
	protected ProgettoRepository progettoRepository;
	
	
	
	@Transactional
	public Progetto getProject(Long id) {
		Optional<Progetto> optional = this.progettoRepository.findById(id);
		return optional.orElse(null);
	}
	
	
	@Transactional
	public Progetto getProject(String nome) {
		Optional<Progetto> optional = this.progettoRepository.findByNome(nome);
		return optional.orElse(null);
	}
	
	
	@Transactional
	public void setProject(Progetto project) {
		this.progettoRepository.save(project);
	}
	
	@Transactional
	public void deleteProject(Progetto project) {
		this.progettoRepository.delete(project);
	}
	
	
	@Transactional
	public void shareProjectwithUser(Progetto project,Utente user) {
		project.addMember(user);
		this.progettoRepository.save(project);
	}
	
	
	@Transactional
	public List<Progetto> retrieveProjectsOwnedBy(Utente user){
		List<Progetto> progetti = this.progettoRepository.findByOwner(user);
		return progetti;
		
	}
	
	@Transactional
	public List<Progetto> getProjectSharedWith(Utente membro){
		List<Progetto> progetti =this.progettoRepository.findByMembri(membro);
		return progetti;
	}

	
	

}
