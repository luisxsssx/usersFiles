package com.example.usersFiles.repositories;

import com.example.usersFiles.models.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRespository extends CrudRepository<RoleEntity, Long> {
}
