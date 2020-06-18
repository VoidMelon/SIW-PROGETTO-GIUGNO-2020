package it.uniroma.siw.romatre.progetto.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;



@Entity
public class Utente {
	
	
	public static final String DEFAULT_ROLE = "DEFAULT";
	public static final String ADMIN_ROLE = "ADMIN";
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long id;
	
	@Column(nullable = false,length = 100,unique = true)
	
	private String username;
	
	@Column(nullable = false, length = 100)
	
	private String password;
	
	@Column(nullable = false, length = 100)
	
	private String nome;
	
	@Column(nullable = false, length = 100)
	
	private String cognome;
	
	@Column(updatable = false,nullable=false)
	
	private LocalDateTime dataDiCreazione;
	
	@OneToMany(cascade = CascadeType.REMOVE,mappedBy = "owner")
	
	private List<Progetto> creati;
	
	@ManyToMany(mappedBy = "membri")
	
	private List<Progetto> visible;
	
	@Column(nullable = false, length = 10)
	private String role;
	
	
	public Utente() {
		this.creati=new ArrayList<Progetto>();
		this.visible= new ArrayList<Progetto>();
	}
	
	public Utente(String username, String password, 
			      String nome, String cognome) {
		this();
		this.username=username;
		this.password=password;
		this.nome=nome;
		this.cognome=cognome;
	}
	
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public LocalDateTime getDataDiCreazione() {
		return dataDiCreazione;
	}
	public void setDataDiCreazione(LocalDateTime dataDiCreazione) {
		this.dataDiCreazione = dataDiCreazione;
	}
	public List<Progetto> getCreati() {
		return creati;
	}
	public void setCreati(List<Progetto> creati) {
		this.creati = creati;
	}
	public List<Progetto> getVisible() {
		return visible;
	}
	public void setVisible(List<Progetto> visible) {
		this.visible = visible;
	}
    @PrePersist
    protected void onPersist() {
        this.dataDiCreazione = LocalDateTime.now();
    }
    
    public void addProgettoCreato(Progetto progetto) {
    	this.creati.add(progetto);
    	
    }
	
	
	
	
	
	
	
	
	
	
	
	
	

}
