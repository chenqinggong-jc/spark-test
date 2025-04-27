package org.example;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class Config {
    public static final String TOKEN_URL = "https://60.217.194.220:30028/ccsp/auth/app/v1/token";
    public static final String ENCRYPT_URL = "https://60.217.194.220:30028/pki/api/v6/encrypt/internal/symmetric";
    public static final String DECRYPT_URL = "https://60.217.194.220:30028/pki/api/v6/encrypt/internal/symmetric";
    public static final String INTEGRITY_URL = "https://60.217.194.220:30028/pki/api/v6/hmac/internal/symmetric";
    public static final String USERNAME = "test@testapp01";
    public static final String PASSWORD = "Aa123456";
    public static final String TENANT_CODE = "test";
    public static final String APP_CODE = "testapp01";
    public static final String KEY_ID = "Symm595182a6-3861-49d2-88bc-9c996d448781";
    public static final String ALG_TYPE = "SGD_SM4_ECB";
    public static final String PADDING_TYPE = "PKCS7PADDING";
    public static final String HMAC_KEY_ID = "Symm40da4f69-ed51-4717-b984-58cba27a1c26";
    public static final String HMAC_KEY_NAME = "HMAC_key01";
    public static final String HMAC_ALG_TYPE = "SGD_SM3";

    public static void trustAllCertificates() {
        try {
            // 创建一个信任所有证书的 TrustManager
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            // 安装信任所有证书的 TrustManager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // 创建一个接受所有主机名的 HostnameVerifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // 安装 HostnameVerifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Error setting up SSL context", e);
        }
    }
}
