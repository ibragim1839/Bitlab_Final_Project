package ibragim.project.core.bitlab.FinalProject.services;

import ibragim.project.core.bitlab.FinalProject.Repositories.BreedsRepository;
import ibragim.project.core.bitlab.FinalProject.Repositories.CommentRepository;
import ibragim.project.core.bitlab.FinalProject.Repositories.PetsRepository;
import ibragim.project.core.bitlab.FinalProject.dto.BreedDto;
import ibragim.project.core.bitlab.FinalProject.mappers.BreedMapper;
import ibragim.project.core.bitlab.FinalProject.models.Animal;
import ibragim.project.core.bitlab.FinalProject.models.Breed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BreedService {
    private final BreedsRepository breedsRepository;
    private final PetsRepository petsRepository;
    private final CommentRepository commentRepository;
    private final BreedMapper breedMapper;


    public List<BreedDto> getAllBreeds(){
        return breedMapper.toBreedDtoList(breedsRepository.findAll());
    }

    public Breed getBreedById(Long id){
        return breedsRepository.findById(id).orElse(null);
    }

    public Breed saveBreed(Breed breed){
        if (breed!=null){
            return breedsRepository.save(breed);
        }
        return null;
    }

    public void deleteBreed(Breed breed) {
        commentRepository.deleteCommentsByPetBreed(breed);
        petsRepository.deletePetsByBreed(breed);
        breedsRepository.delete(breed);
    }

    public List<BreedDto> getBreedsWithPetsAndNoOwner(){
        List<Long> list = breedsRepository.getBreedsWhatHasPetsWithNoOwner();
        List<Breed> breeds = breedsRepository.getBreedByIdInOrderByAnimal(list);
        return breedMapper.toBreedDtoList(breeds);
    }
}