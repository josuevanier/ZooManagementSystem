package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class ZooManagement {
    HashSet<Users> user = new HashSet<>();
    ArrayList< ? extends Staff> staff;

    static Scanner sc = new Scanner(System.in);
   static String zooName;{
        System.out.println("Enter The name of the Zoo !");
            zooName = sc.next();
    }
    public static String getZooName(){
       return zooName;
    }

public  void sortingBaseEnclosure(ArrayList<Enclosure> enclosures){

}

    public void sortingBasedOnGmail(ArrayList<Users> users){

    }

    public void sortingBasedOnStaffName(ArrayList< ? extends Staff> staffs){}
}


