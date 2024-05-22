package org.example;

import java.io.*;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains the info of an Admin
 * this class extended staff and impliment maintainable
 * This class follow the singleton pattern which means only one instance can be made
 * @author Joseph Josue Forestal
 */
public class Admin extends Staff implements Maintainable {
    //
    private static Scanner sc = new Scanner(System.in);
    private static Admin instance;
    private String email;
    private String passWord;
    private String userName;

    /**
     * Constructor
     * @param name the first name of the admin
     * @param lastName the last name of the admin
     * @param email the email of the admin
     * @param userName the userName of the admin
     * @param passWord the password of the admin
     */
    private Admin(String name, String lastName, String email, String userName, String passWord) {
        super(name, lastName);
        this.email = email;
        this.userName = userName;
        this.passWord = passWord;
    }

    /**
     * Get an admin instance baseed of the singleton pattern(only one instance can be made)
     * @return An Admin object
     */
    public static Admin getInstance() {
        if (instance == null) {
            System.out.println("Is path already given (Y/N) ");
            String answer = sc.next();
            String pathName = "/Users/josue/Library/Mobile Documents/com~apple~TextEdit/Documents/AdminOfPorject.rtf";
            if(answer.equalsIgnoreCase("N")) {
                System.out.println("Enter admin file pathName for info: ");
                pathName = sc.next();
                return instance  = readAdminFromFile(pathName);
            }else return instance = readAdminFromFile(pathName);
        } else {
            System.out.println("Maximum of one Admin !");
        }
        return instance;
    }

    /**
     * Get the admin's email
     * @return the email (not use)
     */

    public String getEmail() {
        return email;
    }

    /**
     * Get the admin's password
     * @return string password
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * Get the admin userName
     * @return string userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Read the admin from a file
     * @param fileAsPath the file name as path
     * @return an admin object
     */
    public static Admin readAdminFromFile(String fileAsPath) {
        String name = "", lastName = "", email = "", userName = "", passWord = "";

        try (BufferedReader adminReader = new BufferedReader(new FileReader(fileAsPath))) {
            String line;
            while ((line = adminReader.readLine()) != null) {
                if (line.startsWith("First name:")) {
                    name = line.substring("First name:".length()).trim();
                    name = name.replace("\\","");
                } else if (line.startsWith("Last name :")) {
                    lastName = line.substring("Last name :".length()).trim();
                    lastName = lastName.replace("\\","");
                } else if (line.startsWith("G@mail:")) {
                    String potentialEmail = line.substring("G@mail :".length()).trim();
                        email = potentialEmail;
                    email = email.replace("\\", "");
                } else if (line.startsWith("User name:")) {
                    userName = line.substring("User name:".length()).trim();
                    userName = userName.replace("\\", "");

                } else if (line.startsWith("User name :")) {
                    userName = line.substring("User name :".length()).trim();
                    userName = userName.replace("\\", "");

                } else if (line.startsWith("Password :")) {
                    passWord = line.substring("Password :".length()).trim();
                    passWord  = passWord.replace("}","");
                } else if (line.startsWith("Password:")) {
                    passWord = line.substring("Password:".length()).trim();
                    passWord  = passWord.replace("}","");
                }
            }
            return new Admin(name, lastName, email, userName, passWord);
        } catch (FileNotFoundException e) {
            System.out.println("Admin file not found: " + fileAsPath);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the admin file: " + fileAsPath);
        }
        return null; // Return null if there was an error or file was not found
    }


    /**
     * Update the opening hours
     * @param zooDays take the days (time ) to update the opening hours
     */
    @Override
    public void updateOpeningHours(Days zooDays) {
        Scanner sc = new Scanner(System.in);
        String regex = "(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])"; // HH:mm:ss format
        Pattern pattern = Pattern.compile(regex);

        LocalTime time = null;
        boolean validInput = false;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss"); // Single or double digit hours

        while (!validInput) {
            System.out.println("Enter the new Closed time (HH:mm:ss):");
            String timeString = sc.nextLine();

            Matcher matcher = pattern.matcher(timeString);
            if (matcher.matches()) {
                try {
                    time = LocalTime.parse(timeString, formatter);
                    zooDays.setOpenTime(time);

                    validInput = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Could not parse date");
                }
            } else {
                System.out.println("Invalid time format. Please enter time in HH:mm:ss format.");
            }
        }

        System.out.println("Time entered: " + time);

    }

    /**
     * Update Ticket prices
     * @param ticket take a current ticket object
     */
    @Override
    public void updateTicketPrices(Tickets ticket) {
        Tickets.setPricing(sc);
    }

    /**
     * Read from file name and put the task into a task list
     */
    @Override
    public void performMaintenanceTasks() {
        System.out.println("Enter file name");
        String FileName = sc.next();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FileName));
            String lineTask;
            while ((lineTask = bufferedReader.readLine()) != null){
                ZooManagement.tasks.add(lineTask);
            }
        }catch (IOException e){
            System.out.println("File not found :" + e.getMessage());
        }
    }

    /**
     * Update the closed time
     * @param zooDays the current days(time) object
     */
    @Override
    public void UpdateClosedTimeHours(Days zooDays) {
        Scanner sc = new Scanner(System.in);
        String regex = "(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])"; // HH:mm:ss format
        Pattern pattern = Pattern.compile(regex);

        LocalTime time = null;
        boolean validInput = false;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss"); // Single or double digit hours

        while (!validInput) {
            System.out.println("Enter the new Closed time (HH:mm:ss):");
            String timeString = sc.nextLine();

            Matcher matcher = pattern.matcher(timeString);
            if (matcher.matches()) {
                try {
                    time = LocalTime.parse(timeString, formatter);
                    zooDays.setClosedTime(time);

                    validInput = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Could not parse date");
                }
            } else {
                System.out.println("Invalid time format. Please enter time in HH:mm:ss format.");
            }
        }

        System.out.println("Time entered: " + time);
    }

    /**
     * View the user transaction
     * @param users A set of users
     * @param userName  the userName that we searched.
     */
    public void viewUserTransactionHistory(Set<Users> users, String userName) {
        System.out.println("User Transaction History:");
        for (Users user : users) {
            if(userName.equalsIgnoreCase(user.getFirstName())) {
                user.displayTransactionHistory();
            }
        }
    }
}
