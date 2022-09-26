package ibragim.project.core.bitlab.FinalProject.mvc.admin;

import ibragim.project.core.bitlab.FinalProject.dto.AnimalDto;
import ibragim.project.core.bitlab.FinalProject.models.*;
import ibragim.project.core.bitlab.FinalProject.services.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class AdminBreedsController {
    private final AnimalService animalService;
    private final BreedService breedService;
    private final FileUploadService fileUploadService;
    private final PetService petService;

    @Value("${breedPicUploadUrl}")
    private String breedPicUploadUrl;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/add-new-breed")
    public String addNewBreed(@RequestParam(name = "breed_name") String name,
                              @RequestParam(name = "breed_specifications") String specifications,
                              @RequestParam(name = "breed_animal") Long animalId,
                              @RequestParam(name = "breed_picture")MultipartFile picture){
        Animal animal = animalService.getAnimal(animalId);
        Breed breed = new Breed();
        if (animal!=null){
            breed.setAnimal(animal);
        }
        breed.setName(name);
        breed.setSpecifications(specifications);
        breed = breedService.saveBreed(breed);
        if (picture.getContentType().equals("image/jpeg") || picture.getContentType().equals("image/png")){
            fileUploadService.uploadBreedPicture(picture, breed);
        }
        return "redirect:/manage-breeds-page";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/change-breed")
    public String addNewBreed(@RequestParam(name = "breed_name") String name,
                              @RequestParam(name = "breed_specifications") String specifications,
                              @RequestParam(name = "breed_animal") Long animalId,
                              @RequestParam(name = "breed_picture")MultipartFile picture,
                              @RequestParam(name = "breed_id") Long breedId){
        Animal animal = animalService.getAnimal(animalId);
        Breed breed = breedService.getBreedById(breedId);
        if (breed!=null){
            breed.setAnimal(animal);
            breed.setName(name);
            breed.setSpecifications(specifications);
            breedService.saveBreed(breed);
            if (picture.getContentType().equals("image/jpeg") || picture.getContentType().equals("image/png")){
                fileUploadService.uploadBreedPicture(picture, breed);
            }
        }
        return "redirect:/manage-breeds-page";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/delete-breed")
    public String deleteBreed(@RequestParam(name = "breed_id") Long id){
        Breed breed = breedService.getBreedById(id);
        if (breed!=null){
            petService.deletePetsByBreed(breed);
            breedService.deleteBreed(breed);
        }
        return "redirect:/manage-breeds-page";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/view-breed-picture/{url}" , produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] viewBreedPicture(@PathVariable(name = "url") String url) throws IOException {
        String picUrl = breedPicUploadUrl + "default.jpg";
        if(url!=null){
            picUrl = breedPicUploadUrl + url + ".jpg";
        }
        InputStream in;
        try{
            ClassPathResource classPathResource = new ClassPathResource(picUrl);
            in = classPathResource.getInputStream();
        }catch (Exception e){
            picUrl = breedPicUploadUrl + "default.png";
            ClassPathResource classPathResource = new ClassPathResource(picUrl);
            in = classPathResource.getInputStream();
        }
        return IOUtils.toByteArray(in);
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
