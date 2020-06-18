package it.uniroma.siw.romatre.progetto.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

@Entity
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false,length = 100)
	private String nome;
	
	@Column
	private String descrizione;
	
	@Column(updatable = false)
	private LocalDateTime dataDiCreazione;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Utente utenteAssegnato;
	
	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name="task_id")
	private List<Tag> tags;
	
	public Task() {
		
		this.tags=new ArrayList<Tag>();
		
	}
	
	
	
	public Task(String nome,String descrizione) {
		this();
		this.nome=nome;
		this.descrizione=descrizione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDateTime getDataDiCreazione() {
		return dataDiCreazione;
	}

	public void setDataDiCreazione(LocalDateTime dataDiCreazione) {
		this.dataDiCreazione = dataDiCreazione;
	}
	
    @PrePersist
    protected void onPersist() {
        this.dataDiCreazione = LocalDateTime.now();
    }



	public Utente getUtenteAssegnato() {
		return utenteAssegnato;
	}



	public void setUtenteAssegnato(Utente utenteAssegnato) {
		this.utenteAssegnato = utenteAssegnato;
	}



	public List<Tag> getTags() {
		return tags;
	}



	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	
	public void addTag(Tag tag) {
		this.tags.add(tag);
	}
	
	
	
    
    
	
	
	
	

}
