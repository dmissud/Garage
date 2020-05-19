package org.dbs.garage.shell;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextMenuItem {
    private final String title;
    private TextMenu submenu;
    private final ActionListener onselect;

    public TextMenuItem(TextMenu subMenu, ActionListener onselect) {
        this.title = subMenu.getTitle();
        this.submenu = subMenu;
        this.onselect = onselect;
    }

    public TextMenuItem(String title, TextMenu submenu, ActionListener onselect) {
        this.title = title;
        this.submenu = submenu;
        this.onselect = onselect;
    }

    public String getTitle() {
        return title;
    }

    public void setSubMenu(TextMenu submenu) {
        this.submenu = submenu;
    }

    public void select() {
        if (onselect != null)
            onselect.actionPerformed(new ActionEvent(this, 0, "select"));
    }

    public TextMenu getSubMenu() {
        return submenu;
    }

    public String toString() {
        return title;
    }

}
