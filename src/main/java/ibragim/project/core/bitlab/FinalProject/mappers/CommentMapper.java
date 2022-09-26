package ibragim.project.core.bitlab.FinalProject.mappers;

import ibragim.project.core.bitlab.FinalProject.dto.CommentDto;
import ibragim.project.core.bitlab.FinalProject.models.Comment;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    public List<CommentDto> toCommentDtoList(List<Comment> commentList);
    public CommentDto toCommentDto(Comment comment);

}
