package ibragim.project.core.bitlab.FinalProject.services;

import ibragim.project.core.bitlab.FinalProject.Repositories.BreedsRepository;
import ibragim.project.core.bitlab.FinalProject.Repositories.PetsRepository;
import ibragim.project.core.bitlab.FinalProject.Repositories.UserRepository;
import ibragim.project.core.bitlab.FinalProject.models.Breed;
import ibragim.project.core.bitlab.FinalProject.models.Pet;
import ibragim.project.core.bitlab.FinalProject.models.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final BreedsRepository breedsRepository;
    private final UserRepository userRepository;
    private final PetsRepository petsRepository;

    @Value("${breedPicUploadUrl}")
    private String breedPicUploadUrl;

    @Value("${breedPicTargetUrl}")
    private String breedPicTargetUrl;

    @Value("${userPicUploadUrl}")
    private String userPicUploadUrl;

    @Value("${userPicTargetUrl}")
    private String userPicTargetUrl;

    @Value("${petPicUploadUrl}")
    private String petPicUploadUrl;

    @Value("${petPicTargetUrl}")
    private String petPicTargetUrl;



    public boolean uploadBreedPicture(MultipartFile picture, Breed breed){
        try{
            String fileName = DigestUtils.sha1Hex(breed.getId()+"_breedPicture");
            byte bytes[] = picture.getBytes();
            Path path = Paths.get(breedPicTargetUrl+"/"+fileName+".jpg");
            Files.write(path, bytes);
            breed.setPictureUrl(fileName);
            breedsRepository.save(breed);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean uploadPetPicture(MultipartFile picture, Pet pet){
        try{
            String fileName = DigestUtils.sha1Hex(pet.getId()+"_petPicture");
            byte bytes[] = picture.getBytes();
            Path path = Paths.get(petPicTargetUrl+"/"+fileName+".jpg");
            Files.write(path, bytes);
            pet.setPicUrl(fileName);
            petsRepository.save(pet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean uploadUserPicture(MultipartFile picture, User user){
        try{
            String fileName = DigestUtils.sha1Hex(user.getId()+"_userPicture");
            byte bytes[] = picture.getBytes();
            Path path = Paths.get(userPicTargetUrl+"/"+fileName+".jpg");
            Files.write(path, bytes);
            user.setPicUrl(fileName);
            userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

}
