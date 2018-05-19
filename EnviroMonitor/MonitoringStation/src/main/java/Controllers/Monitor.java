package Controllers;

import Environment.MonitoringStationPOA;
import Environment.Reading;
import Environment.RegionalCentre;
import Environment.RegionalCentreHelper;
import Models.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CosNaming.NamingContextExt;

/**
 * Monitors readings for the sensor and triggers alarm at Regional Centre if reading is above the threshold.
 */
public class Monitor extends Thread {

    private Settings settings;
    private NamingContextExt namingService;
    private boolean stop;
    private MonitoringStationPOA station;
    private final Object lock;
    private static final Logger logger = LogManager.getLogger(Monitor.class.getName());

    public Monitor(NamingContextExt namingService, MonitoringStationPOA station) {
        this.namingService = namingService;
        this.settings = Settings.getInstance();
        this.stop = false;
        this.station = station;
        this.lock = new Object();
    }

    @Override
    public void run() {
        //continue running until stop is triggered
        while (!stop) {
            //need to synchronize so that sensor is not shutdown while reading
            synchronized (lock) {
                if (settings.isActive()) {
                    Reading reading = station.get_reading();
                    if (reading.reading_value >= settings.getAlarmLevel())
                        reportAlarm(reading);
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.warn("Error sleeping in monitor thread",e);
            }
        }
    }

    /**
     * Triggers stop flag for this thread.
     */
    public void stopThis() {
        synchronized (lock) {
            stop = true;
        }
    }

    /**
     * Report alarm to the regional centre.
     * @param reading - Reading that triggered the alarm.
     */
    private void reportAlarm(Reading reading) {
        try {
            RegionalCentre center = RegionalCentreHelper.narrow(namingService.resolve_str(settings.getRegStationName()));
            center.raise_alarm(reading);
        } catch (Exception e) {
            logger.error("Error reporting potential alarm",e);
        }
    }
}
