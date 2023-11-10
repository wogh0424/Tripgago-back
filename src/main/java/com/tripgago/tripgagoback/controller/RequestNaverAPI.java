package com.tripgago.tripgagoback.controller;

import com.tripgago.tripgagoback.model.NaverProductDto;
import com.tripgago.tripgagoback.model.NaverRequestVariableDto;
import com.tripgago.tripgagoback.service.NaverBlogService;
import com.tripgago.tripgagoback.service.CheckLangService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RequestNaverAPI {
    private final NaverBlogService naverProductService;
    private final CheckLangService translationAPI;
    @GetMapping("/api/getNaverAPI/{keyword}")
    public List<NaverProductDto> getNaverAPI(@PathVariable String keyword) {
        System.out.println(keyword);
        String order = keyword.substring(6, keyword.indexOf("&"));
        String[] parts = keyword.split("&");
        String input = parts[1].substring(parts[1].indexOf("=")+1);
        String resultinput = translationAPI.checkLang(input);
        System.out.println(resultinput);
        int page = Integer.parseInt(parts[2].substring(parts[2].indexOf("=")+1));
        NaverRequestVariableDto naverRequestVariableDto = NaverRequestVariableDto.builder()
                .query(resultinput)
                .display(30)
                .start(page)
                .sort(order)
                .build();
        List<NaverProductDto> list = naverProductService.naverShopSearchAPI(naverRequestVariableDto);
        return list;
    }
}


