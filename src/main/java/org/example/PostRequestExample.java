package org.example;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class PostRequestExample {

    public static void main(String[] args) {
        // 创建HttpClient对象
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建HttpPost对象
            HttpPost httpPost = new HttpPost("http://jsonplaceholder.typicode.com/posts");

            // 设置请求头
            httpPost.setHeader("Content-type", "application/json");

            // 创建JSON对象
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("userId", 1);

            // 设置请求体
            StringEntity entity = new StringEntity(jsonBody.toString());
            httpPost.setEntity(entity);

            // 执行请求
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // 获取响应状态码
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println("Status Code: " + statusCode);

                // 获取响应体
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String responseString = EntityUtils.toString(responseEntity);
                    System.out.println("Response: " + responseString);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
