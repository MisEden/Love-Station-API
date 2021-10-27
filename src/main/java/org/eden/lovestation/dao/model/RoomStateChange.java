package org.eden.lovestation.dao.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "room_state_changes")
@Data
public class RoomStateChange {

    @GenericGenerator(name = "generator", strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Id
    private String id;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "room_state_id")
    private RoomState roomState;

    private Date newStartDate;
    private Date newEndDate;

    private String changedItem;
    private String reason;
    private String deniedReason;

    private boolean referralVerified = false;

    private boolean adminVerified = false;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    public Date updatedAt;

}
