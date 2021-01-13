package maze;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final String title;
    private final List<MenuItem> items = new ArrayList<>();

    public Menu(String title) {
        this.title = title;
    }

    private MenuItem getMenuItem(String actionKey) {
        return items.stream()
                .filter(it -> it.getActionKey().equals(actionKey))
                .findFirst()
                .orElse(null);
    }

    public void runMenuAction(String actionKey) {
        MenuItem menuItem = getMenuItem(actionKey);
        if (menuItem == null) {
            return;
        }
        menuItem.runAction();
    }

    public Menu addMenuItem(String actionKey, String description, Action action) {
        items.add(new MenuItem(actionKey, description, action));
        return this;
    }

    public void removeMenuItem(String actionKey) {
        MenuItem item = getMenuItem(actionKey);
        if (item == null) {
            return;
        }

        items.remove(item);
    }

    public boolean hasAction(String actionKey) {
        return getMenuItem(actionKey) != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n");
        for (MenuItem item : items) {
            sb.append(item).append("\n");
        }
        return sb.toString().strip();
    }
}
