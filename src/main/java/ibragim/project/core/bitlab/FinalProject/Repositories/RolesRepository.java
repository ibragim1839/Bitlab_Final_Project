package ibragim.project.core.bitlab.FinalProject.Repositories;

import ibragim.project.core.bitlab.FinalProject.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RolesRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.name = ?1")
    public Role findRoleByRoleName(String name);
}
