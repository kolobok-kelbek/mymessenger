package com.myprod.mymessenger.user.manager.repository;

import com.myprod.mymessenger.user.manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select t from #{#entityName} t where t.phone = ?1")
    Optional<User> findByPhone(final String phone);

    @Query("select t from #{#entityName} t where t.username = ?1")
    Optional<User> findByUsername(final String username);
}
