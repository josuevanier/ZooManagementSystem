package org.example;

/**
 * Staff is an abstract class
 * Contains info for staff ( sets of methods and field (blue-prints ))
 * @author Joseph Josue Forestal
 */
public abstract class  Staff {
    //
    double hours;
    String firstName;
    String lastName;
 private String id;
    static  int nextId = 0;

    /**
     * Default constructor
     */
    public Staff(){
    }

    /**
     * Get  this staff id
     * @return this staff ID
     */
    public  String staffGetId(){
        return id;
    }

    /**
     * Constructor for staff with more info (use for Normal employees)
     * @param firstName this staff's first name
     * @param lastName this staff's last name
     * @param hours this staff's hours worked
     */
    public Staff(String firstName, String lastName, double hours){
        this.firstName = firstName;
        this.lastName = lastName;
        this.hours = hours;
        this.id = String.format("STAFF-"+ nextId++);
    }

    /**
     * Constructor for staff with less info(use for Admin)
     * @param firstName this staff's first name
     * @param lastName this staff's last name
     */
    public Staff(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
