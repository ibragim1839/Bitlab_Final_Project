package ibragim.project.core.bitlab.FinalProject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "body",columnDefinition = "TEXT")
    String body;

    @Column(name = "post_date", insertable = false)
    Timestamp postDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    User author;

    @ManyToOne(fetch = FetchType.EAGER)
    Pet pet;
}
