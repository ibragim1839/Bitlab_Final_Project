package ibragim.project.core.bitlab.FinalProject.mvc.admin;

import ibragim.project.core.bitlab.FinalProject.models.*;
import ibragim.project.core.bitlab.FinalProject.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminAnimalController {
    private final AnimalService animalService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/change-animal")
    public String changeAnimal(@RequestParam(name = "animal_name") String name,
                               @RequestParam(name = "animal_specifications") String specifications,
                               @RequestParam(name = "animal_id") Long id){
        Animal animal = new Animal();
        animal = animalService.getAnimal(id);
        if (animal!=null){
            animal.setName(name);
            animal.setSpecifications(specifications);
            animalService.saveAnimal(animal);
        }
        return "redirect:/manage-animals-page";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/delete-animal")
    public String deleteAnimal(@RequestParam(name = "animal_id") Long id){
        Animal animal =animalService.getAnimal(id);
        if (animal!=null){
            animalService.deleteAnimal(animal);
        }
        return "redirect:/manage-animals-page";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/add-new-animal")
    public String changeAnimal(@RequestParam(name = "animal_name") String name,
                               @RequestParam(name = "animal_specifications") String specifications){
        Animal animal = new Animal();
        animal.setName(name);
        animal.setSpecifications(specifications);
        animalService.saveAnimal(animal);
        return "redirect:/manage-animals-page";
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
