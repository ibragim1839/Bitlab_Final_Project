package ibragim.project.core.bitlab.FinalProject.dto;

import ibragim.project.core.bitlab.FinalProject.models.Animal;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class BreedDto {
    private Long id;
    private String name;
    private String specifications;
    private AnimalDto animal;
    private String pictureUrl;
}
