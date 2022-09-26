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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class AdminUsersController {
    private final UserService userService;
    private final RolesService rolesService;
    private final OwnerService ownerService;
    private final DoctorService doctorService;
    private final PetService petService;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PetMapper petMapper;
    private final OwnerMapper ownerMapper;
    private final DoctorMapper doctorMapper;
    private final CommentService commentService;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin-user-details/{UserId}")
    public String detailsOfUser(@PathVariable(name = "UserId") Long id,
                                Model model) {
        User theUser = userService.getUserById(id);
        if (theUser != null) {
            model.addAttribute("user", userMapper.toUserDto(theUser));
            List<Role> unChosenRoles = rolesService.getAllRoles();
            unChosenRoles.removeAll(theUser.getRoles());
            Role roleOwner = rolesService.getRoleByRoleName("ROLE_OWNER");
            Role roleDoctor = rolesService.getRoleByRoleName("ROLE_DOCTOR");
            if (theUser.getRoles().contains(roleDoctor)){
                Doctor doctor = doctorService.getDoctorByUserId(theUser.getId());
                if (doctor!=null){
                    model.addAttribute("doctor", doctorMapper.toDoctorDto(doctor));
                    model.addAttribute("petsOfDoctor", petMapper.toPetDtoList
                            (petService.getPetsByDoctor_Id(doctor.getId())));
                }
            }
            if (theUser.getRoles().contains(roleOwner)){
                Owner owner = ownerService.getOwnerByUserId(theUser.getId());
                if (owner!=null){
                    model.addAttribute("owner", ownerMapper.toOwnerDto(owner));
                    model.addAttribute("petsOfOwner", petMapper.toPetDtoList
                            (petService.getPetsByOwner_Id(owner.getId())));
                }
            }
            model.addAttribute("unChosenRoles", roleMapper.toRoleDtoList(unChosenRoles));
            return "admin-user-details-page";
        }
        return "redirect:/user-error-page";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/add-role-to-user")
    public String addRoleToUser(@RequestParam(name = "roleId") Long roleId,
                                @RequestParam(name = "userId") Long userId) {
        userService.addRoleToUser(roleId, userId);
        return "redirect:/admin-user-details/"+userId;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/delete-role-from-user")
    public String deleteRoleFromUser(@RequestParam(name = "role_id") Long roleId,
                                     @RequestParam(name = "user_id") Long userId){
        userService.deleteRoleFromUser(roleId, userId);
        try {
            return "redirect:/admin-user-details/"+userId;
        }
        catch (Exception e){
            return "redirect:/user-error";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/change-doctor-details")
    public String changeDoctorDetails(@RequestParam(name = "user_id") Long user_id,
                                      @RequestParam(name = "doctor_id") Long doctor_id,
                                      @RequestParam(name = "doctor_address") String address,
                                      @RequestParam(name = "doctor_phoneNumber") String phoneNumber,
                                      @RequestParam(name = "doctor_description") String description){
        Doctor doctor  = doctorService.getDoctorById(doctor_id);
        if (doctor!=null){
            doctor.setAddress(address);
            doctor.setPhoneNumber(phoneNumber);
            doctor.setDescription(description);
            doctorService.saveDoctor(doctor);
        }
        return "redirect:/admin-user-details/"+user_id;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/change-owner-details")
    public String changeOwnerDetails(@RequestParam(name = "user_id") Long user_id,
                                     @RequestParam(name = "owner_id") Long owner_id,
                                     @RequestParam(name = "owner_address") String address,
                                     @RequestParam(name = "owner_phoneNumber") String phoneNumber){
        Owner owner  = ownerService.getOwnerById(owner_id);
        if (owner!=null){
            owner.setAddress(address);
            owner.setPhoneNumber(phoneNumber);
            ownerService.saveOwner(owner);
        }
        return "redirect:/admin-user-details/"+user_id;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/remove-pet-from-doctor")
    public String removePetFromDoctor(@RequestParam(name = "userId") Long userId,
                                      @RequestParam(name = "petId") Long petId){
        Doctor doctor = doctorService.getDoctorByUserId(userId);
        Pet pet = petService.getPetById(petId);
        if (pet!=null && doctor!=null){
            commentService.deleteCommentsByPetId(petId);
            petService.removePetFromDoctorByPetId(pet.getId());
        }
        return "redirect:/admin-user-details/"+userId;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OWNER')")
    @PostMapping(value = "/remove-pet-from-owner")
    public String removePetFromOwner(@RequestParam(name = "userId") Long userId,
                                      @RequestParam(name = "petId") Long petId){
        Owner owner = ownerService.getOwnerByUserId(userId);
        Pet pet = petService.getPetById(petId);
        if (pet!=null && owner!=null){
            if (userService.getUserById(Objects.requireNonNull(getCurrentUser()).getId()).getRoles().contains(rolesService.getRoleByRoleName("ROLE_ADMIN")) ||
                    pet.getOwner().getUser().getId() == getCurrentUser().getId()){
                commentService.deleteCommentsByPetId(petId);
                petService.removePetFromOwnerByPetId(pet.getId());
            }
        }
        return "redirect:/admin-user-details/"+userId;
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

