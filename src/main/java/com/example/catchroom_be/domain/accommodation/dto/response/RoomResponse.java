package com.example.catchroom_be.domain.accommodation.dto.response;

import com.example.catchroom_be.domain.accommodation.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoomResponse {
    private Long id;

    private int normalCapacity;

    private int maxCapacity;

    private int price;

    private String introduction;

    private String name;

    private String service;

    private int count;

    public static RoomResponse fromEntity(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .normalCapacity(room.getNormalCapacity())
                .maxCapacity(room.getMaxCapacity())
                .price(room.getPrice())
                .introduction(room.getIntroduction())
                .name(room.getName())
                .service(room.getService())
                .count(room.getCount())
                .build();
    }
}
