package io.github.josiasbarreto_dev.desafio_naruto.model;

import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
public class Character{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String village;
    private ArrayList<String> jutsus = new ArrayList<>();
    private int chakra;

    @Enumerated(EnumType.STRING)
    private NinjaType ninjaType;

    public Character() {
    }

    public Character(Long id, String name, int age, String village, ArrayList<String> jutsus, int chakra, NinjaType ninjaType) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.village = village;
        this.jutsus = jutsus;
        this.chakra = chakra;
        this.ninjaType = ninjaType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public ArrayList<String> getJutsus() {
        return jutsus;
    }

    public void setJutsus(ArrayList<String> jutsus) {
        this.jutsus = jutsus;
    }

    public int getChakra() {
        return chakra;
    }

    public void setChakra(int chakra) {
        this.chakra = chakra;
    }

    public boolean addJutsu(String jutsu) {
        if (jutsus.contains(jutsu)) {
            return false;
        }
        return jutsus.add(jutsu);
    }

    public NinjaType getNinjaType() {
        return ninjaType;
    }

    public void setNinjaType(NinjaType ninjaType) {
        this.ninjaType = ninjaType;
    }

    public void increaseChakra(int amount){
        this.chakra += amount;
    }

    public String displayInfo() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", village='" + village + '\'' +
                ", jutsus=" + jutsus +
                ", chakra=" + chakra +
                ", ninjaType=" + ninjaType +
                '}';
    }
}

