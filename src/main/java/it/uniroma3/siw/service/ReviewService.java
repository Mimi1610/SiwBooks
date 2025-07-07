package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public void delete(Review review) {
        reviewRepository.delete(review);
    }

    public Review findReviewById(Long id) {
        return reviewRepository.findById(id).get();
    }

    public List<Review> getReviewsByBook(Book book) {
        return reviewRepository.findByBook(book);
    }

    public boolean alreadyReviewed(Book book, User user) {
        return reviewRepository.existsByBookAndUser(book, user);
    }

    public Review findByBookAndUser(Book book, User user) {
        return reviewRepository.findByBookAndUser(book, user);
    }

	public boolean existsByBookAndCredentials(Book book, Credentials credentials) {
		return reviewRepository.existsByBookAndUser(book, credentials.getUser());
	}

	public List<Review> findAllByBook(Book book) {
		return reviewRepository.findByBook(book);
	}

	public void deleteById(Long reviewId) {
		this.reviewRepository.deleteById(reviewId);		
	}

	public void deleteAll(List<Review> reviews) {
		this.reviewRepository.deleteAll(reviews);
	}


}
