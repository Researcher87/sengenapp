package com.example.sengen.sengen24.exception;

/**
 * This exceptions is used to indicate a problem in the dictionary, like a missing
 * property (missing genus form), an invalid value or property and the like.
 */
public class DictionaryEntryException extends Exception {
    public DictionaryEntryException() {
    }

    public DictionaryEntryException(String message) {
        super(message);
    }
}
