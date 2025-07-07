package it.uniroma3.siw.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String title;

	@NotNull
	private Integer year;
	
	@NotBlank
	@Column(length = 2000)
	private String description;
	

	//@JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
	@ManyToMany
	@JoinTable(
	        name = "author_books",
	        joinColumns = @JoinColumn(name = "books_id"),
	        inverseJoinColumns = @JoinColumn(name = "authors_id")
	    )
	private List<Author> authors;
	
	private String coverImageFileName;
	
	@ElementCollection
    private List<String> imageFileNames = new ArrayList<>();

	@OneToMany(mappedBy = "book")
	private List<Review> reviews;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public String getCoverImageFileName() {
		return coverImageFileName;
	}

	public void setCoverImageFileName(String coverImageFileName) {
		this.coverImageFileName = coverImageFileName;
	}

	public List<String> getImageFileNames() {
		return imageFileNames;
	}

	public void setImageFileNames(List<String> imageFileNames) {
		this.imageFileNames = imageFileNames;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getVoteAvg() {
		if (reviews == null || reviews.isEmpty())
			return null;
		return (float) reviews.stream().mapToInt(Review::getVote).average().orElse(0.0);
	}

	@Transient
    public String getImagePath(String imageFileName) {
        if (imageFileName == null)
            return "/images/default.jpg";

        Path staticPath = Paths.get("src/main/resources/static/images/books", imageFileName);
        Path uploadPath = Paths.get("uploads/images/books", imageFileName);

        if (Files.exists(staticPath)) {
            return "/images/books/" + imageFileName;
        } else if (Files.exists(uploadPath)) {
            return "/uploads/images/books/" + imageFileName;
        } else {
            return "/images/default.jpg";
        }
    }	
	
	@Override
	public int hashCode() {
		return Objects.hash(id, title, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(id, other.id) &&
		       Objects.equals(title, other.title) &&
		       Objects.equals(year, other.year);
	}
}
