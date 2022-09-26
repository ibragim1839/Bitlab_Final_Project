package ibragim.project.core.bitlab.FinalProject.services;

import ibragim.project.core.bitlab.FinalProject.Repositories.*;
import ibragim.project.core.bitlab.FinalProject.mappers.AnimalMapper;
import ibragim.project.core.bitlab.FinalProject.models.Animal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService{
    private final AnimalsRepository animalsRepository;
    private final PetsRepository petsRepository;
    private final BreedsRepository breedsRepository;
    private final AnimalMapper animalMapper;
    private final CommentRepository commentRepository;

    public List<Animal> getAnimals(){
        return animalsRepository.findAll();
    }

    public Animal getAnimal(Long id){
        return animalsRepository.findById(id).orElse(null);
    }

    public Animal saveAnimal(Animal animal){
        if (animal!=null){
            return animalsRepository.save(animal);
        }
        return null;
    }

    public void deleteAnimal(Animal animal){
        commentRepository.deleteCommentsByPetBreedAnimal(animal);
        petsRepository.deletePetsByBreedAnimal(animal);
        breedsRepository.deleteBreedsByAnimal(animal);
        animalsRepository.delete(animal);
    }
}
