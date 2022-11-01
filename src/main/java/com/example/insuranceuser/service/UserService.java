package com.example.insuranceuser.service;
import com.example.insuranceuser.dto.LoginDTO;
import com.example.insuranceuser.dto.OtpDTO;
import com.example.insuranceuser.dto.UserDTO;
import com.example.insuranceuser.exception.OTPException;
import com.example.insuranceuser.exception.UserException;
import com.example.insuranceuser.model.OTP;
import com.example.insuranceuser.model.User;
import com.example.insuranceuser.repository.OtpRepo;
import com.example.insuranceuser.repository.UserRepo;
import com.example.insuranceuser.util.EmailSenderService;
import com.example.insuranceuser.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class UserService implements IUserService{
    @Autowired
    UserRepo userRepo;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    OtpRepo otpRepo;

    @Override
    public User register(UserDTO userDTO){
        User user=new User(userDTO);
        userRepo.save(user);
        String token=tokenUtil.createToken(user.getUserId());
        emailSenderService.sendEmail(user.getEmail(),"Registered in Insurance User!", "http://localhost:8100/user/verify/"+token);
        return user;
    }
    @Override
    public User verifyUser(String token) {
        long id = tokenUtil.decodeToken(token);
        User user = userRepo.findById(id).get();
        if (userRepo.findById(id).isPresent()) {
            user.setVerified(true);
            userRepo.save(user);
            return user;
        }else {
            throw new UserException("Sorry! We can not fin user id: "+id);
        }
    }
    @Override
    public String sendOTP(String email){
        User user = userRepo.findByEmail(email);
        if (user != null && user.isVerified()) {
            OTP otplogin = new OTP();
            Random random = new Random();
            Long otp = Long.valueOf(random.nextInt(900000) + 100000);
            otplogin.setOtp(otp);
            otplogin.setEmail(email);
            otplogin.setOtpTime(LocalDateTime.now());
            otplogin.setOtpExpiredTime(LocalDateTime.now().plusMinutes(10));
            otpRepo.save(otplogin);
            emailSenderService.sendEmail(email, "OTP Login", "Here is the OTP for your login: " + otp + "\n This OTP is valid for 10 Minutes");
            return "OTP sent to the email address";
        } else
            throw new OTPException("Invalid Email Address");
    }
    @Override
    public String loginWithOTP(OtpDTO otpDTO){
        User user = userRepo.findByEmail(otpDTO.getEmail());
        OTP otplogin = otpRepo.findByEmail(otpDTO.getEmail());
        LocalDateTime checkOtpExpire = LocalDateTime.now();
        long noOfSeconds = Duration.between(otplogin.getOtpExpiredTime(), checkOtpExpire).toSeconds();
        if (user != null && otplogin != null && otplogin.getOtp()==(otpDTO.getOtp())) {
            //OTP valid for 10 minutes
            if(noOfSeconds<=600) {
                emailSenderService.sendEmail(otpDTO.getEmail(), "OTP Login", "Login Successful");
                //Deleting the OTP after successful Login
                otpRepo.delete(otplogin);
                String token = tokenUtil.createToken(user.getUserId());
                return "Login Successful\n"+token;
            }else
                //Deleting the expired OTP
                otpRepo.delete(otplogin);
            throw new OTPException("OTP expired");
        } else
            throw new OTPException("Invalid OTP");
    }
    @Override
    public String login(LoginDTO loginDTO) {
        String user=userRepo.findByLogin(loginDTO.getEmail(),loginDTO.getPassword());
        return "Login Successful!";
    }
    @Override
    public List<User> getAll(){
        List<User> list=userRepo.findAll();
        return list;
    }
    @Override
    public  List<User> getUserWithHealthCondition(String condition){
        List<User> userList=userRepo.findByHealthCondition(condition);
        return userList;
    }
    @Override
    public User getDetailsById(long id){
        User user=userRepo.findById(id).get();
        if (userRepo.findById(id).isPresent()){
            return user;
        }else {
            throw new UserException("Searched user id is not present! please check user id: "+id);
        }
    }
    @Override
    public String loginCheck(String email, String password){
        User user=userRepo.findByEmail(email);
        if(email.equals(user.getEmail()) && password.equals(user.getPassword())){
            emailSenderService.sendEmail(user.getEmail(),"Logged in Successfully!", "Hii...."+user.getFullName()+"\n\n You have successfully logged in into Book Store App!");
            return "Congratulations!! You have logged in successfully!";

        }else {
            throw new UserException("Sorry! Email or Password is incorrect!");
        }
    }
    @Override
    public User updateUserById(UserDTO userDTO, long id) {
        User user = userRepo.findById(id).get();
        if (userRepo.findById(id).isPresent()) {
            user.setFullName(userDTO.getFullName());
            user.setPermanentAddress(userDTO.getPermanentAddress());
            user.setTemporaryAddress(userDTO.getTemporaryAddress());
            user.setMobileNumber(userDTO.getMobileNumber());
            user.setAge(userDTO.getAge());
            user.setUserType(userDTO.getUserType());
            user.setOccupation(userDTO.getOccupation());
            user.setFamilyBackground(userDTO.getFamilyBackground());
            user.setKyc(userDTO.getKyc());
            user.setHealthCondition(userDTO.getHealthCondition());
            user.setVehicleData(userDTO.getVehicleData());
            user.setRegisteredDate(userDTO.getRegisteredDate());
            user.setUpdatedDate(userDTO.getUpdatedDate());
            userRepo.save(user);
            return user;
        } else {
            throw new UserException("Sorry! We can not find the user id: " + id);
        }
    }
    @Override
    public void deleteById(long id){
        if(userRepo.findById(id).isPresent()){
            userRepo.deleteById(id);
        }else {
            throw new UserException("Sorry! We can not find the user id: "+id);
        }
    }
    @Override
    public List<User> getAllUserBetweenRegisteredDate(String date1,String date2){
        List<User> userList=userRepo.findAllUsersBetweenRegisteredDate(date1,date2);
        return userList;
    }
    @Override
    public List<User> getUserWithVehicleData(String vehicle){
        List<User> userList=userRepo.findUserWithVehicleData(vehicle);
        return userList;
    }
}
