package ibragim.project.core.bitlab.FinalProject.api;

import ibragim.project.core.bitlab.FinalProject.dto.PetDto;
import ibragim.project.core.bitlab.FinalProject.mappers.PetMapper;
import ibragim.project.core.bitlab.FinalProject.models.Pet;
import ibragim.project.core.bitlab.FinalProject.models.User;
import ibragim.project.core.bitlab.FinalProject.services.DoctorService;
import ibragim.project.core.bitlab.FinalProject.services.OwnerService;
import ibragim.project.core.bitlab.FinalProject.services.PetService;
import ibragim.project.core.bitlab.FinalProject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;
    private final PetMapper petMapper;
    private final UserService userService;
    private final OwnerService ownerService;

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_OWNER')")
    public List<PetDto> getPetsOfUser(){
        Long id = ownerService.getOwnerByUserId(Objects.requireNonNull(getCurrentUser()).getId()).getId();
        List<PetDto> pets = petMapper.toPetDtoList(petService.getPetsByOwner_Id(id));
        return pets;
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ROLE_OWNER')")
    public PetDto getAPetOfUser(@PathVariable(name = "id") Long id){
        Pet pet = petService.getPetById(id);
        if (pet!=null && pet.getOwner()!=null){
            if (pet.getOwner().getUser().getId() == getCurrentUser().getId()){
                return petMapper.toPetDto(pet);
            }
        }
        return null;
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }
}
