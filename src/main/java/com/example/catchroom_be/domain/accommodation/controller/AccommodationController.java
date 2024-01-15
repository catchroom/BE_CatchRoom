package com.example.catchroom_be.domain.accommodation.controller;

import com.example.catchroom_be.domain.accommodation.dto.response.AccommodationResponse;
import com.example.catchroom_be.domain.accommodation.entity.Accommodation;
import com.example.catchroom_be.domain.accommodation.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/accommodation")
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping("/{accommodationId}")
    public AccommodationResponse getAccommodation(@PathVariable Long accommodationId) {
        return accommodationService.getAccommodation(accommodationId);
    }
}
