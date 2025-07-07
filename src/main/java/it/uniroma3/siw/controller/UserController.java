package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ReviewService;
import it.uniroma3.siw.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/personalArea")
	public String showPersonalArea(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Credentials credentials = credentialsService.getCredentials(auth.getName());
		User user = credentials.getUser();
		
	    Object principal = auth.getPrincipal();
	    boolean google=false;
	    if (principal instanceof OAuth2User oauth2User) {
	    	google=true;
	    }

		model.addAttribute("credentials", credentials);
		model.addAttribute("user", user);
		model.addAttribute("reviews", user.getReviews());
		model.addAttribute("google", google);
		
		return "/personalArea";
	}

	@PostMapping("/updateProfile")
	public String updateProfile(
	        @ModelAttribute("credentials") Credentials formCredentials,
	        @RequestParam("oldPassword") String oldPassword,
	        @RequestParam("newPassword") String newPassword,
	        @RequestParam("confirmPassword") String confirmPassword,
	        //@AuthenticationPrincipal UserDetails userDetails,
	        HttpServletRequest request,
	        HttpServletResponse response,
	        RedirectAttributes redirectAttributes, 
	        Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Credentials currentCredentials = credentialsService.getCredentials(userDetails.getUsername());
		User formUser = formCredentials.getUser();
		User currentUser = currentCredentials.getUser();

		boolean hasErrors = false;
		
		boolean credentialsChange = false;
		
		if(formCredentials.getUsername() == null || formCredentials.getUsername().isBlank()) {
			model.addAttribute("noUsername", "Campo Obbligatorio");
			hasErrors=true;
		}else {
			Credentials sameUsername = credentialsService.getCredentials(formCredentials.getUsername());
			if(sameUsername!=null && !sameUsername.getId().equals(currentCredentials.getId())){
				model.addAttribute("sameUsername", "Username già in uso");
				hasErrors=true;
			}
		}
		
		if(formUser.getName() == null || formUser.getName().isBlank()) {
			model.addAttribute("noName", "Campo Obbligatorio");
			hasErrors=true;
		}
		
		if(formUser.getSurname() == null || formUser.getSurname().isBlank()) {
			model.addAttribute("noSurname", "Campo Obbligatorio");
			hasErrors=true;
		}
		
		if(formUser.getEmail() == null || formUser.getEmail().isBlank()) {
			model.addAttribute("noEmail", "Campo Obbligatorio");
			hasErrors=true;
		}else if(userService.existsByEmailExceptCurrent(formUser.getEmail(), currentUser.getId())){
				model.addAttribute("sameEmail", "Email già in uso");
				hasErrors=true;
			}
		
		if(hasErrors) {
			model.addAttribute("credentials",currentCredentials);
			return "personalArea";
		}
				
		// === AGGIORNA USERNAME SE CAMBIATO ===
        if (!formCredentials.getUsername().equals(currentCredentials.getUsername())) {
            currentCredentials.setUsername(formCredentials.getUsername());
            credentialsChange = true;
        }

        // === CAMBIO PASSWORD SE RICHIESTO ===
        if (newPassword != null && !newPassword.isBlank()) {
            if (oldPassword == null || oldPassword.isBlank()) {
                model.addAttribute("noPassword", "Campo obbligatorio.");
    			model.addAttribute("credentials",currentCredentials);
                return "personalArea";
            }

            if (!passwordEncoder.matches(oldPassword, currentCredentials.getPassword())) {
                model.addAttribute("passwordError", "Password corrente errata.");
    			model.addAttribute("credentials",currentCredentials);
                return "personalArea";
            }

            if (newPassword.equals(oldPassword)) {
                model.addAttribute("passwordChangeError", "La nuova password deve essere diversa da quella corrente.");
    			model.addAttribute("credentials",currentCredentials);
                return "personalArea";
            }

            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("passwordMismatchError", "Le password non coincidono.");
    			model.addAttribute("credentials",currentCredentials);
                return "personalArea";
            }

            currentCredentials.setPassword(newPassword);
            credentialsChange = true;
        }
		
	    // Aggiorna i campi modificabili
	    currentUser.setName(formUser.getName());
	    currentUser.setSurname(formUser.getSurname());
	    currentUser.setEmail(formUser.getEmail());
	   
	    // Salva
	    credentialsService.saveCredentials(currentCredentials);

	    redirectAttributes.addFlashAttribute("profileSuccessMessage", "Profilo aggiornato con successo.");
	    if(credentialsChange==false)
	    	return "redirect:/personalArea";
	    else {
	    	new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
	    	return "credentialsChangeSuccessful";
	    }
	}


	// Mostra tutti gli utenti con le rispettive recensioni
	@GetMapping("/admin/manageUsers")
	public String manageUsers(@RequestParam(value = "username", required = false) String username, Model model, HttpServletRequest request) {
		List<Credentials> allCredentials;
		if (username != null && !username.isEmpty()) {
			allCredentials = credentialsService.findByUsernameStartingWithIgnoreCase(username);
		} else {
			allCredentials = (List<Credentials>) credentialsService.getAllCredentials();
		}
		model.addAttribute("request", request);
		model.addAttribute("credentialsList", allCredentials);
		//model.addAttribute("users", userService.findAllUsersWithReviews());
		return "admin/manageUsers";
	}

	// Elimina una recensione specifica
	@PostMapping("/admin/deleteReview/{id}")
	public String deleteReview(@PathVariable("id") Long reviewId, RedirectAttributes redirectAttributes) {
		reviewService.deleteById(reviewId);
		redirectAttributes.addFlashAttribute("successMessage", "Recensione eliminata con successo!");
		return "redirect:/admin/manageUsers";
	}
	

    @GetMapping("personalArea/deleteReview/{id}")
    public String deleteOwnReview(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Credentials credentials = credentialsService.getCredentials(username);
        User currentUser = credentials.getUser();

        Review review = reviewService.findReviewById(id);
        if (review != null && review.getUser().equals(currentUser)) {
            reviewService.delete(review);
            redirectAttributes.addFlashAttribute("successMessage", "Recensione eliminata con successo.");
            return "redirect:/personalArea";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Non puoi eliminare questa recensione.");
        return "redirect:/";
    }
    
}
