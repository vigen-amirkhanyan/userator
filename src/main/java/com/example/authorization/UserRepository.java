package com.example.authorization;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity findUserByEmail(String email);
    UserEntity findUserByaccessToken(String accessToken);
}
