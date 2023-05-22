package com.company.jmixpm.bdd;

import com.codeborne.selenide.Selenide;
import com.company.jmixpm.uitest.screen.*;
import com.thoughtworks.gauge.Step;
import io.jmix.masquerade.Selectors;
import io.jmix.masquerade.component.HasActions;

import static io.jmix.masquerade.Conditions.ENABLED;
import static io.jmix.masquerade.Conditions.VISIBLE;
import static io.jmix.masquerade.Selectors.$j;

public class ProjectBddTestSpec {

    @Step("Open application in the browser")
    public void openApplicationUrl() {
        Selenide.open("/");
    }

    @Step("Log in as user with <username> username and <password> password")
    public void login(String username, String password) {
        LoginScreen loginScreen = $j(LoginScreen.class);

        loginScreen.getUsernameField()
                .shouldBe(VISIBLE, ENABLED)
                .setValue(username);
        loginScreen.getPasswordField()
                .shouldBe(VISIBLE, ENABLED)
                .setValue(password);

        loginScreen.getLoginButton()
                .shouldBe(VISIBLE)
                .click();
    }

    @Step("Open the project browser")
    public void openProjectBrowser() {
        $j(MainScreen.class).openProjectBrowse();
    }

    @Step("Open the project editor")
    public void openProjectEditor() {
        $j(ProjectBrowse.class).create();
    }

    @Step("Fill from fields with following values: name is <name>, manager is <manager>")
    public void fillFormFields(String name, String manager) {
        ProjectEdit projectEdit = $j(ProjectEdit.class);

        projectEdit.getNameField()
                .shouldBe(VISIBLE, ENABLED)
                .setValue(name);

        UserBrowse userBrowse = projectEdit.getManagerField()
                .shouldBe(VISIBLE, ENABLED)
                .triggerAction(UserBrowse.class, new HasActions.Action("entityLookup"));
        userBrowse.selectUser(manager);
    }

    @Step("Save new project")
    public void saveNewProject() {
        $j(ProjectEdit.class).commitAndClose();
    }

    @Step("Make sure the new project with <name> name is added to project table")
    public void checkProjectCreation(String name) {
        $j(ProjectBrowse.class).getProjectTable()
                .shouldBe(VISIBLE, ENABLED)
                .getRow(Selectors.byText(name))
                .shouldBe(VISIBLE);
    }
}
