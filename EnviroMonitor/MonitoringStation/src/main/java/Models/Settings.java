package Models;

/**
 * Singleton that stores program settings during runtime.
 */
public class Settings {
    private String stationName;
    private String stationGroup;
    private float alarmLevel;
    private String location;
    private String IORPath;
    private String regStationName;
    private boolean active;
    private static Settings instance;
    private final Object lock;

    private Settings() {
        lock = new Object();
    }

    public static Settings getInstance() {
        if (instance == null)
            instance = new Settings();
        return instance;
    }

    public String getStationName() {
        return stationName;
    }

    public boolean isActive() {
        return active;
    }

    public String getRegStationName() {
        return regStationName;
    }

    public void setRegStationName(String regStationName) {
        synchronized (lock) {
            this.regStationName = regStationName;
        }
    }

    public void setActive(boolean active) {
        synchronized (lock) {
            this.active = active;
        }
    }

    public void setStationName(String stationName) {
        synchronized (lock) {
            this.stationName = stationName;
        }
    }

    public String getStationGroup() {
        return stationGroup;
    }

    public void setStationGroup(String stationGroup) {
        synchronized (lock) {
            this.stationGroup = stationGroup;
        }
    }

    public float getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(float alarmLevel) {
        synchronized (lock) {
            this.alarmLevel = alarmLevel;
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        synchronized (lock) {
            this.location = location;
        }
    }

    public String getIORPath() {
        return IORPath;
    }

    public void setIORPath(String IORPath) {
        synchronized (lock) {
            this.IORPath = IORPath;
        }
    }
}
