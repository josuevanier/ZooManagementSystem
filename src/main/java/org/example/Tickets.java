package org.example;

import jakarta.mail.MessagingException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Tickets {
    private double price;
    private String id;
    private LocalDateTime reservationTime;
    private String userEmail;
    private String status;
    private static int nextId = 1;
    private static Map<String, LocalDateTime> userReservations = new HashMap<>();

    public Tickets(double price, LocalDateTime reservationTime, String userEmail) {
        this.price = price;
        this.reservationTime = reservationTime;
        this.userEmail = userEmail;
        generateUniqueId();
        this.status = "Booked";
    }

    private void generateUniqueId() {
        this.id = String.format("Zoo-%d", nextId++);
    }

    public double getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getStatus() {
        return status;
    }

    public static void bookTicket(double price, LocalDateTime reservationTime, String userEmail) {
            if (userReservations.containsKey(userEmail) && userReservations.get(userEmail).isEqual(reservationTime)) {
                System.out.println("You already have a reservation at this time.");
                return;
            }

            // Create a new ticket
            Tickets ticket = new Tickets(price, reservationTime, userEmail);
            System.out.println("Ticket booked successfully. ID: " + ticket.getId());

            // Update user's reservation time
            userReservations.put(userEmail, reservationTime);

            // Send confirmation email
            try {
                EmailSenderClass.sendEmail("Reservation Confirmation", ticket.getId(), userEmail);
            } catch (MessagingException e) {
                System.out.println("Failed to send confirmation email: " + e.getMessage());
            }
        }

    public static void main(String[] args) {
        LocalDateTime reservationTime1 = LocalDateTime.now().plusDays(1);
        bookTicket(13.0, reservationTime1, "user1@gmail.com");

        bookTicket(12.5, reservationTime1, "user1@gmail.com");
    }

}
