package org.eden.lovestation.dao.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "firms")
@Data
public class Firm {

    @GenericGenerator(name = "generator", strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Id
    private String id;

    private String name;
    private String address;
    private String phone;
    private String contactPeople;
    private String contactTitle;
    private String contactPhone;
    private String contactEmail;

    @OneToMany(mappedBy = "firm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FirmEmployees> firmEmployees;

}
