package org.example;
import java.util.Properties;
import java.util.Scanner;

public class Users {
    String firstName;
    String lastName;
    int age;
    static Scanner sc = new Scanner(System.in);

    String gmail;{
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

    public static void main(String[] args) {
        Users users = new Users("Josue,","2",4);
    }

}
