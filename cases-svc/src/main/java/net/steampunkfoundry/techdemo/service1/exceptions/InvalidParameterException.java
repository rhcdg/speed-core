package net.steampunkfoundry.techdemo.service1.exceptions;

public class InvalidParameterException extends Exception {

    public InvalidParameterException(String invalidParameter) {
        super("Parameter '" + invalidParameter + "' is invalid.");
    }
}