package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TaobaoSuggestRequest {

    public static void main(String[] args) {
        try {
//            // 商品关键字
//            String keyword = "手机";
//            // 编码关键字
//            String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.toString());
//            // 构建完整的URL
//            String urlStr = "http://suggest.taobao.com/sug?code=utf-8&q=" + encodedKeyword + "&callback=cb";

            String type="yuantong";
            String postid="11111111111";
            String urlStr = "http://www.kuaidi100.com/query?type=" + type + "&postid="+postid;


            // 发送GET请求并处理重定向
            String response = sendGetRequest(urlStr);
            if (response != null) {
                System.out.println("Response: " + response);
            } else {
                System.out.println("Failed to get a valid response.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while making the request: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String sendGetRequest(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // 成功
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            }
        } else if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
            // 处理重定向
            String redirectUrl = connection.getHeaderField("Location");
            System.out.println("Redirecting to: " + redirectUrl);
            return sendGetRequest(redirectUrl); // 递归调用
        } else {
            System.err.println("Request failed with response code: " + responseCode);
        }

        connection.disconnect();
        System.exit(0);
        return null;
    }
}
