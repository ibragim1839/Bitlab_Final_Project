package ibragim.project.core.bitlab.FinalProject.mappers;

import ibragim.project.core.bitlab.FinalProject.dto.DoctorDto;
import ibragim.project.core.bitlab.FinalProject.models.Doctor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    public List<DoctorDto> toDoctorDtoList(List<Doctor> doctorList);
    public Doctor toDoctorDto(Doctor doctor);
}
