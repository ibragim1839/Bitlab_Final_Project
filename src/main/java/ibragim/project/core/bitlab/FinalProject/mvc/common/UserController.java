package ibragim.project.core.bitlab.FinalProject.mvc.common;

import ibragim.project.core.bitlab.FinalProject.models.User;
import ibragim.project.core.bitlab.FinalProject.services.FileUploadService;
import ibragim.project.core.bitlab.FinalProject.services.UserService;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FileUploadService fileUploadService;

    @Value("${userPicUploadUrl}")
    private String userPicUploadUrl;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/view-user-picture/{url}" , produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] viewUserPicture(@PathVariable(name = "url") String url) throws IOException {
        String picUrl = userPicUploadUrl + "default.jpg";
        if(url!=null){
            picUrl = userPicUploadUrl + url + ".jpg";
        }
        InputStream in;
        try{
            ClassPathResource classPathResource = new ClassPathResource(picUrl);
            in = classPathResource.getInputStream();
        }catch (Exception e){
            picUrl = userPicUploadUrl + "default.png";
            ClassPathResource classPathResource = new ClassPathResource(picUrl);
            in = classPathResource.getInputStream();
        }
        return IOUtils.toByteArray(in);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/upload_user_avatar")
    public String uploadUserAvatar(@RequestParam(name = "avatar") MultipartFile picture){
        if (picture.getContentType().equals("image/jpeg") || picture.getContentType().equals("image/png")){
            fileUploadService.uploadUserPicture(picture, getCurrentUser());
        }
        return "redirect:/profile";
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
