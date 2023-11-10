package com.tripgago.tripgagoback.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class DockerKey {
    @Value("${API_KEY}")
    private String apiKey;
    @Value("${API_PASSWORD}")
    private String apiPw;
}
