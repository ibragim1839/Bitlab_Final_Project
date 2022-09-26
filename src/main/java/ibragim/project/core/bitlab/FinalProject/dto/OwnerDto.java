package ibragim.project.core.bitlab.FinalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OwnerDto {
    private Long id;
    private String address;
    private String phoneNumber;
    private UserDto user;
}
