package com.example.catchroom_be.domain.accommodation.dto.response;

import com.example.catchroom_be.domain.accommodation.entity.Accommodation;
import com.example.catchroom_be.domain.accommodation.type.AccommodationTypeUtil.AccommodationType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccommodationResponse {

    private Long id;

    private String name;

    private String region;

    private String introduction;

    private String service;

    private String thumbnailUrl;

    private String latitude;

    private String longitude;

    private String address;

    private AccommodationType type;

    private double star;

    private int roomCount;

    private List<RoomResponse> roomList;

    public static AccommodationResponse fromEntity(Accommodation accommodation) {
        return AccommodationResponse.builder()
                .id(accommodation.getId())
                .name(accommodation.getName())
                .thumbnailUrl(accommodation.getThumbnailUrl())
                .address(accommodation.getAddress())
                .introduction(accommodation.getIntroduction())
                .latitude(accommodation.getLatitude())
                .longitude(accommodation.getLongitude())
                .region(accommodation.getRegion())
                .service(accommodation.getService())
                .roomCount(accommodation.getRoomCount())
                .star(accommodation.getStar())
                .type(accommodation.getType())
                .roomList(
                        Optional.ofNullable(accommodation.getRoomList())
                                .orElse(new ArrayList<>())
                                .stream().map(RoomResponse::fromEntity)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
