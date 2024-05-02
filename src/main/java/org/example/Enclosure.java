package org.example;
import java.util.ArrayList;
import java.util.List;
public class Enclosure {
    private String id;
    private String name;
    private List<Animal> animals;

    public Enclosure(String name) {
        this.name = name;
        this.id = "ENCLOSURE-" + generateUniqueId();
        this.animals = new ArrayList<>();
    }

    private String generateUniqueId() {
        return String.valueOf(System.nanoTime());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public  void sortingBaseEnclosure(){
//logic here
    }
}
