package org.example;
import java.util.*;

/**
 * Contains the info of an Enclosure
 * @author Joseph Josue Forestal
 */
public class Enclosure {
    private String id;
    private String name;
    static  int i = 0;
    private Map<String, Map<String, Animal>> animalsBySpecies;


    /**
     *
     * @param name
     */
    public Enclosure(String name) {
        this.name = name;
        this.id = "ENCLOSURE-" + generateUniqueId();
        this.animalsBySpecies = new HashMap<>();
    }

    private String generateUniqueId() {


        return String.valueOf(++i);

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }



   public void addAnimal(Animal animal) {
        String species = animal.getSpecies();
        animalsBySpecies.putIfAbsent(species, new HashMap<>());
        animalsBySpecies.get(species).put(animal.getId(), animal);
    }

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

    public  void displayAnimals(List<Enclosure> enclosures) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Display animals based on species ");
        System.out.println("2. Display all animals (default)");
        int choice = 2;
        if(scanner.hasNextInt()){
             choice  = scanner.nextInt();
        }
        if (choice == 1){
            System.out.print("Enter species to display: ");
            String species = scanner.nextLine().trim();
            for(Enclosure enclosure : enclosures) {
                if(species.equalsIgnoreCase(enclosure.getName()))displayAnimalsBySpecies(species);

            }
        }else {
            for(Enclosure enclosure : enclosures) {
                System.out.println("Enclosre species " + "(" + enclosure.getName()+ ")->" + "Id: " + enclosure.getId()  );
                enclosure.displayAllAnimals();
            }
        }
        }


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




    public void sortAnimals() {
        Map<String, Map<String, Animal>> sortedAnimalsBySpecies = new TreeMap<>(animalsBySpecies);
        animalsBySpecies.clear();
        animalsBySpecies.putAll(sortedAnimalsBySpecies);
        System.out.println(getAnimalsBySpecies());
    }
}
