package com.tripgago.tripgagoback.component;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GetAPIKEY {
    @Value("${KeyId}")
    private String keyid;
    @Value("${keypw}")
    private String keypw;
}
