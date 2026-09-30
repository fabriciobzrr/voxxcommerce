package com.fabriciobezerra.voxcommerce.repositories;

import com.fabriciobezerra.voxcommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT obj FROM Usuario obj WHERE obj.email = :email")
    User findByEmail(String email);
}
