package org.example;
import java.util.*;

/**
 * Contains the info of an Enclosure that contains animals
 * Thus this class allow us to regroup animals together into one section
 * @author Joseph Josue Forestal
 */
public class Enclosure {
    private String id;
    private String name;
    static  int i = 0;
    private Map<String, Map<String, Animal>> animalsBySpecies;


    /**
     * Enclosure constructor
     * @param name  String name of the enclosure
     */
    public Enclosure(String name) {
        this.name = name;
        this.id = "ENCLOSURE-" + generateUniqueId();
        this.animalsBySpecies = new HashMap<>();
    }

    /**
     * Generate uniq id for enclosures
     * @return string id
     */
    private String generateUniqueId() {
        return String.valueOf(++i);
    }

    /**
     * Get the enclosure's id
     * @return the curent string id
     */
    public String getId() {
        return id;
    }

    /**
     * Get the enclosure name
     * @return the enclosure's id as a string
     */
    public String getName() {
        return name;
    }


    /**
     * This method add an animal to enclosure
     * @param animal take an animal object
     */
   public void addAnimal(Animal animal) {
        String species = animal.getSpecies();
        animalsBySpecies.putIfAbsent(species, new HashMap<>());
        animalsBySpecies.get(species).put(animal.getId(), animal);
    }

    /**
     * This method get animal by species
     * @return String animals just for printing
     */
    public String getAnimalsBySpecies() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Map<String, Animal>> entry : animalsBySpecies.entrySet()) {
            String species = entry.getKey();
            Map<String, Animal> animals = entry.getValue();
            result.append(species).append(":");
            for (String animalId : animals.keySet()) {
                result.append(" ").append(animalId);
            }
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * This method display animals in the enclosre
     * @param enclosures list of enclosures
     */
    public  void displayAnimals(List<Enclosure> enclosures) {
        Scanner  scanner = new Scanner(System.in);
        System.out.println("1. Display animals based on species ");
        System.out.println("2. Display all animals (default)");
        int choice = 2; // Default choice
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left by nextInt()
        }

        if (choice == 1) {
            System.out.print("Enter species to display: ");
            String species = scanner.nextLine().trim();
            boolean found = false;
            for (Enclosure enclosure : enclosures) {
                if (enclosure.getName().equalsIgnoreCase(species)) {
                    enclosure.displayAnimalsBySpecies(species);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No animals of species " + species + " found.");
            }
        } else {
            for (Enclosure enclosure : enclosures) {
                System.out.println("Enclosure species (" + enclosure.getName() + ") -> Id: " + enclosure.getId());
                enclosure.displayAllAnimals();
            }
        }
    }

    /**
     * Dispplayt all animals
     */
    private  void displayAllAnimals() {
        System.out.println("Animals in " + name + ":");
        for (Map.Entry<String, Map<String, Animal>> entry : animalsBySpecies.entrySet()) {
            String species = entry.getKey();
            Map<String, Animal> animals = entry.getValue();
            System.out.println(species + ":");
            for (Animal animal : animals.values()) {
                System.out.println(" - " + animal.getId());
            }
        }
    }

    /**
     * Display animals by species
     * @param species species string to search for the animals
     */
    private void displayAnimalsBySpecies(String species) {
        if (animalsBySpecies.containsKey(species)) {
            System.out.println("Animals of species " + species + " in " + name + ":");
            Map<String, Animal> animals = animalsBySpecies.get(species);
            for (Animal animal : animals.values()) {
                System.out.println(" - " + animal.getId());
            }
        } else {
            System.out.println("No animals of species " + species + " found in " + name + ".");
        }
    }


    /**
     * This method sort animal using tree map
     */
    public void sortAnimals() {
        Map<String, Map<String, Animal>> sortedAnimalsBySpecies = new TreeMap<>(animalsBySpecies);
        animalsBySpecies.clear();
        animalsBySpecies.putAll(sortedAnimalsBySpecies);
        System.out.println(getAnimalsBySpecies());
    }
}
