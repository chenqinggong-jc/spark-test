package org.example;

import org.apache.flink.table.functions.ScalarFunction;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class EncryptFieldUDF extends ScalarFunction {

    public String eval(String data) {
        try {
            // 获取 token
            String token = TokenService.getToken();

            // 设置信任所有证书
            Config.trustAllCertificates();

            // 对传入的数据进行 Base64 编码
            String encodedData = Base64.getEncoder().encodeToString(data.getBytes("UTF-8"));
            System.out.println("Base64: " + encodedData);

            URL url = new URL(Config.ENCRYPT_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-SW-Authorization-Token", token);
            connection.setRequestProperty("X-SW-Authorization-TenantCode", Config.TENANT_CODE);
            connection.setRequestProperty("X-SW-Authorization-AppCode", Config.APP_CODE);
            connection.setDoOutput(true);

            String jsonInputString = "{\n" +
                    "    \"keyId\": \"" + Config.KEY_ID + "\",\n" +
                    "    \"algType\": \"" + Config.ALG_TYPE + "\",\n" +
                    "    \"inData\": \"" + encodedData + "\",\n" +
                    "    \"paddingType\": \"" + Config.PADDING_TYPE + "\"\n" +
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
                    System.out.println("Response: " + response); // 添加日志输出
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    if (jsonResponse.has("result")) {
                        return jsonResponse.getJSONObject("result").getString("outData");
                    } else {
                        throw new RuntimeException("Response does not contain 'result' field: " + response);
                    }
                }
            } else {
                throw new RuntimeException("Failed to encrypt data, HTTP error code : " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }
}
