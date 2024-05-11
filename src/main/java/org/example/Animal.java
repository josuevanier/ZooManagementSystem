package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Animal {
    private String id;
    private String species;
    private String enclosureId;

    public Animal(String species, String enclosureId) {
        this.species = species;
        this.enclosureId = enclosureId;
        this.id = "ANIMAL-" + generateUniqueId();
    }

    private String generateUniqueId() {
        return String.valueOf(System.currentTimeMillis());
    }

    public String getId() {
        return id;
    }

    public String getSpecies() {
        return species;
    }

    public String getEnclosureId() {
        return enclosureId;
    }


    /**
     * Method that read from a given file to put animals into a lIST
     * @param fileName The file name
     * @return A list of all the animals  read from that file otherwise exception
     */
    public static List<Animal> readFromFile(String fileName) {
        List<Animal> animals = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length >= 2) {
                    String name = data[0].trim(); // Trim spaces from the first element
                    String type = data[1].trim(); // Trim spaces from the second element
                    animals.add(new Animal(name, type));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Animal file not found.");
        }
        return animals;
    }
}

