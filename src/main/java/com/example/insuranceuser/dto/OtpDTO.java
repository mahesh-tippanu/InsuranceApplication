package com.example.insuranceuser.dto;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class OtpDTO {
    private String email;
    private long otp;
}
