package model.species;

import java.util.ArrayList;

public class Specie {

    private String specieName = new String("");
    private int specieId = -1;

    /**
     * All available algorithms
     */
    private static ArrayList<Specie> species = new ArrayList<>();

    public Specie() {
        this.specieName = new String("No name");
        this.specieId = Specie.getSpecies().size();
        Specie.getSpecies().add(this);
    }

    public Specie(String name) {
        this.specieName = new String(name);
        this.specieId = Specie.getSpecies().size();
        Specie.getSpecies().add(this);
    }

    public static ArrayList<Specie> getSpecies() {
        return species;
    }

    public String getSpecieName() {
        return specieName;
    }

    public void setSpecieName(String specieName) {
        this.specieName = specieName;
    }

    public int getSpecieId() {
        return specieId;
    }

    public void setSpecieId(int specieId) {
        this.specieId = specieId;
    }
}
