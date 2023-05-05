package exercise.model;

import lombok.Data;

import javax.persistence.*;

// BEGIN
@Data
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    private String body;
    @ManyToOne()
    private Category category;
}
// END
