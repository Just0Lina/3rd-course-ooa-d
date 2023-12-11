package literavibe.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "avg_marks", schema = "litera_vibe")
public class AvgMark {
    @Id
    private Long bookId;

    @Column(name = "avg_mark")
    private Float avgMark;

    @Column(name = "quantity")
    private Long quantity;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @MapsId
    private Book book;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvgMark avgMark1 = (AvgMark) o;
        return Objects.equals(bookId, avgMark1.bookId) && Objects.equals(avgMark, avgMark1.avgMark) && Objects.equals(quantity, avgMark1.quantity) && Objects.equals(book, avgMark1.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, avgMark, quantity, book);
    }

}
