package com.example.catchroom_be.domain.user.service.social;

import com.example.catchroom_be.domain.user.dto.response.KaKaoClientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Service
@Slf4j
@RequiredArgsConstructor
public class KaKaoGetIdService {

    private static final String KAKAO_ID_GET_URL = "https://kapi.kakao.com/v2/user/me";

    private final RestTemplate restTemplate;

    public Long getKaKaoId(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", Charset.forName("UTF-8"));
        headers.setContentType(mediaType);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<KaKaoClientResponse> response = restTemplate.exchange(
                KAKAO_ID_GET_URL, HttpMethod.GET, entity, KaKaoClientResponse.class);

        return response.getBody().getId();

    }
}
