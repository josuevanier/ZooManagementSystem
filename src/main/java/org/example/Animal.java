package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Contains the info of a simple animal object
 * @author Joseph Josue Forestal
 */
public class Animal {
    private String id;
    private String species;
    private static int ID;

    /**
     * Constructor for animal
     * @param species name of the animal(e.g (type) lion)
     */
    public Animal(String species) {
        this.species = species;
        this.id = "ANIMAL-" + ++ID;
    }


    /**
     * Get the animal ID
     * @return string id
     */
    public String getId() {
        return id;
    }

    /**
     * Get the current animal species
     * @return string species name(e.g lion)
     */
    public String getSpecies() {
        return species;
    }




    /**
     * Method that read from a given file to put animals into a lIST
     * @param fileName The file name
     * @return A list of all the animals  read from that file otherwise exception
     */
    public static List<Animal> readFromFile(String fileName) {
        List<Animal> animals = new ArrayList<>();
        try  {
            BufferedReader animalReader  = new BufferedReader(new FileReader(fileName));
           String line;
            while ((line = animalReader.readLine()) != null) {
                String[] data = line.split(",");
                for (String species : data) {
                    String trueSpecies = species.trim();
                    animals.add(new Animal(trueSpecies));
                }
            }
            animalReader.close();
        } catch (IOException e) {
            System.out.println("Animal file not found.");
        }
        return animals;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Path");

        String pathName =  sc.next();
        readFromFile(pathName);
    }
}

