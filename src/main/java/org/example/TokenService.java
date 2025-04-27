package org.example;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TokenService {

    public static synchronized String getToken() {
        // 从Redis中获取Token
        String accessToken = RedisUtil.getString("access_token");
        if (accessToken == null) {
            try {
                // 设置信任所有证书
                Config.trustAllCertificates();

                URL url = new URL(Config.TOKEN_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                String jsonInputString = "{\n" +
                        "    \"username\":\"" + Config.USERNAME + "\",\n" +
                        "    \"password\":\"" + Config.PASSWORD + "\"\n" +
                        "}";

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }
                        JSONObject jsonResponse = new JSONObject(response.toString());
                        accessToken = jsonResponse.getJSONObject("data").getString("accessToken");
                        int expiresIn = jsonResponse.getJSONObject("data").getInt("expiresIn");

                        // 将Token存储到Redis中
                        RedisUtil.setString("access_token", accessToken);
                        RedisUtil.setExpire("access_token", expiresIn - 60);
                    }
                } else {
                    throw new RuntimeException("Failed to get token, HTTP error code : " + responseCode);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error getting token", e);
            }
        }
        return accessToken;
    }
}
