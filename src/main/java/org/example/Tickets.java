package org.example;

import jakarta.mail.MessagingException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Contains the info of a ticket
 * Tickets contains everything that the user can pay
 * @author Joseph Josue Forestal
 */
public class Tickets {
    private static double childPrice;
    private static double adultPrice;
    private static double seniorPrice;
    private double price;
    private String id;
    private LocalDateTime reservationTime;
    private String userEmail;
    private String status;
    private static int nextId = 1;
    private  static Scanner scanner = new Scanner(System.in);
    private static Map<String, LocalDateTime> userReservations = new HashMap<>();

    public Tickets(LocalDateTime reservationTime, String userEmail, List<Person> persons) {
        this.price = calculateTotalPrice(persons);
        this.reservationTime = reservationTime;
        this.userEmail = userEmail;
        generateUniqueId();
        this.status = "Booked";
    }

    /**
     * This method generate unique id for the ticket id
     */
    private void generateUniqueId() {
        this.id = String.format("Zoo-%d", nextId++);
    }

    /**
     * This method get the price
     * @return double price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Thi method is to set the price for each data field child, adults and seniors/elders
     * @param scanner
     */
    public static void setPricing(Scanner scanner) {
        childPrice = promptForDouble(scanner, "Enter price for child: ");
        adultPrice = promptForDouble(scanner, "Enter price for adults");
        seniorPrice = promptForDouble(scanner, "Enter price for seniors: ");
    }

    /**
     * Get children's price
     * @return double
     */
    public static double getChildPrice() {
        return childPrice;
    }

    public static double getAdultPrice() {
        return adultPrice;
    }

    /**
     * Get elders' price
     * @return double price
     */
    public static double getSeniorPrice() {
        return seniorPrice;
    }

    /**
     * Promt to get the values for prices
     * @param scanner the scanner system input
     * @param prompt is a String message
     * @return double value/prices in one
     */
    private static double promptForDouble(Scanner scanner, String prompt) {
        double value = 0;
        boolean valid = false;

        while (!valid) {
            System.out.println(prompt);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                if (value >= 0) {  // Ensure the price is not negative.
                    valid = true;
                } else {
                    System.out.println("Price must be non-negative. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid double.");
                scanner.next(); // Consume the invalid input
            }
        }
        return value;
    }

    /**
     * Set price (not use)
     * @param value doublethe value
     */
    public void setPrice(double value){
        this.price = value;
    }

    /**
     * This method calculate the toal price for each persons
     * @param persons a list persons to check the total price
     * @return double price
     */
    private static double calculateTotalPrice(List<Person> persons) {
        double totalPrice = 0.0;
        for (Person person : persons) {
            if (person.getAge() < 12) {
                totalPrice += childPrice;
            } else if (person.getAge() >= 65) {
                totalPrice += seniorPrice;
            } else {
                totalPrice += adultPrice;
            }
        }
        return totalPrice;
    }

    /**
     * This method return ticket id
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *This method get the current ticket status
     * @return  String status of thecurrent ticker
     */
    public String getStatus() {
        return status;
    }

    /**
     * Thi method book a ticket
     * @param days the current days (Days object)
     * @param reservationTime the reservation time
     * @param userEmail the email of the user
     * @param persons and a list persons
     * @return
     */
    public static Tickets bookTicket(Days days, LocalDateTime reservationTime, String userEmail, List<Person> persons) {
        LocalTime reservationLocalTime = reservationTime.toLocalTime(); // Extract the time component
        // Check if the zoo is open at the specified time
        if (!days.isOpen(reservationTime)) {
            System.out.println("The zoo is closed at the specified date and time.");
            return null;
        }
        // Check if the zoo has special events or schedules at the specified date and time
        LocalDate date = reservationTime.toLocalDate();
        if (days.getSpecialSchedule().containsKey(date)) {
            LocalTime[] specialTimes = days.getSpecialSchedule().get(date);
            if (specialTimes != null && (reservationTime.toLocalTime().isBefore(specialTimes[0]) || reservationTime.toLocalTime().isAfter(specialTimes[1]))) {
                System.out.println("The zoo is closed at the specified date and time according to special schedule.");
                return null;
            }
        }
        // Create a new ticket
        Tickets ticket = new Tickets(reservationTime, userEmail, persons);
        ticket.price = ticket.calculateTotalPrice(persons); // Set calculated price
        System.out.println("Ticket(s) booked successfully. ID: " + ticket.getId());
        // Update user's reservation time
        userReservations.put(userEmail, reservationTime);
        // Send confirmation email
        sendConfirmationEmail(userEmail, reservationTime, ticket.getPrice(), ticket.getId());
        return ticket;
    }

    /**
     * This method sends email confirmation to the user
     * @param userEmail the user's email
     * @param reservationTime the local reservation time
     * @param totalPrice the total price
     * @param ticketId the ticket id
     */
    private static void sendConfirmationEmail(String userEmail, LocalDateTime reservationTime, double totalPrice, String ticketId) {
        // Format the reservation time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM d, uuuu 'at' h:mm a");
        String formattedReservationTime = reservationTime.format(formatter);

        // Prepare the email content
        String emailSubject = "Reservation Confirmation";
        String emailBody = "Dear Customer,\n\n"
                + "Your reservation has been confirmed!\n"
                + "Reservation Time: " + formattedReservationTime + "\n"
                + "Total Amount: $" + totalPrice + "\n"
                + "Ticket ID: " + ticketId + "\n\n"
                + "Thank you for choosing our zoo!\n\n"
                + "Best regards,\n"
                + "Zoo Management Team";

        // Send the email
        try {
            EmailSenderClass.sendEmail(userEmail, emailSubject, emailBody);
            System.out.println("Confirmation email sent successfully to " + userEmail);
        } catch (MessagingException e) {
            System.out.println("Failed to send confirmation email: " + e.getMessage());
        }
    }

    /**
     * Set status of the current ticket
     * @param status booked or unbooked
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
