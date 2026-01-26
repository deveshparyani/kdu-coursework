package com.myproject.backendminiproject.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "device_inventory"
)
@Getter
@Setter
@SQLRestriction("deleted_date IS NULL")
public class Device extends BaseEntity {

    @Column(
            name = "kickston_id",
            nullable = false,
            unique = true,
            length = 6
    )
    private String kickstonId;

    @Column(
            name = "device_username",
            nullable = false,
            unique = true
    )
    private String deviceUsername;

    @Column(
            name = "device_password",
            nullable = false
    )
    private String devicePassword;

    @Column(
            name = "manufacture_date_time",
            nullable = false
    )
    private LocalDateTime manufactureDateTime;

    @Column(
            name = "manufacture_factory_place",
            nullable = false
    )
    private String manufactureFactoryPlace;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
