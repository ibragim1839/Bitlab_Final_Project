package ibragim.project.core.bitlab.FinalProject.dto;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private List<RoleDto> roles;
    private String picUrl;
}
