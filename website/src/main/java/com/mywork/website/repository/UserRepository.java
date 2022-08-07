package com.mywork.website.repository;

import com.mywork.website.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
   // @Override

    Optional<User> findUserByEmail(String email);
}
