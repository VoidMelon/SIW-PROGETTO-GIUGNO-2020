package it.uniroma.siw.romatre.progetto.controller.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import it.uniroma.siw.romatre.progetto.model.Utente;
import it.uniroma.siw.romatre.progetto.repository.UtenteRepository;


@Component
@Scope(value = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {
	
	
	
	private Utente user;
	
	
	
	
	@Autowired
	UtenteRepository utenteRepository;
	
	
	public Utente getLoggedUser() {
		if(this.user==null)
			this.update();
		return this.user;
	}
	
	
	
	private void update() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUserDetails = (UserDetails) obj;
		
		this.user=this.utenteRepository.findByUsername(loggedUserDetails.getUsername()).get();
		//this.user.setPassword("[PROTECTED]");
	}
	
	
	
	
	

}
