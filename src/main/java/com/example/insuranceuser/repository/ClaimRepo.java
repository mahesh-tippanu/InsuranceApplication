package com.example.insuranceuser.repository;

import com.example.insuranceuser.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepo extends JpaRepository<Claim,Long> {
    @Query(value = "select * from insurance.claim where claim.status_claimed=:status",nativeQuery = true)
    List<Claim> findByStatusClaimed(boolean status);
}
