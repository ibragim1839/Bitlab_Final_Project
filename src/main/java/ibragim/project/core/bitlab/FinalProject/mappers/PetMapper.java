package ibragim.project.core.bitlab.FinalProject.mappers;

import ibragim.project.core.bitlab.FinalProject.dto.PetDto;
import ibragim.project.core.bitlab.FinalProject.models.Pet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMapper {
    public List<PetDto> toPetDtoList(List<Pet> petDtoList);
    public PetDto toPetDto(Pet petDto);
}
