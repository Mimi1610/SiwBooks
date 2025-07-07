package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.repository.AuthorRepository;
import jakarta.transaction.Transactional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    
    @Transactional
    public Author findById(Long id) {
        return authorRepository.findById(id).get();
    }
    
    @Transactional
    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }

    public void save(Author author) {
        authorRepository.save(author);
    }
    
    public void deleteById(Long id) {
    	authorRepository.deleteById(id);
    }

	public List<Author> findByNameAndSurnameAndDateOfBirth(String name, String surname, LocalDate dateOfBirth) {
		return this.authorRepository.findByNameAndSurnameAndDateOfBirth(name, surname, dateOfBirth);
	}

	public Set<Author> findByNameStartingWithIgnoreCase(String name) {
		return this.authorRepository.findByNameStartingWithIgnoreCase(name);
	}

	public Set<Author> findBySurnameStartingWithIgnoreCase(String name) {
		return this.authorRepository.findBySurnameStartingWithIgnoreCase(name);
	}
	
	public Set<Author> findAllAuthors(){
		return this.authorRepository.findAll();
	}
	
}
