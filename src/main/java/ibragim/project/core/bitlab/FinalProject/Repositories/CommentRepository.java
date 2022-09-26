package ibragim.project.core.bitlab.FinalProject.Repositories;
import ibragim.project.core.bitlab.FinalProject.models.Animal;
import ibragim.project.core.bitlab.FinalProject.models.Breed;
import ibragim.project.core.bitlab.FinalProject.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public void deleteCommentsByPet_Id(Long pet_id);
    public void deleteCommentsByPetBreedAnimal(Animal animal);
    public void deleteCommentsByPetBreed(Breed breed);
    public List<Comment> findCommentsByPet_IdOrderByPostDate(Long id);

}
