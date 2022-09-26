package ibragim.project.core.bitlab.FinalProject.Repositories;

import ibragim.project.core.bitlab.FinalProject.models.Doctor;
import ibragim.project.core.bitlab.FinalProject.models.Owner;
import ibragim.project.core.bitlab.FinalProject.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    public Doctor getDoctorByUser_Id(Long id);
    public void deleteByUser_Id(Long id);

}
