package net.steampunkfoundry.techdemo.client.printclient;

import javafx.stage.Stage;
import net.steampunkfoundry.techdemo.client.printclient.login.LoginManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Stage Listener listening for the StageReadyEvent
 */
@Component
public class StageListenner implements ApplicationListener<StageReadyEvent> {

    private final ApplicationContext ac;
    private final LoginManager loginManager;

    /**
     * Constructor
     *
     * @param ac           ApplicationContext
     * @param loginManager LoginManager
     */
    public StageListenner(ApplicationContext ac, LoginManager loginManager) {
        this.ac = ac;
        this.loginManager = loginManager;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        Stage stage = stageReadyEvent.getStage();
        loginManager.setStage(stage);
        loginManager.showLoginScreen();
        stage.setScene(loginManager.getScene());
        stage.show();
    }
}
