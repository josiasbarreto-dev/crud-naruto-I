package io.github.josiasbarreto_dev.desafio_naruto.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
