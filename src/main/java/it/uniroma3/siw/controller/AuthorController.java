package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.controller.validator.AuthorValidator;
import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;
    
    @Autowired
    private AuthorValidator authorValidator;


    // Utente
    @GetMapping("/authors")
    public String getAuthors(@RequestParam(value = "name", required = false) String name, Model model) {
    	Set<Author> authors;
    	if(name!=null && !name.isEmpty()) {
    		authors=new HashSet<>(authorService.findByNameStartingWithIgnoreCase(name));
    		authors.addAll(authorService.findBySurnameStartingWithIgnoreCase(name));
    	}else {
    		authors= authorService.findAllAuthors(); 
    	}
        model.addAttribute("authors", authors);
        return "authors.html";
    }

    @GetMapping("/author/{id}")
    public String getAuthor(@PathVariable("id") Long id, Model model) {
        model.addAttribute("author", authorService.findById(id));
        return "author.html";
    }
    
    // Admin
    @GetMapping("/admin/formNewAuthor")
    public String formNewAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "admin/formNewAuthor.html";
    }
    
    
    @PostMapping("/admin/author")
    public String saveAuthor(@Valid @ModelAttribute("author") Author author,
                             BindingResult bindingResult,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             Model model,
                             RedirectAttributes redirectAttributes) {


        if (imageFile == null || imageFile.isEmpty()) {
            model.addAttribute("imageError", "Devi caricare una foto dell'autore");
            bindingResult.reject("image", "Nessuna immagine selezionata");
        }
        
        authorValidator.validate(author, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/formNewAuthor.html";
        }

        try {
            String fileName = imageFile.getOriginalFilename();
            Path uploadPath = Paths.get("uploads", "images", "authors");
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            author.setImageFileName(fileName);
        } catch (IOException e) {
            model.addAttribute("uploadError", "Errore durante il caricamento dell'immagine.");
            return "admin/formNewAuthor.html";
        }

        authorService.save(author);
        redirectAttributes.addFlashAttribute("successMessage", "Autore aggiunto con successo!");
        return "redirect:/admin/manageAuthors";
    }


    
    @GetMapping("/admin/manageAuthors")
    public String manageAuthors(@RequestParam(value = "name", required = false) String name, Model model, HttpServletRequest request) {
    	Set<Author> authors;
    	if(name!=null && !name.isEmpty()) {
    		authors=new HashSet<>(authorService.findByNameStartingWithIgnoreCase(name));
    		authors.addAll(authorService.findBySurnameStartingWithIgnoreCase(name));
    	}else {
    		authors= authorService.findAllAuthors(); 
    	}
        model.addAttribute("request", request); // utile se usi il path completo per redirect dinamici
        model.addAttribute("authors", authors);
        return "admin/manageAuthors.html";
    }

    @GetMapping("/admin/updateAuthor/{id}")
    public String formUpdateAuthor(@PathVariable("id") Long id, Model model) {
        Author author = authorService.findById(id);
        model.addAttribute("author", author);
        model.addAttribute("dateOfBirth", author.getDateOfBirth());
        
        return "admin/formUpdateAuthor.html";
    }
    
    
    @PostMapping("/admin/updateAuthor/{id}")
    public String updateAuthor(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("author") Author author,
                               BindingResult bindingResult,
                               @RequestParam("imageFile") MultipartFile imageFile,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        Author existingAuthor = authorService.findById(id);
        if (existingAuthor == null) {
            model.addAttribute("errorMessage", "Autore non trovato.");
            return "admin/formUpdateAuthor.html";
        }

        // Controllo duplicati solo se le info sono cambiate
        List<Author> duplicates = authorService.findByNameAndSurnameAndDateOfBirth(author.getName(), author.getSurname(), author.getDateOfBirth());

        boolean isDuplicate = duplicates.stream()
            .anyMatch(a -> !a.getId().equals(existingAuthor.getId()));

        if (isDuplicate) {
            bindingResult.reject("author.duplicate", "Esiste già un autore con questo nome, cognome e data di nascita");
        }

        if (bindingResult.hasErrors()) {
            return "admin/formUpdateAuthor.html";
        }

        existingAuthor.setName(author.getName());
        existingAuthor.setSurname(author.getSurname());
        existingAuthor.setDateOfBirth(author.getDateOfBirth());
        existingAuthor.setDateOfDeath(author.getDateOfDeath());
        existingAuthor.setNationality(author.getNationality());
        existingAuthor.setDescription(author.getDescription());

        if (!imageFile.isEmpty()) {
            try {
                String fileName = imageFile.getOriginalFilename();
                Path uploadPath = Paths.get("uploads", "images", "authors");
                if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                existingAuthor.setImageFileName(fileName);
            } catch (IOException e) {
                model.addAttribute("uploadError", "Errore durante il caricamento dell'immagine.");
                return "admin/formUpdateAuthor.html";
            }
        }

        authorService.save(existingAuthor);
        redirectAttributes.addFlashAttribute("successMessage", "Autore aggiornato con successo!");
        return "redirect:/admin/updateAuthor/" + id;
    }


    
    @PostMapping("/admin/deleteAuthor/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    	Author author = authorService.findById(id);
    	List<Book> books = author.getBooks();
    	
    	for(Book b: books) {
    		if(b.getAuthors().size()==1) {
    			redirectAttributes.addFlashAttribute("errorMessage", "Questo è l'unico autore di un libro, elimina prima il libro se vuoi cancellare questo autore!");
    			return "redirect:/admin/manageAuthors";
    		}
    		
    		b.getAuthors().remove(author);
            redirectAttributes.addFlashAttribute("successMessage", "Eliminazione avvenuta con successo!");

    	}
        authorService.deleteById(id);
        return "redirect:/admin/manageAuthors";
    }
    

}
