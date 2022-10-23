package com.nikitin.codeinsideproject.error;

public class PersonAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public PersonAlreadyExistException() {
        super();
    }

    public PersonAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PersonAlreadyExistException(final String message) {
        super(message);
    }

    public PersonAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
