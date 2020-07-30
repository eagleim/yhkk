package com.zxelec.yhkk.utils;

import com.alibaba.fastjson.JSON;
import com.zxelec.yhkk.entity.SubscribeRsp;
import com.zxelec.yhkk.entity.ViidResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpUtil {
    private static Logger logger = LogManager.getLogger(HttpUtil.class);

    /**
     * 带Digest auth发送http请求
     *
     * @param url
     * @param jsonString
     * @param username
     * @param password
     * @return
     */
    public static ViidResult postToVIID(String url, String jsonString, String username, String password) {
//        String result = "";
//        String code = "";
        ViidResult viidResult = new ViidResult();
        if (!StringUtils.isEmpty(jsonString)) {
            HttpContext httpContext = new BasicHttpContext();
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

            CloseableHttpClient httpClient = HttpClients.createDefault();
            httpContext.setAttribute(ClientContext.CREDS_PROVIDER, credentialsProvider);
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-type", "application/json");
            try {
                StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost, httpContext);
                viidResult.setCode(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
                if (httpResponse.getStatusLine().getStatusCode() < 400) {
                    logger.info("发送成功");
                }
                HttpEntity httpEntity = httpResponse.getEntity();
                viidResult.setResult(EntityUtils.toString(httpEntity, "utf-8"));
            } catch (Exception e) {
                logger.error("发送失败:" + e);
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error("关闭失败" + e);
                }
            }

        } else {
            viidResult.setResult(JSON.toJSONString(new SubscribeRsp("-1", "发送文件为空")));
            logger.error("发送文件为空");
        }
        return viidResult;
    }

    /**
     * 根据url获取图片转byte64
     *
     * @param urlString
     * @return
     */
    public static String URLtoImageBase64(String urlString) {
        HttpURLConnection httpURLConnection = null;
        DataInputStream dataInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            URL url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            dataInputStream = new DataInputStream(httpURLConnection.getInputStream());
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int count = 0;
            while ((count = dataInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, count);
            }
        } catch (Exception e) {
            logger.error("获取图片失败" + e);
        } finally {
            try {
                dataInputStream.close();
                byteArrayOutputStream.close();
                httpURLConnection.disconnect();
            } catch (IOException e) {
                logger.error("关闭流失败" + e);
            }
        }
        return base64Encode(byteArrayOutputStream.toByteArray());
    }

    public static String base64Encode(byte[] image) {
        BASE64Encoder decoder = new BASE64Encoder();
        return decoder.encode(image).replaceAll("\n", "").replaceAll("\r", "");
    }

}
