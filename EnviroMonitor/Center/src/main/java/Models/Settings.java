package Models;

/**
 * Singleton that stores program settings during runtime.
 */
public class Settings {
    private static Settings instance;
    private String name;
    private String IORPath;
    private boolean load;

    private Settings() {
        load=false;
    }

    public static Settings getInstance() {
        if (instance == null)
            instance = new Settings();
        return instance;
    }

    public String getIORPath() {
        return IORPath;
    }

    public void setIORPath(String IORPath) {
        this.IORPath = IORPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Indicates whether program should try to load previous state from files
     * @return - if state should be loaded
     */
    public boolean shouldLoad() {
        return load;
    }

    public void load() {
        this.load = true;
    }
}
