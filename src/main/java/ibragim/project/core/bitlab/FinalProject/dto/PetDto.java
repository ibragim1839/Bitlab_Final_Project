package ibragim.project.core.bitlab.FinalProject.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PetDto {
    private Long id;
    private String name;
    private String description;
    private BreedDto breed;
    private OwnerDto owner;
    private DoctorDto doctor;
    private String picUrl;
}
