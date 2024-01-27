package com.example.catchroom_be.domain.accommodation.entity;

import com.example.catchroom_be.domain.accommodation.type.AccommodationTypeUtil.AccommodationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "accommodation")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String region;

    private String introduction;

    private String service;

    private String thumbnailUrl;

    private String latitude;

    private String longitude;

    private String address;

    private Integer phoneNumber;

    @Enumerated(EnumType.STRING)
    private AccommodationType type;

    private double star;

    private int roomCount;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccommodationImage> accommodationImageList = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Room> roomList = new ArrayList<>();

}
