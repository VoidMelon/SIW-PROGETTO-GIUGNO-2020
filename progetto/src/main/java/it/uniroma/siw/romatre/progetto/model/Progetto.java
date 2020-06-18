package it.uniroma.siw.romatre.progetto.model;

import java.time.LocalDate;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

@Entity
public class Progetto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false,length = 100)
	private String nome;
	
	@Column(nullable=false,length=1000)
	private String descrizione;

	@Column(updatable = false)
	private LocalDate dataInizio;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	private Utente owner;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Utente> membri;
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name="progetto_id")
	
	private List<Task> tasks;
	
	
	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name="progetto_id")
	private List<Tag> tags;
	
	
	
	public Progetto() {
		this.tasks=new ArrayList<Task>();
		this.membri=new ArrayList<Utente>();
		this.tags=new ArrayList<Tag>();
		
	}
	
	public Progetto(String nome, String descrizione) {
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

	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Utente getOwner() {
		return owner;
	}

	public void setOwner(Utente owner) {
		this.owner = owner;
	}

	public List<Utente> getMembri() {
		return membri;
	}

	public void setMembri(List<Utente> membri) {
		this.membri = membri;
	}
	
	public void addMember(Utente utente) {
		if(!this.membri.contains(utente)) {
			this.membri.add(utente);
		}
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(Task task) {
		this.tasks.add(task);
	}
	
    @PrePersist
    protected void onPersist() {
        this.dataInizio = LocalDate.now();
    }

	@Override
	public String toString() {
		return "Progetto [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", dataInizio=" + dataInizio
				+ ", owner=" + owner + "]";
	}
	
	
	public void addTag(Tag tag) {
		this.tags.add(tag);
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	
    
    
    
	
	
	
	
	
	
	
	
	
	
	

}
