package com.example.catchroom_be.domain.user.dto.response;

import lombok.Getter;

@Getter
public class ReviewResponse {
    String accommodationName;
    String content;
    boolean isModify;


    public void fromEntity(String accommodationName,String content,boolean isModify) {
        this.accommodationName = accommodationName;
        this.content = content;
        this.isModify = isModify;
    }

}
