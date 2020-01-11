package com.myprod.mymessanger.user.manager.repository;

import com.myprod.mymessanger.user.manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select t from #{#entityName} t where t.email = ?1")
    User findByEmail(String email);

    @Query("select t from #{#entityName} t where t.uuid = ?1")
    User findByUuid(UUID uuid);

}

