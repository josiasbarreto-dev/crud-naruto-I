package io.github.josiasbarreto_dev.desafio_naruto.model;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity(name = "character")
@Table(name = "character")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ninjaType")
public abstract class Character{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(unique = true)
    protected String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "jutsus", joinColumns = @JoinColumn(name = "character_id"))
    @MapKeyColumn(name = "jutsu_name")
    protected Map<String, Jutsu> jutsus  = new HashMap<>();
    protected Integer chakra = 100;
    protected Integer life;

    public Character() {
    }

    public Character(String name, Integer life) {
        this.name = name;
        this.life = life;
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

    public Map<String, Jutsu> getJutsus() {
        return jutsus;
    }

    public Integer getChakra() {
        return chakra;
    }

    public void setChakra(Integer chakra) {
        this.chakra += chakra;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public boolean addJutsu(String name, Jutsu jutsu){
        if (jutsus.putIfAbsent(name, jutsu) == null) {
            System.out.println("Jutsu '" + name + "' added successfully!");
            return true;
        } else {
            System.out.println("Jutsu '" + name + "' already exists in the map. Not added.");
            return false;
        }
    }

    public void loseLife(Integer damage){
        this.life -= damage;
        if(this.life < 0){
            this.life = 0;
        }
    }

    public abstract void useJutsu(String jutsuName, Character adversaryNinja);
    public abstract void dodge(Jutsu jutsu);
}

