package com.company.jmixpm.uitest.screen;

import io.jmix.masquerade.Wire;
import io.jmix.masquerade.base.Composite;
import io.jmix.masquerade.component.*;
import org.openqa.selenium.support.FindBy;

public class LoginScreen extends Composite<LoginScreen> {

    @Wire
    private TextField usernameField;

    @Wire
    private PasswordField passwordField;

    @Wire(path = {"loginForm", "loginButton"})
    private Button loginButton;

    @Wire
    private ComboBox localesField;

    @Wire
    private Label welcomeLabel;

    @FindBy(className = "jmix-login-caption")
    private Label welcomeLabelTest;

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public ComboBox getLocalesField() {
        return localesField;
    }

    public Label getWelcomeLabel() {
        return welcomeLabel;
    }

    public Label getWelcomeLabelTest() {
        return welcomeLabelTest;
    }
}
