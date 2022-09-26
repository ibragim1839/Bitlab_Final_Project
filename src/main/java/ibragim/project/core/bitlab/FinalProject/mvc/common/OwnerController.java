package ibragim.project.core.bitlab.FinalProject.mvc.common;

import ibragim.project.core.bitlab.FinalProject.dto.UserDto;
import ibragim.project.core.bitlab.FinalProject.mappers.OwnerMapper;
import ibragim.project.core.bitlab.FinalProject.mappers.UserMapper;
import ibragim.project.core.bitlab.FinalProject.models.Doctor;
import ibragim.project.core.bitlab.FinalProject.models.Owner;
import ibragim.project.core.bitlab.FinalProject.models.User;
import ibragim.project.core.bitlab.FinalProject.services.OwnerService;
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

@Controller
@RequiredArgsConstructor
public class OwnerController {
    private final UserMapper userMapper;
    private final OwnerMapper ownerMapper;
    private final OwnerService ownerService;

    @PreAuthorize("hasAnyRole('ROLE_OWNER')")
    @GetMapping(value = "/owner-pets")
    public String getPetsOfOwner(){
        return "client-all-pets";
    }

    @PreAuthorize("hasAnyRole('ROLE_OWNER')")
    @GetMapping(value = "/owner-pets/{petId}")
    public String getOnePetOfOwner(@PathVariable(name = "petId") Long petId){
        return "client-pet-details";
    }

    @PreAuthorize("hasAnyRole('ROLE_OWNER')")
    @PostMapping(value = "/change-owner-information")
    public String changeDoctorInformation(@RequestParam(name = "owner_address") String address,
                                          @RequestParam(name = "owner_phoneNumber") String phoneNumber){
        Owner owner = ownerService.getOwnerByUserId(getCurrentUser().getId());
        owner.setAddress(address);
        owner.setPhoneNumber(phoneNumber);
        ownerService.saveOwner(owner);
        return "redirect:/profile";
    }

    public UserDto getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User user = (User) authentication.getPrincipal();
            return userMapper.toUserDto(user);
        }
        return null;
    }
}
