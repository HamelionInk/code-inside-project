package com.nikitin.codeinsideproject.error;

public class NotesNotFoundException extends RuntimeException{

    public NotesNotFoundException() {
        super();
    }

    public NotesNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotesNotFoundException(final String message) {
        super(message);
    }

    public NotesNotFoundException(final Throwable cause) {
        super(cause);
    }
}
