package ibragim.project.core.bitlab.FinalProject.mvc.admin;


import ibragim.project.core.bitlab.FinalProject.mappers.*;
import ibragim.project.core.bitlab.FinalProject.models.*;
import ibragim.project.core.bitlab.FinalProject.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminHomeController {
    private final UserService userService;
    private final AnimalService animalService;
    private final BreedService breedService;
    private final PetService petService;
    private final UserMapper userMapper;
    private final PetMapper petMapper;
    private final AnimalMapper animalMapper;
    private final BreedMapper breedMapper;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin-panel")
    public String adminPanel(){
        return "admin-main-page";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/manage-users-page")
    public String manageUsersPage(Model model){
        model.addAttribute("userList", userMapper.toUserDtoList(userService.getAllUsers()));
        return "admin-users-page";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/manage-animals-page")
    public String manageAnimalsPage(Model model){
        model.addAttribute("animals", animalMapper.toAnimalDtoList(animalService.getAnimals()));
        return "admin-animals-page";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/manage-breeds-page")
    public String manageBreedsPage(Model model){
        model.addAttribute("breeds", breedService.getAllBreeds());
        model.addAttribute("animals", animalMapper.toAnimalDtoList(animalService.getAnimals()));
        return "admin-breeds-page";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/manage-pets-page")
    public String managePetsPage(Model model){
        model.addAttribute("pets", petMapper.toPetDtoList(petService.getAllPets()));
        model.addAttribute("breeds", breedService.getAllBreeds());
        return "admin-pets-page";
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
