package ibragim.project.core.bitlab.FinalProject.services;

import ibragim.project.core.bitlab.FinalProject.Repositories.OwnerRepository;
import ibragim.project.core.bitlab.FinalProject.models.Owner;
import ibragim.project.core.bitlab.FinalProject.models.Pet;
import ibragim.project.core.bitlab.FinalProject.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService extends UserService {
    @Autowired
    private OwnerRepository ownerRepository;

    public Owner saveOwner(Owner owner){
        return ownerRepository.save(owner);
    }

    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id).orElse(null);
    }
    public Owner getOwnerByUserId(Long id) {
        return ownerRepository.getOwnerByUser_Id(id);
    }
    public void deleteOwnerByUserId(Long id){
        ownerRepository.deleteByUser_Id(id);
    }
    public List<Owner> getAllOwners(){
        return ownerRepository.findAll();
    }

}
