package ibragim.project.core.bitlab.FinalProject.services;

import ibragim.project.core.bitlab.FinalProject.Repositories.*;
import ibragim.project.core.bitlab.FinalProject.dto.PetDto;
import ibragim.project.core.bitlab.FinalProject.mappers.PetMapper;
import ibragim.project.core.bitlab.FinalProject.models.*;
import liquibase.pro.packaged.S;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetsRepository petsRepository;
    private final DoctorRepository doctorRepository;
    private final OwnerRepository ownerRepository;
    private final BreedsRepository breedsRepository;
    private final CommentRepository commentRepository;

    private final PetMapper petMapper;


    public void deletePetsByBreed(Breed breed){
        petsRepository.deletePetsByBreed(breed);
    };
    public void deletePetsByBreedAnimal(Animal animal){petsRepository.deletePetsByBreedAnimal(animal);};
    public List<Pet> getPetsByBreedAnimal(Animal animal){
        return petsRepository.getPetsByBreedAnimal(animal);
    };
    public List<Pet> getPetsByBreed(Breed breed){
        return petsRepository.getPetsByBreed(breed);
    }
    public List<Pet> getPetsByOwner_Id(Long id){
        return petsRepository.getPetsByOwner_Id(id);
    }
    public List<Pet> getPetsByDoctor_Id(Long id){
        return petsRepository.getPetsByDoctor_Id(id);
    }
    public void deleteOwnerFromPetByOwnerId(Long ownerId){
        petsRepository.deleteOwnerFromPetByOwnerId(ownerId);
    }
    public void deleteDoctorFromPetByDoctorId(Long doctorId){
        petsRepository.deleteDoctorFromPetByDoctorId(doctorId);
    }
    public List<Pet> getAllPets(){
        return petsRepository.findAll();
    }
    public Pet savePet(Pet pet){
        return petsRepository.save(pet);
    }
    public Pet getPetById(Long id){
        return petsRepository.findById(id).orElse(null);
    }

    public Pet setDoctorToPet(Long petId, Long doctorId){
        Pet pet = petsRepository.findById(petId).orElse(null);
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if(pet!=null && doctor!=null){
            pet.setDoctor(doctor);
            return petsRepository.save(pet);
        }
        return null;
    }

    public Pet setOwnerToPet(Long petId, Long ownerId){
        Pet pet = petsRepository.findById(petId).orElse(null);
        Owner owner = ownerRepository.findById(ownerId).orElse(null);
        if(pet!=null && owner!=null){
            commentRepository.deleteCommentsByPet_Id(petId);
            pet.setOwner(owner);
            return petsRepository.save(pet);
        }
        return null;
    }

    public void deletePet(Pet pet){
        if(petsRepository.findById(pet.getId()).isPresent()){
            commentRepository.deleteCommentsByPet_Id(pet.getId());
            petsRepository.delete(pet);
        }
    }

    public Pet deleteOwnerFromPet(Long petId){
        Pet pet = petsRepository.findById(petId).orElse(null);
        if (pet!=null){
            pet.setOwner(null);
            return petsRepository.save(pet);
        }
        return null;
    }

    public List<Pet> getPetsByDoctorUserId(Long id) {
        Doctor doctor = doctorRepository.getDoctorByUser_Id(id);
        return getPetsByDoctor_Id(doctor.getId());
    }

    public List<Pet> getPetsWithNoOwner(){
        return petsRepository.findAllByOwnerIsNull();
    }

    public void removePetFromDoctorByPetId(Long id){
        Pet pet = getPetById(id);
        if (pet != null){
            pet.setDoctor(null);
            savePet(pet);
        }
    }

    public void removePetFromOwnerByPetId(Long id) {
        Pet pet = getPetById(id);
        if (pet != null){
            pet.setOwner(null);
            savePet(pet);
        }
    }

    public List<PetDto> getPetsWithNoOwnerByBreedId(Long id){
        return petMapper.toPetDtoList(petsRepository.getPetsByBreedIdAndOwnerIsNull(id));
    }

//    public List<PetDto> findPetsWithNoCardsAndNoOwners(){
//        List<Pet> pets = getPetsWithNoOwner();
//        List<Card> cards = cardRepository.findAll();
//        HashSet<Long> idOfPetsWithCards = new HashSet<>();
//        for (Card card:cards){
//            idOfPetsWithCards.add(card.getPet().getId());
//        }
//        HashSet<Long> idOfPetsWithNoOwner = new HashSet<>();
//        for (Pet pet:pets){
//            idOfPetsWithNoOwner.add(pet.getId());
//        }
//        idOfPetsWithNoOwner.removeAll(idOfPetsWithCards);
//        List<Long> idOfPetsWithNoOwnerAndNoCard = new ArrayList<>(idOfPetsWithNoOwner);
//         List<Pet> petsWithNoOwnerAndNoCard = petsRepository.getPetsByIdIn(idOfPetsWithNoOwnerAndNoCard);
//        return petMapper.toPetDtoList(petsWithNoOwnerAndNoCard);
//    }
}
