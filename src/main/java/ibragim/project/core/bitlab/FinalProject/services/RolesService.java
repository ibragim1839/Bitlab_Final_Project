package ibragim.project.core.bitlab.FinalProject.services;

import ibragim.project.core.bitlab.FinalProject.Repositories.RolesRepository;
import ibragim.project.core.bitlab.FinalProject.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolesService {
    private final RolesRepository rolesRepository;

    public List<Role> getAllRoles(){
        return rolesRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return rolesRepository.findById(id).orElse(null);
    }

    public Role getRoleByRoleName(String name){
        return rolesRepository.findRoleByRoleName(name);
    }
}
