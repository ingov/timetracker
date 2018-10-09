package de.spiegel.timetracker.presentation;

import javax.enterprise.inject.Model;

/**
 *
 * @author veithi
 */
@Model
public class Index {

    private String message;

    public Index() {
        this.message = "HELLO";
    }

    public String getMessage() {
        return message;
    }

}
