package it.uniroma3.siw.controller.validator;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.BookService;

@Component
public class BookValidator implements Validator{
	
	@Autowired
	private BookService bookService;
	
	 private boolean sameAuthors(List<Author> a1, List<Author> a2) {
	        if (a1 == null || a2 == null) return false;
	        if (a1.size() != a2.size()) return false;
	        return a1.containsAll(a2) && a2.containsAll(a1);
	    }
	
	@Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (book.getTitle() != null && book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            // Cerca tutti i libri con lo stesso titolo
            Set<Book> existingBooks = bookService.findByTitle(book.getTitle());

            for (Book existing : existingBooks) {
                if (!existing.getId().equals(book.getId()) && sameAuthors(existing.getAuthors(), book.getAuthors())) {
                    errors.reject("book.duplicate", "Esiste già un libro con questo titolo e autori");
                    break;
                }
            }
        }
    }
	
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Book.class.equals(aClass);
	}
}