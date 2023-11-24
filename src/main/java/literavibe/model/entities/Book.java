package literavibe.model.entities;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.*;

@NoArgsConstructor
@Getter
@Setter
//@ToString
@Entity
@Builder
@Table(name = "books", schema = "litera_vibe")

@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private Integer pages;
    private String info;
    private Date year;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    @ToString.Exclude
    private Publisher publisher;


    @ManyToMany(mappedBy = "books")
    private List<Author> authors = new ArrayList<>();



    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "media_id")
    @ToString.Exclude
    private Media media;

    @ManyToMany(mappedBy = "books")
    @ToString.Exclude
    private Set<Category> categories;

    @ManyToMany(mappedBy = "books")
    @ToString.Exclude
    private List<Collection> collections;


    @ToString.Exclude
    @OneToMany(mappedBy = "book", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Mark> marks;

    @OneToOne(mappedBy = "book", orphanRemoval = true, cascade = CascadeType.ALL)
    private AvgMark avgMark;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder authorIds = new StringBuilder();
        for (Author author : authors) {
            authorIds.append(author.getId()).append(", ");
        }
        // Remove the trailing comma and space
        if (authorIds.length() > 0) {
            authorIds.setLength(authorIds.length() - 2);
        }

        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pages=" + pages +
                ", authorIds=" + authorIds.toString() +
                '}';
    }
}
