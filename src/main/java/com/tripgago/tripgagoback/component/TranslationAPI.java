package com.tripgago.tripgagoback.component;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TranslationAPI {
     public String Translation(String langcode, String input) {
        String clientId = "tIQAtTUj6REirsdJuCO0";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "qNIxLZ7ZZk";//애플리케이션 클라이언트 시크릿값";

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String text;
        try {
            text = URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

         String responseBody = post(apiURL, requestHeaders, text, langcode);
         String key = "translatedText\":";
         String result2 = "";
         String result = responseBody.substring(responseBody.indexOf("translatedText\":"));
         String result1 = result.substring(key.length() + 1);
         int endIndex = result1.indexOf("\",\"engineType\":\"N2MT\"}}}");
         if (endIndex != -1) {
             result2 = result1.substring(0, endIndex);
             System.out.println(result2); // "Hello. How are you today?"
         } else {
             System.out.println("특정 문자열을 찾을 수 없습니다.");
         }
        return result2;
    }

    private static String post(String apiUrl, Map<String, String> requestHeaders, String text, String langcode){
        HttpURLConnection con = connect(apiUrl);
        String ko = "";

        String postParams = "source="+langcode+"&target=ko&text=" + text; //원본언어: 언어인식 -> 목적언어: 한국어
        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(con.getInputStream());
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}