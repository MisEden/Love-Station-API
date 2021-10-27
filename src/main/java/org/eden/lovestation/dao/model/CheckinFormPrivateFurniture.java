package org.eden.lovestation.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "checkin_form_private_furniture")
@Data
public class CheckinFormPrivateFurniture {
    @GenericGenerator(name = "generator", strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Id
    String id;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "checkin_id")
    private CheckinForm checkinForm;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "furniture_id")
    private PrivateFurniture privateFurniture;

    Boolean broken;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date createdAt;
}
