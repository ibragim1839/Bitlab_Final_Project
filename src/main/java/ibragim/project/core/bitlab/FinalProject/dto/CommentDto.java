package ibragim.project.core.bitlab.FinalProject.dto;

import ibragim.project.core.bitlab.FinalProject.models.Pet;
import ibragim.project.core.bitlab.FinalProject.models.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String body;
    private Timestamp postDate;
    private UserDto author;
    private PetDto pet;
}
