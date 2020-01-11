package com.myprod.mymessanger.user.manager.repository;

import com.myprod.mymessanger.user.manager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select t from #{#entityName} t where t.name = ?1")
    Role findByName(String name);
}
