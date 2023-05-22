package com.company.jmixpm.uitest.screen;

import com.codeborne.selenide.Condition;
import io.jmix.masquerade.Wire;
import io.jmix.masquerade.base.Composite;
import io.jmix.masquerade.component.Button;
import io.jmix.masquerade.component.Table;

import static io.jmix.masquerade.Components.wire;

public class ProjectBrowse extends Composite<ProjectBrowse> {

    @Wire
    private Button createBtn;
    @Wire
    private Table projectsTable;

    public Table getProjectTable() {
        return projectsTable;
    }

    public ProjectEdit create() {
        createBtn.should(Condition.visible)
                .click();

        return wire(ProjectEdit.class);
    }
}
