package com.tripgago.tripgagoback.model;
import lombok.*;
import org.json.JSONObject;

@Getter
public class NaverProductDto {
    private String title;
    private String link;
    private String description;
    private String postdate;

    public NaverProductDto(JSONObject itemJson) {
        this.title = itemJson.getString("title");
        this.link = itemJson.getString("link");
        this.description = itemJson.getString("description");
        this.postdate = itemJson.getString("postdate");
    }
}
