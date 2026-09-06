package com.yashwanth.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryStats {

    private String category;

    private Long count;

}