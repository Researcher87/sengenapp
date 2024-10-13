package com.example.sengen.sengen24.exception;

/**
 * This exception indicates that a sentence or phrase could not be generated, because no possible word for
 * selection was available. In most cases this exception may be a result of a too restrictive word selection
 * configuration.
 */
public class NoWordMatchException extends Exception {
    public NoWordMatchException() {
    }

    public NoWordMatchException(String message) {
        super(message);
    }
}
