package org.eden.lovestation.dao.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "investigation")
@Data
public class Investigation {

    @GenericGenerator(name = "generator", strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Id
    private String id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "room_state_id")
    private RoomState roomStates;

    private short serviceEfficiency;
    private short serviceAttitude;
    private short serviceQuality;
    private short equipmentFurniture;
    private short equipmentElectricDevice;
    private short equipmentAssistive;
    private short equipmentBedding;
    private short equipmentBarrierFreeEnvironment;
    private short environmentClean;
    private short environmentComfort;
    private short safetyFirefighting;
    private short safetySecomEmergencySystem;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    public Date updatedAt;

    public void setServiceEfficiency(short serviceEfficiency){
        this.serviceEfficiency = serviceEfficiency;
    }

    public void setRoomStates(RoomState roomStates){
        this.roomStates = roomStates;
    }
}
