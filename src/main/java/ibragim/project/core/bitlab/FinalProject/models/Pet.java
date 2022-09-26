package ibragim.project.core.bitlab.FinalProject.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.print.Doc;
import java.util.List;

@Entity
@Table(name = "pets")
@Getter
@Setter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(columnDefinition = "TEXT", name = "description")
    private String description;

    @Column(name = "pic_url")
    private String picUrl;

    @ManyToOne
    private Breed breed;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Owner owner;
}
