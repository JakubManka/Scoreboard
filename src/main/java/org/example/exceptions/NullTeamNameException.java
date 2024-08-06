package org.example.exceptions;

public class NullTeamNameException extends RuntimeException{
    public NullTeamNameException() {
        super("Team name cannot be null");
    }
}
