package com.scm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

   Optional<User> findUserByEmail(String email);
   Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmailToken(String id);
}
