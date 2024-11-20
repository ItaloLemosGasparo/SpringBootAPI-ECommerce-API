package com.italo.ecommerce_api.repositories;

import com.italo.ecommerce_api.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNameContainingIgnoreCase(String name);

    List<User> findAllByNameContainingIgnoreCase(String name);
}
