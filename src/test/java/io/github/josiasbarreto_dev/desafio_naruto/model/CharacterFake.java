package io.github.josiasbarreto_dev.desafio_naruto.model;

class CharacterFake extends Character {
    public CharacterFake(String name, Integer life) {
        super(name, life);
    }

    @Override
    public void useJutsu(String jutsuName, Character adversaryNinja) {}

    @Override
    public void dodge(Jutsu jutsu) {}

    @Override
    public NinjaType getType() {
        return null;
    }
}

