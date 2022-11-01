package com.example.insuranceuser.service;

import com.example.insuranceuser.dto.ClaimDTO;
import com.example.insuranceuser.model.Claim;

import java.util.List;

public interface IClaimService {
    Claim register(ClaimDTO claimDTO) throws Exception;

    Claim updateById(long id, ClaimDTO claimDTO);

    void deleteById(long id);

    List<Claim> getAllInsuranceByClaimed(boolean status);

    Claim changeClaimStatus(ClaimDTO claimDTO, long id);

    List<Claim> getAllClaimedInsurance();
}
