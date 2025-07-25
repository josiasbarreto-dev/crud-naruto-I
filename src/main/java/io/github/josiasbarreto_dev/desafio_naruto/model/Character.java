package io.github.josiasbarreto_dev.desafio_naruto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<Jutsu> jutsus = new ArrayList<>();
    protected Integer chakra = 100;
    protected Integer life;

    public Character(String name, Integer life) {
        this.name = name;
        this.life = life;
    }

    public boolean addJutsu(Jutsu jutsu){
        boolean exists = jutsus.stream().anyMatch(existingJutsu -> existingJutsu.getName().equals(jutsu.getName()));
        if (!exists) {
            jutsu.setCharacter(this);
            jutsus.add(jutsu);
            System.out.println("Jutsu '" + jutsu.getName() + "' added to " + name + ".");
            return true;
        } else {
            System.out.println("Jutsu '" + jutsu.getName() + "' already exists in the character's jutsus. Not added.");
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
    public abstract NinjaType getType();
}

