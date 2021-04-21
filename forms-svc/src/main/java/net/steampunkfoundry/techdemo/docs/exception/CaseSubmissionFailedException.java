package net.steampunkfoundry.techdemo.docs.exception;

import net.steampunkfoundry.techdemo.docs.service.CaseClient.CaseResponse;

public class CaseSubmissionFailedException extends Exception {

    public CaseSubmissionFailedException(String anumber, CaseResponse response) {
        super("Case submission by " + anumber + " failed: " + response.getMsg());
    }

}
