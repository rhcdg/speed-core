package net.steampunkfoundry.techdemo.client.printclient.controller;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.util.concurrent.TimeoutException;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import net.steampunkfoundry.techdemo.client.printclient.FxApplication;
import net.steampunkfoundry.techdemo.client.printclient.PrintClientApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;


@SpringBootTest(classes = PrintClientApplication.class)
@ActiveProfiles("test")
@ContextConfiguration
@RunWith(SpringRunner.class)
public class LoginControllerTest extends ApplicationTest {

    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(FxApplication.class);
    }

    @After
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[] {});
        release(new MouseButton[] {});
    }

    @Test
    public void invalidLoginTest() {
        // when:
        clickOn(".button");

        // then:
        verifyThat("#invalidLoginError", hasText("Invalid Login"));
        verifyThat("#invalidLoginError", isVisible());
    }

    @Test
    public void validLoginTest() {
        // when:
        clickOn("#user");
        write("adjudicator@test.com");
        clickOn("#password");
        write("P@55word");
        clickOn(".button");

        // then:
        verifyThat("#printButton", isVisible());
    }

    @Test
    public void invalidRoleTest() {
        // when:
        clickOn("#user");
        write("readonly@test.com");
        clickOn("#password");
        write("P@55word");
        clickOn(".button");

        // then:
        verifyThat("#invalidLoginError", hasText("Invalid Login"));
        verifyThat("#invalidLoginError", isVisible());
    }
}

