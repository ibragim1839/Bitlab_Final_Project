package ibragim.project.core.bitlab.FinalProject.mvc.common;

import ibragim.project.core.bitlab.FinalProject.dto.BreedDto;
import ibragim.project.core.bitlab.FinalProject.dto.PetDto;
import ibragim.project.core.bitlab.FinalProject.models.Owner;
import ibragim.project.core.bitlab.FinalProject.models.Pet;
import ibragim.project.core.bitlab.FinalProject.models.Role;
import ibragim.project.core.bitlab.FinalProject.models.User;
import ibragim.project.core.bitlab.FinalProject.services.*;
import liquibase.pro.packaged.S;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PetsController {
    private final PetService petService;
    private final BreedService breedService;
    private final OwnerService ownerService;
    private final RolesService rolesService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/all-pets-with-no-owner")
    public String getAllPetsWithNoOwner(Model model){
        List<Pet> pets = petService.getPetsWithNoOwner();
        List<BreedDto> breeds = breedService.getBreedsWithPetsAndNoOwner();
        model.addAttribute("pets", pets);
        model.addAttribute("breeds", breeds);
        return "client-all-pets-with-no-owner";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/all-pets-with-no-owner/breed/{breedId}")
    public String getPetsWithNoOwnerByBreedId(Model model,
                                              @PathVariable(name = "breedId") Long id){
        List<PetDto> pets = petService.getPetsWithNoOwnerByBreedId(id);
        List<BreedDto> breeds = breedService.getBreedsWithPetsAndNoOwner();
        model.addAttribute("pets", pets);
        model.addAttribute("breeds", breeds);
        return "client-all-pets-with-no-owner";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/all-pets-with-no-owner/{petId}")
    public String getPetWithNoOwner(Model model,
                                        @PathVariable(name = "petId") Long id){
        Pet pet = petService.getPetById(id);
        model.addAttribute("pet", pet);
        return "client-pet-with-no-owner-details";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/user-adopt-pet")
    public String setPetToUser(@RequestParam(name = "address") String address,
                               @RequestParam(name = "phone_number") String phoneNumber,
                               @RequestParam(name = "pet_id") Long pet_id){
        if (address.isEmpty() || phoneNumber.isEmpty()){
            return "redirect:/all-pets-with-no-owner/"+pet_id+"?DataError";
        }
        User user = userService.setRoleOwnerToUSerByUserId(getCurrentUser().getId());
        Owner owner = new Owner();
        owner.setPhoneNumber(phoneNumber);
        owner.setAddress(address);
        owner.setUser(user);
        owner = ownerService.saveOwner(owner);
        petService.setOwnerToPet(pet_id, owner.getId());
        return "redirect:/logout";
    }


    @PreAuthorize("hasAnyRole('ROLE_OWNER')")
    @PostMapping(value = "/owner-adopt-pet")
    public String setPetToOwner(@RequestParam(name = "pet_id") Long petId){
        Owner owner =ownerService.getOwnerByUserId(getCurrentUser().getId());
        petService.setOwnerToPet(petId, owner.getId());
        return "redirect:/owner-pets/"+petId;
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
