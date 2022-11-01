package com.example.insuranceuser.repository;

import com.example.insuranceuser.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepo extends JpaRepository<OTP,Long> {
    @Query(value = "select * from insurance.otp where otp.email=:email",nativeQuery = true)
    OTP findByEmail(String email);
}
