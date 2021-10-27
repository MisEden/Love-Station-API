package org.eden.lovestation.dao.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "referral_employees")
@Data
public class ReferralEmployee {
    @GenericGenerator(name = "generator", strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Id
    private String id;
    private String lineId;
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "referral_id")
    private Referral referral;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "role_id")
    private Role role;
    private String name;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "referral_title_id")
    private ReferralTitle referralTitle;
    @OneToMany(mappedBy = "referralEmployee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CheckinApplication> checkinApplications;
    private String workIdentity;
    private String image;
    private String account;
    private String password;
    private String email;
    private String gender;
    private String address;
    private String phone;
    private String cellphone;
    private Boolean verified;
    private Boolean agreePersonalInformation;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    public Date updatedAt;
    @OneToMany(mappedBy = "referralEmployee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReferralNumber> referralNumbers;
}
