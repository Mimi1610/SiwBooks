package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.BookRepository;

	@Service
	public class BookService {

	    @Autowired
	    private BookRepository bookRepository;

	    public Book findById(Long id) {
	        return bookRepository.findById(id).get();
	    }

	    public Iterable<Book> findAll() {
	        return bookRepository.findAll();
	    }

	    public void save(Book book) {
	        bookRepository.save(book);
	    }

	    public Iterable<Book> findByYear(int year) {
	        return bookRepository.findByYear(year);
	    }

	    public boolean existsByTitleAndYear(String title, Integer year) {
	        return bookRepository.existsByTitleAndYear(title, year);
	    }
	    
	    public void deleteById(Long id) {
	    	this.bookRepository.deleteById(id);
	    }
	    
	    @Transactional(readOnly = true)
	    public List<Book> findTopRatedBooks() {
	        return bookRepository.findBooksWithTopReviews();
	    }

		public List<Book> findByTitleAndYear(String title, Integer year) {
			return bookRepository.findByTitleAndYear(title, year);
		}

		public Set<Book> findByTitle(String title) {
			return bookRepository.findByTitle(title);
		}

		public List<Book> findByTitleStartingWithIgnoreCase(String title) {
			return bookRepository.findByTitleStartingWithIgnoreCase(title);
		}

		public boolean removeImage(Book book, String imageName) {

	        if (book == null || imageName == null) return false;

	        List<String> images = new ArrayList<>(book.getImageFileNames());

	        boolean removed = false;
	        if(images.remove(imageName)) {
	            removed = true;
	        }


	        if (removed) {
	            System.out.println("Cancellata");

	            book.setImageFileNames(images);

	            bookRepository.save(book);
	            for( String img : book.getImageFileNames()) {
	                System.out.println(img);
	            }
	        }

	        return removed;
	    }

}


