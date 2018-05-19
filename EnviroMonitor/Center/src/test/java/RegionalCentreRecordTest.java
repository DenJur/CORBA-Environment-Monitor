import Models.RegionalCenterRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegionalCentreRecordTest {

    @Test
    void nameTest() {
        String name = "testName";
        RegionalCenterRecord r = new RegionalCenterRecord(name);
        assertEquals(name,r.getName());
    }

    @Test
    void alarmInitTest() {
        RegionalCenterRecord r = new RegionalCenterRecord("");
        assertFalse(r.getAlarm());
    }

    @Test
    void alarmTriggerTest() {
        RegionalCenterRecord r = new RegionalCenterRecord("");
        r.triggerAlarm();
        assertTrue(r.getAlarm());
    }

    @Test
    void alarmResetTest() {
        RegionalCenterRecord r = new RegionalCenterRecord("");
        r.triggerAlarm();
        r.resetAlarm();
        assertFalse(r.getAlarm());
    }
}
