CREATE TABLE characters (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    chakra INT DEFAULT 100,
    life INT,
    ninjaType VARCHAR(31),
    CONSTRAINT uq_character_name UNIQUE (name)
);

CREATE TABLE jutsu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    damage INT,
    chakra_consumption INT,
    character_id BIGINT,

    CONSTRAINT fk_jutsu_character FOREIGN KEY (character_id) REFERENCES characters(id)
        ON DELETE CASCADE
);