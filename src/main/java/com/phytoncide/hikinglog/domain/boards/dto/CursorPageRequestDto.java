package com.phytoncide.hikinglog.domain.boards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursorPageRequestDto {

    private Long size;

    private Integer page;

}
