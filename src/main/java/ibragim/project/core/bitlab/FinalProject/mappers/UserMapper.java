package ibragim.project.core.bitlab.FinalProject.mappers;

import ibragim.project.core.bitlab.FinalProject.dto.UserDto;
import ibragim.project.core.bitlab.FinalProject.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public List<UserDto> toUserDtoList(List<User> users);
    public UserDto toUserDto(User user);
}
