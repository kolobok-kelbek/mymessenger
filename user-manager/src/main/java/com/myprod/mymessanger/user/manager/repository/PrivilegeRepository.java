package com.myprod.mymessanger.user.manager.repository;

import com.myprod.mymessanger.user.manager.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    @Query("select t from #{#entityName} t where t.name = ?1")
    Privilege findByName(String name);
}
