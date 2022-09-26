package ibragim.project.core.bitlab.FinalProject.mvc.admin;


import ibragim.project.core.bitlab.FinalProject.mappers.*;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminPetsController {
    private final OwnerService ownerService;
    private final DoctorService doctorService;
    private final PetService petService;
    private final BreedService breedService;
    private final PetMapper petMapper;
    private final OwnerMapper ownerMapper;
    private final DoctorMapper doctorMapper;
    private final BreedMapper breedMapper;
    private final FileUploadService fileUploadService;
    private final CommentService commentService;

    @Value("${petPicUploadUrl}")
    private String petPicUploadUrl;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/add-new-pet")
    public String addNewPet(@RequestParam(name = "pet_name") String name,
                            @RequestParam(name = "pet_description") String description,
                            @RequestParam(name = "pet_breed_animal") Long breedId,
                            @RequestParam(name = "pet_picture")MultipartFile picture){
        Breed breed = breedService.getBreedById(breedId);
        Pet pet = new Pet();
        pet.setName(name);
        pet.setDescription(description);
        if (breed!=null){
            pet.setBreed(breed);
        }
        if (picture.getContentType().equals("image/jpeg") || picture.getContentType().equals("image/png")){
            fileUploadService.uploadPetPicture(picture, pet);
        }
        petService.savePet(pet);
        return "redirect:/manage-pets-page";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/change-pet-picture")
    public String addNewPet(@RequestParam(name = "pet_id") Long id,
                            @RequestParam(name = "pet_picture") MultipartFile picture){
        Pet pet =petService.getPetById(id);
        if (picture.getContentType().equals("image/jpeg") || picture.getContentType().equals("image/png")){
            fileUploadService.uploadPetPicture(picture, pet);
        }
        return "redirect:/admin-pet-details/"+id;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/change-pet-information")
    public String ChangePetInformation(@RequestParam(name = "pet_name") String name,
                            @RequestParam(name = "pet_description") String description,
                            @RequestParam(name = "pet_breed_animal") Long breedId,
                            @RequestParam(name = "pet_id") Long id){
        Breed breed = breedService.getBreedById(breedId);
        Pet pet = petService.getPetById(id);
        pet.setName(name);
        pet.setDescription(description);
        if (breed!=null){
            pet.setBreed(breed);
        }
        petService.savePet(pet);
        return "redirect:/admin-pet-details/"+id;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin-pet-details/{petId}")
    public String detailsOfPet(@PathVariable(name = "petId") Long petId,
                               Model model){
        Pet pet = petService.getPetById(petId);
        List<Doctor> doctorList = doctorService.getAllDoctors();
        List<Owner> ownerList = ownerService.getAllOwners();
        model.addAttribute("breeds", breedService.getAllBreeds());
        if (doctorList!=null){
            model.addAttribute("doctors", doctorMapper.toDoctorDtoList(doctorList));
        }
        if (ownerList!=null){
            model.addAttribute("owners",ownerMapper.toOwnerDtoList(ownerList));
        }
        if (pet!=null){
            model.addAttribute("pet", petMapper.toPetDto(pet));
            return "admin-pet-details";
        }
        else {
            return "redirect:/petError";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/set-doctor-to-pet")
    public String setDoctorToPet(@RequestParam(name = "pet_id") Long petId,
                                 @RequestParam(name = "doctor_id") Long doctorId){
        commentService.deleteCommentsByPetId(petId);
        doctorService.getDoctorById(doctorId);
        petService.setDoctorToPet(petId, doctorId);
        return "redirect:/admin-pet-details/"+petId;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/set-owner-to-pet")
    public String setOwnerToPet(@RequestParam(name = "pet_id") Long petId,
                                 @RequestParam(name = "owner_id") Long ownerId){
        petService.setOwnerToPet(petId, ownerId);
        return "redirect:/admin-pet-details/"+petId;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/delete-owner-from-pet")
    public String deleteOwnerFromPet(@RequestParam(name = "pet_id")Long petId){
        commentService.deleteCommentsByPetId(petId);
        petService.deleteOwnerFromPet(petId);
        return "redirect:/admin-pet-details/"+petId;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/delete-pet")
    public String deleteAnimal(@RequestParam(name = "pet_id") Long id){
        Pet pet = petService.getPetById(id);
        petService.deletePet(pet);
        return "redirect:/manage-pets-page";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/view-pet-picture/{url}" , produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] viewPetPicture(@PathVariable(name = "url") String url) throws IOException {
        String picUrl = petPicUploadUrl + "default.jpg";
        if(url!=null){
            picUrl = petPicUploadUrl + url + ".jpg";
        }
        InputStream in;
        try{
            ClassPathResource classPathResource = new ClassPathResource(picUrl);
            in = classPathResource.getInputStream();
        }catch (Exception e){
            picUrl = petPicUploadUrl + "default.png";
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
