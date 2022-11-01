package com.example.insuranceuser.dto;

import com.example.insuranceuser.model.Claim;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseClaimDTO {
    private String message;
    private Object object;
    public ResponseClaimDTO(String message, Claim claim) {
        this.message=message;
        this.object=claim;
    }

    public ResponseClaimDTO(String message, String exception) {
        this.message=message;
        this.object=exception;
    }

    public ResponseClaimDTO(String message, List<Claim> claimList) {
        this.message=message;
        this.object=claimList;
    }
}
