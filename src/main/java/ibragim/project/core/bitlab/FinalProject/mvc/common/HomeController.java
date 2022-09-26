package ibragim.project.core.bitlab.FinalProject.mvc.common;

import ibragim.project.core.bitlab.FinalProject.mappers.DoctorMapper;
import ibragim.project.core.bitlab.FinalProject.mappers.OwnerMapper;
import ibragim.project.core.bitlab.FinalProject.mappers.UserMapper;
import ibragim.project.core.bitlab.FinalProject.models.Doctor;
import ibragim.project.core.bitlab.FinalProject.models.Role;
import ibragim.project.core.bitlab.FinalProject.models.User;
import ibragim.project.core.bitlab.FinalProject.services.DoctorService;
import ibragim.project.core.bitlab.FinalProject.services.OwnerService;
import ibragim.project.core.bitlab.FinalProject.services.RolesService;
import ibragim.project.core.bitlab.FinalProject.services.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final RolesService rolesService;
    private final UserMapper userMapper;
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    @GetMapping(value = "/enter")
    public String getEnterPage(){
        return "enter-page";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public String getProfilePage(Model model){
        User user = userService.getUserById(getCurrentUser().getId());
        model.addAttribute("user", userMapper.toUserDto(user));
        Role roleDoctor = rolesService.getRoleByRoleName("ROLE_DOCTOR");
        Role roleOwner = rolesService.getRoleByRoleName("ROLE_OWNER");
        if (user.getRoles().contains(roleDoctor)){
            model.addAttribute("doctor",
                    doctorMapper.toDoctorDto(doctorService.getDoctorByUserId(getCurrentUser().getId())));
        }
        if (user.getRoles().contains(roleOwner)){
            model.addAttribute("owner",
                    ownerMapper.toOwnerDto(ownerService.getOwnerByUserId(getCurrentUser().getId())));
        }
        return "client-profile";
    }

    @GetMapping(value = "/registration")
    public String getRegistrationPage(){
        return "registration-page";
    }

    @GetMapping(value = "/")
    public String getMainPage(){
        return "main-page";
    }


    @PostMapping(value = "/user-registration")
    public String registerNewUser(@RequestParam(name = "user_fullName") String fullName,
                                  @RequestParam(name = "user_email") String email,
                                  @RequestParam(name = "user_password") String password,
                                  @RequestParam(name = "user_password1") String password1){
        if(fullName!=null && email!=null && password!=null && password1!=null){
            if(password.equals(password1)){
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setPassword(password);
                newUser.setFullName(fullName);
                userService.registerNewUser(newUser);
                return "redirect:/enter?registrationSuccess";
            }
            else{ return "redirect:/registration?passwordError";}
        }
        else{
            return "redirect:/registration?requestError";
        }
    }


    @GetMapping(value = "/view-logo" , produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] viewUserPicture() throws IOException {
        String picUrl = "static/project_pictures/logo.jpg";
        InputStream in;
        try{
            ClassPathResource classPathResource = new ClassPathResource(picUrl);
            in = classPathResource.getInputStream();
        }catch (Exception e){
            picUrl = "static/project_pictures/logo.jpg";
            ClassPathResource classPathResource = new ClassPathResource(picUrl);
            in = classPathResource.getInputStream();
        }
        return IOUtils.toByteArray(in);
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
