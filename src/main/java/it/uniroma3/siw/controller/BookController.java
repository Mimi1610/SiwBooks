package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.uniroma3.siw.controller.validator.BookValidator;
import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private BookValidator bookValidator;

	// Elenco di tutti i libri (es. /books)
	@GetMapping("/books")
	public String listBooks(@RequestParam(value = "title", required = false) String title, Model model) {
		List<Book> books;
		if (title != null && !title.isEmpty()) {
			books = bookService.findByTitleStartingWithIgnoreCase(title);
		} else {
			books = (List<Book>) bookService.findAll();
		}
		model.addAttribute("books", books);

		return "books.html";
	}

	@GetMapping("/book/{id}")
	public String bookDetails(@PathVariable("id") Long id, Model model) {
		Book book = bookService.findById(id);
		model.addAttribute("book", book);

		List<Review> bookReviews = reviewService.findAllByBook(book);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String username = auth.getName();
			Credentials credentials = credentialsService.getCredentials(username);

			if (reviewService.existsByBookAndCredentials(book, credentials)) {
				Review myReview = reviewService.findByBookAndUser(book, credentials.getUser());
				bookReviews.remove(myReview);
				model.addAttribute("myReview", myReview);
			} else {
				// Se l'utente non ha ancora scritto una review, preparala vuota per il form
				Review newReview = new Review();
				newReview.setBook(book);
				model.addAttribute("review", newReview);
			}
		}

		model.addAttribute("bookReviews", bookReviews);

		return "book.html";
	}

	// Gestione da parte dell'amministratore
	@GetMapping("/admin/manageBooks")
	public String manageBooks(@RequestParam(value = "title", required = false) String title, Model model,
			HttpServletRequest request) {
		List<Book> books;
		if (title != null && !title.isEmpty()) {
			books = bookService.findByTitleStartingWithIgnoreCase(title);
		} else {
			books = (List<Book>) bookService.findAll();
		}

		model.addAttribute("request", request); // necessario per frontend
		model.addAttribute("books", books);
		return "admin/manageBooks.html";
	}

	// (futuro) Form per aggiungere un nuovo libro
	@GetMapping("/admin/formNewBook")
	public String formNewBook(@RequestParam(required = false) Long selectedAuthorId, Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("authors", authorService.findAll());
		model.addAttribute("selectedAuthorId", selectedAuthorId);
		return "admin/formNewBook.html";
	}

	@PostMapping("/admin/book")
	public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult,
			@RequestParam("image") MultipartFile imageFile,
			@RequestParam(value = "authorsHidden", required = false) String authorsIdsString, Model model,
			RedirectAttributes redirectAttributes) {

		// 1. Recupera gli autori selezionati
		List<Author> selectedAuthors = new ArrayList<>();

		if (authorsIdsString != null && !authorsIdsString.isBlank()) {
			String[] idStrings = authorsIdsString.split(",");
			for (String idStr : idStrings) {
				try {
					Long authorId = Long.parseLong(idStr.trim());
					Author author = authorService.findById(authorId);
					if (author != null)
						selectedAuthors.add(author);
				} catch (NumberFormatException e) {
					// ignora ID non validi
				}
			}
		}

		// 2. Validazioni manuali
		if (selectedAuthors.isEmpty()) {
			model.addAttribute("authorsError", "Seleziona almeno un autore");
			bindingResult.reject("authors", "Nessun autore selezionato");
		}

		if (imageFile == null || imageFile.isEmpty()) {
			model.addAttribute("imageError", "Devi caricare una copertina");
			bindingResult.reject("image", "Nessuna immagine selezionata");
		}

		// 3. Salva l'immagine (necessaria per la validazione)
		if (!imageFile.isEmpty()) {
			try {
				String fileName = imageFile.getOriginalFilename();
				Path uploadPath = Paths.get("uploads", "images", "books");
				if (!Files.exists(uploadPath))
					Files.createDirectories(uploadPath);
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
				book.setCoverImageFileName(fileName);
			} catch (IOException e) {
				model.addAttribute("uploadError", "Errore durante il caricamento dell'immagine.");
				model.addAttribute("authors", authorService.findAll());
				return "admin/formNewBook";
			}
		}

		// 4. Assegna gli autori e valida
		book.setAuthors(selectedAuthors);
		bookValidator.validate(book, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("authors", authorService.findAll());
			return "admin/formNewBook";
		}

		// 5. Salva e redirect
		bookService.save(book);
		redirectAttributes.addFlashAttribute("successMessage", "Libro aggiunto con successo!");
		return "redirect:/admin/manageBooks";
	}

	@GetMapping("/admin/updateBook/{id}")
	public String formUpdateBook(@RequestParam(required = false) Long selectedAuthorId, @PathVariable("id") Long id,
			Model model) {
		Book book = bookService.findById(id);
		model.addAttribute("book", book);
		model.addAttribute("allAuthors", authorService.findAll());
		model.addAttribute("selectedAuthorId", selectedAuthorId);
		return "admin/formUpdateBook.html";
	}

	@PostMapping("/admin/updateBook/{id}")
	public String updateBook(@PathVariable("id") Long id, @Valid @ModelAttribute("book") Book book,
			BindingResult bindingResult, @RequestParam("image") MultipartFile imageFile,
			@RequestParam(value = "authorsHidden", required = false) String authorsIdsString, Model model,
			RedirectAttributes redirectAttributes) {

		Book existingBook = bookService.findById(id);
		if (existingBook == null) {
			model.addAttribute("errorMessage", "Libro non trovato.");
			model.addAttribute("allAuthors", authorService.findAll());
			return "admin/formUpdateBook";
		}

		// 1. Recupera autori selezionati
		List<Author> selectedAuthors = new ArrayList<>();
		if (authorsIdsString != null && !authorsIdsString.isBlank()) {
			String[] idStrings = authorsIdsString.split(",");
			for (String idStr : idStrings) {
				try {
					Long authorId = Long.parseLong(idStr.trim());
					Author author = authorService.findById(authorId);
					if (author != null)
						selectedAuthors.add(author);
				} catch (NumberFormatException ignored) {
				}
			}
		}

		if (selectedAuthors.isEmpty()) {
			model.addAttribute("authorsError", "Seleziona almeno un autore");
			bindingResult.reject("authors", "Nessun autore selezionato");
		}

		// 2. Prepara book per validazione personalizzata
		book.setId(existingBook.getId()); // necessario per il validator
		book.setAuthors(selectedAuthors); // set temporaneo per validazione

		// 3. Validazione personalizzata
		bookValidator.validate(book, bindingResult);

		// 4. In caso di errori, torna al form con il book modificato
		if (bindingResult.hasErrors()) {
			book.setCoverImageFileName(existingBook.getCoverImageFileName());
			model.addAttribute("book", book);
			model.addAttribute("allAuthors", authorService.findAll());
			return "admin/formUpdateBook";
		}

		// 5. Aggiorna effettivamente existingBook
		existingBook.setTitle(book.getTitle().trim());
		existingBook.setYear(book.getYear());
		existingBook.setDescription(book.getDescription());
		existingBook.setAuthors(selectedAuthors);

		// 6. Salva immagine se fornita
		if (!imageFile.isEmpty()) {
			try {
				String fileName = imageFile.getOriginalFilename();
				Path uploadPath = Paths.get("uploads", "images", "books");
				if (!Files.exists(uploadPath))
					Files.createDirectories(uploadPath);
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
				existingBook.setCoverImageFileName(fileName);
			} catch (IOException e) {
				model.addAttribute("uploadError", "Errore durante il caricamento dell'immagine.");
				model.addAttribute("book", book);
				model.addAttribute("allAuthors", authorService.findAll());
				return "admin/formUpdateBook";
			}
		}

		// 7. Salva nel DB
		bookService.save(existingBook);
		redirectAttributes.addFlashAttribute("successMessage", "Libro aggiornato con successo!");
		return "redirect:/admin/updateBook/" + id;
	}

	@PostMapping("/admin/deleteBook/{id}")
	public String deleteBook(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		Book book = bookService.findById(id);
		List<Review> reviews = reviewService.getReviewsByBook(book);
		reviewService.deleteAll(reviews);
		bookService.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Libro eliminato con successo!");
		return "redirect:/admin/manageBooks";
	}

	@PostMapping("/admin/book/{bookId}/remove-image/{imageName:.+}")
	public String removeImageFromBook(@RequestParam(required = false) Long selectedAuthorId, @PathVariable Long bookId, @PathVariable String imageName, Model model) {
		  Book book = bookService.findById(bookId);
	        if (!bookService.removeImage(book, imageName)) {
	            model.addAttribute("genericErrorImg", "L'immagine non è stata rimossa.");
	        } else {
	            model.addAttribute("successDeleteImg", "L'immagine è stata rimossa correttamente.");
	        } 
		model.addAttribute("book", book);
		model.addAttribute("allAuthors", authorService.findAll());
		model.addAttribute("selectedAuthorId", selectedAuthorId);
		return "admin/formUpdateBook.html";

	}
	
	@PostMapping("/admin/book/{bookId}/add-images")
    public String addExtraImages(@RequestParam(required = false) Long selectedAuthorId,
    							@PathVariable Long bookId,
                                 @RequestParam("extraImages") List<MultipartFile> files,
                                 @RequestParam(value = "name", required = false) String name,
                                 Model model) {
        Book book = bookService.findById(bookId);

        Path uploadPath = Paths.get("uploads", "images", "books");

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            List<String> newImageNames = new ArrayList<>();

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = file.getOriginalFilename();
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                    newImageNames.add(fileName);
                }
            }

            // Aggiorna la lista immagini solo se ci sono immagini nuove
            if (!newImageNames.isEmpty()) {
                if (book.getImageFileNames() == null) {
                    book.setImageFileNames(new ArrayList<>());
                }
                book.getImageFileNames().addAll(newImageNames);
                bookService.save(book);
            }

        } catch (IOException e) {
            model.addAttribute("genericErrorImg", "Errore caricamento immagine.");
            e.printStackTrace();
        }

		model.addAttribute("book", book);
		model.addAttribute("allAuthors", authorService.findAll());
		model.addAttribute("selectedAuthorId", selectedAuthorId);
		return "admin/formUpdateBook.html";
    }
	

}
