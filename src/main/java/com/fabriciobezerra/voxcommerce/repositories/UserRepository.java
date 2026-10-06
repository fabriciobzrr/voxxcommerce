package com.fabriciobezerra.voxcommerce.repositories;

import com.fabriciobezerra.voxcommerce.entities.User;
import com.fabriciobezerra.voxcommerce.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //    @Query(nativeQuery = true, value = "SELECT obj FROM User obj WHERE obj.email = :email")
    @Query(nativeQuery = true, value = "SELECT tb_user.email AS username, tb_user.password, tb_role.id AS roleld, tb_role.authority\n" +
            "FROM tb_user\n" +
            "INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id\n" +
            "INNER JOIN tb_role ON tb_role.id = tb_user_role.role_id\n" +
            "WHERE tb_user.email = :email;")
    List<UserDetailsProjection> searchUserAndRoleByEmail(String email);

    User findByEmail(String email);
}
