package com.company.jmixpm.uitest.screen;

import io.jmix.masquerade.Wire;
import io.jmix.masquerade.base.Composite;
import io.jmix.masquerade.component.SideMenu;

public class MainScreen extends Composite<MainScreen> {

    @Wire
    private SideMenu sideMenu;

    public ProjectBrowse openProjectBrowse() {
        return sideMenu.openItem(new SideMenu.Menu<>(ProjectBrowse.class,
                "application", "Project.browse"));
    }
}
