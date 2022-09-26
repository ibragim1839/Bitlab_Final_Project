package ibragim.project.core.bitlab.FinalProject.api;

import ibragim.project.core.bitlab.FinalProject.dto.PetDto;
import ibragim.project.core.bitlab.FinalProject.mappers.PetMapper;
import ibragim.project.core.bitlab.FinalProject.models.Pet;
import ibragim.project.core.bitlab.FinalProject.models.User;
import ibragim.project.core.bitlab.FinalProject.services.DoctorService;
import ibragim.project.core.bitlab.FinalProject.services.PetService;
import ibragim.project.core.bitlab.FinalProject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/patients")
@CrossOrigin
@RequiredArgsConstructor
public class PatientController {
    private final PetService petService;
    private final PetMapper petMapper;
    private final UserService userService;
    private final DoctorService doctorService;


    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
    public List<PetDto> getPatientsOfUser(){
        Long id = doctorService.getDoctorByUserId(Objects.requireNonNull(getCurrentUser()).getId()).getId();
        List<PetDto> patients = petMapper.toPetDtoList(petService.getPetsByDoctor_Id(id));
        return patients;
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
    public PetDto getOnePatient(@PathVariable(name = "id") Long id){
        Pet pet = petService.getPetById(id);
        if (pet!=null && pet.getDoctor()!=null){
            if (pet.getDoctor().getUser().getId() == getCurrentUser().getId()){
                return petMapper.toPetDto(pet);
            }
        }
        return null;
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }
}
