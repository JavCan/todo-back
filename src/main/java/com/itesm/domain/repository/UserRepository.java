package com.itesm.domain.repository;


import com.itesm.domain.models.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByFirebaseUuid(String firebaseUuid);
    Optional<User> findByEmail(String email);
    User create(User user);
}
