package com.example.usersFiles.controller;

import com.example.usersFiles.controller.request.CreateUserDTO;
import com.example.usersFiles.models.ERole;
import com.example.usersFiles.models.RoleEntity;
import com.example.usersFiles.models.UserEntity;
import com.example.usersFiles.repositories.UserRespository;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class PrincipalController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRespository userRespository;

    @GetMapping("/hi")
    public String hello() {
        return "Luis Carlos";
    }


    // Crear un usuario
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {

        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .fullName(createUserDTO.getFullname())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();

        userRespository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    // Eliminar el usuario por medio del Id
    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id) {
        userRespository.deleteById(Long.parseLong(id));
        return "Se ha borrado el usuario con id".concat(id);
    }

    // Ver todos los ususarios
    @GetMapping("/allUsers")
    public ResponseEntity<Iterable<UserEntity>> getAllUsers() {
        Iterable<UserEntity> userEntity = userRespository.findAll();
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    // Ver los usuarios por medio del Id
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUsersById(@PathVariable Long id) {
        Optional<UserEntity> user = userRespository.findById(id);

        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}