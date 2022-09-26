package ibragim.project.core.bitlab.FinalProject.mappers;

import ibragim.project.core.bitlab.FinalProject.models.Owner;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    public List<Owner> toOwnerDtoList(List<Owner> ownerList);
    public Owner toOwnerDto(Owner owner);
}
