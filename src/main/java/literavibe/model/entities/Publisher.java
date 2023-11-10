package literavibe.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "publishers", schema = "litera_vibe")
@AllArgsConstructor

public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "display_name")
    private String displayName;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "password")
    private String password;


}
