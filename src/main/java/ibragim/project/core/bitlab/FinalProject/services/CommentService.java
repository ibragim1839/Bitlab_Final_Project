package ibragim.project.core.bitlab.FinalProject.services;

import ibragim.project.core.bitlab.FinalProject.Repositories.CommentRepository;
import ibragim.project.core.bitlab.FinalProject.Repositories.PetsRepository;
import ibragim.project.core.bitlab.FinalProject.Repositories.UserRepository;
import ibragim.project.core.bitlab.FinalProject.models.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PetsRepository petsRepository;

    public List<Comment> getCommentsOfPet(Long petId){
        return commentRepository.findCommentsByPet_IdOrderByPostDate(petId);
    }

    public Comment saveComment(Comment comment){
        if (comment.getPet().getDoctor()!=null){
            if(comment.getPet().getDoctor().getUser().getId() == comment.getAuthor().getId()){
                return commentRepository.save(comment);
            }
        }
        if (comment.getPet().getOwner()!=null){
            if(comment.getPet().getOwner().getUser().getId() == comment.getAuthor().getId()){
                return commentRepository.save(comment);
            }
        }
        return null;
    }

    public void deleteCommentsByPetId(Long id){
        commentRepository.deleteCommentsByPet_Id(id);
    }

    public Comment getCommentById(Long id){
        return commentRepository.findById(id).orElse(null);
    }


}
