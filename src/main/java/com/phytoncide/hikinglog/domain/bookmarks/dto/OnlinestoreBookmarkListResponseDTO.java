package com.phytoncide.hikinglog.domain.bookmarks.dto;

import com.phytoncide.hikinglog.domain.bookmarks.entity.BookmarkEntity;
import com.phytoncide.hikinglog.domain.mountain.entity.MountainEntity;
import com.phytoncide.hikinglog.domain.store.entity.OnlineOutdoorMallEntity;
import com.phytoncide.hikinglog.domain.store.entity.StoreEntity;
import com.phytoncide.hikinglog.global.enums.BookmarkType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlinestoreBookmarkListResponseDTO {

    private List<OnlinestoreBookmarkResponseDTO> bookmarkList;
    private boolean hasNext;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OnlinestoreBookmarkResponseDTO {
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Integer id;
        private Integer storeId;
        private String name;
        private String image;
        private String link;
        private BookmarkType bookmarkType;

        public static OnlinestoreBookmarkResponseDTO toDTO(BookmarkEntity bookmarkEntity,
                                                                            OnlineOutdoorMallEntity onlineOutdoorMallEntity) {
            return OnlinestoreBookmarkResponseDTO.builder()
                    .createdAt(bookmarkEntity.getCreatedAt())
                    .updatedAt(bookmarkEntity.getUpdatedAt())
                    .id(onlineOutdoorMallEntity.getOid())
                    .storeId(onlineOutdoorMallEntity.getStoreId())
                    .name(onlineOutdoorMallEntity.getOName())
                    .image(onlineOutdoorMallEntity.getOImage())
                    .link(onlineOutdoorMallEntity.getLink())
                    .bookmarkType(bookmarkEntity.getBookmarkType())
                    .build();
        }
    }
}
