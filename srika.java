import java.io.*;
import java.util.*;

public class Main {
    
    // Inner class to hold feedback data
    static class Feedback {
        private String name;
        private String feedbackText;

        // Method to set feedback values
        public void setFeedback(String name, String feedbackText) {
            this.name = name;
            this.feedbackText = feedbackText;
        }

        // Method to return formatted feedback string
        @Override
        public String toString() {
            return "Name: " + name + "\nFeedback: " + feedbackText + "\n--------------------------";
        }
    }

    // List to store feedback objects
    private static List<Feedback> feedbackList = new ArrayList<>();
    private static final String FILE_PATH = "feedback.txt";  // File path for storing feedback

    // Method to load feedback from file
    private static void loadFeedbackFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String name;
            String feedbackText;
            while ((name = reader.readLine()) != null && (feedbackText = reader.readLine()) != null) {
                Feedback feedback = new Feedback();
                feedback.setFeedback(name, feedbackText);
                feedbackList.add(feedback);  // Add feedback to list
            }
        } catch (IOException e) {
            System.out.println("No previous feedback found. Starting fresh.");
        }
    }

    // Method to save feedback to file
    private static void saveFeedbackToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Feedback feedback : feedbackList) {
                writer.write(feedback.name);
                writer.newLine();
                writer.write(feedback.feedbackText);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving feedback to file.");
        }
    }

    // Method to collect feedback
    private static void collectFeedback() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // Input validation: Check if name is empty
        if (name.trim().isEmpty()) {
            System.out.println("Name cannot be empty. Please try again.");
            return;
        }

        System.out.print("Enter your feedback: ");
        String feedbackText = scanner.nextLine();

        // Input validation: Check if feedback is empty
        if (feedbackText.trim().isEmpty()) {
            System.out.println("Feedback cannot be empty. Please try again.");
            return;
        }

        // Create Feedback object and add to list
        Feedback feedback = new Feedback();
        feedback.setFeedback(name, feedbackText);
        feedbackList.add(feedback);

        // Save feedback to file
        saveFeedbackToFile();

        System.out.println("Thank you for your feedback!\n");
    }

    // Method to display all collected feedback
    private static void displayFeedback() {
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback collected yet.\n");
        } else {
            System.out.println("\nAll Feedbacks:");
            for (Feedback feedback : feedbackList) {
                System.out.println(feedback);
            }
        }
    }

    // Main method to run the feedback system
    public static void main(String[] args) {
        loadFeedbackFromFile();  // Load feedback from file at the start

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        // Main menu loop
        while (!exit) {
            // Display menu
            System.out.println("Feedback System");
            System.out.println("1. Collect Feedback");
            System.out.println("2. View All Feedback");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    collectFeedback();
                    break;
                case 2:
                    displayFeedback();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
