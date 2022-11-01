package com.example.insuranceuser.service;

import com.example.insuranceuser.dto.LoginDTO;
import com.example.insuranceuser.dto.OtpDTO;
import com.example.insuranceuser.dto.UserDTO;
import com.example.insuranceuser.model.User;

import java.time.LocalDate;
import java.util.List;

public interface IUserService {
    User register(UserDTO userDTO);

    List<User> getAll();

    List<User> getUserWithHealthCondition(String condition);

    User getDetailsById(long id);

    User updateUserById(UserDTO userDTO, long id);

    void deleteById(long id);

    List<User> getAllUserBetweenRegisteredDate(String date1,String date2);

    List<User> getUserWithVehicleData(String vehicle);

    String loginCheck(String email, String password);

    User verifyUser(String token);

    String sendOTP(String email);

    String loginWithOTP(OtpDTO otpDTO);

    String login(LoginDTO loginDTO);
}
