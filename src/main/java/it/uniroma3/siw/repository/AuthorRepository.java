package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long>{
	
	public boolean existsByNameAndSurname(String name, String surname);

	public List<Author> findByNameAndSurnameAndDateOfBirth(String name, String surname, LocalDate dateOfBirth);

	public Set<Author> findByNameStartingWithIgnoreCase(String name);

	public Set<Author> findBySurnameStartingWithIgnoreCase(String name);
	
	public Set<Author> findAll();

}
