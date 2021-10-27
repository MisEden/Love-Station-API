package org.eden.lovestation.dao.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "admins")
@Data
public class Admin {
    @GenericGenerator(name = "generator", strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Id
    private String id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CheckinApplication> checkinApplications;
    private String lineId;
    private String account;
    private String password;
    private String name;
    private String email;
    private Boolean verified;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    public Date updatedAt;
}

