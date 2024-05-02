package org.example;

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
    public void readFromFIle(){

    }
}
