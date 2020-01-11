package com.myprod.mymessenger.user.manager.repository;

import com.myprod.mymessenger.user.manager.entity.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, UUID> {

}
