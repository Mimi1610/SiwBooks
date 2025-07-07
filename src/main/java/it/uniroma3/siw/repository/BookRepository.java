package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Book;
import jakarta.transaction.Transactional;

public interface BookRepository extends CrudRepository<Book, Long>{

	    public List<Book> findByYear(int year);

	    public boolean existsByTitleAndYear(String title, int year);
	    
	    @Query("SELECT b FROM Book b JOIN b.reviews r GROUP BY b HAVING AVG(r.vote) >= 4 ORDER BY AVG(r.vote) DESC")
	    List<Book> findBooksWithTopReviews();

		public List<Book> findByTitleAndYear(String title, Integer year);

		public Set<Book> findByTitle(String title);

		public List<Book> findByTitleStartingWithIgnoreCase(String title);


	}


