package com.example.catchroom_be.domain.accommodation.service;

import com.example.catchroom_be.domain.accommodation.dto.response.AccommodationResponse;
import com.example.catchroom_be.domain.accommodation.entity.Accommodation;
import com.example.catchroom_be.domain.accommodation.entity.Room;
import com.example.catchroom_be.domain.accommodation.exception.AccommodationException;
import com.example.catchroom_be.domain.accommodation.repository.AccommodationRepository;
import com.example.catchroom_be.domain.accommodation.repository.RoomRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public AccommodationResponse getAccommodation(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new AccommodationException(ErrorCode.ACCOMMODATION_NOT_FOUND));

        return AccommodationResponse.fromEntity(accommodation);
    }

    @Transactional(readOnly = true)
    public Room getRoomEntity(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AccommodationException(ErrorCode.ROOM_NOT_FOUND));
        return room;
    }
}
