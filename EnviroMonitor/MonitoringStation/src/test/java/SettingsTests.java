import Models.Settings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SettingsTests {

    @Test
    void stationNameTest() {
        String name="testStation";
        Settings s=Settings.getInstance();
        s.setStationName(name);
        assertEquals(name,s.getStationName());
        name="testStationOther";
        s.setStationName(name);
        assertEquals(name,s.getStationName());
    }

    @Test
    void regionalCentreNameTest() {
        String name="testCentre";
        Settings s=Settings.getInstance();
        s.setRegStationName(name);
        assertEquals(name,s.getRegStationName());
        name="testCentreOther";
        s.setRegStationName(name);
        assertEquals(name,s.getRegStationName());
    }

    @Test
    void IORPathTest() {
        String path="testPath";
        Settings s=Settings.getInstance();
        s.setIORPath(path);
        assertEquals(path,s.getIORPath());
        path="testPathOther";
        s.setIORPath(path);
        assertEquals(path,s.getIORPath());
    }

    @Test
    void locationTest() {
        String location="testLocation";
        Settings s=Settings.getInstance();
        s.setLocation(location);
        assertEquals(location,s.getLocation());
        location="testLocationOther";
        s.setLocation(location);
        assertEquals(location,s.getLocation());
    }

    @Test
    void stationGroupTest() {
        String group="testStationGroup";
        Settings s=Settings.getInstance();
        s.setStationGroup(group);
        assertEquals(group,s.getStationGroup());
        group="testStationGroupOther";
        s.setStationGroup(group);
        assertEquals(group,s.getStationGroup());
    }

    @Test
    void alarmThresholdTest() {
        float threshold=12.3f;
        Settings s=Settings.getInstance();
        s.setAlarmLevel(threshold);
        assertEquals(threshold,s.getAlarmLevel());
        threshold=3.21f;
        s.setAlarmLevel(threshold);
        assertEquals(threshold,s.getAlarmLevel());
    }

    @Test
    void stationActiveTest() {
        boolean active=true;
        Settings s=Settings.getInstance();
        s.setActive(active);
        assertEquals(active,s.isActive());
        active=false;
        s.setActive(active);
        assertEquals(active,s.isActive());
    }
}
