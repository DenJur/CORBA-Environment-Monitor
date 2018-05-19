package Controllers;

import Environment.*;
import Models.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

/**
 * Thread that listens to network calls from CORBA.
 */
public class OrbRunner extends Thread {

    private ORB orb;
    private Settings settings;
    private NamingContextExt namingService;
    private MonitoringStationPOA station;
    private static final Logger logger = LogManager.getLogger(OrbRunner.class.getName());

    public OrbRunner(ORB orb, NamingContextExt namingService, MonitoringStationPOA station) {
        this.orb = orb;
        this.namingService = namingService;
        this.settings = Settings.getInstance();
        this.station = station;
    }

    @Override
    public void run() {
        try {
            //register with the naming service
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(station);
            MonitoringStation cref = MonitoringStationHelper.narrow(ref);
            String name = settings.getStationName();
            NameComponent[] countName = namingService.to_name(name);
            namingService.rebind(countName, cref);

            //register with the regional centre
            RegionalCentre center = RegionalCentreHelper.narrow(namingService.resolve_str(settings.getRegStationName()));
            center.add_monitoring_station(settings.getStationName(), settings.getStationGroup(), settings.getLocation());
            orb.run();
        } catch (Exception e) {
            logger.fatal("Error initialising orb runner",e);
            System.exit(3);
        }
    }
}
