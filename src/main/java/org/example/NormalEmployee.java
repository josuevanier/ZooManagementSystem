package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Class that Contains the info of a normal employee
 * Normal employees is just an admin with less methods
 * extended staff
 * @author Joseph Josue Forestal
 */
public class NormalEmployee extends Staff implements Maintainable {
//
    private String id;
    private String gmail;
    private static Scanner sc = new Scanner(System.in);
    public List<String> transactionHistory;

    /**
     * Constructor of a normal employee
     * @param firstName
     * @param lastName
     * @param hours
     * @param gmail
     */
    public NormalEmployee(String firstName, String lastName, double hours, String gmail) {
        super(firstName, lastName, hours);
        this.gmail = gmail;
        this.id = super.staffGetId();
        this.transactionHistory = new ArrayList<>();
    }

    /**
     * Get the current employee hours
     * @return double hours worked of that employee
     */
    public double getHours() {
        return hours;
    }

    /**
     * Get the firstName of the current employee
     * @return the employee's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get the current employee's last name
     * @return ast name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get the current employee id
     * @return String Id
     */
    public String getId() {
        return id;
    }

    /**
     * Set hours (Not use)
     * @param hours double hours being set
     */
    public void setHours(double hours) {
        this.hours = hours;
    }


    /**
     * READsTAFFfROM FILE
     * @param filename The string file name as a path
     * @return Tree set of emplyee to avoid duplicates
     */
    public static TreeSet<NormalEmployee> readStaffFromFile(String filename) {
        TreeSet<NormalEmployee> staffList = new TreeSet<>(Comparator.comparing(NormalEmployee::getFirstName));
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            double hoursWorked = 0.0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }

                // Split the line into tokens based on ","
                String[] tokens = line.split(",");

                String firstName = null, lastName = null, gmail = null;


                for (String token : tokens) {
                    String[] keyValue = token.split(":");
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();

                    switch (key) {
                        case "FirstName":
                            firstName = value;
                            break;
                        case "LastName":
                            lastName = value;
                            break;
                        case "Hours":
                            value = value.trim();
                            hoursWorked = Double.parseDouble(value);
                            break;
                        case "Email":
                            gmail = value;
                            break;
                    }
                }

                if (firstName != null && lastName != null && gmail != null && hoursWorked != 0.0) {
                    staffList.add(new NormalEmployee(firstName, lastName, hoursWorked, gmail));
                }

            }
            System.out.println("Employees added successfully !");
        }catch(IOException e){
        System.out.println("File not found ");
    }
        return staffList;
}

    /**
     * Update pseudo update (since he can't actually update ) the openeing hours
     * @param zooDays the current zooo days
     */
    @Override
    public void updateOpeningHours(Days zooDays) {
        System.out.println("Normal employees do not have permission to update opening hours.");
    }


    /**
     * Set/ Update the current prices
     * @param tickets Tickets object
     */
    @Override
    public void updateTicketPrices(Tickets tickets) {
        Tickets.setPricing(sc);
    }


    /**
     * For the normal employee he can see the tasks
     */
    @Override
    public void performMaintenanceTasks() {
        System.out.println(ZooManagement.tasks);
    }

    /**
     * Pseudo Update closed time since the employee can't
     * @param days
     */
    @Override
    public void UpdateClosedTimeHours(Days days) {
        System.out.println("Normal employee cannot update closed time hours");
    }

    /**
     * The employees can add Transactions their history
     * @param transaction transaction string being add to its transaction history
     */
    public  void Transaction(String transaction){
        this.transactionHistory.add(transaction);
    }

    /**
     * Get the current employee history
     */
    public void getTransactionHistory() {
        System.out.print("History : ");
        for(String transac : transactionHistory){
            if(!transac.isEmpty() && !transac.isBlank()){
                System.out.print( transac + " ");
            }
        }
    }

    public static void main(String[] args) {

    }
}
