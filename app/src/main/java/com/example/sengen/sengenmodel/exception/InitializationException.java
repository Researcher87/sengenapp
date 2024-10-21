package com.example.sengen.sengenmodel.exception;

/**
 * General exception used for program start and initialization.
 */
public class InitializationException extends Exception {
    public InitializationException() {
    }

    public InitializationException(String message) {
        super(message);
    }

    public InitializationException(String message, Exception e) {
        super(message, e);
    }
}
