package com.myprod.mymessenger.user.manager.repository;

import com.myprod.mymessenger.user.manager.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  @Query("select t from #{#entityName} t where t.phone = ?1")
  Optional<User> findByPhone(final String phone);

  @Query("select t from #{#entityName} t where t.username = ?1")
  Optional<User> findByUsername(final String username);
}
