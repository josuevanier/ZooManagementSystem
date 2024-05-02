package org.example;

public class  Staff {
    double hours;
    String firstName;
    String lastName;
 private String id;
    static  int nextId = 0;

    public Staff(){

    }
    public  String staffGetId(){
        return id;
    }
    public Staff(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = String.format("STAFF-"+nextId++);
    }

    public static void main(String[] args) {

    }
}
