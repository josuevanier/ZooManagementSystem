package org.example;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Users {
   private String firstName;
    private String lastName;
    private int age;
    private static Scanner sc = new Scanner(System.in);
    private List<Tickets> transactions;

  private   String gmail;{
        System.out.println("Provide valid Email (exa1mple3@gmail.com) please:" );
        String email = sc.next();
        if(EmailSenderClass.isEmailValid(email)){
            System.out.println("Successfully !");
        this.gmail = email;
        }
    }
    public Users(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.transactions = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public static Scanner getSc() {
        return sc;
    }

    public String getGmail() {
        return gmail;
    }

    public List<Tickets> getTransactions() {
        return transactions;
    }
    public void bookTickets(double price, int quantity, LocalDateTime reservationTime) {
        for (int i = 0; i < quantity; i++) {
            Tickets.bookTicket(price, reservationTime, gmail);
        }
    }


    public void cancelTicket(String ticketId) {
        for (Tickets ticket : transactions) {
            if (ticket.getId().equals(ticketId)) {
                transactions.remove(ticket);
                System.out.println("Ticket cancelled successfully. ID: " + ticketId);
                return;
            }
        }
        System.out.println("Ticket not found with ID: " + ticketId);
    }
    public void displayTransactionHistory() {
        System.out.println("Transaction History for User: " + gmail);
        for (Tickets ticket : transactions) {
            System.out.println("Ticket ID: " + ticket.getId() + ", Price: $" + ticket.getPrice() + ", Status: " + ticket.getStatus());
        }
    }

    public static void main(String[] args) {

      Users users = new Users("Josue,","2",4);

    }

}
