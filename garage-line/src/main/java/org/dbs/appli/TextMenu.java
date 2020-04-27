package org.dbs.appli;

import java.util.ArrayList;
import java.util.List;

public class TextMenu {
        private List<TextMenuItem> items = new ArrayList<>();
        private String title;

        public TextMenu(String title) {
            this.title = title;
        }

        public TextMenu doOption(int option) {
            if (option == 0) return null;
            option--;
            if (option >= items.size()) {
                System.out.println("Unknown option " + option);
                return this;
            }
            items.get(option).select();
            TextMenu next = items.get(option).getSubMenu();

            return next == null ? this : next;
        }

        public void addItem(TextMenuItem item) {
            items.add(item);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(title).append("\n");
            sb.append("-".repeat(title.length()));
            sb.append("\n");
            for (int i = 0; i < items.size(); i++) {
                sb.append((i + 1)).append(") ").append(items.get(i)).append("\n");
            }
            sb.append("0) Quit");
            return sb.toString();
        }

    public String getTitle() {
        return this.title;
    }
}