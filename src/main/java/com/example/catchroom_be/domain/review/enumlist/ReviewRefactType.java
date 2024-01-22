package com.example.catchroom_be.domain.review.enumlist;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReviewRefactType {
    REVIEWREFACTVALID("수정 가능"),
    REVIEWREFACTNOTVALID("수정 불가능");

    private String type;
}
