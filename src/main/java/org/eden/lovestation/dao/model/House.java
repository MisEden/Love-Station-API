package org.eden.lovestation.dao.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "houses")
@Data
public class House {

    @GenericGenerator(name = "generator", strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Id
    private String id;

    private int serial;
    private String city;
    private String name;
    private String introduction;
    private String squareFootage;
    private String roomLayout;
    private String totalFloor;
    private String style;
    private String feature;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

    private String roomDescription;
    private String traffic;
    private String address;
    private String nearHospital;
    private String lifeFunction;
    private String planimetricMap;
    private String fullDegreePanorama;
    private boolean disable;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HouseImage> houseImages;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> rooms;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CheckinApplication> checkinApplications;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    public Date updatedAt;
}
