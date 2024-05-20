package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class NormalEmployee extends Staff implements Maintainable {

    private String id;
    private String gmail;
    private static Scanner sc = new Scanner(System.in);
    private List<String> transactionHistory;

    public NormalEmployee(String firstName, String lastName, double hours, String gmail) {
        super(firstName, lastName, hours);
        this.gmail = gmail;
        this.id = super.staffGetId();
        this.transactionHistory = new ArrayList<>();
    }

    public double getHours() {
        return hours;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setId(String id) {
        this.id = id;
    }


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
        }catch(IOException e){
        System.out.println("File issues");
    }
        return staffList;
}

    @Override
    public void updateOpeningHours(Days zooDays) {
        System.out.println("Normal employees do not have permission to update opening hours.");
    }





    @Override
    public void updateTicketPrices(Tickets tickets) {
        Tickets.setPricing(sc);
    }



    @Override
    public void performMaintenanceTasks() {
        System.out.println(ZooManagement.tasks);
    }

    @Override
    public void UpdateClosedTimeHours(Days days) {
        System.out.println("Normal employee cannot update closed time hours");
    }
    public  void Transaction(String transaction){
        this.transactionHistory.add(transaction);
    }


    public static void main(String[] args) {
       TreeSet<NormalEmployee> employees =  readStaffFromFile("/Users/josue/EmployeeReadFile");

        for(NormalEmployee employee : employees){
            System.out.println("Employee " + employee.getFirstName() + " " + employee.getHours() + " " + employee.gmail);
        }
    }
}
