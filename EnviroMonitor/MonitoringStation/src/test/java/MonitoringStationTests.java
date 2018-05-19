import Controllers.MonitoringStationImpl;
import Environment.MonitoringStationPOA;
import Environment.Reading;
import Interfaces.ISensor;
import Models.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonitoringStationTests {
    private static final float reading = 12.34f;
    private static MonitoringStationPOA station;
    private static final String name = "stationName";
    private static final String group = "stationGroup";
    private static final String location = "stationLocation";

    @BeforeEach
    void init() {
        TestSensor sensor = new TestSensor();
        Settings settings = Settings.getInstance();
        settings.setAlarmLevel(reading);
        settings.setStationGroup(group);
        settings.setLocation(location);
        settings.setStationName(name);
        station = new MonitoringStationImpl(sensor);
    }

    @Test
    void alarmLevelTest() {
        assertEquals(station.alarming_level(), reading);
        float newLevel = 10f;
        station.alarming_level(newLevel);
        assertEquals(newLevel, station.alarming_level());
    }

    @Test
    void locationTest() {
        assertEquals(location, station.location());
    }

    @Test
    void groupTest() {
        assertEquals(group, station.group_name());
    }

    @Test
    void nameTest() {
        assertEquals(name, station.station_name());
    }

    @Test
    void readingTest() {
        Reading r = station.get_reading();
        assertEquals(reading, r.alarm_level);
        assertEquals(reading, r.reading_value);
        assertEquals(location, r.location);
        assertEquals(name, r.station_name);
    }

    private static class TestSensor implements ISensor {

        @Override
        public float getReading() {
            return reading;
        }

        @Override
        public void reset() {
        }
    }
}
