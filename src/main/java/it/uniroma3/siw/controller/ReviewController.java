package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ReviewService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class ReviewController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CredentialsService credentialsService;

    @PostMapping("/user/review/{bookId}")
    public String createReview(@PathVariable("bookId") Long bookId,
                               @Valid @ModelAttribute("review") Review review,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Credentials credentials = credentialsService.getCredentials(username);
        User user = credentials.getUser();

        Book book = bookService.findById(bookId);
        if (book == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Libro non trovato.");
            return "redirect:/books";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookReviews", reviewService.findAllByBook(book));
            model.addAttribute("myReview", null);
            model.addAttribute("review", review);
            return "book.html";
        }

        review.setUser(user);
        review.setBook(book);
        reviewService.save(review);
        return "redirect:/book/" + bookId;
    }

    @PostMapping("/user/review/update")
    public String updateReview(@Valid @ModelAttribute("myReview") Review review,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Credentials credentials = credentialsService.getCredentials(username);
        User currentUser = credentials.getUser();
        
        Review existing = reviewService.findReviewById(review.getId());
        
        if (bindingResult.hasErrors()) {       	
            Book book = existing.getBook();
            model.addAttribute("book", book);
            review.setBook(book);
            model.addAttribute("myReview", review);
            
            List<Review> all = reviewService.findAllByBook(book);
            all.remove(existing);
            model.addAttribute("bookReviews", all);
            return "book.html";
        }

        existing.setTitle(review.getTitle().trim());
        existing.setText(review.getText().trim());
        existing.setVote(review.getVote());

        reviewService.save(existing);
        redirectAttributes.addFlashAttribute("successMessage", "Recensione aggiornata con successo.");
        return "redirect:/book/" + existing.getBook().getId();
    }

    @GetMapping("user/deleteReview/{id}")
    public String deleteOwnReview(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Credentials credentials = credentialsService.getCredentials(username);
        User currentUser = credentials.getUser();

        Review review = reviewService.findReviewById(id);
        if (review != null && review.getUser().equals(currentUser)) {
            reviewService.delete(review);
            redirectAttributes.addFlashAttribute("successMessage", "Recensione eliminata con successo.");
            return "redirect:/book/" + review.getBook().getId();
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Non puoi eliminare questa recensione.");
        return "redirect:/";
    }
}
