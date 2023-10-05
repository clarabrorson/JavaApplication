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
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.simple.JSONObject;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import springboot.kafka.webservice.payload.Book;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Scanner;

/**
 * This class handles the data
 */
public class DataManager {

    /**
     * This method takes user input to create a JSON object and sends it to a web API endpoint.
     */

    public static void userInputForKafka() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Name your favorite book: ");

        long id = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("ID: ");
                String input = scanner.nextLine();
                id = Long.parseLong(input);

                if (id > 0) {
                    validInput = true;
                } else {
                    System.out.println("ID must be a positive integer. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid input.");
            }
        }

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        JSONObject myObj = new JSONObject();
        myObj.put("id", id);
        myObj.put("title", title);
        myObj.put("author", author);
        myObj.put("genre", genre);

        try {
            DataManager.sendToWebAPI(myObj);
        } catch (Exception e) {
            System.out.println("An error occurred while sending data: " + e.getMessage());
        }
    }
    /**
     * Sends a JSON object to a web API endpoint.
     *
     * @param myObj The JSON object to be sent.
     * @return A response message from the web API.
     */

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
                    System.out.println("Book sent to kafka server and database");
                    returnResp = responseString;
                }
            } catch (ParseException e) {
                System.err.println("Error parsing the response from the web API: " + e.getMessage());
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            System.err.println("Error creating HTTP client or executing request: " + e.getMessage());
            e.printStackTrace(); // Skriv ut stackspåret för IOException
        }
        return returnResp;
    }
    /**
     * Retrieves a list of books from a Kafka topic.
     *
     * @param topicName The name of the Kafka topic to fetch data from.
     * @return An ArrayList of Book objects retrieved from the Kafka topic in JSON format.
     */
    public static ArrayList<Book> getDataFromKafka(String topicName) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "fetchingGroup");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put("spring.json.trusted.packages", "*");

        Consumer<String, Book> consumer = new KafkaConsumer<>(props);
        consumer.assign(Collections.singletonList(new TopicPartition(topicName, 0)));
        consumer.seekToBeginning(consumer.assignment());

        ArrayList<Book> books = new ArrayList<Book>();

        while (true) {
            ConsumerRecords<String, Book> records = consumer.poll(Duration.ofMillis(100));
            if (records.isEmpty()) continue;
            for (ConsumerRecord<String, Book> record : records) {
                Book book = record.value();
                if (book != null)
                    if (book.getTitle() != null) {
                        books.add(book);
                        System.out.println(book.getTitle());
                    } else {
                        System.out.println("No book found");
                    }
            }
            break;
        }

        return books;
    }

}
