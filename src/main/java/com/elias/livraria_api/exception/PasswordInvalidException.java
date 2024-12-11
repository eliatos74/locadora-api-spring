package com.elias.livraria_api.exception;

public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException(String messsage) {
        super(messsage);
    }
}
