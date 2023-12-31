package literavibe.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "collections", schema = "litera_vibe")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "author_id")
//    @ToString.Exclude
//    private User author;
//
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "media_id")
//    @ToString.Exclude
//    private Media media;

/**
 * @deprecated
 * Будет подгружаться вся коллекция. Если во всей коллекции нет нужды, лучше использовать другие методы
 * Добавление/удаление рецептов через коллекцию не будет работать - сделано специально, чтобы поле использовалось только как read-only
 * */
    @ManyToMany
    @JoinTable(
            name = "collections_distribution" , schema = "litera_vibe",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @Deprecated()
    private Set<Book> books;

//    @ToString.Exclude
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User author;

    public Collection(String name) {
        this.name = name;
    }
}
