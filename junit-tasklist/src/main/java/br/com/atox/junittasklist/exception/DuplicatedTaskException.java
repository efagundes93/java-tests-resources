package br.com.atox.junittasklist.exception;

public class DuplicatedTaskException extends RuntimeException {
    public DuplicatedTaskException(String message) {
        super(message);
    }
}