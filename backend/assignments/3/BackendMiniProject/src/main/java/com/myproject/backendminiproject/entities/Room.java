package com.myproject.backendminiproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "rooms"
)
@Getter
@Setter
@SQLRestriction("deleted_date IS NULL")
public class Room extends BaseEntity {

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @ManyToOne
    @JoinColumn(name = "home_id", nullable = false)
    private House house;

    @OneToMany(mappedBy = "room")
    private List<Device> devices = new ArrayList<>();
}
