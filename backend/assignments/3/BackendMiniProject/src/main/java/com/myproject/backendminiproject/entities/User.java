package com.myproject.backendminiproject.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(
        name = "users"
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("deleted_date IS NULL")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Builder.Default
    private Set<String> roles = new HashSet<>();

    @OneToMany(mappedBy = "admin")
    @Builder.Default
    private List<House> adminHouses = new ArrayList<>();

    @ManyToMany(mappedBy = "members")
    @Builder.Default
    private Set<House> houses = new HashSet<>();
}
