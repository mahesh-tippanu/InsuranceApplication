package com.example.insuranceuser.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "insure")
public class Create {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long insuranceId;
    @ManyToOne
    @JoinColumn
    private Category categoryId;
    @ManyToOne
    @JoinColumn
    private User userId;
    @OneToOne
    @JoinColumn
    private User fullName;
    @ManyToOne
    @JoinColumn
    private Category insuranceName;
    private String status;
    private long monthPeriod;
    private String registeredDate;
    private String updatedDate;
    private boolean claimed;
    private boolean update;

    public Create(Category categoryId,User userId,User fullName,Category insuranceName,String status,long monthPeriod,String registeredDate,String updatedDate,boolean claimed,boolean update) {
       this.categoryId=categoryId;
       this.userId=userId;
       this.fullName=fullName;
       this.insuranceName=insuranceName;
        this.status=status;
        this.monthPeriod=monthPeriod;
        this.registeredDate=registeredDate;
        this.updatedDate=updatedDate;
        this.claimed=claimed;
        this.update=update;

    }

}
