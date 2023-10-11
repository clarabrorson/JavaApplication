
import org.example.KafkaDataFetcher;
import org.example.WebAPIClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.*;
import springboot.kafka.webservice.payload.Book;
import org.json.simple.JSONObject;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;

/**
 * KafkaTests
 * This class is used to test the Kafka functionality
 * Annotation Before is used to set up a JSONObject that is used in the tests
 * The methods being tested are:
 * - sendToWebAPI
 * - getDataFromKafka
 * The test for getDataFromKafka is set with a Thread.sleep() to make sure that the test is not run before the data is sent to Kafka
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KafkaTests {
    private static final String KAFKA_TOPIC_NAME = "bookTopic_json";
    private JSONObject sharedJsonObj;

    @Before
    public void setUp() {
        sharedJsonObj = new JSONObject();
        sharedJsonObj.put("id", 250L);
        sharedJsonObj.put("title", "NewestTestTitle");
        sharedJsonObj.put("author", "NewestTestAuthor");
        sharedJsonObj.put("genre", "NewestTestGenre");
    }

    @Order(1)
    @Test
    public void sendToWebAPITest() {

        String sendResponse = WebAPIClient.sendToWebAPI(sharedJsonObj);
        assertEquals("Json message sent to kafka topic", sendResponse);
    }

    @Order(2)
    @Test
    public void getDataFromKafkaTest() {

        ArrayList<Book> books = KafkaDataFetcher.getDataFromKafka(KAFKA_TOPIC_NAME);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            Book testBook = books.get(books.size() - 1);

            assertEquals(sharedJsonObj.get("id"), testBook.getId());
            assertEquals(sharedJsonObj.get("title"), testBook.getTitle());
            assertEquals(sharedJsonObj.get("author"), testBook.getAuthor());
            assertEquals(sharedJsonObj.get("genre"), testBook.getGenre());
        }
    }











