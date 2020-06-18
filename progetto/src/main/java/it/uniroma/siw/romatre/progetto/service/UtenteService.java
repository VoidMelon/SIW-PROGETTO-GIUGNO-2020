package it.uniroma.siw.romatre.progetto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma.siw.romatre.progetto.model.Progetto;
import it.uniroma.siw.romatre.progetto.model.Utente;
import it.uniroma.siw.romatre.progetto.repository.UtenteRepository;

@Service
public class UtenteService {
	
	
	@Autowired
	protected UtenteRepository utenteRepository;
	
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	
	
	@Transactional
	public Utente getUtente(Long id) {
		Optional<Utente> optional = this.utenteRepository.findById(id);
		return optional.orElse(null);
	}
	
	
	@Transactional
	public void setUtente(Utente user) {
		user.setRole(Utente.DEFAULT_ROLE);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		this.utenteRepository.save(user);
	}
	
	
	
	@Transactional
	public Utente getUtente(String username) {
		Optional<Utente> optional = this.utenteRepository.findByUsername(username);
		return optional.orElse(null);
	}
	
	
	@Transactional
	public void deleteUser(Utente user) {
		this.utenteRepository.delete(user);
	}
	
	@Transactional
	public void deleteUser(String username) {
		Utente user=this.getUtente(username);
		this.deleteUser(user);
	}
	
	
	@Transactional
	public List<Utente>getAllUsers() {
    List<Utente> result = new ArrayList<>();
    Iterable<Utente> iterable = this.utenteRepository.findAll();
    for(Utente user : iterable)
        result.add(user);
    return result;
	}
	
	
	@Transactional
	public List<Utente> getMembers(Progetto project){
		
		List<Utente> membri=this.utenteRepository.findByVisible(project);
		return membri;
		
		
	}
	
	@Transactional
	public void saveUser(Utente user) {
		this.utenteRepository.save(user);
	}
	

}
