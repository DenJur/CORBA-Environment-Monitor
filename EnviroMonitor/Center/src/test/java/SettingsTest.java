import Models.Settings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SettingsTest {

    @Test
    void nameTest() {
        String name="testCentre";
        Settings s=Settings.getInstance();
        s.setName(name);
        assertEquals(name,s.getName());
        name="testCentreOther";
        s.setName(name);
        assertEquals(name,s.getName());
    }

    @Test
    void loadTest() {
        Settings s=Settings.getInstance();
        assertFalse(s.shouldLoad());
        s.load();
        assertTrue(s.shouldLoad());
    }

    @Test
    void IORTest() {
        String path="testIOR";
        Settings s=Settings.getInstance();
        s.setIORPath(path);
        assertEquals(path,s.getIORPath());
        path="testIOROther";
        s.setIORPath(path);
        assertEquals(path,s.getIORPath());
    }
}
