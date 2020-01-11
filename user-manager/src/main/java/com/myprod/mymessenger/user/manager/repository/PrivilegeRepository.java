package com.myprod.mymessenger.user.manager.repository;

import com.myprod.mymessenger.user.manager.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    @Query("select t from #{#entityName} t where t.name = ?1")
    Privilege findByName(String name);
}
