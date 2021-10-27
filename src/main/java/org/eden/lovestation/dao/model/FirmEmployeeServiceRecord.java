package org.eden.lovestation.dao.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "firm_employee_service_record")
@Data
public class FirmEmployeeServiceRecord {

    @GenericGenerator(name = "generator", strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Id
    private String id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "checkin_application_id")
    private CheckinApplication checkinApplication;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "firm_employee_id")
    private FirmEmployees firmEmployees;

    private String service;

    private String remark;

    private String beforeImage;

    private String afterImage;

    private String viewed = "未檢視";

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    public Date updatedAt;

}
