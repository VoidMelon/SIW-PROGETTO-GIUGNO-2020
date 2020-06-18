package it.uniroma.siw.romatre.progetto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma.siw.romatre.progetto.model.Tag;
import it.uniroma.siw.romatre.progetto.repository.TagRepository;

@Service
public class TagService {
	
	
	
	
	
	@Autowired
	TagRepository tagRepository;
	
	
	
	
	@Transactional
	public Tag getTag(Long id){
		Optional<Tag> optional = this.tagRepository.findById(id);
		return optional.orElse(null);
	}
	
	
	@Transactional
	public Tag getTag(String nome) {
		Optional<Tag> optional = this.tagRepository.findByNome(nome);
		return optional.orElse(null);
	}
	
	@Transactional
	public void setTask(Tag tag) {
		this.tagRepository.save(tag);
	}
	
	@Transactional
	public void deleteTag(Tag tag) {
		this.tagRepository.delete(tag);
	}
	
	@Transactional
	public List<Tag> getAllTags() {
		Iterable<Tag> iter=this.tagRepository.findAll();
		List<Tag> tags = new ArrayList<>();
		for(Tag tag:iter) {
			tags.add(tag);
		}
		return tags;
	}
	

}
