package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ReviewService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;
	
	@Autowired
	private ReviewService reviewService;

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegister";
	}

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "formLogin";
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("books", bookService.findTopRatedBooks());
		//login normale
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
		        return "index";
		    }

		    Object principal = authentication.getPrincipal();

		    // Caso login standard (UserDetails)
		    if (principal instanceof UserDetails userDetails) {
		        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		        if (credentials != null && credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
		            return "admin/indexAdmin";
		        }
		    } 
		    //Caso login con Google (OAuth2User)
		    else if (principal instanceof OAuth2User oauth2User) {
		        String email = oauth2User.getAttribute("email");
		        Credentials credentials = credentialsService.getCredentials(email); 

		        if (credentials != null && credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
		            return "admin/indexAdmin";
		        }
		    }

		    return "index";
	}

	@GetMapping("/success")
	public String defaultAfterLogin(Model model) {
		model.addAttribute("books", bookService.findTopRatedBooks());

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());

		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			return "admin/indexAdmin.html";
		}
		return "redirect:/";
	}

	 @PostMapping("/register")
	    public String registerUser(@Valid @ModelAttribute("user") User user,
	                               BindingResult userBindingResult,
	                               @Valid @ModelAttribute("credentials") Credentials credentials,
	                               BindingResult credentialsBindingResult,
	                               @RequestParam("confirmPassword") String confirmPassword,
	                               Model model) {

		 boolean hasError = false;
		 
	        if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
	        	
	            // Controllo se le password coincidono
	            if (!credentials.getPassword().equals(confirmPassword)) {
	                model.addAttribute("passwordMismatchError", "Le password non coincidono");
	                hasError = true;
	            }

	            // Controllo se lo username esiste già
	            if (this.credentialsService.existsByUsername(credentials.getUsername())) {
	                model.addAttribute("usernameAlreadyInUseError", "Username già esistente.");
	                hasError = true;
	            }

	            // Controllo se l'email è già usata
	            if (this.userService.existsByEmail(user.getEmail())) {
	                model.addAttribute("emailAlreadyInUseError", "Email già esistente.");
	                hasError = true;
	            }

	            // Se anche uno solo dei controlli ha fallito, torno al form
	            if (hasError)
	                return "formRegister";
	            credentials.setUser(user);
	            this.credentialsService.saveCredentials(credentials);
	            return "registrationSuccessful";
	        }

	        return "formRegister";
	    }

	}
