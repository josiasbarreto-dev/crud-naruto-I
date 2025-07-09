package io.github.josiasbarreto_dev.desafio_naruto.exception;

public class NameAlreadyExistsException extends RuntimeException{
    public NameAlreadyExistsException(String message) {
        super(message);
    }
}
