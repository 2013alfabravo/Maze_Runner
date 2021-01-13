package maze;

public class MenuItem {
    private final String actionKey;
    private final String description;
    private final Action action;

    public MenuItem(String actionKey, String description, Action action) {
        this.actionKey = actionKey;
        this.description = description;
        this.action = action;
    }

    public void runAction() {
        action.run();
    }

    public String getActionKey() {
        return actionKey;
    }

    @Override
    public String toString() {
        return actionKey + ". " + description;
    }
}
