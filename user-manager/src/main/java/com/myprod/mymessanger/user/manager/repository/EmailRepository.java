package com.myprod.mymessanger.user.manager.repository;

import com.myprod.mymessanger.user.manager.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailRepository extends JpaRepository<Email, UUID> {
}
