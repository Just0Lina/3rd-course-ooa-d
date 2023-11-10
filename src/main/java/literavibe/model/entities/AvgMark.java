package literavibe.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

}
