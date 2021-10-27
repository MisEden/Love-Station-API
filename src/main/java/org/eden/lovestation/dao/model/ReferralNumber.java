package org.eden.lovestation.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "referral_numbers")
@Data
public class ReferralNumber {
    @Id
    private String id;
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "referral_employee_id")
    private ReferralEmployee referralEmployee;
    private String identityCard;
}
