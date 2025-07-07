package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Book;

public interface ReviewRepository extends CrudRepository<Review, Long>{
	
	public List<Review> findByBook(Book book);
	
	public boolean existsByBookAndUser(Book book, User user);
	
	public Review findByBookAndUser(Book book, User user);


}
