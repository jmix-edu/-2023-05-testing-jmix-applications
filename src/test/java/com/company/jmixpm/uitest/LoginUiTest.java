package com.company.jmixpm.uitest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.company.jmixpm.uitest.screen.LoginScreen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.jmix.masquerade.Conditions.*;
import static io.jmix.masquerade.Selectors.$j;

public class LoginUiTest {

    @Test
    @DisplayName("Checks login screen")
    public void login() {
        Selenide.open("/");

        LoginScreen loginScreen = $j(LoginScreen.class);

        loginScreen.getUsernameField()
                .shouldBe(EDITABLE)
                .shouldBe(ENABLED);

        loginScreen.getUsernameField()
                .setValue("")
                .shouldBe(Condition.value(""));

        loginScreen.getUsernameField()
                .setValue("admin");
        loginScreen.getPasswordField()
                .setValue("admin");

        loginScreen.getWelcomeLabelTest()
                .shouldBe(VISIBLE);

        loginScreen.getLoginButton()
                .shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .shouldHave(caption("Submit"));

        $j("loginForm").shouldBe(VISIBLE);

        loginScreen.getLoginButton().click();
    }
}
