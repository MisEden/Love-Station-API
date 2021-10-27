package org.eden.lovestation.dao.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "checkin_forms")
@Data
public class CheckinForm {
    @GenericGenerator(name = "generator", strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Id
    private String id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "house_id")
    private House house;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "room_state_id")
    private RoomState roomState;
//    private String electronicLock;
//    private String powerSupply;
//    private String readCheckin;
//    private String signCheckin;
//    private String securityGuard;
//    private String hotWater;
//    private String airConditioner;
//    private String television;
//    private String electricPot;
//    private String refrigerator;
//    private String electromagneticOven;
//    private String electricKettle;
//    private String electricFans;
//    private String hairDryer;
//    private String electricBed;
//    private String bedding;
//    private String bathroom;
//    private String garbage;
//    private String clean;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }
}
