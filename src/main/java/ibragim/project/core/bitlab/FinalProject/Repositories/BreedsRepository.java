package ibragim.project.core.bitlab.FinalProject.Repositories;

import ibragim.project.core.bitlab.FinalProject.models.Animal;
import ibragim.project.core.bitlab.FinalProject.models.Breed;
import liquibase.pro.packaged.L;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BreedsRepository extends JpaRepository<Breed, Long> {
    public void deleteBreedsByAnimal(Animal animal);

    @Query(value = "SELECT id FROM breeds intersect select breed_id from pets", nativeQuery = true)
    public List<Long> getBreedsWhatHasPets();

    @Query(value = "SELECT id FROM breeds intersect select breed_id from pets where owner_id is null", nativeQuery = true)
    public List<Long> getBreedsWhatHasPetsWithNoOwner();

    public List<Breed> getBreedByIdInOrderByAnimal(List<Long> ids);
}
