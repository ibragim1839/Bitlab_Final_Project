package ibragim.project.core.bitlab.FinalProject.mappers;

import ibragim.project.core.bitlab.FinalProject.dto.AnimalDto;
import ibragim.project.core.bitlab.FinalProject.models.Animal;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnimalMapper {
    public List<AnimalDto> toAnimalDtoList(List<Animal> animalList);
    public AnimalDto toAnimalDto(Animal animal);
}
