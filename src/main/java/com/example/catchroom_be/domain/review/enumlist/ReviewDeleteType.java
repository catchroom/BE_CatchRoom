package com.example.catchroom_be.domain.review.enumlist;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReviewDeleteType {
    REVIEWDELETE("리뷰 삭제 완료"),
    REVIEWWIRTEEXPIRE("리뷰 작성기한 만료");

    private String type;
}
