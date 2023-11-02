package com.tripgago.tripgagoback.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NaverRequestVariableDto {
    String query;
    Integer display;
    Integer start;
    String sort;
}
