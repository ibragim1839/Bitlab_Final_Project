package ibragim.project.core.bitlab.FinalProject.Repositories;

import ibragim.project.core.bitlab.FinalProject.models.Owner;
import ibragim.project.core.bitlab.FinalProject.models.Pet;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.ManyToOne;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    public Owner getOwnerByUser_Id(Long id);
    public void deleteByUser_Id(Long id);
}
