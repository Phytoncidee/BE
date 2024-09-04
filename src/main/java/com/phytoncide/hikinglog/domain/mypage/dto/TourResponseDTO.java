package com.phytoncide.hikinglog.domain.mypage.dto;

import com.phytoncide.hikinglog.domain.mypage.entity.TourEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourResponseDTO {

    private Integer tourId; // 투어의 고유 ID
    private String tourTitle; // 투어 제목
    private Integer mountainId; // 산의 ID
    private List<String> preHikeAccomoIds; // 등산 전 숙박 ID 리스트
    private List<String> preHikeRestaurantIds; // 등산 전 음식점 ID 리스트
    private List<String> postHikeAccomoIds; // 등산 후 숙박 ID 리스트
    private List<String> postHikeRestaurantIds; // 등산 후 음식점 ID 리스트
    // 사용자 ID 추가
    private Integer userId;
    private Enum<TourEntity.Status> status;
}