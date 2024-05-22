package org.example;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * Contains the info of a user
 * @author Joseph Josue Forestal
 */
public class Users {
    //
    private Days zooDays;
    private String firstName;
    private String lastName;
    private int age;
    private static Scanner sc = new Scanner(System.in);
    private List<Tickets> transactions;

    private String gmail;

    {
        {
            while (true) {
                System.out.println("Provide valid Email (exa1mple3@gmail.com) please:");
                String email = sc.next();

                if (EmailSenderClass.isEmailValid(email)) {
                    System.out.println("Successfully !");
                    this.gmail = email;
                    break;
                }
            }

        }
    }

    /**
     * Constructor for user
     *
     * @param firstName this user's first name
     * @param lastName  this user's last name
     * @param age       this user's age
     * @param zooDays   the current zoo days
     */
    public Users(String firstName, String lastName, int age, Days zooDays) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.transactions = new ArrayList<>();
        this.zooDays = zooDays;
    }

    /**
     * Get this user firstName
     *
     * @return String user first name
     */

    public String getFirstName() {
        return firstName;
    }

    /**
     * Get this user lastName
     *
     * @return String user lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get this user's age
     *
     * @return int user's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Get scanner (Not use)
     *
     * @return scanner (Not use)
     */
    public static Scanner getSc() {
        return sc;
    }

    /**
     * Geet this user gmail
     *
     * @return String user gmail (not use)
     */
    public String getGmail() {
        return gmail;
    }

    /**
     * Get the current users transactions
     * @return A list of transactions
     */
    public List<Tickets> getTransactions() {
        return transactions;
    }

    /**
     * Method to book a ticket for the user based on the reservations times of the user
     */
    public void bookTickets() {
        LocalDateTime reservationTime = null;
        boolean validTime = false;
        // Loop until a valid reservation time is entered
        while (!validTime) {
            System.out.println("Enter reservation date and time (YYYY-MM-DDT(T constant)HH:MM):");
            String dateTimeInput = sc.next().trim();


            if (dateTimeInput.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}$")) {
                reservationTime = LocalDateTime.parse(dateTimeInput);
                validTime = true; // Mark the time as valid to exit the loop
                break; // break is powerful also
            } else {
                System.out.println("Invalid input");
                validTime = false;
            }

        }

        System.out.println("Is this ticket for yourself? (yes/no)");
        String response = sc.next().toLowerCase();

        List<Person> persons = new ArrayList<>();
        if (response.equalsIgnoreCase("yes")) {
            persons.add(new Person(this.age, this.firstName, this.lastName));
        } else {
            int quantity = 0;
            boolean validInput = false;

            while (!validInput) {
                System.out.println("How many tickets do you want to book?");
                if (sc.hasNextInt()) {
                    quantity = sc.nextInt();
                    sc.nextLine(); // clear the buffer
                    if (quantity > 0) {
                        validInput = true;
                    } else {
                        System.out.println("Please enter a positive number.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.next(); // consume the non-integer input
                }
            }

            for (int i = 0; i < quantity; i++) {
                int age = 0;
                validInput = false;

                while (!validInput) {
                    System.out.println("Enter age for ticket #" + (i + 1) + ":");
                    if (sc.hasNextInt()) {
                        age = sc.nextInt();
                        sc.nextLine(); // clear the buffer
                        if (age >= 0) {
                            validInput = true;
                        } else {
                            System.out.println("Age cannot be negative. Please enter a valid age.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid number.");
                        sc.next(); // consume the non-integer input
                    }
                }
                System.out.println("Enter first name for ticket #" + (i + 1) + ":");
                String firstName = sc.nextLine();
                System.out.println("Enter last name for ticket #" + (i + 1) + ":");
                String lastName = sc.nextLine();
                persons.add(new Person(age, firstName, lastName));
            }
        }
        // Call the existing bookTicket method from Tickets class
        Tickets tickets = Tickets.bookTicket(zooDays, reservationTime, gmail, persons);
        if (tickets != null) this.transactions.add(tickets);
    }

    /**
     * Cancel a transaction in the ucrrent user's List of transactions(history)
     * @param ticketId take a string id for the ticket booked.
     */
        public void cancelTicket (String ticketId){
            for (Tickets ticket : transactions) {
                if (ticket.getId().equals(ticketId)) {
                    ticket.setStatus("UnBooked");
                    System.out.println("Ticket cancelled successfully. ID: " + ticketId);
                    return;
                }
            }
            System.out.println("Ticket not found with ID: " + ticketId);
        }

    /**
     * This method display transaction history of the user if he booKed multiple ticket
     */
    public void displayTransactionHistory () {
            System.out.println("Transaction History for User: " + gmail);
            for (Tickets ticket : transactions) {
                System.out.println("Ticket ID: " + ticket.getId() + ", Price: $" + ticket.getPrice() + ", Status: " + ticket.getStatus());
            }
        }

    }
