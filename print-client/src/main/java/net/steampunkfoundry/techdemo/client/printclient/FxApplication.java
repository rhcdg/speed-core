package net.steampunkfoundry.techdemo.client.printclient;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * This is the Main FX Application bootstrapped by PrintClientApplication
 */
public class FxApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        ApplicationContextInitializer<GenericApplicationContext> initializer = new ApplicationContextInitializer<GenericApplicationContext>() {
            @Override
            public void initialize(GenericApplicationContext ac) {
                ac.registerBean(Application.class, () -> FxApplication.this);
                ac.registerBean(Parameters.class, () -> getParameters());
                ac.registerBean(HostServices.class, () -> getHostServices());
            }
        };
        context = new SpringApplicationBuilder().sources(PrintClientApplication.class)
                .initializers(initializer).run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        context.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() throws Exception {
        context.close();
        Platform.exit();
    }
}


