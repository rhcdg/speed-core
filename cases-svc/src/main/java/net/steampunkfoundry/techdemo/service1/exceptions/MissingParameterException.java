package net.steampunkfoundry.techdemo.service1.exceptions;

public class MissingParameterException extends Exception {

    public MissingParameterException(String missingParameter) {
        super("'" + missingParameter + "' is required.");
    }
}
