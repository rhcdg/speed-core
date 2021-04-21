package net.steampunkfoundry.techdemo.client.printclient;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

/**
 * This Event is fired when the Stage is ready and holds a reference to the stage.
 */
class StageReadyEvent extends ApplicationEvent {

    /**
     * Constructor
     *
     * @param source the stage
     */
    public StageReadyEvent(Stage source) {
        super(source);
    }

    /**
     * return the stage
     *
     * @return the stage
     */
    public Stage getStage() {
        return Stage.class.cast(source);
    }
}
