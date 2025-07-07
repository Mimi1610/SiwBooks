package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.service.AuthorService;

import java.util.List;

@Component
public class AuthorValidator implements Validator {

    @Autowired
    private AuthorService authorService;

    @Override
    public void validate(Object target, Errors errors) {
        Author author = (Author) target;

        if (author.getName() != null && author.getSurname() != null && author.getDateOfBirth() != null) {
            List<Author> existingAuthors = authorService.findByNameAndSurnameAndDateOfBirth(author.getName(), author.getSurname(), author.getDateOfBirth());

            if (!existingAuthors.isEmpty()) {
                errors.reject("author.duplicate", "Esiste già un autore con questo nome, cognome e data di nascita");
            }
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Author.class.equals(aClass);
    }
}
