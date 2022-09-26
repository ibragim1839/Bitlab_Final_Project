package ibragim.project.core.bitlab.FinalProject.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "breeds")
@Entity
@Getter
@Setter
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(columnDefinition = "TEXT", name = "specifications")
    private String specifications;

    @Column(name = "pic_url")
    private String pictureUrl;

    @ManyToOne
    private Animal animal;

}
