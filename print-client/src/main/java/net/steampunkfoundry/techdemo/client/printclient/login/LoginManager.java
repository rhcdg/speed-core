package net.steampunkfoundry.techdemo.client.printclient.login;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.steampunkfoundry.techdemo.client.printclient.controller.CaseListController;
import net.steampunkfoundry.techdemo.client.printclient.controller.LoginController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * LoginManager opens the caseList window if the user is authenticated; otherwise sends them to the
 * login screen.
 */
@Component
public class LoginManager {

    private final ApplicationContext ac;
    private Scene scene;
    private Resource loginResource;
    private Resource caseListResource;
    private FXMLLoader loginFxmlLoader;
    private FXMLLoader caseListFxmlLoader;
    private Stage stage;


    /**
     * constructor
     *
     * @param loginResource    login fxmlResource
     * @param caseListResource caseList fxml Resource
     * @param ac               application context
     */
    public LoginManager(@Value("classpath:/fxml/login.fxml") Resource loginResource,
            @Value("classpath:/fxml/caselist.fxml") Resource caseListResource,
            ApplicationContext ac) {
        this.ac = ac;
        this.scene = new Scene(new StackPane());
        this.loginResource = loginResource;
        this.caseListResource = caseListResource;
        try {
            loginFxmlLoader = new FXMLLoader(loginResource.getURL());
            loginFxmlLoader.setControllerFactory(ac::getBean);
            caseListFxmlLoader = new FXMLLoader(caseListResource.getURL());
            caseListFxmlLoader.setControllerFactory(ac::getBean);
        } catch (IOException e) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Callback method invoked to notify that a user has been authenticated. Will show the main
     * application screen.
     */
    public void authenticated(String sessionID) {
        showCaseListScreen(sessionID);
    }

    /**
     * Callback method invoked to notify that a user has logged out of the main application. Will
     * show the login application screen.
     */
    public void logout() {
        showLoginScreen();
    }

    /**
     * show the login screen
     */
    public void showLoginScreen() {
        try {
            stage.setWidth(250);
            stage.setHeight(175);
            stage.setTitle("Speed Print Client Login");
            scene.setRoot((Parent) loginFxmlLoader.load());
            LoginController controller =
                    loginFxmlLoader.<LoginController>getController();
            controller.initLoginManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * show the case list screen
     *
     * @param sessionID the session ID
     */
    private void showCaseListScreen(String sessionID) {
        try {
            stage.setWidth(750);
            stage.setHeight(750);
            stage.setTitle("Case List");
            scene.setRoot((Parent) caseListFxmlLoader.load());
            CaseListController controller =
                    caseListFxmlLoader.<CaseListController>getController();
            controller.initLoginManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * returns the current scene
     *
     * @return the current scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * sets the stage.
     *
     * @param stage stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

