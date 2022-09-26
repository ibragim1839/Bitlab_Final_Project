package ibragim.project.core.bitlab.FinalProject.api;

import com.zaxxer.hikari.util.SuspendResumeLock;
import ibragim.project.core.bitlab.FinalProject.dto.*;
import ibragim.project.core.bitlab.FinalProject.mappers.CommentMapper;
import ibragim.project.core.bitlab.FinalProject.mappers.PetMapper;
import ibragim.project.core.bitlab.FinalProject.mappers.UserMapper;
import ibragim.project.core.bitlab.FinalProject.models.*;
import ibragim.project.core.bitlab.FinalProject.services.CommentService;
import ibragim.project.core.bitlab.FinalProject.services.PetService;
import liquibase.pro.packaged.S;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final PetService petService;
    private final PetMapper petMapper;
    private final UserMapper userMapper;

    @GetMapping(value = "/{petId}")
    @PreAuthorize("hasAnyRole('ROLE_OWNER','ROLE_DOCTOR')")
    public List<CommentDto> getCommentsOfPet(@PathVariable(name = "petId") Long id){
        Pet pet = petService.getPetById(id);
        User user = getCurrentUser();
        Doctor doctor = pet.getDoctor();
        Owner owner = pet.getOwner();
        List<CommentDto> commentDto = new ArrayList<>();
        if(owner!=null){
            if (user.getId() == owner.getUser().getId()){
                commentDto = commentMapper.toCommentDtoList(commentService.getCommentsOfPet(id));
            }
        }
        if(doctor!=null){
            if (user.getId() == doctor.getUser().getId()){
                commentDto = commentMapper.toCommentDtoList(commentService.getCommentsOfPet(id));
            }
        }
        return commentDto;
    }

    @PreAuthorize("hasAnyRole('ROLE_OWNER','ROLE_DOCTOR')")
    @PostMapping
    public Comment addComment(@RequestBody Comment comment){
        comment.setPet(petService.getPetById(comment.getPet().getId()));
        User user = getCurrentUser();
        comment.setAuthor(user);
        return commentService.saveComment(comment);
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
