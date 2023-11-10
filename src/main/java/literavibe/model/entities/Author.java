package literavibe.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
//@ToString
@Entity
@Builder
@Table(name = "authors", schema = "litera_vibe")
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "second_name")
    private String secondName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_url")
    @ToString.Exclude
    private Media media;

    @Column(name = "info")
    private String info;

    @ManyToMany
    @JoinTable(name = "authors_books", schema = "litera_vibe",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                // Avoid printing the entire set to prevent infinite loop
                ", books=" + books.size() +
                '}';
    }
}
