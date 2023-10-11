package org.example;

import org.json.simple.JSONObject;
import java.util.Scanner;

/**
 * This class is responsible for handling user input.
 * The user can send and retrieve data from the Kafka server.
 * If the user tries to enter an empty field, the application will prompt the user to enter all fields.
 */
public class UserInputManager {

    public static void userInputForKafka() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the details of the book:");

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
            System.out.println("Please enter all fields.");
            return;
        }

        JSONObject myObj = new JSONObject();
        myObj.put("title", title);
        myObj.put("author", author);
        myObj.put("genre", genre);

        try {
            WebAPIClient.sendToWebAPI(myObj);
        } catch (Exception e) {
            System.out.println("An error occurred while sending data: " + e.getMessage());
        }
    }
}

