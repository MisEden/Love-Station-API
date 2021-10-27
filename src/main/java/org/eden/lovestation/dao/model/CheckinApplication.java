package org.eden.lovestation.dao.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "checkin_applications")
@Data
public class CheckinApplication {

    @GenericGenerator(name = "generator", strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Id
    private String id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "referral_employee_id")
    private ReferralEmployee referralEmployee;
    private Date referralDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;
    private Date adminDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;
    private Date volunteerDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "firm_employee_id")
    private FirmEmployees firmEmployees;
    private Date firmEmployeeDate;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "room_state_id")
    private RoomState roomState;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "checkinApplication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LandlordServiceRecord> landlordServiceRecords;

    @OneToMany(mappedBy = "checkinApplication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VolunteerServiceRecord> volunteerServiceRecords;

    private boolean applicantIn;
    private String bloodType;
    private String language;
    private String specialMedicalHistory;
    private String drugAllergy;
    private String diagnosedWith;
    private String overviewPatientCondition;
    private String medicine;
    private String userIdentity;
    private String selfCareAbility;
    private String attachment;
    private String caregiverName;
    private String caregiverRelationship;
    private String caregiverPhone;
    private String applicantInfectiousDisease;
    private String caregiverInfectiousDisease;
    private String oneEmergencyContactName;
    private String oneEmergencyContactPhone;
    private String oneEmergencyContactRelationship;
    private String twoEmergencyContactName;
    private String twoEmergencyContactRelationship;
    private String twoEmergencyContactPhone;
    private String applicationReason;
    private Boolean firstVerified;
    private String firstVerifiedReason;
    private Boolean secondVerified;
    private String rentImage;
    private String affidavitImage;

    private Date checkinNotificationDate;
    private Date checkoutNotificationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    public Date updatedAt;


    public RoomState getRoomState(){ return roomState; }

}
