package org.eden.lovestation.dao.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "referrals")
@Data
public class Referral {
    @GenericGenerator(name = "generator", strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Id
    private String id;
    private String hospitalChineseName;
    private String hospitalEnglishName;
    private String number;
    private String city;

    @OneToMany(mappedBy = "referral", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReferralEmployee> referralEmployees;
}
