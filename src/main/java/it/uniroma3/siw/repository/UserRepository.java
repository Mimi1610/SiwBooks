package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

	public boolean existsByEmail(String email);
	

	@Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.reviews r LEFT JOIN FETCH r.book")
	public List<User> findAllUsersWithReviews();


	public User findByEmail(String email);


}
