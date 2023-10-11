package org.example;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.simple.JSONObject;
import java.io.IOException;
/**
  * This class is used to send the book data to the web API and Kafka server
  * The method includes try catch blocks to handle exceptions that may occur
  */
public class WebAPIClient {

    public static String sendToWebAPI(JSONObject myObj) {
        String returnResp = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:8080/api/v1/kafka/publish");

            String jsonPayload = myObj.toJSONString();
            StringEntity entity = new StringEntity(jsonPayload, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String responseString = EntityUtils.toString(responseEntity);
                    System.out.println("Book sent to Kafka server and database");
                    returnResp = responseString;
                }
            } catch (ParseException e) {
                System.err.println("Error parsing the response from the web API: " + e.getMessage());
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            System.err.println("Error creating HTTP client or executing request: " + e.getMessage());
            e.printStackTrace();
        }
        return returnResp;
    }
}
