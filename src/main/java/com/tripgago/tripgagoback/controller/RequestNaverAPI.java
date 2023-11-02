package com.tripgago.tripgagoback.controller;

import com.tripgago.tripgagoback.model.NaverProductDto;
import com.tripgago.tripgagoback.model.NaverRequestVariableDto;
import com.tripgago.tripgagoback.service.NaverProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RequestNaverAPI {
    private final NaverProductService naverProductService;

    @GetMapping("/api/getNaverAPI/{keyword}")
    public List<NaverProductDto> getNaverAPI(@PathVariable String keyword) {
        String order = keyword.substring(6, keyword.indexOf("&"));
        String[] parts = keyword.split("&");
        String input = parts[1].substring(parts[1].indexOf("=")+1);
        NaverRequestVariableDto naverRequestVariableDto = NaverRequestVariableDto.builder()
                .query(input)
                .display(20)
                .start(1)
                .sort(order)
                .build();
        List<NaverProductDto> list = naverProductService.naverShopSearchAPI(naverRequestVariableDto);
        return list;
    }
}


