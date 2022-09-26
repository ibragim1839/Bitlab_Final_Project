package ibragim.project.core.bitlab.FinalProject.dto;

import ibragim.project.core.bitlab.FinalProject.models.Pet;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DoctorDto {
    private Long id;
    private String address;
    private String phoneNumber;
    private String description;
    private UserDto user;
}
