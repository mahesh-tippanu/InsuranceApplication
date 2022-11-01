package com.example.insuranceuser.service;

import com.example.insuranceuser.dto.ClaimDTO;
import com.example.insuranceuser.exception.ClaimException;
import com.example.insuranceuser.model.Category;
import com.example.insuranceuser.model.Claim;
import com.example.insuranceuser.repository.CategoryRepo;
import com.example.insuranceuser.repository.ClaimRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaimService implements IClaimService{
    @Autowired
    ClaimRepo claimRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Override
    public Claim register(ClaimDTO claimDTO) throws Exception {
        Optional<Category> category = categoryRepo.findById(claimDTO.getCategoryId());
        if (category.isPresent()) {
            Claim claim = new Claim(claimDTO.getFullName(), category.get(),category.get(), claimDTO.getStatusClaimed());
            claimRepo.save(claim);
            return claim;
        } else {
            throw new ClaimException("Sorry! category id is not present! Please check and try again!");
        }
    }
    @Override
    public  Claim updateById(long id, ClaimDTO claimDTO){
        Optional<Category> category=categoryRepo.findById(claimDTO.getCategoryId());
        Claim claim=claimRepo.findById(id).get();
        if (category.isPresent() && claimRepo.findById(id).isPresent()){
            claim.setFullName(claimDTO.getFullName());
            claim.setCategoryId(category.get());
            claim.setInsuranceName(category.get());
            claim.setStatusClaimed(claimDTO.getStatusClaimed());
            claimRepo.save(claim);
            return claim;
        }else {
            throw new ClaimException("Sorry! We can not find category id or claim id! Please check and try again!");
        }
    }
    @Override
    public void deleteById(long id){
        if(claimRepo.findById(id).isPresent()){
            claimRepo.deleteById(id);
        }else {
            throw new ClaimException("Sorry! We can not find claim id: "+id);
        }
    }
    @Override
    public List<Claim> getAllInsuranceByClaimed(boolean status){
        List<Claim> claimList=claimRepo.findByStatusClaimed(status);
        return claimList;
    }
    @Override
    public Claim changeClaimStatus(ClaimDTO claimDTO, long id){
        Claim claim=claimRepo.findById(id).get();
        if(claimRepo.findById(id).isPresent()){
            claim.setStatusClaimed(claimDTO.getStatusClaimed());
            claimRepo.save(claim);
            return claim;
        }else {
            throw new ClaimException("Sorry! We can not find claim id: "+id+" Please check and try again!");
        }
    }
    @Override
    public List<Claim> getAllClaimedInsurance(){
        List<Claim> claimList=claimRepo.findAll();
        return claimList;
    }
}
