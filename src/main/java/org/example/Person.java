package org.example;

/**
 * Contains the info of a simple person
 * When we want to book ticket for different person that is not the current user
 * @author  Joseph Josue Forestal
 */
public class Person {
    // Person class
    private int age;
    private String firstName;
    private String lastName;

    /**
     * Constructor to create a simple person object
     * @param age this person's age
     * @param firstName this person's firstName
     * @param lastName this person's last name
     */
    public Person(int age, String firstName, String lastName) {
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Get this person age
     * @return  integer age
     */
    public int getAge() {
        return age;
    }
}
