package com.zxelec.yhkk.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


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
    public static void postToVIID(String url, String jsonString, String username, String password) {

        HttpContext httpContext = new BasicHttpContext();
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        
        httpContext.setAttribute(ClientContext.CREDS_PROVIDER, credentialsProvider);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(60000).setConnectionRequestTimeout(60000)  
                .setSocketTimeout(60000).build();
                

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-type", "application/json");
        try {
            StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            httpResponse = httpClient.execute(httpPost, httpContext);
            
            if (httpResponse.getStatusLine().getStatusCode() < 400) {
                logger.info("发送成功, httpStatusCode:{}, ", (httpResponse.getStatusLine().getStatusCode()));
            }
            HttpEntity httpEntity = httpResponse.getEntity();
            logger.info("视图库返回消息：" + EntityUtils.toString(httpEntity, "utf-8"));
        } catch (Exception e) {
            logger.error("发送失败:" + e.getMessage());
            
        } finally {
            if(httpResponse != null) {
            	try {
					httpResponse.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("视图库httpResponse close 异常！" + e.getMessage());
				}
            }
            if(httpClient != null) {
            	try {
					httpClient.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("视图库httpClient close 异常！" + e.getMessage());
				}
            }
            
        }


    }

    /**
     * 根据url获取图片转byte64
     *
     * @param urlString
     * @return
     */
    public static String URLtoImageBase64(String urlString) {
    	CloseableHttpClient httpClient = null;
    	CloseableHttpResponse response = null;
        InputStream dataInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            httpClient = HttpClients.createDefault();
                        
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(3000).setConnectionRequestTimeout(3000)  
                    .setSocketTimeout(3000).build();

            HttpGet httpGet = new HttpGet(urlString);
            httpGet.setConfig(requestConfig);
            
            response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK) {
            	logger.error("图片服务器响应错误，URL：{},错误代码：{}", urlString, response.getStatusLine().getStatusCode());
            	return "0";
            }
            
            HttpEntity entity = response.getEntity();
            dataInputStream = entity.getContent();
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int count = 0;
            while ((count = dataInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, count);
            }
            
        } catch (Exception e) {
            logger.error("图片下载Exception,URL:{}, 错误码：{}", urlString, e);
            return "0";
        } finally {
            try {
            	if(dataInputStream != null) {
            		dataInputStream.close();
            	}
                if(byteArrayOutputStream != null) {
                	byteArrayOutputStream.close();
                }
                if(response != null) {
                	response.close();
                }
                if(httpClient != null) {
                	httpClient.close();
                }
                
            } catch (Exception e) {
                logger.error("关闭流失败" + e);
            }
        }
        
        if (byteArrayOutputStream == null) {
        	return "0";
        }
        return base64Encode(byteArrayOutputStream.toByteArray());
    }

    public static String base64Encode(byte[] image) {
        BASE64Encoder decoder = new BASE64Encoder();
        return decoder.encode(image).replaceAll("\n", "").replaceAll("\r", "");
    }

}
