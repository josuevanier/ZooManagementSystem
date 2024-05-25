package org.example;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Contains the info of a simple animal object
 * This class is really simple just to keep track of animals
 * @author Joseph Josue Forestal
 */
public class Animal {
    //
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
    public static void readFromFile(String fileName, List<Enclosure> enclosures) {
        try(BufferedReader animalReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = animalReader.readLine()) != null) {
                String[] data = line.split(",");
                for (String species : data) {
                    String trueSpecies = species.trim();
                    Enclosure existingEnclosure = null;

                    // Check if the enclosure already exists
                    for (Enclosure enclosure : enclosures) {
                        if (trueSpecies.equalsIgnoreCase(enclosure.getName())) {
                            existingEnclosure = enclosure;
                            break;
                        }
                    }

                    if (existingEnclosure != null) {
                        // Add the animal to the existing enclosure
                        Animal animal = new Animal(trueSpecies);
                        existingEnclosure.addAnimal(animal);
                        System.out.println("Animal  of " + species + " (" + animal.getId() + ") added " + "to " + existingEnclosure.getId() );
                    } else {
                        // Create a new enclosure and add the animal
                        Enclosure newEnclosure = new Enclosure(trueSpecies);
                        Animal animal = new Animal(trueSpecies);
                        newEnclosure.addAnimal(animal);
                        enclosures.add(newEnclosure);
                        System.out.println("A new enclosure has been created for " + trueSpecies + "\n" +
                                "Animal added: " + animal.getId() + " of " + newEnclosure.getName() + " species");
                    }
                }
            }
        } catch(IOException E){
            System.out.println("Animal file not found.");
        }
    }

    public static void main(String[] args) {
        List<Enclosure> enclosures = new ArrayList<>();
        readFromFile("src/main/java/org/example/AnimalFromFile.rtf",enclosures);
        System.out.println(Arrays.toString(enclosures.toArray()));
    }
}

