package io.github.josiasbarreto_dev.desafio_naruto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity(name = "character")
@Table(name = "character")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ninjaType")
@Getter
@Setter
@NoArgsConstructor
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

    public Character(String name, Integer life) {
        this.name = name;
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

