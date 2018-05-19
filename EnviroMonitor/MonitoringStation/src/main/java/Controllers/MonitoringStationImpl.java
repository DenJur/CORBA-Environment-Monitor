package Controllers;

import Environment.MonitoringStationPOA;
import Environment.Reading;
import Interfaces.ISensor;
import Models.Settings;

/**
 * Implementation of IDL POA class.
 */
public class MonitoringStationImpl extends MonitoringStationPOA {

    private Settings settings;
    private ISensor sensor;

    public MonitoringStationImpl(ISensor sensor) {
        settings = Settings.getInstance();
        this.sensor = sensor;
    }

    @Override
    public String station_name() {
        return settings.getStationName();
    }

    @Override
    public String group_name() {
        return settings.getStationGroup();
    }

    @Override
    public String location() {
        return settings.getLocation();
    }

    @Override
    public float alarming_level() {
        return settings.getAlarmLevel();
    }

    @Override
    public void alarming_level(float newAlarming_level) {
        settings.setAlarmLevel(newAlarming_level);
    }

    @Override
    public Reading get_reading() {
        Reading reading = new Reading();
        reading.date_time = System.currentTimeMillis();
        reading.station_name = station_name();
        reading.reading_value = sensor.getReading();
        reading.status = settings.isActive();
        reading.alarm_level = alarming_level();
        reading.location = location();
        reading.group = group_name();
        return reading;
    }

    @Override
    public void turn_on() {
        settings.setActive(true);
    }

    @Override
    public void turn_off() {
        settings.setActive(false);
    }

    @Override
    public void reset() {
        sensor.reset();
    }
}
