package ibragim.project.core.bitlab.FinalProject.mappers;

import ibragim.project.core.bitlab.FinalProject.dto.RoleDto;
import ibragim.project.core.bitlab.FinalProject.models.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    public List<RoleDto> toRoleDtoList(List<Role> roleList);
    public RoleDto toRoleDto(Role role);
}
