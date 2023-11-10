package literavibe.model.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "media", schema = "litera_vibe")
@Builder
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    @ToString.Exclude
    private MediaType mediaType;

    @Column(name = "file_data", columnDefinition = "BLOB")
    @JdbcTypeCode(Types.VARBINARY)
    @NotNull
    private byte[] fileData;

    @OneToOne(mappedBy = "media", orphanRemoval = true)
    @ToString.Exclude
    private Book book;

    @OneToOne(mappedBy = "media", orphanRemoval = true)
    @ToString.Exclude
    private Author author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return getId() == media.getId();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
