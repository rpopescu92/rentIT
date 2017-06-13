package com.rentIT.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChartResponse {
    private List<String> labels;
    private List<Integer> data;
    private List<List<Integer>> complexData;
    private List<String> series;
}
