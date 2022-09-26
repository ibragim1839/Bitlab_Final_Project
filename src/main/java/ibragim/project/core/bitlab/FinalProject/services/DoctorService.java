package ibragim.project.core.bitlab.FinalProject.services;

import ibragim.project.core.bitlab.FinalProject.Repositories.DoctorRepository;
import ibragim.project.core.bitlab.FinalProject.models.Doctor;
import ibragim.project.core.bitlab.FinalProject.models.Owner;
import ibragim.project.core.bitlab.FinalProject.models.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor saveDoctor(Doctor doctor){
        return doctorRepository.save(doctor);
    }
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }
    public Doctor getDoctorByUserId(Long id) {
        return doctorRepository.getDoctorByUser_Id(id);
    }
    public void deleteDoctorByUserId(Long id){
        doctorRepository.deleteByUser_Id(id);
    }
    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }
}
