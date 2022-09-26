package ibragim.project.core.bitlab.FinalProject.Repositories;

import ibragim.project.core.bitlab.FinalProject.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PetsRepository extends JpaRepository<Pet, Long> {
    public void deletePetsByBreed(Breed breed);
    public void deletePetsByBreedAnimal(Animal animal);
    public List<Pet> getPetsByBreedAnimal(Animal animal);
    public List<Pet> getPetsByBreed(Breed breed);
    public List<Pet> getPetsByOwner_Id(Long id);
    public List<Pet> getPetsByDoctor_Id(Long id);

    @Modifying
    @Query(value = "INSERT INTO pets where owner_id=?1 (owner_id) values (?2)",nativeQuery = true)
    public void setNewOwnerToPetsByOldOwnerId(Long oldOwnerId, Long newOwnerId);

    @Modifying
    @Query(value = "INSERT INTO pets where doctor_id=?1 (doctor_id) values (?2)",nativeQuery = true)
    public void setNewDoctorToPetsByOldDoctorId(Long oldDoctorId, Long newDoctorId);

    @Modifying
    @Query(value = "UPDATE pets set owner_id=null WHERE owner_id=?1", nativeQuery = true)
    public void deleteOwnerFromPetByOwnerId(Long ownerId);

    @Modifying
    @Query(value = "update pets set doctor_id=null where doctor_id=?1", nativeQuery = true)
    public void deleteDoctorFromPetByDoctorId(Long doctorId);

    public List<Pet> findAllByOwnerIsNull();

    public List<Pet> findPetsByOwnerIsNullAndBreed_Id(Long id);

    public List<Pet> getPetsByIdIn(List<Long> idList);

    public List<Pet> getPetsByBreedIdAndOwnerIsNull(Long id);
}
