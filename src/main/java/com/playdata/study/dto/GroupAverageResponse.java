package com.playdata.study.dto;

import com.querydsl.core.Tuple;
import lombok.*;

import static com.playdata.study.entity.QIdol.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GroupAverageResponse {

    private String groupName;
    private double averageAge;

    // Tuple을 DTO로 변환하는 메서드
    public static GroupAverageResponse from(Tuple tuple) {
        return GroupAverageResponse.builder()
                .groupName(tuple.get(idol.group.groupName))
                .averageAge(tuple.get(idol.age.avg()))
                .build();
    }

}