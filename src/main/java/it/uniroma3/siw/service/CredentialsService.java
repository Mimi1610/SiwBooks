package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected CredentialsRepository credentialsRepository;

    @Transactional
    public Credentials getCredentials(Long id) {
        Optional<Credentials> result = this.credentialsRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Credentials getCredentials(String username) {
        Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
        return result.orElse(null);
    }

    @Transactional
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRole(Credentials.DEFAULT_ROLE);

        String rawPassword = credentials.getPassword();
        // Codifica solo se la password non è già codificata (verifica con BCrypt pattern)
        if (!rawPassword.startsWith("$2a$")) {
            credentials.setPassword(this.passwordEncoder.encode(rawPassword));
        }

        return this.credentialsRepository.save(credentials);
    }

    public boolean existsByUsername(String username) {
		return credentialsRepository.existsByUsername(username);
	}

    @Transactional
    public Iterable<Credentials> getAllCredentials() {
        return this.credentialsRepository.findAll();
    }

	public List<Credentials> findByUsernameStartingWithIgnoreCase(String username) {
		return credentialsRepository.findByUsernameStartingWithIgnoreCase(username);
	}

	@Transactional
	public Optional<Credentials> findByUsername(String email) {
		return this.credentialsRepository.findByUsername(email);
	}


}
