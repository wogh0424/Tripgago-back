package com.tripgago.tripgagoback.service;

import com.tripgago.tripgagoback.model.NaverProductDto;
import com.tripgago.tripgagoback.model.NaverRequestVariableDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NaverBlogService {

    public List<NaverProductDto> naverShopSearchAPI(NaverRequestVariableDto naverVariable) {
        String url = "https://openapi.naver.com/";

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("v1/search/blog.json")
                .queryParam("query", naverVariable.getQuery())
                .queryParam("display", naverVariable.getDisplay())
                .queryParam("start", naverVariable.getStart())
                .queryParam("sort", naverVariable.getSort())
                .encode()
                .build()
                .toUri();
        log.info("uri : {}", uri);
        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "tIQAtTUj6REirsdJuCO0")
                .header("X-Naver-Client-Secret", "qNIxLZ7ZZk")
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        List<NaverProductDto> naverProductDtos = fromJSONtoNaverProduct(result.getBody());

        log.info("result ={}", naverProductDtos);
        return naverProductDtos;

    }
    private List<NaverProductDto> fromJSONtoNaverProduct(String result) {
        // 문자열 정보를 JSONObject로 바꾸기
        JSONObject rjson = new JSONObject(result);
        // JSONObject에서 items 배열 꺼내기
        // JSON 배열이기 때문에 보통 배열이랑 다르게 활용해야한다.
        JSONArray naverProducts = rjson.getJSONArray("items");
        List<NaverProductDto> naverProductDtoList = new ArrayList<>();
        for (int i = 0; i < naverProducts.length(); i++) {
            JSONObject naverProductsJson = (JSONObject) naverProducts.get(i);
            NaverProductDto itemDto = new NaverProductDto(naverProductsJson);
            naverProductDtoList.add(itemDto);
        }
        return naverProductDtoList;
    }
}

