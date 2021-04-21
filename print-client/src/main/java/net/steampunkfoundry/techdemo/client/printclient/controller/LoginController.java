package net.steampunkfoundry.techdemo.client.printclient.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.steampunkfoundry.techdemo.client.printclient.login.AuthenticationHelper;
import net.steampunkfoundry.techdemo.client.printclient.login.LoginManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoginController implements Initializable {

    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Label invalidLoginError;

    @Value("${cognito.poolid}")
    private String cognitoPoolId;

    @Value("${cognito.region}")
    private String cognitoRegion;

    @Value("${cognito.clientid}")
    private String cognitoClientId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invalidLoginError.setVisible(false);
        user.requestFocus();
    }

    /**
     * Initialize the LoginController with the LoginManager
     *
     * @param loginManager loginManager
     */
    public void initLoginManager(final LoginManager loginManager) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String sessionID = authorize();
                if (sessionID != null) {
                    invalidLoginError.setVisible(false);
                    loginManager.authenticated(sessionID);
                } else {
                    invalidLoginError.setVisible(true);
                }
            }
        });
    }

    /**
     * Check authorization credentials. If accepted, return a sessionID for the authorized session
     * otherwise, return null.
     */
    private String authorize() {
        AuthenticationHelper helper = new AuthenticationHelper(cognitoPoolId, cognitoClientId,
                cognitoRegion);
        return helper.performSrpAuthentication(user.getText(), password.getText());
    }
}
