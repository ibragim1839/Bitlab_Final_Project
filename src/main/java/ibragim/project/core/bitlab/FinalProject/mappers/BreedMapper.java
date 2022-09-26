package ibragim.project.core.bitlab.FinalProject.mappers;

import ibragim.project.core.bitlab.FinalProject.dto.BreedDto;
import ibragim.project.core.bitlab.FinalProject.models.Breed;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BreedMapper {
    public List<BreedDto> toBreedDtoList(List<Breed> breedList);
    public BreedDto toBreedDto(Breed breedList);
}
