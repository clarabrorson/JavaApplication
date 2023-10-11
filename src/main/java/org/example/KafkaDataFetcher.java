package org.example;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import springboot.kafka.webservice.payload.Book;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
/**
  * This class is responsible for fetching data from Kafka.
  * The method getDataFromKafka() fetches data from Kafka and returns an ArrayList of books.
  * The method includes a if statement that checks if the book is null or not.
  */
public class KafkaDataFetcher {
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
                        System.out.println("------------------");
                        System.out.println("Book retrieved: ");
                        System.out.println("Title: " + book.getTitle());
                        System.out.println("Author: " + book.getAuthor());
                        System.out.println("Genre: " + book.getGenre());
                        System.out.println("------------------");
                    } else {
                        System.out.println("No book found");
                    }
            }
            break;
        }
        return books;
    }
}







