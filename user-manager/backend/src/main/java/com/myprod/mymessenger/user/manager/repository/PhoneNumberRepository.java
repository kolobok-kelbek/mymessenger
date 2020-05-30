package com.myprod.mymessenger.user.manager.repository;

import com.myprod.mymessenger.user.manager.entity.PhoneNumber;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, UUID> {}
