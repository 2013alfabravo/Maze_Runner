package maze;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private String title = "";
    private final List<MenuItem> items = new ArrayList<>();

    private static class MenuItem {
        private final String actionKey;
        private final String text;
        private final Action action;
        private boolean isVisible = true;

        private MenuItem(String actionKey, String text, Action action) {
            this.actionKey = actionKey;
            this.text = text;
            this.action = action;
        }

        private void runAction() {
            action.run();
        }

        private String getActionKey() {
            return actionKey;
        }

        private void makeVisible() {
            isVisible = true;
        }

        public void makeInvisible() {
            isVisible = false;
        }

        public boolean isVisible() {
            return isVisible;
        }

        @Override
        public String toString() {
            return actionKey + ". " + text;
        }
    }

    private MenuItem getMenuItem(String actionKey) {
        return items.stream()
                .filter(it -> it.getActionKey().equals(actionKey))
                .findFirst()
                .orElse(null);
    }

    public Menu setTitle(String title) {
        this.title = title;
        return this;
    }

    public void runMenuAction(String actionKey) {
        MenuItem menuItem = getMenuItem(actionKey);
        if (menuItem == null) {
            return;
        }
        menuItem.runAction();
    }

    public Menu addMenuItem(String actionKey, String text, Action action) {
        items.add(new MenuItem(actionKey, text, action));
        return this;
    }

    public void removeMenuItem(String actionKey) {
        MenuItem item = getMenuItem(actionKey);
        if (item == null) {
            return;
        }

        items.remove(item);
    }

    public Menu setVisible(String actionKey) {
        MenuItem item = getMenuItem(actionKey);
        if (item != null) {
            item.makeVisible();
        }
        return this;
    }

    public Menu setInvisible(String actionKey) {
        MenuItem item = getMenuItem(actionKey);
        if (item != null) {
            item.makeInvisible();
        }
        return this;
    }

    public boolean hasAction(String actionKey) {
        MenuItem item = getMenuItem(actionKey);
        return  item != null && item.isVisible();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n");
        for (MenuItem item : items) {
            if (item.isVisible()) {
                sb.append(item).append("\n");
            }
        }
        return sb.toString().strip();
    }
}
