package com.company.jmixpm.uitest;

import com.codeborne.selenide.Selenide;
import com.company.jmixpm.JmixPmApplication;
import com.company.jmixpm.extention.ChromeExtension;
import com.company.jmixpm.uitest.screen.*;
import io.jmix.masquerade.Selectors;
import io.jmix.masquerade.component.HasActions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import static io.jmix.masquerade.Conditions.ENABLED;
import static io.jmix.masquerade.Conditions.VISIBLE;
import static io.jmix.masquerade.Selectors.$j;

@ExtendWith(ChromeExtension.class)
@SpringBootTest(classes = JmixPmApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"jmix.ui.test-mode=true"})
public class ProjectUiTest {

    @Test
    @DisplayName("Checks project creation")
    public void checkProjectCreation() {
        Selenide.open("/");

        LoginScreen loginScreen = $j(LoginScreen.class);

        loginScreen.getUsernameField()
                .shouldBe(VISIBLE, ENABLED)
                .setValue("dev1");
        loginScreen.getPasswordField()
                .shouldBe(VISIBLE, ENABLED)
                .setValue("dev1");

        loginScreen.getLoginButton()
                .shouldBe(VISIBLE)
                .click();

        ProjectBrowse projectBrowse = $j(MainScreen.class).openProjectBrowse();

        ProjectEdit projectEdit = projectBrowse.create();
        projectEdit.getNameField()
                .shouldBe(VISIBLE, ENABLED)
                .setValue("UI testing");

        projectEdit.getStartDateField()
                .shouldBe(VISIBLE, ENABLED)
                .setDateValue("22/05/2023");
        projectEdit.getEndDateField()
                .shouldBe(VISIBLE, ENABLED)
                .setDateValue("23/05/2023");

        UserBrowse userBrowse = projectEdit.getManagerField()
                .shouldBe(VISIBLE, ENABLED)
                .triggerAction(UserBrowse.class, new HasActions.Action("entityLookup"));

        userBrowse.selectUser("dev1");

        projectEdit.commitAndClose();

        projectBrowse.getProjectTable()
                .shouldBe(VISIBLE, ENABLED)
                .getRow(Selectors.byText("UI testing"))
                .shouldBe(VISIBLE);
    }
}
