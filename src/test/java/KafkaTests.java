import org.example.DataManager;
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

        String sendResponse = DataManager.sendToWebAPI(sharedJsonObj);
        assertEquals("Json message sent to kafka topic", sendResponse);
    }

    @Order(2)
    @Test
    public void getDataFromKafkaTest() {

        ArrayList<Book> books = DataManager.getDataFromKafka(KAFKA_TOPIC_NAME);

            Book testBook = books.get(books.size() - 1);

            assertEquals(sharedJsonObj.get("id"), testBook.getId());
            assertEquals(sharedJsonObj.get("title"), testBook.getTitle());
            assertEquals(sharedJsonObj.get("author"), testBook.getAuthor());
            assertEquals(sharedJsonObj.get("genre"), testBook.getGenre());
        }
    }

    /*@Test
    public void sendToWebAPITest() {

        JSONObject myObj = new JSONObject();
        myObj.put("id", 250L);
        myObj.put("title", "NewestTestTitle");
        myObj.put("author", "NewestTestAuthor");
        myObj.put("genre", "NewestTestGenre");

        //Anropa metod för att skcika den
        String resp = DataManager.sendToWebAPI(myObj);

        //Jämföra response-värden
        assertEquals(resp, "Json message sent to kafka topic");
    }

    @Test
    public void getDataFromKafkaTest() {
        // Skapa ett test-Book-objekt med värden
        Book book = new Book();
        book.setId(250L);
        book.setTitle("NewestTestTitle");
        book.setAuthor("NewestTestAuthor");
        book.setGenre("NewestTestGenre");

        // Anropa metod för att hämta böcker från Kafka
        ArrayList<Book> books = DataManager.getDataFromKafka("bookTopic_json");

        // Kontrollera att det finns minst en bok
        if (!books.isEmpty()) {
            Book testBook = books.get(books.size() - 1);

            // Jämför egenskaper med det skapade test-Book-objektet
            assertEquals(testBook.getId(), book.getId());
            assertEquals(testBook.getTitle(), book.getTitle());
            assertEquals(testBook.getAuthor(), book.getAuthor());
            assertEquals(testBook.getGenre(), book.getGenre());
        } else {
            // Om det inte finns några böcker, kan du hantera detta här
            System.out.println("No books found");
        }
    }*/











