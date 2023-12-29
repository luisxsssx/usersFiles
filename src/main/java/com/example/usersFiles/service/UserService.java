package com.example.usersFiles.service;

import com.example.usersFiles.models.UserEntity;
import com.example.usersFiles.repositories.UserRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UserService {

    @Autowired
    private UserRespository userRespository;

    // Method to update user data
    public UserEntity updateUser(Long id, UserEntity updateUser) {
        UserEntity userEntity = userRespository.findById(id).orElse(null);

        if(userEntity == null) {
            return null;
        }

        userEntity.setUsername(updateUser.getUsername());
        userEntity.setFullName(updateUser.getFullName());
        userEntity.setEmail(updateUser.getEmail());
        userEntity.setRoles(updateUser.getRoles());
        userEntity.setPassword(updateUser.getPassword());

        return updateUser;
    }

}