package ibragim.project.core.bitlab.FinalProject.services;

import ibragim.project.core.bitlab.FinalProject.Repositories.*;
import ibragim.project.core.bitlab.FinalProject.models.Doctor;
import ibragim.project.core.bitlab.FinalProject.models.Owner;
import ibragim.project.core.bitlab.FinalProject.models.Role;
import ibragim.project.core.bitlab.FinalProject.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PetsRepository petsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByEmail(username);
    }

    public User addNewUser(User user){
        return userRepository.save(user);
    }

    public User registerNewUser(User newUser){
        List<Role> roles = new ArrayList<>();
        Role role = rolesRepository.findRoleByRoleName("ROLE_USER");
        roles.add(role);
        newUser.setRoles(roles);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(User user){userRepository.delete(user);}

    public User deleteRoleFromUser(Long roleId, Long userId){
        User user = getUserById(userId);
        Role role = rolesRepository.findById(roleId).orElse(null);
        if(user!=null && role!=null){
            if(role.getName().equals("ROLE_OWNER")){
                Owner owner = ownerRepository.getOwnerByUser_Id(userId);
                if(owner!=null){
                    List<Role> roles = user.getRoles();
                    roles.remove(rolesRepository.findRoleByRoleName(role.getName()));
                    user.setRoles(roles);
                    saveUser(user);
                    petsRepository.deleteOwnerFromPetByOwnerId(owner.getId());
                    ownerRepository.deleteByUser_Id(userId);
                }
            }
            else if(role.getName().equals("ROLE_DOCTOR")){
                Doctor doctor = doctorRepository.getDoctorByUser_Id(userId);
                if (doctor!=null){
                    List<Role> roles = user.getRoles();
                    roles.remove(rolesRepository.findRoleByRoleName(role.getName()));
                    user.setRoles(roles);
                    saveUser(user);
                    petsRepository.deleteDoctorFromPetByDoctorId(doctor.getId());
                    doctorRepository.deleteByUser_Id(userId);
                }
            }
            else if(role.getName().equals("ROLE_ADMIN")){
                List<Role> roles = user.getRoles();
                roles.remove(rolesRepository.findRoleByRoleName(role.getName()));
                user.setRoles(roles);
                saveUser(user);
            }
        }
        return user;
    }

    public User addRoleToUser(Long roleId, Long userId) {
        User user = getUserById(userId);
        Role role = rolesRepository.findById(roleId).orElse(null);

        if (user != null && role != null) {
            if (role.getName().equals("ROLE_OWNER")) {
                Owner owner = new Owner();
                owner.setUser(user);
                List<Role> roles = user.getRoles();
                if (roles.contains(rolesRepository.findRoleByRoleName("ROLE_DOCTOR"))){
                    owner.setPhoneNumber(doctorRepository.getDoctorByUser_Id(userId).getPhoneNumber());
                    owner.setAddress(doctorRepository.getDoctorByUser_Id(userId).getAddress());
                }
                ownerRepository.save(owner);
                roles.add(rolesRepository.findRoleByRoleName("ROLE_OWNER"));
                user.setRoles(roles);
                return saveUser(user);

            } else if (role.getName().equals("ROLE_DOCTOR")) {
                Doctor doctor = new Doctor();
                doctor.setUser(user);
                List<Role> roles = user.getRoles();
                if (roles.contains(rolesRepository.findRoleByRoleName("ROLE_OWNER"))){
                    doctor.setPhoneNumber(ownerRepository.getOwnerByUser_Id(userId).getPhoneNumber());
                    doctor.setAddress(ownerRepository.getOwnerByUser_Id(userId).getAddress());
                }
                doctorRepository.save(doctor);
                roles.add(rolesRepository.findRoleByRoleName("ROLE_DOCTOR"));
                user.setRoles(roles);
                return saveUser(user);

            } else if (role.getName().equals("ROLE_ADMIN")) {
                List<Role> roles = user.getRoles();
                roles.add(rolesRepository.findRoleByRoleName("ROLE_ADMIN"));
                user.setRoles(roles);
                return saveUser(user);
            }
        }
        return null;
    }

    public User setRoleOwnerToUSerByUserId(Long id){
        User user = getUserById(id);
        List<Role> roles = user.getRoles();
        roles.add(rolesRepository.findRoleByRoleName("ROLE_OWNER"));
        user.setRoles(roles);
        return saveUser(user);
    }
}
