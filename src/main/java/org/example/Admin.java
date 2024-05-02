package org.example;

import java.util.Scanner;

public class Admin extends Staff {
    private static Admin instance;
    private String email;
    private String passWord;
    private String userName;
    private static Scanner scanner = new Scanner(System.in);

    private Admin(String name, String lastName, String email, String userName, String passWord) {
        super(name, lastName);
        this.email = email;
        this.userName = userName;
        this.passWord = passWord;
    }

    public static Admin getInstance() {
        if (instance == null) {
            System.out.println("Enter the name of the Admin:");
            String name = scanner.next();

            System.out.println("Admin's last Name: ");
            String lastName = scanner.next();

            System.out.println("Please enter the Admin email: ");
            String email = scanner.next();

            System.out.println("Please provide user Name(email): ");
            String userName = scanner.next();

            System.out.println("Please enter your password carefully !");
            String passWord = scanner.next();

            instance = new Admin(name, lastName, email, userName, passWord);
        } else {
            System.out.println("Maximum of one Admin !");
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isValidPassword() {

        return true;
    }

    public static void main(String[] args) {
        Admin admin = Admin.getInstance();
        System.out.println("Admin Email: " + admin.getEmail());
        System.out.println("Admin Username: " + admin.getUserName());
    }
}
