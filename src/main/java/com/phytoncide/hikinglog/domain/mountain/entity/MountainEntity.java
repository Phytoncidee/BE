package com.phytoncide.hikinglog.domain.mountain.entity;

import com.phytoncide.hikinglog.global.enums.Level;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mountain")
public class MountainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mid")
    private Integer mid;

    @Column(nullable = false)
    private Integer mntilistno;

    @Column(nullable = false)
    private String mName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String info;

    @Column
    private Double mntiHigh;

    @Column
    private String mImage;

}
