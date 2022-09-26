package ibragim.project.core.bitlab.FinalProject.mvc.common;

import ibragim.project.core.bitlab.FinalProject.models.Doctor;
import ibragim.project.core.bitlab.FinalProject.models.Pet;
import ibragim.project.core.bitlab.FinalProject.models.User;
import ibragim.project.core.bitlab.FinalProject.services.DoctorService;
import ibragim.project.core.bitlab.FinalProject.services.PetService;
import lombok.Getter;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DoctorController {
    private final PetService petService;
    private final DoctorService doctorService;

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
    @GetMapping(value = "/doctor-patients")
    public String getPatientsOfDoctor(){
        return "client-all-patients";
    }

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
    @GetMapping(value = "/doctor-patients/{patientId}")
    public String getOnePetOfOwner(@PathVariable(name = "patientId") Long patientId){
        return "client-patient-details";
    }

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
    @PostMapping(value = "/change-doctor-information")
    public String changeDoctorInformation(@RequestParam(name = "address") String address,
                                          @RequestParam(name = "phone_number") String phoneNumber,
                                          @RequestParam(name = "specifications") String specifications){
        Doctor doctor = doctorService.getDoctorByUserId(getCurrentUser().getId());
        doctor.setAddress(address);
        doctor.setPhoneNumber(phoneNumber);
        doctor.setDescription(specifications);
        doctorService.saveDoctor(doctor);
        return "redirect:/profile";
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }


}
