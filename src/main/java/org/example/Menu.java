package org.example;

import java.util.Scanner;

import static org.example.DataManager.getDataFromKafka;
import static org.example.DataManager.userInputForKafka;

public class Menu {

    public static void printMenu() {

        System.out.println("======================================");
        System.out.println("           Kafka Project Menu         ");
        System.out.println(" ------------------------------------ ");
        System.out.println("Send and Retrieve your favorite books!");
        System.out.println("======================================");
        System.out.println("1. Send book to Kafka Server");
        System.out.println("2. Retrieve book from Kafka Server");
        System.out.println("0. Exit");
    }

    public static void userMenu() {
        String choice = "";
        Scanner scan = new Scanner(System.in);

        do {
            printMenu();
            System.out.print("Please select an option from the menu: ");
            choice = scan.nextLine();

            switch (choice) {
                case "1" -> {
                    try {
                        userInputForKafka();
                    } catch (Exception e) {
                        System.out.println("An error occurred: " + e.getMessage());
                    }
                }
                case "2" -> {
                    try {
                        getDataFromKafka("bookTopic_json");
                    } catch (Exception e) {
                        System.out.println("An error occurred: " + e.getMessage());
                    }
                }
                case "0" -> System.out.println("Thank you for using the application!");
                default -> System.out.println("Invalid input. Please select a valid option (1, 2, or 0).");
            }

            if (!choice.equals("0")) {
                System.out.println("Press enter to continue...");
                scan.nextLine();
            }

        } while (!choice.equals("0"));

        scan.close();
    }

    /*public static void userMenu() {

        String choice = "";
        Scanner scan = new Scanner(System.in);
        do {
            printMenu();


            System.out.print("Please select an option from the menu: ");
            choice = scan.nextLine();

            switch (choice) {
                case "1" -> userInputForKafka();
                case "2" -> getDataFromKafka("bookTopic_json");
                case "0" -> System.out.println("Thank you for using the application!");
                default -> System.out.println("Invalid input. Please try again");
            }

            if (!choice.equals("0")) {
                System.out.println("Press enter to continue...");
                scan.nextLine();
            }

        } while (choice.equals("0"));

        scan.close();
    } */


}
